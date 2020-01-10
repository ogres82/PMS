package com.jdry.pms.assertStockManage.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.service.SupplieTypeService;

/**
 * 
 * 描述：物资类别管理
 * @author hezuping
 * @date 2016-01-06 20:11:56
 */
@Component
public class SupplieTypeView {
	
	@Resource
	SupplieTypeService supplieTypeService;
	/**
	 * 获取物资类别信息
	 * @param parameter
	 * @param page
	 * @param criteria
	 */
	
	@SuppressWarnings("unchecked")
	@DataProvider
	public void getSuplieTypeInfo(Map<String, Object> parameter,Page page,Criteria criteria)
	{
		supplieTypeService.querySuppliesInfo(page, parameter, criteria);
		
	}
	
	/**
	 * 添加类别信息
	 * @param supplieTypes
	 */
	@DataResolver
	public void saveSupplieTypeProperty(Collection<SupplieType> supplieTypes)
	{
		supplieTypeService.saveSupplieType(null,null);	
		
	}
	
	
	@Expose
	public SupplieType getSupplieTypeInfo(Map<String, Object> parameter){
		return supplieTypeService.getSupplieTypeInfo(parameter);
	}
	
	

}
