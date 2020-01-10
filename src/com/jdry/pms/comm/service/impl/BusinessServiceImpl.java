package com.jdry.pms.comm.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.RoleMember;
import com.jdry.pms.comm.dao.BusinessDao;
import com.jdry.pms.comm.service.BusinessService;

@Component
public class BusinessServiceImpl implements BusinessService {

	@Resource
	BusinessDao businessDao;
	
	@Override
	public String getBusinessId(String key,String type) {
		// TODO Auto-generated method stub
		return businessDao.getBusinessId(key,type);
	}

	@Override
	public Collection<DefaultDept> getAllDetp(String parentId) {
		// TODO Auto-generated method stub
		return businessDao.getAllDetp(parentId);
	}

	@Override
	public String findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		String cName = "";
		List<DefaultUser> defaultUsers= (List<DefaultUser>) businessDao.findUserByUserName(userName);
		if(null != defaultUsers && defaultUsers.size()>0){
			DefaultUser defaultUser = defaultUsers.get(0);
			if(null == defaultUser){
				defaultUser = new DefaultUser();
			}
			cName = defaultUser.getCname();
		}else{
			 
		}
		return cName;
	}

	@Override
	public RoleMember getRoleMemberByUsername(String username) {
		
		return businessDao.getRoleMemberByUsername(username);
	}

}
