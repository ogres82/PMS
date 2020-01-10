package com.jdry.pms.chargeManager.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.dao.ChargeSerialDao;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.service.ChargeSerialService;

@Repository
@Component
public class ChargeSerialImpl implements ChargeSerialService {

	@Resource
	private ChargeSerialDao dao;
	
	//Collection<ChargeTypeSettingEntity> 
	@Override
	public void queryAll(Page<ChargeSerialViewEntity> page, Map<String, Object> parameter)
	{
		dao.queryAll(page, parameter);
	}
	
	
	@Override
	public void save(ChargeSerialEntity chargeTypes){
		dao.save(chargeTypes);
	}
	
	@Override
	public void delete(ChargeSerialEntity chargeType){
		dao.delete(chargeType);
	}
	
	@Override
	public String paid(Map<String, Object> parameter, List<ChargeSerialEntity> chargeInfos){
		return dao.paid(parameter, chargeInfos);
	}


	@Override
	public List<ChargeSerialViewEntity> getPaidHostory(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.getPaidHostory(parameter);
	}


	@Override
	public boolean validateOrderId(String orderId) {
		// TODO Auto-generated method stub
		return dao.validateOrderId(orderId);
	}


	@Override
	public boolean paidFromApp(Map<String, Object> parameter, List<ChargeRoomInfoViewEntity> infos) {
		// TODO Auto-generated method stub
		return dao.paidFromApp(parameter,infos);
	}


	@Override
	public boolean toPrestore(ChargeSerialEntity serial) {
		// TODO Auto-generated method stub
		return dao.toPrestore(serial);
	}


	@Override
	public boolean storeFromApp(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.storeFromApp(parameter);
	}


	@Override
	public List chargingItem(String room_id) {
		return dao.chargingItem(room_id);
	}

}
