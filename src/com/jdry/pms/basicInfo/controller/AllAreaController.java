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

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.lzmh.service.LzmhService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class AllAreaController implements IController{

	@Resource
	private AllAreaService service;

	@Resource
	private LzmhService lzmhService;
	
	@Override
	public boolean anonymousAccess() {
		
		return false;
	}
	static Logger log = Logger.getLogger(AllAreaController.class);
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {

		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String buildId=null == arg0.getParameter("buildId")?"":arg0.getParameter("buildId").toString();//员工工号

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
			    Page<AllArea> page =new Page<AllArea>(showCount, currentPage);
			    service.queryPropertyByParam(page, parameter, null);
			    List<AllArea> result=(List<AllArea>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editAllArea") || method.equals("viewAllArea")){
				
				arg1.setContentType("application/json;charset=utf-8");
				AllArea allArea = new AllArea();
				allArea = service.getAllAreaById(buildId);
				String jsonString = JSON.toJSONString(allArea);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				String buildName= null == arg0.getParameter("buildName")?"":arg0.getParameter("buildName").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				
				AllArea allArea = new AllArea();
				if(buildId!=null && !buildId.isEmpty()){
					allArea.setBuildId(buildId);
				}
				allArea.setBuildName(buildName);
				allArea.setRemark(remark);
				service.addAllArea(allArea);
				lzmhService.addCommunity(allArea);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteAllArea")){

//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(buildId)){
						service.deleteAllArea(buildId);
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
			
			}else if(method.equalsIgnoreCase("initDropAll")){
				/**
				List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) service.getDirectoryLikeCode("emp_status");
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				*/
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		
	}

	@Override
	public String getUrl() {
		return "/allAreaInfo/allAreaList";
	}

	@Override
	public boolean isDisabled() {
		
		return false;
	}

}
