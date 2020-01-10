package com.jdry.pms.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.report.service.OwnerChartService;

@Repository
@Component
public class OwnerChartController implements IController{

	@Resource
	OwnerChartService service;
	
	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			
			if(method.equalsIgnoreCase("ownerInByRoomType")){

				List<Object> obj = service.ownerInByRoomType();
				List<Object> obj1 = service.ownerInByRoomState();
				if(obj!=null && obj1!=null){
					String jsonObj = JSON.toJSONString(obj);
					String jsonObj1 = JSON.toJSONString(obj1);
					String jsonString = "{'inC':"+jsonObj+",'outC':"+jsonObj1+"}";
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(jsonString));
					out.flush();
				}
			}
			
			if(method.equalsIgnoreCase("ownerInByCommunity")){

				List<Object> obj = service.communityAndRoom();
				List<Object> obj1 = service.communityAndIns();
				if(obj!=null && obj1!=null){
					String jsonObj = JSON.toJSONString(obj);
					String jsonObj1 = JSON.toJSONString(obj1);
					String jsonString = "{'line1':"+jsonObj+",'line2':"+jsonObj1+"}";
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(jsonString));
					out.flush();
				}
			}
			
			if(method.equalsIgnoreCase("ownerInByMonth")){

				List<Object> obj = service.ownerInByMonth();
				if(obj!=null){
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(obj));
					out.flush();
				}
			}
			
			if(method.equalsIgnoreCase("roomAndInRate")){

				List<Object> obj = service.roomAndInRate();
				if(obj!=null){
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(obj));
					out.flush();
				}
			}
			
			if(method.equalsIgnoreCase("ownerSexChart")){

				List<Object> obj = new ArrayList<Object>();
				String[] arr = {"9","男"};
				String[] arr1 = {"4","女"};
				obj.add(arr);
				obj.add(arr1);
				if(obj!=null){
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(obj));
					out.flush();
				}
			}
			
			if(method.equalsIgnoreCase("ownerAgeChart")){

				List<Object> obj = service.ownerAgeChart();
				if(obj!=null){
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(obj));
					out.flush();
				}
			}
			
			if(method.equalsIgnoreCase("ownerVipChart")){
				Random r = new Random();
				List<Object> obj = new ArrayList<Object>();
				List<Object> obj1 = new ArrayList<Object>();
				String[] name = {"高尔夫","健身俱乐部","KTV","酒吧"};
				for(int i=0;i<name.length;i++){
					String[] arr = {r.nextInt(100)+1+"",name[i]};
					obj.add(arr);
					String[] arr1 = {r.nextInt(100)+1+"",name[i]};
					obj1.add(arr1);
				}
				if(obj!=null && obj1!=null){
					String jsonObj = JSON.toJSONString(obj);
					String jsonObj1 = JSON.toJSONString(obj1);
					String jsonString = "{'line1':"+jsonObj+",'line2':"+jsonObj1+"}";
					
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(JSON.toJSONString(jsonString));
					out.flush();
				}
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/ownerChart/ownerChartList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
