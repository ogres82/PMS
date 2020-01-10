package com.jdry.pms.report.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.report.dao.ChargeReportDao;
import com.jdry.pms.report.service.ChargeReportService;
@Repository
@Component
public class ChargeReportServiceImpl implements ChargeReportService{

	@Resource
	ChargeReportDao dao;
	
	@Override
	public Collection getChargeInfoByArea(String month){
		return dao.getChargeInfoByArea(month);
	}

	@Override
	public Collection getChargeInfoByType(String month) {
		// TODO Auto-generated method stub
		return dao.getChargeInfoByType(month);
	}

	@Override
	public Collection getChargeInfoByHis() {
		// TODO Auto-generated method stub
		return dao.getChargeInfoByHis();
	}

	@Override
	public Collection getChargeInfoByBuild(String date, String communityId) {
		// TODO Auto-generated method stub
		return dao.getChargeInfoByBuild(date, communityId);
	}

	@Override
	public Collection getChargeInfoByYear() {
		// TODO Auto-generated method stub
		return dao.getChargeInfoByYear();
	}

	@Override
	public Collection getChargeInfoByMonth(String year) {
		// TODO Auto-generated method stub
		return dao.getChargeInfoByMonth(year);
	}

	@Override
	public List<Map<String, Object>> queryAuditRep(String beginDate,String endDate) {
		// TODO Auto-generated method stub
		return dao.queryAuditRep(beginDate,endDate);
	}

	@Override
	public List<Map<String, Object>> queryAuditDetail(String beginDate,String endDate) {
		// TODO Auto-generated method stub
		return dao.queryAuditDetail(beginDate,endDate);
	}
}
