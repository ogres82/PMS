package com.jdry.pms.comm.util;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.comm.service.BusinessService;

@Component
public class CommDept {
	@Resource
	BusinessService businessService;
	
	@Resource(name=IUserService.BEAN_ID)
	IUserService iUserService;
	
	@DataProvider
	public Collection<DefaultDept> getAllDetp(String parentId){
		
		return businessService.getAllDetp(parentId);
	}
	
	@DataProvider
	public Collection<IUser> loadUsersByDeptId(String paramString){
		return iUserService.loadUsersByDeptId(paramString);
	}
}
