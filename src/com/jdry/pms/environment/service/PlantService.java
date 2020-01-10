package com.jdry.pms.environment.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentPlant;
import com.jdry.pms.environment.pojo.VEnvironmentPlant;

@Repository
public interface PlantService {

	public void queryPlantByParam(Page<VEnvironmentPlant> page,
			Map<String, Object> parameter, String type);

	public VEnvironmentPlant getVEnvironmentPlantById(String plantId);

	public void addPlant(EnvironmentPlant plant);

	public void deletePlant(String plantId);
	
	

}
