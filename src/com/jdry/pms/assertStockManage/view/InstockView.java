package com.jdry.pms.assertStockManage.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.service.InstockService;

@Component
public class InstockView 
{
	@Resource
	InstockService instockService;
	
	/**
	 * 查询库存信息
	 * @param parameter
	 * @param page
	 * @param criteria
	 */
	@DataProvider
	public void quaryInstockInfo(Map<String, Object> parameter,Page page,Criteria criteria)
	{
		
		
		
	}
	
	
	/**
	 * 入库信息的操作
	 * @param instocks
	 */
	@DataResolver
	public void operationInstockInfo(Collection<InstockModel> instocks)
	{
		instockService.OperationInstockInfo(instocks);
		
	}

}
