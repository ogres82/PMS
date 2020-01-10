package com.jdry.pms.contract.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.pojo.ContractRec;
import com.jdry.pms.contract.pojo.VContractRec;

@Repository
public interface ContractRecService {

	public void queryVContractRecByParam(Page<VContractRec> page,
			Map<String, Object> parameter, String type);

	public VContractRec getVContractRecById(String recId);

	public void addContractRec(ContractRec contractRec);

	public void deleteContractRec(String recId);

}
