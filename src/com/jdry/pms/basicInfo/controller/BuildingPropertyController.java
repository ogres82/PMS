package com.jdry.pms.basicInfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingHousetype;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.VBuildingProperty;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.lzmh.service.LzmhService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class BuildingPropertyController implements IController{

	@Resource
	private BuildingPropertyService service;
	
	@Resource
	private AllAreaService allAreaService;
	
	@Resource
	private AreaPropertyService areaPropertyService;
	
	@Resource
	private PropertyManagerService propertyManagerService;
	
	@Resource
	private LzmhService lzmhService;
	
	@Override
	public boolean anonymousAccess() {
		
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {


		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String storiedBuildId=null == arg0.getParameter("storiedBuildId")?"":arg0.getParameter("storiedBuildId").toString();
		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<VBuildingProperty> page =new Page<VBuildingProperty>(showCount, currentPage);
			    service.queryBuildingPropertyByParam(page, parameter, null);
			    List<VBuildingProperty> result=(List<VBuildingProperty>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editBuildingProperty") || method.equals("viewBuildingProperty")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VBuildingProperty vBuildingProperty = new VBuildingProperty();
				vBuildingProperty = service.getVBuildingPropertyById(storiedBuildId);
				String jsonString = JSON.toJSONString(vBuildingProperty);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				String storiedBuildName= null == arg0.getParameter("storiedBuildName")?"":arg0.getParameter("storiedBuildName").toString();
				String belongCommId= null == arg0.getParameter("belongCommId")?"":arg0.getParameter("belongCommId").toString();
				String storiedType= null == arg0.getParameter("storiedType")?"":arg0.getParameter("storiedType").toString();
				String houseTypeId= null == arg0.getParameter("houseTypeId")?"":arg0.getParameter("houseTypeId").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				
				BuildingProperty buildingProperty = new BuildingProperty();
				if(storiedBuildId!=null && !storiedBuildId.isEmpty()){
					buildingProperty.setStoriedBuildId(storiedBuildId);
				}
				buildingProperty.setStoriedBuildName(storiedBuildName);
				buildingProperty.setBelongCommId(belongCommId);
				buildingProperty.setRemark(remark);
				buildingProperty.setStoriedType(storiedType);
				buildingProperty.setHouseTypeId(houseTypeId);
				service.addBuildingProperty(buildingProperty);
				lzmhService.addTenement(buildingProperty);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteBuildingProperty")){

//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(storiedBuildId)){
						service.deleteBuildingProperty(storiedBuildId);
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}
			
			}
			
			if(method.equalsIgnoreCase("initAllAreaDrop")){
				
				Map<String,Object> param = new HashMap<String,Object>();
				List<AllArea> allAreas = (List<AllArea>) allAreaService.queryAllAreaByParam(param);
				String jsonString = JSON.toJSONString(allAreas);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				
			}

			if(method.equalsIgnoreCase("initAreaPropertyDrop")){
				String buildId= null == arg0.getParameter("buildId")?"":arg0.getParameter("buildId").toString();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("buildId", buildId);
				List<AreaProperty> areaProperty = (List<AreaProperty>) areaPropertyService.queryAreaPropertyByParam(param);
				String jsonString = JSON.toJSONString(areaProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("initHouseTypeDrop")){
				List<BuildingHousetype> houseType = service.queryHouseType();
				String jsonString = JSON.toJSONString(houseType);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("initDropAll")){
				
				List<DirDirectoryDetail> dir=propertyManagerService.getDirectoryLikeCode("storied_type");
				String jsonString = JSON.toJSONString(dir);
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
		
		return "/buildingPropertyInfo/buildingPropertyList";
	}

	@Override
	public boolean isDisabled() {
		
		return false;
	}

}
