package com.jdry.pms.patrolPlan.service.Impl;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.gis.app.entity.GisUserLocation;
import com.jdry.pms.gis.web.service.impl.WebGisService;
import com.jdry.pms.patrolPlan.dao.PatrolNodeRecDao;
import com.jdry.pms.patrolPlan.pojo.PatrolNodeRecEntity;
import com.jdry.pms.patrolPlan.pojo.PlanNodeRecEntity;
import com.jdry.pms.patrolPlan.service.PatrolNodeRecService;

@Repository
@Component
public class PatrolNodeRecServiceImpl implements PatrolNodeRecService {
	@Resource
	PatrolNodeRecDao dao;

	@Override
	public List<PatrolNodeRecEntity> queryPatrolNodeRec(String patrolUserId) {
		// TODO Auto-generated method stub
		return dao.queryPatrolNodeRec(patrolUserId);
	}

	public String savePatrolNodeRec(String data) {
		// TODO Auto-generated method stub
		if (dao == null) {
			dao = (PatrolNodeRecDao) SpringUtil.getObjectFromApplication("patrolNodeRecDao");
		}
		try {
			Date dt = new Date();
			long time = dt.getTime();
			WebGisService wbgss = new WebGisService();
			PatrolNodeRecEntity pnre = new PatrolNodeRecEntity();
			JSONObject obj = (JSONObject) JSON.parse(data);
			pnre.setPatrolContent(URLDecoder.decode(obj.getString("patrolContent"), "UTF-8"));
			pnre.setPatrolNodeId(obj.getIntValue("patrolNodeId"));
			pnre.setPatrolUserId(obj.getString("patrolUserId"));		

			GisUserLocation GisUser = wbgss.getUserLatestLocation(obj.getString("patrolUserId"));
			if(GisUser==null){
				return "{\"status\":\"0\",\"message\":\"保存失败！,暂时无获取到用户坐标，请稍后再试！\",\"data\":\"\"}";
			}
			
			pnre.setxAxis(GisUser.getXAxis());
			pnre.setyAxis(GisUser.getYAxis());
			pnre.setInsertTime(time);
			System.out.println(JSON.toJSONString(GisUser));
			dao.savePatrolNodeRec(pnre);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"0\",\"message\":\"保存失败！\",\"data\":\"\"}";
		}
		return "{\"status\":\"1\",\"message\":\"保存成功\",\"data\":\"\"}";
	}
	
	//提供给APP历史数据接口
	public String getPatrolNodeRecForApp(String data) {
		JSONObject obj = JSON.parseObject(data);
		String patrolUserId = null == obj.get("patrolUserId") ? "" : obj.get("patrolUserId").toString();
		if (dao == null) {
			dao = (PatrolNodeRecDao) SpringUtil.getObjectFromApplication("patrolNodeRecDao");
		}
		if (patrolUserId.length() > 0) {
			List<PatrolNodeRecEntity> lists = queryPatrolNodeRec(patrolUserId);
			String jsonString = JSON.toJSONString(lists);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}
	//给webgis查询数据
	public String getPatrolNodeRecForGis(String data) {
		
		JSONObject obj = JSON.parseObject(data);
		System.out.println(data);
		String patrolUserId = null == obj.get("userName") ? "" : obj.get("userName").toString();
		String strDate = null == obj.get("date") ? "" : obj.get("date").toString();

		try{
			if (dao == null) {
				dao = (PatrolNodeRecDao) SpringUtil.getObjectFromApplication("patrolNodeRecDao");
			}
			if (patrolUserId.length() > 0) {
				List<Map> lists = dao.queryPatrolNodeRec(patrolUserId, strDate);				
				
				String jsonString = JSON.toJSONString(lists);
				return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
			} else {
				return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return "{\"status\":\"0\",\"message\":\"查询条件有误！\",\"data\":\"\"}";
		}
	}

	@Override
	public void queryNodeRec(Page<PlanNodeRecEntity> page, Map<String, Object> parameter, Criteria criteria) {
		// TODO Auto-generated method stub
		dao.queryNodeRec(page, parameter, criteria);
	}
}
