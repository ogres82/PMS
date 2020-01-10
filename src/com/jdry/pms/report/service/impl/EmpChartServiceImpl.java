package com.jdry.pms.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.report.dao.EmpChartDao;
import com.jdry.pms.report.service.EmpChartService;

@Repository
@Component
public class EmpChartServiceImpl implements EmpChartService{

	@Resource 
	EmpChartDao dao;
	
	@Override
	public List<Object> empSexChart() {
		return dao.empSexChart();
	}

	@Override
	public List<Object> empDegreeChart() {
		return dao.empDegreeChart();
	}

	@Override
	public List<Object> empAgeChart() {
		return dao.empAgeChart();
	}

	@Override
	public List<Object> empDeptChart() {
		return dao.empDeptChart();
	}
}
