package com.jdry.pms.assignWork.controller;

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
import com.jdry.pms.assignWork.pojo.UserDeptEntitiy;
import com.jdry.pms.assignWork.service.DispatchPersonService;

@Repository
@Component
public class DispatchPersonController  implements IController{

	@Resource
	DispatchPersonService dispatchPersonService;
	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException 
	{
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String method=null == req.getParameter("method")?"":req.getParameter("method").toString();//方法
		if(method.equals("engine"))
		{
	  // 每页行数
	  String search=java.net.URLDecoder.decode(req.getParameter("search") , "UTF-8");
	  if(search.equals("undefined"))
	    {
	    	search="";
	    }
	    Map<String, Object> parameter = new HashMap();
	    parameter.put("search", search);
		List<UserDeptEntitiy> res=new ArrayList<UserDeptEntitiy>();
		List ls=dispatchPersonService.queryAllHandler();
		for(int i=0;i<ls.size();i++)
		{
			UserDeptEntitiy ud=new UserDeptEntitiy();
			Object[] obj = (Object[]) ls.get(i);
			String userName=null==obj[0]?"":obj[0].toString();
			ud.setUserName(userName);
			String cname=null==obj[1]?"":obj[1].toString();
			ud.setCname(cname);
			String dept_id=null==obj[2]?"":obj[2].toString();
			ud.setDept_id(dept_id);
			String dept_name=null==obj[3]?"":obj[3].toString();
			ud.setDept_name(dept_name);
			String deption_id=null==obj[4]?"":obj[4].toString();
			ud.setDeption_id(deption_id);
			String deption_name=null==obj[5]?"":obj[5].toString();
			ud.setDeption_name(deption_name);
			String work_state=null==obj[6]?"":obj[6].toString();
			ud.setWork_state(work_state);
			String work_times=null==obj[7]?"":obj[7].toString();
			ud.setWork_times(work_times);
			res.add(ud);
			
			
		}
		String jsonString = JSON.toJSONString(res);
		//传输JSON
		PrintWriter out = resp.getWriter();
		out.println(jsonString);
		out.flush();	
			
			
			
			
		}
	}

	@Override
	public String getUrl()
	{
		return "/assingwork/personControl";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}

}
