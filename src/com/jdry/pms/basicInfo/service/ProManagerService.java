package com.jdry.pms.basicInfo.service;

import java.util.Collection;

import org.springframework.stereotype.Repository;

@Repository
public interface ProManagerService {
	public String managerLogin(String userName,String password);
	
	public String getSalt(String userName);
	public Collection getPositionByName(String userName);

}
