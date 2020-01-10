package com.jdry.pms.assertStockManage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.dao.VoucherDetailDao;
import com.jdry.pms.assertStockManage.service.VoucherDetailService;

@Repository
@Component
public class VoucherDetailServiceImpl implements VoucherDetailService {

	@Resource
	VoucherDetailDao voucherDetailDao;
	
	
	@Override
	public List findVoucherDetailInfo(Map<String, Object> parameter, Page page,
			Criteria criteria) 
	{
		return voucherDetailDao.findVoucherDetailInfo(parameter, page, criteria);
	}


	@Override
	public Object quaryVoucherInfo(Map map) {
		return voucherDetailDao.quaryVoucherInfo(map);
	}

}
