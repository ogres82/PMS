package com.jdry.pms.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

 @Override
protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException{
	 
  System.out.println("调用doGet方法");
  response.setContentType("text;html;charset=utf-8");
  String clsName=request.getParameter("clsName");
  String methodName=request.getParameter("methodName");
  String data=request.getParameter("data");
  System.out.println("username="+clsName);
  System.out.println("password="+methodName);
  System.out.println("servlet test success....");
  String res = "";
  Map m = new HashMap();
  try {	
		Class cls = Class.forName(clsName);
		Method method = cls.getDeclaredMethod(methodName);
		m = (Map) method.invoke(cls.newInstance());
}   catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  
  OutputStream stream;
try {
	stream = response.getOutputStream();
	stream.write(data.getBytes("UTF-8"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//获取一个向Response对象写入数据的流,当tomcat服务器进行响应的时候，会将Response中的数据写给浏览器
	
 }

	  @Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
	     
	      PrintWriter out = response.getWriter();
	      out.println("The Parameter are ："+request.getParameter("name2"));
	  }
	
}