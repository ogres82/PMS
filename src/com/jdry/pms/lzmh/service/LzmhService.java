package com.jdry.pms.lzmh.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;

@Repository
public interface LzmhService {
	
	public String addCommunity(AllArea allArea);
	public String modCommunity(AllArea allArea);
	public String delCommunity(int lzId);
	
	public String addPartition(AreaProperty areaProp);
	public String modPartition(AreaProperty areaProp);
	public String delPartition(int lzId);
	
	public String addTenement(BuildingProperty bp);
	public String modTenement(BuildingProperty bp);
	public String delTenement(int lzId);
	
	public String addUnit(BuildUnit bu);
	public String modUnit(BuildUnit bu);
	public String delUnit(int lzId);
	
	public String addRoom(HouseProperty hp);
	public String modRoom(HouseProperty hp);
	public String delRoom(int lzId);
	
	public String addOwnerInfo(HouseOwner ho);
	public String delOwnerInfo(int resident_id);
	
	
	public List <Map<String,Object>> lzmhUserInfo(Map<String, Object> parameter);
}
