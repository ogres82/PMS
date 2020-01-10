package com.jdry.pms.chargeManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;
@Repository
public interface ChargeArrearViewService
{

	public void queryAll(Page<ChargeArrearViewEntity> page,Map<String, Object> parameter);
	public List<ChargeArrearViewEntity> queryAll();
		
}
