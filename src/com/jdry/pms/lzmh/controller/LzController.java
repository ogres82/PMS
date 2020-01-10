package com.jdry.pms.lzmh.controller;

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
import com.jdry.pms.lzmh.service.LzmhService;
@Repository
@Component
public class LzController implements IController {
	@Resource
	LzmhService lzmhService;

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		String jsonString = "";
		PrintWriter out = arg1.getWriter();

		try {
			if (method.equalsIgnoreCase("userList")) {
				Map<String, Object> parameter = new HashMap<String, Object>();	
				List<Map<String,Object>> list = lzmhService.lzmhUserInfo(parameter);
				jsonString = JSON.toJSONString(list);
				System.out.print(jsonString);
			}
		} catch (Exception e) {
			// TODO: handle exception
			jsonString = JSON.toJSONString("操作失败！");
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/lzmh";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

}
