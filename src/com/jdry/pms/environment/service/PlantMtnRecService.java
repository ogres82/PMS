package com.jdry.pms.environment.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentPlantMtnRec;
import com.jdry.pms.environment.pojo.VEnvironmentPlantMtnRec;

@Repository
public interface PlantMtnRecService {

	public void queryPlantMtnRecByParam(Page<VEnvironmentPlantMtnRec> page,
			Map<String, Object> parameter, String type);

	public VEnvironmentPlantMtnRec getVPlantMtnRecById(String recId);

	public void addPlantMtnRec(EnvironmentPlantMtnRec mtnRec);

	public void deletePlantMtnRec(String recId);
}
