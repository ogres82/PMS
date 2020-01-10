package com.jdry.pms.contract.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.pojo.ContractAttachRela;
import com.jdry.pms.contract.pojo.ContractAttachment;
import com.jdry.pms.contract.pojo.ContractInfo;
import com.jdry.pms.contract.pojo.VContractAttachment;
import com.jdry.pms.contract.pojo.VContractInfo;

@Repository
public interface ContractInfoService {

	public void queryContractByParam(Page<VContractInfo> page,
			Map<String, Object> parameter, String type);

	public ContractInfo getContractById(String contractId);

	public String addContractInfo(ContractInfo contract);

	public void deleteContractInfo(String contractId);
	
	public Collection<ContractInfo> queryContractByParam(Map<String, Object> param);

	public String saveContractAttachment(ContractAttachment atta);

	public void saveContractAttachRela(ContractAttachRela rela);

	public List<VContractAttachment> getContractAttachmentById(String contractId);

	public ContractAttachment getAttachmentByFileId(String fileId);

	public void deleteAttachment(String fileId);

}
