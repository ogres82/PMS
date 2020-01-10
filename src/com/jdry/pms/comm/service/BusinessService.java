package com.jdry.pms.comm.service;

import java.util.Collection;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.RoleMember;


public interface BusinessService {
	public String getBusinessId(String key,String type);
	public Collection<DefaultDept> getAllDetp(String parentId);
	public String findUserByUserName(String userName);
	public RoleMember getRoleMemberByUsername(String username);
}
