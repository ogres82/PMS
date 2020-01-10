package com.jdry.pms.kpi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.kpi.dao.AssessmentRateDao;
import com.jdry.pms.kpi.pojo.AssessmentRate;
import com.jdry.pms.kpi.service.AssessmentRateSerivce;

@Service
public class AssessmentRateSerivceImpl extends BaseDaoImpl<AssessmentRate> implements AssessmentRateSerivce{
	@Resource
	AssessmentRateDao dao;
}
