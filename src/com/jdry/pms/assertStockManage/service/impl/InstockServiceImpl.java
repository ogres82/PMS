package com.jdry.pms.assertStockManage.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assertStockManage.dao.InstockDao;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.service.InstockService;

@Repository
@Component
public class InstockServiceImpl implements InstockService 
{
	@Resource
	InstockDao instockDao;
	
	@Override
	public void OperationInstockInfo(Collection<InstockModel> instock)
	{
		instockDao.OperationInstockInfo(instock);
	}
}
