package com.jdry.pms.ztree.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.ztree.pojo.HouseZtree;
import com.jdry.pms.ztree.service.ZtreeService;

@Repository
public class ZtreeController implements IController {
	@Resource
	private ZtreeService ztreeService;

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/ztree";
	}

	@Override
	public void execute(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String method = httpservletrequest.getParameter("method");
		String nodeLevel = httpservletrequest.getParameter("nodeLevel");
		String nodeId = httpservletrequest.getParameter("nodeId");
		httpservletrequest.setCharacterEncoding("utf-8");
		httpservletresponse.setCharacterEncoding("utf-8");
		httpservletresponse.setContentType("text/html; charset=utf-8");
		String jsonString = "";
		PrintWriter out = httpservletresponse.getWriter();

		try {
			if ("getHouseTree".equals(method)) {

				Map<String, Object> objects = new HashMap<String, Object>();
				if (!"".equals(nodeId) && nodeId != null) {
					objects.put("nodeId", nodeId);
				}
				if (!"".equals(nodeLevel) && nodeLevel != null) {
					objects.put("nodeLevel", nodeLevel);
				}
				String hql = " FROM HouseZtree WHERE 1=1";
				List<HouseZtree> lists = ztreeService.findEntityByHQL(hql, objects);
				jsonString = JSON.toJSONString(lists);
				System.out.print(jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
		}

	}

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
