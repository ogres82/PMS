package com.jdry.pms.cleaning.service;

import java.util.Map;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.cleaning.pojo.CleanRec;

public interface CleanRecService {
	public void query(Page<CleanRec> page, Map<String, Object> parameter,
			Criteria criteria);
}
