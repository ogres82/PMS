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
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.pojo.Titmsfiles;
import com.jdry.pms.assertStockManage.service.ItmsfilesService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;
/**
 * 物品档案信息
 * @author hezuping
 *
 */
@Repository
@Component
public class ItmsFilesController implements IController
{
	@Resource
	CommUtil commUtil;
	@Resource
	CommUser commUser;
	@Resource
	ItmsfilesService itmsfilesService;

	@Override
	public boolean anonymousAccess() {
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
		if(method.equals("getItmsFilesInfo"))
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
			    Page<Titmsfiles> page =new Page<Titmsfiles>(showCount, currentPage);
			    List res= itmsfilesService.quaryItmsfilesInfo(page, parameter, null);
			    
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
			    List<Titmsfiles> result=(List<Titmsfiles>) page.getEntities();
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
		if(method.equalsIgnoreCase("findSuppTypInfo")){
			
			
			String parent_supp_id=null==request.getParameter("parent_supp_id")?"":request.getParameter("parent_supp_id");
			List<SupplieType> positions=null;
			if(parent_supp_id=="")
			{
				 positions=(List<SupplieType>) itmsfilesService.getDirectoryLikeCode("item");	
				
			}else
			{
				 positions=(List<SupplieType>) itmsfilesService.getDirectoryLikeCode(parent_supp_id);
				
			}
			
			
			
			String jsonString = JSON.toJSONString(positions);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("pragma","no-cache");
			response.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = response.getWriter();
			out.println(jsonString);
			out.flush();
		}
		if(method.equals("add"))
		{
			String bar_code = commUtil.getBusinessId("WPDA","D");
			Map map = new HashMap();
			map.put("bar_code", bar_code);
			String jsonString = JSON.toJSONString(map);
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			
			
		}
		if(method.equals("save"))//保存及修改
		{
			 PrintWriter out = response.getWriter();
			 String item_id=null==request.getParameter("item_id")?"":request.getParameter("item_id");
			 String item_code=null==request.getParameter("item_code")?"":request.getParameter("item_code");
			 String bar_code=null==request.getParameter("bar_code")?"":request.getParameter("bar_code");
			 String item_name=null==request.getParameter("item_name")?"":request.getParameter("item_name");
			 String item_type=null==request.getParameter("item_type")?"":request.getParameter("item_type");
			 String item_unit=null==request.getParameter("item_unit")?"":request.getParameter("item_unit");
			 String stock_uplimit=null==request.getParameter("stock_uplimit")?"0":request.getParameter("stock_uplimit");
			 String stock_lowerlimit=null==request.getParameter("stock_lowerlimit")?"0":request.getParameter("stock_lowerlimit");
			 String defu_inprice=null==request.getParameter("defu_inprice")?"0":request.getParameter("defu_inprice");
			 String defu_outprice=null==request.getParameter("defu_outprice")?"0":request.getParameter("defu_outprice");
			 String stock_avgprice=null==request.getParameter("stock_avgprice")?"0":request.getParameter("stock_avgprice");
			 String item_flag=null==request.getParameter("item_flag")?"":request.getParameter("item_flag");
			 String item_Ptype=null==request.getParameter("item_Ptype")?"":request.getParameter("item_Ptype");
			 Titmsfiles tf=new Titmsfiles();
			 tf.setItem_id(item_id);
			 tf.setItem_code(item_code);
			 tf.setItem_type(item_type);
			 tf.setItem_name(item_name);
			 tf.setItem_unit(item_unit);
			 tf.setItem_Ptype(item_Ptype);
			 tf.setBar_code(bar_code);
			 tf.setDefu_inprice(Double.parseDouble(defu_inprice));
			 tf.setDefu_outprice(Double.parseDouble(defu_outprice));
			 tf.setStock_avgprice(Double.parseDouble(stock_avgprice));
			 tf.setStock_uplimit(Double.parseDouble(stock_uplimit));
			 tf.setStock_lowerlimit(Double.parseDouble(stock_lowerlimit));
			 tf.setItem_flag(item_flag);
			 Collection<Titmsfiles> items=new  ArrayList<Titmsfiles>();
			 items.add(tf);
			 String flag=itmsfilesService.saveItmsfilesInfo(items);
			 out.print(JSON.toJSONString(flag));
			 out.flush();
			
		}
		if(method.equals("preQuaryItmsFilese"))
		{
		 String bar_code=null==request.getParameter("bar_code")?"":request.getParameter("bar_code");
		 Map map=new HashMap();
		 map.put("bar_code", bar_code);
		 Titmsfiles file=itmsfilesService.findItmsFilesByCode(map);
		 String jsonString = JSON.toJSONString(file);
		  PrintWriter out = response.getWriter();
		  out.print(jsonString);
		  out.flush();
		}
		if(method.equals("deleteItmsFiles"))
		{
			  String bar_codes=null==request.getParameter("bar_codes")?"":request.getParameter("bar_codes");  
			  PrintWriter out = response.getWriter();
			  String msg="";
			  if(!StringUtil.isEmpty(bar_codes)){
				  JSONArray jsonArr = JSON.parseArray(bar_codes);
				  boolean flag=false;
				  for(int i=0;i<jsonArr.size();i++){
				  String bar_code = (String) jsonArr.get(i);
				  flag=itmsfilesService.deleteItmsFilesInfoById(bar_code);
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
		
		
		
		
	}

	@Override
	public String getUrl() {
		return "/assertStockManage/showSItmsFilesInfo";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
