package com.jdry.pms.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.Url;
import com.jdry.pms.system.pojo.RoleBtn;
import com.jdry.pms.system.pojo.VRoleBtn;
import com.jdry.pms.system.pojo.VRoleUrl;
@Repository
public interface URLBtnService {

	public List<Role> loadAllRoles();
	public Url getUrl(String urlId);
	public List<VRoleUrl> loadUrlByRole(Map<String,String> parameter);
	public String getTreeData(String roleId);
	public List loadBtnByUrl(String urlId,String roleId);
	public void saveRoleBtn(RoleBtn roleBtn);
	public void deleteRoleBtn(String id);
	public List<VRoleBtn> getAuthBtnByRole(String roleId,String url);
	public void deleteRoleUrlByUrlId(String urlId);
	public void deleteUrlBtnByUrlId(String urlId);
	public void deleteRoleBtnByUrlId(String urlId);
}
