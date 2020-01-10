package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.assertStockManage.service.ItmsfilesService;
import com.jdry.pms.assertStockManage.service.VoucherService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;
/**
 * 入库控制
 * @author hezuping
 * 修改时间 2016-05-31 09:01:49
 */
@Repository
@Component
public class VoucherController implements IController {

	@Resource
	CommUtil commUtil;
	@Resource
	CommUser commUser;
	@Resource
	ItmsfilesService itmsfilesService;
	@Resource
	VoucherService voucherService;
	
	
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings({ "unchecked", "static-access", "rawtypes", "unused" })
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		String method=request.getParameter("method");
		if(method.equals("queryOrder"))
		{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String occurren_date=sd.format(new Date());
			String voucher_code = commUtil.getBusinessId("RK","D");
			
			IUser user=CommUser.getUser();
			String t_handler=user.getCname();
			Map map = new HashMap();
			map.put("voucher_code", voucher_code);
			map.put("t_handler", t_handler);
			map.put("occurren_date", occurren_date);
			String jsonString = JSON.toJSONString(map);
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			
		}
		if(method.equals("getItemFiles"))
		{
			
			int currentPage = request.getParameter("offset") == null ? 1 : Integer.parseInt(request.getParameter("offset"));// 每页行数			  
		    int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
		    String search = request.getParameter("search") == null ? "" : request.getParameter("search");
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		    Map<String, Object> parameter = new HashMap();
		    parameter.put("search", search);
		    Page page =new Page(showCount, currentPage);	
			List res=itmsfilesService.quaryItmsfilesInfo(page, parameter, null);
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
				String suppliy_num = null == obj[13]?"":obj[13].toString();
				map.put("suppliy_num", suppliy_num);
				String type_name = null == obj[14]?"":obj[14].toString();
				map.put("type_name", type_name);
				list.add(map);
				
			}
			page.setEntities(list);
			List result=(List) page.getEntities();
			long count = page.getEntityCount();	
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("total", count);
		    info.put("rows", result);
			String jsonString = JSON.toJSONString(info);
			//传输JSON
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			out.flush();
			
		}
		if(method.equals("getTempData")){//
		 request.setCharacterEncoding("utf8"); 
		 String  selectionss = java.net.URLDecoder.decode( request.getParameter("selectionss"), "UTF-8");// 每页行数		
		 JSONArray jsonArr = JSON.parseArray(selectionss);
		 try{
		 for(int i=0;i<jsonArr.size();i++)
		 {
			 JSONObject obj=JSON.parseObject(jsonArr.get(i).toString()); 
			 
			String bar_code= obj.get("bar_code").toString();
			String suppliy_code=obj.getString("suppliy_code");
			String item_code=obj.getString("item_code");
			String item_flag=obj.getString("item_flag");
			String item_id=obj.getString("item_id");
			String item_name=java.net.URLDecoder.decode(obj.getString("item_name") , "UTF-8");
			String item_type=obj.getString("item_type");
			String item_unit=obj.getString("item_unit");
			
			String suppliy_num=obj.getString("suppliy_num");
			int num=0;
			if(suppliy_num.equals(""))
			{
				
				num=0;
			}else
			{
				num=Integer.parseInt(suppliy_num);
				
			}
			//String type_name=obj.getString("type_name");
			double defu_inprice=obj.getDoubleValue("defu_inprice");
			SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date2String=sp.format(new Date());
			InstockModel stock=new InstockModel();
			stock.setInstock_time(date2String);
			stock.setItem_code(item_code);
			stock.setItem_name(item_name);
			stock.setSuppliy_num(num);
			stock.setUnit_price(defu_inprice);
			stock.setItem_id(item_id);
			stock.setItem_type(item_type);
			boolean flag=voucherService.save(stock);
			System.out.println(flag);
		 }}finally{
			 
			    Map<Object, Object> info = new HashMap<Object, Object>();
			    info.put("succ", "succ");
				String jsonString = JSON.toJSONString(info); 
				PrintWriter out = response.getWriter();
				out.print(jsonString);
				out.flush(); 
		 }
		}
		if(method.equals("getTempDatas"))
		{
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("content-type","text/html;charset=UTF-8");
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("pragma","no-cache");
			response.setHeader("cache-control","no-cache");
			int currentPage = request.getParameter("offset") == null ? 1 : Integer.parseInt(request.getParameter("offset"));// 每页行数			  
		    int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
		    String search = request.getParameter("search") == null ? "" : request.getParameter("search");
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		    Map<String, Object> parameter = new HashMap();
		    parameter.put("search", search);
		    Page page =new Page(showCount, currentPage);	
		    
		    
		    List res=voucherService.quaryInstockInfo(page, parameter, null);
		    List list=new ArrayList();
			for(int i=0;i<res.size();i++)
			{
				Map map = new HashMap();
				Object[] obj = (Object[])res.get(i);	
				String instock_id = null == obj[0]?"":obj[0].toString();
				map.put("instock_id", instock_id);
				String suppliy_code = null == obj[1]?"":obj[1].toString();
				map.put("suppliy_code", suppliy_code);	
				String voucher_id = null == obj[2]?"":obj[2].toString();
				map.put("voucher_id", voucher_id);	
				String suppliy_name = null == obj[3]?"":obj[3].toString();
				map.put("suppliy_name", suppliy_name);	
				String unit_price = null == obj[4]?"":obj[4].toString();
				map.put("unit_price", unit_price);	
				String instock_time = null == obj[5]?"":obj[5].toString();
				map.put("instock_time", instock_time);	
				
				String suppliy_num = null == obj[6]?"":obj[6].toString();
				map.put("suppliy_num", suppliy_num);
				
				String item_code = null == obj[7]?"":obj[7].toString();
				map.put("item_code", item_code);
				
				String item_name = null == obj[8]?"":obj[8].toString();
				map.put("item_name", item_name);
				
				String sum_price = null == obj[9]?"":obj[9].toString();
				map.put("sum_price", sum_price);
				String item_type = null == obj[10]?"":obj[10].toString();
				map.put("item_type", item_type);
				
				String oper_id = null == obj[11]?"":obj[11].toString();
				map.put("oper_id", oper_id);
				String item_id = null == obj[12]?"":obj[12].toString();
				map.put("item_id", item_id);
				
				String stock_type = null == obj[13]?"":obj[13].toString();
				map.put("stock_type", stock_type);
				String stock_lowerlimit = null == obj[14]?"":obj[14].toString();
				map.put("stock_lowerlimit", stock_lowerlimit);
				String stock_uplimit = null == obj[15]?"":obj[15].toString();
				map.put("stock_uplimit", stock_uplimit);
				
				
				
				map.put("add_num", "0");
				
				String oper_name = null == obj[16]?"":obj[16].toString();
				map.put("oper_name", oper_name);
				

				String type_name = null == obj[17]?"":obj[17].toString();
				map.put("type_name", type_name);
				
				
				list.add(map);
				
				
			}
		    page.setEntities(list);
		    List result=(List) page.getEntities();
			String b = JSON.toJSONString(result);				
			long count = page.getEntityCount();	
			 try{
			Map info = new HashMap();
		    info.put("total", count);
		    info.put("rows", result);
			String aa = JSON.toJSONString(info); 
			String jsonString= java.net.URLDecoder.decode(aa, "UTF-8");
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			out.flush();
			 }finally{
			voucherService.deleteStockInfo();
			 }
		}
		if(method.equals("submitVoucherData"))
		{
		    String instockJson = request.getParameter("instockJson") == null ? "" : request.getParameter("instockJson");
		    String orderJson = request.getParameter("orderJson") == null ? "" : request.getParameter("orderJson");
		    JSONArray jsonArr = JSON.parseArray(instockJson);
		    List<InstockModel> instockList=new ArrayList<InstockModel>();
		    for(int i=0;i<jsonArr.size();i++)
		    {
		    	InstockModel ins=JSON.parseObject(jsonArr.get(i).toString(), new TypeReference<InstockModel>(){});
		    	instockList.add(ins);
		    }
		    VoucherModel vo=null;
		    if(!orderJson.equals("")||orderJson!=null)
		    {
		        vo=JSON.parseObject(orderJson.toString(), new TypeReference<VoucherModel>(){});
		        
		    }
		    boolean flag=voucherService.saveVoucherAndInstock(vo, instockList);
		    String msg="";
		    if(flag)
		    {
		    	msg="入库成功";
		    }else
		    {
		    	msg="入库失败";
		    }
		    
		    Map<Object, Object> info = new HashMap<Object, Object>();
		    info.put("msg", msg);
			String jsonString = JSON.toJSONString(info); 
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		return "/assertStockManage/voucherShowList";
	}

	@Override
	public boolean isDisabled() 
	{
		return false;
	}

}
