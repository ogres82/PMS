package com.jdry.pms.assignWork.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.dao.AssignWorkServiceInterfaceDao;
import com.jdry.pms.assignWork.service.AssignWorkServiceInterfaceService;
@Repository
@Component
public class AssignWorkServiceInterfaceServiceImpl implements AssignWorkServiceInterfaceService {
	@Resource
	AssignWorkServiceInterfaceDao assignWorkServiceInterfaceDao;
	
	@Override
	public List queryBasicInfo(String phone)
	{
		if(phone.equals("")||null==phone)
		{
			return null;
		}else
		{
			
			return assignWorkServiceInterfaceDao.queryBasicInfo(phone);
		}
		
	}

	@Override
	public List queryComplaintHistoryByPhone(String phone)
	{
		return assignWorkServiceInterfaceDao.queryComplaintHistoryByPhone(phone);
	}

	@Override
	public List queryComplaintDetailByRptId(String mtn_id)
	{
		return assignWorkServiceInterfaceDao.queryComplaintDetailByRptId(mtn_id);
		
	}

}
