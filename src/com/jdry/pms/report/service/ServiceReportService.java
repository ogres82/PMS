package com.jdry.pms.report.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
@Repository
public interface ServiceReportService {
	
	public List getAllServiceInfo();

	public List<Object> getImpairInfo();

	public List<Object> getAssignWrokInfo();

	public List<Object> getHouseWorkInfo();

	public List getSatisfaction();

	public List<Object> getZXSatisfaction();

	public List<Object> getJZSatisfaction();

	public List<Object> getEventAnalysis(String event_type);

	public Object getEventSatisfaAnalysis(String string);

	public List getEventResponse(int i);

	public List<Object> getEventTimeAnalysis(int week);
	
	public List<HouseworkEventEntity> queryHouseworkList(String finishFlag);
	
	public List<WorkMainEntity> queryCompList(String finishFlag);
	
	public List<WorkMainEntity> queryMtnList(String finishFlag);
}
