package com.jdry.pms.basicInfo.service;

import java.util.List;
import java.util.Map;

import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.comm.service.BaseService;

public interface BuildUnitService extends BaseService<BuildUnit>{
	public List<BuildUnit> queryBuildUnitInfo(Map<String, Object> params);
}
