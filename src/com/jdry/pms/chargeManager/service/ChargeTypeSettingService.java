package com.jdry.pms.chargeManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;

@Repository
public interface ChargeTypeSettingService
{

	public void queryAll(Page<ChargeTypeSettingViewEntity> page, Map<String, Object> parameter);
	
	public void saveAll(ChargeTypeSettingEntity chargeTypes);
	public void delete(List<ChargeTypeSettingEntity> chargeType);
	
	public List queryChargeTypeInfo(String keyword, String type);
	public List queryHourseOwnerInfo(String keyword);
	public List queryBelongUnitInfo(String keyword);
	public List queryStoriedBuildInfo(String keyword);
	
	public boolean validateTpyeNo(String type);
	
}
