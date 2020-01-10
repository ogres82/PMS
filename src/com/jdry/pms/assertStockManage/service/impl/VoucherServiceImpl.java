package com.jdry.pms.assertStockManage.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.dao.VoucherDao;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.assertStockManage.service.VoucherService;
@Repository
@Component
public class VoucherServiceImpl implements VoucherService {
	
	@Resource
	VoucherDao voucherDao;
	@Override
	public boolean save(InstockModel stock)
	{
		return voucherDao.save(stock);

	}
	@Override
	public List quaryInstockInfo(Page<InstockModel> page,
			Map<String, Object> parameter, Criteria criteria) {
		return voucherDao.quaryInstockInfo(page, parameter, criteria);
	}
	@Override
	public boolean updateInstockInfo(InstockModel ins) throws SQLException
	{
		return voucherDao.updateInstockInfo(ins);
	}
	
	@Override
	public boolean saveVoucherAndInstock(VoucherModel vo,List<InstockModel> instockList) {
		boolean flag=false;
		try {
			try {
				flag=voucherDao.saveVoucherAndInstock(vo,instockList);
			} catch (ParseException e) {
				flag=false;
			}
		} catch (SQLException e) {
			flag=false;
		}
		return flag;
	}
	
	
	@Override
	public boolean deleteStockInfo() {
		return voucherDao.deleteStockInfo();
	}

}
