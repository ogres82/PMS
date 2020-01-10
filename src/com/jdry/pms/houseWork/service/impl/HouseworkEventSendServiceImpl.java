package com.jdry.pms.houseWork.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.dao.HouseworkEventSendDao;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.service.HouseworkEventSendService;

@Repository
@Component
public class HouseworkEventSendServiceImpl implements HouseworkEventSendService {

	 @Resource
	 HouseworkEventSendDao houseworkEventSendDao;
	/**
	 * 查询派工单数据
	 */
	@Override
	public List queryHouseWorkEventSendInfo(Page page,Map<String, Object> parameter, Object object)
	{
		return houseworkEventSendDao.queryHouseWorkEventSendInfo(page,parameter,object);
	}
	@Override
	public HouseWorkEventSendEntity queryHouseWorkSendInfo(String send_id) {
		return houseworkEventSendDao.queryHouseWorkSendInfoById(send_id);
	}
	@Override
	public HouseWorkEventSendEntity queryHouseWorkById(String eventId)
	{
		// TODO Auto-generated method stub
		return houseworkEventSendDao.queryHouseWorkById(eventId);
	}
	@Override
	public HouseWorkEventSendEntity queryHouseWorkSendInfoByEventNo(
			String event_no) {
		return houseworkEventSendDao.queryHouseWorkSendInfoByEventNo(event_no);
	}

}
