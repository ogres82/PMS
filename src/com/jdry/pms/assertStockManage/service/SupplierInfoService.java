package com.jdry.pms.assertStockManage.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieInfo;

/**
 * 供应商信息
 * @author hezuping
 *
 */
@Repository
public interface SupplierInfoService {
	/**
	 * 按条件查询供应商信息
	 * @param page
	 * @param parameter
	 * @param criteria
	 */
   public List querySupplierInfo(Page<SupplieInfo> page,Map<String, Object> parameter,Criteria criteria);

	public void saveOrUpdateSupplierInfo(Collection<SupplieInfo> supplieInfos);

	public SupplieInfo getSupplieInfo(Map<String, Object> parameter);
	
	public boolean save(SupplieInfo sup);

	public boolean deleteSupplieInfoById(String suppliy_code);

	public Collection<SupplieInfo> querySulierInfos();

}
