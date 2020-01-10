package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.VAreaProperty;

@Repository
public interface AreaPropertyService {

	public void query(Page<AreaProperty> page, Map<String, Object> parameter,
			Criteria criteria);

	public void saveAreaProperty(Collection<AreaProperty> areaPropertys);

	public List<AllArea> getAllAreaId(Map<String, Object> parameter);

	public Collection<AreaProperty> queryAreaPropertyByParam(Map<String, Object> parameter);

	public Collection<AreaProperty> queryAreaPropertyByParent(String id);

	public void queryAreaPropertyByParam(Page<VAreaProperty> page,
			Map<String, Object> parameter, String type);

	public String addAreaProperty(AreaProperty areaProperty);

	public void deleteAreaProperty(String buildId);

	public AreaProperty getAreaPropertyById(
			String communityId);

	public AreaProperty getAreaPropertyByName(
			String communityName);
}
