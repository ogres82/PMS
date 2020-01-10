package com.jdry.pms.assertStockManage.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import com.jdry.pms.assertStockManage.pojo.Titmsfiles;
import com.jdry.pms.assertStockManage.service.ItmsfilesService;
/**
 * 描述：物品档案信息
 * @author hezuping
 *
 */
@Component
public class ItmsfilesView 
{
	@Resource
	ItmsfilesService itmsfilesService;
	@SuppressWarnings("unchecked")
	@DataProvider
	public void quaryItmsfilesInfo(Map<String, Object> parameter,Page page,Criteria criteria)
	{
		List res=itmsfilesService.quaryItmsfilesInfo(page, parameter, criteria);
		List list=new ArrayList();
		for(int i=0;i<res.size();i++)
		{
			Map map = new HashMap();
			Object[] obj = (Object[])res.get(i);
			String item_id = null == obj[0]?"":obj[0].toString();
			map.put("item_id", item_id);
			
			String bar_code = null == obj[1]?"":obj[1].toString();
			map.put("bar_code", bar_code);	
			
			
			String defu_inprice = null == obj[2]?"":obj[2].toString();
			map.put("defu_inprice", defu_inprice);	
			
			String defu_outprice = null == obj[3]?"":obj[3].toString();
			map.put("defu_outprice", defu_outprice);	
			
			String item_code = null == obj[4]?"":obj[4].toString();
			map.put("item_code", item_code);	
			
			String item_name = null == obj[5]?"":obj[5].toString();
			map.put("item_name", item_name);	
			
			
			String item_type = null == obj[6]?"":obj[6].toString();
			map.put("item_type", item_type);	
			
			
			String item_unit = null == obj[7]?"":obj[7].toString();
			map.put("item_unit", item_unit);	
			
			String stock_avgprice = null == obj[8]?"":obj[8].toString();
			map.put("stock_avgprice", stock_avgprice);	
			
			String stock_lowerlimit = null == obj[9]?"":obj[9].toString();
			map.put("stock_lowerlimit", stock_lowerlimit);
			
			String stock_uplimit = null == obj[10]?"":obj[10].toString();
			map.put("stock_uplimit", stock_uplimit);
			
			
			String item_flag = null == obj[11]?"":obj[11].toString();
			map.put("item_flag", item_flag);
			
			String suppliy_num = null == obj[12]?"":obj[12].toString();
			map.put("suppliy_num", suppliy_num);
			
			String type_name = null == obj[13]?"":obj[13].toString();
			map.put("type_name", type_name);
			
			list.add(map);
			
		}
	
		page.setEntities(list);
	}
	
	@DataResolver
	public String saveItmsfilesInfo(Collection<Titmsfiles> itemfiles)
	{
		return itmsfilesService.saveItmsfilesInfo(itemfiles);
	}
	
	
	@Expose
	public Titmsfiles getItmsFiles(Map<String, Object> parameter){
		return itmsfilesService.getItmsFiles(parameter);
	}
	
	
	@Expose
	public String delItemFiles(String obj){
		Collection<Titmsfiles> itemcc = new ArrayList<Titmsfiles>();
		if(obj.contains(",")){
			String[] items = obj.split(",");
			for(int i=0;i<items.length;i++){
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("item_id", items[i]);
				Titmsfiles item = itmsfilesService.getItmsFiles(map);
				if(item != null){
					try {
						item = EntityUtils.toEntity(item);
					} catch (Exception e) {
						e.printStackTrace();
					}
					EntityUtils.setState(item, EntityState.DELETED);
					itemcc.add(item);
				}
			}
			
		}else{
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("item_id", obj);
			Titmsfiles item = itmsfilesService.getItmsFiles(map);
			if(item != null){
				try {
					item = EntityUtils.toEntity(item);
				} catch (Exception e)
				{
				}
				EntityUtils.setState(item, EntityState.DELETED);
				itemcc.add(item);
			}
			
		}
		saveItmsfilesInfo(itemcc);
		return "success";
	
	}
}
