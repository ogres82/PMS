package com.jdry.pms.environment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.environment.pojo.EnvironmentArea;
import com.jdry.pms.environment.pojo.EnvironmentPlantMtnRec;
import com.jdry.pms.environment.pojo.VEnvironmentPlantMtnRec;
import com.jdry.pms.environment.service.AreaPlantService;
import com.jdry.pms.environment.service.PlantMtnRecService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class PlantMtnRec implements IController{

	@Resource
	private PlantMtnRecService service;
	
	@Resource
	private AreaPlantService areaPlantService;
	
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
		String recId=null == arg0.getParameter("recId")?"":arg0.getParameter("recId").toString();

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
			    Page<VEnvironmentPlantMtnRec> page =new Page<VEnvironmentPlantMtnRec>(showCount, currentPage);
			    service.queryPlantMtnRecByParam(page, parameter, null);
			    List<VEnvironmentPlantMtnRec> result=(List<VEnvironmentPlantMtnRec>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editPlantMtnRec") || method.equals("viewPlantMtnRec")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VEnvironmentPlantMtnRec minRec = new VEnvironmentPlantMtnRec();
				minRec = service.getVPlantMtnRecById(recId);
				String jsonString = JSON.toJSONString(minRec);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String areaId= null == arg0.getParameter("areaId")?"":arg0.getParameter("areaId").toString();
				String plantMtnDetail= null == arg0.getParameter("plantMtnDetail")?"":arg0.getParameter("plantMtnDetail").toString();
				String plantMtnDate= null == arg0.getParameter("plantMtnDate")?"":arg0.getParameter("plantMtnDate").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				String result = "";
				EnvironmentPlantMtnRec minRec = new EnvironmentPlantMtnRec();
				if(recId !=null && !recId.isEmpty()){
					minRec.setRecId(recId);
				}
				minRec.setAreaId(areaId);
				minRec.setPlantMtnDetail(plantMtnDetail);
				minRec.setPlantMtnPerson(CommUser.getUserName());
				if(plantMtnDate!=null && !plantMtnDate.isEmpty()){
					minRec.setPlantMtnDate(sdf.parse(plantMtnDate));
				}
				minRec.setRemark(remark);
				
				try{
					service.addPlantMtnRec(minRec);
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
			
			if(method.equalsIgnoreCase("deletePlantMtnRec")){

				PrintWriter out = arg1.getWriter();
				String result = "删除失败";
				try{
					
					if(!StringUtil.isEmpty(recId)){
						service.deletePlantMtnRec(recId);
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
				Map<String,Object> param = new HashMap<String,Object>();
				List<EnvironmentArea> area=(List<EnvironmentArea>) areaPlantService.queryAreaByParam(param);
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
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		return "/plantMtnRec/plantMtnRecList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
