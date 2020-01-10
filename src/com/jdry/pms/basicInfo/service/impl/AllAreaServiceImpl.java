package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.AllAreaDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.comm.dao.impl.BaseDaoImpl;

@Repository
@Component
public class AllAreaServiceImpl extends BaseDaoImpl<AllArea> implements AllAreaService{

	@Resource
	AllAreaDao dao;
	
	@Override
	public void query(Page<AllArea> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		dao.query(page, parameter, criteria);
	}

	@Override
	public void saveAllArea(Collection<AllArea> areas) {
		
		dao.saveAllArea(areas);
	}

	@Override
	public Collection<AllArea> queryAllAreaByParam(Map<String, Object> parameter) {
		
		return dao.queryAllAreaByParam(parameter);
	}

	@Override
	public void queryPropertyByParam(Page<AllArea> page,
			Map<String, Object> parameter, String type) {
		dao.queryPropertyByParam(page,parameter,type);
		
	}

	@Override
	public AllArea getAllAreaById(String buildId) {
		
		return dao.getAllAreaById(buildId);
	}

	@Override
	public String addAllArea(AllArea allArea) {
		
		return dao.addAllArea(allArea);
	}

	@Override
	public void deleteAllArea(String buildId) {
		dao.deleteAllArea(buildId);
		
	}

	@Override
	public AllArea getAllAreaByName(String buildName) {
		return dao.getAllAreaByName(buildName);
	}

}
