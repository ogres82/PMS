package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jdry.pms.basicInfo.pojo.*;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

@Repository
public interface BuildingPropertyService {

	public void query(Page<BuildingProperty> page, Map<String, Object> parameter,
			Criteria criteria);

	public void saveBuildingProperty(Collection<BuildingProperty> builds);

	public List<AreaProperty> getCommId(Map<String, Object> parameter);

	public Collection<BuildingProperty> queryBuildingPropertyByParam(
			Map<String, Object> parameter);

	public Collection<BuildingProperty> queryBuildingPropertyByParent(String id);

	public void queryBuildingPropertyByParam(Page<VBuildingProperty> page,
			Map<String, Object> parameter, String type);

	public VBuildingProperty getVBuildingPropertyById(
			String storiedBuildId);

	public String addBuildingProperty(BuildingProperty buildingProperty);

	public void deleteBuildingProperty(String storiedBuildId);
	
	public BuildingProperty getBuildingPropertyByName(
			String storiedBuildName);
	
	public List<VBuildingProperty> queryBuildingByName(String buildingName);
	public List<VHouseOwner> queryOwnerByBuild(String buildingId);

	public List<BuildingHousetype> queryHouseType();
	public List queryBuildingImgById(String buildingId);

}
