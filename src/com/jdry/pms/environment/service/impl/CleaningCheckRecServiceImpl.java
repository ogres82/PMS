package com.jdry.pms.environment.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.dao.CleaningCheckRecDao;
import com.jdry.pms.environment.pojo.EnvironmentCleaningCheckRec;
import com.jdry.pms.environment.pojo.VEnvironmentCleaningCheckRec;
import com.jdry.pms.environment.service.CleaningCheckRecService;

@Repository
@Component
public class CleaningCheckRecServiceImpl implements CleaningCheckRecService{

	@Resource
	CleaningCheckRecDao dao;

	@Override
	public void queryCheckRecByParam(Page<VEnvironmentCleaningCheckRec> page,
			Map<String, Object> parameter, String type) {
		dao.queryCheckRecByParam(page,parameter,type);
	}

	@Override
	public EnvironmentCleaningCheckRec getVCheckRecById(String recId) {
		return dao.getVCheckRecById(recId);
	}

	@Override
	public void addCheckRec(EnvironmentCleaningCheckRec checkRec) {
		dao.addCheckRec(checkRec);
	}

	@Override
	public void deleteCheckRec(String recId) {
		dao.deleteCheckRec(recId);
	}
}
