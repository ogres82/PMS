package com.jdry.pms.system.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.pojo.VBdf2RoleMember;

@Repository
public interface RoleMemberService {

	public void loadMembers(Page<VBdf2RoleMember> page, Map<String, Object> parameter);

}
