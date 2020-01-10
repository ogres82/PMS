package com.jdry.pms.assertStockManage.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieInfo;
import com.jdry.pms.assertStockManage.service.SupplierInfoService;

/**
 * 供应商信息管理
 * @author hezuping
 *
 */
@Component
public class SupplierInfoView
{

	@Resource
	SupplierInfoService supplierInfoService;
	/**
	 * 按条件查询信息
	 * @param parameter
	 * @param page
	 * @param criteria
	 */
	@DataProvider
	public void quarySupplierInfo(Map<String, Object> parameter,Page page,Criteria criteria)
	{
		supplierInfoService.querySupplierInfo(page, parameter, criteria);
	}
	
	/**
	 * 添加类别信息
	 * @param supplieTypes
	 */
	@DataResolver
	public void saveSupplierInfoProperty(Collection<SupplieInfo> supplieInfos)
	{
		supplierInfoService.saveOrUpdateSupplierInfo(supplieInfos);	
		
	}
	
	@Expose
	public SupplieInfo getSupplieInfo(Map<String, Object> parameter){
		return supplierInfoService.getSupplieInfo(parameter);
	}
	
	@Expose
	public String update_SupplieInfo(SupplieInfo sup) throws Exception{
		Collection<SupplieInfo> sups = new ArrayList<SupplieInfo>();
		if(sup != null){
			sup = EntityUtils.toEntity(sup);
			EntityUtils.setState(sup, EntityState.MODIFIED);
			sups.add(sup);
			supplierInfoService.saveOrUpdateSupplierInfo(sups);
			return "success";
		}
		return "failed";
	}
}
