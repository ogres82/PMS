package com.jdry.pms.exportData.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.exportData.pojo.OwnerRoomInfoExp;
import com.jdry.pms.exportData.service.OwnerRoomInfoExpServics;
import com.jdry.pms.dir.service.DirectoryService;

@Repository
@Component
public class OwnerRoomInfoExpController implements IController {

	@Resource
	private OwnerRoomInfoExpServics service;
	
	@Resource
	private DirectoryService dirService;
	
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		String jsonString = "";
		PrintWriter out = arg1.getWriter();
		try {
			if (method.equalsIgnoreCase("list")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String communityId = arg0.getParameter("communityIdSearch") == null ? "" : arg0.getParameter("communityIdSearch").toString();
				String belongSbId = arg0.getParameter("storiedBuildIdSearch") == null ? "" : arg0.getParameter("storiedBuildIdSearch").toString();
				String roomType = arg0.getParameter("roomTypeSearch") == null ? "" : arg0.getParameter("roomTypeSearch").toString();
				String roomState =arg0.getParameter("roomStateSearch") == null ? "" : arg0.getParameter("roomStateSearch").toString();
				parameter.put("communityId", communityId);
				parameter.put("belongSbId", belongSbId);
				parameter.put("roomType", roomType);
				parameter.put("roomState", roomState);
				
				List<OwnerRoomInfoExp> result = service.queryOwnerRoomInfo(parameter);
				jsonString = JSON.toJSONString(result);
			}
			if(method.equalsIgnoreCase("initDropAll")){
				List<DirDirectoryDetail> positions
					=(List<DirDirectoryDetail>) dirService.getDirectoryLikeCode("");
				jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");		
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/expBasicInfo/ownerRoomInfoExp";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
