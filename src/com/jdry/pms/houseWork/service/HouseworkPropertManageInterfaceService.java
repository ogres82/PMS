package com.jdry.pms.houseWork.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;

/**
 * 描述：家政服务物管端
 * @author hezuping
 * 2016年5月25日22:58:42
 */
@Component
public interface HouseworkPropertManageInterfaceService
{
    /**
     * 指派的工单
     * @param oper_id
     * @return
     */
	public List quaryHouseKeepingList(String oper_id);
    /**
     * 描述：获取家政详细通过事件ID
     * @param event_no
     * @return
     */
	public Object getHouseKeepingDeatail(String event_no);
	/**
	 * 描述：获取ROOMNO
	 * @param room_id
	 * @return
	 */
	public String getRoomNoById(String room_id);
	
	public HouseworkVisitEntity quaryVistInfoById(String id);
	
	
}
