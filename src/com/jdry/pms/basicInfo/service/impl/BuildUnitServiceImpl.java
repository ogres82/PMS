package com.jdry.pms.basicInfo.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.basicInfo.dao.BuildUnitDao;
import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.VBuildingUnitAllInfo;
import com.jdry.pms.basicInfo.service.BuildUnitService;
import com.jdry.pms.comm.dao.impl.BaseDaoImpl;

@Service
public class BuildUnitServiceImpl extends BaseDaoImpl<BuildUnit> implements BuildUnitService{
	@Resource
	BuildUnitDao dao;
	
	public List<BuildUnit> queryBuildUnitInfo(Map<String, Object> params){
		return dao.queryBuildUnitInfo(params);		
	};
}
