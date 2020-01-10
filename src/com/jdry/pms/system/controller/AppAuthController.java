package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.Role;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.system.pojo.AppAuth;
import com.jdry.pms.system.service.AppAuthService;
import com.jdry.pms.system.service.URLBtnService;
@Repository
@Component
public class AppAuthController implements IController{
	
	@Resource
	URLBtnService roleService;
	
	@Resource
	AppAuthService appAuthService;
	
	@Resource
	DirectoryService directoryService;

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
		
		if(method.equals("rolelist")){
			List<Role> result = roleService.loadAllRoles();
			String jsonString = JSON.toJSONString(result);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("urllist")){
			String roleId = null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();
			List result = appAuthService.loadUrlByRole(roleId);
			List list2 = new ArrayList();
			for(int i=0;i<result.size();i++){
				Map map = new HashMap();
				Object[] obj = (Object[])result.get(i);
				map.put("id", obj[0]);
				map.put("roleId", obj[1]);
				map.put("urlId", obj[2]);
				map.put("urlName", obj[3]);
				map.put("name", obj[4]);
				map.put("orderNo", obj[5]);
				list2.add(map);
			}
			PrintWriter out = arg1.getWriter();
			String jsonString = JSON.toJSONString(list2);
			out.println(jsonString);
			out.flush();
		}else if(method.equals("save")){
			PrintWriter out = arg1.getWriter();
			String urls = null == arg0.getParameter("urls")?"":arg0.getParameter("urls").toString();
			AppAuth aa = null;
			if(!StringUtil.isEmpty(urls)){
				List<AppAuth> list = JSON.parseArray(urls, AppAuth.class);
				for(int i=0;i<list.size();i++){
					aa = list.get(i);
					if(aa.getId() != null && !"".equals(aa.getId())){
						if(aa.isState()){
							System.out.println("角色"+aa.getRoleId()+"什么都不做");
						}else{
							appAuthService.deleteAppAuth(aa.getId());
							System.out.println("角色"+aa.getRoleId()+"---删除菜单"+aa.getUrlId());
						}
					}else{
						if(aa.isState()){
							appAuthService.saveAppAuth(aa);
							System.out.println("角色"+aa.getRoleId()+"---增加菜单"+aa.getUrlId());
						}else{
							System.out.println("角色"+aa.getRoleId()+"什么都不做");
						}
					}
				}
				
			}
			String jsonString = JSON.toJSONString("操作成功！");
			out.println(jsonString);
			out.flush();
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/system/appAuthorize";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
