package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.system.pojo.SystemBtnUrl;
import com.jdry.pms.system.pojo.VSystemBtnUrl;
import com.jdry.pms.system.service.SystemBtnUrlService;

@Component
public class SystemBtnUrlController implements IController{

	@Resource
	SystemBtnUrlService service;
	
	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<VSystemBtnUrl> page =new Page<VSystemBtnUrl>(showCount, currentPage);
			    service.querySystemBtnUrlByPage(page, parameter);
				Collection<VSystemBtnUrl> result= page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			
			if(method.equals("save")){
				String id= null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
				String urlId= null == arg0.getParameter("urlId")?"":arg0.getParameter("urlId").toString();
				String urlName= null == arg0.getParameter("urlName")?"":arg0.getParameter("urlName").toString();
				String btnId= null == arg0.getParameter("btnId")?"":arg0.getParameter("btnId").toString();
				String btnName= null == arg0.getParameter("btnName")?"":arg0.getParameter("btnName").toString();
				String btnOrder= null == arg0.getParameter("btnOrder")?"":arg0.getParameter("btnOrder").toString();
				SystemBtnUrl btnUrl = new SystemBtnUrl();
				if(!id.isEmpty()){
					btnUrl.setId(id);
				}
				btnUrl.setUrlId(urlId);
				btnUrl.setUrlName(urlName);
				btnUrl.setBtnId(btnId);
				btnUrl.setBtnName(btnName);
				if(!btnOrder.isEmpty()){
					btnUrl.setOrder_(Integer.parseInt(btnOrder));
				}
				service.saveSystemBtnUrl(btnUrl);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteSystemBtnUrl")){
				String id= null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
				String[] ids = id.split(",");
				for(int i=0;i<ids.length;i++){
					service.deleteSystemBtnUrl(ids[i]);
				}
				String b = JSON.toJSONString("删除成功");				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("getPageList")){
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("keyword", keyword);
				List<Map<String,Object>> buildingProperty = service.getPageList(param);
				String jsonString = JSON.toJSONString(buildingProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/system/btnUrl";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
