package com.jdry.pms.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.jdry.pms.report.service.EmpChartService;

@Repository
@Component
public class EmpChartController implements IController{
	
	@Resource
	private EmpChartService service;
	
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
			
			if(method.equalsIgnoreCase("empSexChart")){

				List<Object> obj = service.empSexChart();
				if(obj!=null){
					String jsonString = JSON.toJSONString(obj);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("empDegreeChart")){

				List<Object> obj = service.empDegreeChart();
				if(obj!=null){
					String jsonString = JSON.toJSONString(obj);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("empAgeChart")){

				List<Object> obj = service.empAgeChart();
				if(obj!=null){
					String jsonString = JSON.toJSONString(obj);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("empDeptChart")){

				List<Object> obj = service.empDeptChart();
				if(obj!=null){
					String jsonString = JSON.toJSONString(obj);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("empWorkload")){

				List<Object> obj = service.empDeptChart();
				if(obj!=null){
					String jsonString = JSON.toJSONString(obj);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
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
		return "/empChart/empChartList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
	

}
