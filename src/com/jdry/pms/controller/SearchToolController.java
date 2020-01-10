package com.jdry.pms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.OwnerInfoService;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.equipment.service.EqpFileService;
@Repository
@Component
public class SearchToolController implements IController{

	@Resource
	OwnerInfoService ownerInfoService;
	@Resource
	AssignWorkService assignWorkService;
	@Resource
	EqpFileService eqpFileService;
	@Resource
	HousePropertyService housePropertyService;
	@Resource
	PropertyManagerService propertyManagerService;
	
	@Override	
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {		
		response.setContentType("text;html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter pri=response.getWriter();	
		String type = request.getParameter("type");
		String keyword = java.net.URLDecoder.decode(request.getParameter("keyword"),"UTF-8");
		List list = new ArrayList();
		if(type.equals("owner")){				//搜索业主						
			if(ownerInfoService == null){
				ownerInfoService = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
			}
			list = ownerInfoService.queryBasicInfo(keyword);
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}else if(type.equals("engine")){		//搜索工程部员工
			if(assignWorkService == null){
				assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
			}
			list = assignWorkService.getHandleWorkers(keyword,"1");
			System.out.println(JSON.toJSON(list));
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}else if(type.equals("service")){		//搜索工程部员工
			if(assignWorkService == null){
				assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
			}
			list = assignWorkService.getHandleWorkers(keyword,"2");
			System.out.println(JSON.toJSON(list));
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}else if(type.equals("allEmp")){		//搜索所有员工
			if(assignWorkService == null){
				assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
			}
			list = assignWorkService.getHandleWorkers(keyword,"0");
			System.out.println(JSON.toJSON(list));
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}else if(type.equals("equipment")){		//搜索设备
			
			if(eqpFileService == null){
				eqpFileService = (EqpFileService) SpringUtil.getObjectFromApplication("eqpFileServiceImpl");
			}
			list = (List) eqpFileService.queryEqpFileByName(keyword);
			System.out.println(JSON.toJSON(list));
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}else if(type.equals("house")){		//搜索房间
			if(housePropertyService == null){
				housePropertyService = (HousePropertyService) SpringUtil.getObjectFromApplication("housePropertyServiceImpl");
			}
			list = housePropertyService.queryHouseDetail(keyword);
			System.out.println(JSON.toJSON(list));
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}else if(type.equals("empInfo")){//搜索员工信息
			if(propertyManagerService == null){
				propertyManagerService = (PropertyManagerService) SpringUtil.getObjectFromApplication("propertyManagerServiceImpl");
			}			
			list = propertyManagerService.getSearchEmp(keyword);			
			pri.print("{\"result\": "+JSON.toJSON(list)+"}");
		}
		else{
			pri.print("请输入正确URL地址！");
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/search";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
