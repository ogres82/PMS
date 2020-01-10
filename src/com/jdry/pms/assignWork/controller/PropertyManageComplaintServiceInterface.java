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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.PropertyManageComplaintService;
import com.jdry.pms.comm.util.SpringUtil;
import com.soft.service.GrapService;

/**
 * 描述：	物业派工物管端（投诉、建议）
 * @author hezuping
 * @time:2016-04-20:08:59
 */
@Repository
@Component
public class PropertyManageComplaintServiceInterface
{

	@Resource
	PropertyManageComplaintService propertyManageComplaintService;
	 
	 @Resource
		AssignWorkService assignWorkService;
	 @Resource
		GrapService grapService;
	/**
	 * 获取派工单历史
	 * @param dispatch_id
	 * @return
	 */
	static Logger log=Logger.getLogger(PropertyManageComplaintServiceInterface.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getDispatchHistory(String jsonData)
	{
		String status="";
		String message="";
		if(propertyManageComplaintService == null)//创建上下文环境
		 {
			propertyManageComplaintService = (PropertyManageComplaintService) SpringUtil.getObjectFromApplication("propertyManageComplaintServiceImpl");
		 }
		WorkComplaintEntity work = JSON.parseObject(jsonData, WorkComplaintEntity.class);
		String operator_id=null==work.getComp_operator_id()?"":work.getComp_operator_id();
		Map map = new HashMap();
		if(operator_id.equals(""))
		{
			status="2";	
			message="请传入用户编号";	
			map.put("status", status);
			map.put("message", message);
			
		}else{
		List ls= propertyManageComplaintService.getDispatchHistory(work.getComp_operator_id());
		List res=new ArrayList();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(ls.size()<1)
		{
			status="1";	
			message="查询无数据";
		}else
		{
			for(int i=0,len=ls.size();i<len;i++)
			{
				Map mp=new HashMap();
				Object[] obj = (Object[])ls.get(i);
				String addres = null == obj[0]?"":obj[0].toString();
				mp.put("addres", addres);
				String event_content = null == obj[1]?"":obj[1].toString();
				mp.put("event_content", event_content);
				String createTime = null == obj[2]?"":obj[2].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!createTime.equals("")) {
						comp_createTime1 = sd.parse(createTime);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status="2";
					message = "后台异常，请联系技术人员";
					
				}
				mp.put("createTime", comp_createTime);
				String event_state = null == obj[3]?"":obj[3].toString();
				mp.put("event_state", event_state);
				String code_detail_name = null == obj[4]?"":obj[4].toString();
				mp.put("code_detail_name", code_detail_name);
				
				String rpt_id = null == obj[6]?"":obj[6].toString();
				mp.put("rpt_id", rpt_id);
				res.add(mp);
				
			}
			status="1";	
			message="查询成功";	
		}
	  
		map.put("status", status);
		map.put("message", message);
		map.put("data", res);
		}
		String jsonString = JSON.toJSONString(map);
		return jsonString;	
	}
	
	/**
	 * 获取派工单详情
	 * @param dispatch_id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getDispatchComplantDetail(String dispatch_id)
	{
		String status="";
		String message="";
		WorkComplaintEntity tempWork = JSON.parseObject(dispatch_id, WorkComplaintEntity.class);
		if(propertyManageComplaintService == null)//创建上下文环境
		 {
			propertyManageComplaintService = (PropertyManageComplaintService) SpringUtil.getObjectFromApplication("propertyManageComplaintServiceImpl");
		 }
		Map map = new HashMap();
		List res=propertyManageComplaintService.getDisPatchComplaintDetail(tempWork.getMtn_id());	
		if(res.size()<1)
		{
			status="1";	
			message="没有查询到相关数据";
		}else
		{
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] obj = (Object[])res.get(0);
		String comp_detail = null == obj[0]?"":obj[0].toString();
		map.put("comp_detail", comp_detail);
		
		String comp_createTime = null == obj[1]?"":obj[1].toString();
		String createTime = "";
		Date comp_createTime1 = null;
		try {
			if (!createTime.equals("")) {
				comp_createTime1 = sd.parse(comp_createTime);
				createTime = sd.format(comp_createTime1);
			}
		} catch (ParseException e) {

			status="2";
			message = "后台异常，请联系技术人员";
			log.error("时间转换异常"+e.getMessage());
		}
		
		
		map.put("comp_createTime", createTime);
		
		String comp_status = null == obj[2]?"":obj[2].toString();
		map.put("comp_status", comp_status);
		
		String comp_reply = null == obj[3]?"":obj[3].toString();
		map.put("comp_reply", comp_reply);
		
		String rpt_name = null == obj[4]?"":obj[4].toString();
		map.put("rpt_name", rpt_name);
		String in_call = null == obj[5]?"":obj[5].toString();
		map.put("in_call", in_call);
		String address = null == obj[6]?"":obj[6].toString();
		map.put("addres", address);
		String comp_result = null == obj[9]?"":obj[9].toString();
		map.put("comp_result", comp_result);
		String comp_finish_time = null == obj[8]?"":obj[8].toString();
		String finish_time = "";
		Date finish_time1 = null;
		try {
			if (!comp_finish_time.equals("")) {
				finish_time1 = sd.parse(comp_finish_time);
				finish_time = sd.format(finish_time1);
				map.put("comp_finish_time", finish_time);
			}
			else
			{
				map.put("comp_finish_time", "");
			}
		} catch (ParseException e) {

			status="2";
			message = "后台异常，请联系技术人员";
		}
		
		status="1";	
		message="查询成功";
		}
		Map jsonMap=new HashMap();
		jsonMap.put("status", status);
		jsonMap.put("message", message);
		jsonMap.put("data", map);
		
		String jsonString = JSON.toJSONString(jsonMap);
		return jsonString;
	}
	/**
	 * 客户端获取未读事件驱动流程
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPreHandleEvent(String Data)
	{
		
		String status="";
		String message="";
		String str ="";
		try {
			 str = URLDecoder.decode(Data, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			 status="0";
			 message="客户端字符串存在问题，请检查";
			 log.error(message+e.getMessage());
		} 
		VWorkCompEntity vw = JSON.parseObject(str, VWorkCompEntity.class);
		if(vw.getMtn_id().equals("")||vw.getMtn_id()==null)
		{
			
			 status="0";
			 message="编号传错";
			
		}else{
		
		String state="2";
		if(assignWorkService == null)//创建上下文环境
		 {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		 }
		WorkMainEntity mainEntity = assignWorkService.getWorkMainById(vw.getMtn_id());
		mainEntity.setEvent_state(state);
		//VWorkCompEntity vw=propertyManageComplaintService.getComplaintHandle(mrid);
		WorkComplaintEntity work=assignWorkService.getWorkComplById(vw.getMtn_id());
		work.setComp_status(state);
		assignWorkService.addWorkMain(mainEntity);
		//assignWorkService.addEventSend("");
		assignWorkService.addWorkCompl(work);
		String processinstanceid=mainEntity.getProcessInstanceId()+"";
		if(grapService == null)//创建上下文环境
		 {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		 }
		grapService.completeBussniseTask(processinstanceid, work.getComp_createby(),null,state);
		
		status="1";
		message="成功处理";
		Map mapjson=new HashMap();
		mapjson.put("status", status);
		mapjson.put("message", message);
		mapjson.put("mtn_id", vw.getMtn_id());
		String jsonString = JSON.toJSONString(mapjson);
		return jsonString;
		}
		return "clinet error";
	}
	
	/**
	 * 物管投诉处理
	 * @param mtn_id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getComplaintHandle(String Json)
	{
		String status="";
		String message="";
		String str ="";
		try {
			 str = URLDecoder.decode(Json, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			 status="0";
			 message="客户端字符串存在问题，请检查";
		}  
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		if(propertyManageComplaintService == null)//创建上下文环境
		 {
			propertyManageComplaintService = (PropertyManageComplaintService) SpringUtil.getObjectFromApplication("propertyManageComplaintServiceImpl");
		 }
		
		if(assignWorkService == null)//创建上下文环境
		 {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		 }
		
		
		WorkComplaintEntity tempWork = JSON.parseObject(str, WorkComplaintEntity.class);
		
		VWorkCompEntity vw=propertyManageComplaintService.getComplaintHandle(tempWork.getMtn_id());
		Map map=new HashMap();
		if(vw==null)
		{
			status="0";
			message="未到处理环节或已处理";
		}else
		{
			String mtnid=null==vw.getMtn_id()?"":vw.getMtn_id();
			String comp_operator_id=null==vw.getComp_operator_id()?"":vw.getComp_operator_id();//是否显示中文
			
			String comp_emergency=null==vw.getComp_emergency()?"":vw.getComp_emergency();
			String Comp_status=null==vw.getComp_status()?"":vw.getComp_status();
			String Comp_status_name= null == vw.getComp_status_name()?"":vw.getComp_status_name().toString();
           // String comp_reply=null==vw.getComp_reply()?"":vw.getComp_reply().toString();
			WorkMainEntity mainEntity = assignWorkService.getWorkMainById(tempWork.getMtn_id());
			String state = "";
			if(null == mainEntity){
				mainEntity = new WorkMainEntity();
			}
			WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(tempWork.getMtn_id());
			
			if(null == complaintEntity){
				complaintEntity = new WorkComplaintEntity();
			}
			
			if(Comp_status.equals("2"))
			{
			complaintEntity.setComp_operator_id(comp_operator_id);
//			complaintEntity.setComp_result("");
//			complaintEntity.setComp_process("");
//			complaintEntity.setComp_solve("");
//			complaintEntity.setComp_degree("");
//			complaintEntity.setComp_detail("");
			complaintEntity.setComp_emergency(comp_emergency);
//			complaintEntity.setComp_createby("系统管理员");
			complaintEntity.setComp_createTime(new Date());
			complaintEntity.setComp_status("3");
			complaintEntity.setComp_finish_time(new Date());
			complaintEntity.setComp_result(tempWork.getComp_reply());//客户端返回的回复信息作为处理结果
//			complaintEntity.setComp_reply(tempWork.getComp_reply());
			state = complaintEntity.getComp_status();
			
			
			//更新数据库
			mainEntity.setEvent_state(state);
			WorkComplaintEntity work=assignWorkService.getWorkComplById(vw.getMtn_id());
			work.setComp_status(state);
			assignWorkService.addWorkMain(mainEntity);
			//assignWorkService.addEventSend("");
			assignWorkService.addWorkCompl(work);
			}
			assignWorkService.addWorkCompl(complaintEntity);
			if(grapService == null)//创建上下文环境
			 {
				grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
			 }
			String processinstanceid = mainEntity.getProcessInstanceId()+"";
			grapService.completeBussniseTask(processinstanceid, complaintEntity.getComp_createby(), null,state);
			
			//添加历史状态20177-1-11
			EventSendEntity send=new EventSendEntity();
			send.setHandle_time(new Date());
			send.setOwnHandler(vw.getComp_operator_id());
			send.setEvent_id(mtnid);
			String deptName=assignWorkService.getdept(vw.getComp_operator_id());
			String dept_name=null==deptName?"":deptName;
			if(dept_name.equals(""))
			{
				send.setHandler_dept("系统");	
			}else{
			send.setHandler_dept(dept_name);
			}
			String phone=assignWorkService.getHandlePhone(vw.getComp_operator_id());
			send.setHandler_phone(phone);
			send.setStatus(state);
			assignWorkService.addEventSend(send);
			status="1";
			message="处理成功";
			map.put("rpt_id", mtnid);
			map.put("comp_createTime", vw.getCreateTime());
			map.put("comp_status", Comp_status);
			map.put("Comp_status_name", Comp_status_name);
		}
		/**
		 * 组装json
		 */
		Map mapjson=new HashMap();
		mapjson.put("status", status);
		mapjson.put("message", message);
		mapjson.put("data", map);
		String jsonString = JSON.toJSONString(mapjson);
		return jsonString;
	}
	/**
	 * 投诉销单
	 * @param comp_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCompSinglelimin(String mtn_id)
	{
		

		String status="";
		String message="";
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		VWorkCompEntity vw=propertyManageComplaintService.getComplaintHandle(mtn_id);
		Map map=new HashMap();
		if(vw==null)
		{
			status="1";
			message="未到处理环节或已处理";
		}else
		{
			String mtnid=null==vw.getMtn_id()?"":vw.getMtn_id();
			String comp_operator_id=null==vw.getRpt_name()?"":vw.getRpt_name();//是否显示中文
			
			String comp_emergency=null==vw.getComp_emergency()?"":vw.getComp_emergency();
			String Comp_status=null==vw.getComp_status()?"":vw.getComp_status();
			String Comp_status_name= null == vw.getComp_status_name()?"":vw.getComp_status_name().toString();
			//String comp_result= null == vw.get?"":arg0.getParameter("comp_result").toString();
			//String comp_process= null == vw.getarg0.getParameter("comp_process")?"":arg0.getParameter("comp_process").toString();
			//String comp_solve= null == arg0.getParameter("comp_solve")?"":arg0.getParameter("comp_solve").toString();
			//String comp_degree= null == arg0.getParameter("comp_degree")?"":arg0.getParameter("comp_degree").toString();
			

			WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
			String state = "";
			if(null == mainEntity){
				mainEntity = new WorkMainEntity();
			}
			WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(mtn_id);
			
			if(null == complaintEntity){
				complaintEntity = new WorkComplaintEntity();
			}
			
			if(Comp_status.equals("1"))
			{
			//消单
			complaintEntity.setComp_finish_time(new Date());
			complaintEntity.setComp_status("3");
			state = complaintEntity.getComp_status();
			mainEntity.setOrder_state("正常");
			}
			assignWorkService.addWorkCompl(complaintEntity);
			
			mainEntity.setFinishTime(new Date());
			assignWorkService.addWorkMain(mainEntity);
			//销单，完成任务
			assignWorkService.addWorkWorkState(comp_operator_id, 0);
			
			String processinstanceid = mainEntity.getProcessInstanceId()+"";
			grapService.completeBussniseTask(processinstanceid, user.getCname(), null,state);
			status="1";
			message="处理成功";
			map.put("rpt_id", mtnid);
			map.put("comp_createTime", vw.getCreateTime());
			map.put("comp_status", Comp_status);
			map.put("Comp_status_name", Comp_status_name);
			
		}
		/**
		 * 组装json
		 */
		Map mapjson=new HashMap();
		mapjson.put("status", status);
		mapjson.put("message", message);
		mapjson.put("data", map);
		String jsonString = JSON.toJSONString(mapjson);
		return jsonString;
	
	}
	
}
