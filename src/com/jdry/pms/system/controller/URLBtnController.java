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
import com.bstek.bdf2.core.model.Url;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.system.pojo.RoleBtn;
import com.jdry.pms.system.service.URLBtnService;
import com.jdry.pms.system.service.UrlService;
@Repository
@Component
public class URLBtnController implements IController{
	
	@Resource
	URLBtnService roleService;
	
	@Resource
	DirectoryService directoryService;
	@Resource
	UrlService urlService;

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
		}else if(method.equals("urltree")){
			String roleId = null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();
			List<Url> list = urlService.loadMeunUrls(roleId);
			String jsonString = JSON.toJSONString(list);
			System.out.println(jsonString);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("btnlist")){
			String urlId = null == arg0.getParameter("urlId")?"":arg0.getParameter("urlId").toString();
			String roleId = null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();
			List result = roleService.loadBtnByUrl(urlId,roleId);
			List list = new ArrayList();
			for(int i=0;i<result.size();i++){
				Map map = new HashMap();
				Object[] obj = (Object[])result.get(i);
				map.put("id", obj[0]);
				map.put("urlId", obj[1]);
				map.put("btnId", obj[2]);
				map.put("btnName", obj[3]);
				map.put("order", obj[4]);
				map.put("roleId", obj[5]);
				list.add(map);
			}
			String jsonString = JSON.toJSONString(list);
//			System.out.println(jsonString);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("save")){
			PrintWriter out = arg1.getWriter();
			String btns = null == arg0.getParameter("btns")?"":arg0.getParameter("btns").toString();
			RoleBtn roleBtn = null;
			if(!StringUtil.isEmpty(btns)){
				List<RoleBtn> list = JSON.parseArray(btns, RoleBtn.class);
				for(int i=0;i<list.size();i++){
					roleBtn = list.get(i);
					if(roleBtn.getId() != null && !"".equals(roleBtn.getId())){
						if(roleBtn.isState()){
							System.out.println("角色"+roleBtn.getRoleId()+"什么都不做");
						}else{
							roleService.deleteRoleBtn(roleBtn.getId());
							System.out.println("角色"+roleBtn.getRoleId()+"---删除菜单"+roleBtn.getUrlId()+"----------"+roleBtn.getBtnId()+"权限");
						}
					}else{
						if(roleBtn.isState()){
							roleService.saveRoleBtn(roleBtn);
							System.out.println("角色"+roleBtn.getRoleId()+"---增加菜单"+roleBtn.getUrlId()+"----------"+roleBtn.getBtnId()+"权限");
						}else{
							System.out.println("角色"+roleBtn.getRoleId()+"什么都不做");
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
		return "/system/btnAuthorize";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
