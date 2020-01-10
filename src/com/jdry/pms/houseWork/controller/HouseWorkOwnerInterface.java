package com.jdry.pms.houseWork.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.controller.EventMessagePushlet;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.AssignWorkServiceInterfaceService;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;
import com.jdry.pms.houseWork.service.HouseWorkOwnerInterfaceService;
import com.jdry.pms.houseWork.service.HouseworkEventService;
import com.jdry.pms.houseWork.service.HouseworkEventVisitSevice;
import com.soft.service.GrapService;

/**
 * 描述：家政服务对外接口——APP业主端
 * 
 * @author hezuping
 * @2016年5月16日14:27:10
 */
@Repository
@Component
public class HouseWorkOwnerInterface {
	@Resource
	CommUtil commUtil;
	@Resource
	HouseWorkOwnerInterfaceService houseWorkOwnerInterfaceService;
	@Resource
	AssignWorkServiceInterfaceService assignWorkServiceInterfaceService;
	@Resource
	GrapService grapService;
	//收费接口
	@Resource
	ChargeInfoService chargeInfoService;
	
	@Resource
	HouseworkEventVisitSevice houseworkEventVisitSevice;
	@Resource
	HouseworkEventService houseworkEventService;
	
	@Resource
	AssignWorkService assignWorkService;
	
	@Resource
	HouseworkEventVisitSevice hs;
	/**
	 * 业主家政服务申请
	 * 
	 * @return
	 */
	static Logger log=Logger.getLogger(HouseWorkOwnerInterface.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ownerEventSupply(String jsonSupply) {

		JSONObject suppInfo = (JSONObject) JSON.parse(jsonSupply);
		String event_from = (String) suppInfo.get("event_from");// 事件来源
		String call_phone = (String) suppInfo.get("call_phone");
		String other = (String) suppInfo.get("other");
		String otherStr="";
		 try {
			 otherStr=URLDecoder.decode(other, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
		String user_address = "";// 地址
		//String event_content = (String) suppInfo.get("event_content");
		//String pre_time = (String) suppInfo.get("pre_time");// 预约时间APP传入
		if(commUtil == null)
		 {
			commUtil = (CommUtil) SpringUtil.getObjectFromApplication("commUtil");
		  }
		String event_no = commUtil.getBusinessId("IM", "D");
		String rpt_name = "";
		String ownerId = "";
		String roomId = "";
		String status = "";// 给APP 端状态
		String message = ""; // 提示信息
		 if(assignWorkServiceInterfaceService == null)
		 {
			 assignWorkServiceInterfaceService = (AssignWorkServiceInterfaceService) SpringUtil.getObjectFromApplication("assignWorkServiceInterfaceServiceImpl");
		  }
		 if(houseWorkOwnerInterfaceService == null)
		 {
			 houseWorkOwnerInterfaceService = (HouseWorkOwnerInterfaceService) SpringUtil.getObjectFromApplication("houseWorkOwnerInterfaceServiceImpl");
		 }
		 if(assignWorkService == null)
		 {
			 assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		
		if (!call_phone.equals("")) {
			String community_name="";
			String storied_build_name="";
			String room_no="";
			List ls = assignWorkServiceInterfaceService.queryBasicInfo(call_phone);
			for (int i = 0, len = ls.size(); i < len; i++) {
				Object[] obj = (Object[]) ls.get(0);// 确认是否只有一条
				community_name = obj[1].toString();
				storied_build_name = obj[3].toString();
				roomId = obj[4].toString();
				room_no=obj[5].toString();
				rpt_name =null==obj[9]?"":obj[9].toString();// 报事人
				ownerId = obj[8].toString();
			}
			/*if(rpt_name.equals(""))
			{
				message="请先完善个人信息资料再进行此操作";
				Map map = new HashMap();
				map.put("status", 0);//失败状态
				map.put("message", message);
				String houseWorkStr = JSON.toJSONString(map);
				return houseWorkStr;
			}*/
			user_address=community_name+"-"+storied_build_name+"-"+room_no;
		} else {
			status = "0";
			message = "程序失败，请联系技术人员";
		}
		HouseworkEventEntity work = new HouseworkEventEntity();
		work.setEvent_from(event_from);
		work.setCall_phone(call_phone);
		work.setOther(otherStr);
		work.setLink_man(ownerId);
		work.setRoom_id(roomId);
		work.setUser_address(user_address);
		work.setEvent_no(event_no);
		work.setAccept_time(new Date());
		work.setEvent_state(HouseWorkTool.WITHOUTSEND);
		work.setLink_man_name(rpt_name);
		
		//写入历史表
		    EventSendEntity sendH=new EventSendEntity();
		    sendH.setEvent_id(event_no);
		    sendH.setHandle_time(new Date());
			sendH.setOwnHandler("");
			sendH.setHandler_dept("客户服务部");	
			sendH.setStatus(work.getEvent_state());
			sendH.setHandler_phone("085186317510");
			assignWorkService.addEventSend(sendH);
			 
			 
			 /**
			 * 消息推送
			 */
			try{
				EventMessagePushlet ep =new EventMessagePushlet();
				String content=""==otherStr?"":otherStr;
				ep.push(event_no, content, rpt_name, "0");
				}catch (Exception e)
				{
					log.warn("Pushlet消息推送失败：异常信息"+event_no+e.getMessage());
				}
			
		String processKey = "houseWorkProcess";
		work.setBpm_processId(Long.parseLong(startHouseWrokBpm(processKey)));
		boolean flag = houseWorkOwnerInterfaceService
				.supplyHouseWorkEventInfo(work);
		if (flag) {
			status = "1";
			message = "申请成功,您的工单号为：" + event_no;
		} else {
			status = "0";
			message = "申请未成功，有可能是网络原因造成";
		}
		Map map = new HashMap();
		map.put("status", status);
		map.put("message", message);
		map.put("data", event_no);
		String houseWorkStr = JSON.toJSONString(map);
		return houseWorkStr;
	}

	public String startHouseWrokBpm(String key) {
		/*
		 *  * 开始流程
		 */
		 if(grapService == null)
		 {
			 grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		 }
		String processId = grapService.startBussniseTask(key);
		if (null == processId || processId.length() < 1) {
			processId = "0";
		} else {
			grapService.completeBussniseTask(processId, "", null);// 多走一步流程
		}
		return processId;
	}

	/**
	 * 描述：获取业主所有家政工单纪录
	 * 
	 * @param phone
	 * @return listStr
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAllHouseWorkEventInfo(String phone) {
		String status = "";
		String message = "";
		JSONObject phoneInfo = (JSONObject) JSON.parse(phone);
		String call_phone = (String) phoneInfo.get("call_phone");// 事件来源
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List res = new ArrayList();
		
		 if(houseWorkOwnerInterfaceService == null)
		 {
			 houseWorkOwnerInterfaceService = (HouseWorkOwnerInterfaceService) SpringUtil.getObjectFromApplication("houseWorkOwnerInterfaceServiceImpl");
		 }
		 if(chargeInfoService == null)
		 {
			 chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		 }
		
		List ls = houseWorkOwnerInterfaceService.getAllHouseWorkEventInfoByPhone(call_phone);
		Map map=new HashMap();
		if (ls.size() < 1) {
			status = "1";
			message = "查询无数据";
		} else {
			for (int i = 0, len = ls.size(); i < len; i++) {
				Map mp = new HashMap();
				Object[] obj = (Object[]) ls.get(i);
				String event_no = null == obj[0] ? "" : obj[0].toString();
				mp.put("event_no", event_no);
				String event_content = null == obj[1] ? "" : obj[1].toString();
				mp.put("event_content", event_content);
				String accept_time = null == obj[2] ? "" : obj[2].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!accept_time.equals("")) {
						comp_createTime1 = sd.parse(accept_time);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status = "0";
					message = "后台异常，请联系技术人员";
					log.error(e.getMessage());
				}
				mp.put("accept_time", comp_createTime);// 开始时间
				String event_state = null == obj[3] ? "" : obj[3].toString();
				mp.put("event_state", event_state);
				String other = null == obj[4] ? "" : obj[4].toString();
				if(other.equals("待派工环节取消家政服务")||other.equals("派工环节业主取消工单")||other.equals("业主现场取消工单")||other.equals("物管拒单"))
				{
					mp.put("cancelFlag", "1");//1为取消
					
				}else
				{
					mp.put("cancelFlag", "3");
					
				}
				//支付状态
				String chargStatus = null == obj[5] ? "" : obj[5].toString();
				if(chargStatus.equals("02"))//付钱
				{
					mp.put("payFlag", "1");//1 已支付  2未支付 3 其他
				}
				if(chargStatus.equals("03"))//欠费
				{
					mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
				}else
				{
					mp.put("payFlag", "3");
				}
				res.add(mp);
			}
			status = "1";
			message = "查询成功";

		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", res);
		String jsonString = JSON.toJSONString(map);
		
		return jsonString;
	}

	/**
	 * 获取家政工单详情
	 * 
	 * @param jsonEvent_no
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getHouseWorkEventDeatail(String jsonEvent_no) {
		String jsonString="";
		String status = "";
		String message = "";
		JSONObject eventNo = (JSONObject) JSON.parse(jsonEvent_no);
		String event_no = (String) eventNo.get("event_no");// 事件来源
		 if(houseWorkOwnerInterfaceService == null)
		 {
			 houseWorkOwnerInterfaceService = (HouseWorkOwnerInterfaceService) SpringUtil.getObjectFromApplication("houseWorkOwnerInterfaceServiceImpl");
		 }
		 if(chargeInfoService == null)
		 {
			 chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		 }
		 if(houseworkEventVisitSevice==null)
		 {
			 houseworkEventVisitSevice = (HouseworkEventVisitSevice) SpringUtil.getObjectFromApplication("houseworkEventVisitSeviceImpl"); 
			 
		 }
		HouseworkEventEntity house = houseWorkOwnerInterfaceService.getHouseWorkEventByNo(event_no);
		Map<String, Object> map = new HashMap<String, Object>();
		if (house != null)// 如果报修单不为空，则执行
		{
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Map<String, String>> ls=new ArrayList<Map<String, String>>();
			List res = houseWorkOwnerInterfaceService.getHouseWorkDispatchDetailByNo(house.getEvent_no());
			if (res.size() < 1) {
				status = "1";
				message = "无相关数据";
			}else{
			 for (int i = 0, len = res.size(); i < len; i++)
			 {
				 Map<String, String> mp = new HashMap<String, String>();
			     Object[] obj = (Object[])res.get(i);
			     String event_id = null == obj[5]?"":obj[5].toString();
			     mp.put("event_no", event_id);
			     
			     String handle_time = null == obj[0]?"":obj[0].toString();
			     String comp_createTime = "";
				 Date comp_createTime1 = null;
					try {
						if (!handle_time.equals("")) {
							comp_createTime1 = sd.parse(handle_time);
							comp_createTime = sd.format(comp_createTime1);
						}
					} catch (ParseException e) {

						status="0";
						message = "后台异常，请联系技术人员";
				} 
				 mp.put("handle_time", comp_createTime);
			     String handlers = null == obj[1]?"":obj[1].toString();
			     mp.put("handlers", handlers);
			     String handler_dept = null == obj[2]?"":obj[2].toString();
			     mp.put("handler_dept", handler_dept);
			     String handler_phone = null == obj[3]?"":obj[3].toString();
			     mp.put("handler_phone", handler_phone);
			     String handle_status = null == obj[4]?"":obj[4].toString();
			     mp.put("handle_status", handle_status);
			     
			     String head_img = null == obj[8]?"":obj[8].toString();
			     mp.put("head_img", head_img);
			     //String del_status = null == obj[7]?"":obj[7].toString();//是否拒单
			    // mp.put("del_status", del_status);
			     ls.add(mp);
			 }
		 }
			
		//外层数据	
			map.put("accept_time", getSimp(house.getAccept_time()));
			if(house.getPre_time()==null)
			{
				map.put("pre_time",null);
			}else
			{
				map.put("pre_time", getSimp(house.getPre_time()));	
			}
			String other=null==house.getOther()?"":house.getOther();
			if(other.equals("待派工环节取消家政服务")||other.equals("派工环节业主取消工单")||other.equals("业主现场取消工单")||other.equals("物管拒单")||other.indexOf("取消")!=-1)
			{
				map.put("cancelFlag", "1");//
			}else
			{
				map.put("cancelFlag", "3");//
			}
			//map.put("pre_time", getSimp(house.getPre_time()));
			map.put("event_content", house.getEvent_content()+"");
			map.put("address", house.getUser_address());
			map.put("call_phone", house.getCall_phone());
			map.put("event_no", house.getEvent_no());
			map.put("event_state",house.getEvent_state());
			
			
			
			//String state=null==house.getEvent_state()?"":house.getEvent_state();
			//if(state.equals("3")){//待回访时候提供
				String id=null==house.getEvent_no()?"0":house.getEvent_no();
				List<ChargeInfoEntity> chargList=chargeInfoService.queryChargeByWorkId(id);
				  if(chargList!=null){
					 
					if(chargList.size()>0){
						String chargStatus=chargList.get(0).getState();
						if(chargStatus.equals("02"))//付钱
						{
							map.put("payFlag", "1");//1 已支付  2未支付 3 其他
						}
						if(chargStatus.equals("03"))//欠费
						{
							map.put("payFlag", "2");//1 已支付  2未支付 3 其他
						}else
						{
							map.put("payFlag", "3");
						}
					}else{
						map.put("payFlag", "3");//1 已支付  2未支付 3 其他
					}
				}else
				{
					map.put("payFlag", "3");//1 已支付  2未支付 3 其他
					
				}
			 HouseworkVisitEntity he=houseworkEventVisitSevice.queryVisitInfo(event_no);
			 if(he!=null){
			 String content=null==he.getVisist_levcontent()?"":he.getVisist_levcontent();
			 String lev=null==he.getVisit_evaluate()?"":he.getVisit_evaluate();
			 String vist_content=null==he.getVisit_content()?"":he.getVisit_content();
			    if(lev.equals("非常满意")){
			    map.put("dispatch_visit_lev", 0);//满意度
			    }else if(lev.equals("满意"))
			    {
			     map.put("dispatch_visit_lev", 1);//满意度
			    }else if(lev.equals("不满意"))
			    {
			    map.put("dispatch_visit_lev", 2);//满意度	
			    }
				map.put("dispatch_evaluate", vist_content);//标签
				map.put("dispatch_visit_record", content);//内容
			 }else
			 {
				 map.put("dispatch_visit_lev", "");//满意度	 
				 map.put("dispatch_evaluate", "");//标签
				 map.put("dispatch_visit_record", "");//内容
			 }
				  
			map.put("gustDetail", ls);
			status="1";
			message = "查询成功";
			
		}
		    Map<String, Object> jmp=new HashMap<String, Object>();
		    jmp.put("status", status);
		    jmp.put("message", message);
		    jmp.put("data", map);
		    jsonString = JSON.toJSONString(jmp);
		    return jsonString;
	}
	
	/**
	 * 家政服务业主评价
	 * @param houseworkJson
	 * @return
	 */
	public String houseWorkOwnerEvaluate(String houseworkJson)
	{
		String status="0";
		String message="失败";
		String str="";
		try {
		 str = URLDecoder.decode(houseworkJson, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			status="0";
			message="输入数据异常";
		}
		Map<String,Object> variables = null;
		JSONObject pho = (JSONObject) JSON.parse(str);
		 String event_no =(String) pho.get("event_no");
		 String visit_evaluate =(String) pho.get("visit_evaluate");//评价等级
		 String visit_record ="";//录音编号
		 String visit_content = (String) pho.get("visit_content");//回访内容
		 String visist_levcontent=(String) pho.get("visist_levcontent");//评价内容
		 String visit_name="";
		try {
			visit_name = URLDecoder.decode((String) pho.get("visit_name"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//回访人
		 if(commUtil == null)
		 {
			commUtil = (CommUtil) SpringUtil.getObjectFromApplication("commUtil");
		  }
		 String visit_no = commUtil.getBusinessId("VS","D");
		 
		 if(grapService == null)//创建上下文环境
		 {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		 }
		
		if(houseworkEventService == null)//创建上下文环境
		 {
			houseworkEventService = (HouseworkEventService) SpringUtil.getObjectFromApplication("houseworkEventServiceImpl");
		 }
		if(houseworkEventVisitSevice == null)//创建上下文环境
		 {
			houseworkEventVisitSevice = (HouseworkEventVisitSevice) SpringUtil.getObjectFromApplication("houseworkEventVisitSeviceImpl");
		 }
		 if(assignWorkService == null)
		 {
			 assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		 HouseworkEventEntity work =houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
		 work.setEvent_state(HouseWorkTool.EVENTFINSH);
		 work.setVisit_state(HouseWorkTool.VISIT_STATE);//1为已回访 0 未回访
		 work.setVisit_oper_id(visit_name);//登录当前人
		 
		 HouseworkVisitEntity vi=new HouseworkVisitEntity();
		 vi.setEvent_id(work.getId());
		 vi.setVisit_no(visit_no);
		 vi.setVisit_evaluate(visit_evaluate);
		 vi.setVisit_from("1");
		 vi.setVisit_record(visit_record);
		 vi.setVisit_content(visit_content);
		 vi.setVisit_time(new Date());
		 vi.setVisit_oper_id(visit_name);
		 vi.setVisist_levcontent(visist_levcontent);
		 boolean flag=houseworkEventVisitSevice.saveEventVisit(vi);
		 
		 EventSendEntity sendH=new EventSendEntity();
		 sendH.setEvent_id(event_no);
		 sendH.setHandle_time(new Date());
		 sendH.setOwnHandler(visit_name);
		 sendH.setHandler_dept("业主");
		 sendH.setStatus(work.getEvent_state());
		 assignWorkService.addEventSend(sendH);
		 /**
		  * 回访流程
		  * 
		  */
		 String processinstanceid = work.getBpm_processId()+"";
		 if(null != processinstanceid && processinstanceid.length()>0)
		 {
			grapService.completeHouseWorkBussniseTask(processinstanceid, visit_name, null,"5");//现场处理
		 }
		 houseworkEventService.saveHouseWorkEvent(work);
		
		 work.setEvent_state(HouseWorkTool.EVENTFILE);
		 houseworkEventService.saveHouseWorkEvent(work);
	
		 EventSendEntity sendH2=new EventSendEntity();
		 sendH2.setEvent_id(event_no);
		 sendH2.setHandle_time(new Date());
		 sendH2.setOwnHandler(visit_name);
		 sendH2.setHandler_dept("业主");
		 sendH2.setStatus(work.getEvent_state());
		 assignWorkService.addEventSend(sendH2);
		
		 String msg="";
	     if(flag)
	     {
	    	 status="1";
			 message="成功";
	     }else
	     {
	    	 status="0";
		     message="评价失败";
	     }
	       variables=new HashMap<String, Object>();
			variables.put("status", status);
			variables.put("message", message);
			variables.put("event_no", "工单："+event_no+" 评价成功");
			String jsonString = JSON.toJSONString(variables);
		  return jsonString;
	}
	
	
	
	
	
	public  String getSimp(Date time)
	{
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time1=sd.format(time);
		return time1;
		
	}
}
