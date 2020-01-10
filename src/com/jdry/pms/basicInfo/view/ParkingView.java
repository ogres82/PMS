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
import com.jdry.pms.basicInfo.pojo.Parking;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.service.ParkingService;
import com.jdry.pms.dir.util.DirctoryComm;

@Component
public class ParkingView {

	@Resource
	ParkingService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@Resource
	DirctoryComm dirctoryComm;
	
	@Resource
	HousePropertyDao hDao;
	
	@DataProvider
	public void queryParking(Page<Parking> page,Map<String, Object> parameter,Criteria criteria) throws Exception{
		service.query(page,parameter,criteria);
		Collection<Parking> parks = page.getEntities();
		List<Parking> results = new ArrayList<Parking>();
		for(Parking park:parks){
			Parking par = EntityUtils.toEntity(park);
			String carportId = park.getCarportId();
			Map<String,String> map = new HashMap<String,String>();
			map.put("carportId", carportId);
			AllArea area = hDao.queryAllAreaById(map);
			AreaProperty property = hDao.queryAreaPropertyById(map);
			ParkingArea parkArea = hDao.queryParkingAreaById(map);
			EntityUtils.setValue(par, "buildName", area.getBuildName()!=null?area.getBuildName():"");
			EntityUtils.setValue(par, "areaName", property.getCommunityName()!=null?property.getCommunityName():"");
			EntityUtils.setValue(par, "parkName", parkArea.getParkName()!=null?parkArea.getParkName():"");
			results.add(par);
		}
		page.setEntities(results);
	}
	
	@DataResolver
	public void saveParking(Collection<Parking> parkings){
	    service.saveParking(parkings);
	}
	
	@Expose
	public String checkParking(String parameter)
			throws InterruptedException {
		
		String isExist = vDao.uniqueCheck("from Parking where carportNo='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "姓名\"" + isExist;
		}
	}
	
	@DataProvider
	public Map<Object, String> getCarportStatus() {
	    return dirctoryComm.getDirByCode("carport_status");
	}
}
