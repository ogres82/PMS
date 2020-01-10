package com.jdry.pms.environment.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentCleaningAreaDuty;

@Repository
public interface CleaningAreaDutyService {

	public void queryAreaDutyByParam(Page<EnvironmentCleaningAreaDuty> page,
			Map<String, Object> parameter, String type);

	public EnvironmentCleaningAreaDuty getAreaDutyById(String planId);

	public void addAreaDuty(EnvironmentCleaningAreaDuty areaDuty);

	public void deleteAreaDuty(String planId);

	public Collection<EnvironmentCleaningAreaDuty> queryCheckRecByParam(Map<String, Object> param);

}
