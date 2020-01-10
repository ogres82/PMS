package com.jdry.pms.gis.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.model.DefaultDept;
import com.jdry.pms.comm.util.RedisPool;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.gis.app.entity.GisUserLocation;
import com.jdry.pms.gis.web.dao.WebGisDao;

@Repository
@Component
public class WebGisService {
	@Resource
	WebGisDao dao;
	static Logger log = Logger.getLogger(WebGisService.class);

	public String getWorkers(String data) {
		// TODO Auto-generated method stub
		String str = "";
		try {
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		JSONObject obj = JSON.parseObject(data);
		String keyword = null == obj.getString("keyword") ? "" : obj.getString("keyword");
		try {
			keyword = URLDecoder.decode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dao == null) {
			dao = (WebGisDao) SpringUtil.getObjectFromApplication("webGisDao");
		}
		List list = dao.getWorkers(keyword);
		if (list.size() > 0) {
			List list2 = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj2 = (Object[]) list.get(i);
				map.put("userName", obj2[0]);
				map.put("cName", obj2[1]);
				map.put("deptName", obj2[2]);
				list2.add(map);
			}
			String jsonString = JSON.toJSONString(list2);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"查无数据！请输入正确名称\",\"data\":\"\"}";
		}
	}

	public String getUserLocation(String data) {
		// TODO Auto-generated method stub
		String str = "";
		try {
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		JSONObject obj = JSON.parseObject(data);
		String userName = null == obj.getString("userName") ? "" : obj.getString("userName");
		long startTime = null == obj.getLong("startTime") ? 0 : obj.getLong("startTime");
		long endTime = null == obj.getLong("endTime") ? 0 : obj.getLong("endTime");
		if (startTime == 0 || endTime == 0) {
			return "{\"status\":\"0\",\"message\":\"查无数据！请输入正确日期\",\"data\":\"\"}";
		}
		if (dao == null) {
			dao = (WebGisDao) SpringUtil.getObjectFromApplication("webGisDao");
		}
		List<GisUserLocation> list = dao.getUserLocation(userName, startTime, endTime);
		if (list.size() > 0) {
			String jsonString = JSON.toJSONString(list);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"无相关地理位置数据\",\"data\":\"\"}";
		}
	}
	/**
	 * 获取所有部门
	 * 
	 * @param data
	 * @return
	 */
	public String getAllDept(String data){
		if (dao == null) {
			dao = (WebGisDao) SpringUtil.getObjectFromApplication("webGisDao");
		}
		List<DefaultDept> list = dao.getAllDept();
		return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + JSON.toJSONString(list) + "}";
	}

	/**
	 * 获取所有用户最新位置
	 * 
	 * @param data
	 * @return
	 */
	public String getAllUserLatestLocation(String data) {
		JSONObject obj = JSON.parseObject(data);
		String deptId = null == obj.getString("deptId") ? "" : obj.getString("deptId");
		if (dao == null) {
			dao = (WebGisDao) SpringUtil.getObjectFromApplication("webGisDao");
		}
		List<GisUserLocation> result = new ArrayList<GisUserLocation>();
		GisUserLocation loc = null;
		List<Map> list = dao.getAllUserName(deptId);
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisObject();
			for (int i = 0; i < list.size(); i++) {
				String locString = jedis.get("gis-" + list.get(i).get("userName"));
				loc = JSON.toJavaObject(JSON.parseObject(locString), GisUserLocation.class);
				// 不在线人员信息
				GisUserLocation unloc = new GisUserLocation();
				unloc.setId(list.get(i).get("userName").toString());
				unloc.setUsername(list.get(i).get("userName").toString());
				unloc.setCname(list.get(i).get("cname").toString());
				unloc.setXAxis(list.get(i).get("xAxis").toString());
				unloc.setYAxis(list.get(i).get("yAxis").toString());
				unloc.setRealtime(Long.parseLong(list.get(i).get("realTime").toString()));

				if (loc != null) {
					long t1 = new Date().getTime();
					long t2 = loc.getRealtime();
					if (t1 - t2 < 1000 * 60 * 30) { // 实时位置30分钟不更新，默认改设备已不定位，不回传实时位置信息
						result.add(loc);
					} else {
						result.add(unloc);
					}
				} else {
					result.add(unloc);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("jedis处理异常！" + e + "-----getAllUserLatestLocation");
		} finally {
			RedisPool.recycleJedisOjbect(jedis);
		}
		// if(result.size() == 0){ //如果redis中查无数据，到数据库中查询相关数据
		// result = dao.getAllUserLatestLocation();
		// }
		if (result.size() > 0) {
			String jsonString = JSON.toJSONString(result);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"无数据\",\"data\":\"\"}";
		}
	}

	/**
	 * 获取用户最新位置
	 * 
	 * @param data
	 * @return
	 */
	public GisUserLocation getUserLatestLocation(String userName) {
		if (dao == null) {
			dao = (WebGisDao) SpringUtil.getObjectFromApplication("webGisDao");
		}
		if ("".equals(userName) && userName == null) {
			return null;
		}

		GisUserLocation loc = null;
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisObject();

			String locString = jedis.get("gis-" + userName);
			loc = JSON.toJavaObject(JSON.parseObject(locString), GisUserLocation.class);
			if (loc != null) {
				long t1 = new Date().getTime();
				long t2 = loc.getRealtime();
				if (t1 - t2 > 1000 * 60 * 3) {
					return null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("jedis处理异常！" + e + "-----getUserLatestLocation");

		} finally {
			RedisPool.recycleJedisOjbect(jedis);
		}
		return loc;

	}

}
