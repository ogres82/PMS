package com.jdry.pms.msgandnotice.service;

import java.util.Collection;

import org.springframework.stereotype.Repository;
import com.bstek.bdf2.core.model.UserPosition;
@Repository
public interface UserPositionService {
	public Collection<UserPosition> findUserPosition() throws Exception;

}
