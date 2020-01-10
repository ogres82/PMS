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
import com.jdry.pms.basicInfo.util.Security;
@Repository
@Component
public class AccessController implements IController{
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text;html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String clsName=request.getParameter("clsname");
		String methodName=request.getParameter("methodname");
		String data = request.getParameter("data");
		String token = request.getParameter("token");
		String result = "";
		PrintWriter pri=response.getWriter();
		JSONObject obj = JSON.parseObject(data);
		if(obj == null){
			obj = new JSONObject();
		}
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		url = url.replaceAll(uri, "");
		obj.put("url", url+path); //url:http://ip:port/projectName
		data = JSON.toJSONString(obj);
		if(Security.validInfo(clsName, methodName)){
			try {
				Class cls = Class.forName(clsName);
				Method method = cls.getDeclaredMethod(methodName,String.class);
				result = (String) method.invoke(cls.newInstance(),data);
			} catch (Exception e) {
				result="{\"status\":\"30\",\"message\":\"系统异常，请稍后再试\"}";
			}
		}else
		{//业务类返回口
			if(token != null && !token.equals("")){     
				try {
					if(Security.checkToken(token)){
							Class cls = Class.forName(clsName);
							Method method = cls.getDeclaredMethod(methodName,String.class);
							result = (String) method.invoke(cls.newInstance(),data);
							System.out.println(result);
					}else{
						result="{\"status\":\"10\",\"message\":\"会话过期，请重新登录！\"}";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result="{\"status\":\"30\",\"message\":\"系统异常，请稍后再试\"}";
				}

			}else{
				result="{\"status\":\"20\",\"message\":\"非法操作\"}";
			}

		}
		pri.print(result);
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/access";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}