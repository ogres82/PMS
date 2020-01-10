package com.jdry.pms.assertStockManage.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;

/**
 * 出库操作
 * @author hezuping
 *
 */
@Repository
public interface OutStockService {
	
	public boolean OutStockInfo(List<InstockModel> instockList,VoucherModel vo);
	

}
