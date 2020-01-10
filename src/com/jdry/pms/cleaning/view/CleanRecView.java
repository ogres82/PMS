package com.jdry.pms.cleaning.view;

import java.util.Map;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.cleaning.pojo.CleanRec;
import com.jdry.pms.cleaning.service.CleanRecService;

//@Component
public class CleanRecView {
//	@Resource
	CleanRecService service;
	public void queryCleanRec(Page<CleanRec> page,Map<String, Object> parameter,Criteria criteria){
		service.query(page,parameter,criteria);
	}
}
