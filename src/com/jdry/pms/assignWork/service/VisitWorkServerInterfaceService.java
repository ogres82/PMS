package com.jdry.pms.assignWork.service;

import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;

/**
 * 描述：回访业务接口
 * @author hezuping
 * 时间：2017年1月19日11:06:17
 */
public interface VisitWorkServerInterfaceService 
{
	public WorkMainDispatchEntity getVisitInfo(String Json);
}
