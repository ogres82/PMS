package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.view.role.url.RoleUrlMaintain;
import com.jdry.pms.system.service.UrlService;

@Component
public class UrlPermissionsController implements IController{

	@Resource
	RoleUrlMaintain roleUrlMaintain;
	
	@Resource
	UrlService urlService;
	
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
				String roleId = null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();		
				List<Url> allUrls = urlService.loadAllMeunUrls();
				List<Url> roleUrls = urlService.loadRoleMeunUrls(roleId);
				Map<String,Object> map = new HashMap<String,Object>();
				List<Map<String,Object>> allTreeNode = new ArrayList<Map<String,Object>>();
				for(int i=0;i<allUrls.size();i++){
					Map<String,Object> treeNodes = new HashMap<String,Object>();
					Url url = allUrls.get(i);
					for(int j=0;j<roleUrls.size();j++){
						if(url.getId().equals(roleUrls.get(j).getId())){
							url.setUse(true);
							break;
						}
					}
					treeNodes.put("checked", url.isUse());
					map = new HashMap<String,Object>();
					map.put("checked", url.isUse());
					treeNodes.put("state", map);
					treeNodes.put("text", url.getName());
					treeNodes.put("href", url.getId());
					treeNodes.put("parentId", url.getParentId());
					if(url.getUrl()==null || url.getUrl().isEmpty()){
						treeNodes.put("selectable", false);
					}
					allTreeNode.add(treeNodes);
				}
				
				List<Map<String,Object>> treeView = this.buildTreeView(allTreeNode);
			    String data = JSON.toJSONString(treeView);
				PrintWriter out = arg1.getWriter();
				out.print(data);
				out.flush();
			}
			
			if(method.equals("save")){
				String roleId = null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();		
				String ids = null == arg0.getParameter("ids")?"":arg0.getParameter("ids").toString();		
				String[] strs= ids.split(",");
				Collection<String> id = new ArrayList<String>();
				String result = "false";
				for(int i=0;i<strs.length;i++){
					id.add(strs[i]);
				}
				try{
					roleUrlMaintain.saveRoleUrls(roleId, id);
					result = "true";
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.print(JSON.toJSONString(result));
					out.flush();
				}
				
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	private List<Map<String, Object>> buildTreeView(
			List<Map<String, Object>> allTreeNode) {
		Map<String, List<Map<String,Object>>> subUrl = new HashMap<String, List<Map<String,Object>>>();
		String rpId = "";
        for (Map<String,Object> r : allTreeNode) {
            rpId = r.get("parentId")==null?"root":r.get("parentId").toString();
            List<Map<String,Object>> childResourceList = subUrl.get(rpId);
            if (childResourceList == null) {
                childResourceList = new LinkedList<Map<String,Object>>();
            }
            childResourceList.add(r);
            subUrl.put(rpId, childResourceList);

        }
        String rId = "";
        List<Map<String,Object>> urlTree = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> r : allTreeNode) {
            rId = r.get("href").toString();
            r.put("nodes",subUrl.get(rId));
            if (r.get("parentId") == null) {
            	urlTree.add(r);
            }
        }
		return urlTree;
	}

	@Override
	public String getUrl() {
		return "/url/urlPermissions";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
