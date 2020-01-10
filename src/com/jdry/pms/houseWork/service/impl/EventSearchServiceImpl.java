package com.jdry.pms.houseWork.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.dao.EventSearchDao;
import com.jdry.pms.houseWork.service.EventSearchService;


@Repository
@Component
public class EventSearchServiceImpl implements EventSearchService {
    @Resource
    EventSearchDao eventSearchDao;
	@Override
	public List queryHouseWorkEvent(Page page, Map<String, Object> parameter,Object object)
	{
		return eventSearchDao.queryHouseWorkEvent(page, parameter, object);
	}

}
