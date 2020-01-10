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
import com.jdry.pms.assertStockManage.service.InstockInformationService;

/**
 * 库存信息
 * @author hezuping
 *
 */
@Component
public class InstockInformationView {
	
	@Resource
	InstockInformationService instockInformationService;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DataProvider
	public void getInsockInfomation(Map<String, Object> parameter,Page page,Criteria criteria)
	{
		List res=instockInformationService.getInsockInfomation(parameter, page, criteria);	
		List list=new ArrayList();
		for(int i=0;i<res.size();i++)
		{
			Map map = new HashMap();
			Object[] obj = (Object[])res.get(i);	
			String type_name = null == obj[0]?"":obj[0].toString();
			map.put("type_name", type_name);
			
			String item_id = null == obj[1]?"":obj[1].toString();
			map.put("item_id", item_id);
			String item_name = null == obj[2]?"":obj[2].toString();
			map.put("item_name", item_name);
			String item_type = null == obj[3]?"":obj[3].toString();
			map.put("item_type", item_type);
			String unit_price = null == obj[4]?"":obj[4].toString();
			map.put("unit_price", unit_price);
			String sum_price = null == obj[5]?"":obj[5].toString();
			map.put("sum_price", sum_price);
			String suppliy_num = null == obj[6]?"":obj[6].toString();
			map.put("suppliy_num", suppliy_num);
			
			String owne_stock = null == obj[7]?"":obj[7].toString();
			map.put("owne_stock", owne_stock);
			
			String bar_code = null == obj[8]?"":obj[8].toString();
			map.put("bar_code", bar_code);
			
			String item_unit = null == obj[9]?"":obj[9].toString();
			map.put("item_unit", item_unit);
			
			String item_flag = null == obj[10]?"":obj[10].toString();
			map.put("item_flag", item_flag);
			list.add(map);
			
		}
		page.setEntities(list);
	}

}
