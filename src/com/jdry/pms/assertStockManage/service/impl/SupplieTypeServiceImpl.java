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
import com.jdry.pms.assertStockManage.dao.SupplieTypeDao;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.service.SupplieTypeService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

@Repository
@Component
public class SupplieTypeServiceImpl implements SupplieTypeService {

	@Resource
	SupplieTypeDao supplieTypeDao;
	@Override
	public List querySuppliesInfo(Page<SupplieType> page,Map<String, Object> parameter, Criteria criteria)
	{
		return supplieTypeDao.querySupplieTypeInfo(page, parameter, criteria);
	}
	
	@Override
	public String saveSupplieType(SupplieType supplieType,String stat)
	{
		return supplieTypeDao.saveSupplieType(supplieType,stat);
	}

	@Override
	public SupplieType getSupplieTypeInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return supplieTypeDao.getSupplieTypeInfo(parameter);
	}

	@Override
	public boolean deleteSupplieTypeById(String id) {
		
		try {
			return supplieTypeDao.deleteSupplieTypeById(id);
		} catch (SQLException e)
		{
			return false;

		}
	}

	@Override
	public  Collection<SupplieType> querySuppTypeList() {
		return supplieTypeDao.querySuppTypeList();
	}

	@Override
	public Collection<DirDirectoryDetail> getDirectoryLikeCode(String code) {
		
		return supplieTypeDao.getDirectoryLikeCode(code);
	}

}
