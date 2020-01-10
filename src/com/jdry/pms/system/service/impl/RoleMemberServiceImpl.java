package com.jdry.pms.system.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.dao.RoleMemberDao;
import com.jdry.pms.system.pojo.VBdf2RoleMember;
import com.jdry.pms.system.service.RoleMemberService;

@Repository
@Component
public class RoleMemberServiceImpl implements RoleMemberService{

	@Resource
	RoleMemberDao dao;
	
	@Override
	public void loadMembers(Page<VBdf2RoleMember> page, Map<String, Object> parameter) {
		dao.loadMembers(page,parameter);
	}

}
