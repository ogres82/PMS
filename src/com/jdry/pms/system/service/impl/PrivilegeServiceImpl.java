package com.jdry.pms.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.system.dao.PrivilegeConfigDao;
import com.jdry.pms.system.pojo.PrivilegeConfig;
import com.jdry.pms.system.service.PrivilegeConfigService;
@Repository
@Component
public class PrivilegeServiceImpl implements PrivilegeConfigService {

	@Resource
	PrivilegeConfigDao dao;
	@Override
	public void saveOrUpdateConfig(List<PrivilegeConfig> list) {
		// TODO Auto-generated method stub
		dao.saveOrUpdateConfig(list);
	}
	@Override
	public List<PrivilegeConfig> loadPrivilegeConfig() {
		// TODO Auto-generated method stub
		return dao.loadPrivilegeConfig();
	}
	@Override
	public PrivilegeConfig loadPCByType(String type) {
		// TODO Auto-generated method stub
		return dao.loadPCByType(type);
	}

}
