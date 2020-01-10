package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.jdry.pms.system.service.PosService;
@Repository
@Component
public class PositionController implements IController{
	
	@Resource
	PosService service;
	
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
		if(method.equals("load")){
			PrintWriter out = arg1.getWriter();
			try {
				List<DefaultPosition> positions =  service.loadPosition();
				String b = JSON.toJSONString(positions);				
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
			DefaultPosition position = new DefaultPosition();
			String id = null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
			String name = null == arg0.getParameter("name")?"":arg0.getParameter("name").toString();
			String desc = null == arg0.getParameter("desc")?"":arg0.getParameter("desc").toString();
			if(id!=null && !id.isEmpty()){
				position.setId(id);
			}
			position.setDesc(desc);
			position.setName(name);
			position.setCreateDate(new Date());
			position.setCompanyId(companyId);
			service.savePosition(position);
			String jsonString = JSON.toJSONString("操作成功！");
			out.println(jsonString);
			out.flush();
			
		}else if(method.equals("delete")){
			String ids = null == arg0.getParameter("ids")?"":arg0.getParameter("ids").toString();
			PrintWriter out = arg1.getWriter();
			try{
				if(!StringUtil.isEmpty(ids)){
					service.deletePosition(ids);
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			}catch(Exception e){
				e.printStackTrace();
				String jsonString = JSON.toJSONString("删除失败！");
				out.println(jsonString);
				out.flush();
			}
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/system/posMaintain";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
