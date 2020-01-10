package com.jdry.pms.camera.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.camera.dao.CameraDao;
import com.jdry.pms.camera.pojo.CameraInfo;
import com.jdry.pms.camera.service.CameraService;
import com.jdry.pms.comm.util.HttpRequestUtil;
import com.jdry.pms.comm.util.PageUtil;

import net.sf.json.JSONArray;

@Repository
@Component
public class CameraController implements IController {

	@Resource
	CameraService cameraService;

	@Resource
	AreaPropertyService areaPropertyService;

	@Resource
	CameraDao cameraDao;

	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String method = request.getParameter("method");
		if (method.equals("cameraList")) {
			PageUtil pageUtil = new PageUtil();
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("limit", request.getParameter("limit"));
			parameter.put("offset", request.getParameter("offset"));
			parameter.put("regionId", request.getParameter("regionId"));
			parameter.put("searchName", request.getParameter("searchName"));
			cameraService.queryCameraList(pageUtil, parameter);

			JSONArray array = new JSONArray();
			List<?> list = pageUtil.getEntityList();
			int size = list.size();
			for (int i = 0; i < size; i++) {

				Object[] obj = (Object[]) list.get(i);
				JSONObject object = new JSONObject();
				object.put("communityId", obj[0]);
				object.put("communityName", obj[1]);
				object.put("id", obj[2]);
				object.put("cameraName", obj[3].toString());
				object.put("cameraAddress", obj[4].toString());
				object.put("model", obj[5].toString());
				object.put("serialNumber", obj[6].toString());
				object.put("verificationCode", obj[7].toString());
				object.put("createDate", obj[7] == null ? "" : obj[8].toString());

				array.add(object);
			}

			String b = JSON.toJSONString(array);
			long count = pageUtil.getTotalCount(); // 获取总记录数
			PrintWriter out = response.getWriter();
			String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
			out.print(r);
			out.flush();
		}
		if (method.equals("saveCamera")) {
			String id = request.getParameter("id");
			String community = request.getParameter("community");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String model = request.getParameter("model");
			String serialNumber = request.getParameter("serialNumber");
			String createDateStr = request.getParameter("createDate");
			String verificationCode = request.getParameter("verificationCode");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			CameraInfo cam = new CameraInfo();
			Date createDate = new Date();
			if (!id.isEmpty()) {
				cam.setId(id);
				try {
					cam.setCreateDate(sdf.parse(createDateStr));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				cam.setCreateDate(createDate);
			}
			cam.setRegionId(community);
			cam.setCameraName(name);
			cam.setCameraAddress(address);
			cam.setCameraModel(model);
			cam.setCameraSerialNumber(serialNumber);
			cam.setCameraVerificationCode(verificationCode);
			String r = "success";
			try {
				// 保存成功后调用摄像头解码
				String url = "https://open.ys7.com/api/lapp/device/encrypt/off?";
				url += "accessToken=at.0epqldwa6rp04aoe9qia7sth1gyu1mc8-32fjy6el5s-13x5gu6-dmrfu9lfr";
				url += ("&deviceSerial=" + serialNumber);
				url += ("&validateCode=" + verificationCode);
				String ds = HttpRequestUtil.sendPost(url, null);
				if (ds.indexOf("\"200\"") == -1 && ds.indexOf("\"60016\"") == -1) {
//					JSONObject jsObj=JSONObject.parseObject(ds);
//					jsObj.put("msg", "摄像头解码失败，"+jsObj.getString("msg"));
//					PrintWriter out = response.getWriter();
//					out.print(JSON.toJSONString(r));
//					out.flush();
					r = "failed";
				} else {
					r = cameraService.saveorUpdateCamera(cam);
				}
			} catch (Exception e) {
				r = "failed";
			}
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(r));
			out.flush();
		}
		if (method.equals("deleteCamera")) {
			String idStr = request.getParameter("idStr");

			String idArr[] = idStr.split(",");
			CameraInfo cam = new CameraInfo();
			String r = "success";
			for (int i = 0; i < idArr.length; i++) {
				if (!idArr[i].isEmpty()) {
					cam.setId(idArr[i]);
					try {
						r = cameraService.deleteCamera(cam);
					} catch (Exception e) {
						r = "failed";
					}
				}
			}

			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(r));
			out.flush();
		}
		if (method.equals("treeList")) {
			ArrayList<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();

			Map<String, Object> param = new HashMap<String, Object>();
			List<AreaProperty> areaProperty = (List<AreaProperty>) areaPropertyService.queryAreaPropertyByParam(param);
			List<CameraInfo> CameraInfo = (List<CameraInfo>) cameraService.getCameraList(param);
			for (int i = 0; i < areaProperty.size(); i++) {
				List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", areaProperty.get(i).getCommunityId());
				map.put("text", areaProperty.get(i).getCommunityName());
				map.put("icon", "fa fa-institution");
				if (!CameraInfo.isEmpty()) {
					childList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < CameraInfo.size(); j++) {
						String id = CameraInfo.get(j).getRegionId();
						String parentId = areaProperty.get(i).getCommunityId();
						if (id.equals(parentId)) {
							Map<String, Object> childMap = new HashMap<String, Object>();
							childMap.put("id", CameraInfo.get(j).getId().toString());
							childMap.put("parnetId", areaProperty.get(i).getCommunityId().toString());
							childMap.put("text", CameraInfo.get(j).getCameraName());
							childMap.put("address", CameraInfo.get(j).getCameraAddress());
							childMap.put("icon", "fa fa-video-camera");
							childList.add(childMap);

							/*
							 * Map<String,Object> camMap = new HashMap<String,Object>();
							 * camMap.put("pageNo", 1); camMap.put("pageSize", 10);
							 * camMap.put("regionSysCodes", CameraInfo.get(j).getCameraAddress()); String
							 * result = HttpRequestUtil.sendPost(HikvisionUtil.buildPostUrl(
							 * "/vss/getPlatCameraResListByRegions", camMap), camMap);
							 * System.out.println(result); JSONObject object = JSON.parseObject(result);
							 * if(object.getIntValue("errorCode") == 0){ com.alibaba.fastjson.JSONArray
							 * array = object.getJSONObject("data").getJSONArray("list");
							 * if(array.size()>0){ for(int k=0;k<array.size();k++){ Map<String, Object>
							 * childMap = new HashMap<String, Object>(); childMap.put("id",
							 * array.getJSONObject(k).getString("cameraId")); childMap.put("parnetId",
							 * areaProperty.get(i).getCommunityId().toString()); childMap.put("text",
							 * array.getJSONObject(k).getString("cameraName")); childMap.put("address",
							 * array.getJSONObject(k).getString("sysCode")); childMap.put("icon",
							 * "fa fa-video-camera"); childList.add(childMap); } } }
							 */
						}
					}
				}
				if (!childList.isEmpty()) {
					List<String> sizelist = new ArrayList<String>();
					sizelist.add(childList.size() + "");
					map.put("nodes", childList);
					map.put("tags", sizelist);
				}
				listmap.add(map);
			}

			String r = JSONArray.fromObject(listmap).toString();
			PrintWriter out = response.getWriter();
			out.print(r);
			out.flush();
		}
		/*
		 * if(method.equals("camera")) { String regionSysCodes = null ==
		 * request.getParameter("regionSysCodes")?"":request.getParameter(
		 * "regionSysCodes").toString(); Map<String,Object> camMap = new
		 * HashMap<String,Object>(); camMap.put("pageNo", 1); camMap.put("pageSize",
		 * 10); camMap.put("regionSysCodes", regionSysCodes); String result =
		 * HttpRequestUtil.sendPost(HikvisionUtil.buildPostUrl(
		 * "/vss/getPlatCameraResListByRegions", camMap), camMap);
		 * System.out.println(result); String jsonString = null; JSONObject object =
		 * JSON.parseObject(result); if(object.getIntValue("errorCode") == 0){ List list
		 * = new ArrayList(); Map<String, String> mapCamera = null;
		 * 
		 * com.alibaba.fastjson.JSONArray array =
		 * object.getJSONObject("data").getJSONArray("list"); if(array.size()>0){
		 * for(int i=0;i<array.size();i++){ mapCamera = new HashMap<String, String>();
		 * mapCamera.put("id", array.getJSONObject(i).getString("cameraId"));
		 * mapCamera.put("name", array.getJSONObject(i).getString("cameraName"));
		 * mapCamera.put("type", array.getJSONObject(i).getString("cameraType"));
		 * mapCamera.put("cameraSyscode", array.getJSONObject(i).getString("sysCode"));
		 * list.add(mapCamera); } }
		 * 
		 * jsonString = JSON.toJSONString(list); } PrintWriter out =
		 * response.getWriter(); out.print(jsonString); out.flush(); }
		 */
	}

	@Override
	public String getUrl() {
		return "/camera/camera";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
