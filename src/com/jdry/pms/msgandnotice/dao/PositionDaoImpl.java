package com.jdry.pms.msgandnotice.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
@Repository
@Transactional
public class PositionDaoImpl extends HibernateDao{
	public Collection<DefaultPosition> findPosition() {
		List<DefaultPosition> result =new ArrayList<DefaultPosition>();
		try {
			Session session = this.getSessionFactory().openSession();
			result=session.createQuery((new StringBuilder()).append(" from ").append(DefaultPosition.class.getName()).
	        		append(" u ").toString()).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	public DefaultPosition findPositionById(String posId){
		DefaultPosition defaultPosition = new DefaultPosition();
		try {
			Session session = this.getSessionFactory().openSession();
			defaultPosition=(DefaultPosition) session.createQuery((new StringBuilder()).append(" from ").append(DefaultPosition.class.getName()).
	        		append(" u where u.id=:id").toString()).setString("id", posId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultPosition;
	}
	public UserPosition findUserByPoId(String posId){
		UserPosition userPosition = new UserPosition();
		try {
			Session session = this.getSessionFactory().openSession();
			userPosition=(UserPosition) session.createQuery((new StringBuilder()).append(" from ").append(UserPosition.class.getName()).
	        		append(" u where u.positionId=:positionId").toString()).setString("positionId", posId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userPosition;
	}
	
	public UserDept findUserDeptByUserId(String userId) {
		UserDept userDept=new UserDept();
		try {
			Session session = this.getSessionFactory().openSession();
			userDept=(UserDept) session.createQuery((new StringBuilder()).append(" from ").append(UserDept.class.getName()).
	        		append(" u where u.username=:username").toString()).setString("username", userId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDept;
	}
	//获取UserPositionList通过UserId
	public List<UserPosition> findUserPositionsByPoId(String sql, Map paraMap) {
		List<UserPosition> userPositionsList=new ArrayList<UserPosition>();
		try {
			userPositionsList=this.query(sql, paraMap);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return userPositionsList;
	}
	
}
