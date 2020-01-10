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
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.environment.pojo.EnvironmentCleaningAreaDuty;
import com.jdry.pms.environment.service.CleaningAreaDutyService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class CleaningAreaDuty implements IController{

	@Resource
	private CleaningAreaDutyService service;
	
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
		String planId=null == arg0.getParameter("planId")?"":arg0.getParameter("planId").toString();

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
			    Page<EnvironmentCleaningAreaDuty> page =new Page<EnvironmentCleaningAreaDuty>(showCount, currentPage);
			    service.queryAreaDutyByParam(page, parameter, null);
			    List<EnvironmentCleaningAreaDuty> result=(List<EnvironmentCleaningAreaDuty>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editAeaDuty") || method.equals("viewAeaDuty")){
				
				arg1.setContentType("application/json;charset=utf-8");
				EnvironmentCleaningAreaDuty areaDuty = new EnvironmentCleaningAreaDuty();
				areaDuty = service.getAreaDutyById(planId);
				String jsonString = JSON.toJSONString(areaDuty);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String planCode= null == arg0.getParameter("planCode")?"":arg0.getParameter("planCode").toString();
				String cleaningType= null == arg0.getParameter("cleaningType")?"":arg0.getParameter("cleaningType").toString();
				String cleanBeginDate= null == arg0.getParameter("cleanBeginDate")?"":arg0.getParameter("cleanBeginDate").toString();
				String cleanEndDate= null == arg0.getParameter("cleanEndDate")?"":arg0.getParameter("cleanEndDate").toString();
				String cleaningArea= null == arg0.getParameter("cleaningArea")?"":arg0.getParameter("cleaningArea").toString();
				String cleaningDetail= null == arg0.getParameter("cleaningDetail")?"":arg0.getParameter("cleaningDetail").toString();
				String cleaningPerson= null == arg0.getParameter("cleaningPerson")?"":arg0.getParameter("cleaningPerson").toString();
				String result = "";
				EnvironmentCleaningAreaDuty areaDuty = new EnvironmentCleaningAreaDuty();
				if(planId !=null && !planId.isEmpty()){
					areaDuty.setPlanId(planId);
				}
				areaDuty.setPlanCode(planCode);
				areaDuty.setCleaningType(cleaningType);
				if(cleanBeginDate!=null && !cleanBeginDate.isEmpty()){
					areaDuty.setCleanBeginDate(sdf.parse(cleanBeginDate));
				}
				if(cleanEndDate!=null && !cleanEndDate.isEmpty()){
					areaDuty.setCleanEndDate(sdf.parse(cleanEndDate));
				}
				areaDuty.setCleaningArea(cleaningArea);
				areaDuty.setCleaningDetail(cleaningDetail);
				areaDuty.setCleaningPerson(cleaningPerson);
				
				try{
					service.addAreaDuty(areaDuty);
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
			
			if(method.equalsIgnoreCase("deleteAeaDuty")){

				PrintWriter out = arg1.getWriter();
				String result = "删除失败";
				try{
					
					if(!StringUtil.isEmpty(planId)){
						service.deleteAreaDuty(planId);
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
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		return "/areaDuty/areaDutyList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
