package com.jdry.pms.environment.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentCleaningAreaDuty;

@Repository
@Transactional
public class CleaningAreaDutyDao extends HibernateDao{

	public void queryAreaDutyByParam(Page<EnvironmentCleaningAreaDuty> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+EnvironmentCleaningAreaDuty.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+EnvironmentCleaningAreaDuty.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and ( a.planCode like:planCode "+
							 " or a.cleaningArea like:cleaningArea " +
							 " or a.cleaningPerson like:cleaningPerson)";
				sqlCount += " and ( b.planCode like:planCode "+
							 " or b.cleaningArea like:cleaningArea " +
							 " or b.cleaningPerson like:cleaningPerson)";
						    
				map.put("planCode", "%"+search+"%");
				map.put("cleaningArea", "%"+search+"%");
				map.put("cleaningPerson", "%"+search+"%");
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

	public void addAreaDuty(EnvironmentCleaningAreaDuty areaDuty) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(areaDuty);
		session.flush();
		session.close();
		session = null;
	}

	public void deleteAreaDuty(String planId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = planId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			EnvironmentCleaningAreaDuty areaDuty = getAreaDutyById(ids[i]);
			if(null != areaDuty){
				session.delete(areaDuty);
			}
		}
		session.flush();
		session.close();
	}

	public EnvironmentCleaningAreaDuty getAreaDutyById(String planId) {
		List<EnvironmentCleaningAreaDuty> lists = this
				.query("from " + EnvironmentCleaningAreaDuty.class.getName()
						+ " where planId='" + planId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public Collection<EnvironmentCleaningAreaDuty> queryCheckRecByParam(
			Map<String, Object> param) {
		String recId = param.get("recId")==null?"":param.get("recId").toString();
		
		String sql  = "from " + EnvironmentCleaningAreaDuty.class.getName()+ " where 1=1 ";
		if(recId!=null && !recId.isEmpty()){
			sql += " and recId = '"+recId+"' ";
		}
		List<EnvironmentCleaningAreaDuty> lists = this.query(sql);
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	
}
