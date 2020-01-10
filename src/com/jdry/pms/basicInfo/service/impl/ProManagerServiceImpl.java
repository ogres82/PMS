package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jdry.pms.basicInfo.dao.ProManagerDao;
import com.jdry.pms.basicInfo.pojo.VUser;
import com.jdry.pms.basicInfo.service.ProManagerService;

@Repository
@Transactional
public class ProManagerServiceImpl implements ProManagerService{

	@Resource
	ProManagerDao dao;
	@Override
	public String managerLogin(String username, String password) {
		// TODO Auto-generated method stub
		return dao.managerLogin(username, password);
	}
	@Override
	public String getSalt(String userName) {
		// TODO Auto-generated method stub
		return dao.getSalt(userName);
	}
	@Override
	public Collection<VUser> getPositionByName(String userName) {
		// TODO Auto-generated method stub
		return dao.getPositionByName(userName);
	}

}
