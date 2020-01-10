package com.jdry.pms.basicInfo.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.OwnerInfoDao;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VOwnerRoomBasicInfo;
import com.jdry.pms.basicInfo.pojo.VPropertyOwner;
import com.jdry.pms.basicInfo.service.OwnerInfoService;

@Repository
@Component
public class OwnerInfoServiceImpl implements OwnerInfoService{
	
	@Resource
	OwnerInfoDao dao;

	@Override
	public void query(Page<PropertyOwner> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		dao.query(page, parameter, criteria);
	}

	@Override
	public void saveOwnerInfo(Collection<PropertyOwner> owners) {
		
		dao.saveOwnerInfo(owners);
	}

	@Override
	public Collection<PropertyOwner> queryPropertyOwnerByParent(String id) {
		
		return dao.queryPropertyOwnerByParent(id);
	}

	@Override
	public Collection<PropertyOwner> queryOwnerInfoByParam(
			Map<String, Object> parameter) {
		
		return dao.queryOwnerInfoByParam(parameter);
	}
	
	@Override
	public Collection<PropertyOwner> queryOwnerInfoByParam(
			Map<String, Object> parameter,String type) {
		
		return dao.queryOwnerInfoByParam(parameter,type);
	}

	@Override
	public String ownerLogin(String userName,String pwd) {
		//String bak=dao.ownerLogin(userName,pwd);
		
		return dao.ownerLogin(userName,pwd);
	}

	@Override
	public void saveToken(String phone, String token) {
		// TODO Auto-generated method stub
		dao.saveToken(phone,token);
	}

	@Override
	public boolean checkToken(String token) {
		// TODO Auto-generated method stub
		return dao.checkToken(token);
	}

	@Override
	public void registerOwner(PropertyOwner owner,String type) {
		 dao.registerOwner(owner,type);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryBasicInfo(String keyword) {
		// TODO Auto-generated method stub
		return dao.queryBasicInfo(keyword);
	}

	@Override
	public void queryOwnerByParam(Page<VPropertyOwner> page,
			Map<String, Object> parameter, String type) {
		
		dao.queryOwnerByParam(page,parameter,type);
	}

	@Override
	public VPropertyOwner getVPropertyOwnerById(String ownerId) {
		
		return dao.getVPropertyOwnerById(ownerId);
	}

	@Override
	public String addPropertyOwner(PropertyOwner owner) {
		return dao.addPropertyOwner(owner);
	}

	@Override
	public void deletePropertyOwner(String ownerId) {
		dao.deletePropertyOwner(ownerId);
	}

	@Override
	public PropertyOwner getPropertyOwnerById(String ownerId) {
		return dao.getPropertyOwnerById(ownerId);
	}

	@Override
	public boolean checkUniPhone(String fieldValue) {
		return dao.checkUniPhone(fieldValue);
	}
	
	@Override
	public String getRoomId(String username) {
		// TODO Auto-generated method stub
		return dao.getRoomId(username);
	}

	@Override
	public List getOwnerInfo(String username) {
		// TODO Auto-generated method stub
		return dao.getOwnerInfo(username);
	}

	@Override
	public List<VPropertyOwner> getFamily(String ownerId) {
		return dao.getFamily(ownerId);
	}

	@Override
	public PropertyOwner queryOwnerByPhone(String phone) {
		// TODO Auto-generated method stub
		return dao.queryOwnerByPhone(phone);
	}

	/* (non-Javadoc)
	 * @see com.jdry.pms.basicInfo.service.OwnerInfoService#queryOwnerByRoomId(java.util.Map)
	 */
	@Override
	public List queryOwnerByRoomId(Map<String, String> parameter) {
		// TODO Auto-generated method stub
		return dao.queryOwnerByRoomId(parameter);
	}

	@Override
	public void queryOwnerVisitorList(Page<VPropertyOwner> page,
			Map<String, Object> parameter) {
		dao.queryOwnerVisitor(page,parameter);
	}

	@Override
	public List<BigInteger> getMySettingPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.getMySettingPage(map);
	}

	@Override
	public List<VOwnerRoomBasicInfo> querOwnerRoomBasicInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.querOwnerRoomBasicInfo(parameter);
	}
}
