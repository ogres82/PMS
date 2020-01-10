package com.jdry.pms.contract.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.dao.ContractRecDao;
import com.jdry.pms.contract.pojo.ContractRec;
import com.jdry.pms.contract.pojo.VContractRec;
import com.jdry.pms.contract.service.ContractRecService;

@Repository
@Component
public class ContractRecServiceImpl implements ContractRecService{

	@Resource
	ContractRecDao dao;

	@Override
	public void queryVContractRecByParam(Page<VContractRec> page,
			Map<String, Object> parameter, String type) {
		dao.queryVContractRecByParam(page,parameter,type);
	}

	@Override
	public VContractRec getVContractRecById(String recId) {
		
		return dao.getVContractRecById(recId);
	}

	@Override
	public void addContractRec(ContractRec contractRec) {
		dao.addContractRec(contractRec);
	}

	@Override
	public void deleteContractRec(String recId) {
		dao.deleteContractRec(recId);
	}
}
