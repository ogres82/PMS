package com.jdry.pms.basicInfo.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.service.ParkingAreaService;

@Component
public class ParkingAreaView {

	@Resource
	ParkingAreaService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@Resource
	HousePropertyDao hDao;
	
	@DataProvider
	public void queryParkingArea(Page<ParkingArea> page,Map<String, Object> parameter,Criteria criteria) throws Exception{
		service.query(page,parameter,criteria);
		Collection<ParkingArea> parkAreas = page.getEntities();
		List<ParkingArea> results = new ArrayList<ParkingArea>();
		for(ParkingArea parArea:parkAreas){
			ParkingArea parkingArea = EntityUtils.toEntity(parArea);
			String parkId = parArea.getParkId();
			Map<String,String> map = new HashMap<String,String>();
			map.put("parkId", parkId);
			AllArea area = hDao.queryAllAreaById(map);
			AreaProperty property = hDao.queryAreaPropertyById(map);
			EntityUtils.setValue(parkingArea, "buildName", area.getBuildName()!=null?area.getBuildName():"");
			EntityUtils.setValue(parkingArea, "areaName", property.getCommunityName()!=null?property.getCommunityName():"");
			results.add(parkingArea);
		}
		page.setEntities(results);
	}
	
	@DataProvider
	public Collection<ParkingArea> queryParkingAreaByParam(Map<String, Object> parameter){
		return service.queryParkingAreaByParam(parameter);
	}
	
	@DataResolver
	public void saveParkingArea(Collection<ParkingArea> parkingAreas){
	    service.saveParkingArea(parkingAreas);
	}
	
	@Expose
	public String checkParking(String parameter)
			throws InterruptedException {
		
		String isExist = vDao.uniqueCheck("from ParkingArea where parkNo='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "姓名\"" + isExist;
		}
	}
}
