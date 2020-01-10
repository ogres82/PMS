package com.jdry.pms.camera.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.camera.pojo.CameraInfo;
import com.jdry.pms.camera.service.CameraService;
import com.jdry.pms.comm.util.SpringUtil;

@Repository
@Component
public class CameraInterface{

	@Resource
	CameraService cameraService;

	@Resource
	AreaPropertyService areaPropertyService;

	/*
	 * 获取全部小区列表
	 * @param null
	 * @return
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryCommunityList(String defaultStr) {
		String status="1";
		String message="获取数据成功";
		List<AreaProperty> areaProperty = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			if(areaPropertyService == null){
				areaPropertyService = (AreaPropertyService) SpringUtil.getObjectFromApplication("areaPropertyServiceImpl");
			}
			areaProperty = (List<AreaProperty>) areaPropertyService.queryAreaPropertyByParam(param);
			
			if(areaProperty.size()<=0) {
				status="0";
				message="暂无数据！";
			}
			/*if(areaProperty.size()!=0) {
				for (int i = 0; i < areaProperty.size(); i++) {
					AreaProperty area = areaProperty.get(i);
					mp.put("communityId", area.getCommunityId());
					mp.put("communityName", area.getCommunityName());
					res.add(mp);
				}
			}else {
				status="0";
				message="暂无数据！";
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			status="0";
			message="获取数据失败！";
		}
		Map map = new HashMap();
        map.put("status", status);
		map.put("message",message);
		map.put("data", areaProperty);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}
	
	/*
	 * 获取摄像头列表
	 * @param communityId 小区Id
	 * @return
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCameraListByCommunityId(String data) {
		JSONObject obj=JSON.parseObject(data);
		String communityId =  obj.getString("communityId");
		String stutus = "1";
		String message = "获取数据成功！";
		List<CameraInfo> cameraList = null;
		try {
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("communityId", communityId);
			
			if(cameraService == null){
				cameraService = (CameraService) SpringUtil.getObjectFromApplication("cameraServiceImpl");
			}
			cameraList = cameraService.getCameraListByCommunityId(parameter);
			
			if(cameraList.size()<=0) {
				stutus="0";
				message="暂无数据！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			stutus="0";
			message="获取数据失败！";
		}
		
		Map map = new HashMap();
        map.put("status", stutus);
		map.put("message",message);
		map.put("data", cameraList);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}
}
