package com.jdry.pms.assertStockManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

@Repository
public interface VoucherDetailService {
	public List findVoucherDetailInfo(Map<String, Object> parameter,Page page,Criteria criteria);

	public Object quaryVoucherInfo(Map map);
}
