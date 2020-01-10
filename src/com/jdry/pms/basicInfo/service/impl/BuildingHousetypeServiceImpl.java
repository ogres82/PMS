package com.jdry.pms.basicInfo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.BuildingHousetypeDao;
import com.jdry.pms.basicInfo.pojo.BuildingHousetype;
import com.jdry.pms.basicInfo.pojo.BuildingImg;
import com.jdry.pms.basicInfo.service.BuildingHousetypeService;

@Repository
@Component
public class BuildingHousetypeServiceImpl implements BuildingHousetypeService{

	@Resource
	BuildingHousetypeDao dao;
	
	@Override
	public void queryListByParam(Page<BuildingHousetype> page,Map<String, Object> parameter) {
		dao.queryListByParam(page,parameter);
	}

	@Override
	public BuildingHousetype getBuildingHousetypeById(String typeId) {
		return dao.getBuildingHousetypeById(typeId);
	}

	@Override
	public String addBuildingHousetype(BuildingHousetype houseType) {
		return dao.addBuildingHousetype(houseType);
	}

	@Override
	public void deleteBuildingHousetype(String typeId) {
		dao.deleteBuildingHousetype(typeId);
	}

	@Override
	public BuildingHousetype getBuildingHousetypeByName(String buildName) {
		return dao.getBuildingHousetypeByName(buildName);
	}

	@Override
	public void saveBuildingImg(BuildingImg img) {
		dao.saveBuildingImg(img);
	}

	@Override
	public BuildingImg getBuildingImgById(String imgId) {
		return dao.getBuildingImgById(imgId);
	}

	@Override
	public List<BuildingImg> getImgByTypeId(String typeId) {
		return dao.getImgByTypeId(typeId);
	}

	@Override
	public void deleteImg(BuildingImg img) {
		dao.deleteImg(img);
	}

}
