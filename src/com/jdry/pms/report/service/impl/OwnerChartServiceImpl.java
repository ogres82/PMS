package com.jdry.pms.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.report.dao.OwnerChartDao;
import com.jdry.pms.report.service.OwnerChartService;

@Repository
@Component
public class OwnerChartServiceImpl implements OwnerChartService{

	@Resource
	OwnerChartDao dao;
	
	@Override
	public List<Object> ownerInByRoomType() {
		return dao.ownerInByRoomType();
	}

	@Override
	public List<Object> ownerInByRoomState() {
		return dao.ownerInByRoomState();
	}

	@Override
	public List<Object> communityAndRoom() {
		return dao.communityAndRoom();
	}

	@Override
	public List<Object> communityAndIns() {
		return dao.communityAndIns();
	}

	@Override
	public List<Object> ownerInByMonth() {
		return dao.ownerInByMonth();
	}

	@Override
	public List<Object> roomAndInRate() {
		return dao.roomAndInRate();
	}

	@Override
	public List<Object> ownerAgeChart() {
		return dao.ownerAgeChart();
	}

}
