package com.jdry.pms.assignWork.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkVisitEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.soft.service.GrapService;

@Repository
@Component
public class VisitWorkController implements IController{

	@Resource
	AssignWorkService assignWorkService;
	
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Resource
	GrapService grapService;
	static Logger log=Logger.getLogger(VisitWorkController.class);
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html;charset=utf-8");
		arg0.setCharacterEncoding("utf-8");
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		String method=null == arg0.getParameter("method")?"":arg0.getParameter("method").toString();//方法
		String assignId=null == arg0.getParameter("assignId")?"":arg0.getParameter("assignId").toString();//业务编号
		if(null !=method && method.equals("visitlist")){
			//返回查询结果列表
			int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
			  // 每页行数
			  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			  if (currentPage != 0) {// 获取页数
			   currentPage = currentPage / showCount;
			  }
			  currentPage += 1;
			  String search=java.net.URLDecoder.decode(arg0.getParameter("search") , "UTF-8");
			  String rpt_name = java.net.URLDecoder.decode(arg0.getParameter("rpt_name") , "UTF-8");
			  String businessFromSearch = arg0.getParameter("businessFromSearch") ;
			  String eventTypeSearch = arg0.getParameter("eventTypeSearch") ;
			  String CommNameSearch = java.net.URLDecoder.decode(arg0.getParameter("CommNameSearch") , "UTF-8");
			  String dateSearch = arg0.getParameter("dateSearch");
			  if(search.equals("undefined"))
			    {
			    	search="";
			    }
			  Map<String, Object> parameter = new HashMap();
			  parameter.put("search", search);
			  parameter.put("businessFromSearch", businessFromSearch);
			  parameter.put("eventTypeSearch", eventTypeSearch);
			  parameter.put("CommNameSearch", CommNameSearch);
			  parameter.put("dateSearch", dateSearch);
			Page<VWorkVisitEntity> page =new Page<VWorkVisitEntity>(showCount, currentPage);
			try {
				assignWorkService.getWorkVisit(page, parameter, null);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			List<VWorkVisitEntity> result=(List<VWorkVisitEntity>) page.getEntities();
			String jsonString = JSON.toJSONString(result);
			JSONArray jsonArr = JSON.parseArray(jsonString); 
		    JSONObject jsonObject=new JSONObject();
		    jsonObject.put("rows", jsonArr);//JSONArray
		    jsonObject.put("total",page.getEntityCount());//总记录数
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(JSON.toJSONString(jsonObject));
			out.flush();
		}else if(null !=method && method.equals("viewVisit")){

			List list = assignWorkService.getVisitWorkById(assignId);
			if(null != list){
				String jsonString = JSON.toJSONString(list.get(0),SerializerFeature.WriteMapNullValue);
				//jsonString = jsonString.substring(1, jsonString.length()-1);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
			}
		}else if(null !=method && method.equals("visitSave")){
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			String dispatch_visit_lev= null == arg0.getParameter("dispatch_visit_lev")?"":arg0.getParameter("dispatch_visit_lev").toString();
			String dispatch_visit_record= null == arg0.getParameter("dispatch_visit_record")?"":arg0.getParameter("dispatch_visit_record").toString();
			String dispatch_visit_recording= null == arg0.getParameter("dispatch_visit_recording")?"":arg0.getParameter("dispatch_visit_recording").toString();
			String rpt_id= null == arg0.getParameter("rpt_id")?"":arg0.getParameter("rpt_id").toString();
			String event_type= null == arg0.getParameter("event_type")?"0":arg0.getParameter("event_type").toString();
			String type= null == arg0.getParameter("type")?"0":arg0.getParameter("type").toString();
			
			/**
			 * 驱动流程图
			 */
			WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);
			Map<String,Object> variables = null;
			String state = "";
			
			if(mainEntity == null){
				mainEntity = new WorkMainEntity();
			}
			
			if(null != event_type && event_type.equals("0")){
				//故障报修
				WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
				if(null == dispatchEntity){
					dispatchEntity = new WorkMainDispatchEntity();
				}
				
				dispatchEntity.setDispatch_visit_lev(dispatch_visit_lev);
				dispatchEntity.setDispatch_visit_record(dispatch_visit_record);
				dispatchEntity.setDispatch_visit_recording(dispatch_visit_recording);
				dispatchEntity.setDispatch_visit_time(new Date());
				if(null != type && type.equals("1")){
					dispatchEntity.setDispatch_status("4");
					
				}else if(null != type && type.equals("2")){//根据最新要求回访以后直接归档 这一步代表以前的待归档操作
					dispatchEntity.setDispatch_status("5");
					//dispatchEntity.setDispatch_finish_time(new Date());
				}
				assignWorkService.addWorkDispatch(dispatchEntity);//回访后第一步推到状态4
				state = dispatchEntity.getDispatch_status();
				//历史纪录
				EventSendEntity send=new EventSendEntity();
				send.setHandle_time(dispatchEntity.getDispatch_finish_time());
				send.setOwnHandler(user.getUsername());
				send.setEvent_id(dispatchEntity.getMtn_id());
				String deptName=assignWorkService.getdept(user.getUsername());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					send.setHandler_dept("系统");	
				}else{
				send.setHandler_dept(dept_name);
				}
				String phone=assignWorkService.getHandlePhone(user.getUsername());
				send.setHandler_phone(phone);
				send.setStatus(state);
				
				assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
				assignWorkService.addEventSend(send);
				
				
				//多走一步满足APP
				dispatchEntity.setDispatch_status("5");
				assignWorkService.addWorkDispatch(dispatchEntity);//回访后第一步推到状态5
				send.setStatus(dispatchEntity.getDispatch_status());
				assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
				assignWorkService.addEventSend(send);
				
				
				
				
				
			}else if(null != event_type && event_type.equals("1")){//咨询建议
				
				WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(rpt_id);
				if(null == complaintEntity){
					complaintEntity = new WorkComplaintEntity();
				}
				
				complaintEntity.setComp_visit_lev(dispatch_visit_lev);
				complaintEntity.setComp_visit_record(dispatch_visit_record);
				complaintEntity.setComp_visit_recording(dispatch_visit_recording);
				if(null != type && type.equals("1")){
					complaintEntity.setComp_status("4");
				}else if(null != type && type.equals("2")){//为APP多走一步
					complaintEntity.setComp_status("5");
				}
				state = complaintEntity.getComp_status();
				assignWorkService.addWorkCompl(complaintEntity);
				
				complaintEntity.setComp_status("5");
				
				//历史纪录
				EventSendEntity send=new EventSendEntity();
				send.setHandle_time(new Date());
				send.setOwnHandler(user.getUsername());
				send.setEvent_id(rpt_id);
				String deptName=assignWorkService.getdept(user.getUsername());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					send.setHandler_dept("系统");	
				}else{
				send.setHandler_dept(dept_name);
				}
				String phone=assignWorkService.getHandlePhone(user.getUsername());
				send.setHandler_phone(phone);
				send.setStatus(state);
				assignWorkService.addEventSend(send);
				assignWorkService.updateWorkMainState(mainEntity.getRpt_id(),complaintEntity.getComp_status());//更新主表状态
				assignWorkService.addWorkCompl(complaintEntity);
				
			}
			
			String processinstanceid = mainEntity.getProcessInstanceId()+"";
			if(null != processinstanceid && processinstanceid.length()>0){
				grapService.completeBussniseTask(processinstanceid, user.getCname(), variables,state);
				grapService.completeBussniseTask(processinstanceid, user.getCname(), variables,"5");//推到归档
			}
			
			//多走一步
			//历史纪录
			EventSendEntity send=new EventSendEntity();
			send.setHandle_time(new Date());
			send.setOwnHandler(user.getUsername());
			send.setEvent_id(rpt_id);
			String deptName=assignWorkService.getdept(user.getUsername());
			String dept_name=null==deptName?"":deptName;
			if(dept_name.equals(""))
			{
				send.setHandler_dept("系统");	
			}else{
			send.setHandler_dept(dept_name);
			}
			String phone=assignWorkService.getHandlePhone(user.getUsername());
			send.setHandler_phone(phone);
			send.setStatus("5");
			assignWorkService.addEventSend(send);
			
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.print(JSON.toJSONString("succese"));
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/visitnwork/visitlist";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
