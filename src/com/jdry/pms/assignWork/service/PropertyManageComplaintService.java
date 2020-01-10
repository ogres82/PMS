package com.jdry.pms.assignWork.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jdry.pms.assignWork.pojo.VWorkCompEntity;

/**
 * 
 * @author hezuping
 *
 */
@Component
public interface PropertyManageComplaintService 
{
	/**
	 * 获取派工单历史
	 * @param dispatch_id
	 * @return
	 */
    public List getDispatchList(String dispatch_id);
	/**
	 * 获取派工单的处理人编号
	 * @param comp_id
	 * @return
	 */
	public List getDispatchHistory(String operation_id);
	/**
	 * 物管端获取派工单详情
	 * @return
	 */
    public List getDisPatchComplaintDetail(String mtr_id);
	/**
	 * 投诉处理根据单号获取信息
	 * @param mtn_id
	 */
    public VWorkCompEntity getComplaintHandle(String mtn_id);
}
