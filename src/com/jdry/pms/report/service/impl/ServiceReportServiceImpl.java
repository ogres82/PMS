package com.jdry.pms.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.report.dao.ServiceReportDao;
import com.jdry.pms.report.service.ServiceReportService;
@Repository
@Component
public class ServiceReportServiceImpl implements ServiceReportService{

	@Resource
	ServiceReportDao dao;
	
	@Override
	public List getAllServiceInfo() {
		// TODO Auto-generated method stub
		List allList = new ArrayList();
		List mtnList = dao.getMtnInfo();
		List comList = dao.getCompInfo();
		List houseworkList = dao.getHouseworkInfo();
		for(int i=0;i<mtnList.size();i++){
			allList.add(mtnList.get(i));
		}
		for(int i=0;i<comList.size();i++){
			allList.add(comList.get(i));
		}
		for(int i=0;i<houseworkList.size();i++){
			allList.add(houseworkList.get(i));
		}
		return allList;
	}

	@Override
	public List<Object> getImpairInfo() {
		return dao.getImpairInfo();
	}

	@Override
	public List<Object> getAssignWrokInfo() {
		return dao.getAssignWrokInfo();
	}

	@Override
	public List<Object> getHouseWorkInfo() {
		return dao.getHouseWorkInfo();
	}

	@Override
	public List getSatisfaction() {
	
		return dao.getSatisfaction();
	}

	@Override
	public List<Object> getZXSatisfaction() {
		return dao.getZXSatisfaction();
	}

	@Override
	public List<Object> getJZSatisfaction() {
		return dao.getJZSatisfaction();
	}

	@Override
	public List<Object> getEventAnalysis(String event_type)
	{
		return dao.getEventAnalysis(event_type);
	}

	@Override
	public Object getEventSatisfaAnalysis(String name) {
		return dao.getEventSatisfaAnalysis(name);
	}

	@Override
	public List getEventResponse(int i) {
		return dao.getEventResponse(i);
	}

	@Override
	public List<Object> getEventTimeAnalysis(int week) {
		return dao.getEventTimeAnalysis( week);
	}

	@Override
	public List<HouseworkEventEntity> queryHouseworkList(String finishFlag) {
		// TODO Auto-generated method stub
		return dao.queryHouseworkList(finishFlag);
	}

	@Override
	public List<WorkMainEntity> queryCompList(String finishFlag) {
		// TODO Auto-generated method stub
		return dao.queryCompList(finishFlag);
	}

	@Override
	public List<WorkMainEntity> queryMtnList(String finishFlag) {
		// TODO Auto-generated method stub
		return dao.queryMtnList(finishFlag);
	}
}
