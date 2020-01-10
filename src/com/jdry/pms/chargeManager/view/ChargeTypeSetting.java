package com.jdry.pms.chargeManager.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRelaViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRoomRelaEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingEntity;
import com.jdry.pms.chargeManager.service.ChargeTypeRoomRelaService;
import com.jdry.pms.chargeManager.service.ChargeTypeSettingService;


@Component
public class ChargeTypeSetting {
	
	@Resource
	private ChargeTypeSettingService service;
	@Resource
	private ChargeTypeRoomRelaService serviceRela;
	
	//收费项目的增删改查
	@DataProvider
	public void queryAll(Page<ChargeTypeSettingEntity> page, Map<String, Object> parameter, Criteria criteria)
	{

//		service.queryAll(page, parameter);

	}
	
	@DataResolver
    public void saveAll(Collection<ChargeTypeSettingEntity> chargeTypes){
		//service.saveAll(chargeTypes);
    }
	
	@DataResolver
	public void delete(ChargeTypeSettingEntity chargeType){
//		service.delete(chargeType);
	}
	
	
	//房间关联的收费项目的增删改查方法
	@DataProvider
	public void queryRelaAll(Page<ChargeTypeRoomRelaEntity> page, Map<String, Object> parameter, Criteria criteria)
	{
		serviceRela.queryAll(page, parameter);
	}
	@DataProvider
	public void queryAllFromView(Page<ChargeTypeRelaViewEntity> page, Map<String, Object> parameter){
		serviceRela.queryAllFromView(page, parameter);
	}
	
	@DataResolver
    public void saveRelaAll(Collection<ChargeTypeRoomRelaEntity> chargeTypes){
		
    }
	
	@DataResolver
	public void deleteRela(ChargeTypeRoomRelaEntity chargeType){
//		serviceRela.delete(chargeType);
	}
	
	@DataResolver
	public void addBatch(Map<String, Object> parameter){
		serviceRela.addBatch(parameter);
	}
		
	
}
