package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jdry.pms.assertStockManage.pojo.SupplieInfo;
import com.jdry.pms.assertStockManage.service.SupplierInfoService;
import com.jdry.pms.comm.util.CommUtil;
/**
 * 供应商信息
 * @author hezuping
 *
 */
@Repository
@Component
public class SupplierInfoController implements IController{
	@Resource
	CommUtil commUtil;
	@Resource
	SupplierInfoService supplierInfoService;
	@Override
	public boolean anonymousAccess() 
	{
		return false;
	}

	@SuppressWarnings("unchecked")
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
		if(method.equals("listSupplierInfo"))//查询
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
			    Page<SupplieInfo> page =new Page<SupplieInfo>(showCount, currentPage);
			    supplierInfoService.querySupplierInfo(page, parameter, null);  
			    List<SupplieInfo> result=(List<SupplieInfo>) page.getEntities();
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
		if(method.equals("save"))
		{
			 PrintWriter out = response.getWriter();
			 String supplytype_id=null==request.getParameter("suppliy_id")?"":request.getParameter("suppliy_id");
			 String suppliy_code=null==request.getParameter("suppliy_code")?"":request.getParameter("suppliy_code");
			 String suppliy_name=null==request.getParameter("suppliy_name")?"":request.getParameter("suppliy_name");
			 String link_name=null==request.getParameter("link_name")?"":request.getParameter("link_name");
			 String link_phone=null==request.getParameter("link_phone")?"":request.getParameter("link_phone");
			 String link_address=null==request.getParameter("link_address")?"":request.getParameter("link_address");
			 String fax=null==request.getParameter("fax")?"":request.getParameter("fax");
			 String zip_code=null==request.getParameter("zip_code")?"":request.getParameter("zip_code");
			 String url=null==request.getParameter("url")?"":request.getParameter("url");
			 String bank=null==request.getParameter("bank")?"":request.getParameter("bank");
			 String bank_account=null==request.getParameter("bank_account")?"":request.getParameter("bank_account");
			 String linke_moble=null==request.getParameter("linke_moble")?"":request.getParameter("linke_moble");
			 String qq=null==request.getParameter("qq")?"":request.getParameter("qq");
			 String other=null==request.getParameter("other")?"":request.getParameter("other");
			 Map parameter=new HashMap();
			 parameter.put("suppliy_code", suppliy_code);
			 SupplieInfo sup=new SupplieInfo();
			
			 if(supplytype_id!=""){
			 sup.setSuppliy_id(supplytype_id);
			 }
			 sup.setSuppliy_code(suppliy_code);
			 sup.setSuppliy_name(suppliy_name);
			 sup.setLink_name(link_name);
			 sup.setLink_phone(link_phone);
			 sup.setLink_address(link_address);
			 sup.setFax(fax);
			 sup.setZip_code(zip_code);
			 sup.setUrl(url);
			 sup.setBank(bank);
			 sup.setBank(bank_account);
			 sup.setLinke_moble(linke_moble);
			 sup.setQq(qq);
			 sup.setOther(other);
			 boolean flag=supplierInfoService.save(sup);
			 String msg="";
			  if(flag)
			  {
				 msg="1"; 
			  }else
			  {
				  msg="0";
			  }
			  out.print(msg);
			  out.flush();
		}
		if(method.equals("addSupplierInfo"))//回写编码
		{
			String suppliy_code1 = commUtil.getBusinessId("GYS","D");
			Map map = new HashMap();
			map.put("suppliy_code1", suppliy_code1);
			String jsonString = JSON.toJSONString(map);
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			
		}
		if(method.equals("viewSupplierInfo"))
		{
			 String suppliy_code=null==request.getParameter("suppliy_code")?"":request.getParameter("suppliy_code");
			  Map map=new HashMap();
			  map.put("suppliy_code", suppliy_code);
			  SupplieInfo sup=supplierInfoService.getSupplieInfo(map);
			  String jsonString = JSON.toJSONString(sup);
			 PrintWriter out = response.getWriter();
			 out.print(jsonString);
			 out.flush();
		}
		if(method.equals("deleteSupplierInfo"))//删除
		{
			  String suppliy_codes=null==request.getParameter("suppliy_codes")?"":request.getParameter("suppliy_codes");  
			  PrintWriter out = response.getWriter();
			  String msg="";
			  if(!StringUtil.isEmpty(suppliy_codes)){
				  JSONArray jsonArr = JSON.parseArray(suppliy_codes);
				  boolean flag=false;
				  for(int i=0;i<jsonArr.size();i++){
				  String suppliy_code = (String) jsonArr.get(i);
				  flag=supplierInfoService.deleteSupplieInfoById(suppliy_code);
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
		if(method.equals("querySupplierInfo"))
		{
			 Collection<SupplieInfo>  supps=supplierInfoService.querySulierInfos();
			 String jsonString = JSON.toJSONString(supps);
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json;charset=utf-8");
				response.setHeader("pragma","no-cache");
				response.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = response.getWriter();
				out.println(jsonString);
				out.flush();
		}
		
	}

	@Override
	public String getUrl()
	{
		return "/assertStockManage/showSupplierInfo";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}

}
