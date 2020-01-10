package com.jdry.pms.kpi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.kpi.dao.AssessmentKpiDao;
import com.jdry.pms.kpi.pojo.AssessmentKpi;
import com.jdry.pms.kpi.service.AssessmentKpiService;

@Service
public class AssessmentKpiServiceImpl extends BaseDaoImpl<AssessmentKpi> implements AssessmentKpiService{
	@Resource
	AssessmentKpiDao dao;
}
