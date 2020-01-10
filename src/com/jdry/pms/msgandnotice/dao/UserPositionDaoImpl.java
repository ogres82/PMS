package com.jdry.pms.msgandnotice.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

@Repository
@Transactional
public class UserPositionDaoImpl extends HibernateDao{
	public UserPosition findUserPositionById(String posId) {
		List<UserPosition> result =new ArrayList<UserPosition>();
		try {
			Session session = this.getSessionFactory().openSession();
			result=session.createQuery((new StringBuilder()).append(" from ").append(UserPosition.class.getName()).
	        		append(" u where u.positionId=:positionId").toString()).setString("positionId", posId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.get(0);

	}
	public Collection<UserPosition> findUserPosition() {
		List<UserPosition> result =new ArrayList<UserPosition>();
		try {
			Session session = this.getSessionFactory().openSession();
			result=session.createQuery((new StringBuilder()).append(" from ").append(UserPosition.class.getName()).
	        		append(" u ").toString()).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
