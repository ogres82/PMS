package com.jdry.pms.assignWork.controller;

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
import nl.justobjects.pushlet.util.Log;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jdry.pms.assignWork.pojo.DispatchImageEntity;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.AssignWorkServiceInterfaceService;
import com.jdry.pms.assignWork.service.ImpairedRepairService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.NoticePushUtil;
import com.jdry.pms.comm.util.SpringUtil;
import com.soft.service.GrapService;

/**
 * 物业派工业主端（投诉、建议）对app接口
 * 
 * @author hezuping
 * 
 */
@Repository
@Component
public class AssignWorkServiceInterface {

	@Resource
	AssignWorkService assignWorkService;
	@Resource
	GrapService grapService;

	@Resource
	AssignWorkServiceInterfaceService assignWorkServiceInterfaceService;
	@Resource
	ImpairedRepairService impairedRepairService;
	@Resource
	CommUtil commUtil;
	static Logger log=Logger.getLogger(AssignWorkServiceInterface.class);
	/**
	 * 申报投诉、建议、报修
	 * 
	 * @param dataJson
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public String getComplaintAndSuggestion(String dataJson) {
		String status="";
		String message="";
		String str="";
		try {
			str = URLDecoder.decode(dataJson, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("报障报修业主申请异常："+e.getMessage());
		}
		WorkMainEntity work = JSON.parseObject(str, WorkMainEntity.class);
		WorkMainDispatchEntity dis = JSON.parseObject(str,WorkMainDispatchEntity.class);
		String in_call = work.getIn_call();// 手机号
		String rpt_name = "";
		String ownerId="";
		String roomId="";
		String address1="";
		String address2="";
		String address3="";
		String addres=null==work.getAddress()? "" :work.getAddress();
		/**
		 * 初始化上下文环境
		 */
		if(assignWorkServiceInterfaceService == null){
				assignWorkServiceInterfaceService = (AssignWorkServiceInterfaceService) SpringUtil.getObjectFromApplication("assignWorkServiceInterfaceServiceImpl");
		}
		if(commUtil == null){
			commUtil = (CommUtil) SpringUtil.getObjectFromApplication("commUtil");
		}
		if(assignWorkService == null){
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		}
		if(grapService == null){
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		}
		if (!in_call.equals("")) {
			List ls = assignWorkServiceInterfaceService.queryBasicInfo(in_call);
			for (int i = 0,len=ls.size();i<len; i++) {
				Object[] obj = (Object[]) ls.get(0);// 确认是否只有一条
				roomId=obj[4].toString();
				rpt_name =null==obj[9]?"":obj[9].toString();// 报事人
				ownerId=obj[8].toString();
				
				address1=obj[1].toString();
				address2=obj[3].toString();
				address3=obj[5].toString();
				addres=address1+"-"+address2+"-"+address3;
			}
		}else{
			 status="0";
			 message="程序失败，请联系技术人员";	
			 log.error("后台异常："+message);
		}
		String rpt_kid = null == work.getRpt_kid() ? "" : work.getRpt_kid();
		String event_source = null == work.getEvent_source() ? "" : work.getEvent_source();// 事件来源
		String event_type = null == work.getEvent_type() ? "" : work.getEvent_type();// 事件类别
		String rpt_id = null == work.getRpt_id() ? "" : work.getRpt_id();
		String mtn_detail = "";// 投诉内容
		
		String mtn_emergency = null == dis.getDispatch_status() ? "0" : dis.getDispatch_status();// 紧急程度
		String dispatch_status = null == dis.getDispatch_status() ? "0" : dis.getDispatch_status();

		// 投诉
		String comp_status = "0";
		String comp_emergency = "0";
		String comp_detail = "";
		WorkMainEntity assignWork = new WorkMainEntity();
		if (event_type.equals("1"))// 投诉内容
		{

			comp_detail = dis.getMtn_detail();
			assignWork.setEvent_content(comp_detail);
		
		} else {
			mtn_detail = dis.getMtn_detail();// 保修内容
			assignWork.setEvent_content(mtn_detail);
			
		}
		assignWork.setEvent_state("0");
		assignWork.setIn_call(in_call);
		assignWork.setAddress(addres);
		assignWork.setRpt_name(rpt_name);
		assignWork.setRpt_kid(rpt_kid);
		assignWork.setEvent_source(event_source);
		assignWork.setEvent_type(event_type);
		assignWork.setOwner_id(ownerId);
		assignWork.setOwner_house(roomId);
		assignWork.setRoomNo(address3);
		
		//if (work.getEvent_time() == null) {
			assignWork.setCreateTime(new Date());//投诉时间后台默认生成
		//} else {
		//	assignWork.setCreateTime(work.getEvent_time());
		//}
		assignWork.setRpt_id(rpt_id);
		String processKey = "assignNewProcess";
		if (rpt_id == null || rpt_id.length() == 0) {
			String businessStr = "bx";
			if (null != event_type && event_type.equals("1")) {
				businessStr = "ts";
			}
			
			rpt_id = commUtil.getBusinessId(businessStr);
			assignWork.setRpt_kid(CommUtil.getGuuid());
		}
		assignWork.setRpt_id(rpt_id);
		if (null != event_type && event_type.equals("0")) {
			// 故障报修
			WorkMainDispatchEntity wormDispatch = null;
			wormDispatch = assignWorkService.getWorkDispatchById(rpt_id);
			if (null == wormDispatch) {
				wormDispatch = new WorkMainDispatchEntity();
				wormDispatch.setDispatch_kid(CommUtil.getGuuid());
			}
			wormDispatch.setMtn_id(rpt_id);
			wormDispatch.setMtn_detail(mtn_detail);
			if (mtn_emergency.equals("")) {
				mtn_emergency = "0";
			}
			wormDispatch.setMtn_emergency(mtn_emergency);
			if (dispatch_status.equals("")) {
				dispatch_status = "0";
			}
			//加图片
			String imgurl=null==work.getImg_url()?"":work.getImg_url();
			/*
			 * 徐磊修改
			 */
			if(!"".equals(imgurl)){
				assignWork.setImg_url(imgurl);
				JSONArray aa=JSON.parseArray(imgurl);
	            for(int i=0;i<aa.size();i++)
	            {   DispatchImageEntity img=new DispatchImageEntity();
	            	img.setEvent_id(rpt_id);
	            	img.setImg_url(aa.get(i)+"");
	            	img.setOther("");
	            	assignWorkService.saveImg(img);
	            }
			}
			status="1";
			message="报障报修成功";
			wormDispatch.setDispatch_status(dispatch_status);
			assignWorkService.addWorkDispatch(wormDispatch);
		} else if (null != event_type && event_type.equals("1")) {
			// 投诉建议
			WorkComplaintEntity complaintEntity = null;
			complaintEntity = assignWorkService.getWorkComplById(rpt_id);
			if (null == complaintEntity) {
				complaintEntity = new WorkComplaintEntity();
				complaintEntity.setComp_kid(CommUtil.getGuuid());
			}
			if (comp_emergency.equals("")) {
				comp_emergency = "0";
			}
			if (comp_status.equals("")) {
				comp_status = "0";
			}
			complaintEntity.setMtn_id(rpt_id);
			complaintEntity.setComp_detail(comp_detail);
			complaintEntity.setComp_status(comp_status);
			complaintEntity.setComp_emergency(comp_emergency);
			assignWorkService.addWorkCompl(complaintEntity);
			processKey = "assignComp";
			status="1";
			message="投诉或建议成功";
		}
		//业主信息替换成物业
		EventSendEntity send=new EventSendEntity();
		send.setHandle_time(assignWork.getCreateTime());
		send.setOwnHandler("");//
		send.setEvent_id(rpt_id);
		send.setHandler_dept("客户服务部");	
		send.setHandler_phone("085186317510");//物业电话
		send.setStatus("0");
		assignWorkService.addEventSend(send);
		/**
		 * 开始流程
		 */
		String processId = grapService.startBussniseTask(processKey);
		if (null == processId || processId.length() < 1) {
			processId = "0";
		}
		
		if(null != event_type && event_type.equals("0")){
			grapService.completeBussniseTask(processId, null, null);
		 }
		if(!comp_emergency.equals("0")&&event_type.equals("1"))
		{
			Map map=new HashMap();
			map.put("flag", "2");
			grapService.completeBussniseTask(processId, "system", map,"1");
			
		}
		
		
		/**
		 * 开始流程完成
		 */
		assignWork.setProcessInstanceId(Long.parseLong(processId));
		assignWorkService.addWorkMain(assignWork);
		// 传输JSON
		Map map = new HashMap();
		map.put("status", status);
		map.put("message", message);
		map.put("rpt_id", rpt_id);
		String ComplaintStr = JSON.toJSONString(map);
		/**
		 * 消息推送
		 */
		try{
			EventMessagePushlet ep =new EventMessagePushlet();
			String content=""==comp_detail+mtn_detail?"":comp_detail+mtn_detail;
			ep.push(rpt_id, content, rpt_name, "0");
			//推送通知APP
			if(null != event_type && event_type.equals("0")){
				NoticePushUtil.pushNoticeByAlias("006", "有一个新维修工单，请前往查看。", "02");
			}
		}catch (Exception e){
			log.warn("Pushlet消息推送失败：异常信息"+rpt_id+e.getMessage());
		}
		return ComplaintStr;
	}

	/**
	 * 获取历史投诉信息
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	public String getComplaintHistory(String phones) {
		String status="";
		String message="";
		String jsonString = "";
		if(assignWorkServiceInterfaceService == null)
		 {
				assignWorkServiceInterfaceService = (AssignWorkServiceInterfaceService) SpringUtil.getObjectFromApplication("assignWorkServiceInterfaceServiceImpl");
		  }
		if(commUtil == null)
		 {
			commUtil = (CommUtil) SpringUtil.getObjectFromApplication("commUtil");
		  }
		if(assignWorkService == null)
		 {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		if(grapService == null)
		 {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		  }
		WorkMainEntity work = JSON.parseObject(phones, WorkMainEntity.class);
		if (work.getIn_call().equals("")) {
			status="0";
			message = "请输入正确的手机号";
		} else {
			List ls = assignWorkServiceInterfaceService.queryComplaintHistoryByPhone(work.getIn_call());
			List list = new ArrayList();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0,len=ls.size(); i<len;i++) {
				Map map = new HashMap();
				Object[] obj = (Object[]) ls.get(i);
				String rpt_id = null == obj[0] ? "" : obj[0].toString();
				map.put("rpt_id", rpt_id);
				String createTime = null == obj[1] ? "" : obj[1].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!createTime.equals("")) {
						comp_createTime1 = sd.parse(createTime);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status="0";
					message = "后台异常，请联系技术人员";
					log.error("时间转换异常："+e.getMessage());
				}
			
				String comp_status = null == obj[2] ? "" : obj[2].toString();
				String crateTime = null == obj[4] ? "" : obj[4].toString();
				
				
				
				String strTime = "";
				Date start_time = null;
				try {
					if (!crateTime.equals("")) {
						start_time = sd.parse(crateTime);
						strTime = sd.format(start_time);
					}
				} catch (ParseException e) {

					status="0";
					message = "后台异常，请联系技术人员";
					log.error("时间转换异常："+e.getMessage());
				}
				
				
				map.put("comp_createTime", strTime);//创建时间
				if(comp_status.equals("0")){
					map.put("handle_time", strTime);//处理时间
				}else
				{
					map.put("handle_time", comp_createTime);//处理时间
				}
				map.put("comp_status", comp_status);
				String code_detail_name = null == obj[3] ? "" : obj[3].toString();
				map.put("comp_statusName", code_detail_name);
				//咨询内容 2017年1月5添加
				String comp_detail = null == obj[5] ? "" : obj[5].toString();
				map.put("comp_detail", comp_detail);
				//客户回复
				String comp_reply = null == obj[6] ? "" : obj[6].toString();
				map.put("comp_reply", comp_reply);
				list.add(map);
			}

			if (ls.size() < 1) {
				status="1";
				message = "没有相关记录";
			}else
			{
				status="1";
				message = "查询成功";
			}
			Map map = new HashMap();
			map.put("status", status);
			map.put("message", message);
			map.put("data", list);
			jsonString = JSON.toJSONString(map);
		}
		return jsonString;
	}

	/**
	 * 通过事件单号查询投诉详情
	 * 
	 * @param rpt_ids
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	public String getComplaintDetailInfo(String rpt_ids) {
		String status="";
		String message="";
		List inner_Ls=new ArrayList();
		if(assignWorkServiceInterfaceService == null)
		 {
				assignWorkServiceInterfaceService = (AssignWorkServiceInterfaceService) SpringUtil.getObjectFromApplication("assignWorkServiceInterfaceServiceImpl");
		  }
		if(commUtil == null)
		 {
			commUtil = (CommUtil) SpringUtil.getObjectFromApplication("commUtil");
		  }
		if(assignWorkService == null)
		 {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		  }
		if(grapService == null)
		 {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		  }
		if (rpt_ids == null || rpt_ids.equals("")) {
			status="0";
			message = "事件编号不能为空";
		} else {

			WorkComplaintEntity comp = JSON.parseObject(rpt_ids,WorkComplaintEntity.class);
			if(assignWorkServiceInterfaceService == null){
			assignWorkServiceInterfaceService = (AssignWorkServiceInterfaceService) SpringUtil.getObjectFromApplication("AssignWorkServiceInterfaceServiceImpl");
			}
			
			if(impairedRepairService == null)
			{
			 impairedRepairService = (ImpairedRepairService) SpringUtil.getObjectFromApplication("impairedRepairServiceImpl");
			}
			List ls = assignWorkServiceInterfaceService.queryComplaintDetailByRptId(comp.getMtn_id());
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//咨询建议流程状态
			List res=impairedRepairService.getDispatchStepByEventId(comp.getMtn_id());
			 if(res.size()<1)
			    {
			    	 status="1";
					 message="无相关数据";
			    }else
			    {
			    for(int i=0,len=res.size();i<len;i++)
				{
			    	Map mp = new HashMap();
			    	Object[] obj = (Object[])res.get(i);
					String handle_time = null == obj[0]?"":obj[0].toString();
					String comp_createTime = "";
					Date comp_createTime1 = null;
					try {
						if (!handle_time.equals("")) {
							comp_createTime1 = sd.parse(handle_time);
							comp_createTime = sd.format(comp_createTime1);
						}
					} catch (ParseException e)
					{
						status="0";
						message = "后台异常，请联系技术人员";
						Log.error(message+e.getMessage());
					}
					mp.put("handle_time", comp_createTime);
					String cname = null == obj[1]?"":obj[1].toString();
					String handlers = null == obj[7]?"":obj[7].toString();
					if(cname.equals(""))
					{
						mp.put("cname", "业主"+handlers);
					}else{
					mp.put("cname", cname);
					}
					String handler_dept = null == obj[2]?"":obj[2].toString();
					mp.put("handler_dept", handler_dept);
					String handler_phone = null == obj[3]?"":obj[3].toString();
					mp.put("handler_phone", handler_phone);
					String statu = null == obj[4]?"":obj[4].toString();
					mp.put("comp_status", statu);
					String mtn_type = null == obj[6]?"":obj[6].toString();
					mp.put("mtn_type", mtn_type);
					String head_img = null == obj[8]?"":obj[8].toString();
					mp.put("head_img", head_img);
					
					inner_Ls.add(mp);
				 }
			    status="1";
			    message="成功";
			    }
			
			Map map = new HashMap();
			if(ls.size()>0){
				
				Object[] obj = (Object[]) ls.get(0);
				String comp_detail = null == obj[0] ? "" : obj[0].toString();
				map.put("comp_detail", comp_detail);
				String createTime = null == obj[1] ? "" : obj[1].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!createTime.equals("")) {
						comp_createTime1 = sd.parse(createTime);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status="0";
					message = "后台异常，请联系技术人员";
					log.error("时间转换异常："+e.getMessage()+message);
				}
				map.put("comp_createTime", comp_createTime);
				String comp_status = null == obj[2] ? "" : obj[2].toString();
				map.put("comp_status", comp_status);
				String comp_result = null == obj[8] ? "" : obj[8].toString();
				
				String comp_visit_record = null == obj[3] ? "" : obj[3].toString();
				if(comp_result.equals(comp_visit_record)&&(!comp_result.equals("")))
				{
					map.put("comp_reply", comp_result);
				}else{
				   map.put("comp_reply", comp_visit_record+" "+comp_result);
				}
				
				String comp_operator = null == obj[4] ? "" : obj[4].toString();
				map.put("comp_operator", comp_operator);
				String Handle_time = null == obj[5] ? "" : obj[5].toString();
				map.put("handle_time", Handle_time);
				
				String phone = null == obj[6] ? "" : obj[6].toString();
				map.put("phone", phone);
				
				String dept_name = null == obj[7] ? "" : obj[7].toString();
				map.put("dept_name", dept_name);
			}
			
			if (ls.size() < 1) {
				status="0";
				message = "查询无相关记录";
			}else
			{
				status="1";
				message = "查询成功";
				
			}
			map.put("data", inner_Ls);
			Map mapJson = new HashMap();
			mapJson.put("status", status);
			mapJson.put("message", message);
			mapJson.put("data", map);
			
          return JSON.toJSONString(mapJson);
		}
		// 传输JSON
		return "";

	}
	
}
