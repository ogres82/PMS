package com.jdry.pms.chargeManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRelaViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRoomRelaEntity;

@Repository
public interface ChargeTypeRoomRelaService
{
	public void queryAllFromView(Page<ChargeTypeRelaViewEntity> page, Map<String, Object> parameter);
	public List queryAllFromView();
	public void queryAll(Page<ChargeTypeRoomRelaEntity> page, Map<String, Object> parameter);
	
	public void saveAll(ChargeTypeRoomRelaEntity chargeTypes);
	public void delete(List<ChargeTypeRoomRelaEntity> chargeType);
	
	public void addBatch(Map<String, Object> parameter);
	public boolean validatePropertyRela(Map<String, Object> parameter);
	
}
