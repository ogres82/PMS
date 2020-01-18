package com.jdry.pms.chargeManager.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.RoomVsFee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.chargeManager.dao.ChargeRoomInfoViewDao;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.service.ChargeRoomInfoViewService;


@Repository
@Component
public class ChargeRoomInfoViewImpl implements ChargeRoomInfoViewService {
	@Resource
	ChargeRoomInfoViewDao dao;
	
	@Override
	public List<ChargeRoomInfoViewEntity> queryAll(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryAll(parameter);
	}

	@Override
	public void queryAll(Page<ChargeRoomInfoViewEntity> page, Map<String, Object> parameter) throws Exception {
		dao.queryAll(page, parameter);
	}

	@Override
	public List<Map<String,Object>> queryPayRec(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryPayRec(parameter);
	}

	@Override
	public List<ChargeRoomInfoViewEntity> queryArrearage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryArrearage(parameter);
	}

	@Override
	public  List<RoomVsFee> getRoomOfChargeInfo(String roomId) {
		return dao.getRoomOfChargeInfo(roomId);
	}
}
