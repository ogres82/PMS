package com.jdry.pms.houseWork.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.houseWork.dao.HouseworkPropertManageInterfaceDao;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;
import com.jdry.pms.houseWork.service.HouseworkPropertManageInterfaceService;

/**
 * 
 * @author hezuping
 * 
 */
@Repository
@Component
public class HouseworkPropertManageInterfaceServiceImpl implements
		HouseworkPropertManageInterfaceService {

	@Resource
	HouseworkPropertManageInterfaceDao houseworkPropertManageInterfaceDao;

	@Override
	public List quaryHouseKeepingList(String oper_id) {
		if (oper_id.equals("") || oper_id == null) {
			return null;
		} else {
			return houseworkPropertManageInterfaceDao.queryHouseKeeping(oper_id);
		}
	}

	@Override
	public Object getHouseKeepingDeatail(String event_no)
	{
		if(event_no.equals(""))
		{
			return "";
		}else{
		return houseworkPropertManageInterfaceDao.getHouseKeepingDeatail(event_no);
		}
	}

	@Override
	public String getRoomNoById(String room_id) {
		
		return houseworkPropertManageInterfaceDao.getRoomNoById(room_id);
	}

	@Override
	public HouseworkVisitEntity quaryVistInfoById(String id) {
		return houseworkPropertManageInterfaceDao.quaryVistInfoById(id);
	}

}
