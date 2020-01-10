package com.jdry.pms.environment.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.dao.PlantMtnRecDao;
import com.jdry.pms.environment.pojo.EnvironmentPlantMtnRec;
import com.jdry.pms.environment.pojo.VEnvironmentPlantMtnRec;
import com.jdry.pms.environment.service.PlantMtnRecService;

@Repository
@Component
public class PlantMtnRecServiceImpl implements PlantMtnRecService{
	
	@Resource
	PlantMtnRecDao dao;

	@Override
	public void queryPlantMtnRecByParam(Page<VEnvironmentPlantMtnRec> page,
			Map<String, Object> parameter, String type) {
		dao.queryPlantMtnRecByParam(page,parameter,type);
	}

	@Override
	public VEnvironmentPlantMtnRec getVPlantMtnRecById(String recId) {
		return dao.getVPlantMtnRecById(recId);
	}

	@Override
	public void addPlantMtnRec(EnvironmentPlantMtnRec mtnRec) {
		dao.addPlantMtnRec(mtnRec);
	}

	@Override
	public void deletePlantMtnRec(String recId) {
		dao.deletePlantMtnRec(recId);
	}

}
