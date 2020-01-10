package com.jdry.pms.environment.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.dao.CleaningAreaDutyDao;
import com.jdry.pms.environment.pojo.EnvironmentCleaningAreaDuty;
import com.jdry.pms.environment.service.CleaningAreaDutyService;

@Repository
@Component
public class CleaningAreaDutyServiceImpl implements CleaningAreaDutyService{
	
	@Resource
	CleaningAreaDutyDao dao;

	@Override
	public void queryAreaDutyByParam(Page<EnvironmentCleaningAreaDuty> page,
			Map<String, Object> parameter, String type) {
		dao.queryAreaDutyByParam(page,parameter,type);
	}

	@Override
	public EnvironmentCleaningAreaDuty getAreaDutyById(String planId) {
		
		return dao.getAreaDutyById(planId);
	}

	@Override
	public void addAreaDuty(EnvironmentCleaningAreaDuty areaDuty) {
		dao.addAreaDuty(areaDuty);
	}

	@Override
	public void deleteAreaDuty(String planId) {
		dao.deleteAreaDuty(planId);
	}

	@Override
	public Collection<EnvironmentCleaningAreaDuty> queryCheckRecByParam(
			Map<String, Object> param) {
		return dao.queryCheckRecByParam(param);
	}

	
	
}
