package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.basicInfo.pojo.PropertyOwner;

@Repository
public interface RoomSituationService {

	public Collection<PropertyOwner> queryRoomAndOwnerByJdbc(Map<String, String> parameter);

}
