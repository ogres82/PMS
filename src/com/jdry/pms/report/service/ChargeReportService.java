package com.jdry.pms.report.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface ChargeReportService {
	
	public Collection getChargeInfoByArea(String month);
	public Collection getChargeInfoByBuild(String date,String communityId);
	public Collection getChargeInfoByType(String month);
	public Collection getChargeInfoByHis();
	public Collection getChargeInfoByYear();
	public Collection getChargeInfoByMonth(String year);
	public List<Map<String,Object>> queryAuditRep(String beginDate,String endDate);
	public List<Map<String,Object>> queryAuditDetail(String beginDate,String endDate);
}
