package com.jdry.pms.environment.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.dao.PlantDao;
import com.jdry.pms.environment.pojo.EnvironmentPlant;
import com.jdry.pms.environment.pojo.VEnvironmentPlant;
import com.jdry.pms.environment.service.PlantService;

@Repository
@Component
public class PlantServiceImpl implements PlantService{
	@Resource
	PlantDao dao;

	@Override
	public void queryPlantByParam(Page<VEnvironmentPlant> page,
			Map<String, Object> parameter, String type) {
		dao.queryPlantByParam(page,parameter,type);
	}

	@Override
	public VEnvironmentPlant getVEnvironmentPlantById(String plantId) {
		
		return dao.getVEnvironmentPlantById(plantId);
	}

	@Override
	public void addPlant(EnvironmentPlant plant) {
		dao.addPlant(plant);
	}

	@Override
	public void deletePlant(String plantId) {
		dao.deletePlant(plantId);
	}
	
	
}
