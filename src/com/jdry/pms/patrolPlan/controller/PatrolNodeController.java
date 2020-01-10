package com.jdry.pms.patrolPlan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import com.jdry.pms.patrolPlan.pojo.PatrolNodeEntity;
import com.jdry.pms.patrolPlan.service.PatrolNodeService;

@Repository
@Component
public class PatrolNodeController implements IController {
	
	@Resource
	PatrolNodeService service;
	
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		// 获取前端的方法来 进行路由
		String method = arg0.getParameter("method");
		// 返回前端字符串
		String jsonString = "";
		PrintWriter out = arg1.getWriter();

		try {
			if (method.equalsIgnoreCase("list")) {
				Map<String,Object> parameter = new HashMap<String,Object>();
				List<PatrolNodeEntity> lists = service.queryPatrolNode(0, parameter);				
				jsonString = JSON.toJSONString(lists);
			}
			if (method.equals("save")) {
				PatrolNodeEntity pne = new PatrolNodeEntity();
				String patrolNodeId = null == arg0.getParameter("patrolNodeId") ? "" : arg0.getParameter("patrolNodeId").toString();
				String patrolNodeName = null == arg0.getParameter("patrolNodeName") ? "" : arg0.getParameter("patrolNodeName").toString();
				String patrolState = null == arg0.getParameter("patrolState") ? "" : arg0.getParameter("patrolState").toString();
				String operId = null == arg0.getParameter("operId") ? "" : arg0.getParameter("operId").toString();
				Date dt = new Date();
				long createTime = dt.getTime();
				if(!"".equals(patrolNodeId)){					
					pne.setPatrolNodeId(Integer.parseInt(patrolNodeId));
				}
				pne.setCreateTime(createTime);
				pne.setOperId(operId);
				pne.setPatrolNodeName(patrolNodeName);
				pne.setPatrolState(patrolState);
				service.savePatrolNode(pne);
				jsonString = JSON.toJSONString("保存成功！");
			}
			if (method.equalsIgnoreCase("delete")) {
				String patrolNodeIds = null == arg0.getParameter("id") ? "" : arg0.getParameter("id").toString();
				service.delPatrolNode(patrolNodeIds);
				jsonString = JSON.toJSONString("删除成功！");
			}

		} catch (Exception e) {
			jsonString = "操作失败，失败信息为：" + e.getMessage();
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
			out.close();
		}

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/patrolPlan/patrolNode";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
