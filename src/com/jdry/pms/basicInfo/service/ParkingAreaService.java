package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.VParkingArea;

@Repository
public interface ParkingAreaService {

	public void query(Page<ParkingArea> page, Map<String, Object> parameter,
			Criteria criteria);

	public void saveParkingArea(Collection<ParkingArea> parkingAreas);

	public Collection<ParkingArea> queryParkingAreaByParam(
			Map<String, Object> parameter);

	public void queryParkingAreaByParam(Page<VParkingArea> page,
			Map<String, Object> parameter, String type);

	public VParkingArea getVParkingAreaById(
			String parkId);

	public void addParkingArea(ParkingArea parkingArea);

	public void deleteParkingArea(String parkId);
	public List<VParkingArea> getAllParkingArea();
}
