package com.jdry.pms.houseWork.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;

/**
 * 家政服务——业主端service层
 * @author hezuping
 * @2016年5月16日14:26:56
 */
@Component
public interface HouseWorkOwnerInterfaceService 
{

	public boolean supplyHouseWorkEventInfo(HouseworkEventEntity work);
	/**
	 * t通过业主电话号码查询属于业主的所有工单信息
	 * @param phone
	 * @return
	 */
	public List getAllHouseWorkEventInfoByPhone(String phone);
	/**
	 * 通过家政事件单号查找事件
	 * @param event_no
	 */
	public HouseworkEventEntity getHouseWorkEventByNo(String event_no);
	/**
	 * 通过事件单号查询流程历史
	 * @param event_no
	 * @return
	 */
	public List getHouseWorkDispatchDetailByNo(String event_no);
	/**
	 * 获取家政项目——APP端
	 *  @return
	 */
	public List getHouseWorkPropery();
}
