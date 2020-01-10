package com.jdry.pms.basicInfo.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.OwnerStayInService;
import com.jdry.pms.dir.util.DirctoryComm;


@Component
public class OwnerStayInView {

	@Resource
	HousePropertyService service;
	
	@Resource
	OwnerStayInService stayInService;
	
	@Resource
	ValidatorsDao vDao;
	
	@Resource
	HousePropertyDao hDao;
	
	@Resource
	DirctoryComm dirctoryComm;
	
	@DataProvider
	public void queryHouseProperty(Page<HouseProperty> page,Map<String, Object> parameter,Criteria criteria) throws Exception{
		stayInService.query(page,parameter,criteria);
		List<HouseProperty> results = new ArrayList<HouseProperty>();
		Collection<HouseProperty> pros = page.getEntities();
		for(HouseProperty pro:pros){
			HouseProperty house = EntityUtils.toEntity(pro);
			String roomId = pro.getRoomId();
			Map<String,String> map = new HashMap<String,String>();
			map.put("roomId", roomId);
			AllArea area = hDao.queryAllAreaById(map);
			AreaProperty property = hDao.queryAreaPropertyById(map);
			BuildingProperty build =hDao.queryBuildingPropertyById(map);
			EntityUtils.setValue(house, "buildName", area.getBuildName()!=null?area.getBuildName():"");
			//EntityUtils.setValue(house, "buildId", hDao.queryAllAreaById(roomId).getBuildId()!=null?hDao.queryAllAreaById(roomId).getBuildId():"");
			EntityUtils.setValue(house, "areaName", property.getCommunityName()!=null?property.getCommunityName():"");
			//EntityUtils.setValue(house, "areaId", hDao.queryAreaPropertyById(roomId).getCommunityId()!=null?hDao.queryAreaPropertyById(roomId).getCommunityId():"");
			EntityUtils.setValue(house, "buildingName", build.getStoriedBuildName()!=null?build.getStoriedBuildName():"");
			//EntityUtils.setValue(house, "belongSbId", hDao.queryBuildingPropertyById(roomId).getStoriedBuildId()!=null?hDao.queryBuildingPropertyById(roomId).getStoriedBuildName():"");
			results.add(house);
		}
		page.setEntities(results);
	}
	
	
}
