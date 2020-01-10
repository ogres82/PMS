package com.jdry.pms.basicInfo.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.jdry.pms.basicInfo.dao.OwnerInfoDao;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.dir.util.DirctoryComm;

@Component
public class HousePropertyView {

	@Resource
	HousePropertyService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@Resource
	HousePropertyDao hDao;
	
	@Resource
	OwnerInfoDao owner;
	
	@Resource
	DirctoryComm dirctoryComm;
	
	@DataProvider
	public void queryHouseProperty(Page<HouseProperty> page,Map<String, Object> parameter,Criteria criteria) throws Exception{
		service.query(page,parameter,criteria);
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
	
	@DataProvider
	public Collection<HouseProperty> queryHousePropertyByParam(Map<String, Object> parameter){
		return service.queryHousePropertyByParam(parameter);
	}
	
	@DataProvider
	public Collection<HouseProperty> queryHousePropertyByParent(String id){
		
		Collection<HouseProperty> houses = service.queryHousePropertyByParent(id);
		List<HouseProperty> results = new ArrayList<HouseProperty>();
		for(HouseProperty house:houses){
			String roomId = house.getRoomId();
			String roomNo = house.getRoomNo();
			Collection<PropertyOwner> owners = owner.queryPropertyOwnerByParent(roomId);
			for(PropertyOwner owner_:owners){
				roomNo += "|"+owner_.getOwnerName();
			}
			house.setRoomNo(roomNo);
			results.add(house);
		}
		return results;
	}
	
	@DataProvider
	public Collection<HouseProperty> queryHousePropertyByJdbc(Map<String, String> parameter){
		return service.queryHousePropertyByJdbc(parameter);
	}
	
	@DataResolver
	public void saveHouseProperty(Collection<HouseProperty> houses){
	    service.saveHouseProperty(houses);
	}
	
	@Expose
	public String checkHouse(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from HouseProperty where roomNo='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "名称\"" + isExist;
		}
	}
	
	/**
	 * 房间类型
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getRoomType() {
	    return dirctoryComm.getDirByCode("room_type");
	}
	
	/**
	 * 房间状态
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getRoomState() {
	    return dirctoryComm.getDirByCode("room_state");
	}
	
	/**
	 * 收费对象
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getChargeObject() {
	    return dirctoryComm.getDirByCode("charge_object");
	}
	
	
	@DataProvider
	public Map<Object, String> getBuilding() {
		Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		List<BuildingProperty> builds = service.getBuilding(parameter);
	    for(BuildingProperty build:builds){
	    	mapValue.put(build.getStoriedBuildId(), build.getStoriedBuildName());
	    }
	    return mapValue;
	}
	
	
}
