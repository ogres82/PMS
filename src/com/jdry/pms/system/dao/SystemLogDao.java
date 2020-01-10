package com.jdry.pms.system.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.system.pojo.SystemLog;

@Repository
@Transactional
public class SystemLogDao extends HibernateDao{
	
	public void createSystemLog(SystemLog log){
		Session session = this.getSessionFactory().openSession();
		session.save(log);
		session.flush();
		session.close();
	}

}
