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
import com.jdry.pms.assertStockManage.service.VoucherDetailService;
/**
 * 描述：单据管理
 * @author hezuping
 * 
 */
@Repository
@Component
public class VoucherDetailController implements IController{

	@Resource
    VoucherDetailService voucherDetailService;
	@Override
	public boolean anonymousAccess() 
	{
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException 
	{
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json;charset=utf-8");
		res.setHeader("pragma","no-cache");
		res.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		if(method.equals("getVoucherDetailInfo"))
		{
			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数			  
		    int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
		    //String search = req.getParameter("search") == null ? "" : req.getParameter("search");
		    String search=java.net.URLDecoder.decode(req.getParameter("search") , "UTF-8");
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
		    List resp=voucherDetailService.findVoucherDetailInfo(parameter, page, null);
		    List list=new ArrayList();
			for(int i=0;i<resp.size();i++)
			{
				Map map = new HashMap();
				Object[] obj = (Object[])resp.get(i);
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
				
				String code_detail_name = null == obj[8]?"":obj[8].toString();
				map.put("instok_typename", code_detail_name);
				
				String suppliy_name = null == obj[9]?"":obj[9].toString();
				map.put("suppliy_name", suppliy_name);
				
				String warehouse_name = null == obj[10]?"":obj[10].toString();
				map.put("warehouse_name", warehouse_name);
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
			PrintWriter out = res.getWriter();
			out.print(jsonString);
			out.flush();
		}
		if(method.equals("preQuaryVoucher"))//查询详情
		{
			String voucher_code=null==req.getParameter("voucher_code")?"":req.getParameter("voucher_code");
			Map map=new HashMap();
			map.put("voucher_code", voucher_code);	
			Object[] obj= (Object[]) voucherDetailService.quaryVoucherInfo(map);
			Map jsonmap = new HashMap();
			String voucher_id = null == obj[0]?"":obj[0].toString();
			jsonmap.put("voucher_id", voucher_id);
			
			String voucher_code1 = null == obj[1]?"":obj[1].toString();
			jsonmap.put("voucher_code", voucher_code1);
			
			String occurren_date = null == obj[2]?"":obj[2].toString();
			jsonmap.put("occurren_date", occurren_date);
			
			String owne_stock = null == obj[3]?"":obj[3].toString();
			jsonmap.put("owne_stock", owne_stock);
			
			String t_handler = null == obj[4]?"":obj[4].toString();
			jsonmap.put("t_handler", t_handler);
			
			String suppliy_code = null == obj[5]?"":obj[5].toString();
			jsonmap.put("suppliy_code", suppliy_code);
			
			String orther = null == obj[6]?"":obj[6].toString();
			jsonmap.put("orther", orther);
			
			String instok_type = null == obj[7]?"":obj[7].toString();
			jsonmap.put("instok_type", instok_type);
			
			String suppliy_name = null == obj[8]?"":obj[8].toString();
			jsonmap.put("suppliy_name", suppliy_name);
			
			String code_detail_name = null == obj[9]?"":obj[9].toString();
			jsonmap.put("instok_typename", code_detail_name);
			
			
			String warehouse_name = null == obj[10]?"":obj[10].toString();
			jsonmap.put("warehouse_name", warehouse_name);
			String jsonString = JSON.toJSONString(jsonmap);
			PrintWriter out = res.getWriter();
			out.print(jsonString);
		}
	}

	@Override
	public String getUrl() 
	{
		return "/assertStockManage/voucherDetail";
	}

	@Override
	public boolean isDisabled() 
	{
		return false;
	}

}
