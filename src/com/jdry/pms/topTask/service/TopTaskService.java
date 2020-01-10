package com.jdry.pms.topTask.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;

/**
 * 代办任务业务层
 * @author hezuping
 * 2016年6月3日09:54:47
 */
@Repository
public interface TopTaskService
{
	//统计代办总条数
	
	public int getTaskCountByUserId(String userId);
	/**
	 * 我的任务列表
	 * @param page
	 * @param parameter
	 * @param object
	 * @return
	 */
	public List queryTopTaskInfo(Page page, Map<String, Object> parameter,	Object object);
	
	/**
	 * 通过物管账号查询出代办信息给APP——端
	 * @param userId
	 * @return
	 */
	public List queryTopTaskInfoByUserId(String userId);
	
	 

	 
}
