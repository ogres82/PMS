package com.jdry.pms.patrolPlan.controller;

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
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.patrolPlan.pojo.PlanNodeRecEntity;
import com.jdry.pms.patrolPlan.service.PatrolNodeRecService;
@Repository
@Component
public class PatrolNodeRecController implements IController {
	@Resource
	PatrolNodeRecService service;

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
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));// 每页行数
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				String search = java.net.URLDecoder.decode(arg0.getParameter("search"), "UTF-8");
				if (search.equals("undefined")) {
					search = "";
				}
				String planIdSearch = arg0.getParameter("planIdSearch") == null ? "" : arg0.getParameter("planIdSearch");
				String nodeIdSearch = arg0.getParameter("nodeIdSearch") == null ? "" : arg0.getParameter("nodeIdSearch");
				String userIdSearch = arg0.getParameter("userIdSearch") == null ? "" : arg0.getParameter("userIdSearch");
				String insertDateSearch = arg0.getParameter("insertDateSearch") == null ? "" : arg0.getParameter("insertDateSearch");

				currentPage += 1;
				Map<String, Object> parameter = new HashMap();

				parameter.put("search", search);
				parameter.put("planIdSearch", planIdSearch);
				parameter.put("nodeIdSearch", nodeIdSearch);
				parameter.put("userIdSearch", userIdSearch);
				parameter.put("insertDateSearch", insertDateSearch);

				Page<PlanNodeRecEntity> page = new Page<PlanNodeRecEntity>(showCount, currentPage);
				service.queryNodeRec(page, parameter, null);
				List<PlanNodeRecEntity> result = (List<PlanNodeRecEntity>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数
				jsonString = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
			
			}
		} catch (Exception e) {
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
		return "/patrolPlan/patrolNodeRec";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
