package com.jdry.pms.basicInfo.view;

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
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;

@Component
public class BuildingPropertyView {

	@Resource
	BuildingPropertyService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@DataProvider
	public void queryBuildingProperty(Page<BuildingProperty> page,Map<String, Object> parameter,Criteria criteria){
		service.query(page,parameter,criteria);
	}
	
	@DataProvider
	public Collection<BuildingProperty> queryBuildingPropertyByParam(Map<String, Object> parameter){
		return service.queryBuildingPropertyByParam(parameter);
	}
	
	@DataProvider
	public Collection<BuildingProperty> queryBuildingPropertyByParent(String id){
		return service.queryBuildingPropertyByParent(id);
	}
	
	@DataResolver
	public void saveBuildingProperty(Collection<BuildingProperty> builds){
	    service.saveBuildingProperty(builds);
	}
	
	@Expose
	public String checkBuidName(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from BuildingProperty where storiedBuildName='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "楼宇\"" +parameter+ "\""+ isExist;
		}
	}
	
	@DataProvider
	public Map<Object, String> getCommId() {
		Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		List<AreaProperty> pros = service.getCommId(parameter);
	    for(AreaProperty pro:pros){
	    	mapValue.put(pro.getCommunityId(), pro.getCommunityName());
	    }
	    return mapValue;
	}
	
}
