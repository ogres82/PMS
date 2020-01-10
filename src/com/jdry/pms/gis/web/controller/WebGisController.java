package com.jdry.pms.gis.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.controller.IController;
@Component
public class WebGisController implements IController{

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg1.setContentType("text;html;charset=utf-8");
		arg1.setCharacterEncoding("utf-8");
		String clsName=arg0.getParameter("clsname");
		String methodName=arg0.getParameter("methodname");
		String data = arg0.getParameter("data");
		arg1.addHeader("Access-Control-Allow-Origin", "*"); 
		String result = "";
		PrintWriter pri=arg1.getWriter();
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
		return "/webgis";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
