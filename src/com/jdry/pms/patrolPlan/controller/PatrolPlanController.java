package com.jdry.pms.patrolPlan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanEntity;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanNodeEntity;
import com.jdry.pms.patrolPlan.service.PatrolPlanService;

@Repository
@Component
public class PatrolPlanController implements IController {
	@Resource
	PatrolPlanService service;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/patrolPlan/patrolPlan";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");

		// 获取前端的方法和参数
		String method = arg0.getParameter("method");
		String data = arg0.getParameter("data");
		String id = arg0.getParameter("id");
		// 返回前端字符串
		String jsonString = "";
		PrintWriter out = arg1.getWriter();
		try {
			if (method.equalsIgnoreCase("list")) {
				List<PatrolPlanEntity> lists = service.queryPatrolPlan();
				jsonString = JSON.toJSONString(lists);
			}
			if (method.equalsIgnoreCase("save")) {
				PatrolPlanEntity ppe = JSON.parseObject(data, PatrolPlanEntity.class);
				service.savePatrolPlan(ppe);
				jsonString = JSON.toJSONString("保存成功!");
			}
			if (method.equalsIgnoreCase("delete")) {
				service.delPatrolPlan(id);
				jsonString = JSON.toJSONString("删除成功！");
			}
			if (method.equalsIgnoreCase("insertNode")) {
				service.savePatrolPlanNode(data);
				jsonString = JSON.toJSONString("保存成功！");
			}
			if (method.equalsIgnoreCase("delNode")) {
				service.delPatrolPlanNode(id);
				jsonString = JSON.toJSONString("删除成功！");
			}
			if (method.equalsIgnoreCase("getPlanOfNode")) {
				String patrolPlanId = null == arg0.getParameter("patrolPlanId") ? "" : arg0.getParameter("patrolPlanId").toString();
				if (!"".equals(patrolPlanId)) {
					List<PatrolPlanNodeEntity> lists = service.queryPatrolPlanNode(Long.parseLong(patrolPlanId));
					jsonString = JSON.toJSONString(lists);
					System.out.print(jsonString);
				}
			}
			if (method.equalsIgnoreCase("insertPlan")) {
				PatrolPlanEntity ppe = JSON.parseObject(data, PatrolPlanEntity.class);
				service.savePatrolPlan(ppe);
				long patrolPlanId = ppe.getPatrolPlanId();
				jsonString = JSON.toJSONString(patrolPlanId);
				System.out.print(jsonString);
			}

		} catch (Exception e) {
			// TODO: handle exception
			jsonString = "操作失败，失败信息为：" + e.getMessage();
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
			out.close();
		}

	}

}
