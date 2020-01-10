package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.WareHouseFiles;
import com.jdry.pms.assertStockManage.service.WareHouseFilesService;
import com.jdry.pms.comm.util.CommUtil;

/**
 * 仓库档案信息
 * @author hezuping
 *
 */
@Repository
@Component
public class WareHouseFilesController implements IController{
	@Resource
	CommUtil commUtil;
	@Resource
	WareHouseFilesService wareHouseFilesService;
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json;charset=utf-8");
		res.setHeader("pragma","no-cache");
		res.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		if(method.equals("getWareHouseInfo"))//查询数据
		{
			  int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数			  
			    int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
			    String search = req.getParameter("search") == null ? "" : req.getParameter("search");
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap();
			    parameter.put("search", search);
			    Page<WareHouseFiles> page =new Page<WareHouseFiles>(showCount, currentPage);
			    wareHouseFilesService.quaryWareHouseFilesInfo(page, parameter, null);
			    List<WareHouseFiles> result=(List<WareHouseFiles>) page.getEntities();
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
		if(method.equals("add"))
		{
			
			String warehouse_code = commUtil.getBusinessId("CK","D");
			Map map = new HashMap();
			map.put("warehouse_code", warehouse_code);
			String jsonString = JSON.toJSONString(map);
			PrintWriter out = res.getWriter();
			out.print(jsonString); 	
			
			
		}
		if(method.equals("save"))//保存数据
		{
			 PrintWriter out = res.getWriter();
			 String warehouse_id=null==req.getParameter("warehouse_id")?"":req.getParameter("warehouse_id");
			 String warehouse_name=null==req.getParameter("warehouse_name")?"":req.getParameter("warehouse_name");
			 String warehouse_code=null==req.getParameter("warehouse_code")?"":req.getParameter("warehouse_code");
			 String warehouse_address=null==req.getParameter("warehouse_address")?"":req.getParameter("warehouse_address");	
			 String link_man=null==req.getParameter("link_man")?"":req.getParameter("link_man");
			 String link_phone=null==req.getParameter("link_phone")?"":req.getParameter("link_phone");
			 String other=null==req.getParameter("other")?"":req.getParameter("other");	
			 WareHouseFiles wh=new WareHouseFiles();
			 wh.setWarehouse_id(warehouse_id);
			 wh.setWarehouse_name(warehouse_name);
			 wh.setWarehouse_code(warehouse_code);
			 wh.setWarehouse_address(warehouse_address);
			 wh.setLink_man(link_man);
			 wh.setLink_phone(link_phone);
			 wh.setOther(other);
			 Collection<WareHouseFiles> wareHouseFiles=new  ArrayList<WareHouseFiles>();
			 wareHouseFiles.add(wh);
			 wareHouseFilesService.saveWareHouseFilesInfo(wareHouseFiles);//后面修改
			 out.print("1");
			 out.flush();
		}
		if(method.equals("preQuaryWareHouseFilese"))//预查询
		{
			  String warehouse_code=null==req.getParameter("warehouse_code")?"":req.getParameter("warehouse_code");
			  Map map=new HashMap();
			  map.put("warehouse_code", warehouse_code);
			  WareHouseFiles wh=wareHouseFilesService.findWareHouseFilesByCode(map);
			  String jsonString = JSON.toJSONString(wh);
			  PrintWriter out = res.getWriter();
			  out.print(jsonString);
			  out.flush();
			
		}
		if(method.equals("deleteWareHouseFiles"))//更加编码删除数据
		{
			 String warehouse_codes=null==req.getParameter("warehouse_codes")?"":req.getParameter("warehouse_codes");  
			  PrintWriter out = res.getWriter();
			  String msg="";
			  if(!StringUtil.isEmpty(warehouse_codes)){
				  JSONArray jsonArr = JSON.parseArray(warehouse_codes);
				  boolean flag=false;
				  for(int i=0;i<jsonArr.size();i++){
				  String warehouse_code = (String) jsonArr.get(i);
				  flag=wareHouseFilesService.deleteSupplieInfoById(warehouse_code);
				  }
				  if(flag)
				  {
					 msg="删除成功"; 
				  }else
				  {
					  msg="删除失败";
				  }
				  String jsonString = JSON.toJSONString(msg);
				  out.print(jsonString);
				  out.flush();
			  }

		}
		if(method.equals("queryWarHouesInfos"))
		{
			Collection<WareHouseFiles>  ls=wareHouseFilesService.queryWarHouesInfos();
			String jsonString = JSON.toJSONString(ls);
			res.setCharacterEncoding("utf-8");
			res.setContentType("application/json;charset=utf-8");
			res.setHeader("pragma","no-cache");
			res.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = res.getWriter();
			out.println(jsonString);
			out.flush();
			
		}
		
	}

	@Override
	public String getUrl() {
		return "/assertStockManage/warHouseFilesList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
