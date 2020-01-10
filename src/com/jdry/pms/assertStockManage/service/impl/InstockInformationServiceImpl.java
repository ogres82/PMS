package com.jdry.pms.assertStockManage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.dao.InstockInformationDao;
import com.jdry.pms.assertStockManage.service.InstockInformationService;

@Repository
@Component
public class InstockInformationServiceImpl implements InstockInformationService 
{
	@Resource
	InstockInformationDao instockInformationDao;
	@Override
	public List getInsockInfomation(Map<String, Object> parameter, Page page,
			Criteria criteria) {
		return instockInformationDao.getInsockInfomation(parameter, page, criteria);

	}
	@Override
	public Object quaryInstockInfo(Map map)
	{
		
		return instockInformationDao.quaryInstockInfo(map);
	}

}
