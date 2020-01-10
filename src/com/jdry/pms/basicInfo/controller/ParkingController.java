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
import com.jdry.pms.basicInfo.pojo.Parking;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.ParkingOwner;
import com.jdry.pms.basicInfo.pojo.VParking;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.ParkingAreaService;
import com.jdry.pms.basicInfo.service.ParkingService;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class ParkingController implements IController{

	@Resource
	private ParkingService service;
	
	@Resource
	private AllAreaService allAreaService;
	
	@Resource
	private AreaPropertyService areaPropertyService;
	
	@Resource
	private PropertyManagerService propertyManagerService;
	
	@Resource
	private ParkingAreaService parkingAreaService;
	
	
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
		String carportId=null == arg0.getParameter("carportId")?"":arg0.getParameter("carportId").toString();
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
			    Page<VParking> page =new Page<VParking>(showCount, currentPage);
			    service.queryParkingByParam(page, parameter, null);
			    List<VParking> result=(List<VParking>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editParking") || method.equals("viewParking")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VParking parking = new VParking();
				parking = service.getVParkingById(carportId);
				String jsonString = JSON.toJSONString(parking);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				
				String belongComId= null == arg0.getParameter("belongComId")?"":arg0.getParameter("belongComId").toString();
				String belongParkNo= null == arg0.getParameter("belongParkNo")?"":arg0.getParameter("belongParkNo").toString();
				String carportNo= null == arg0.getParameter("carportNo")?"":arg0.getParameter("carportNo").toString();
				String carportStatus= null == arg0.getParameter("carportStatus")?"":arg0.getParameter("carportStatus").toString();
				String carportFloor= null == arg0.getParameter("carportFloor")?"":arg0.getParameter("carportFloor").toString();
				String carportType= null == arg0.getParameter("carportType")?"":arg0.getParameter("carportType").toString();
				String licensePlateNo= null == arg0.getParameter("licensePlateNo")?"":arg0.getParameter("licensePlateNo").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				String ownerId= null == arg0.getParameter("ownerId")?"":arg0.getParameter("ownerId").toString();
				
				Parking parking = new Parking();
				if(carportId!=null && !carportId.isEmpty()){
					parking.setCarportId(carportId);
				}
				parking.setBelongComId(belongComId);
				parking.setBelongParkNo(belongParkNo);
				parking.setCarportNo(carportNo);
				parking.setCarportStatus(carportStatus);
				parking.setLicensePlateNo(licensePlateNo);
				parking.setCarportFloor(carportFloor);
				parking.setCarportType(carportType);
				parking.setRemark(remark);
				carportId = service.addParking(parking);
				if(ownerId != null && !ownerId.isEmpty()){
					ParkingOwner po = new ParkingOwner();
					if(carportId!=null && !carportId.isEmpty()){
						po.setCarportId(carportId);
					}
					po.setOwnerId(ownerId);
					service.addParkingOwner(po);
				}
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("success"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteParking")){

				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(carportId)){
						service.deleteParking(carportId);
						service.deleteParkingOwner(carportId);
					}
					String jsonString = JSON.toJSONString("删除成功!");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败!");
					out.println(jsonString);
					out.flush();
				}
			
			}else if(method.equalsIgnoreCase("initDropAll")){
				
				List<DirDirectoryDetail> positions=propertyManagerService.getDirectoryLikeCode("carport_status");
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
			
			if(method.equalsIgnoreCase("initParkingAreaProp")){
				String communityId= null == arg0.getParameter("communityId")?"":arg0.getParameter("communityId").toString();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("communityId", communityId);
				List<ParkingArea> areaProperty = (List<ParkingArea>) parkingAreaService.queryParkingAreaByParam(param);
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
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		
		return "/parking/parkingList";
	}

	@Override
	public boolean isDisabled() {
		
		return false;
	}

}
