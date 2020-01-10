package com.jdry.pms.secManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.secManage.pojo.SecurityCalendarEvent;
import com.jdry.pms.secManage.pojo.SecurityEvent;
import com.jdry.pms.secManage.service.SecurityEventService;

@Repository
@Component
public class SecurityEventController  implements IController{


	@Resource
	private SecurityEventService service;
	@Resource
	CommUtil commUtil;
	@Override
	public boolean anonymousAccess() {
		
		return false;
	}
	
	public String getList(){
		List<Emp> result=service.queryAll(null, null);
		Map<Object, Object> info = new HashMap<Object, Object>();
	    info.put("empInfo", result);
		String b = JSON.toJSONString(info);
		b = b.substring(b.indexOf(":")+1, b.length()-1);
		return b;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String empId=null == arg0.getParameter("empId")?"":arg0.getParameter("empId").toString();//事件ID

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<SecurityEvent> page =new Page<SecurityEvent>(showCount, currentPage);
			    service.queryEventByParam(page, parameter, null);
			    List<SecurityEvent> result=(List<SecurityEvent>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("edit") || method.equals("viewEmp")){
				
				arg1.setContentType("application/json;charset=utf-8");
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent = service.getEventById(empId);
				String jsonString = JSON.toJSONString(securityEvent);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("add")){
				arg1.setContentType("application/json;charset=utf-8");
				String event_no = commUtil.getBusinessId("SJ","D");
				String result="{\"event_no\":\""+event_no+"\"}";
				PrintWriter out = arg1.getWriter();
				out.println(result);
				out.flush();
			}
			if(method.equals("save")){
				String eventId= null == arg0.getParameter("eventId")?"":arg0.getParameter("eventId").toString();				
				String eventCode= null == arg0.getParameter("eventCode")?"":arg0.getParameter("eventCode").toString();
				String eventTitle= null == arg0.getParameter("eventTitle")?"":arg0.getParameter("eventTitle").toString();
				String eventType= null == arg0.getParameter("eventType")?"":arg0.getParameter("eventType").toString();
				String eventHappenTime= null == arg0.getParameter("eventHappenTime")?"":arg0.getParameter("eventHappenTime").toString();
				String eventDetailText= null == arg0.getParameter("eventDetailText")?"":arg0.getParameter("eventDetailText").toString();
				String eventResultText= null == arg0.getParameter("eventResultText")?"":arg0.getParameter("eventResultText").toString();
				String eventRemarkText= null == arg0.getParameter("eventRemarkText")?"":arg0.getParameter("eventRemarkText").toString();
				String eventBurst= null == arg0.getParameter("eventBurst")?"":arg0.getParameter("eventBurst").toString();
				String eventChargerId= null == arg0.getParameter("eventChargerId")?"":arg0.getParameter("eventChargerId").toString();
				String eventRecTime= null == arg0.getParameter("eventRecTime")?"":arg0.getParameter("eventRecTime").toString();
				
				SecurityEvent se = new SecurityEvent();
				if(eventId !=null && !eventId.isEmpty()){
					se.setEventId(eventId);
				}
				se.setEventBurst(eventBurst);
				se.setEventChargerId(eventChargerId);
				se.setEventCode(eventCode);
				se.setEventDetail(eventDetailText);
				se.setEventHappenTime(DateUtil.convertStringToDate(eventHappenTime, "yyyy-MM-dd hh:mm:ss"));
				se.setEventRecTime(DateUtil.convertStringToDate(eventRecTime, "yyyy-MM-dd hh:mm:ss"));
				se.setEventRemark(eventRemarkText);
				se.setEventResult(eventResultText);
				se.setEventTitle(eventTitle);
				se.setEventType(eventType);
				service.addSecurityEvent(se);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("delete")){

//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(empId)){
						service.deleteEmp(empId);
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
			
			}else if(method.equalsIgnoreCase("initDropAll")){
				List<DirDirectoryDetail> positions=service.getDirectoryLikeCode("emp_status");
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equalsIgnoreCase("initDeptDrop")){
				Map<String, Object> parameter = new HashMap<String, Object>();
				List<DefaultDept> depts=(List<DefaultDept>) service.queryDept(parameter);
				String jsonString = JSON.toJSONString(depts);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equalsIgnoreCase("getEvents")){
			    String start=arg0.getParameter("start");
			    String end=arg0.getParameter("end");
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("start", start);
			    parameter.put("end", end);
			    Page<SecurityEvent> page =new Page<SecurityEvent>(2000, 1);
			    service.queryEventByParams(page, parameter, null);
			    List<SecurityEvent> result=(List<SecurityEvent>) page.getEntities();
			    List<SecurityCalendarEvent> rtnList=new ArrayList<SecurityCalendarEvent>();
			    for(SecurityEvent securityEvent:result){
			    	SecurityCalendarEvent securityCalendarEvent=new SecurityCalendarEvent();
			    	securityCalendarEvent.setId(securityEvent.getEventId());
			    	securityCalendarEvent.setTitle(securityEvent.getEventTitle());
			    	securityCalendarEvent.setStart(securityEvent.getEventHappenTime());
			    	rtnList.add(securityCalendarEvent);
			    }
				String b = JSON.toJSONString(rtnList);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(b);
				out.flush();
			}else if(method.equalsIgnoreCase("initPositionDrop")){
				Map<String, Object> parameter = new HashMap<String, Object>();
				List<DefaultPosition> positions=(List<DefaultPosition>) service.queryPosition(parameter);
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/secmanage/eventList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
