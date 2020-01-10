package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.service.SupplieTypeService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
/**
 * 描述：物资类别管理
 * @author hezuping
 *
 */
@Repository
@Component
public class SupplieTypeController implements IController {
	@Resource
	CommUtil commUtil;
	@Resource
	SupplieTypeService supplieTypeService;
	@Resource
	DirectoryService directoryService;
	@Override
	public boolean anonymousAccess() {
		return false;
	}
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)throws IOException, ServletException {
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html;charset=utf-8");
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("application/json;charset=utf-8");
		arg1.setHeader("pragma","no-cache");
		arg1.setHeader("cache-control","no-cache");
		String method=arg0.getParameter("method");
	  if(method.equals("getSuplieTypeInfo"))
	  {
		    int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
		    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
		    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		    Map<String, Object> parameter = new HashMap();
		    parameter.put("search", search);
		    Page<SupplieType> page =new Page<SupplieType>(showCount, currentPage);
		    quarySupplieType(page, parameter, null);
		    List<SupplieType> result=(List<SupplieType>) page.getEntities();
			String b = JSON.toJSONString(result);				
			long count = page.getEntityCount();	
			
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("total", count);
		    info.put("rows", result);
			String jsonString = JSON.toJSONString(info);
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.print(jsonString);
			out.flush();
	  
	  
	  }
	  if(method.equals("add"))
	  {
		  String supply_code = commUtil.getBusinessId("SP","D");
			Map map = new HashMap();
			map.put("supply_code", supply_code);
			String jsonString = JSON.toJSONString(map);
			PrintWriter out = arg1.getWriter();
			out.print(jsonString);  
		  
		  
	  }
	  if(method.equals("save"))
	  {
		 String supplytype_id=null==arg0.getParameter("supplytype_id")?"":arg0.getParameter("supplytype_id");
		 String supply_code= arg0.getParameter("supply_code");
		 String type_name=arg0.getParameter("type_name");
		 String type_orther=arg0.getParameter("type_orther");
		 String parent_supp_id=null==arg0.getParameter("parent_supp_id")?"":arg0.getParameter("parent_supp_id");
		 SupplieType st=new SupplieType();
		 st.setSupply_code(supply_code);
		 st.setSupplytype_id(supplytype_id);
		 st.setType_name(type_name);
		 st.setType_orther(type_orther);
		 st.setParent_supp_id(parent_supp_id);
		 String flag=add(st,"0");
		 PrintWriter out = arg1.getWriter();
		 out.print(flag);
		 out.flush();
		  
	  }
	  if(method.equals("preQuarySupplieType"))
	  {
		  String supply_code=null==arg0.getParameter("supply_code")?"":arg0.getParameter("supply_code");
		  Map map=new HashMap();
		  map.put("supply_code", supply_code);
		  SupplieType supplieType= supplieTypeService.getSupplieTypeInfo(map);
		  //Map<Object, Object> info = new HashMap<Object, Object>();
		  //info.put("SupplieType", supplieType);
		  String jsonString = JSON.toJSONString(supplieType);
			
		  //传输JSON
			PrintWriter out = arg1.getWriter();
			out.print(jsonString);
			out.flush();
	  }
	  if(method.equals("deleteSupplietye"))
	  {
		  String supply_codes=null==arg0.getParameter("supply_codes")?"":arg0.getParameter("supply_codes");  
		  PrintWriter out = arg1.getWriter();
		  if(!StringUtil.isEmpty(supply_codes)){
			  JSONArray jsonArr = JSON.parseArray(supply_codes);
			  for(int i=0;i<jsonArr.size();i++){
			  String supply_code = (String) jsonArr.get(i);
			  boolean flag=supplieTypeService.deleteSupplieTypeById(supply_code);
			  String msg="";
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
	  if(method.equals("querySuppTypeList"))
	  {
		    Collection<SupplieType>  supps=supplieTypeService.querySuppTypeList();
			String jsonString = JSON.toJSONString(supps);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
	  }
	  if(method.equals("initDropAll"))
	  {
			List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) supplieTypeService.getDirectoryLikeCode("item_");
			List<DirDirectoryDetail> ls=new ArrayList<DirDirectoryDetail>();
			for(int i=0;i<positions.size();i++)
			{
				DirDirectoryDetail di=new DirDirectoryDetail();
				di.setId(positions.get(i).getId());
				di.setCode_detail_name(positions.get(i).getCode_detail_name());
				di.setCode(positions.get(i).getCode());
				ls.add(di);
			}
			
			String jsonString = JSON.toJSONString(ls);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		  
		  
		  
	  }

	}

	/**
	 * 添加,修改类别信息
	 * @param supplieTypes
	 * @param stat
	 */
	public String add(SupplieType supplieType,String stat)
	{
		
		return supplieTypeService.saveSupplieType(supplieType,stat);
		
	}
	/**
	 * 查询物资信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SupplieType> quarySupplieType(Page<SupplieType> page, Map<String, Object> parameter,Criteria criteria)
	{
		return supplieTypeService.querySuppliesInfo(page, parameter, criteria);
	}
	
	/**
	 * 指定路径
	 */
	@Override
	public String getUrl() 
	{
		return"/assertStockManage/showSupplieTyeList";
	}

	
	@Override
	public boolean isDisabled()
	{
		return false;
	}
	
	public static String getUTF_8String(String str) throws UnsupportedEncodingException{
		byte[] arrayStr = str.getBytes("iso-8859-1");
		String rtnStr = new String(arrayStr, "UTF-8");
		return rtnStr;
	}

}
