package com.jdry.pms.system.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.system.pojo.PrivilegeConfig;

@Repository
public interface PrivilegeConfigService {

	public void saveOrUpdateConfig(List<PrivilegeConfig> list);
	public List<PrivilegeConfig> loadPrivilegeConfig();
	public PrivilegeConfig loadPCByType(String type);
}
