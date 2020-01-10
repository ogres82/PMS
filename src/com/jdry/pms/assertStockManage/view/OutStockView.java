package com.jdry.pms.assertStockManage.view;

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
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.assertStockManage.service.OutStockService;
import com.jdry.pms.assertStockManage.service.VoucherService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;

@Component
public class OutStockView {
	
	@Resource
	CommUtil commUtil;
	@Resource
	CommUser commUser;
	@Resource
	OutStockService outStockService;
	
	@Resource
	VoucherService voucherService;
	@SuppressWarnings("unchecked")
	@DataProvider
	public Collection<Map<String, Object>> generOutStockOrder()
	{
		boolean flag=voucherService.deleteStockInfo();
		if(flag){
		  return null;
		}else{
		String voucher_code = commUtil.getBusinessId("CK","D");
		String t_handler=CommUser.getUserName();
		Map map = new HashMap();
		map.put("voucher_code", voucher_code);
		map.put("t_handler", t_handler);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		return list;
	  }
	}
	
	
	@Expose
	public String editOutstock(Map<String, Object> ls)
	{
		
		String voucher_code=null==ls.get("voucher_code")?"":ls.get("voucher_code").toString();
		String occurren_date=null==ls.get("occurren_date")?"":ls.get("occurren_date").toString();
		String owne_stock=null==ls.get("owne_stock")?"":ls.get("owne_stock").toString();
		SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		 Date date = null;
		try {
			date = sdf1.parse(occurren_date);
		} catch (ParseException e) {
			
		}
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 String dd = format.format(date);
		String t_handler=null==ls.get("t_handler")?"":ls.get("t_handler").toString();
		String suppliy_code=null==ls.get("suppliy_code")?"":ls.get("suppliy_code").toString();
		List<InstockModel> instockList=(List<InstockModel>) ls.get("ls");
		VoucherModel vo=new VoucherModel();
		vo.setOccurren_date(dd.toString());
		vo.setOwne_stock(owne_stock);
		vo.setSuppliy_code(suppliy_code);
		vo.setT_handler(t_handler);
		vo.setVoucher_code(voucher_code);
		boolean flag=outStockService.OutStockInfo(instockList,vo);
		if(flag)
		{
			return"1";
		}else
		return"0";
	}

}
