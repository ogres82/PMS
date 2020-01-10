package com.jdry.pms.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.system.pojo.VBdf2User;

@Repository
public interface UserMaintainService {

	public List<VBdf2User> queryUserByParam(int rows);

	public DefaultUser getUserById(String username);

	public void deleteUser(String username);

	public void saveUser(DefaultUser duser);
	
	public VBdf2User getVBdf2UserById(String username);
	
	public List getDeptAndPos(); 
	
	public Map<String, Object> getUserDetailById(String userName);

}
