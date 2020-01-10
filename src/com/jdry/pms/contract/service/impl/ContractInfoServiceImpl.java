package com.jdry.pms.contract.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.dao.ContractInfoDao;
import com.jdry.pms.contract.pojo.ContractAttachRela;
import com.jdry.pms.contract.pojo.ContractAttachment;
import com.jdry.pms.contract.pojo.ContractInfo;
import com.jdry.pms.contract.pojo.VContractAttachment;
import com.jdry.pms.contract.pojo.VContractInfo;
import com.jdry.pms.contract.service.ContractInfoService;

@Repository
@Component
public class ContractInfoServiceImpl implements ContractInfoService{
	
	@Resource
	ContractInfoDao dao;

	@Override
	public void queryContractByParam(Page<VContractInfo> page,
			Map<String, Object> parameter, String type) {
		dao.queryContractByParam(page,parameter,type);
	}

	@Override
	public ContractInfo getContractById(String contractId) {
		
		return dao.getContractById(contractId);
	}

	@Override
	public String addContractInfo(ContractInfo contract) {
		return dao.addContractInfo(contract);
	}

	@Override
	public void deleteContractInfo(String contractId) {
		dao.deleteContractInfo(contractId);
	}

	@Override
	public Collection<ContractInfo> queryContractByParam(Map<String, Object> param) {
		return dao.queryContractByParam(param);
	}

	@Override
	public String saveContractAttachment(ContractAttachment atta) {
		return dao.saveContractAttachment(atta);
	}

	@Override
	public void saveContractAttachRela(ContractAttachRela rela) {
		dao.saveContractAttachRela(rela);
	}

	@Override
	public List<VContractAttachment> getContractAttachmentById(String contractId) {
		return dao.getContractAttachmentById(contractId);
	}

	@Override
	public ContractAttachment getAttachmentByFileId(String fileId) {
		return dao.getAttachmentByFileId(fileId);
	}

	@Override
	public void deleteAttachment(String fileId) {
		dao.deleteAttachment(fileId);
	}
}
