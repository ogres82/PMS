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
import com.jdry.pms.assertStockManage.dao.ItmsfilesDao;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.pojo.Titmsfiles;
import com.jdry.pms.assertStockManage.service.ItmsfilesService;

@Repository
@Component
public class ItmsfilesServiceImpl implements ItmsfilesService
{
	@Resource
	ItmsfilesDao itmsfilesDao;
	
	@Override
	public List quaryItmsfilesInfo(Page<Titmsfiles> page,Map<String, Object> parameter, Criteria criteria)
	{
	 return itmsfilesDao.quaryItmsfilesInfo(page, parameter, criteria);
	}

	
	@Override
	public String  saveItmsfilesInfo(Collection<Titmsfiles> itemfiles)
	{
		return itmsfilesDao.saveItmsfilesInfo(itemfiles);
	}


	@Override
	public Titmsfiles getItmsFiles(Map<String, Object> parameter) 
	{
		return itmsfilesDao.getItmsFiles(parameter);
	}


	@Override
	public Collection<SupplieType> getDirectoryLikeCode(String code) {
		return itmsfilesDao.getDirectoryLikeCode(code);
	}


	@Override
	public Titmsfiles findItmsFilesByCode(Map map) {
		return itmsfilesDao.findItmsFilesByCode(map);
	}


	@Override
	public boolean deleteItmsFilesInfoById(String bar_code) {
		try {
			return itmsfilesDao.deleteItmsFilesInfoById(bar_code);
		} catch (SQLException e) {
			return false;
		}
	}
}