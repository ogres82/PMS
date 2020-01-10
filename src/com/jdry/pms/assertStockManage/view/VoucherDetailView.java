package com.jdry.pms.assertStockManage.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.service.VoucherDetailService;

/***
 * 
 * @author hezuping
 *
 */
@Component
public class VoucherDetailView
{
    @Resource
    VoucherDetailService voucherDetailService;
   
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@DataProvider
	public void findVoucherDetailInfo(Map<String, Object> parameter,Page page,Criteria criteria)
	{
    	List res=voucherDetailService.findVoucherDetailInfo(parameter, page, criteria);
    	List list=new ArrayList();
		for(int i=0;i<res.size();i++)
		{
			Map map = new HashMap();
			Object[] obj = (Object[])res.get(i);
			String voucher_id = null == obj[0]?"":obj[0].toString();
			map.put("voucher_id", voucher_id);
			
			String voucher_code = null == obj[1]?"":obj[1].toString();
			map.put("voucher_code", voucher_code);
			
			String occurren_date = null == obj[2]?"":obj[2].toString();
			map.put("occurren_date", occurren_date);
			
			String owne_stock = null == obj[3]?"":obj[3].toString();
			map.put("owne_stock", owne_stock);
			
			String t_handler = null == obj[4]?"":obj[4].toString();
			map.put("t_handler", t_handler);
			
			String suppliy_code = null == obj[5]?"":obj[5].toString();
			map.put("suppliy_code", suppliy_code);
			
			String orther = null == obj[6]?"":obj[6].toString();
			map.put("orther", orther);
			
			String instok_type = null == obj[7]?"":obj[7].toString();
			map.put("instok_type", instok_type);
			
			String suppliy_name = null == obj[8]?"":obj[8].toString();
			map.put("suppliy_name", suppliy_name);
			list.add(map);
		}
		page.setEntities(list);
	}
	
}
