package com.jdry.pms.msgandnotice.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.UserPosition;
import com.jdry.pms.msgandnotice.dao.UserPositionDaoImpl;
import com.jdry.pms.msgandnotice.service.UserPositionService;
@Repository
@Component
public class UserPositionServiceImpl implements UserPositionService{
	@Resource
	UserPositionDaoImpl userPositionDao;
	@Override
	public Collection<UserPosition> findUserPosition() throws Exception {
		List<UserPosition> userPositions=(List<UserPosition>) userPositionDao.findUserPosition();
		return userPositions;
	}

}
