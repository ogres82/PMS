package com.jdry.pms.assertStockManage.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.dao.VoucherAndStockInfoDao;
import com.jdry.pms.assertStockManage.pojo.ViewiteminfoModel;
import com.jdry.pms.assertStockManage.service.VoucherAndStockInfoService;

@Repository
@Component
public class VoucherAndStockInfoServiceImpl implements VoucherAndStockInfoService
{

	@Resource
	VoucherAndStockInfoDao voucherAndStockInfoDao;
	@Override
	public void quaryInstockHisInfo(Page<ViewiteminfoModel> page,Map<String, Object> parameter, Criteria criteria)
	{
		voucherAndStockInfoDao.quaryInstockHisInfo(page, parameter, criteria);
	}
	
}
