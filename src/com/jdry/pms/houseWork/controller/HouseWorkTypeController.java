package com.jdry.pms.houseWork.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jdry.pms.houseWork.pojo.HouseworkType;
import com.jdry.pms.houseWork.service.HouseWorkTypeService;

/**
 * 家政服务管理项目
 * @author hezuping
 *
 */
@Repository
@Component
public class HouseWorkTypeController implements IController{

	
	@Resource
	HouseWorkTypeService houseWorkTypeService;
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException
	{
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("pragma","no-cache");
		resp.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		if(method.equals("listWorkType"))
		{
			Map<String, String> map=new HashMap<String, String>();
			List<HouseworkType> typeLs=houseWorkTypeService.queryType();
			String jsonString = JSON.toJSONString(typeLs);
			//传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();
		}
		
		
		
	}

	@Override
	public String getUrl() {
		
		return "/houseWork/workType";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
