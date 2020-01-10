package com.jdry.pms.gis.app.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.gis.app.entity.GisUserLocation;

@Repository
@Transactional
public class SendEmpLocationInfoDao extends HibernateDao{

	public String insertUserLocation(GisUserLocation loc) {
	    Session session = this.getSessionFactory().openSession();
	    session.save(loc);
	    session.flush();
	    session.close();
		return loc.getId();
	}

}
