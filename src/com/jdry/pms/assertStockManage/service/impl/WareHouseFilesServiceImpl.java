package com.jdry.pms.assertStockManage.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.dao.WareHouseFilesDao;
import com.jdry.pms.assertStockManage.pojo.WareHouseFiles;
import com.jdry.pms.assertStockManage.service.WareHouseFilesService;

@Repository
@Component
public class WareHouseFilesServiceImpl implements WareHouseFilesService {

	@Resource
	WareHouseFilesDao wareHouseFilesDao;

	@Override
	public void quaryWareHouseFilesInfo(Page<WareHouseFiles> page,
			Map<String, Object> parameter, Criteria criteria) {
		wareHouseFilesDao.quaryWareHouseFilesInfo(page, parameter, criteria);
	}

	@Override
	public void saveWareHouseFilesInfo(Collection<WareHouseFiles> wareHouseFiles)
	{
		wareHouseFilesDao.operationWareHouseFilesInfo(wareHouseFiles);
		
	}

	@Override
	public WareHouseFiles findWareHouseFilesByCode(Map map) 
	{
		return wareHouseFilesDao.findWareHouseFilesByCode(map);
	}

	@Override
	public boolean deleteSupplieInfoById(String warehouse_code) {
		try {
			return wareHouseFilesDao.deleteSupplieInfoById(warehouse_code);
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Collection<WareHouseFiles> queryWarHouesInfos() {
		// TODO Auto-generated method stub
		return wareHouseFilesDao.queryWarHouesInfos();
	}
	

}
