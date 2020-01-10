package com.jdry.pms.basicInfo.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.service.RoomSituationService;

@Component
public class RoomSituationView {

	@Resource
	RoomSituationService service;
	
	@DataProvider
	public Collection<PropertyOwner> queryRoomAndOwnerByJdbc(Map<String, String> parameter){
		return service.queryRoomAndOwnerByJdbc(parameter);
	}
}
