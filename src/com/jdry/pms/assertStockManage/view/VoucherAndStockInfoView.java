package com.jdry.pms.assertStockManage.view;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.service.VoucherAndStockInfoService;

/**
 * 表单详情
 * @author hezuping
 *
 */
@Component
public class VoucherAndStockInfoView 
{

	@Resource
	VoucherAndStockInfoService voucherAndStockInfoService;
	@DataProvider
	public void getInstockHis(Page page,Map<String, Object> parameter, Criteria criteria)
	{
		
		voucherAndStockInfoService.quaryInstockHisInfo(page, parameter, criteria);
	}
	
	
}
