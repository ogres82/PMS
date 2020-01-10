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
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.service.AreaPropertyService;

@Component
public class AreaPropertyView {

	@Resource
	AreaPropertyService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@DataProvider
	public void queryAreaProperty(Page<AreaProperty> page,Map<String, Object> parameter,Criteria criteria){
		service.query(page,parameter,criteria);
	}
	
	@DataProvider
	public Collection<AreaProperty> queryAreaPropertyByParam(Map<String, Object> parameter){
		return service.queryAreaPropertyByParam(parameter);
	}
	
	@DataProvider
	public Collection<AreaProperty> queryAreaPropertyByParent(String id){
		return service.queryAreaPropertyByParent(id);
	}
	
	@DataResolver
	public void saveAreaProperty(Collection<AreaProperty> areaPropertys){
	    service.saveAreaProperty(areaPropertys);
	}
	
	@Expose
	public String checkEmpName(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from AreaProperty where empName='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "姓名\"" + isExist;
		}
	}
	
	@DataProvider
	public Map<Object, String> getAllAreaId() {
		Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		List<AllArea> areas = service.getAllAreaId(parameter);
	    for(AllArea area:areas){
	    	mapValue.put(area.getBuildId(), area.getBuildName());
	    }
	    return mapValue;
	}
	
}
