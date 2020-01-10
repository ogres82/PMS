
package com.jdry.pms.comm.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.comm.util.NoticePushUtil;
import com.jdry.pms.comm.util.RedisPool;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;

/**
 * Copyright 2017 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2017-2-23 下午2:59:11
 * @description
 *
 */
public class ExpireEventService {
	
	//已派工工单redis处理，判断是否需要推送通知
	public static void dispatchToRedis(Object object) {
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisObject();
			//报障报修工单
			if(object instanceof WorkMainDispatchEntity){
				String state = ((WorkMainDispatchEntity)object).getDispatch_status();
				String dispatchId = ((WorkMainDispatchEntity)object).getDispatch_id();
				if(state.equals("1")){  //派工操作，派工单存入redis
					jedis.hset("dispatchSpace", dispatchId, JSON.toJSONString(object));
				}else{     //其他操作，删除redis中派工单
					if(dispatchId != null){
						jedis.hdel("dispatchSpace", dispatchId);
					}
				}
			}
			//家政服务申报单
			if(object instanceof HouseworkEventEntity){
				String state = ((HouseworkEventEntity)object).getEvent_state();
				String dispatchId = ((HouseworkEventEntity)object).getId();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(state.equals("1")){  //派工，保存申报单预约时间
					if(((HouseworkEventEntity)object).getPre_time() != null){
						jedis.set(dispatchId+"-preTime", sdf.format(((HouseworkEventEntity)object).getPre_time()));
					}
				}else{   //其他
					if(dispatchId != null){
						jedis.del(dispatchId+"-preTime");
						jedis.hdel("dispatchSpace", dispatchId);
					}
				}
			}
			//家政服务派工单
			if(object instanceof HouseWorkEventSendEntity){
				String dispatchId = ((HouseWorkEventSendEntity)object).getEvent_id();
				jedis.hset("dispatchSpace", dispatchId, JSON.toJSONString(object));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			RedisPool.recycleJedisOjbect(jedis);
		}
	}
	//判断派工单时效，推送通知
	public static void delayedDispatchPush(){
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisObject();
			List<String> list = jedis.hvals("dispatchSpace");
			if(list.size() > 0){
				for(int i=0;i<list.size();i++){
					JSONObject object = JSON.parseObject(list.get(i));
					WorkMainDispatchEntity dispatch=JSON.toJavaObject(object, WorkMainDispatchEntity.class);
					HouseWorkEventSendEntity dispatch2=JSON.toJavaObject(object, HouseWorkEventSendEntity.class);
					if(dispatch.getDispatch_id() != null){  //通过判断对象内是否有值以判断为报修还是家政
						//派工时间超过一定时间后
						if(System.currentTimeMillis() - dispatch.getCreateTime().getTime() > 1000*60*2){
							NoticePushUtil.pushNotice(dispatch.getDispatch_handle_id(), dispatch.getDispatch_handle_name()+"，你有一个维修工单即将超时，请立即前往处理！","02");
							jedis.hdel("dispatchSpace", dispatch.getDispatch_id());
							System.out.print("推送通知---"+dispatch.getDispatch_handle_name());
						}
					}
					if(dispatch2.getId() != null){
						String preTimeString = jedis.get(dispatch2.getEvent_id()+"-preTime");
						if(preTimeString != null && !preTimeString.isEmpty()){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							//即将到预约时间
							if(sdf.parse(preTimeString).getTime() - System.currentTimeMillis() < 1000*60){
								NoticePushUtil.pushNotice(dispatch2.getOper_id(), dispatch2.getOper_name()+"，你有一个家政工单即将到预约时间，请前往处理！","02");
								jedis.hdel("dispatchSpace", dispatch2.getEvent_id());
								jedis.del(dispatch2.getEvent_id()+"-preTime");
								System.out.print("推送通知---"+dispatch2.getOper_name());
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			RedisPool.recycleJedisOjbect(jedis);
		}
	}

}
