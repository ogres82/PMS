package com.jdry.pms.exportData.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.exportData.pojo.OwnerRoomInfoExp;
@Repository
public interface OwnerRoomInfoExpServics {
	public List<OwnerRoomInfoExp> queryOwnerRoomInfo(Map<String, Object> parameter);
}
