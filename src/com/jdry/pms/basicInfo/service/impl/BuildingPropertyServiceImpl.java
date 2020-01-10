package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.BuildingPropertyDao;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingHousetype;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.VBuildingProperty;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;

@Repository
@Component
public class BuildingPropertyServiceImpl implements BuildingPropertyService{

	@Resource
	BuildingPropertyDao dao;
	
	@Override
	public void query(Page<BuildingProperty> page,
			Map<String, Object> parameter, Criteria criteria) {
		
		dao.query(page, parameter, criteria);
	}

	@Override
	public void saveBuildingProperty(Collection<BuildingProperty> builds) {
		
		dao.saveBuildingProperty(builds);
	}

	@Override
	public List<AreaProperty> getCommId(Map<String, Object> parameter) {
		
		return dao.getCommId(parameter);
	}

	@Override
	public Collection<BuildingProperty> queryBuildingPropertyByParam(
			Map<String, Object> parameter) {
		
		return dao.queryBuildingPropertyByParam(parameter);
	}

	@Override
	public Collection<BuildingProperty> queryBuildingPropertyByParent(String id) {
		
		return dao.queryBuildingPropertyByParent(id);
	}

	@Override
	public void queryBuildingPropertyByParam(Page<VBuildingProperty> page,
			Map<String, Object> parameter, String type) {
		
		dao.queryBuildingPropertyByParam(page,parameter,type);
	}

	@Override
	public VBuildingProperty getVBuildingPropertyById(String storiedBuildId) {
		
		return dao.getVBuildingPropertyById(storiedBuildId);
	}

	@Override
	public String addBuildingProperty(BuildingProperty buildingProperty) {
		return dao.addBuildingProperty(buildingProperty);
	}

	@Override
	public void deleteBuildingProperty(String storiedBuildId) {
		dao.deleteBuildingProperty(storiedBuildId);
	}

	@Override
	public BuildingProperty getBuildingPropertyByName(String storiedBuildName) {
		
		return dao.getBuildingPropertyByName(storiedBuildName);
	}

	@Override
	public List<VBuildingProperty> queryBuildingByName(String buildingName) {
		// TODO Auto-generated method stub
		return dao.queryBuildingByName(buildingName);
	}

	@Override
	public List<VHouseOwner> queryOwnerByBuild(String buildingId) {
		// TODO Auto-generated method stub
		return dao.queryOwnerByBuild(buildingId);
	}

	@Override
	public List<BuildingHousetype> queryHouseType() {
		return dao.queryHouseType();
	}

	/* (non-Javadoc)
	 * @see com.jdry.pms.basicInfo.service.BuildingPropertyService#queryBuildingImgById(java.lang.String)
	 */
	@Override
	public List queryBuildingImgById(String buildingId) {
		// TODO Auto-generated method stub
		return dao.queryBuildingImgById(buildingId);
	}

}
