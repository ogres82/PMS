package com.jdry.pms.assertStockManage.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;

/**
 * 库存的操作
 * @author hezuping
 *
 */
@Repository
public interface VoucherService {
	
	
	public boolean save(InstockModel stock);
	
	
	public List quaryInstockInfo(Page<InstockModel> page,Map<String, Object> parameter,Criteria criteria);

	
	public boolean updateInstockInfo(InstockModel ins) throws SQLException;
    
	public boolean saveVoucherAndInstock(VoucherModel vo,List<InstockModel> instockList);
	
	
	public boolean deleteStockInfo();

}
