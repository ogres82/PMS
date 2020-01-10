package com.jdry.pms.assertStockManage.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.dao.SupplierInfoDao;
import com.jdry.pms.assertStockManage.pojo.SupplieInfo;
import com.jdry.pms.assertStockManage.service.SupplierInfoService;

@Repository
@Component
public class SupplierInfoServiceImpl implements SupplierInfoService {

	@Resource
	SupplierInfoDao supplierInfoDao;
	
	@Override
	public List querySupplierInfo(Page<SupplieInfo> page,Map<String, Object> parameter, Criteria criteria) 
	{
		return supplierInfoDao.querySupplierInfoByProperty(page, parameter, criteria);
	}

	@Override
	public void saveOrUpdateSupplierInfo(Collection<SupplieInfo> supplieInfos) {
		
		supplierInfoDao.saveOrUpdateSupplierInfo(supplieInfos);
	}

	@Override
	public SupplieInfo getSupplieInfo(Map<String, Object> parameter) 
	{
		return supplierInfoDao.getSupplieInfo(parameter);
	}

	@Override
	public boolean save(SupplieInfo sup) 
	{
		
		return supplierInfoDao.save(sup);
	}

	@Override
	public boolean deleteSupplieInfoById(String suppliy_code)
	{
		try {
			return supplierInfoDao.deleteSupplieInfoById(suppliy_code);
		} catch (SQLException e)
		{
			return false;
		}
		
	}

	@Override
	public Collection<SupplieInfo> querySulierInfos() {
		// TODO Auto-generated method stub
		return supplierInfoDao.querySulierInfos();
	}

}
