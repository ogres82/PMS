package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.bstek.bdf2.core.controller.IController;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.service.InstockInformationService;
/**
 * 库存控制器
 * 时间：2016-04-08:00：24
 * @author hezuping
 *
 */
@Repository
@Component
public class InstockInformationController implements IController{
	@Resource
	InstockInformationService instockInformationService;
	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		String method=request.getParameter("method");
		if(method.equals("getInstockInfo"))
		{
			int currentPage = request.getParameter("offset") == null ? 1 : Integer.parseInt(request.getParameter("offset"));// 每页行数			  
		    int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
		   // String search = request.getParameter("search") == null ? "" : request.getParameter("search");
		    String search=java.net.URLDecoder.decode(request.getParameter("search") , "UTF-8");
		    if(search.equals("undefined"))
		    {
		    	search="";
		    }
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		    Map<String, Object> parameter = new HashMap();
		    parameter.put("search", search);
		    Page page =new Page(showCount, currentPage);	
		    List res=instockInformationService.getInsockInfomation(parameter, page, null);
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
				String instock_id = null == obj[11]?"":obj[11].toString();
				map.put("instock_id", instock_id);
				String warehouse_name = null == obj[12]?"":obj[12].toString();
				map.put("warehouse_name", warehouse_name);
				
				String code_detail_name = null == obj[13]?"":obj[13].toString();
				map.put("code_detail_name", code_detail_name);
				
				String stock_lowerlimit = null == obj[14]?"":obj[14].toString();
				String stock_uplimit = null == obj[15]?"":obj[15].toString();
				String flag="";
				if(!suppliy_num.equals("")&&((!stock_lowerlimit.equals(""))||(!suppliy_num.equals("")&&(!stock_uplimit.equals("")))))
					
				{
					if((Integer.parseInt(stock_lowerlimit)>=Integer.parseInt(suppliy_num))&&(Integer.parseInt(suppliy_num)>0))
					{
					  	
						flag="1";//下线
						map.put("flag", flag);
					}
					
					if((Integer.parseInt(suppliy_num))>=Integer.parseInt(stock_uplimit))
					{
					  	
						flag="2";//库存上下标识
						map.put("flag", flag);
					}else if(Integer.parseInt(suppliy_num)>Integer.parseInt(stock_lowerlimit)&&Integer.parseInt(suppliy_num)<=Integer.parseInt(stock_uplimit))
					{
						flag="4";//库存上下标识
						map.put("flag", flag);
						
					}
					if(Integer.parseInt(suppliy_num)==0)
					{
						flag="3";//库存不足
						map.put("flag", flag);	
					}
					
				}
				
				
				
				
				map.put("stock_lowerlimit", stock_lowerlimit);
				map.put("stock_uplimit", stock_uplimit);
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
		if(method.equals("preQuaryInstockInfo"))
		{
		String instock_id=null==request.getParameter("instock_id")?"":request.getParameter("instock_id");
		Map map=new HashMap();
		map.put("instock_id", instock_id);	
		Object[] obj=(Object[]) instockInformationService.quaryInstockInfo(map);
		Map jsonmap = new HashMap();
		jsonmap.put("type_name", obj[0]);
		jsonmap.put("item_name", obj[2]);
		jsonmap.put("item_type", obj[3]);
		jsonmap.put("unit_price", obj[4]);
		jsonmap.put("sum_price", obj[5]);
		jsonmap.put("suppliy_num", obj[6]);
		jsonmap.put("owne_stock", obj[7]);
		jsonmap.put("bar_code", obj[8]);
		jsonmap.put("item_unit", obj[9]);
		jsonmap.put("item_flag", obj[10]);
		jsonmap.put("warehouse_name", obj[12]);
		jsonmap.put("item_Ptype", obj[13]);
		
		jsonmap.put("stock_lowerlimit", obj[14]);
		jsonmap.put("stock_uplimit", obj[15]);
		
		String jsonString = JSON.toJSONString(jsonmap);
		PrintWriter out = response.getWriter();
		out.print(jsonString);
	    out.flush();
		}
	}

	@Override
	public String getUrl() {
		return "/assertStockManage/showInstockInformation";
	}

	@Override
	public boolean isDisabled() 
	{
		return false;
	}

}
