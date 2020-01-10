package com.jdry.pms.chargeManager.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.dao.ChargeArrearViewDao;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;
import com.jdry.pms.chargeManager.service.ChargeArrearViewService;

@Repository
@Component
public class ChargeArrearViewImpl implements ChargeArrearViewService {

	@Resource
	private ChargeArrearViewDao dao;
	
	//Collection<ChargeTypeSettingEntity> 
	@Override
	public void queryAll(Page<ChargeArrearViewEntity> page,Map<String, Object> parameter)
	{
		dao.queryAll(page,parameter);
	}

	@Override
	public List<ChargeArrearViewEntity> queryAll() {
		// TODO Auto-generated method stub
		return dao.queryAll();
	}	

}
