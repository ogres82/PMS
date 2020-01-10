package com.jdry.pms.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.Role;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.dao.RoleMantainDao;
import com.jdry.pms.system.service.RoleMantainService;

@Repository
@Component
public class RoleMantainServiceImpl implements RoleMantainService{

	@Resource
	RoleMantainDao dao;
	
	@Override
	public void queryRoles(Page<Role> page, Map<String, Object> parameter,
			String type) {
		dao.queryRoles(page,parameter,type);
	}

	@Override
	public Role getRoleById(String roleId) {
		return dao.getRoleById(roleId);
	}

	@Override
	public void addRole(Role role) {
		dao.addRole(role);
	}

	@Override
	public void deleteRole(String roleId) {
		dao.deleteRole(roleId);
	}
	
	@Override
	public List<Role> getRoles(int rows) {
		return dao.getRoles(rows);
	}
}
