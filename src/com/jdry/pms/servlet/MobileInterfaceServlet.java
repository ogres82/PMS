package com.jdry.pms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdry.pms.servlet.pojo.CustmerInfo;
import com.jdry.pms.servlet.util.Security;

public class MobileInterfaceServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException,ServletException{
		response.setContentType("text;html;charset=utf-8");
		String clsName=request.getParameter("clsname");
		String methodName=request.getParameter("methodname");
		String data = request.getParameter("data");
		String token=null==request.getParameter("token")?"":request.getParameter("token");
		String result = "";
		HttpSession session = request.getSession(true);
		PrintWriter pri=response.getWriter();
		if(Security.validInfo(clsName, methodName)){
			try {
				Class cls = Class.forName(clsName);
				Method method = cls.getDeclaredMethod(methodName,String.class);
				String bakResult = (String) method.invoke(cls.newInstance(),data);
				CustmerInfo cus=Security.getCustInfo(bakResult);
				if(cus.getPassword()!=""&&cus.getUsername()!="")
				{
					
					session.setAttribute("username", cus.getUsername());
					session.setAttribute("password", cus.getPassword());
					result=Security.getEncode(cus.getUsername(),cus.getPassword());

				}else
					result=bakResult;

			} catch (Exception e) {
				result="系统异常，请联系技术人员";
			}
		}else
		{//业务类返回口
			if(!token.equals(""))
			{     //验证临界值情况
				String key=Security.getReEncode(session.getAttribute("username").toString(),session.getAttribute("password").toString());
				if(Security.ValidPas(token, key))
				{
					try {
						Class cls = Class.forName(clsName);
						Method method = cls.getDeclaredMethod(methodName,String.class);
						result = (String) method.invoke(cls.newInstance(),data);

					} catch (Exception e)
					{
						result="系统异常，请联系技术人员";
					}

				}else
				{
					result="token 过期，重新登录";
				}

			}else
			{
				result="非法操作";
			}

		}
		pri.print(result);


	}

	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {

		response.setContentType("text;html;charset=utf-8");
		String clsName=request.getParameter("clsname");
		String methodName=request.getParameter("methodname");
		String data = request.getParameter("data");
		String token=null==request.getParameter("token")?"":request.getParameter("token");
		String result = "";
		HttpSession session = request.getSession(true);
		PrintWriter pri=response.getWriter();
		if(Security.validInfo(clsName, methodName)){
			try {
				Class cls = Class.forName(clsName);
				Method method = cls.getDeclaredMethod(methodName,String.class);
				String bakResult = (String) method.invoke(cls.newInstance(),data);
				CustmerInfo cus=Security.getCustInfo(bakResult);
				if(cus.getPassword()!=""&&cus.getUsername()!="")
				{
					session.setAttribute("username", cus.getUsername());
					session.setAttribute("password", cus.getPassword());
					result=Security.getEncode(cus.getUsername(),cus.getPassword());

				}else
					result=bakResult;

			} catch (Exception e) {
				result="系统异常，请联系技术人员";
			}
		}else
		{//业务类返回口
			if(!token.equals(""))
			{     //验证临界值情况
				String key=Security.getReEncode(session.getAttribute("username").toString(),session.getAttribute("password").toString());
				if(Security.ValidPas(token, key))
				{
					try {
						Class cls = Class.forName(clsName);
						Method method = cls.getDeclaredMethod(methodName,String.class);
						result = (String) method.invoke(cls.newInstance(),data);

					} catch (Exception e)
					{
						result="系统异常，请联系技术人员";
					}

				}else
				{
					result="token 过期，重新登录";
				}

			}else
			{
				result="非法操作";
			}

		}
		pri.print(result);
	}

}
