package com.jdry.pms.houseWork.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.dao.HouseworkEventVisitDao;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;
import com.jdry.pms.houseWork.service.HouseworkEventVisitSevice;

@Repository
@Component
public class HouseworkEventVisitSeviceImpl implements HouseworkEventVisitSevice {
     @Resource
     HouseworkEventVisitDao houseworkEventVisitDao;
	@Override
	public List queryHouseWorkEventVisitInfo(Page page,Map<String, Object> parameter, Object object)
	{
		return houseworkEventVisitDao.queryHouseWorkEventVisitInfo(page, parameter, object);
	}
	@Override
	public boolean saveEventVisit(HouseworkVisitEntity vi) {
		
		return houseworkEventVisitDao.saveEventVisit(vi);
	}
	
	@Override
	public HouseworkVisitEntity queryVisitInfo(String event_no)
	{
		return houseworkEventVisitDao.queryVisitInfo(event_no);
	}

}
