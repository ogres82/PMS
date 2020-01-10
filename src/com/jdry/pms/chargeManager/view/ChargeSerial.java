package com.jdry.pms.chargeManager.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeSerialEntity;
import com.jdry.pms.chargeManager.service.ChargeSerialService;


@Component
public class ChargeSerial {
	
	@Resource
	private ChargeSerialService service;
	
	//预收费的增删改查
	@DataProvider
	public void queryAll(Page<ChargeSerialEntity> page, Map<String, Object> parameter, Criteria criteria)
	{
//		service.queryAll(page, parameter);
	}
	
	@DataResolver
    public void saveAll(Collection<ChargeSerialEntity> chargeTypes){
//		service.saveAll(chargeTypes);
    }
	
	@DataResolver
	public void delete(ChargeSerialEntity chargeType){
		service.delete(chargeType);
	}
		
}
