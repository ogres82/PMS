package com.jdry.pms.patrolPlan.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.patrolPlan.pojo.PatrolNodeEntity;

@Repository
@Transactional
public class PatrolNodeDao extends HibernateDao {

	public List<PatrolNodeEntity> queryPatrolNode(int rows, Map<String, Object> parameter) {

		String hql = "from PatrolNodeEntity where 1=1";
		Session session = this.getSessionFactory().openSession();

		hql += " order by createTime desc";
		Query query = session.createQuery(hql);

		if (rows > 0) {
			query.setMaxResults(rows);
		}
		List<PatrolNodeEntity> lists = query.list();
		if (null != lists && lists.size() > 0) {
			session.close();
			return lists;
		} else {
			session.close();
			return null;
		}
	}

	public void savePatrolNode(PatrolNodeEntity pne) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(pne);
		session.flush();
		session.close();
	}

	public void delPatrolNode(String patrolNodeIds) {
		String ids[] = patrolNodeIds.split(",");
		Session session = this.getSessionFactory().openSession();	
		int len = ids.length;
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				PatrolNodeEntity pne = getPatrolNodeEntitybyId(ids[i]);
				if (null != pne) {
					session.delete(pne);
				}
			}
		}
		session.flush();
		session.close();
	}

	public PatrolNodeEntity getPatrolNodeEntitybyId(String patrolNodeId) {
		PatrolNodeEntity pne;
		if ("".equals(patrolNodeId) && patrolNodeId == null) {
			pne = null;
		} else {
			String hql = "from PatrolNodeEntity where 1=1";
			hql += " and patrolNodeId = '" + patrolNodeId + "'";
			pne = (PatrolNodeEntity) this.query(hql).get(0);
		}
		return pne;
	}
}
