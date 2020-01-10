package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.AreaPropertyDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.VAreaProperty;
import com.jdry.pms.basicInfo.service.AreaPropertyService;

@Repository
@Component
public class AreaPropertyServiceImpl implements AreaPropertyService{

	@Resource
	AreaPropertyDao dao;
	
	@Override
	public void query(Page<AreaProperty> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		dao.query(page, parameter, criteria);
	}

	@Override
	public void saveAreaProperty(Collection<AreaProperty> areaPropertys) {
		
		dao.saveAreaProperty(areaPropertys);
	}

	@Override
	public List<AllArea> getAllAreaId(Map<String, Object> parameter) {
		
		return dao.getAllAreaId(parameter);
	}

	@Override
	public Collection<AreaProperty> queryAreaPropertyByParam(
			Map<String, Object> parameter) {
		
		return dao.queryAreaPropertyByParam(parameter);
	}

	@Override
	public Collection<AreaProperty> queryAreaPropertyByParent(String id) {
		
		return dao.queryAreaPropertyByParent(id);
	}

	@Override
	public void queryAreaPropertyByParam(Page<VAreaProperty> page,
			Map<String, Object> parameter, String type) {
		dao.queryAreaPropertyByParam(page,parameter,type);
		
	}

	@Override
	public String addAreaProperty(AreaProperty areaProperty) {
		
		return dao.addAreaProperty(areaProperty);
	}

	@Override
	public void deleteAreaProperty(String buildId) {
		dao.deleteAreaProperty(buildId);
	}

	@Override
	public AreaProperty getAreaPropertyById(String communityId) {
		 
		return dao.getAreaPropertyById(communityId);
	}

	@Override
	public AreaProperty getAreaPropertyByName(String communityName) {
		return dao.getAreaPropertyByName(communityName);
	}

}
