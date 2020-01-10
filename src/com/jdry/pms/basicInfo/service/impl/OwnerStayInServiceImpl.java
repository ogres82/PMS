package com.jdry.pms.basicInfo.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.OwnerStayInDao;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.service.OwnerStayInService;

@Repository
@Component
public class OwnerStayInServiceImpl implements OwnerStayInService{

	@Resource
	OwnerStayInDao dao;
	
	@Override
	public void query(Page<HouseProperty> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		dao.query(page, parameter, criteria);
	}
}
