package com.jdry.pms.environment.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.environment.pojo.Bdf2User;
import com.jdry.pms.environment.pojo.EnvironmentArea;
import com.jdry.pms.environment.pojo.EnvironmentAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlantNum;

@Repository
public interface AreaPlantService {

	public void queryVAreaPlantByParam(Page<VAreaPlant> page,
			Map<String, Object> parameter, String type);

	public VAreaPlant getAreaPlantById(String areaId);

	public String addArea(EnvironmentArea area);

	public void deleteArea(String areaId);

	public void addAreaPlant(EnvironmentAreaPlant area);

	public boolean checkUnique(Map<String, Object> param);
	
	public Collection<EnvironmentArea> queryAreaByParam(Map<String, Object> param);

	public void queryVAreaPlantNumByParam(Page<VAreaPlantNum> page,
			Map<String, Object> parameter, String type);

	public List<Bdf2User> queryBdf2UserByParam(Map<String, Object> param);

	public VAreaPlant getAreaByGisId(String areaGisId);

	public List<VEmp> queryEmpByKeyword(Map<String, Object> param);

	public void deleteAreaPlant(String areaId);

}
