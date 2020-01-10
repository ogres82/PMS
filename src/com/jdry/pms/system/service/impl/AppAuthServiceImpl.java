package com.jdry.pms.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.system.dao.AppAuthDao;
import com.jdry.pms.system.pojo.AppAuth;
import com.jdry.pms.system.pojo.AppUrl;
import com.jdry.pms.system.pojo.VAppAuth;
import com.jdry.pms.system.service.AppAuthService;
@Repository
@Component
public class AppAuthServiceImpl implements AppAuthService {

	@Resource
	AppAuthDao dao;
	@Override
	public List<AppUrl> getAllUrl() {
		// TODO Auto-generated method stub
		return dao.getAllUrl();
	}
	@Override
	public List loadUrlByRole(String roleId) {
		// TODO Auto-generated method stub
		return dao.loadUrlByRole(roleId);
	}
	@Override
	public List<VAppAuth> getAppAuthByRole(String roleId) {
		// TODO Auto-generated method stub
		return dao.getAppAuthByRole(roleId);
	}
	@Override
	public void saveAppAuth(AppAuth appAuth) {
		// TODO Auto-generated method stub
		dao.saveAppAuth(appAuth);
	}
	@Override
	public void deleteAppAuth(String id) {
		// TODO Auto-generated method stub
		dao.deleteAppAuth(id);
	}

}
