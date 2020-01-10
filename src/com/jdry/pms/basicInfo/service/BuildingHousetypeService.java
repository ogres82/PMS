package com.jdry.pms.basicInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.BuildingHousetype;
import com.jdry.pms.basicInfo.pojo.BuildingImg;

@Repository
public interface BuildingHousetypeService {

	public void queryListByParam(Page<BuildingHousetype> page,Map<String, Object> parameter);

	public BuildingHousetype getBuildingHousetypeById(String typeId);

	public String addBuildingHousetype(BuildingHousetype houseType);

	public void deleteBuildingHousetype(String typeId);
	
	public BuildingHousetype getBuildingHousetypeByName(String buildName);

	public void saveBuildingImg(BuildingImg img);

	public BuildingImg getBuildingImgById(String imgId);

	public List<BuildingImg> getImgByTypeId(String typeId);

	public void deleteImg(BuildingImg img);

}
