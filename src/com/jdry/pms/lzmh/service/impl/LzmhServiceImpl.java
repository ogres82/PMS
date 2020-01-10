package com.jdry.pms.lzmh.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.lzmh.dao.LzmhDao;
import com.jdry.pms.lzmh.service.LzmhService;

@Repository
@Component
public class LzmhServiceImpl implements LzmhService {
	@Resource
	private LzmhDao dao;

	
	@Override
	public List <Map<String,Object>> lzmhUserInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.getLzmhUserInfo(parameter);
	}


	@Override
	public String addCommunity(AllArea allArea) {
		// TODO Auto-generated method stub
		return dao.addCommunity(allArea);
	}


	@Override
	public String modCommunity(AllArea allArea) {
		// TODO Auto-generated method stub
		return dao.addCommunity(allArea);
	}


	@Override
	public String delCommunity(int lzId) {
		// TODO Auto-generated method stub
		return dao.delCommunity(lzId);
	}


	@Override
	public String addPartition(AreaProperty areaProp) {
		// TODO Auto-generated method stub
		return dao.addPartition(areaProp);
	}


	@Override
	public String modPartition(AreaProperty areaProp) {
		// TODO Auto-generated method stub
		return dao.modPartition(areaProp);
	}


	@Override
	public String delPartition(int lzId) {
		// TODO Auto-generated method stub
		return dao.delPartition(lzId);
	}


	@Override
	public String addTenement(BuildingProperty bp) {
		// TODO Auto-generated method stub
		return dao.addTenement(bp);
	}


	@Override
	public String modTenement(BuildingProperty bp) {
		// TODO Auto-generated method stub
		return dao.modTenement(bp);
	}


	@Override
	public String delTenement(int lzId) {
		// TODO Auto-generated method stub
		return dao.delTenement(lzId);
	}


	@Override
	public String addUnit(BuildUnit bu) {
		// TODO Auto-generated method stub
		return dao.addUnit(bu);
	}


	@Override
	public String modUnit(BuildUnit bu) {
		// TODO Auto-generated method stub
		return dao.modUnit(bu);
	}


	@Override
	public String delUnit(int lzId) {
		// TODO Auto-generated method stub
		return dao.delUnit(lzId);
	}


	@Override
	public String addRoom(HouseProperty hp) {
		// TODO Auto-generated method stub
		return dao.addRoom(hp);
	}


	@Override
	public String modRoom(HouseProperty hp) {
		// TODO Auto-generated method stub
		return dao.modRoom(hp);
	}


	@Override
	public String delRoom(int lzId) {
		// TODO Auto-generated method stub
		return dao.delRoom(lzId);
	}


	@Override
	public String addOwnerInfo(HouseOwner ho) {
		// TODO Auto-generated method stub
		return dao.addOwnerInfo(ho);
	}


	@Override
	public String delOwnerInfo(int resident_id) {
		// TODO Auto-generated method stub
		return dao.delOwnerInfo(resident_id);
	}

}
