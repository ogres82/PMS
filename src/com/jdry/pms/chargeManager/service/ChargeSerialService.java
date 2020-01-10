package com.jdry.pms.chargeManager.service;


import java.util.List;
import java.util.Map;

import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
@Repository
public interface ChargeSerialService
{

	public void queryAll(Page<ChargeSerialViewEntity> page, Map<String, Object> parameter);
	
	public void save(ChargeSerialEntity chargeTypes);
	public void delete(ChargeSerialEntity chargeType);
	public String paid(Map<String, Object> parameter, List<ChargeSerialEntity> chargeInfos);
	public List<ChargeSerialViewEntity> getPaidHostory(Map<String, Object> parameter);
	public boolean validateOrderId(String orderId);
	public boolean paidFromApp(Map<String, Object> parameter, List<ChargeRoomInfoViewEntity> infos);
	public boolean toPrestore(ChargeSerialEntity serial);
	public boolean storeFromApp(Map<String, Object> parameter);
	public List chargingItem(String room_id);
}
