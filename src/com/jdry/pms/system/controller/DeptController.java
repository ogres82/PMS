package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.business.IDept;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.view.dept.DeptMaintain;
import com.jdry.pms.system.service.DepartmentService;
@Repository
@Component
public class DeptController implements IController{
	
	@Resource
	DepartmentService service;
	
	@Resource
	DeptMaintain deptService;
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String parentId=arg0.getParameter("parentId");
		if(method.equals("load")){
			PrintWriter out = arg1.getWriter();
			try {
				List<IDept> depts = deptService.loadDepts(parentId);
				String b = JSON.toJSONString(depts);				
				out.println(b);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				String jsonString = JSON.toJSONString("操作失败！");
				out.println(jsonString);
				out.flush();
			}
		}else if(method.equals("save")){
		    String companyId = "jdry";
			PrintWriter out = arg1.getWriter();
			DefaultDept dept = new DefaultDept();
			String id = null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
			String name = null == arg0.getParameter("name")?"":arg0.getParameter("name").toString();
			String desc = null == arg0.getParameter("desc")?"":arg0.getParameter("desc").toString();
			String pId = null == arg0.getParameter("parentId")?"":arg0.getParameter("parentId").toString();
			dept.setId(id);
			if(!pId.isEmpty()){
				dept.setParentId(pId);
			}
			dept.setDesc(desc);
			dept.setName(name);
			dept.setCreateDate(new Date());
			dept.setCompanyId(companyId);
			service.saveDept(dept);
			String jsonString = JSON.toJSONString("操作成功！");
			out.println(jsonString);
			out.flush();
			
		}else if(method.equals("delete")){
			String id = null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
			PrintWriter out = arg1.getWriter();
			if(!id.isEmpty()){
				service.deleteDept(id);
				String jsonString = JSON.toJSONString("操作成功！");
				out.println(jsonString);
				out.flush();
			}
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/system/deptMaintain";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
