package com.jdry.pms.assignWork.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.assignWork.pojo.DispatchImageEntity;
import com.jdry.pms.assignWork.pojo.DispatchPerson;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.DispatchPersonService;
import com.jdry.pms.comm.util.CommUtil;
import com.soft.service.GrapService;

@Repository
@Component
public class DirectDispatchController implements IController{

	static Logger log=Logger.getLogger(DirectDispatchController.class);
	@Resource
	CommUtil commUtil;
	
	@Resource
	AssignWorkService assignWorkService;
	
	@Resource
	GrapService grapService;
	@Resource
	DispatchPersonService dispatchPersonService;
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException
	{
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String method=null == req.getParameter("method")?"":req.getParameter("method").toString();//方法
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		if(method.equals("directDispatch"))
		{
			String in_call= null == req.getParameter("in_call")?"":req.getParameter("in_call").toString();
			//String rpt_kid= null == req.getParameter("rpt_kid")?"":req.getParameter("rpt_kid").toString();
			String rpt_name= null == req.getParameter("rpt_name")?"":req.getParameter("rpt_name").toString();
			String addres= null == req.getParameter("addres")?"":req.getParameter("addres").toString();
			String event_source= null == req.getParameter("event_source")?"":req.getParameter("event_source").toString();
			String event_type= null == req.getParameter("event_type")?"":req.getParameter("event_type").toString();
			String rpt_id= null == req.getParameter("rpt_id")?"":req.getParameter("rpt_id").toString();
			String ownerId= null == req.getParameter("ownerId")?"":req.getParameter("ownerId").toString();
			String roomId= null == req.getParameter("roomId")?"":req.getParameter("roomId").toString();
			String roomNo= null == req.getParameter("roomNum")?"":req.getParameter("roomNum").toString();
			String mtn_detail= null == req.getParameter("mtn_detail")?"":req.getParameter("mtn_detail").toString();
			String mtn_emergency= null == req.getParameter("mtn_emergency")?"0":req.getParameter("mtn_emergency").toString();
			String dispatch_status= null == req.getParameter("dispatch_status")?"":req.getParameter("dispatch_status").toString();
			String dispatch_handle_id= null == req.getParameter("dispatch_handle_id")?"":req.getParameter("dispatch_handle_id").toString();
			String dispatch_handle_name= null == req.getParameter("dispatch_handle_username")?"":req.getParameter("dispatch_handle_username").toString();
			//投诉
			String comp_status= null == req.getParameter("comp_status")?"0":req.getParameter("comp_status").toString();
			String comp_emergency= null == req.getParameter("comp_emergency")?"0":req.getParameter("comp_emergency").toString();
			String comp_detail= null == req.getParameter("comp_detail")?"":req.getParameter("comp_detail").toString();
			String img_url= null ==  req.getParameter("img_url")?"": req.getParameter("img_url").toString();
			WorkMainEntity assignWork=assignWorkService.getWorkMainById(rpt_id);
			if(assignWork==null){
			assignWork= new WorkMainEntity();
			assignWork.setOwner_id(ownerId);
			assignWork.setOwner_house(roomId);
			assignWork.setRoomNo(roomNo);
			}
			assignWork.setIn_call(in_call);
			assignWork.setAddress(addres);
			assignWork.setRpt_name(rpt_name);
		
			assignWork.setEvent_source(event_source);
			assignWork.setEvent_type(event_type);
			assignWork.setCreateby(user.getUsername());
			//assignWork.setCreateTime(new Date());
			
			assignWork.setCreateTime(new Date());
			assignWork.setEvent_state("1");//派工
			//派工表
			EventSendEntity send=new EventSendEntity();
			send.setHandle_time(new Date());
			send.setOwnHandler(dispatch_handle_id);
			String deptName=assignWorkService.getdept(dispatch_handle_id);
			String phone=assignWorkService.getHandlePhone(dispatch_handle_id);
			String dept_name=null==deptName?"":deptName;
			if(dept_name.equals(""))
			{
				send.setHandler_dept("");	
			}else{
			send.setHandler_dept(dept_name);
			}
			send.setHandler_phone(phone);
			send.setStatus("0");
			
			String processKey = "assignNewProcess";
			if(rpt_id == "" || rpt_id.length()==0){
				String businessStr = "bx";
				if(null != event_type && event_type.equals("1")){
					businessStr="ts";
				}
				rpt_id = commUtil.getBusinessId(businessStr);
				assignWork.setRpt_kid(CommUtil.getGuuid());
			}
			assignWork.setRpt_id(rpt_id);
			send.setEvent_id(rpt_id);
			if(!"".equals(img_url)){
				String[] aa = img_url.split(",");
				String modifyImgUrl = JSON.toJSONString(aa);
				assignWork.setImg_url(modifyImgUrl);
	            for(int i=0;i<aa.length;i++)
	            {   DispatchImageEntity img=new DispatchImageEntity();
	            	img.setEvent_id(rpt_id);
	            	img.setImg_url(aa[i]+"");
	            	img.setOther("");
	            	assignWorkService.saveImg(img);
	            }
			}
			assignWorkService.addEventSend(send);
			
			if(null != event_type && event_type.equals("0")){
				//故障报修
				WorkMainDispatchEntity wormDispatch = null;
				wormDispatch = assignWorkService.getWorkDispatchById(rpt_id);
				if(null == wormDispatch){
					wormDispatch = new WorkMainDispatchEntity();
					wormDispatch.setDispatch_kid(CommUtil.getGuuid());
				}
				wormDispatch.setMtn_id(rpt_id);
				wormDispatch.setMtn_detail(mtn_detail);
				assignWork.setEvent_content(mtn_detail);//事件类容
				if(mtn_emergency.equals("")){
					mtn_emergency = "0";
				}
				wormDispatch.setMtn_emergency(mtn_emergency);
				if(dispatch_status.equals("")){
					dispatch_status = "1";
				}
				wormDispatch.setDispatch_time(new Date());
				wormDispatch.setCreateby(user.getUsername());
				wormDispatch.setCreateTime(new Date());//派工时间
				wormDispatch.setDispatch_status(assignWork.getEvent_state());
				assignWork.setEvent_time(wormDispatch.getCreateTime());
				wormDispatch.setDispatch_handle_id(dispatch_handle_id);
				wormDispatch.setDispatch_handle_name(dispatch_handle_name);
				wormDispatch.setDispatch_id(commUtil.getBusinessId("pg"));
				assignWorkService.addWorkDispatch(wormDispatch);
				
				
			}else if(null != event_type && event_type.equals("1")){
				//投诉建议
				WorkComplaintEntity complaintEntity = null;
				assignWork.setEvent_time(new Date());
				complaintEntity = assignWorkService.getWorkComplById(rpt_id);
				if(null == complaintEntity){
					complaintEntity = new WorkComplaintEntity();
					complaintEntity.setComp_kid(CommUtil.getGuuid());
				}
				if(comp_emergency.equals("")){
					comp_emergency = "0";
				}
				if(comp_status.equals("")){
					comp_status = "1";
				}
				complaintEntity.setMtn_id(rpt_id);
				complaintEntity.setComp_detail(comp_detail);
				assignWork.setEvent_content(comp_detail);//事件类容
				complaintEntity.setComp_status(comp_status);
				complaintEntity.setComp_emergency(comp_emergency);
				complaintEntity.setComp_createby(user.getUsername());
				
				complaintEntity.setComp_operator_id(dispatch_handle_id);
				complaintEntity.setComp_operator(dispatch_handle_name);
				complaintEntity.setComp_emergency(comp_emergency);
				complaintEntity.setComp_createby(user.getUsername());
				complaintEntity.setComp_createTime(new Date());
				complaintEntity.setComp_status("1");
				
				complaintEntity.setComp_id(commUtil.getBusinessId("pg"));
//				dispatchEntity.setDispatch_id(commUtil.getBusinessId("pg"));
				String state = complaintEntity.getComp_status();
				assignWork.setEvent_state(state);
				String slname=null==assignWork.getCreateby()?"":assignWork.getCreateby();
				if(slname.equals("")){
					assignWork.setCreateby(user.getUsername());
				}
				
				assignWorkService.addWorkCompl(complaintEntity);
				processKey = "assignComp";
			}
			
			/**
			 * 描述：后续加入的工单和处理人的纪录操作
			 * 时间：2016年10月8日14:41:43
			 */
			DispatchPerson person=new DispatchPerson();
			person.setRpt_id(rpt_id);
			person.setHandle_id(dispatch_handle_id);
			person.setHandle_name(dispatch_handle_name);
			if(rpt_id.indexOf("TS")!=-1)
			{
				dispatch_status=comp_status;
			}
			person.setEvent_state(dispatch_status);
			person.setRemark("");
			dispatchPersonService.addDisptchPersonInfo(person);
			
			
			
			/**
			 * 开始流程
			 */
			String processId="";
			
			if(("").equals(assignWork.getProcessInstanceId())||assignWork.getProcessInstanceId()==null)
			{
			 processId = grapService.startBussniseTask(processKey);
			 if(null == processId || processId.length()<1){
				processId = "0";
			
			}
			if(null != event_type && event_type.equals("0")){
				grapService.completeBussniseTask(processId, null, null);
			 }
			if(!comp_emergency.equals("0")&&event_type.equals("1"))
			{
				Map map=new HashMap();
				map.put("flag", "2");
				grapService.completeBussniseTask(processId, user.getCname(), map,"1");
				
			}
			assignWork.setProcessInstanceId(Long.parseLong(processId));
		}else
		{
			processId=assignWork.getProcessInstanceId()+"";
			
		}
			/**
			 * 派工
			 */
			Map map=new HashMap();
			map.put("flag", "1");
			if(null != processId && processId.length()>0){
				if(!comp_emergency.equals("0")&&event_type.equals("1"))
				{
				    map=new HashMap();
					map.put("flag", "2");
					grapService.completeBussniseTask(processId, user.getCname(), map,"1");
					
				}
				if(!comp_emergency.equals("0")&&comp_emergency!="")
				{
					grapService.completeBussniseTask(processId, user.getCname(), map,"1");	
				}else{
					grapService.completeBussniseTask(processId, user.getCname(), map,"1");
				}
			}
			
		
			assignWorkService.addWorkMain(assignWork);
			send.setStatus("1");
			assignWorkService.addEventSend(send);
			PrintWriter out = resp.getWriter();
			String jsonString = JSON.toJSONString(rpt_id);
			out.println(jsonString);
			out.flush();
		}
		
		
		
	}

	@Override
	public String getUrl() 
	{
		return "/assigenwork/directDispatch";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}

}
