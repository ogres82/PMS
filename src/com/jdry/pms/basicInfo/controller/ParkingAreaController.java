package com.jdry.pms.basicInfo.controller;

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
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.VParkingArea;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.ParkingAreaService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;

@Repository
@Component
public class ParkingAreaController implements IController{

	@Resource
	private ParkingAreaService service;
	
	@Resource
	private AllAreaService allAreaService;
	
	@Resource
	DirectoryService directoryService;
	
	@Resource
	private AreaPropertyService areaPropertyService;
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
		String parkId=null == arg0.getParameter("parkId")?"":arg0.getParameter("parkId").toString();
		SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				/*int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<VParkingArea> page =new Page<VParkingArea>(showCount, currentPage);
			    service.queryParkingAreaByParam(page, parameter, null);
			    List<VParkingArea> result=(List<VParkingArea>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();*/
				List<VParkingArea> list = service.getAllParkingArea();
				String jsonString = JSON.toJSONString(list);
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if(method.equalsIgnoreCase("editParkingArea") || method.equals("viewParkingArea")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VParkingArea vParkingArea = new VParkingArea();
				vParkingArea = service.getVParkingAreaById(parkId);
				String jsonString = JSON.toJSONString(vParkingArea);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				String parkName= null == arg0.getParameter("parkName")?"":arg0.getParameter("parkName").toString();
				String belongComId= null == arg0.getParameter("belongComId")?"":arg0.getParameter("belongComId").toString();
				String remark = null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				String parkCode = null == arg0.getParameter("parkCode")?"":arg0.getParameter("parkCode").toString();
				String parkPosition = null == arg0.getParameter("parkPosition")?"":arg0.getParameter("parkPosition").toString();
				String parkState = null == arg0.getParameter("parkState")?"":arg0.getParameter("parkState").toString();
				String usingDate = null == arg0.getParameter("usingDate")?"":arg0.getParameter("usingDate").toString();
				String floors = null == arg0.getParameter("floors")?"":arg0.getParameter("floors").toString();
				String parkNum = null == arg0.getParameter("parkNum")?"":arg0.getParameter("parkNum").toString();
				ParkingArea parkingArea = new ParkingArea();
				if(parkId !=null && !parkId.isEmpty()){
					parkingArea.setParkId(parkId);
				}
				if(!usingDate.isEmpty()&&usingDate!=null){
					parkingArea.setUsingDate(sdf.parse(usingDate));
				}
				if(!floors.isEmpty()&&floors!=null){
					parkingArea.setFloors(Integer.parseInt(floors));
				}
				if(!parkNum.isEmpty()&&parkNum!=null){
					parkingArea.setParkNum(Integer.parseInt(parkNum));
				}
				parkingArea.setParkCode(parkCode);
				parkingArea.setParkPosition(parkPosition);
				parkingArea.setParkState(parkState);
				parkingArea.setParkName(parkName);
				parkingArea.setBelongComId(belongComId);
				parkingArea.setRemark(remark);
				service.addParkingArea(parkingArea);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteParkingArea")){

//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(parkId)){
						service.deleteParkingArea(parkId);
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
			
			if(method.equals("initParkingAreaState")){
				List<DirDirectoryDetail> parkingAreaState=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("parking_area");
				String jsonString = JSON.toJSONString(parkingAreaState);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(jsonString);
				out.flush();
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		
		return "/parkingArea/parkingAreaList";
	}

	@Override
	public boolean isDisabled() {
		
		return false;
	}

}
