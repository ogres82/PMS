package com.jdry.pms.assignWork.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.dao.PropertyManageComplaintDao;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.service.PropertyManageComplaintService;

@Repository
@Component
public class PropertyManageComplaintServiceImpl implements PropertyManageComplaintService 
{
	@Resource
	PropertyManageComplaintDao propertyManageComplaintDao;
	/**
	 * 获取派工单历史
	 */
	@Override
	public List getDispatchList(String dispatch_id)
	{
		return propertyManageComplaintDao.getDispatchList(dispatch_id);
	}
	@Override
	
	public List getDispatchHistory(String comp_id)
	{
		
		return propertyManageComplaintDao.getDispatchHistory(comp_id);
		
	}
	@Override
	public List getDisPatchComplaintDetail(String mtr_id)
	{
		return propertyManageComplaintDao.getDisPatchComplaintDetail(mtr_id);
	}
	@Override
	public VWorkCompEntity getComplaintHandle(String mtn_id) {
		
		return propertyManageComplaintDao.getComplaintHandle(mtn_id);
	}

}
