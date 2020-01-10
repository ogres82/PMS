package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.basicInfo.dao.RoomSituationDao;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.service.RoomSituationService;

@Repository
@Component
public class RoomSituationServiceImpl implements RoomSituationService{
    @Resource
    RoomSituationDao dao;

	@Override
	public Collection<PropertyOwner> queryRoomAndOwnerByJdbc(Map<String, String> parameter) {
	
		return dao.queryRoomAndOwnerByJdbc(parameter);
	}
}
