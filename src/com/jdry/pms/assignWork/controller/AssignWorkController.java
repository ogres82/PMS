package com.jdry.pms.assignWork.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.pojo.DispatchImageEntity;
import com.jdry.pms.assignWork.pojo.DispatchPerson;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.pojo.VWorkDispatchEntity;
import com.jdry.pms.assignWork.pojo.VWorkMainEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.DispatchPersonService;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.comm.service.BusinessService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.ImgCompress;
import com.jdry.pms.comm.util.NoticePushUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
import com.soft.service.GrapService;

@Repository
@Component
public class AssignWorkController implements IController{
	
	@Resource
	AssignWorkService assignWorkService;
	@Resource
	ChargeInfoService chargeInfoService;
    @Resource
    DispatchPersonService dispatchPersonService;
    
    @Resource 
	CommUtil util;
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Resource
	CommUtil commUtil;
	
	@Resource
	DirectoryService directoryService;
	
	@Resource
	BusinessService businessService;
	
	@Resource
	GrapService grapService;
	static Logger log=Logger.getLogger(AssignWorkController.class);
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html;charset=utf-8");
		arg0.setCharacterEncoding("utf-8");
		
		String method=null == arg0.getParameter("method")?"":arg0.getParameter("method").toString();//方法
		String assignId=null == arg0.getParameter("assignId")?"":arg0.getParameter("assignId").toString();//业务编号
		arg0.setAttribute("method", method);
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		if(method.equals("listWork")){
			//返回查询结果列表

			 int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
			  // 每页行数
			  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			  String search=java.net.URLDecoder.decode(arg0.getParameter("search") , "UTF-8");
			  String businessFromSearch = arg0.getParameter("businessFromSearch") == null ? "" : arg0.getParameter("businessFromSearch");
			  String eventTypeSearch = arg0.getParameter("eventTypeSearch") == null ? "" : arg0.getParameter("eventTypeSearch");
			  if(search.equals("undefined"))
			    {
			    	search="";
			    }
			    Map<String, Object> parameter = new HashMap();
			    parameter.put("search", search);
			    parameter.put("businessFromSearch", businessFromSearch);
			    parameter.put("eventTypeSearch", eventTypeSearch);
			  if (currentPage != 0) {// 获取页数
			   currentPage = currentPage / showCount;
			  }
			  currentPage += 1;
			Page<VWorkMainEntity> page =new Page<VWorkMainEntity>(showCount, currentPage);
			try {
				assignWorkService.getWorkMain(page, parameter, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<VWorkMainEntity> result=(List<VWorkMainEntity>) page.getEntities();
			String jsonString = JSON.toJSONString(result);
			JSONArray jsonArr = JSON.parseArray(jsonString); 
		    JSONObject jsonObject=new JSONObject();
		    jsonObject.put("rows", jsonArr);//JSONArray
		    jsonObject.put("total",page.getEntityCount());//总记录数
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			/**try{
			EventMessagePushlet ep =new EventMessagePushlet();
			ep.push("333333", "33", "huanglin", "2");
			}catch (Exception e)
			{
			}**/
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(JSON.toJSONString(jsonObject));
			out.flush();
			//arg0.getRequestDispatcher("/assignwork/AssignWorkList.jsp").forward(arg0,arg1);
		
		}else if(null !=method && method.length()>0){
			
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			if(method.equals("addAssign")){
				//主表
				WorkMainEntity assignWork = new WorkMainEntity(); 
				assignWork.setCreateby(user.getUsername());
				assignWork.setCreateTime(new Date());
				assignWork.setEvent_type("0");
				assignWork.setEvent_source("0");
				String jsonString = JSON.toJSONString(assignWork);
				//派工单明细
				WorkMainDispatchEntity wormDispatch = new WorkMainDispatchEntity();
				wormDispatch.setMtn_emergency("0");
				wormDispatch.setDispatch_status("0");
				String jsonDispatchString = JSON.toJSONString(assignWork);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				jsonString = jsonString.substring(0, jsonDispatchString.length()-1)+",\"mtn_emergency\":\"0\",\"dispatch_status\":\"0\",\"comp_emergency\":\"0\",\"comp_status\":\"0\"}";
				out.println(jsonString);
//				out.println(jsonDispatchString);
				out.flush();
				arg0.setAttribute("assignWork", assignWork);
				//arg0.getRequestDispatcher("/assignwork/AssignWorkAdd.jsp").forward(arg0,arg1);
			}else if(method.equals("updateAssign") || method.equals("viewAssign")){
				
				arg1.setContentType("application/json;charset=utf-8");
				WorkMainEntity assignWork = new WorkMainEntity();
				assignWork = assignWorkService.getWorkMainById(assignId);
				String jsonString = JSON.toJSONString(assignWork);
				
				String rpt_idStr=assignId.substring(0,2);
				String str="";
				//派工单明细
				WorkMainDispatchEntity wormDispatch = new WorkMainDispatchEntity();
				wormDispatch = assignWorkService.getWorkDispatchById(assignId);
				if(null == wormDispatch){
					wormDispatch = new WorkMainDispatchEntity();
				}
				
				//投诉明细
				WorkComplaintEntity complaintEntity = null;
				complaintEntity = assignWorkService.getWorkComplById(assignId);
				if(null == complaintEntity){
					complaintEntity = new WorkComplaintEntity();
				}
				if(rpt_idStr.equals("BX"))
				{
					 String jsonString2 = JSON.toJSONString(wormDispatch);
					 str=jsonString2.replace("{", ",");
				}else if(rpt_idStr.equals("TS"))
				{
				 String jsonString3 = JSON.toJSONString(complaintEntity);
				 str=jsonString3.replace("{", ",");
				}
				jsonString = jsonString.substring(0,jsonString.length()-1)+str;
				/*jsonString = jsonString.substring(0,jsonString.length()-1)+",\"mtn_emergency\":\""+mtn_emergency+"\"," +
						"\"dispatch_status\":\""+dispatch_status+"\",\"mtn_detail\":\""+mtn_detail+"\"," +
						"\"comp_emergency\":\""+comp_emergency+"\",\"comp_status\":\""+comp_status+"\"," +
						"\"comp_detail\":\""+comp_detail+"\",\"comp_reply\":\""+comp_reply+"\"}";*///废除前人代码
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equals("save")){
				String in_call= null == arg0.getParameter("in_call")?"":arg0.getParameter("in_call").toString();
				String rpt_kid= null == arg0.getParameter("rpt_kid")?"":arg0.getParameter("rpt_kid").toString();
				String rpt_name= null == arg0.getParameter("rpt_name")?"":arg0.getParameter("rpt_name").toString();
				String addres= null == arg0.getParameter("addres")?"":arg0.getParameter("addres").toString();
				String event_source= null == arg0.getParameter("event_source")?"":arg0.getParameter("event_source").toString();
				String event_type= null == arg0.getParameter("event_type")?"":arg0.getParameter("event_type").toString();
				String rpt_id= null == arg0.getParameter("rpt_id")?"":arg0.getParameter("rpt_id").toString();
				
				String ownerId= null == arg0.getParameter("ownerId")?"":arg0.getParameter("ownerId").toString();
				String roomId= null == arg0.getParameter("roomId")?"":arg0.getParameter("roomId").toString();
				String roomNo= null == arg0.getParameter("roomNum")?"":arg0.getParameter("roomNum").toString();
				
				String mtn_detail= null == arg0.getParameter("mtn_detail")?"":arg0.getParameter("mtn_detail").toString();
				String mtn_emergency= null == arg0.getParameter("mtn_emergency")?"0":arg0.getParameter("mtn_emergency").toString();
				String dispatch_status= null == arg0.getParameter("dispatch_status")?"0":arg0.getParameter("dispatch_status").toString();
				
				//投诉
				String comp_status= null == arg0.getParameter("comp_status")?"0":arg0.getParameter("comp_status").toString();
				String comp_emergency= null == arg0.getParameter("comp_emergency")?"0":arg0.getParameter("comp_emergency").toString();
				String comp_detail= null == arg0.getParameter("comp_detail")?"":arg0.getParameter("comp_detail").toString();
				String img_url= null == arg0.getParameter("img_url")?"":arg0.getParameter("img_url").toString();
				
				WorkMainEntity assignWork = new WorkMainEntity();
				assignWork.setIn_call(in_call);
				assignWork.setAddress(addres);
				assignWork.setRpt_name(rpt_name);
				assignWork.setRpt_kid(rpt_kid);
				assignWork.setEvent_source(event_source);
				assignWork.setEvent_type(event_type);
				assignWork.setCreateby(user.getUsername());
				assignWork.setCreateTime(new Date());
				assignWork.setRpt_id(rpt_id);
				assignWork.setOwner_id(ownerId);
				assignWork.setOwner_house(roomId);
				assignWork.setRoomNo(roomNo);
				assignWork.setEvent_state("0");//未派工
				
				//派工表
				EventSendEntity send=new EventSendEntity();
				send.setHandle_time(assignWork.getCreateTime());
				send.setOwnHandler(assignWork.getCreateby());
				String deptName=assignWorkService.getdept(assignWork.getCreateby());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					send.setHandler_dept("系统");	
				}else{
				send.setHandler_dept(dept_name);
				}
				send.setHandler_phone(in_call);
				send.setStatus(assignWork.getEvent_state());
				
				String processKey = "assignNewProcess";
				if(rpt_id == null || rpt_id.length()==0){
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
						dispatch_status = "0";
					}
					wormDispatch.setDispatch_status(dispatch_status);
					assignWorkService.addWorkDispatch(wormDispatch);
				}else if(null != event_type && event_type.equals("1")){
					//投诉建议
					WorkComplaintEntity complaintEntity = null;
					complaintEntity = assignWorkService.getWorkComplById(rpt_id);
					if(null == complaintEntity){
						complaintEntity = new WorkComplaintEntity();
						complaintEntity.setComp_kid(CommUtil.getGuuid());
					}
					if(comp_emergency.equals("")){
						comp_emergency = "0";
					}
					if(comp_status.equals("")){
						comp_status = "0";
					}
					complaintEntity.setMtn_id(rpt_id);
					complaintEntity.setComp_detail(comp_detail);
					assignWork.setEvent_content(comp_detail);//事件类容
					complaintEntity.setComp_status(comp_status);
					complaintEntity.setComp_emergency(comp_emergency);
					assignWorkService.addWorkCompl(complaintEntity);
					processKey = "assignComp";
				}
				/**
				 * 开始流程
				 */
				String processId = grapService.startBussniseTask(processKey);
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
				/**
				 * 开始流程完成
				 */
				assignWork.setProcessInstanceId(Long.parseLong(processId));
				assignWorkService.addWorkMain(assignWork);
				assignWorkService.addEventSend(send);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				if(null != event_type && event_type.equals("0")){
					NoticePushUtil.pushNoticeByAlias("006", "有一个新维修工单，请前往查看。", "02");
				}
				
			}else if(method.equals("deleteAssign")){
//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(assignId)){
						JSONArray jsonArr = JSON.parseArray(assignId);
						for (int i=0;i<jsonArr.size();i++){
							//检查状态,派工单状态为---未派工    ----的才能删除
							String rpt_id = (String) jsonArr.get(i);
							WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
							if(dispatchEntity == null){
								dispatchEntity = new WorkMainDispatchEntity();
							}
							//报障明细
							String dispatchStatu = dispatchEntity.getDispatch_status();
							if(null == dispatchStatu || dispatchStatu.equals("0") || dispatchStatu.equals("")){
								assignWorkService.deleteWorkDispatch(rpt_id);//删除派工信息
								assignWorkService.deleteWorkMain(rpt_id);//删除主表信息
							}
							//投诉明细
//							WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
							
							WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(rpt_id);
							if(complaintEntity == null){
								complaintEntity = new WorkComplaintEntity();
							}
							String compStatu = complaintEntity.getComp_status();
							if(null == compStatu || compStatu.equals("0") || compStatu.equals("")){
								assignWorkService.deleteWorkComp(rpt_id);//删除投诉信息
								//没有明细也要删除主表
								assignWorkService.deleteWorkMain(rpt_id);//删除主表信息
							}
							
						}
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					String jsonString = JSON.toJSONString("删除失败！");
					log.error("删除失败："+e.getMessage());
					out.println(jsonString);
					out.flush();
				}
			}else if(method.equalsIgnoreCase("initDropAll")){
				List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("main");
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equals("listDispatch")){
				//返回list画面
				//arg0.getRequestDispatcher("/assignwork/AssignWorkList.jsp").forward(arg0,arg1);
				
				//返回查询结果列表

				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				  // 每页行数
				  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				  String search=java.net.URLDecoder.decode(arg0.getParameter("search") , "UTF-8");
				  if(search.equals("undefined"))
				    {
				    	search="";
				    }
				  Map<String, Object> parameter = new HashMap();
				  parameter.put("search", search);
				  parameter.put("userId", user.getUsername());
				  if (currentPage != 0) {// 获取页数
				   currentPage = currentPage / showCount;
				  }
				  currentPage += 1;
				Page<VWorkDispatchEntity> page =new Page<VWorkDispatchEntity>(showCount, currentPage);
				try {
					assignWorkService.getWorkDispatch(page, parameter, null);
				} catch (Exception e) {
					log.error("异常");
					
				}
				List<VWorkDispatchEntity> result=(List<VWorkDispatchEntity>) page.getEntities();
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
			}else if(method.equals("dispatchView")){
				List list = assignWorkService.getDispatchWorkById(assignId);
				if(null != list){
					try {
						Thread.sleep(200);
						String jsonString = JSON.toJSONString(list.get(0));
						PrintWriter out = arg1.getWriter();
						out.println(jsonString);
						out.flush();
					} catch (InterruptedException e) 
					{
						log.warn("线程异常:"+e.getMessage());
						System.out.println("线程异常");
					}
					
				}
				
			}else if(method.equals("dispatchSave")){
				boolean flag=false;
				String mtn_id= null == arg0.getParameter("mtn_id")?"":arg0.getParameter("mtn_id").toString();
				String dispatch_handle_id= null == arg0.getParameter("dispatch_handle_id")?"":arg0.getParameter("dispatch_handle_id").toString();
				String dispatch_handle_name= null == arg0.getParameter("dispatch_handle_name")?"":arg0.getParameter("dispatch_handle_name").toString();
				String mtn_emergency= null == arg0.getParameter("mtn_emergency")?"":arg0.getParameter("mtn_emergency").toString();
				String type= null == arg0.getParameter("type")?"":arg0.getParameter("type").toString();
				
				String deptName= null == arg0.getParameter("deptName")?"":arg0.getParameter("deptName").toString();
				
				String mtn_type= null == arg0.getParameter("mtn_type")?"":arg0.getParameter("mtn_type").toString();
				String dispatch_tools= null == arg0.getParameter("dispatch_tools")?"":arg0.getParameter("dispatch_tools").toString();
				String dispatch_expense= null == arg0.getParameter("dispatch_expense")?"":arg0.getParameter("dispatch_expense").toString();
				String dispatch_result= null == arg0.getParameter("dispatch_result")?"":arg0.getParameter("dispatch_result").toString();
				
				String material_cost= null == arg0.getParameter("material_cost")?"":arg0.getParameter("material_cost").toString();
				String labor_cost= null == arg0.getParameter("labor_cost")?"":arg0.getParameter("labor_cost").toString();
			
				
				//业主处理意见
				String mtn_priority= null == arg0.getParameter("mtn_priority")?"0":arg0.getParameter("mtn_priority").toString();
				Map delmap=new HashMap();
				/**
				 * 驱动流程图
				 */
				WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
				/**
				 * 流程处理
				 */
				String processinstanceid = mainEntity.getProcessInstanceId()+"";
				
				Map<String,Object> variables = null;
				String state = "";
				EventSendEntity send=new EventSendEntity();
				if(mainEntity == null){
					mainEntity = new WorkMainEntity();
				}
				WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(mtn_id);
			//	synchronized(dispatchEntity.getClass()){
				if(type.equals("5")){
					//派工
					dispatchEntity.setDispatch_handle_id(dispatch_handle_id);
					dispatchEntity.setDispatch_handle_name(dispatch_handle_name);
					dispatchEntity.setMtn_emergency(mtn_emergency);
					dispatchEntity.setDispatch_time(new Date());
					dispatchEntity.setDispatch_status("1");
					dispatchEntity.setDispatch_id(commUtil.getBusinessId("pg"));
					state = dispatchEntity.getDispatch_status();
					
					String slname=null==dispatchEntity.getCreateby()?"":dispatchEntity.getCreateby();
					if(slname.equals(""))
					{
						dispatchEntity.setCreateby(user.getUsername());
						mainEntity.setCreateby(user.getUsername());
					}
					
					
					//派工纪录
					send.setHandle_time(dispatchEntity.getDispatch_time());
					send.setOwnHandler(dispatch_handle_id);
					send.setEvent_id(dispatchEntity.getMtn_id());
					String dept_name=null==assignWorkService.getdept(dispatch_handle_id)?"":assignWorkService.getdept(dispatch_handle_id);
					//String dept_name=null==deptName?"":deptName;
					if(dept_name.equals(""))
					{
						send.setHandler_dept("");	
					}else{
					send.setHandler_dept(dept_name);
					}
					String phone=assignWorkService.getHandlePhone(dispatch_handle_id);
					send.setHandler_phone(phone);
					send.setStatus(dispatchEntity.getDispatch_status());
					send.setMtn_type(mtn_type);
					//assignWorkService.addWorkDispatch(dispatchEntity);
					assignWorkService.addWorkMain(mainEntity);
					//assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
					//assignWorkService.addEventSend(send);
				}else if(type.equals("6")){
					//现场处理
					
					if(dispatchEntity.getMtn_type()==null)
					{
						dispatchEntity.setMtn_type(mtn_type);	
					}
					if(dispatchEntity.getMtn_priority()==null)
					{
						dispatchEntity.setMtn_priority(mtn_priority);	
					}
					
					dispatchEntity.setDispatch_tools(dispatch_tools);
					dispatchEntity.setDispatch_expense(dispatch_expense);
					if(labor_cost.equals(""))
					{
						dispatchEntity.setLabor_cost(0.0);
						
					}else{
					dispatchEntity.setLabor_cost(Double.parseDouble(labor_cost));
					}
					if(material_cost.equals(""))
					{
						dispatchEntity.setMaterial_cost(0.0);
					}else{
					 dispatchEntity.setMaterial_cost(Double.parseDouble(material_cost));
					}
					dispatchEntity.setDispatch_result(dispatch_result);
					dispatchEntity.setDispatch_arrive_time(new Date());
					dispatchEntity.setDispatch_status("2");
					dispatchEntity.setMtn_priority(mtn_priority);//业主处理意见
					variables = new HashMap<String,Object>();
					//flag==2 质保期...,  flag==1 现场确认及费用核算
					
					if(null != mtn_type && mtn_type.equals("1")){
						//日常紧急维修
						variables.put("flag", "2");
						//compFlag==1  业主拒绝维修         compFlag==2业主确认维修
						if(null != mtn_priority && mtn_priority.equals("0")){
							//业主维修意见
							variables.put("compFlag", "2");
						}else if(null != mtn_priority && mtn_priority.equals("1")){
							mainEntity.setOrder_state("拒单");
							variables.put("compFlag", "1");
							assignWorkService.addWorkMain(mainEntity);
						}
					}else{
						variables.put("flag", "1");
					}
					
					state = dispatchEntity.getDispatch_status();
					send.setHandle_time(dispatchEntity.getDispatch_arrive_time());
					send.setOwnHandler(dispatchEntity.getDispatch_handle_id());
					send.setEvent_id(dispatchEntity.getMtn_id());
					//String deptName=assignWorkService.getdept(dispatchEntity.getDispatch_handle_id());
					String dept_name=null==deptName?"":deptName;
					if(dept_name.equals(""))
					{
						send.setHandler_dept("系统");	
					}else{
					send.setHandler_dept(dept_name);
					}
					String phone=assignWorkService.getHandlePhone(dispatchEntity.getDispatch_handle_id());
					send.setHandler_phone(phone);
					send.setStatus(state);
					//assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
					//assignWorkService.addEventSend(send);
					
				}else if(type.equals("7")){
					//销单
					
					try{
						Map map=new HashMap<String,Object>();
						map.put("room_id", mainEntity.getOwner_house());
						map.put("room_no", mainEntity.getRoomNo());
						map.put("owner_id", mainEntity.getOwner_id());
						map.put("owner_name", mainEntity.getRpt_name());
						map.put("begin_time", getSimp(new Date()));
						map.put("end_time",  getSimp(new Date()));
						
						map.put("oper_emp_id", user.getUsername());
						double mt_cost=null==dispatchEntity.getMaterial_cost()?0.0:dispatchEntity.getMaterial_cost();
						double lb_cost=null==dispatchEntity.getLabor_cost()?0.0:dispatchEntity.getLabor_cost();
						//收费接口:
						if(!("").equals(mt_cost))
						{
							map.put("work_id", mtn_id);
							map.put("charge_type_no", "0007");
							map.put("charge_type_name", "维修材料费");
							map.put("receive_amount", mt_cost);//材料费
							flag=chargeInfoService.addCharge(map);
							
						}
						if(!("").equals(lb_cost))
						{
							map.put("work_id", mtn_id);
							map.put("charge_type_no", "0006");
							map.put("charge_type_name", "人工费");
							map.put("receive_amount", lb_cost);//人工费
							flag=chargeInfoService.addCharge(map);	
						}
						}catch(Exception e)
						{
							
						}
					
					//================================
					//消单
					dispatchEntity.setDispatch_finish_time(new Date());
					dispatchEntity.setDispatch_status("3");
					state = dispatchEntity.getDispatch_status();
					
					send.setHandle_time(null);
					send.setOwnHandler(user.getUsername());
					send.setEvent_id(dispatchEntity.getMtn_id());
				
					//String deptName=assignWorkService.getdept(user.getUsername());
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
                    if(("").equals(mainEntity.getOrder_state())||mainEntity.getOrder_state()==null){
					mainEntity.setOrder_state("正常");
					assignWorkService.addWorkMain(mainEntity);
                    }
					
				}else if(type.equals("8"))
					//取消
				{
					
					
					if(mainEntity.getEvent_state().equals("0"))
					{
						mainEntity.setOrder_state("取消");
						delmap.put("flag", "2");//取消标识
						state="1";
						if(null != processinstanceid && processinstanceid.length()>0){
							grapService.completeBussniseTask(processinstanceid, user.getCname(), delmap,state);
						}
						
					}else if(mainEntity.getEvent_state().equals("1"))
					{
						state="2";
						delmap.put("flag", "3");//现场确认之前
						mainEntity.setOrder_state("取消");
						if(null != processinstanceid && processinstanceid.length()>0){
							grapService.completeBussniseTask(processinstanceid, user.getCname(), delmap,state);
						}
						//派工表更新-----
					}
					
					if(null != processinstanceid && processinstanceid.length()>0){
						grapService.completeBussniseTask(processinstanceid,  user.getCname(), null,"1");
					}
					send.setHandle_time(new Date());
					send.setOwnHandler(user.getUsername());
					send.setEvent_id(dispatchEntity.getMtn_id());
					String dept_name=assignWorkService.getdept(user.getUsername());
					//String dept_name=null==deptName?"":deptName;
					if(dept_name.equals(""))
					{
						send.setHandler_dept("");	
					}else{
					send.setHandler_dept(dept_name);
					}
					String phone=assignWorkService.getHandlePhone(user.getUsername());
					send.setHandler_phone(phone);
					send.setStatus("5");
					mainEntity.setEvent_state("5");
					
					mainEntity.setFinishTime(new Date());
					dispatchEntity.setDispatch_status("5");
					dispatchEntity.setDispatch_finish_time(new Date());
					dispatchEntity.setDispatch_handle_id(user.getUsername());
					dispatchEntity.setDispatch_handle_name(user.getCname());
					dispatchEntity.setDispatch_result("业主取消服务");
					assignWorkService.addWorkMain(mainEntity);
					
					
				}
				
				if(type.equals("6"))
				{
					if(null != processinstanceid && processinstanceid.length()>0){
						grapService.completeBussniseTask(processinstanceid, user.getCname(), variables,state);
					}
					if(variables!=null){
						if(null != processinstanceid && processinstanceid.length()>0){
							grapService.completeBussniseTask(processinstanceid, user.getCname(), variables,state);
					}
					}
				}else if(type.equals("5"))//新加
				{
					Map map=new HashMap();
					map.put("flag", "1");
					if(null != processinstanceid && processinstanceid.length()>0){
						grapService.completeBussniseTask(processinstanceid, user.getCname(), map,state);
					}
				}else if(type.equals("7"))
				{
					if(null != processinstanceid && processinstanceid.length()>0){
						grapService.completeBussniseTask(processinstanceid, user.getCname(), variables,state);
					}
				}
				assignWorkService.addWorkDispatch(dispatchEntity);
				//销单完成任务
				if(type.equals("7")){
					//完成时间
//					WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
					mainEntity.setFinishTime(new Date());
					mainEntity.setOrder_state("正常");
					assignWorkService.addWorkMain(mainEntity);
					//完成任务
					assignWorkService.addWorkWorkState(dispatch_handle_id, 0);
				}else if(type.equals("5")){
					//分派任务
					assignWorkService.addWorkWorkState(dispatch_handle_id, 1);
				}
				
				assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
				assignWorkService.addEventSend(send);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				//}
			}else if(method.equals("dispatchDelete")){

//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(assignId)){
						JSONArray jsonArr = JSON.parseArray(assignId);
						for (int i=0;i<jsonArr.size();i++){
							//检查状态,派工单状态为---未派工    ----的才能删除
							String rpt_id = (String) jsonArr.get(i);
							WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
							WorkMainDispatchEntity dispatchEntityEmp = new WorkMainDispatchEntity();
							if(dispatchEntity == null){
								dispatchEntity = new WorkMainDispatchEntity();
							}
							dispatchEntityEmp.setDispatch_kid(dispatchEntity.getDispatch_kid());
							dispatchEntityEmp.setMtn_id(dispatchEntity.getMtn_id());
							dispatchEntityEmp.setDispatch_status("0");
							/**
							 * 清空掉派工信息
							 */
							assignWorkService.addWorkDispatch(dispatchEntityEmp);
//							String dispatchStatu = dispatchEntity.getDispatch_status();
//							if(null == dispatchStatu || dispatchStatu.equals("0") || dispatchStatu.equals("")){
//								assignWorkService.deleteWorkDispatch(rpt_id);//删除派工信息
//								assignWorkService.deleteWorkMain(rpt_id);//删除主表信息
//							}
							
						}
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}
				
			}else if(method.equals("listComp")){
				//投诉列表
				//返回查询结果列表
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				  // 每页行数
				  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				  if (currentPage != 0) {// 获取页数
				   currentPage = currentPage / showCount;
				  }
				  currentPage += 1;
				  String search=java.net.URLDecoder.decode(arg0.getParameter("search") , "UTF-8");
				  if(search.equals("undefined"))
				    {
				    	search="";
				    }
				  Map<String, Object> parameter = new HashMap();
				  parameter.put("search", search);
				  parameter.put("userId", user.getUsername());
				  
				Page<VWorkCompEntity> page =new Page<VWorkCompEntity>(showCount, currentPage);
				try {
					assignWorkService.getWorkComp(page, parameter, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<VWorkCompEntity> result=(List<VWorkCompEntity>) page.getEntities();
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
			}else if(method.equals("compView")){
				List list = assignWorkService.getCompWorkById(assignId);
				if(null != list && list.size()>0){
					String jsonString = JSON.toJSONString(list.get(0));
					
					//jsonString = jsonString.substring(1, jsonString.length()-1);
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}
				
			}else if(method.equals("CompSave")){
				
				String mtn_id= null == arg0.getParameter("mtn_id")?"":arg0.getParameter("mtn_id").toString();
				String comp_operator_id= null == arg0.getParameter("comp_operator_id")?"":arg0.getParameter("comp_operator_id").toString();
				String comp_operator_username= null == arg0.getParameter("comp_operator_username")?"":arg0.getParameter("comp_operator_username").toString();
				String comp_emergency= null == arg0.getParameter("comp_emergency")?"0":arg0.getParameter("comp_emergency").toString();
				String type= null == arg0.getParameter("type")?"":arg0.getParameter("type").toString();
				
				String comp_detail= null == arg0.getParameter("comp_detail")?"":arg0.getParameter("comp_detail").toString();
				String comp_result= null == arg0.getParameter("comp_result")?"":arg0.getParameter("comp_result").toString();
				String comp_process= null == arg0.getParameter("comp_process")?"":arg0.getParameter("comp_process").toString();
				String comp_solve= null == arg0.getParameter("comp_solve")?"":arg0.getParameter("comp_solve").toString();
				String comp_degree= null == arg0.getParameter("comp_degree")?"":arg0.getParameter("comp_degree").toString();
				String comp_reply= null == arg0.getParameter("comp_reply")?"":arg0.getParameter("comp_reply").toString();
				
				WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
				String state = "";
				if(null == mainEntity){
					mainEntity = new WorkMainEntity();
				}
				WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(mtn_id);
				
				if(null == complaintEntity){
					complaintEntity = new WorkComplaintEntity();
				}
				
			//增加派工状态记录2017-1-11
				EventSendEntity send=new EventSendEntity();
				send.setHandle_time(new Date());
				send.setOwnHandler(user.getUsername());
				String deptName=assignWorkService.getdept(user.getUsername());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					send.setHandler_dept("系统");	
				}else{
				send.setHandler_dept(dept_name);
				}
				send.setHandler_phone(user.getMobile());
				
				
				
				if(type.equals("5")){
					//分派人
					complaintEntity.setComp_operator_id(comp_operator_id);
					complaintEntity.setComp_operator(comp_operator_username);
					complaintEntity.setComp_emergency(comp_emergency);
					complaintEntity.setComp_createby(user.getUsername());
					complaintEntity.setComp_createTime(new Date());
					complaintEntity.setComp_status("1");
					complaintEntity.setComp_reply(comp_reply);
					complaintEntity.setComp_id(commUtil.getBusinessId("pg"));
//					dispatchEntity.setDispatch_id(commUtil.getBusinessId("pg"));
					state = complaintEntity.getComp_status();
					mainEntity.setEvent_state(state);
					
					String slname=null==mainEntity.getCreateby()?"":mainEntity.getCreateby();
					if(slname.equals("")){
					mainEntity.setCreateby(user.getUsername());
					
					send.setStatus(state);
					}
				}else if(type.equals("6")){
					//现场处理
//					complaintEntity.setComp_operator_id(comp_operator_id);
					complaintEntity.setComp_result(comp_result);
					complaintEntity.setComp_process(comp_process);
					complaintEntity.setComp_solve(comp_solve);
					complaintEntity.setComp_degree(comp_degree);
					complaintEntity.setComp_detail(comp_detail);
					complaintEntity.setComp_emergency(comp_emergency);
					complaintEntity.setComp_createby(user.getUsername());
					complaintEntity.setComp_createTime(new Date());
					complaintEntity.setComp_status("2");
					state = complaintEntity.getComp_status();
					mainEntity.setEvent_state(state);
					send.setStatus(state);
				}else if(type.equals("7")){
					//消单
					complaintEntity.setComp_finish_time(new Date());
					complaintEntity.setComp_status("3");
					state = complaintEntity.getComp_status();
					mainEntity.setEvent_state(state);
					mainEntity.setOrder_state("正常");
					mainEntity.setFinishTime(new Date());
					
					send.setStatus(state);
				}
				
				if(type.equals("7")){
					//完成时间
					
					mainEntity.setFinishTime(new Date());
					//assignWorkService.addWorkMain(mainEntity);
					//销单，完成任务
					assignWorkService.addWorkWorkState(comp_operator_id, 0);
				}else if(type.equals("5")){
					//分配任务
					assignWorkService.addWorkWorkState(comp_operator_id, 1);
				}
				assignWorkService.addWorkMain(mainEntity);
				assignWorkService.addWorkCompl(complaintEntity);
				send.setEvent_id(mtn_id);
				assignWorkService.addEventSend(send);
				String processinstanceid = mainEntity.getProcessInstanceId()+"";
				
				/*if(type.equals("5"))
				{
				Map map=new HashMap();
				map.put("flag", "2");
				grapService.completeBussniseTask(processinstanceid, user.getCname(), map,state);
				}else{*/
				
				grapService.completeBussniseTask(processinstanceid, user.getCname(), null,state);
				//}
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
			}else if(method.equals("compDelete")){
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(assignId)){
						JSONArray jsonArr = JSON.parseArray(assignId);
						for (int i=0;i<jsonArr.size();i++){
							//检查状态,派工单状态为---未派工    ----的才能删除
							String rpt_id = (String) jsonArr.get(i);
							WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(rpt_id);
//							WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
							WorkComplaintEntity complaintEntityEmp = new WorkComplaintEntity();
							if(complaintEntity == null){
								complaintEntity = new WorkComplaintEntity();
							}
							complaintEntityEmp.setComp_kid(complaintEntity.getComp_kid());
							complaintEntityEmp.setMtn_id(complaintEntity.getMtn_id());
							complaintEntityEmp.setComp_status("0");
							/**
							 * 清空掉派工信息
							 * 
							 */
							assignWorkService.addWorkCompl(complaintEntity);
							
						}
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}
				
			
			}else if(method.equals("nameformat")){
				//用户名字转换
				String userName= null == arg0.getParameter("userName")?"":arg0.getParameter("userName").toString();
				String cName = businessService.findUserByUserName(userName);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString(cName));
				out.flush();
			}else if(method.equals("workListStat")){
				//用户名字转换
				String mtn_id= null == arg0.getParameter("mtn_id")?"":arg0.getParameter("mtn_id").toString();
				String cName = assignWorkService.getWorkMainState(mtn_id);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString(cName));
				out.flush();
			}else if(method.equals("handle"))//咨询建议处理
			{
				String in_call= null == arg0.getParameter("in_call")?"":arg0.getParameter("in_call").toString();
				String addres= null == arg0.getParameter("addres")?"":arg0.getParameter("addres").toString();
				String rpt_id= null == arg0.getParameter("mtn_id")?"":arg0.getParameter("mtn_id").toString();
				String mtn_detail= null == arg0.getParameter("mtn_detail")?"":arg0.getParameter("mtn_detail").toString();
				String mtn_emergency= null == arg0.getParameter("mtn_emergency")?"0":arg0.getParameter("mtn_emergency").toString();
				String dispatch_status= null == arg0.getParameter("dispatch_status")?"0":arg0.getParameter("dispatch_status").toString();
				String comp_reply= null == arg0.getParameter("comp_reply")?"":arg0.getParameter("comp_reply").toString();
				
				String comp_operator_id= null == arg0.getParameter("comp_operator_id")?"":arg0.getParameter("comp_operator_id").toString();
				String comp_detail= null == arg0.getParameter("comp_detail")?"":arg0.getParameter("comp_detail").toString();
				String comp_result= null == arg0.getParameter("comp_result")?"":arg0.getParameter("comp_result").toString();
				String comp_process= null == arg0.getParameter("comp_process")?"":arg0.getParameter("comp_process").toString();
				String comp_solve= null == arg0.getParameter("comp_solve")?"":arg0.getParameter("comp_solve").toString();
				String comp_degree= null == arg0.getParameter("comp_degree")?"":arg0.getParameter("comp_degree").toString();
				//咨询建议
				String comp_status= null == arg0.getParameter("comp_status")?"0":arg0.getParameter("comp_status").toString();
				String comp_emergency= null == arg0.getParameter("comp_emergency")?"0":arg0.getParameter("comp_emergency").toString();
				//String comp_detail= null == arg0.getParameter("comp_detail")?"":arg0.getParameter("comp_detail").toString();	
				WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);
				WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(rpt_id);
				Map<String,Object> variables=new HashMap();	
				String processinstanceid = mainEntity.getProcessInstanceId()+"";
				mainEntity.setOrder_state("正常");
				mainEntity.setEvent_state("3");
				mainEntity.setFinishTime(new Date());
				mainEntity.setCreateby(user.getUsername());
				if(comp_emergency.equals("0"))//咨询处理未完成
				{
					
					variables.put("flag", 1);
					
				}
				complaintEntity.setComp_result(comp_result);
				complaintEntity.setComp_process(comp_process);
				complaintEntity.setComp_solve(comp_solve);
				complaintEntity.setComp_degree(comp_degree);
				complaintEntity.setComp_detail(comp_detail);
				complaintEntity.setComp_status("3");
				String state = complaintEntity.getComp_status();
				complaintEntity.setComp_reply(comp_reply);
				complaintEntity.setMtn_id(rpt_id);
				complaintEntity.setComp_emergency(comp_emergency);
				complaintEntity.setComp_createby(user.getUsername());
				if(comp_operator_id.equals(""))
				{
					complaintEntity.setComp_operator_id(user.getUsername());//处理人ID
					complaintEntity.setComp_operator(user.getCname());//处理人	
				}else{
				complaintEntity.setComp_operator_id(comp_operator_id);//处理人
				}
				complaintEntity.setComp_createTime(new Date());
				complaintEntity.setComp_id(commUtil.getBusinessId("pg"));
				complaintEntity.setComp_finish_time(new Date());
				try{
				assignWorkService.updateWorkMainState(rpt_id,state);//更新主表状态
				EventSendEntity send=new EventSendEntity();
				send.setHandle_time(complaintEntity.getComp_createTime());
				send.setOwnHandler(user.getUsername());
				String deptName=assignWorkService.getdept(user.getUsername());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					send.setHandler_dept("系统");	
				}else{
				send.setHandler_dept(dept_name);
				}
				send.setHandler_phone(in_call);
				send.setStatus(state);
				assignWorkService.addEventSend(send);
				
				assignWorkService.addWorkCompl(complaintEntity);
				}finally{
				grapService.completeBussniseTask(processinstanceid, user.getCname(), variables,"6");
				}
				assignWorkService.addWorkMain(mainEntity);
				String jsonString = JSON.toJSONString("操作成功！");
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equals("getEventImg"))
			{
				String rpt_id= null == arg0.getParameter("rpt_id")?"":arg0.getParameter("rpt_id").toString();	
			    List<DispatchImageEntity> res=	assignWorkService.getEventImgByRptId(rpt_id);
			  //传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(JSON.toJSONString(res));
				out.flush();
			}else if(method.equals("dispatchEditeSave"))
			{
				String rpt_id= null == arg0.getParameter("mtn_id")?"":arg0.getParameter("mtn_id").toString();
				String dispatch_handle_id= null == arg0.getParameter("dispatch_handle_id")?"":arg0.getParameter("dispatch_handle_id").toString();
				String dispatch_handle_name= null == arg0.getParameter("dispatch_handle_name")?"":arg0.getParameter("dispatch_handle_name").toString();
				String mtn_emergency= null == arg0.getParameter("mtn_emergency")?"":arg0.getParameter("mtn_emergency").toString();
				String type= null == arg0.getParameter("type")?"":arg0.getParameter("type").toString();
				String deptName= null == arg0.getParameter("deptName")?"":arg0.getParameter("deptName").toString();
				String mtn_type= null == arg0.getParameter("mtn_type")?"":arg0.getParameter("mtn_type").toString();
				String dispatch_tools= null == arg0.getParameter("dispatch_tools")?"":arg0.getParameter("dispatch_tools").toString();
				String dispatch_expense= null == arg0.getParameter("dispatch_expense")?"":arg0.getParameter("dispatch_expense").toString();
				String dispatch_result= null == arg0.getParameter("dispatch_result")?"":arg0.getParameter("dispatch_result").toString();
				String material_cost= null == arg0.getParameter("material_cost")?"":arg0.getParameter("material_cost").toString();
				String labor_cost= null == arg0.getParameter("labor_cost")?"":arg0.getParameter("labor_cost").toString();
				String handerIds= null == arg0.getParameter("handerIds")?"":arg0.getParameter("handerIds").toString();
				String handerNames= null == arg0.getParameter("handerNames")?"":arg0.getParameter("handerNames").toString();
				dispatchPersonService.deleteDispatchPersonById(rpt_id);
				String [] strIds=handerIds.split(";");
				String [] strnames=handerNames.split(";");
				DispatchPerson person = null;
				for(int i=0;i<strIds.length;i++){
					person=new DispatchPerson();
					person.setRpt_id(rpt_id);
					person.setHandle_id(strIds[i]);
					person.setHandle_name(strnames[i]);
					//person.setEvent_state(event_state);
					dispatchPersonService.addDisptchPersonInfo(person);
				}
				person=new DispatchPerson();
				person.setRpt_id(rpt_id);
				person.setHandle_id(dispatch_handle_id);
				person.setHandle_name(dispatch_handle_name);
				dispatchPersonService.addDisptchPersonInfo(person);
				try {
					assignWorkService.updateWorkMainHanders(rpt_id,handerNames);
					assignWorkService.updateDispatchWorkMainHanders(rpt_id, dispatch_handle_name, dispatch_handle_id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
			}
			if(method.equalsIgnoreCase("inputFile")){
				arg1.setContentType("application/json"); 
				String folder =  null == arg0.getParameter("folder")?"":arg0.getParameter("folder").toString();

				String result = "{\"status\":\"0\",\"data\":\"上传失败\"}";
				try{
					String paths = inputFile(arg0,arg1,folder);
					if(paths!=null){
						result = "{\"status\":\"1\",\"data\":\""+paths+"\"}";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				String b = JSON.toJSONString(result);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/assigenwork/assignlist";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
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
	
	@SuppressWarnings("static-access")	
public String inputFile(HttpServletRequest arg0, HttpServletResponse arg1,String folder) throws IOException, ServletException{
		
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String savePath = "D:/upload/app/"+folder;
		String paths = "";
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath+"目录不存在，需要创建");
			//创建目录
			file.mkdirs();
		}
		
		try{
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8"); 
			//3、判断提交上来的数据是否是上传表单的数据
			if(!ServletFileUpload.isMultipartContent(arg0)){
				//按照传统方式获取数据
				return null;
			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(arg0);
			
			for(FileItem item : list){
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				}else{//如果fileitem中封装的是上传文件
					//得到上传的文件名称，
					String filename = item.getName();
					System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
						continue;
					}
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					String ext = filename.substring(filename.lastIndexOf(".") + 1);
					String uuid = CommUtil.getGuuid();
					String relativePath = savePath+"/"+uuid+"."+ext;
					String url = arg0.getRequestURL().toString();
					String url1 = arg0.getServletPath();
					url = url.replace(url1, "");
			    	String filePath = url + "/fileUpload.app?method=download&filepath="+relativePath;
					if(paths.isEmpty()){
						paths = filePath;
					}else{
						paths += ","+filePath;
					}
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					//获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					//创建一个文件输出流
					//创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + uuid +"."+ext);
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len=in.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					//关闭输入流
					in.close();
					//关闭输出流
					out.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
					if(!folder.equals("logo")){
						
						ImgCompress imgCom = new ImgCompress(savePath + "/" +  uuid +"."+ext);
						imgCom.resizeFix(600, 600);
					}
					//return "true";
				}
			}
			return paths;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filepath){
		 // path是指欲下载的文件的路径。
		try {
		    File file = new File(filepath);
		    String filename = file.getName();
		    // 以流的形式下载文件。
		    InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
		    byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
		    fis.close();
		    // 清空response
		    response.reset();
		    // 设置response的Header
		    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
		    response.addHeader("Content-Length", "" + file.length());
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    toClient.write(buffer);
		    toClient.flush();
		    toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
