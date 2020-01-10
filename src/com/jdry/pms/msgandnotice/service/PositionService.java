package com.jdry.pms.msgandnotice.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
@Repository
public interface PositionService {
	public Collection<DefaultPosition> findPosition() throws Exception;
	
	public DefaultPosition findPositionById(String posId) throws Exception;

	public UserPosition findUserByPoId(String posId);

	public UserDept findUserDeptByUserId(String string);

	public List<UserPosition> findUserPositionsByPoId(String posId);


}
