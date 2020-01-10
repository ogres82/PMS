package com.jdry.pms.houseWork.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.pojo.VHouseWorkEventSendEnitiy;
/**
 * 描述：家政服务
 * @author hezuping
 *
 */
@Repository
public interface HouseworkEventService 
{
	
	public boolean saveHouseWorkEvent(HouseworkEventEntity work);

	/**
	 * 查询事件内容
	 * @param page
	 * @param parameter
	 * @param object
	 * @return
	 */
	public List queryHouseWorkEventInfo(Page page,Map<String, Object> parameter, Object object);

	public String findEventNOByNo(String event_no);
/**
 * 通过事件编号查询事件
 * @param event_no
 * @return
 */
	public HouseworkEventEntity queryHouseWorkEventInfoByNo(String event_no);
   /**
    * 存储派工单信息
    * @param send
    * @return
    */
    public boolean saveHouseWorkSend(HouseWorkEventSendEntity send);

    public void deleteHouseWorkInfo(String houseWorkId);

	public VHouseWorkEventSendEnitiy queryhouseWorkInfoByNo(String event_no);

}
