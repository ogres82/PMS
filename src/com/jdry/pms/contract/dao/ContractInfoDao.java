package com.jdry.pms.contract.dao;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.pojo.ContractAttachRela;
import com.jdry.pms.contract.pojo.ContractAttachment;
import com.jdry.pms.contract.pojo.ContractInfo;
import com.jdry.pms.contract.pojo.VContractAttachment;
import com.jdry.pms.contract.pojo.VContractInfo;

@Repository
@Transactional
public class ContractInfoDao extends HibernateDao{

	public void queryContractByParam(Page<VContractInfo> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VContractInfo.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VContractInfo.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and ( a.contractCode like:contractCode "+
							 " or a.contractName like:contractName "+
							 " or a.contractTypeName like:contractTypeName "+
							 " or a.contractStatusName like:contractStatusName )";
				sqlCount += " and ( b.contractCode like:contractCode "+
							" or b.contractName like:contractName "+
							" or b.contractTypeName like:contractTypeName "+
							" or b.contractStatusName like:contractStatusName )";
						    
				map.put("contractCode", "%"+search+"%");
				map.put("contractName", "%"+search+"%");
				map.put("contractTypeName", "%"+search+"%");
				map.put("contractStatusName", "%"+search+"%");
			}
		}		
		hql += whereCase;				
		System.out.println("hql==="+hql);
		
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	public ContractInfo getContractById(String contractId) {

		List<ContractInfo> lists = this
				.query("from " + ContractInfo.class.getName()
						+ " where contractId='" + contractId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public String addContractInfo(ContractInfo contract) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(contract);
		session.flush();
		session.close();
		return contract.getContractId();
	}

	public void deleteContractInfo(String contractId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = contractId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			ContractInfo contract = getContractById(ids[i]);
			if(null != contract){
				session.delete(contract);
			}
		}
		session.flush();
		session.close();
		deleteContractAttachRela(contractId);
	}
	
	public void deleteContractAttachRela(String contractId) {
		Session session = this.getSessionFactory().openSession();
		String fileIds = "";
		String[] ids = contractId.split(",");
		for(int i = 0;i<ids.length;i++){
			List<ContractAttachRela> relas = getContractAttachRelaById(ids[i]);
			for(int j=0;j<relas.size();j++){
				session.delete(relas.get(j));
				if(fileIds.isEmpty()){
					fileIds = relas.get(j).getFileId();
				}else{
					fileIds += ","+relas.get(j).getFileId();
				}
			}
		}
		session.flush();
		session.close();
		deleteContractAttach(fileIds);
	}
	
	public void deleteContractAttach(String fileIds) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = fileIds.split(",");
		for(int i = 0;i<ids.length;i++){
			ContractAttachment rela = getAttachmentByFileId(ids[i]);
			session.delete(rela);
			File file = new File(rela.getFilePath());
		    if (file.isFile() && file.exists())
		    {
		    	file.delete();
		    }
		}
		session.flush();
		session.close();
	}

	private List<ContractAttachRela> getContractAttachRelaById(String contractId) {
		
		List<ContractAttachRela> lists = this
				.query("from " + ContractAttachRela.class.getName()
						+ " where contractId='" + contractId + "'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public Collection<ContractInfo> queryContractByParam(Map<String, Object> param) {
		
		String contractId = param.get("contractId")==null?"":param.get("contractId").toString();
		
		String keyword = param.get("keyword")==null?"":param.get("keyword").toString();


		String sql  = "from " + ContractInfo.class.getName()+ " where 1=1 ";
		if(!contractId.isEmpty()){
			sql += " and contractId = '"+contractId+"' ";
		}
		if(!keyword.isEmpty()){
			sql += " and (contractCode like '%"+keyword+"%' or contractName like '%"+keyword+"%') ";
		}
		List<ContractInfo> lists = this.query(sql);
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public String saveContractAttachment(ContractAttachment atta) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(atta);
		String fileId = atta.getFileId();
		session.flush();
		session.close();
		return fileId;
	}

	public void saveContractAttachRela(ContractAttachRela rela) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(rela);
		session.flush();
		session.close();
	}

	public List<VContractAttachment> getContractAttachmentById(String contractId) {
		if(!contractId.isEmpty()){
			String sql  = "from " + VContractAttachment.class.getName()+ " where contractId = '"+contractId+"' ";
			List<VContractAttachment> lists = this.query(sql);
			if (null != lists && lists.size() > 0) {
				return lists;
			} else {
				return null;
			}
			
		}else{
			return null;
		}
	}

	public ContractAttachment getAttachmentByFileId(String fileId) {
		if(!fileId.isEmpty()){
			String sql  = "from " + ContractAttachment.class.getName()+ " where fileId = '"+fileId+"' ";
			List<ContractAttachment> lists = this.query(sql);
			if (null != lists && lists.size() > 0) {
				return lists.get(0);
			} else {
				return null;
			}
			
		}else{
			return null;
		}
	}

	public void deleteAttachment(String fileId) {
		Session session = this.getSessionFactory().openSession();
		ContractAttachment atta = getAttachmentByFileId(fileId);
		session.delete(atta);
		File file = new File(atta.getFilePath());
		if (file.isFile() && file.exists())
		{
			file.delete();
		}
		session.flush();
		session.close();
		deleteAttachmentRelaByFileId(fileId);
	}
	
	public void deleteAttachmentRelaByFileId(String fileId) {
		Session session = this.getSessionFactory().openSession();
		List<ContractAttachRela> relas = getContractAttachRelaByFileId(fileId);
		for(int j=0;j<relas.size();j++){
			session.delete(relas.get(j));
		}
		session.flush();
		session.close();
	}
	
	public List<ContractAttachRela> getContractAttachRelaByFileId(String fileId) {
		
		List<ContractAttachRela> lists = this
				.query("from " + ContractAttachRela.class.getName()
						+ " where fileId='" + fileId + "'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

}
