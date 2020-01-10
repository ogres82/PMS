package com.jdry.pms.chargeManager.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.dao.ChargeTypeRoomRelaDao;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRelaViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRoomRelaEntity;
import com.jdry.pms.chargeManager.service.ChargeTypeRoomRelaService;

@Repository
@Component
public class ChargeTypeRoomRelaImpl implements ChargeTypeRoomRelaService {

	@Resource
	private ChargeTypeRoomRelaDao dao;
	
	@Override
	public void queryAllFromView(Page<ChargeTypeRelaViewEntity> page, Map<String, Object> parameter){
		dao.queryAllFromView(page, parameter);
	}
	
	//Collection<ChargeTypeRoomRelaEntity> 
	@Override
	public void queryAll(Page<ChargeTypeRoomRelaEntity> page, Map<String, Object> parameter)
	{
		dao.queryAll(page, parameter);
	}
	
	
	@Override
	public void saveAll(ChargeTypeRoomRelaEntity chargeTypes){
		dao.saveAll(chargeTypes);
	}
	
	@Override
	public void delete(List<ChargeTypeRoomRelaEntity> chargeType){
		dao.delete(chargeType);
	}

	@Override
	public void addBatch(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		dao.addBatch(parameter);
	}

	@Override
	public List queryAllFromView() {
		// TODO Auto-generated method stub
		return dao.queryAllFromView();
	}

	@Override
	public boolean validatePropertyRela(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.validatePropertyRela(parameter);
	}

}
