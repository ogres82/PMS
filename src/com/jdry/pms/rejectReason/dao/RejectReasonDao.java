package com.jdry.pms.rejectReason.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.rejectReason.pojo.RejectReasonEntity;

@Transactional
@Repository
public class RejectReasonDao extends HibernateDao {
	
	//向APP返回拒绝理由，理由通过使用次数进行排序
	public List<RejectReasonEntity> getRejectReason(String rejectType,int rows){
		String hql = " FROM RejectReasonEntity WHERE 1=1";
		Session session = this.getSessionFactory().openSession();
		if(rejectType.length()>0){
			hql += " AND rejectType = '"+rejectType+"'";
		}
		hql += " order by rejectNum desc";
		Query query = session.createQuery(hql);
		if (rows > 0) {
			query.setMaxResults(rows);
		}
		List<RejectReasonEntity> lists = query.list();
		return lists;
	}
}
