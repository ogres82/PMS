package com.jdry.pms.environment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.environment.pojo.Bdf2User;
import com.jdry.pms.environment.pojo.EnvironmentArea;
import com.jdry.pms.environment.pojo.EnvironmentAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlantNum;
import com.jdry.pms.environment.service.AreaPlantService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class AreaPlant implements IController{

	@Resource
	private AreaPlantService service;
	
	@Resource
	private PropertyManagerService propertyManagerService;
	
	@Override
	public boolean anonymousAccess() {
		return true;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {

		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String areaId=null == arg0.getParameter("areaId")?"":arg0.getParameter("areaId").toString();

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<VAreaPlant> page =new Page<VAreaPlant>(showCount, currentPage);
			    service.queryVAreaPlantByParam(page, parameter, null);
			    List<VAreaPlant> result=(List<VAreaPlant>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			//区域植被
			if(method.equalsIgnoreCase("areaPlantList")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    parameter.put("areaId", areaId);
			    Page<VAreaPlantNum> page =new Page<VAreaPlantNum>(showCount, currentPage);
			    service.queryVAreaPlantNumByParam(page, parameter, null);
			    List<VAreaPlantNum> result=(List<VAreaPlantNum>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editArea")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VAreaPlant areaPlant = new VAreaPlant();
				areaPlant = service.getAreaPlantById(areaId);
				String jsonString = JSON.toJSONString(areaPlant);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equalsIgnoreCase("editAreaPlant")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VAreaPlant areaPlant = new VAreaPlant();
				areaPlant = service.getAreaPlantById(areaId);
				String jsonString = JSON.toJSONString(areaPlant);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				
				String areaName= null == arg0.getParameter("areaName")?"":arg0.getParameter("areaName").toString();
				String areaResPerson= null == arg0.getParameter("areaResPerson")?"":arg0.getParameter("areaResPerson").toString();
				String areaGisId= null == arg0.getParameter("areaGisId")?"":arg0.getParameter("areaGisId").toString();
				String areaGisArea= null == arg0.getParameter("areaGisArea")?"":arg0.getParameter("areaGisArea").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				String areaPlantInfo = null == arg0.getParameter("areaPlantInfo")?"":arg0.getParameter("areaPlantInfo").toString();
				String result = "";
				EnvironmentArea area = new EnvironmentArea();
				if(areaId !=null && !areaId.isEmpty()){
					area.setAreaId(areaId);
				}
				area.setAreaName(areaName);
				area.setAreaResPerson(areaResPerson);
				area.setAreaGisId(areaGisId);
				if(!areaGisArea.isEmpty()){
					area.setAreaGisArea(BigDecimal.valueOf(Double.parseDouble(areaGisArea)));
				}
				area.setRemark(remark);
				
				try{
					String id = service.addArea(area);
					if(!areaPlantInfo.isEmpty()){
						List<EnvironmentAreaPlant> objs = JSON.parseArray(areaPlantInfo, EnvironmentAreaPlant.class);
						if(objs.size()>0){
							for(EnvironmentAreaPlant a:objs){
								Map<String,Object> param = new HashMap<String,Object>();
								param.put("areaId", id);
								param.put("plantId", a.getPlantId());
								a.setAreaId(id);
								service.addAreaPlant(a);
								result = "保存成功";
							}
						}else{
							service.deleteAreaPlant(areaId);
						}
					}
						
					result = "保存成功";
				}catch(Exception e){
					result = "保存失败";
					e.printStackTrace();
				}finally{
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.print(JSON.toJSONString(result));
					out.flush();
				}
				
			}
			
			if(method.equals("saveAreaPlant")){
				
				String areaPlantInfo = null == arg0.getParameter("areaPlantInfo")?"":arg0.getParameter("areaPlantInfo").toString();
				String result = "";
				List<EnvironmentAreaPlant> objs = JSON.parseArray(areaPlantInfo, EnvironmentAreaPlant.class);
				try{
					for(EnvironmentAreaPlant area:objs){
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("areaId", area.getAreaId());
						param.put("plantId", area.getPlantId());
						
						boolean flag = service.checkUnique(param);
						if(flag){
							
						}else{
							service.addAreaPlant(area);
						}
						result = "保存成功";
					}
					
				}catch(Exception e){
					result = "保存失败";
					e.printStackTrace();
				}finally{
					
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.print(JSON.toJSONString(result));
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("deleteArea")){

				PrintWriter out = arg1.getWriter();
				String result = "删除失败";
				try{
					
					if(!StringUtil.isEmpty(areaId)){
						service.deleteArea(areaId);
						result = "删除成功";
					}

				}catch(Exception e){
					result = "删除失败";
					e.printStackTrace();
				}finally{
					String jsonString = JSON.toJSONString(result);
					out.println(jsonString);
					out.flush();
				}
			
			}else if(method.equalsIgnoreCase("initDropAll")){
				List<DirDirectoryDetail> positions=propertyManagerService.getDirectoryLikeCode("cleaning_type");
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("userInfo")){
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("search", search);
				List<Bdf2User> buildingProperty = service.queryBdf2UserByParam(param);
				String jsonString = JSON.toJSONString(buildingProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			
			if(method.equalsIgnoreCase("checkUnique")){
			    String areaGisId = arg0.getParameter("areaGisId") == null ? "" : arg0.getParameter("areaGisId");
			    VAreaPlant area = service.getAreaByGisId(areaGisId);
			    String jsonString = JSON.toJSONString(area);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			
			if(method.equalsIgnoreCase("bsEmpInfo")){  //下拉搜索框
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("keyword", keyword);
				param.put("depart", "depart");
				List<VEmp> emp = service.queryEmpByKeyword(param);
				String jsonString = JSON.toJSONString(emp);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("checkUniquePlant")){ 
				Map<String,Object> param = new HashMap<String,Object>();
				String plantId = arg0.getParameter("plantId") == null ? "" : arg0.getParameter("plantId");
				param.put("areaId", areaId);
				param.put("plantId", plantId);
				boolean flog = service.checkUnique(param);
				String jsonString = JSON.toJSONString(flog);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		return "/areaPlant/areaPlantList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
