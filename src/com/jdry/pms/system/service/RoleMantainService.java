package com.jdry.pms.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.Role;
import com.bstek.dorado.data.provider.Page;

@Repository
public interface RoleMantainService {

	public void queryRoles(Page<Role> page, Map<String, Object> parameter, String type);

	public Role getRoleById(String roleId);

	public void addRole(Role role);

	public void deleteRole(String roleId);

	public List<Role> getRoles(int rows);
}
