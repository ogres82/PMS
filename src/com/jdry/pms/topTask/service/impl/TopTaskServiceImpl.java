package com.jdry.pms.topTask.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.topTask.dao.TopTaskDao;
import com.jdry.pms.topTask.service.TopTaskService;

@Repository
@Component
public class TopTaskServiceImpl implements TopTaskService
{
	 @Resource
	 TopTaskDao topTaskDao;
	 
	/**
	 * 代办总条数
	 * 
	 */
	@Override
	public int getTaskCountByUserId(String userId)
	{
		return topTaskDao.getTaskCountByUserId(userId);
	}

	@Override
	public List queryTopTaskInfo(Page page, Map<String, Object> parameter,Object object)
	{
		return topTaskDao.queryTopTaskInfo(page,parameter,object);
	}

	@Override
	public List queryTopTaskInfoByUserId(String userId)
	{
		
		return topTaskDao.queryTopTaskInfoByUserId(userId);
	}
}
