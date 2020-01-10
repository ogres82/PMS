package com.jdry.pms.report.service;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface EmpChartService {

	public List<Object> empSexChart();

	public List<Object> empDegreeChart();

	public List<Object> empAgeChart();

	public List<Object> empDeptChart();
	
}
