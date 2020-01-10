package com.jdry.pms.houseWork.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.houseWork.dao.HouseWorkTypeDao;
import com.jdry.pms.houseWork.pojo.HouseworkType;
import com.jdry.pms.houseWork.service.HouseWorkTypeService;

@Repository
@Component
public class HouseWorkTypeServiceImpl implements HouseWorkTypeService {
    @Resource
    HouseWorkTypeDao houseWorkTypeDao;
	@Override
	public List<HouseworkType> queryType()
	{
		return houseWorkTypeDao.queryType();
	}

}
