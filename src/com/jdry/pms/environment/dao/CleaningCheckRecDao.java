package com.jdry.pms.environment.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentCleaningCheckRec;
import com.jdry.pms.environment.pojo.VEnvironmentCleaningCheckRec;

@Repository
@Transactional
public class CleaningCheckRecDao extends HibernateDao{

	public void queryCheckRecByParam(Page<VEnvironmentCleaningCheckRec> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VEnvironmentCleaningCheckRec.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VEnvironmentCleaningCheckRec.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and ( a.cleaningCheckDetail like:cleaningCheckDetail "+
							 " or a.cleaningCheckRes like:cleaningCheckRes )";
				sqlCount += " and ( b.cleaningCheckDetail like:cleaningCheckDetail "+
							 " or b.cleaningCheckRes like:cleaningCheckRes)";
						    
				map.put("cleaningCheckDetail", "%"+search+"%");
				map.put("cleaningCheckRes", "%"+search+"%");
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

	public EnvironmentCleaningCheckRec getVCheckRecById(String recId) {

		List<EnvironmentCleaningCheckRec> lists = this
				.query("from " + EnvironmentCleaningCheckRec.class.getName()
						+ " where recId='" + recId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public EnvironmentCleaningCheckRec getCheckRecById(String recId) {

		List<EnvironmentCleaningCheckRec> lists = this
				.query("from " + EnvironmentCleaningCheckRec.class.getName()
						+ " where recId='" + recId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addCheckRec(EnvironmentCleaningCheckRec checkRec) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(checkRec);
		session.flush();
		session.close();
		session = null;
	}

	public void deleteCheckRec(String recId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = recId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			EnvironmentCleaningCheckRec checkRec = getCheckRecById(ids[i]);
			if(null != checkRec){
				session.delete(checkRec);
			}
		}
		session.flush();
		session.close();
		
	}

}
