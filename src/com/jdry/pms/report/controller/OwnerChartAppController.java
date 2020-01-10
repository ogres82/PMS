package com.jdry.pms.report.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.report.service.OwnerChartService;

@Component
@Repository
public class OwnerChartAppController {
	
	
	@Resource
	OwnerChartService ownerChartService;
	
	/**
	 * 各小区入住情况，入住率
	 * @Title: queryOwnerInByCommunity   
	 * @Description: TODO
	 */
	@SuppressWarnings("unused")
	public String queryOwnerInByCommunity(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		if(ownerChartService == null){
			ownerChartService = (OwnerChartService) SpringUtil.getObjectFromApplication("ownerChartServiceImpl");
		}
		JSONObject ob = JSON.parseObject(str);
		List<Object> obj = ownerChartService.communityAndRoom();
		List<Object> obj1 = ownerChartService.communityAndIns();
		
		
		String jsonString ="";
		if(obj!=null && obj1!=null){
			String jsonObj = JSON.toJSONString(obj);
			String jsonObj1 = JSON.toJSONString(obj1);
			 jsonString = "{'line1':"+jsonObj+",'line2':"+jsonObj1+"}";
			
		}
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	} 
	
}
