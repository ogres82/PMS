package com.jdry.pms.assertStockManage.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.Titmsfiles;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.assertStockManage.service.VoucherService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;

@Component
public class VoucherView 
{
	
	@Resource
	VoucherService voucherService;
	@Resource
	CommUtil commUtil;
	@Resource
	CommUser commUser;
	
	private String type="";
	
	
	@SuppressWarnings("unchecked")
	@DataProvider
	public Collection<Map<String, Object>> generVoucherOrder()
	{
		
		boolean flag=voucherService.deleteStockInfo();
		/*if(type.equals("1"))
		{   
			type="0";
			return null;
			
		}else{*/
		String voucher_code = commUtil.getBusinessId("RK","D");
		String t_handler=CommUser.getUserName();
		Map map = new HashMap();
		map.put("voucher_code", voucher_code);
		map.put("t_handler", t_handler);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		return list;
		//}
	}
		
	@Expose
	public boolean reportInstock(List<Titmsfiles> instockList) {
		boolean flag=false;
		if(instockList != null && instockList.size()>0) {
			for(Titmsfiles item:instockList)
			{
				InstockModel stock=new InstockModel();	
				//stock.setInstock_time(new Date());
				stock.setItem_code(item.getItem_code());
				stock.setItem_name(item.getItem_name());
				stock.setSuppliy_num(0);
				stock.setUnit_price(item.getDefu_inprice());
				stock.setItem_id(item.getItem_id());
				flag=voucherService.save(stock);
				
			}
			
		}
		return flag;	
	}
	
	@DataProvider
	public void getInstock(Page page,Map<String, Object> parameter, Criteria criteria)
	{
		
		voucherService.quaryInstockInfo(page, parameter, criteria);
		
	}
	
	
	@Expose
	public boolean editInstock(Map<String, Object> ls)
	{
		String instock_id=null==ls.get("instock_id")?"":ls.get("instock_id").toString();
		String unit_price=null==ls.get("unit_price")?"":ls.get("unit_price").toString();
		String stock_type=null==ls.get("stock_type")?"":ls.get("stock_type").toString();
		
		String suppliy_num=null==ls.get("suppliy_num")?"":ls.get("suppliy_num").toString();
		String sum_price=null==ls.get("sum_price")?"":ls.get("sum_price").toString();
		String item_code=null==ls.get("item_code")?"":ls.get("item_code").toString();
		
		InstockModel stock=new InstockModel();
		stock.setInstock_id(instock_id);
	    stock.setItem_code(item_code);
       if(stock_type.equals("CK")){
	      stock.setSuppliy_num(-Integer.parseInt(suppliy_num));	
		}else{
		stock.setSuppliy_num(Integer.parseInt(suppliy_num));
		}
		stock.setSum_price(Double.parseDouble(sum_price));
		stock.setUnit_price(Double.parseDouble(unit_price));
		try {
			return voucherService.updateInstockInfo(stock);
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Expose
	public boolean operationInstock(Map<String, Object> vouches)
	{
		
	String voucher_code=null==vouches.get("voucher_code")?"":vouches.get("voucher_code").toString();
	String occurren_date=null==vouches.get("occurren_date")?"":vouches.get("occurren_date").toString();
	String owne_stock=null==vouches.get("owne_stock")?"":vouches.get("owne_stock").toString();
	SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	
	
    Date date = null;
	try {
		date = sdf1.parse(occurren_date);
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}   
	
	
	
	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 String dd = format.format(date);
	String t_handler=null==vouches.get("t_handler")?"":vouches.get("t_handler").toString();
	String suppliy_code=null==vouches.get("suppliy_code")?"":vouches.get("suppliy_code").toString();
	List<InstockModel> instockList=(List<InstockModel>) vouches.get("instockList");
	VoucherModel vo=new VoucherModel();
	vo.setOccurren_date(dd.toString());
	vo.setOwne_stock(owne_stock);
	vo.setSuppliy_code(suppliy_code);
	vo.setT_handler(t_handler);
	vo.setVoucher_code(voucher_code);
	vo.setOrther("");
	boolean flag=false;
	flag=voucherService.saveVoucherAndInstock(vo,instockList);
	/*if(flag)
	{
		type="1";//清空数据
		generVoucherOrder();
	}*/
	return flag;
	}
	
}
