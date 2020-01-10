package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.ParkingAreaDao;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.VParkingArea;
import com.jdry.pms.basicInfo.service.ParkingAreaService;

@Repository
@Component
public class ParkingAreaServiceImpl implements ParkingAreaService{

	@Resource
	ParkingAreaDao dao;
	
	@Override
	public void query(Page<ParkingArea> page, Map<String, Object> parameter,
			Criteria criteria) {
		dao.query(page, parameter, criteria);
	}

	@Override
	public void saveParkingArea(Collection<ParkingArea> parkingAreas) {
		
		dao.saveParkingArea(parkingAreas);
	}

	@Override
	public Collection<ParkingArea> queryParkingAreaByParam(
			Map<String, Object> parameter) {
		
		return dao.queryParkingAreaByParam(parameter);
	}

	@Override
	public void queryParkingAreaByParam(Page<VParkingArea> page,
			Map<String, Object> parameter, String type) {
		dao.queryParkingAreaByParam(page,parameter,type);
	}

	@Override
	public VParkingArea getVParkingAreaById(String parkId) {
		
		return dao.getVParkingAreaById(parkId);
	}

	@Override
	public void addParkingArea(ParkingArea parkingArea) {
		dao.addParkingArea(parkingArea);
	}

	@Override
	public void deleteParkingArea(String parkId) {
		dao.deleteParkingArea(parkId);
	}

	@Override
	public List<VParkingArea> getAllParkingArea() {
		// TODO Auto-generated method stub
		return dao.getAllParkingArea();
	}
}
