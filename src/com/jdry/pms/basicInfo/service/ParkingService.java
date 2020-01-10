package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Parking;
import com.jdry.pms.basicInfo.pojo.ParkingOwner;
import com.jdry.pms.basicInfo.pojo.VParking;

@Repository
public interface ParkingService {

	public void query(Page<Parking> page, Map<String, Object> parameter,
			Criteria criteria);

	public void saveParking(Collection<Parking> parkings);

	public void queryParkingByParam(Page<VParking> page,
			Map<String, Object> parameter, String type);

	public VParking getVParkingById(String carportId);

	public String addParking(Parking parking);

	public void deleteParking(String carportId);
	
	public void addParkingOwner(ParkingOwner po);
	
	public void deleteParkingOwner(String carportId);

}
