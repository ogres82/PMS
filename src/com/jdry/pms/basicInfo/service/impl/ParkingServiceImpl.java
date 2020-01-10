package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.ParkingDao;
import com.jdry.pms.basicInfo.pojo.Parking;
import com.jdry.pms.basicInfo.pojo.ParkingOwner;
import com.jdry.pms.basicInfo.pojo.VParking;
import com.jdry.pms.basicInfo.service.ParkingService;

@Repository
@Component
public class ParkingServiceImpl implements ParkingService{

	@Resource
	ParkingDao dao;
	
	@Override
	public void query(Page<Parking> page, Map<String, Object> parameter,
			Criteria criteria) {
		dao.query(page, parameter, criteria);
	}

	@Override
	public void saveParking(Collection<Parking> parkings) {
		
		dao.saveParking(parkings);
	}

	@Override
	public void queryParkingByParam(Page<VParking> page,
			Map<String, Object> parameter, String type) {
		
		dao.queryParkingByParam(page,parameter,type);
	}

	@Override
	public VParking getVParkingById(String carportId) {
		
		return dao.getVParkingById(carportId);
	}

	@Override
	public String addParking(Parking parking) {
		
		return dao.addParking(parking);
	}

	@Override
	public void deleteParking(String carportId) {
		
		dao.deleteParking(carportId);
	}

	@Override
	public void addParkingOwner(ParkingOwner po) {
		// TODO Auto-generated method stub
		dao.addParkingOwner(po);
	}

	@Override
	public void deleteParkingOwner(String carportId) {
		// TODO Auto-generated method stub
		dao.deleteParkingOwner(carportId);
	}


}
