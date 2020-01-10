package com.jdry.pms.system.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.system.pojo.AppAuth;
import com.jdry.pms.system.pojo.AppUrl;
import com.jdry.pms.system.pojo.VAppAuth;
@Repository
public interface AppAuthService {
	
	public List<AppUrl> getAllUrl();
	public List loadUrlByRole(String roleId);
	public List<VAppAuth> getAppAuthByRole(String roleId);
	public void saveAppAuth(AppAuth appAuth);
	public void deleteAppAuth(String id);
}
