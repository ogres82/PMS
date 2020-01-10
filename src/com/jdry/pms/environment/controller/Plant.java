package com.jdry.pms.environment.controller;

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
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.environment.pojo.EnvironmentPlant;
import com.jdry.pms.environment.pojo.VEnvironmentPlant;
import com.jdry.pms.environment.service.PlantService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class Plant implements IController{

	@Resource
	private PlantService service;
	
	@Resource
	private PropertyManagerService propertyManagerService;
	
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
		String plantId=null == arg0.getParameter("plantId")?"":arg0.getParameter("plantId").toString();

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
			    Page<VEnvironmentPlant> page =new Page<VEnvironmentPlant>(showCount, currentPage);
			    service.queryPlantByParam(page, parameter, null);
			    List<VEnvironmentPlant> result=(List<VEnvironmentPlant>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editPlant") || method.equals("viewPlant")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VEnvironmentPlant plant = new VEnvironmentPlant();
				plant = service.getVEnvironmentPlantById(plantId);
				String jsonString = JSON.toJSONString(plant);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				
				String plantMtnStandard= null == arg0.getParameter("plantMtnStandard")?"":arg0.getParameter("plantMtnStandard").toString();
				String plantName= null == arg0.getParameter("plantName")?"":arg0.getParameter("plantName").toString();
				String plantType= null == arg0.getParameter("plantType")?"":arg0.getParameter("plantType").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				String result = "";
				EnvironmentPlant plant = new EnvironmentPlant();
				if(plantId !=null && !plantId.isEmpty()){
					plant.setPlantId(plantId);
				}
				plant.setPlantMtnStandard(plantMtnStandard);
				plant.setPlantName(plantName);
				plant.setPlantType(plantType);
				plant.setRemark(remark);
				
				try{
					service.addPlant(plant);
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
			
			if(method.equalsIgnoreCase("deletePlant")){

				PrintWriter out = arg1.getWriter();
				String result = "删除失败";
				try{
					
					if(!StringUtil.isEmpty(plantId)){
						service.deletePlant(plantId);
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
				List<DirDirectoryDetail> positions=propertyManagerService.getDirectoryLikeCode("plant_type");
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
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		return "/plant/plantList";
	}

	@Override
	public boolean isDisabled() {
		
		return false;
	}

}
