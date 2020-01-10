package com.jdry.pms.exportData.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.exportData.pojo.RoomChargeInfoExp;
@Repository
public interface RoomChargeInfoExpService {
	public List<RoomChargeInfoExp> queryOwnerRoomInfo(Map<String, Object> parameter);
}
