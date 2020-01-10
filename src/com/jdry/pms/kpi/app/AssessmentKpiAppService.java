package com.jdry.pms.kpi.app;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.kpi.pojo.AssessmentKpi;
import com.jdry.pms.kpi.pojo.AssessmentRec;
import com.jdry.pms.kpi.service.AssessmentKpiService;
import com.jdry.pms.kpi.service.AssessmentRateSerivce;
import com.jdry.pms.kpi.service.AssessmentRecService;

/**
 * AssessmentKpiAppService class
 * 
 * @author huanglin
 * @date 2017/10/20
 */

@Repository
public class AssessmentKpiAppService {
	@Resource
	AssessmentKpiService akService;

	@Resource
	AssessmentRateSerivce arService;

	@Resource
	AssessmentRecService arcService;

	public String getKpi(String data) {

		if (akService == null) {
			akService = (AssessmentKpiService) SpringUtil.getObjectFromApplication("assessmentKpiServiceImpl");
		}
        Map<String,Object> parameter = new HashMap<String,Object>(3);
        
 
       
		String hql = " From AssessmentKpi WHERE 1=1";

		JSONObject parm = JSON.parseObject(data);
		int kpiLv = parm.getIntValue("kpiLv");
		String kpiParentId = null == parm.getString("kpiParentId") ? "" : parm.getString("kpiParentId").toString();
		String empPostId = null == parm.getString("empPostId") ? "" : parm.getString("empPostId").toString();
		String kpiType = null == parm.getString("kpiType") ? "" : parm.getString("kpiType").toString();

		hql += " AND kpiLv =:kpiLv";
		parameter.put("kpiLv", kpiLv);
		if (!"".equals(kpiParentId)) {
			hql += " AND kpiParentId =:kpiParentId";
			parameter.put("kpiParentId", kpiParentId);
		}
		if (!"".equals(empPostId)) {
			hql += " AND empPostId like :empPostId";
			parameter.put("empPostId", "%"+empPostId+"%");
		}
		if (!"".equals(kpiType)) {
			hql += " AND kpiType =:kpiType";
			parameter.put("kpiType", kpiType);
		}
		
		
		List<AssessmentKpi> list = akService.findEntityByHQL(hql,parameter);

		if (list != null && list.size() > 0) {
			String jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public String saveKpiRec(String data) throws UnsupportedEncodingException {
		if (arcService == null) {
			arcService = (AssessmentRecService) SpringUtil.getObjectFromApplication("assessmentRecServiceImpl");
		}
		
		//获取JSON
		JSONObject jsonData = JSON.parseObject(data);
		//获取考核项目有可能是多项
		JSONArray jsonRec = jsonData.getJSONArray("kpiRec");
		AssessmentRec arc = null;
		
		JSONObject kpiRec = null;

		String empId = jsonData.getString("empId");
		String kpiId ="";
		int kpiValue = 0;
		String reason = "";
		String operId= jsonData.getString("operId");
				
		Date insertDate= new Date(jsonData.getLongValue("insertDate"));
		String imgUrls= jsonData.getString("imgUrls");
		

		//解析考核项
		for (int i = 0, len = jsonRec.size(); i < len; i++) {
			arc  = new AssessmentRec();
			
			kpiRec = (JSONObject) jsonRec.get(i);
			kpiId =kpiRec.getString("kpiId");
			kpiValue =kpiRec.getIntValue("kpiValue");
			reason =URLDecoder.decode(kpiRec.getString("reason"),"UTF-8");
			arc.setEmpId(empId);
			arc.setInsertDate(insertDate);
			arc.setImgUrls(imgUrls);
			arc.setKpiId(kpiId);
			arc.setKpiValue(kpiValue);
			arc.setOperId(operId);
			arc.setReason(reason);
			arcService.saveEntity(arc);	
		}

		return "{\"status\":\"1\",\"message\":\"保存成功！\",\"data\":\"\"}";
	}

	public String queryKpiRec(String data) {
		if (arcService == null) {
			arcService = (AssessmentRecService) SpringUtil.getObjectFromApplication("assessmentRecServiceImpl");
		}
		Map<String, Object> parameter = new HashMap<String, Object>(4);
		JSONObject parm = JSON.parseObject(data);

		String empDeptId = null == parm.getString("empDeptId") ? "" : parm.getString("empDeptId").toString();
		String insertDate = null == parm.getString("insertDate") ? "" : parm.getString("insertDate").toString();
		String operId = null == parm.getString("operId") ? "" : parm.getString("operId").toString();
		String recId = null == parm.getString("recId") ? "" : parm.getString("recId").toString();
		String kpiType = null == parm.getString("kpiType") ? "" : parm.getString("kpiType").toString();
		parameter.put("empDeptId", empDeptId);
		parameter.put("insertDate", insertDate);
		parameter.put("operId", operId);
		parameter.put("recId", recId);
		parameter.put("kpiType", kpiType);


		@SuppressWarnings("rawtypes")
		List<Map> list = arcService.queryAssessmentRecAll(parameter);

		if (list.size() > 0) {
			String jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}

}
