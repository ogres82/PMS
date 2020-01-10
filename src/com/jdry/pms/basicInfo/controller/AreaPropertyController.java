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
import com.jdry.pms.basicInfo.pojo.VAreaProperty;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.lzmh.service.LzmhService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class AreaPropertyController implements IController{

	@Resource
	private AreaPropertyService service;
	@Resource
	private AllAreaService allAreaService;
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
		String communityId=null == arg0.getParameter("communityId")?"":arg0.getParameter("communityId").toString();//员工工号

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
			    Page<VAreaProperty> page =new Page<VAreaProperty>(showCount, currentPage);
			    service.queryAreaPropertyByParam(page, parameter, null);
			    List<VAreaProperty> result=(List<VAreaProperty>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editAreaProperty") || method.equals("viewAreaProperty")){
				
				arg1.setContentType("application/json;charset=utf-8");
				AreaProperty AreaProperty = new AreaProperty();
				AreaProperty = service.getAreaPropertyById(communityId);
				String jsonString = JSON.toJSONString(AreaProperty);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				String communityName= null == arg0.getParameter("communityName")?"":arg0.getParameter("communityName").toString();
				String belongBuildId= null == arg0.getParameter("belongBuildId")?"":arg0.getParameter("belongBuildId").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				
				AreaProperty areaProperty = new AreaProperty();
				if(communityId!=null && !communityId.isEmpty()){
					areaProperty.setCommunityId(communityId);
				}
				areaProperty.setCommunityName(communityName);
				areaProperty.setBelongBuildId(belongBuildId);
				areaProperty.setRemark(remark);
				service.addAreaProperty(areaProperty);
				lzmhService.addPartition(areaProperty);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteAreaProperty")){

				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(communityId)){
						service.deleteAreaProperty(communityId);
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
			
			}else if(method.equalsIgnoreCase("initBuildDrop")){
				
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
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		
	
	}

	@Override
	public String getUrl() {
		
		return "/areaPropertyInfo/areaPropertyList";
	}

	@Override
	public boolean isDisabled() {
		
		return false;
	}

}
