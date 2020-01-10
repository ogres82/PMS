package com.jdry.pms.houseWork.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nl.justobjects.pushlet.util.Log;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;
import com.jdry.pms.houseWork.service.HouseworkEventSendService;
import com.jdry.pms.houseWork.service.HouseworkEventService;
import com.jdry.pms.houseWork.service.HouseworkPropertManageInterfaceService;
import com.soft.service.GrapService;

/**
 * 描述：家政服务物管端接口——APP
 * @author hezuping
 * 2016年5月25日20:54:34
 */
@Repository
@Component
public class HouseworkPropertManageInterface {
	
	@Resource
	HouseworkPropertManageInterfaceService houseworkPropertManageInterfaceService;
	
	@Resource
	HouseworkEventService houseworkEventService;
	
	@Resource
	GrapService grapService;
	
	@Resource
	AssignWorkService assignWorkService;
	
	//收费接口
		@Resource
		ChargeInfoService chargeInfoService;
	@Resource
	HouseworkEventSendService houseworkEventSendService;
	static Logger log=Logger.getLogger(HouseworkPropertManageInterface.class);
	/**
	 * 家政服务列表
	 * 通过指派人获取
	 * @return
	 */
	@SuppressWarnings("unused")
	public String quaryHouseKeepingList(String handleJson )
	{
		
		 if(houseworkPropertManageInterfaceService == null)
		 {
			 houseworkPropertManageInterfaceService = (HouseworkPropertManageInterfaceService) SpringUtil.getObjectFromApplication("houseworkPropertManageInterfaceServiceImpl");
		  }
		 if(chargeInfoService == null)//创建上下文环境
		 {
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		 }
		String status = "";
		String message = "";
		JSONObject hander = (JSONObject) JSON.parse(handleJson);
		String oper_id =null== (String) hander.get("oper_id")?"":(String) hander.get("oper_id");// 处理人
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();	
		 //测试读取实现
		// long startTime = System.currentTimeMillis();
		List<?> ls=houseworkPropertManageInterfaceService.quaryHouseKeepingList(oper_id);
		if(ls.size()<0)
		{
			 status = "0";
			 message = "查询无数据";
		}else
		{
			for (int i = 0, len = ls.size(); i < len; i++)
			{
				Map<String, String> mp = new HashMap<String, String>();
				Object[] obj = (Object[]) ls.get(i);
				String event_no = null == obj[0] ? "" : obj[0].toString();
				mp.put("event_no", event_no);
				
				String link_man_name = null == obj[1] ? "" : obj[1].toString();
				mp.put("link_man_name", link_man_name);
				
				String pre_time = null == obj[2] ? "" : obj[2].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!pre_time.equals("")) {
						comp_createTime1 = sd.parse(pre_time);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status = "0";
					message = "后台异常，请联系技术人员";
				}
				mp.put("pre_time", comp_createTime);
				String user_address = null == obj[3] ? "" : obj[3].toString();
				mp.put("user_address", user_address);
				
				String event_state = null == obj[4] ? "" : obj[4].toString();
				
				String chargStatus = null == obj[8] ? "" : obj[8].toString();
				if(chargStatus.equals("02"))//付钱
				{
					mp.put("payFlag", "1");//1 已支付  2未支付 3 其他
				}
				else if(chargStatus.equals("03"))//欠费
				{
					mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
				}else if(chargStatus.equals("")||chargStatus==null)
				{
					mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
					
				}
				mp.put("event_state", event_state);
				String other = null == obj[7] ? "" : obj[7].toString();
				if(other.equals("待派工环节取消家政服务")||other.equals("派工环节业主取消工单")||other.equals("物管拒单"))
				{
					mp.put("cancelFlag", "1");
					
				}else
				{
					mp.put("cancelFlag", "");	
				}
				res.add(mp);
			}
			status = "1";
			message = "查询成功";
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", res);
		 //long endTime = System.currentTimeMillis();
		 //System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}
	@SuppressWarnings("unchecked")
	public String getHouseKeepingDeatail(String eventIdJson)
	{
		
		 if(houseworkPropertManageInterfaceService == null)
		 {
			 houseworkPropertManageInterfaceService = (HouseworkPropertManageInterfaceService) SpringUtil.getObjectFromApplication("houseworkPropertManageInterfaceServiceImpl");
		  }
		 if(houseworkEventService == null)
		 {
			 houseworkEventService = (HouseworkEventService) SpringUtil.getObjectFromApplication("houseworkEventServiceImpl");
		  }
		String status = "";
		String message = "";
		JSONObject eventObj = (JSONObject) JSON.parse(eventIdJson);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String event_no =null== (String) eventObj.get("event_no")?"":(String) eventObj.get("event_no");// 事件编号
		Object[] obj=(Object[]) houseworkPropertManageInterfaceService.getHouseKeepingDeatail(event_no);
		//查询出ID
		HouseworkEventEntity hw=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
		HouseworkVisitEntity visit=houseworkPropertManageInterfaceService.quaryVistInfoById(hw.getId());
		Map<String, String> mp = new HashMap<String, String>();
		Map map = new HashMap();
		if(visit!=null)
		{
			mp.put("visit_evaluate", visit.getVisit_evaluate());
			mp.put("visit_content", visit.getVisit_content());
			mp.put("visist_levcontent",visit.getVisist_levcontent());
			mp.put("visit_name", "业主");
		}
		if(obj==null)
		{
			 status = "1";
			 message = "查询无数据";
		}else
		{
			
			mp.put("event_no", obj[0].toString());
			if(obj[1]==null)
			{
				mp.put("event_content", "");	
			}else
			{
				mp.put("event_content", obj[1].toString());	
			}
			
			if(obj[2]==null)
			{
				mp.put("pre_time","");	
			}else
			{
				String time=obj[2].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!time.equals("")) {
						comp_createTime1 = sd.parse(time);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status = "0";
					message = "后台异常，请联系技术人员";
					log.error(e.getMessage());
				}	
				
			mp.put("pre_time", comp_createTime);
			
			}
			String address=null==obj[3]?"":obj[3].toString();
			mp.put("user_address", address);
			
			String link_man_name=null==obj[4]?"":obj[4].toString();
			mp.put("link_man_name", link_man_name);
			mp.put("call_phone", obj[5].toString());
			String fee=null==obj[6]?"" :obj[6].toString();
			mp.put("houseworkfee", fee);
			String event_state=null==obj[7]?"":obj[7].toString();
			mp.put("event_state", event_state);
			
			try{
				if(event_state.equals("3")){//待回访时候提供
					List<ChargeInfoEntity> chargList = null;
					 if(chargeInfoService == null)
					 {
						 chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
					 }
					chargList=chargeInfoService.queryChargeByWorkId(event_no);
				   
					int i1=0;
					int j=0;
					
					for(ChargeInfoEntity charg:chargList)
					{
						String chargStatus=charg.getState();
						if(chargStatus.equals("02"))
						{
						 	i1=++i1;
						}
					}
					if(i1>0){
					if(i1==chargList.size())
					{
						mp.put("payFlag", "1");//1 已支付  2未支付 3 其他
					}else
					{
						mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
					}
					}else
					{
						mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
					}
				  }
				 }catch (Exception e)
				    {
						Log.info("异常："+e.getMessage());
					}
				finally
				{
					String other = null == obj[4] ? "" : obj[4].toString();
					if(other.equals("待派工环节取消家政服务")||other.equals("派工环节业主取消工单")||other.equals("物管拒单"))
					{
						mp.put("cancelFlag", "1");
						
					}else
					{
						mp.put("cancelFlag", "");
					}	
				}
			 status = "1";
			 message = "查询成功";
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", mp);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}
	
	@SuppressWarnings("unchecked")
	public String houseWorkCancel(String dataJson)//物管拒单
	{
		
		 if(houseworkPropertManageInterfaceService == null)
		 {
			 houseworkPropertManageInterfaceService = (HouseworkPropertManageInterfaceService) SpringUtil.getObjectFromApplication("houseworkPropertManageInterfaceServiceImpl");
		  }
		 
		 if(grapService == null)
		 {
			 grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		  }
		 
		 if(houseworkEventService == null)
		 {
			 houseworkEventService = (HouseworkEventService) SpringUtil.getObjectFromApplication("houseworkEventServiceImpl");
		  }
		 if(houseworkEventSendService == null)
		 {
			 houseworkEventSendService = (HouseworkEventSendService) SpringUtil.getObjectFromApplication("houseworkEventSendServiceImpl");
		  }
		 if(assignWorkService == null)
		 {
			 assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		 String status = "";
		 String message = "";
		 JSONObject eventObj = (JSONObject) JSON.parse(dataJson);
		 String event_no = eventObj.get("event_no") == null ? "" :  (String)eventObj.get("event_no");
		 String handler_id = eventObj.get("handler_id") == null ? "" :  (String)eventObj.get("handler_id");
		
			HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			work.setEvent_state(HouseWorkTool.REFUSE_STATE);
			work.setOther("物管拒单");
			
		    HouseWorkEventSendEntity send= houseworkEventSendService.queryHouseWorkSendInfoByEventNo(event_no);
			send.setDelete_time(new Date());
			send.setArrv_time(new Date());
			//流程纪录
				EventSendEntity sendH=new EventSendEntity();
				sendH.setHandle_time(send.getDelete_time());
				sendH.setOwnHandler(send.getOper_id());
				String deptName=assignWorkService.getdept(send.getOper_id());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					sendH.setHandler_dept("系统");	
				}else{
				sendH.setHandler_dept(dept_name);
				}
				sendH.setStatus(work.getEvent_state());
				sendH.setEvent_id(event_no); 
				send.setHandle_content("拒单");
				
			 /**
			  * 开始流程
			  */
			 Map variables = new HashMap<String,Object>();
			 variables.put("flag", "3");//拒单
			 String processinstanceid = work.getBpm_processId()+"";
			 if(null != processinstanceid && processinstanceid.length()>0)
			 {
				 grapService.completeHouseWorkBussniseTask(processinstanceid, handler_id, variables, "3"); 
				 grapService.completeHouseWorkBussniseTask(processinstanceid, handler_id, null, null); 
			 }
			 
			 assignWorkService.addEventSend(sendH);
			 houseworkEventService.saveHouseWorkEvent(work);
			 boolean flag=houseworkEventService.saveHouseWorkSend(send);
			 status="1";
			 message="取消成功";
			 String msg="";
			 Map map=new HashMap();
		     if(flag)
		     {
		    	 msg="工单："+event_no+"已经拒单";
		     }else
		     {
		    	 msg="faild";
		     }
		     map.put("status", status);
		     map.put("message", message);
		     map.put("msg", msg);
			 String jsonString = JSON.toJSONString(map);
			 return jsonString;
	}
	/**
	 * 现场支付
	 * @param keepingPayJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String gethouseKeepingPay(String keepingPayJson)
	{
		 String status = "";
		 String message = "";
		 if(houseworkEventService == null)
		 {
			 houseworkEventService = (HouseworkEventService) SpringUtil.getObjectFromApplication("houseworkEventServiceImpl");
		  }//
		 if(houseworkEventSendService == null)
		 {
			 houseworkEventSendService = (HouseworkEventSendService) SpringUtil.getObjectFromApplication("houseworkEventSendServiceImpl");
		  }
		 if(houseworkPropertManageInterfaceService == null)
		 {
			 
			 houseworkPropertManageInterfaceService = (HouseworkPropertManageInterfaceService) SpringUtil.getObjectFromApplication("houseworkPropertManageInterfaceServiceImpl");
		  }
		 if(assignWorkService == null)
		 {
			 assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		 if(grapService == null)
		 {
			 grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		  }
		 if(chargeInfoService == null)//初始化上下文环境
		 {
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		  }
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 //String id = req.getParameter("id") == null ? "" : req.getParameter("id");
		 JSONObject eventObj = (JSONObject) JSON.parse(keepingPayJson);
		 String event_no = eventObj.get("event_no") == null ? "" :  (String)eventObj.get("event_no");
		 String handler_id=eventObj.get("handler_id") == null ? "" :  (String)eventObj.get("handler_id");;//处理人
		 String houseKeepPay=eventObj.get("houseKeepPay") == null ? "" :  (String)eventObj.get("houseKeepPay");;//处理人
		
		 HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
		 if(work==null)
		 {
			 work=new HouseworkEventEntity();
			// work.setId(id);
		 }
		 work.setEvent_state(HouseWorkTool.EVENTHANDLESTATE);
		 
				 //req.getParameter("send_id") == null ? "" : req.getParameter("send_id");
		 HouseWorkEventSendEntity send= houseworkEventSendService.queryHouseWorkSendInfoByEventNo(event_no);
		 EventSendEntity sendH=new EventSendEntity();
		 
		 String arrv_time ="";
		 if(arrv_time.equals("")){
		 send.setArrv_time(new Date());
		 }else
		 {
			try {
				send.setArrv_time(sd.parse(arrv_time));
			} catch (ParseException e) 
			{
				 status="0";
				 message="异常";
			} 
		 }
		 if(houseKeepPay.equals(""))
		 {
			 send.setHouseKeepingPay(0.00);	 
		 }else
		 {
			 send.setHouseKeepingPay(Double.parseDouble(houseKeepPay));
			
		 }
		 //收费接口
		 try{
				
				Map map=new HashMap<String,Object>();
				map.put("room_id",work.getRoom_id());
				String roomNo=houseworkPropertManageInterfaceService.getRoomNoById(work.getRoom_id());
				map.put("room_no", roomNo);
				map.put("owner_id", work.getLink_man());
				map.put("owner_name", work.getLink_man_name());
				map.put("begin_time", getSimp(new Date()));
				map.put("end_time",  getSimp(new Date()));
				map.put("oper_emp_id", send.getOper_id());
				if(("0").equals(houseKeepPay))
				{
					
				}
				else
				{
					map.put("work_id", work.getEvent_no());
					map.put("charge_type_no", "0004");
					map.put("charge_type_name", "家政服务费");
					map.put("receive_amount", houseKeepPay);//材料费
				   boolean flag=chargeInfoService.addCharge(map);
				   if(flag)
				   {
					 status="1";
					 message="成功";
				   }
				}
				
				}catch(Exception e)
				{
					 status="0";
					 message="系统异常，请联系管理员";
				}finally
				{
					
					 //状态写入历史纪录
				    sendH.setEvent_id(event_no);
				    sendH.setHandle_time(send.getArrv_time());
					sendH.setOwnHandler(send.getOper_id());
					String phone=assignWorkService.getHandlePhone(send.getOper_id());
					sendH.setHandler_phone(phone);
					String deptName=assignWorkService.getdept(send.getOper_id());
					String dept_name=null==deptName?"":deptName;
					if(dept_name.equals(""))
					{
						sendH.setHandler_dept("系统");	
					}else{
					sendH.setHandler_dept(dept_name);
					}
					sendH.setStatus(work.getEvent_state());
					
				}
				
		 
		 
		
			//send.setEvent_id(event_no);
		 /**
		  * 开始流程
		  */
		 Map variables = new HashMap<String,Object>();
		 variables.put("flag", "2");
		 String processinstanceid = work.getBpm_processId()+"";
		 if(null != processinstanceid && processinstanceid.length()>0)
		 {
			grapService.completeHouseWorkBussniseTask(processinstanceid,handler_id, variables,"3");//现场处理
			//grapService.completeHouseWorkBussniseTask(processinstanceid, "", null, "4");//模拟消单
		 }
		 
		 assignWorkService.addEventSend(sendH);
		 houseworkEventService.saveHouseWorkEvent(work);
		 boolean flag=houseworkEventService.saveHouseWorkSend(send);
		 
		 String msg="";
		 Map map=new HashMap();
	     if(flag)
	     {
	    	 status="1";
			 message="工单："+event_no+"已处理";
	     }else
	     {
	    	 msg="工单："+event_no+"处理失败";
	     }
	     map.put("status", status);
	     map.put("message", message);
	     map.put("msg", event_no);
		 String jsonString = JSON.toJSONString(map);
		 return jsonString;
	}
	
	/**
	 * 处理完成，消单
	 * @param cancelJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String houseKeepingCancel(String cancelJson)
	{
		
		
		 if(houseworkEventService == null)
		 {
			 houseworkEventService = (HouseworkEventService) SpringUtil.getObjectFromApplication("houseworkEventServiceImpl");
		  }
		 if(houseworkEventSendService == null)
		 {
			 houseworkEventSendService = (HouseworkEventSendService) SpringUtil.getObjectFromApplication("houseworkEventSendServiceImpl");
		  }
		 
		 if(assignWorkService == null)
		 {
			 assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		 if(grapService == null)
		 {
			 grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		  }
		String status = "";
		String message = "";
		JSONObject eventObj = (JSONObject) JSON.parse(cancelJson);
		String event_no = eventObj.get("event_no") == null ? "" :  (String)eventObj.get("event_no");
		String handle_id = eventObj.get("handler_id") == null ? "" :  (String)eventObj.get("handler_id");
		HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
		work.setEvent_state(HouseWorkTool.DEL_STATE);
	    HouseWorkEventSendEntity send= houseworkEventSendService.queryHouseWorkSendInfoByEventNo(event_no);
	    send.setDelete_time(new Date());
		//流程纪录
		EventSendEntity sendH=new EventSendEntity();
		sendH.setEvent_id(event_no);
		sendH.setHandle_time(send.getDelete_time());
		sendH.setOwnHandler(send.getOper_id());
		String deptName=null;
		if(!handle_id.equals(""))
		{
			deptName=assignWorkService.getdept(handle_id);
		}else
		{
			deptName=assignWorkService.getdept(send.getOper_id());
		}
		String phone=assignWorkService.getHandlePhone(send.getOper_id());
		sendH.setHandler_phone(phone);
		String dept_name=null==deptName?"":deptName;
		if(dept_name.equals(""))
		{
		sendH.setHandler_dept("系统");	
		}else{
		sendH.setHandler_dept(dept_name);
		}
		sendH.setStatus(work.getEvent_state());
		//send.setEvent_id(event_no);
		 
		String processinstanceid = work.getBpm_processId()+"";
		 if(null != processinstanceid && processinstanceid.length()>0)
		 {
			 grapService.completeHouseWorkBussniseTask(processinstanceid, "", null, "4"); 
		 }
		
		 assignWorkService.addEventSend(sendH);
		 houseworkEventService.saveHouseWorkEvent(work);
		 boolean flag=houseworkEventService.saveHouseWorkSend(send);
	     
		 String msg="";
	     if(flag)
	     {
	    	  status = "1";
			  message="工单："+event_no+",服务完成";
			  
	     }else
	     {   status="0";
	    	 message="faild";
	     }
	     Map map=new HashMap();
	     map.put("status", status);
	     map.put("message", message);
	     map.put("event_no", event_no);
		 String jsonString = JSON.toJSONString(map);
		 return jsonString;
	}
	/**
	 * 时间转换
	 * @param time
	 * @return
	 */
	public  String getSimp(Date time)
	{
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String time1=sd.format(time);
		return time1;
		
	}
}
