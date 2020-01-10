package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jdry.pms.assertStockManage.pojo.ViewiteminfoModel;
import com.jdry.pms.assertStockManage.service.VoucherAndStockInfoService;
/**
 * 单据详情
 * @author hezuping
 *
 */
@Repository
@Component
public class VoucherAndStockInfoConroller implements IController
{

	@Resource
	VoucherAndStockInfoService voucherAndStockInfoService;

	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("pragma","no-cache");
		resp.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		if(method.equals("getVoucherAndStockDetail"))
		{
			String voucher_id = (String) (req.getParameter("voucher_id") == null ? 1 : req.getParameter("voucher_id"));			  	
			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数			  
		    int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
		    String search = req.getParameter("search") == null ? "" : req.getParameter("search");
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		
			Map<String, Object> parameter = new HashMap();
			parameter.put("voucher_id", voucher_id);
		    parameter.put("search", search);
		    Page page =new Page(showCount, currentPage);
			voucherAndStockInfoService.quaryInstockHisInfo(page, parameter, null);
			
			List<ViewiteminfoModel> result=(List<ViewiteminfoModel>) page.getEntities();
			long count = page.getEntityCount();	
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("total", count);
		    info.put("rows", result);
			String jsonString = JSON.toJSONString(info);
			//传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();
		
		
		}
	}

	@Override
	public String getUrl()
	{
		return "/assertStockManage/showVoucherAndStockDetail";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}
	
	
	
}
