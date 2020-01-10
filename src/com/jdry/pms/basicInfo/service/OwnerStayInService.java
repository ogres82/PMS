package com.jdry.pms.basicInfo.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.HouseProperty;

@Repository
public interface OwnerStayInService {
	public void query(Page<HouseProperty> page, Map<String, Object> parameter,
			Criteria criteria);
}
