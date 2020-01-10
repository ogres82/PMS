package com.jdry.pms.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleResource;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.view.frame.main.MainFrame;
import com.bstek.bdf2.core.view.role.component.RoleComponentMaintain;
import com.jdry.pms.system.dao.URLBtnDao;
import com.jdry.pms.system.pojo.RoleBtn;
import com.jdry.pms.system.pojo.UrlBtn;
import com.jdry.pms.system.pojo.VRoleBtn;
import com.jdry.pms.system.pojo.VRoleUrl;
import com.jdry.pms.system.service.URLBtnService;
@Repository
@Component
public class URLBtnServiceImpl implements URLBtnService {
	@Resource
	URLBtnDao dao;
	@Resource
	RoleComponentMaintain service;
	@Resource
	MainFrame mainFrame;
	@Override
	public List<Role> loadAllRoles() {
		// TODO Auto-generated method stub
		return dao.loadAllRoles();
	}
	@Override
	public Url getUrl(String urlId) {
		// TODO Auto-generated method stub
		return dao.getUrl(urlId);
	}
	@Override
	public List<VRoleUrl> loadUrlByRole(Map<String,String> parameter) {
		// TODO Auto-generated method stub
		return dao.loadUrlByRole(parameter);
	}
	@Override
	public String getTreeData(String roleId) {
		// TODO Auto-generated method stub
		String data = "";
		String parentId = "";
		Map map3 = null;
		Map map2 = null;
		Map map1 = null;
		Map map = null;
		String id1 = "";
		String id2 = "";
		Map<String,String> para = new HashMap<String,String>();
		para.put("roleId", roleId);
		para.put("parentId", "");
		List<VRoleUrl> roleUrl = loadUrlByRole(para);
		List li1 = new ArrayList();
		if(roleUrl != null && roleUrl.size() != 0){
			int length1 = roleUrl.size();
			for(int i=0;i<length1;i++){
				if(roleUrl.get(i).getParentId()==null){
					VRoleUrl ru1 = roleUrl.get(i);
					map1 = new HashMap();
					map1.put("text", ru1.getName());
					map1.put("selectable", false);
					para.put("roleId", roleId);
					para.put("parentId", ru1.getUrlId());
					List<VRoleUrl> secUrl = loadUrlByRole(para);
					if(secUrl.size()>0){
						List li2 = new ArrayList();
						for(int j=0;j<secUrl.size();j++){
							VRoleUrl ru2 = secUrl.get(j);
							map2 = new HashMap();
							map2.put("text", ru2.getName());
							if(ru2.getUrl()!=null){
								map2.put("href", ru2.getUrlId());
							}else{
								map2.put("selectable", false);
							}
							para.put("roleId", roleId);
							para.put("parentId", secUrl.get(j).getUrlId());
							List<VRoleUrl> trdUrl = loadUrlByRole(para);
							if(trdUrl.size()>0){
								List li3 = new ArrayList();
								for(int k=0;k<trdUrl.size();k++){
									VRoleUrl ru3 = trdUrl.get(k);
									map3 = new HashMap();
									map3.put("text", ru3.getName());
									map3.put("href", ru3.getUrlId());
									li3.add(map3);
								}
								map2.put("nodes", li3);
								li2.add(map2);
							}else{
								li2.add(map2);
							}
						}
						map1.put("nodes", li2);
						li1.add(map1);
					}else{
						li1.add(map1);
					}
				}
			}
			data = JSON.toJSONString(li1);
			System.out.println(data);
		}else{
			data = JSON.toJSONString("该角色未进行URL授权");
		}
		return data;
	}
	@Override
	public List loadBtnByUrl(String urlId,String roleId) {
		// TODO Auto-generated method stub
		return dao.loadBtnByUrl(urlId,roleId);
	}
	@Override
	public void saveRoleBtn(RoleBtn roleBtn) {
		// TODO Auto-generated method stub
		dao.saveRoleBtn(roleBtn);
	}
	@Override
	public void deleteRoleBtn(String id) {
		// TODO Auto-generated method stub
		dao.deleteRoleBtn(id);
	}
	@Override
	public List<VRoleBtn> getAuthBtnByRole(String roleId,String url) {
		// TODO Auto-generated method stub
		return dao.getAuthBtnByRole(roleId, url);
	}
	/*
	 * 删除角色与url对应关系
	 */
	@Override
	public void deleteRoleUrlByUrlId(String urlId){
		List<RoleResource> list = dao.queryRoleUrlByUrlId(urlId);
		if(list != null){
			dao.deleteRoleUrl(list);
		}
	}
	/*
	 * 删除按钮与url对应关系
	 */
	@Override
	public void deleteUrlBtnByUrlId(String urlId){
		List<UrlBtn> list = dao.queryUrlBtnByUrlId(urlId);
		if(list != null){
			dao.deleteUrlBtn(list);
		}
	}
	/*
	 * 删除角色、url、按钮对应关系
	 */
	@Override
	public void deleteRoleBtnByUrlId(String urlId){
		List<RoleBtn> list = dao.queryRoleBtnUrlId(urlId);
		if(list != null){
			dao.deleteRoleBtn(list);
		}
	}

}
