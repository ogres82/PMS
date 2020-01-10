package com.jdry.pms.kpi.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.kpi.dao.AssessmentRecDao;
import com.jdry.pms.kpi.pojo.AssessmentRec;
import com.jdry.pms.kpi.service.AssessmentRecService;

@Service
@SuppressWarnings("rawtypes")
public class AssessmentRecServiceImpl extends BaseDaoImpl<AssessmentRec> implements AssessmentRecService{
	@Resource
	AssessmentRecDao dao;

	@Override
	public List<Map> queryAssessmentRecAll(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryAssessmentRecAll(parameter);
	}

	@Override
	public List<Map> queryAssessmentRecMonthly(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryAssessmentRecMonthly(parameter);
	}
}
