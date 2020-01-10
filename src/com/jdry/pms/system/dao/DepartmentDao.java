package com.jdry.pms.system.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
@Repository
@Transactional
public class DepartmentDao extends HibernateDao{
	public void saveDept(DefaultDept dept){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(dept);
		session.flush();
		session.close();
	}
	
	public void deleteDept(String id){
		DefaultDept dept = getDeptById(id);
		Session session = this.getSessionFactory().openSession();
		session.delete(dept);
		session.flush();
		session.close();
	}
	
	public DefaultDept getDeptById(String id){
		String hql = "from DefaultDept where id = '"+id+"'";
		return (DefaultDept) this.query(hql).get(0);
	}
}
