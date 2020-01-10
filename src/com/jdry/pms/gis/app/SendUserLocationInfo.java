package com.jdry.pms.gis.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.RedisPool;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.gis.app.dao.SendEmpLocationInfoDao;
import com.jdry.pms.gis.app.entity.GisUserLocation;
import com.jdry.pms.msgandnotice.pojo.AppResult;

@Component
public class SendUserLocationInfo {

	@Resource
	SendEmpLocationInfoDao dao;
	static Logger log = Logger.getLogger(SendUserLocationInfo.class);

	public String insertUserLocation(String data) throws IOException {
		/**
		 * 在jedis更新每人最新位置信息
		 */
		String str = "";
		try {
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		JSONObject obj = JSON.parseObject(str);
		String username = obj.getString("username");
		String cname = obj.getString("cname");
		String xAxis = obj.getString("xAxis");
		String yAxis = obj.getString("yAxis");
		String realtime = obj.getString("realtime");
		GisUserLocation loc = new GisUserLocation();
		loc.setCname(cname);
		loc.setUsername(username);
		loc.setXAxis(xAxis);
		loc.setYAxis(yAxis);

		if (realtime != null && !realtime.isEmpty()) {
			loc.setRealtime(Long.parseLong(realtime));
		}
		String key = "gis-" + username;
		this.saveLocationToRedis(key, loc); // 启动数据保存至redis操作进程
		this.objectPersist(key); // 启动数据持久化进程
		loc = null; // 释放内存
		AppResult result = new AppResult();
		result.setStatus("1");
		result.setMessage("发送成功！");
		return JSON.toJSONString(result);
	}

	/**
	 * 将位置对象以序列化方式存入redis
	 * 
	 * @param key
	 * @param obj
	 */
	private void saveLocationToRedis(String key, GisUserLocation loc) {
		final String rowKey = key;
		final GisUserLocation rowValue = loc;

		// 开启进程进行存储操作
		new Thread(new Runnable() {
			boolean flag = true;

			@Override
			public void run() {
				Jedis jedis = null;
				while (flag) {
					try {
						jedis = RedisPool.getJedisObject();
						jedis.set(rowKey, JSON.toJSONString(rowValue));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("redis处理异常！" + e + "-----saveLocationToRedis");
					} finally {
						flag = false; // 保存完成置位结束进程
						RedisPool.recycleJedisOjbect(jedis);
					}
				}
			}
		}).start();
	}

	/**
	 * 从redis中取出最新位置信息持久化至数据库
	 * 
	 * @param key
	 */
	private void objectPersist(String key) {

		final String rowKey = key;
		// 开启进程进行存储操作
		new Thread(new Runnable() {
			boolean flag = true;

			@Override
			public void run() {
				Jedis jedis = null;
				while (flag) {
					GisUserLocation loc = null;
					try {
						jedis = RedisPool.getJedisObject();
						String locString = jedis.get(rowKey);
						loc = JSON.toJavaObject(JSON.parseObject(locString), GisUserLocation.class);
						if (loc != null) {
							if (dao == null) {
								dao = (SendEmpLocationInfoDao) SpringUtil.getObjectFromApplication("sendEmpLocationInfoDao");
							}
							dao.insertUserLocation(loc);
							loc = null;
						}
					} catch (Exception e) {
						log.error("redis处理异常！" + e.getMessage() + "-----objectPersist");
						e.printStackTrace();
					} finally {
						RedisPool.recycleJedisOjbect(jedis);
						flag = false;
					}
				}
			}
		}).start();

	}
	
	
	public String getRefreshTime(String data){
		String str = "{\"status\":\"1\",\"message\":\"获取成功\",\"data\":{\"time\":\""+150*1000+"\"}}";
		return str;
	}

}
