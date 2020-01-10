package com.jdry.pms.assignWork.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.dao.VisitWorkServerInterfaceServiceDAO;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.service.VisitWorkServerInterfaceService;

@Repository
@Component
public class VisitWorkServerInterfaceServiceImpl implements VisitWorkServerInterfaceService {
	@Resource
	VisitWorkServerInterfaceServiceDAO dao;
	
	@Override
	public WorkMainDispatchEntity getVisitInfo(String mtn_id)
	{
		return dao.getVisitInfo(mtn_id);
	}

}
