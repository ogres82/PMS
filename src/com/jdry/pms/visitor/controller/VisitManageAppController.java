package com.jdry.pms.visitor.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.visitor.pojo.VisitorRec;
import com.jdry.pms.visitor.service.VisitManageService;

@Repository
@Component
public class VisitManageAppController {
	@Resource
	VisitManageService service;
	public String addVisitRec(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String ownerId = null == obj.getString("ownerId")?"" : obj.getString("ownerId");
		String ownerName = null == obj.getString("ownerName")?"" : obj.getString("ownerName");
		String ownerPhone = null == obj.getString("ownerPhone")?"" : obj.getString("ownerPhone");
		String roomId = null == obj.getString("roomId")?"" : obj.getString("roomId");
		String roomAddress = null == obj.getString("roomAddress")?"" : obj.getString("roomAddress");
		Date visitTime = new Date();
		String visitorNumber = null == obj.getString("visitorNumber")?"" : obj.getString("visitorNumber");
		String managerId = null == obj.getString("managerId")?"" : obj.getString("managerId");
		String managerName = null == obj.getString("managerName")?"" : obj.getString("managerName");
		VisitorRec rec = new VisitorRec();
		if(visitorNumber!=null && !visitorNumber.isEmpty()){
			rec.setVisitorNumber(Integer.parseInt(visitorNumber));
		}
		rec.setOwnerId(ownerId);
		rec.setOwnerName(ownerName);
		rec.setOwnerPhone(ownerPhone);
		rec.setRoomId(roomId);
		rec.setRoomAddress(roomAddress);
		rec.setVisitTime(visitTime);
		rec.setManagerId(managerId);
		rec.setManagerName(managerName);
		if(service == null){
			service = (VisitManageService) SpringUtil.getObjectFromApplication("visitManageServiceImpl");
		}
		service.addVisitRec(rec);
		return "{\"status\":\"1\",\"message\":\"记录成功！\"}";
	}


	public String loadVisitRec(String data){
		JSONObject obj = JSON.parseObject(data);
		String managerId = null == obj.getString("managerId")?"" : obj.getString("managerId");
		if(service == null){
			service = (VisitManageService) SpringUtil.getObjectFromApplication("visitManageServiceImpl");
		}
		List<VisitorRec> list = service.loadVisitRec(managerId);
		String jsonString = JSON.toJSONString(list);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	public String loadVisitRecByOwner(String data){
		JSONObject obj = JSON.parseObject(data);
		String ownerId = null == obj.getString("ownerId")?"" : obj.getString("ownerId");
		if(service == null){
			service = (VisitManageService) SpringUtil.getObjectFromApplication("visitManageServiceImpl");
		}
		List<VisitorRec> list = service.loadVisitRecByOwner(ownerId);
		String jsonString = JSON.toJSONString(list);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}

}
