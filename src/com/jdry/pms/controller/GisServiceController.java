package com.jdry.pms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.controller.IController;
@Repository
@Component
public class GisServiceController implements IController{

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("text;html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String clsName=request.getParameter("clsname");
		String methodName=request.getParameter("methodname");
		String data = request.getParameter("data");
		response.addHeader("Access-Control-Allow-Origin", "*"); 
		String result = "";
		JSONObject obj = JSON.parseObject(data);
		if(obj == null){
			obj = new JSONObject();
		}
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		url = url.replaceAll(uri, "");
		obj.put("durl", url+path); //url:http://ip:port/projectName
		obj.put("url", url);
		data = JSON.toJSONString(obj);
		PrintWriter pri=response.getWriter();
		try {
			Class cls = Class.forName(clsName);
			Method method = cls.getDeclaredMethod(methodName,String.class);
			result = (String) method.invoke(cls.newInstance(),data);
		} catch (Exception e) {
			e.printStackTrace();
			result="{'status':'30','message':'系统异常，请稍后再试'}";
		}
		pri.print(result);
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/gis";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
