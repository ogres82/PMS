package com.jdry.pms.assignWork.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.dao.AssingWorkSearchDao;
import com.jdry.pms.assignWork.service.AssingWorkSearchService;

@Component
public class AssingWorkSearchServiceImpl implements AssingWorkSearchService {

	@Resource
	AssingWorkSearchDao assingWorkSearchDao;
	
	@Override
	public void assignWorkSearch(Page page, Map<String, Object> parameter,Object object,Criteria criteria) throws Exception
	{
		 assingWorkSearchDao.assignWorkSearch(page, parameter, object,criteria);
	}

}
