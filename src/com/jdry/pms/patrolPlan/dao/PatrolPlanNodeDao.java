package com.jdry.pms.patrolPlan.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanNodeEntity;

@Repository
@Transactional
public class PatrolPlanNodeDao extends HibernateDao {

	public List<PatrolPlanNodeEntity> queryPatrolPlanNode(long patrolPlanId) {

			String hql = " from PatrolPlanNodeEntity where 1=1 and patrolPlanId='" + patrolPlanId + "'";
			List<PatrolPlanNodeEntity> lists = this.query(hql);
			if(lists.size()>0){
				return lists;
			}else {
				return null;
			}	
	}

	public void savePatrolPlanNode(PatrolPlanNodeEntity ppne) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(ppne);
		session.flush();
		session.close();
	}

	public PatrolPlanNodeEntity getPatrolPlanNodeById(String id) {
		String hql = " from PatrolPlanNodeEntity where 1=1 ";
		if (id.length() > 0) {
			hql += " and id='" + id + "'";
			List<PatrolPlanNodeEntity> lists = this.query(hql);
			if (lists.size() > 0) {
				return lists.get(0);
			}
		}
		return null;
	}

	public void delPlanNodeById(String id) {
		Session session = this.getSessionFactory().openSession();
		String ids[] = id.split(",");
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			PatrolPlanNodeEntity ppne = getPatrolPlanNodeById(ids[i]);
			if (ppne != null) {
				session.delete(ppne);
			}
		}
		session.flush();
		session.close();
	}

	public void delPlanNodeByPlanId(long patrolPlanId) {
		List<PatrolPlanNodeEntity> list = this.queryPatrolPlanNode(patrolPlanId);
		Session session = this.getSessionFactory().openSession();

		if (list == null) {
			return;
		} else {
			int len = list.size();
			for (int i = 0; i < len; i++) {
				session.delete(list.get(i));
			}
		}
		session.flush();
		session.close();
	}
}
