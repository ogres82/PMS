package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.comm.service.BaseService;

@Repository
public interface AllAreaService extends BaseService<AllArea>{

	public void query(Page<AllArea> page, Map<String, Object> parameter,
			Criteria criteria);

	public void saveAllArea(Collection<AllArea> areas);

	public Collection<AllArea> queryAllAreaByParam(Map<String, Object> parameter);

	public void queryPropertyByParam(Page<AllArea> page,
			Map<String, Object> parameter, String type);

	public AllArea getAllAreaById(String buildId);

	public String addAllArea(AllArea allArea);

	public void deleteAllArea(String buildId);
	
	public AllArea getAllAreaByName(String buildName);

}
