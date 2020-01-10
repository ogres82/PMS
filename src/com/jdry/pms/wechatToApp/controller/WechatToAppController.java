package com.jdry.pms.wechatToApp.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jdry.pms.wechatToApp.pojo.WechatMatterEntity;
import com.jdry.pms.wechatToApp.service.WechatToAppService;

@Repository
@Component
public class WechatToAppController implements IController {
	@Resource
	WechatToAppService service;

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
		String method = arg0.getParameter("method");
		String thumbMediaId = arg0.getParameter("thumbMediaId");
		String mediaId = arg0.getParameter("mediaId");
		String data = arg0.getParameter("data");
		PrintWriter out = arg1.getWriter();
		String jsonString = "";
		try {
			if (method.equals("list")) {
				List<WechatMatterEntity> List = service.getMatterInfo();
				jsonString = JSON.toJSONString(List);
			}
			if (method.equals("del")) {
				if (!"".equals(data)&&data!=null) {
					service.delMatterInfo(data);
				}
				jsonString = JSON.toJSONString("删除成功！");
			}
			if (method.equals("publish")) {			
			    service.setPublish(thumbMediaId,mediaId);		
				jsonString = JSON.toJSONString("发布成功！");
			}
			if (method.equals("syn")) {
				service.syncMatterInfo();
				jsonString = JSON.toJSONString("同步成功！");
			}
		} catch (Exception e) {
			jsonString = JSON.toJSONString("操作失败！");
			e.printStackTrace();
		} finally {
			out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/wechatToApp";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
