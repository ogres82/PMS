package com.jdry.pms.assertStockManage.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.ViewiteminfoModel;

@Repository
public interface VoucherAndStockInfoService 
{

	public void quaryInstockHisInfo(Page<ViewiteminfoModel> page,Map<String, Object> parameter, Criteria criteria);
}
