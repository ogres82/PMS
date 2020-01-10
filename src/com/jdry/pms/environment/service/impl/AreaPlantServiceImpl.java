package com.jdry.pms.environment.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.environment.dao.AreaPlantDao;
import com.jdry.pms.environment.pojo.Bdf2User;
import com.jdry.pms.environment.pojo.EnvironmentArea;
import com.jdry.pms.environment.pojo.EnvironmentAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlantNum;
import com.jdry.pms.environment.service.AreaPlantService;

@Repository
@Component
public class AreaPlantServiceImpl implements AreaPlantService{

	@Resource
	AreaPlantDao dao;

	@Override
	public void queryVAreaPlantByParam(Page<VAreaPlant> page,
			Map<String, Object> parameter, String type) {
		dao.queryVAreaPlantByParam(page,parameter,type);
	}

	@Override
	public VAreaPlant getAreaPlantById(String areaId) {
		
		return dao.getAreaPlantById(areaId);
	}

	@Override
	public String addArea(EnvironmentArea area) {
		return dao.addArea(area);
	}

	@Override
	public void deleteArea(String areaId) {
		dao.deleteArea(areaId);
	}

	@Override
	public void addAreaPlant(EnvironmentAreaPlant area) {
		dao.addAreaPlant(area);
	}

	@Override
	public boolean checkUnique(Map<String, Object> param) {
		return dao.checkUnique(param);
	}

	@Override
	public Collection<EnvironmentArea> queryAreaByParam(
			Map<String, Object> param) {
		
		return dao.queryAreaByParam(param);
	}

	@Override
	public void queryVAreaPlantNumByParam(Page<VAreaPlantNum> page,
			Map<String, Object> parameter, String type) {
		dao.queryVAreaPlantNumByParam(page,parameter);
	}

	@Override
	public List<Bdf2User> queryBdf2UserByParam(Map<String, Object> param) {
		return dao.queryBdf2UserByParam(param);
	}

	@Override
	public VAreaPlant getAreaByGisId(String areaGisId) {
		return dao.getAreaByGisId(areaGisId);
	}

	@Override
	public List<VEmp> queryEmpByKeyword(Map<String, Object> param) {
		
		return dao.queryEmpByKeyword(param);
	}

	@Override
	public void deleteAreaPlant(String areaId) {
		dao.deleteAreaPlant(areaId);
	}
}
