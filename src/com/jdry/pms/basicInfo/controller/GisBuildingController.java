package com.jdry.pms.basicInfo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.basicInfo.pojo.VBuildingProperty;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;
import com.jdry.pms.comm.util.SpringUtil;

public class GisBuildingController {
	
	@Resource
	BuildingPropertyService service;

	public String queryBuildingByName(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String buildingName = null == obj.getString("buildingName")?"" : obj.getString("buildingName");
		if(service == null){
			service = (BuildingPropertyService) SpringUtil.getObjectFromApplication("buildingPropertyServiceImpl");
		}
		List<VBuildingProperty> result = service.queryBuildingByName(buildingName);
		String jsonString = JSON.toJSONString(result);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	public String queryOwnerByBuild(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String buildingId = null == obj.getString("buildingId")?"" : obj.getString("buildingId");
		if(service == null){
			service = (BuildingPropertyService) SpringUtil.getObjectFromApplication("buildingPropertyServiceImpl");
		}
		List<VHouseOwner> result = service.queryOwnerByBuild(buildingId);
		String jsonString = JSON.toJSONString(result);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	public String queryBuildingById(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String buildingId = null == obj.getString("buildingId")?"" : obj.getString("buildingId");
		if(service == null){
			service = (BuildingPropertyService) SpringUtil.getObjectFromApplication("buildingPropertyServiceImpl");
		}
		VBuildingProperty vp = service.getVBuildingPropertyById(buildingId);
		String jsonString = JSON.toJSONString(vp);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	public String queryBuildingImgById(String data){
		JSONObject obj = JSON.parseObject(data);
		String buildingId = null == obj.getString("buildingId")?"" : obj.getString("buildingId");
		String durl = null == obj.getString("durl")?"" : obj.getString("durl");
		if(service == null){
			service = (BuildingPropertyService) SpringUtil.getObjectFromApplication("buildingPropertyServiceImpl");
		}
		List list = service.queryBuildingImgById(buildingId);
		List result = new ArrayList();
		for(int i=0;i<list.size();i++){
			Object[] objArray = (Object[]) list.get(i);
			Map map = new HashMap();
			map.put("typeName", objArray[0]);
			map.put("imgName", objArray[1]);
			map.put("imgUrl", durl+objArray[2]);
			result.add(map);
		}
		String jsonString = JSON.toJSONString(result);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
}
