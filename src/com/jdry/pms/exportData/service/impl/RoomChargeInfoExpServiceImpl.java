package com.jdry.pms.exportData.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.exportData.dao.RoomChargeInfoExpDao;
import com.jdry.pms.exportData.pojo.RoomChargeInfoExp;
import com.jdry.pms.exportData.service.RoomChargeInfoExpService;
@Repository
@Component
public class RoomChargeInfoExpServiceImpl implements RoomChargeInfoExpService {
	@Resource
	RoomChargeInfoExpDao dao;

	@Override
	public List<RoomChargeInfoExp> queryOwnerRoomInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryOwnerRoomInfo(parameter);
	}

}
