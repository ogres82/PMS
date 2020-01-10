package com.jdry.pms.kpi.service;

import java.util.List;
import java.util.Map;

import com.jdry.pms.comm.service.BaseService;
import com.jdry.pms.kpi.pojo.AssessmentRec;

public interface AssessmentRecService  extends BaseService<AssessmentRec>{
	@SuppressWarnings("rawtypes")
	public List<Map> queryAssessmentRecAll(Map<String, Object> parameter);
	@SuppressWarnings("rawtypes")
	public List<Map> queryAssessmentRecMonthly(Map<String, Object> parameter);
}
