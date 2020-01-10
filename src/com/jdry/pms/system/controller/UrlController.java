package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.view.url.UrlMaintain;
import com.jdry.pms.system.service.URLBtnService;
import com.jdry.pms.system.service.UrlService;
@Repository
@Component
public class UrlController implements IController{
	
	@Resource
	UrlService service;
	
	@Resource
	URLBtnService urlBtnService;
	
	@Resource
	UrlMaintain urlService;
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
		String parentId=null == arg0.getParameter("parentId")?"":arg0.getParameter("parentId").toString();
		if(method.equals("urltree")){
			PrintWriter out = arg1.getWriter();
			try {
				List<Url> urls = service.loadAllBuildMeunUrls();
				String b = JSON.toJSONString(urls);				
				out.println(b);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				String jsonString = JSON.toJSONString("操作失败！");
				out.println(jsonString);
				out.flush();
			}
		}else if(method.equals("countChildren")){
			int order = urlService.countChildren(parentId);
			PrintWriter out = arg1.getWriter();
			out.println(order);
			out.flush();
		}else if(method.equals("save")){
			IUser user = ContextHolder.getLoginUser();
		    if (user == null) {
		      throw new NoneLoginException("Please login first");
		    }
		    String companyId = "jdry";
			PrintWriter out = arg1.getWriter();
			Url urlEntity = new Url();
			String id = null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
			String name = null == arg0.getParameter("name")?"":arg0.getParameter("name").toString();
			String url = null == arg0.getParameter("url")?"":arg0.getParameter("url").toString();
			String icon = null == arg0.getParameter("icon")?"":arg0.getParameter("icon").toString();
			String desc = null == arg0.getParameter("desc")?"":arg0.getParameter("desc").toString();
			String pId = null == arg0.getParameter("parentId")?"":arg0.getParameter("parentId").toString();
			String order = null == arg0.getParameter("order")?"":arg0.getParameter("order").toString();
			String forNavigation = null == arg0.getParameter("forNavigation")?"":arg0.getParameter("forNavigation").toString();
			if(order!=null && !order.isEmpty()){
				urlEntity.setOrder(Integer.parseInt(order));
			}
			if(forNavigation!=null && !forNavigation.isEmpty()){
				urlEntity.setForNavigation(Boolean.parseBoolean(forNavigation));
			}
			if(id.isEmpty()){
				urlEntity.setId(UUID.randomUUID().toString());
			}else{
				urlEntity.setId(id);
			}
			if(!pId.isEmpty()){
				urlEntity.setParentId(pId);
			}
			urlEntity.setDesc(desc);
			urlEntity.setIcon(icon);
			urlEntity.setName(name);
			urlEntity.setUrl(url);
			urlEntity.setCompanyId(companyId);
			service.saveUrl(urlEntity);
			urlService.refreshUrlCache();
			String jsonString = JSON.toJSONString("操作成功！");
			out.println(jsonString);
			out.flush();
			
		}else if(method.equals("delete")){
			String id = null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
			PrintWriter out = arg1.getWriter();
			if(!id.isEmpty()){
				service.deleteUrl(id);
				urlBtnService.deleteRoleUrlByUrlId(id);
				urlBtnService.deleteUrlBtnByUrlId(id);
				urlBtnService.deleteRoleBtnByUrlId(id);
				urlService.refreshUrlCache();
				String jsonString = JSON.toJSONString("操作成功！");
				out.println(jsonString);
				out.flush();
			}
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/system/urlMaintain";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
