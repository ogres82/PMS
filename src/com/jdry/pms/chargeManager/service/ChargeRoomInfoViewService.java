package com.jdry.pms.chargeManager.service;

import java.util.List;
import java.util.Map;

import com.jdry.pms.chargeManager.pojo.RoomVsFee;
import org.springframework.stereotype.Repository;

import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;

@Repository
public interface ChargeRoomInfoViewService {
	
	public List<ChargeRoomInfoViewEntity> queryAll(Map<String, Object> parameter);
	
	public List<Map<String,Object>> queryPayRec(Map<String, Object> parameter);
	
	public List<ChargeRoomInfoViewEntity> queryArrearage(Map<String, Object> parameter);

	public  List<RoomVsFee> getRoomOfChargeInfo(String roomId);
}
