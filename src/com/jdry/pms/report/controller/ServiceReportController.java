package com.jdry.pms.report.controller;

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

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.report.service.ServiceReportService;
@Repository
@Component
public class ServiceReportController implements IController{
	@Resource
	ServiceReportService service;
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		PrintWriter out = arg1.getWriter();
		String method=arg0.getParameter("method");
		String date2 = null == arg0.getParameter("date2")?"":arg0.getParameter("date2").toString();
		String date1 = null == arg0.getParameter("date1")?"":arg0.getParameter("date1").toString();
		if(method.equals("history")){
			List list = service.getAllServiceInfo();
			if(list.size()>0){
				List list2 = new ArrayList();
				for(int i=0;i<list.size();i++){
					Map map = new HashMap();
					Object[] obj = (Object[])list.get(i);
					map.put("typeName", obj[0]);
					map.put("type", obj[1]);
					map.put("months", obj[2]);
					map.put("count", obj[3]);
					map.put("satisfy", obj[4]);
					list2.add(map);
				}
				
				String jsonString = JSON.toJSONString(list2);
				out.println(jsonString);
				out.flush();
			}
		}
		if(method.equals("getServiceInfo"))
		{
			List<Object> obj = service.getImpairInfo();
			List<Object> obj2 = service.getAssignWrokInfo();
			List<Object> obj3 = service.getHouseWorkInfo();
			//报障报修
			List<Object> obj4= service.getSatisfaction();
			//咨询建议
			List<Object> obj5= service.getZXSatisfaction();
			//家政服务
			List<Object> obj6= service.getJZSatisfaction();
			String jsonObj = JSON.toJSONString(obj);
			String jsonObj1 = JSON.toJSONString(obj2);
			String jsonObj2 = JSON.toJSONString(obj3);
			
			String jsonObj3 = JSON.toJSONString(obj4);
			String jsonObj4 = JSON.toJSONString(obj5);
			String jsonObj5 = JSON.toJSONString(obj6);
			String jsonString = "{'line1':"+jsonObj+",'line2':"+jsonObj1+",'line3':"+jsonObj2+",'line4':"+jsonObj3+",'line5':"+jsonObj4+",'line6':"+jsonObj5+"}";
			
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out1 = arg1.getWriter();
			out1.println(JSON.toJSONString(jsonString));
			out1.flush();
		}
		if(method.equals("eventAnalysis"))
		{
			List<Object> Bzobj = service.getEventAnalysis("BX");
			List<Object> Zxobj = service.getEventAnalysis("TS");
			List<Object> Jzobj = service.getEventAnalysis("IM");
			String jsonBxObj = JSON.toJSONString(Bzobj);
			String jsonZxObj = JSON.toJSONString(Zxobj);
			String jsonJzObj = JSON.toJSONString(Jzobj);
			String jsonString = "{'line1':"+jsonBxObj+",'line2':"+jsonZxObj+",'line3':"+jsonJzObj+"}";
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out1 = arg1.getWriter();
			out1.println(JSON.toJSONString(jsonString));
			out1.flush();
		}
		if(method.equals("eventSatisfaAnalysis"))
		{
			Object Fcobj = service.getEventSatisfaAnalysis("非常满意");
			Object Jbobj = service.getEventSatisfaAnalysis("基本满意");
			Object Bmobj = service.getEventSatisfaAnalysis("不满意");
			String jsonBxObj = JSON.toJSONString(Fcobj);
			String jsonZxObj = JSON.toJSONString(Jbobj);
			String jsonJzObj = JSON.toJSONString(Bmobj);
			String jsonString = "{'line1':"+jsonBxObj+",'line2':"+jsonZxObj+",'line3':"+jsonJzObj+"}";
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out1 = arg1.getWriter();
			out1.println(JSON.toJSONString(jsonString));
			out1.flush();
		}
		if(method.equals("eventResponse"))
		{
			List ls30 = service.getEventResponse(30);
			List ls60 = service.getEventResponse(60);
			List ls90 = service.getEventResponse(90);
			List ls120 = service.getEventResponse(120);
			List ls150 = service.getEventResponse(150);
			List ls180 = service.getEventResponse(180);
			String strBz30="";
			String strZx30="";
			String strJz30="";
			for(int i=0;i<ls30.size();i++)
			{
				Object[] obj=(Object[]) ls30.get(i);
				String num=null==obj[0]?"0":obj[0].toString();
				if(i==0)
				{
					strBz30="[0,0,0],[0,1,"+num+"]";	
				}else if(i==ls30.size()-1)
				{
					strJz30="[1,0,0],[1,1,"+num+"]";;
				}else
				{
					strZx30="[2,0,0],[2,1,"+num+"]";;	
				}
			}
			String strBz60="";
			String strZx60="";
			String strJz60="";
			for(int i=0;i<ls60.size();i++)
			{
				Object[] obj=(Object[]) ls60.get(i);
				String num=null==obj[0]?"0":obj[0].toString();
				if(i==0)
				{
					strBz60="[0,2,"+num+"]";	
				}else if(i==ls30.size()-1)
				{
					strJz60="[1,2,"+num+"]";;
				}else
				{
					strZx60="[2,2,"+num+"]";;	
				}
			}
			
			
			String strBz90="";
			String strZx90="";
			String strJz90="";
			for(int i=0;i<ls90.size();i++)
			{
				Object[] obj=(Object[]) ls90.get(i);
				String num=null==obj[0]?"0":obj[0].toString();
				if(i==0)
				{
					strBz90="[0,3,"+num+"]";	
				}else if(i==ls90.size()-1)
				{
					strJz90="[1,3,"+num+"]";;
				}else
				{
					strZx90="[2,3,"+num+"]";;	
				}
			}
			String strBz120="";
			String strZx120="";
			String strJz120="";
			for(int i=0;i<ls120.size();i++)
			{
				Object[] obj=(Object[]) ls120.get(i);
				String num=null==obj[0]?"0":obj[0].toString();
				if(i==0)
				{
					strBz120="[0,4,"+num+"]";	
				}else if(i==ls120.size()-1)
				{
					strJz120="[1,4,"+num+"]";;
				}else
				{
					strZx120="[2,4,"+num+"]";;	
				}
			}
			
			String strBz150="";
			String strZx150="";
			String strJz150="";
			for(int i=0;i<ls150.size();i++)
			{
				Object[] obj=(Object[]) ls150.get(i);
				String num=null==obj[0]?"0":obj[0].toString();
				if(i==0)
				{
					strBz150="[0,5,"+num+"]";	
				}else if(i==ls150.size()-1)
				{
					strJz150="[1,5,"+num+"]";;
				}else
				{
					strZx150="[2,5,"+num+"]";;	
				}
			}
			
			String strBz180="";
			String strZx180="";
			String strJz180="";
			for(int i=0;i<ls180.size();i++)
			{
				Object[] obj=(Object[]) ls180.get(i);
				String num=null==obj[0]?"0":obj[0].toString();
				if(i==0)
				{
					strBz180="[0,6,"+num+"]";	
				}else if(i==ls180.size()-1)
				{
					strJz180="[1,6,"+num+"]";;
				}else
				{
					strZx180="[2,6,"+num+"]";;	
				}
			}
			
			String str="["+strBz30+","+strBz60+","+strBz90+","+strBz120+","+strBz150+","+strBz180+"," +
					""+strZx30+","+strZx60+","+strZx90+","+strZx120+","+strZx150+","+strZx180+"," +
					""+strJz30+","+strJz60+","+strJz90+","+strJz120+","+strJz150+","+strJz180+"]";
			String jsonStr = JSON.toJSONString(str);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out1 = arg1.getWriter();
			out1.println(JSON.toJSONString(jsonStr));
			out1.flush();
			
		}
		if(method.equals("eventTimeAnalysis"))
		{
			//星期一
			List<Object> objls=service.getEventTimeAnalysis(1);
			String jsonStr=_EventHander.combQuery(objls,1);
			//星期二
			List<Object> objls2=service.getEventTimeAnalysis(2);
			String jsonStr2=_EventHander.combQuery(objls2,2);
			//星期三
			List<Object> objls3=service.getEventTimeAnalysis(3);
			String jsonStr3=_EventHander.combQuery(objls3,3);
			//星期四
			List<Object> objls4=service.getEventTimeAnalysis(4);
			String jsonStr4=_EventHander.combQuery(objls4,4);
			//星期五
			List<Object> objls5=service.getEventTimeAnalysis(5);
			String jsonStr5=_EventHander.combQuery(objls5,5);
			//星期六
			List<Object> objls6=service.getEventTimeAnalysis(6);
			String jsonStr6=_EventHander.combQuery(objls6,6);
			//星期
			List<Object> objls7=service.getEventTimeAnalysis(0);
			String jsonStr7=_EventHander.combQuery(objls7,0);
			
			String str="["+jsonStr+","+jsonStr2+","+jsonStr3+","+jsonStr4+","+jsonStr5+","+jsonStr6+","+jsonStr7+"]";
			String jsonOutStr = JSON.toJSONString(str);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out1 = arg1.getWriter();
			out1.println(JSON.toJSONString(jsonOutStr));
			out1.flush();
			
		}
	}

	@Override
	public String getUrl() {
		return "/report/serviceReport";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
