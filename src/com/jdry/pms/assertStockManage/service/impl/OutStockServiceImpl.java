package com.jdry.pms.assertStockManage.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assertStockManage.dao.OutStockDao;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.assertStockManage.service.OutStockService;
/**
 * 出库操作
 * @author hezuping
 * 时间：2016=01-25
 *
 */
@Repository
@Component
public class OutStockServiceImpl implements OutStockService 
{
    @Resource
    OutStockDao outStockDao;
	@Override
	public boolean OutStockInfo(List<InstockModel> instockList,VoucherModel vo) {
		try {
			return outStockDao.OutStockInfo(instockList, vo);
		} catch (SQLException e) {
			return false;
		}
	}
 
}
