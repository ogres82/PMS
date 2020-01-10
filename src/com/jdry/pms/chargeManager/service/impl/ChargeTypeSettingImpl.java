package com.jdry.pms.chargeManager.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.dao.ChargeTypeSettingDao;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;
import com.jdry.pms.chargeManager.service.ChargeTypeSettingService;

@Repository
@Component
public class ChargeTypeSettingImpl implements ChargeTypeSettingService {

	@Resource
	private ChargeTypeSettingDao dao;
	
	//Collection<ChargeTypeSettingEntity> 
	@Override
	public void queryAll(Page<ChargeTypeSettingViewEntity> page, Map<String, Object> parameter)
	{
		dao.queryAll(page, parameter);
	}
	
	
	@Override
	public void saveAll(ChargeTypeSettingEntity chargeTypes){
		dao.saveAll(chargeTypes);
	}
	
	@Override
	public void delete(List<ChargeTypeSettingEntity> chargeType){
		dao.delete(chargeType);
	}


	@Override
	public List queryChargeTypeInfo(String keyword, String type) {
		// TODO Auto-generated method stub
		return dao.queryChargeTypeInfo(keyword,type);
	}


	@Override
	public List queryHourseOwnerInfo(String keyword) {
		// TODO Auto-generated method stub
		return dao.queryHourseOwnerInfo(keyword);
	}


	@Override
	public List queryBelongUnitInfo(String keyword) {
		// TODO Auto-generated method stub
		return dao.queryBelongUnitInfo(keyword);
	}


	@Override
	public List queryStoriedBuildInfo(String keyword) {
		// TODO Auto-generated method stub
		return dao.queryStoriedBuildInfo(keyword);
	}


	@Override
	public boolean validateTpyeNo(String type) {
		// TODO Auto-generated method stub		
		return dao.validateTpyeNo(type);
	}

}
