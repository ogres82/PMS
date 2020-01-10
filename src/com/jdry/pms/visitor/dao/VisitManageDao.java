package com.jdry.pms.visitor.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.visitor.pojo.VisitorRec;
@Repository
@Transactional
public class VisitManageDao extends HibernateDao{
	
	public void addVisitRec(VisitorRec rec){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(rec);
		session.flush();
		session.close();
	}
	
	public List<VisitorRec> loadVisitRec(String managerId){
		String hql = "from VisitorRec where managerId='"+managerId+"' order by visitTime desc";
		return this.query(hql);
	}
	
	public List<VisitorRec> loadVisitRecByOwner(String ownerId){
		String hql = "from VisitorRec where ownerId='"+ownerId+"' order by visitTime desc";
		return this.query(hql);
	}

	public List<VisitorRec> loadAllVisitRec(){
		String hql = "from VisitorRec order by visitTime desc";
		return this.query(hql);
	}
}
