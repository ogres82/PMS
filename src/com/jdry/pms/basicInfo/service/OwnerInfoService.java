package com.jdry.pms.basicInfo.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VOwnerRoomBasicInfo;
import com.jdry.pms.basicInfo.pojo.VPropertyOwner;

@Repository
public interface OwnerInfoService {

	public void query(Page<PropertyOwner> page, Map<String, Object> parameter,
			Criteria criteria);

	public void saveOwnerInfo(Collection<PropertyOwner> owners);
	
	public void registerOwner(PropertyOwner owner,String type);

	public Collection<PropertyOwner> queryPropertyOwnerByParent(String id);

	public Collection<PropertyOwner> queryOwnerInfoByParam(
			Map<String, Object> parameter);
	public Collection<PropertyOwner> queryOwnerInfoByParam(
			Map<String, Object> parameter,String type);
	
	public String ownerLogin(String userName,String pwd);
	
	public void saveToken(String phone,String token);
	
	public boolean checkToken(String token);
	
	@SuppressWarnings("rawtypes")
	public List queryBasicInfo(String keyword);

	public void queryOwnerByParam(Page<VPropertyOwner> page,
			Map<String, Object> parameter, String type);
	

	public VPropertyOwner getVPropertyOwnerById(
			String ownerId);

	public String addPropertyOwner(PropertyOwner owner);

	public void deletePropertyOwner(String ownerId);

	public PropertyOwner getPropertyOwnerById(String ownerId);

	public boolean checkUniPhone(String fieldValue);
	
	public String getRoomId(String username);
	public List getOwnerInfo(String username);

	public List<VPropertyOwner> getFamily(String ownerId);
	public PropertyOwner queryOwnerByPhone(String phone);
	public List queryOwnerByRoomId(Map<String,String> parameter);

	public void queryOwnerVisitorList(Page<VPropertyOwner> page,
			Map<String, Object> parameter);
	
	public List<BigInteger> getMySettingPage(Map<String, Object> map);
	
	public List<VOwnerRoomBasicInfo> querOwnerRoomBasicInfo(Map<String, Object> parameter);
}
