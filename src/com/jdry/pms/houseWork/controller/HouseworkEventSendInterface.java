package com.jdry.pms.houseWork.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.service.HouseworkEventSendService;
import com.jdry.pms.houseWork.service.HouseworkEventService;
import com.soft.service.GrapService;

/**
 * 描述：业主端对家政服务APP接口
 * @author hezuping
 *
 */
@Repository
@Component
public class HouseworkEventSendInterface 
{
	
	@Resource
	HouseworkEventSendService houseworkEventSendService;
	@Resource
	HouseworkEventService houseworkEventService;
	@Resource
	GrapService grapService;
	@Resource
	AssignWorkService assignWorkService;
	static Logger log=Logger.getLogger(HouseworkEventSendInterface.class);
	/**
	 * 现场处理业主端取消工单
	 * @param houseWorkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String houseWorkDispatchSingle(String houseWorkId)
	{
		
		 JSONObject hwrok = (JSONObject) JSON.parse(houseWorkId);
		 String event_no=(String) hwrok.get("event_no");
		 //String id=(String) hwrok.get("id");
		 String arrv_time="";
		 String status="0";
		 String message="取消失败,联系物管人员";
		 if(houseworkEventService == null)
		 {
			 houseworkEventService = (HouseworkEventService) SpringUtil.getObjectFromApplication("houseworkEventServiceImpl");
		  }
		 if(houseworkEventSendService == null)
		 {
			 houseworkEventSendService = (HouseworkEventSendService) SpringUtil.getObjectFromApplication("houseworkEventSendServiceImpl");
		  }
		 
		 if(grapService == null)
		 {
			 grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		  }
		 if(assignWorkService == null)
		 {
			 assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		 HouseworkEventEntity work =houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
		 if(work==null)
		 {
			 work=new HouseworkEventEntity();
			// work.setId(id);
		 }
		 String type=null==work?"0":work.getEvent_state();
		 /**
		  * 开始判断环节
		  */
		 if(type.equals("0"))//业主待派工环节取消
		 {
			// HouseworkEventEntity work =houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			 work.setEvent_state(HouseWorkTool.EVENTFILE);
			 work.setFinish_time(new Date());
			 work.setOther("待派工环节取消家政服务");
			
			EventSendEntity send=new EventSendEntity();
			send.setHandle_time(new Date());
			if(work.getLink_man_name()==null||work.getLink_man_name().equals(""))
			{
				send.setOwnHandler("业主");
			}else{
			send.setOwnHandler(work.getLink_man_name());
			}
			send.setHandler_phone(work.getCall_phone());
			send.setStatus(work.getEvent_state());
			send.setEvent_id(event_no);
			send.setDel_status("1");//删除状态 1表示删除，其他正常
			
			 /**
				 * 驱动流程
				 */
				 Map variables = new HashMap<String,Object>();
				 variables.put("flag", "1");
				 String processinstanceid = work.getBpm_processId()+"";
				 if(null != processinstanceid && processinstanceid.length()>0)
				 {
					grapService.completeHouseWorkBussniseTask(processinstanceid, work.getLink_man(), variables,"1");
					/**
					 * 推到结束
					 */
					grapService.completeHouseWorkBussniseTask(processinstanceid,work.getLink_man(), null,"1");
					grapService.completeHouseWorkBussniseTask(processinstanceid, work.getLink_man(), null,null);
				 }
				 assignWorkService.addEventSend(send);
				 boolean flag=houseworkEventService.saveHouseWorkEvent(work);
				 
				  status="1";
				  message="取消成功";
			 
		 }
		 else if(type.equals("1")){//业主现场取消
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
		
		 work.setEvent_state(HouseWorkTool.EVENTFILE);
		 work.setOther("业主现场取消工单");
		 HouseWorkEventSendEntity send;
		if(!work.getId().equals(""))
		 {
		  send= houseworkEventSendService.queryHouseWorkSendInfo(work.getId());
		  if(send==null)
		  {
			  send=new HouseWorkEventSendEntity();
		  }
		 }else
		 {
		  send=new HouseWorkEventSendEntity();
		 }
		 send.setDelete_time(new Date());//删除时间
		 send.setHandle_content("业主消单");//消单原因或内容
		 if(arrv_time.equals("")){
		 send.setArrv_time(new Date());
		 }else
		 {
			try {
				send.setArrv_time(sd.parse(arrv_time));
			} catch (ParseException e) 
			{
				 status="0";
				 message="后台异常，联系技术人员";
				 log.error(message+e.getMessage());
			} 
		 }
		 //历史表
		  EventSendEntity sen=new EventSendEntity();
		  sen.setHandle_time(send.getDelete_time());
			if(work.getLink_man_name()==null||work.getLink_man_name().equals(""))
			{
				sen.setOwnHandler("业主");
			}else{
				sen.setOwnHandler(work.getLink_man_name());
			}
			sen.setHandler_phone(work.getCall_phone());
			sen.setStatus(work.getEvent_state());
			sen.setEvent_id(event_no);
			sen.setDel_status("1");//删除状态 1表示删除，其他正常
		    assignWorkService.addEventSend(sen);
		 
		 /**
		  * 开始流程
		  */
		 Map variables = new HashMap<String,Object>();
		 variables.put("flag", "1");
		 String processinstanceid = work.getBpm_processId()+"";
		 if(null != processinstanceid && processinstanceid.length()>0)
		 {
			grapService.completeHouseWorkBussniseTask(processinstanceid,"", variables,"3");//现场处理
			grapService.completeHouseWorkBussniseTask(processinstanceid, "", null, null);//模拟消单
		 }
		 
		 houseworkEventService.saveHouseWorkEvent(work);
		 boolean flag=houseworkEventService.saveHouseWorkSend(send);
	     
		 String msg="";
	     if(flag)
	     {
	    	 status="1";
			 message="操作成功";
	     }else
	     {
	    	 status="0";
			 message="操作失败";
	     }
	 }
		    Map map=new HashMap();
	        map.put("status", status);
			map.put("message",message);
			map.put("event_no", event_no);
			String jsonString = JSON.toJSONString(map);
		    return jsonString;	
	}
	/**
	 * 描述：获取家政服务项目
	 * @return
	 */
	public String getHouseWorkProperty()
	{
		
		
		
		return "";
	}
	
}
