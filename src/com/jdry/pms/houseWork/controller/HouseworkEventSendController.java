package com.jdry.pms.houseWork.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.pojo.VHouseWorkEventSendEnitiy;
import com.jdry.pms.houseWork.service.HouseworkEventSendService;
import com.jdry.pms.houseWork.service.HouseworkEventService;
import com.soft.service.GrapService;
/**
 * 派工单处理控制器
 * @author hezuping
 *
 */
@Repository
@Component
public class HouseworkEventSendController implements IController {

	@Resource
	HouseworkEventSendService houseworkEventSendService;
	@Resource
	HouseworkEventService houseworkEventService;
	@Resource
	GrapService grapService;
	@Resource
	AssignWorkService assignWorkService;
	
	static Logger log=Logger.getLogger(HouseworkEventSendController.class);
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException
	{
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("pragma","no-cache");
		resp.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		String workId=null == req.getParameter("deleteIds")?"":req.getParameter("deleteIds").toString();//业务编号
		if(method.equals("listEventSend"))
		{
			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数			  
		    int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
		    String search=java.net.URLDecoder.decode(req.getParameter("search") , "UTF-8");
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		    Map<String, Object> parameter = new HashMap();
		    if(search.equals("undefined"))
		    {
		    	search="";
		    }
		    parameter.put("search", search);
		    parameter.put("userId", user.getUsername());
		    Page page =new Page(showCount, currentPage);
		    List res=houseworkEventSendService.queryHouseWorkEventSendInfo(page, parameter, null);
		    List list=new ArrayList();
			for(int i = 0,len=res.size();i<len; i++)
			{
				Map map = new HashMap();
				Object[] obj = (Object[])res.get(i);
				String event_no = null == obj[0]?"":obj[0].toString();
				map.put("event_no", event_no);
				String event_title = null == obj[1]?"":obj[1].toString();
				map.put("event_title", event_title);	
				String call_phone = null == obj[2]?"":obj[2].toString();
				map.put("call_phone", call_phone);	
				
				String accept_time = null == obj[3]?"":obj[3].toString();
				String pre_time = null == obj[6]?"":obj[6].toString();
				
					if(!accept_time.equals("")){
					 map.put("accept_time", getSimp(accept_time));
					}else
					{
						map.put("accept_time", accept_time);	
					}
					if(!pre_time.equals("")){
					 map.put("pre_time", getSimp(pre_time));
					}else
					{
						map.put("pre_time", pre_time);	
					}
				String event_source = null == obj[4]?"":obj[4].toString();
				map.put("event_source", event_source);
				String event_source_name = null == obj[5]?"":obj[5].toString();
				map.put("event_source_name", event_source_name);	
				String record_id = null == obj[7]?"":obj[7].toString();
				map.put("record_id", record_id);	
				
				String bpm_processId = null == obj[8]?"":obj[8].toString();
				map.put("bpm_processId", bpm_processId);
				String rpt_name = null == obj[9]?"":obj[9].toString();
				map.put("rpt_name", rpt_name);
				String event_content = null == obj[10]?"":obj[10].toString();
				map.put("event_content", event_content);
				String verify_oper_id = null == obj[11]?"":obj[11].toString();
				map.put("verify_oper_id", verify_oper_id);
				
				
				
				String user_address = null == obj[12]?"":obj[12].toString();
				map.put("user_address", user_address);
				String id = null == obj[13]?"":obj[13].toString();
				map.put("id", id);
				//派工
				String oper_id = null == obj[14]?"":obj[14].toString();
				map.put("oper_id", oper_id);//处理人ID
				
				String send_time = null == obj[15]?"":obj[15].toString();
				map.put("send_time", send_time);
				
				String send_state = null == obj[16]?"":obj[16].toString();
				map.put("send_state", send_state);
				
				String event_state = null == obj[17]?"":obj[17].toString();
				map.put("event_state", event_state);
				String send_no = null == obj[18]?"":obj[18].toString();
				map.put("send_no", send_no);
				
				String oper_name = null == obj[19]?"":obj[19].toString();
				map.put("oper_name", oper_name);
				String send_id = null == obj[20]?"":obj[20].toString();
				map.put("send_id", send_id);
				
				String handle_content = null == obj[21]?"":obj[21].toString();
				map.put("handle_content", handle_content);
				String arrv_time = null == obj[22]?"":obj[22].toString();
				map.put("arrv_time", arrv_time);
				
				String houseKeepingPay = null == obj[23]?"":obj[23].toString();
				map.put("houseKeepingPay", houseKeepingPay);
				
				String verify_oper_name = null == obj[25]?"":obj[25].toString();
				map.put("verify_oper_name", verify_oper_name);
				
				list.add(map);
				
			}
			page.setEntities(list);
			List result=(List) page.getEntities();
			long count = page.getEntityCount();	
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("total", count);
		    info.put("rows", result);
			String jsonString = JSON.toJSONString(info);
			//传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();	
		}if(method.equals("preEventSendInfo"))//预览
		{
			 String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			 VHouseWorkEventSendEnitiy wh=houseworkEventService.queryhouseWorkInfoByNo(event_no);
			 String jsonString = JSON.toJSONString(wh);
				//传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();	
			
		}
		if(method.equals("eventSendHandle"))//家政处理
		{
			 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String id = req.getParameter("id") == null ? "" : req.getParameter("id");
			 String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			 HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			 if(work==null)
			 {
				 work=new HouseworkEventEntity();
				 work.setId(id);
			 }
			 work.setEvent_state(HouseWorkTool.EVENTHANDLESTATE);
			 
			 String send_id = req.getParameter("send_id") == null ? "" : req.getParameter("send_id");
			 HouseWorkEventSendEntity send;
			 if(!send_id.equals(""))
			 {
			  send= houseworkEventSendService.queryHouseWorkSendInfo(send_id);
			 }else
			 {
			  send=new HouseWorkEventSendEntity();
			  send.setId(send_id);
			 }
			 String handle_content = req.getParameter("handle_content") == null ? "" : req.getParameter("handle_content");
			 String arrv_time = req.getParameter("arrv_time") == null ? "" : req.getParameter("arrv_time");
			 String houseKeepingPay = req.getParameter("houseKeepingPay") == null ? "" : req.getParameter("houseKeepingPay");
			 send.setHandle_content(handle_content);
			 if(arrv_time.equals("")){
			 send.setArrv_time(new Date());
			 }else
			 {
				try {
					send.setArrv_time(sd.parse(arrv_time));
				} catch (ParseException e) 
				{
					log.error("时间解析异常"+e.getMessage());
				} 
			 }
			 if(!("").equals(houseKeepingPay))
				{
					send.setHouseKeepingPay(Double.parseDouble(houseKeepingPay));
					
				}else
				{
					send.setHouseKeepingPay(0.00);	
				}
			 //状态写入历史纪录
			    EventSendEntity sendH=new EventSendEntity();
			    sendH.setHandle_time(send.getArrv_time());
				sendH.setOwnHandler(send.getOper_id());
				sendH.setEvent_id(event_no);
				String deptName=assignWorkService.getdept(send.getOper_id());
				String phone=assignWorkService.getHandlePhone(send.getOper_id());
				String dept_name=null==deptName?"":deptName;
				if(dept_name.equals(""))
				{
					sendH.setHandler_dept("系统");	
				}else{
				sendH.setHandler_dept(dept_name);
				}
				sendH.setHandler_phone(phone);
				
				sendH.setStatus(work.getEvent_state());
				send.setEvent_id(id);//事件ID
			 /**
			  * 开始流程
			  */
			 Map variables = new HashMap<String,Object>();
			 variables.put("flag", "2");
			 String processinstanceid = work.getBpm_processId()+"";
			 if(null != processinstanceid && processinstanceid.length()>0)
			 {
				grapService.completeHouseWorkBussniseTask(processinstanceid, user.getUsername(), variables,"3");//现场处理
				//grapService.completeHouseWorkBussniseTask(processinstanceid, "", null, "4");//模拟消单
			 }
			 
			 assignWorkService.addEventSend(sendH);
			 houseworkEventService.saveHouseWorkEvent(work);
			 boolean flag=houseworkEventService.saveHouseWorkSend(send);
		     
			 String msg="";
		     if(flag)
		     {
		    	 msg="success";
		     }else
		     {
		    	 msg="faild";
		     }
		     PrintWriter out = resp.getWriter();
			 out.print(JSON.toJSONString(msg));
			 out.flush();
		}
		if(method.equals("eventSendXiaoDan"))
		{
			String event_id = req.getParameter("id") == null ? "" : req.getParameter("id");
			String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			String send_id = req.getParameter("send_id") == null ? "" : req.getParameter("send_id");
		    
			HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			work.setEvent_state(HouseWorkTool.DEL_STATE);
		    HouseWorkEventSendEntity send= houseworkEventSendService.queryHouseWorkSendInfo(send_id);
			send.setDelete_time(new Date());
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
		    	 msg="success";
		     }else
		     {
		    	 msg="faild";
		     }
		     PrintWriter out = resp.getWriter();
			 out.print(JSON.toJSONString(msg));
			 out.flush();
		}
		
		if(method.equals("refuseEventSendOrder"))//物管拒单
		{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String event_id = req.getParameter("id") == null ? "" : req.getParameter("id");
			String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			String send_id = req.getParameter("send_id") == null ? "" : req.getParameter("send_id");
			String arrv_time = req.getParameter("arrv_time") == null ? "" : req.getParameter("arrv_time");
		
			HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			work.setEvent_state(HouseWorkTool.REFUSE_STATE);
			work.setOther("物管拒单");
		    HouseWorkEventSendEntity send= houseworkEventSendService.queryHouseWorkSendInfo(send_id);
		    if(send==null)
		    {
		    	send=new HouseWorkEventSendEntity();
		    	
		    }
			send.setDelete_time(new Date());
			 if(arrv_time.equals("")){
				 send.setArrv_time(new Date());
				 }else
				 {
					try {
						send.setArrv_time(sd.parse(arrv_time));
					} catch (ParseException e) 
					{
						log.error("时间解析异常"+e.getMessage());
					} 
				 }
			 
			 
			 
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
			 /**
			  * 开始流程
			  */
			 Map variables = new HashMap<String,Object>();
			 variables.put("flag", "3");//拒单
			 String processinstanceid = work.getBpm_processId()+"";
			 if(null != processinstanceid && processinstanceid.length()>0)
			 {
				 grapService.completeHouseWorkBussniseTask(processinstanceid, user.getUsername(), variables, "3"); 
				 grapService.completeHouseWorkBussniseTask(processinstanceid, user.getUsername(), null, null); 
			 }
			 
			 assignWorkService.addEventSend(sendH);
			 houseworkEventService.saveHouseWorkEvent(work);
			 boolean flag=houseworkEventService.saveHouseWorkSend(send);
		     
			 String msg="";
		     if(flag)
		     {
		    	 msg="工单："+event_no;
		     }else
		     {
		    	 msg="faild";
		     }
		     PrintWriter out = resp.getWriter();
			 out.print(JSON.toJSONString(msg));
			 out.flush();
		}
		if(method.equals("houseWorkDispatchSingle"))//测试业主端派工取消
		{
			 JSONObject hwrok = (JSONObject) JSON.parse("");
			// String event_no=(String) hwrok.get("event_no");
			 //String id=(String) hwrok.get("id");
			 //String arrv_time=(String) hwrok.get("arrv_time");
			 String arrv_time="";
			 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			 HouseworkEventEntity work=houseworkEventService.queryHouseWorkEventInfoByNo("IM20160512000004");
			 if(work==null)
			 {
				 work=new HouseworkEventEntity();
				 //work.setId(id);
			 }
			 work.setEvent_state("5");
			 work.setOther("派工环节业主取消工单");
			 HouseWorkEventSendEntity send;
			if(!work.getId().equals(""))
			 {
			  send= houseworkEventSendService.queryHouseWorkSendInfo(work.getId());
			 }else
			 {
			  send=new HouseWorkEventSendEntity();
			 }
			 send.setDelete_time(new Date());
			// String handle_content = req.getParameter("handle_content") == null ? "" : req.getParameter("handle_content");
			// String arrv_time = req.getParameter("arrv_time") == null ? "" : req.getParameter("arrv_time");
			 send.setHandle_content("业主消单原因或内容");//消单原因或内容
			/* if(arrv_time.equals("")){
			 send.setArrv_time(new Date());
			 }else
			 {
				try {
					send.setArrv_time(sd.parse(arrv_time));
				} catch (ParseException e) 
				{
					
				} 
			 }*/
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
		    	 msg="success";
		     }else
		     {
		    	 msg="faild";
		     }
			
			
		     PrintWriter out = resp.getWriter();
			 out.print(JSON.toJSONString(msg));
			 out.flush();
		}
		if(method.equals("deleteHouseWork"))
		{
			
			PrintWriter out = resp.getWriter();
			try{
				
				if(!StringUtil.isEmpty(workId)){
					JSONArray jsonArr = JSON.parseArray(workId);
					for (int i=0;i<jsonArr.size();i++){
						
						String houseWorkId = (String) jsonArr.get(i);
						houseworkEventService.deleteHouseWorkInfo(houseWorkId);
					}
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();
			
		}catch(Exception e){
			String jsonString = JSON.toJSONString("删除失败！");
			out.println(jsonString);
			out.flush();
		}
		}if(method.equals("preHouseWorkMessage"))
		{
			String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			HouseworkEventEntity hw=houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			String jsonString = JSON.toJSONString(hw);
				//传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();	
			
		}

	}

	@Override
	public String getUrl() {
		return "/houseWork/evnetsend";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}
	
	public  String getSimp(String time)
	{
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sd.parse(time);
		} catch (ParseException e) {
			log.error("时间解析异常"+e.getMessage());
		}
		String time1=sd.format(d);
		return time1;
		
	}

}
