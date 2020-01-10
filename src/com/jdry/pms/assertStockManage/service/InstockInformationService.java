package com.jdry.pms.assertStockManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * 库存信息
 * @author hezuping
 *
 */
@Repository
public interface InstockInformationService {
	
	public List getInsockInfomation(Map<String, Object> parameter,Page page,Criteria criteria);

	public Object quaryInstockInfo(Map map);

}
