package com.jdry.pms.patrolPlan.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanEntity;

@Repository
@Transactional
public class PatrolPlanDao extends HibernateDao {
	@Resource
	private PatrolPlanNodeDao dao;

	public List<PatrolPlanEntity> queryPatrolPlan() {
		String hql = " from PatrolPlanEntity where 1=1 ";
		List<PatrolPlanEntity> lists = this.query(hql);
		return lists;
	}

	public void savePatrolPlan(PatrolPlanEntity ppe) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(ppe);
		session.flush();
		session.close();
	}

	public PatrolPlanEntity getPatrolPlanById(String patrolPlanId) {
		String hql = " from PatrolPlanEntity where 1=1 ";
		if (patrolPlanId.length() > 0) {
			hql += " and patrolPlanId='" + patrolPlanId + "'";
			List<PatrolPlanEntity> lists = this.query(hql);
			if (lists.size() > 0) {
				return lists.get(0);
			}
		}
		return null;
	}

	public void delPatrolPlan(String patrolPlanIds) {
		Session session = this.getSessionFactory().openSession();
		String ids[] = patrolPlanIds.split(",");
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			PatrolPlanEntity ppe = this.getPatrolPlanById(ids[i]);
			if (ppe != null) {
				long PlanId = Long.parseLong(ids[i]);
				dao.delPlanNodeByPlanId(PlanId);
				session.delete(ppe);
			}
		}
		session.flush();
		session.close();
	}
}
