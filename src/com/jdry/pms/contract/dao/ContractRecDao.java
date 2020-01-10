package com.jdry.pms.contract.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.pojo.ContractRec;
import com.jdry.pms.contract.pojo.VContractRec;

@Repository
@Transactional
public class ContractRecDao extends HibernateDao{

	public void queryVContractRecByParam(Page<VContractRec> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VContractRec.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VContractRec.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and ( a.contractCode like:contractCode "+
							 " or a.contractName like:contractName "+
							 " or a.contractDealContent like:contractDealContent "+
							 " or a.recPerson like:recPerson )";
				sqlCount += " and ( b.contractCode like:contractCode "+
							" or b.contractName like:contractName "+
							" or b.contractDealContent like:contractDealContent "+
							" or b.recPerson like:recPerson )";
						    
				map.put("contractCode", "%"+search+"%");
				map.put("contractName", "%"+search+"%");
				map.put("contractDealContent", "%"+search+"%");
				map.put("recPerson", "%"+search+"%");
			}
		}		
		hql += whereCase+" order by a.recTime desc ";				
		System.out.println("hql==="+hql);
		
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	public ContractRec getContractRecById(String recId) {

		List<ContractRec> lists = this
				.query("from " + ContractRec.class.getName()
						+ " where recId='" + recId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addContractRec(ContractRec contractRec) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(contractRec);
		session.flush();
		session.close();
		session = null;
	}

	public void deleteContractRec(String recId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = recId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			ContractRec rec = getContractRecById(ids[i]);
			if(null != rec){
				session.delete(rec);
			}
		}
		session.flush();
		session.close();
		
	}

	public VContractRec getVContractRecById(String recId) {
		List<VContractRec> lists = this
				.query("from " + VContractRec.class.getName()
						+ " where recId='" + recId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

}
