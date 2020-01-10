package com.jdry.pms.houseWork.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.houseWork.dao.HouseWorkOwnerInterfaceDao;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.service.HouseWorkOwnerInterfaceService;
/**
 * 描述：业主端实现——APP
 * @author hezuping
 * 2016年5月16日14:29:26
 */
@Repository
@Component
public class HouseWorkOwnerInterfaceServiceImpl implements HouseWorkOwnerInterfaceService 
{

	@Resource
	HouseWorkOwnerInterfaceDao houseWorkOwnerInterfaceDao;
	
	@Override
	public boolean supplyHouseWorkEventInfo(HouseworkEventEntity work)
	{
		return houseWorkOwnerInterfaceDao.supplyHouseWorkEventInfo(work);
	}

	@Override
	public List getAllHouseWorkEventInfoByPhone(String phone)
	{
		if(phone==null||phone.equals(""))
		{
		 List ls=new ArrayList();
		 return ls;
		}else{
		 return houseWorkOwnerInterfaceDao.getAllHouseWorkEventInfoByPhone(phone);
		}
	}

	@Override
	public HouseworkEventEntity getHouseWorkEventByNo(String event_no)
	{
		return houseWorkOwnerInterfaceDao.getHouseWorkEventByNo(event_no);
	}

	@Override
	public List getHouseWorkDispatchDetailByNo(String event_no)
	{
		if(!("").equals(event_no)||event_no!=null)
		{
			return houseWorkOwnerInterfaceDao.getHouseWorkDispatchDetailByNo(event_no);
		}else
		{
			return null;
		}
		
	}

	@Override
	public List getHouseWorkPropery()
	{
		return houseWorkOwnerInterfaceDao.getHouseWorkPropery();
	}

}
