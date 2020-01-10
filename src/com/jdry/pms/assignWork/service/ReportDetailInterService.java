package com.jdry.pms.assignWork.service;

import java.util.List;

/**
 * 描述：报障报修
 * @author hezuping
 *
 */
public interface ReportDetailInterService
{
	
	public List getReportDetailList(String mtr_id);
	
	/**
	 * 状态事件详情
	 * @param mtr_id
	 * @return
	 */
	public List getReportDeailInfoList(String mtr_id);

	/**
	 * 获取报修历史
	 * @param phone
	 */
	public List getRepairHistoryByPhone(String phone);

}
