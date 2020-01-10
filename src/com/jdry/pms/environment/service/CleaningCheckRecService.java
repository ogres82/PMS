package com.jdry.pms.environment.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentCleaningCheckRec;
import com.jdry.pms.environment.pojo.VEnvironmentCleaningCheckRec;

@Repository
public interface CleaningCheckRecService {

	public void queryCheckRecByParam(Page<VEnvironmentCleaningCheckRec> page,
			Map<String, Object> parameter, String type);

	public EnvironmentCleaningCheckRec getVCheckRecById(String recId);

	public void addCheckRec(EnvironmentCleaningCheckRec checkRec);

	public void deleteCheckRec(String recId);

}
