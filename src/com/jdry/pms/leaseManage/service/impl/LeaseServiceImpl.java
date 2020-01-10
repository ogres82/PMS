package com.jdry.pms.leaseManage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.leaseManage.dao.LeaseDao;
import com.jdry.pms.leaseManage.pojo.LeaseInfo;
import com.jdry.pms.leaseManage.service.LeaseService;

@Service
public class LeaseServiceImpl implements LeaseService{	
	@Resource
	public LeaseDao dao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getLeaseInfoAll(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.getLeaseInfoAll(parameter);
	}

	@Override
	public void saveLeaseInfo(LeaseInfo li,String operate) {
		// TODO Auto-generated method stub
		dao.saveLeaseInfo(li,operate);
	}

	@Override
	public void delLeaseInfo(String Id) {
		// TODO Auto-generated method stub
		dao.delLeaseInfo(Id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryShopInfo(String keyword) {
		// TODO Auto-generated method stub
		return dao.queryShopInfo(keyword);
	}
}
