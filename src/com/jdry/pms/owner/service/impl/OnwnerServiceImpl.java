package com.jdry.pms.owner.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.owner.dao.OnwnerDao;
import com.jdry.pms.owner.service.OnwnerService;

@Repository
@Component
public class OnwnerServiceImpl implements OnwnerService {

	@Resource
	private OnwnerDao dao;
	
	@Override
	public List findOnwerInfo(Map<String, Object> parameter) 
	{
		 List ls=dao.findOnwerInfo(parameter);
		 
		 return ls;
	}

}
