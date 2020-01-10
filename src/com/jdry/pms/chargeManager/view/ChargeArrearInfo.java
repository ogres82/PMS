package com.jdry.pms.chargeManager.view;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;


@Component
public class ChargeArrearInfo {
	
//	@Resource
//	private ChargeArrearViewService service;
	
	//收费项目的增删改查

//	@DataProvider
//	public Collection<ChargeArrearViewEntity> queryAll(Map<String, Object> parameter)
//	{
//		return service.queryAll(parameter);
//	}

	@DataProvider
	public Collection<ChargeArrearViewEntity> queryAll(Map<String, Object> parameter)
	{
		return null;
		//return service.queryAll(parameter);
	}

	
}
