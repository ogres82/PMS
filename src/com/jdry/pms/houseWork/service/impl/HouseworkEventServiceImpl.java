package com.jdry.pms.houseWork.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.service.impl.ExpireEventService;
import com.jdry.pms.houseWork.dao.HouseworkEventDao;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.pojo.VHouseWorkEventSendEnitiy;
import com.jdry.pms.houseWork.service.HouseworkEventService;
/**
 * 
 * @author hezuping
 *
 */
@Repository
@Component
public class HouseworkEventServiceImpl implements HouseworkEventService {
    @Resource
    HouseworkEventDao houseworkEventDao;
	@Override
	public boolean saveHouseWorkEvent(HouseworkEventEntity work) 
	{
		boolean flag = houseworkEventDao.saveHouseWorkEvent(work);
		ExpireEventService.dispatchToRedis(work);
		return flag;
	}
	@Override
	public List queryHouseWorkEventInfo(Page page,Map<String, Object> parameter, Object object)
	{
		return houseworkEventDao.queryHouseWorkEventInfo(page,parameter,object);
	}
	@Override
	public String findEventNOByNo(String event_no)
	{
		return houseworkEventDao.findEventNOByNo(event_no);
	}
	@Override
	public HouseworkEventEntity queryHouseWorkEventInfoByNo(String event_no) {
		return houseworkEventDao.queryHouseWorkEventInfoByNo(event_no);
	}
	@Override
	public boolean saveHouseWorkSend(HouseWorkEventSendEntity send) {
		boolean flag = houseworkEventDao.saveHouseWorkSend(send);
		ExpireEventService.dispatchToRedis(send);
		return flag;
		
	}
	@Override
	public void deleteHouseWorkInfo(String houseWorkId) {
		try {
			houseworkEventDao.deleteHouseWorkInfo(houseWorkId);
		} catch (SQLException e) {
			System.out.println("删除异常");
		}
		
	}
	@Override
	public VHouseWorkEventSendEnitiy queryhouseWorkInfoByNo(String event_no) {
		return houseworkEventDao.queryhouseWorkInfoByNo(event_no);
	}

}
