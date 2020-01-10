package com.jdry.pms.patrolPlan.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.patrolPlan.pojo.PatrolNodeRecEntity;
import com.jdry.pms.patrolPlan.pojo.PlanNodeRecEntity;

@Repository
@Transactional
public class PatrolNodeRecDao extends HibernateDao {
	public List<PatrolNodeRecEntity> queryPatrolNodeRec(String patrolUserId) {
		String hql = " from  PatrolNodeRecEntity where 1=1";
		if (patrolUserId.length() > 0) {
			hql += " and patrolUserId = '" + patrolUserId + "'";
		}
		return this.query(hql);
	}

	public void queryNodeRec(Page<PlanNodeRecEntity> page, Map<String, Object> parameter, Criteria criteria) {

		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		String search = parameter.get("search") == null ? "" : parameter.get("search").toString();

		String planIdSearch = parameter.get("planIdSearch") == null ? "" : parameter.get("planIdSearch").toString();
		String nodeIdSearch = parameter.get("nodeIdSearch") == null ? "" : parameter.get("nodeIdSearch").toString();
		String userIdSearch = parameter.get("userIdSearch") == null ? "" : parameter.get("userIdSearch").toString();
		String insertDateSearch = parameter.get("insertDateSearch") == null ? "" : parameter.get("insertDateSearch").toString();

		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from " + PlanNodeRecEntity.class.getName() + " du where 1=1";
		String sqlCount = "select count(*) from " + PlanNodeRecEntity.class.getName() + " du where 1=1";
		if (!search.equals("")) {
			hql += " and du.planName like:planName" + " or du.patrolNodeName like:patrolNodeName" + " or du.patrolUserName like:patrolUserName"+ " or du.patrolContent like:patrolContent"+ " or du.insertTime like:insertTime";
			sqlCount +=  " and du.planName like:planName" + " or du.patrolNodeName like:patrolNodeName" + " or du.patrolUserName like:patrolUserName"+ " or du.patrolContent like:patrolContent"+ " or du.insertTime like:insertTime";
			map.put("planName", "%" + search + "%");
			map.put("patrolNodeName", "%" + search + "%");
			map.put("patrolUserName", "%" + search + "%");
			map.put("patrolContent", "%" + search + "%");
			map.put("insertTime", "%" + search + "%");
		}

		if (planIdSearch != null && !"".equals(planIdSearch)) {
			map.put("planIdSearch", Long.parseLong(planIdSearch) );
			hql += " and du.patrolPlanId =:planIdSearch";
			sqlCount += " and du.patrolPlanId =:planIdSearch";
		}
		if (nodeIdSearch != null && !"".equals(nodeIdSearch)) {
			map.put("nodeIdSearch", Long.parseLong(nodeIdSearch));
			hql += " and du.patrolNodeId =:nodeIdSearch";
			sqlCount += " and du.patrolNodeId =:nodeIdSearch";
		}
		if (userIdSearch != null && !"".equals(userIdSearch)) {
			map.put("userIdSearch", userIdSearch);
			hql += " and du.patrolUserId =:userIdSearch";
			sqlCount += " and du.patrolUserId =:userIdSearch";
		}
		if (insertDateSearch != null && !"".equals(insertDateSearch)) {
			map.put("insertDateSearch", insertDateSearch);
			hql += " and du.queryDate =:insertDateSearch";
			sqlCount += " and du.queryDate =:insertDateSearch";
		}

		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Map> queryPatrolNodeRec(String patrolUserId, String queryDate) {
		String hql = "Select new map(patrolRecId as id,patrolUserId as username,patrolNodeName as patrolNodeName,patrolUserName as cname,patrolContent as patrolContent,xAxis as xAxis,yAxis as yAxis,FROM_UNIXTIME(insertTime/1000) as time )"
				+ " From PatrolNodeRecEntity where 1=1 ";
		if (patrolUserId.length() > 0) {
			hql += " and patrolUserId = '" + patrolUserId + "'";
		}
		if (queryDate != null) {
			hql += " and FROM_UNIXTIME(insertTime/1000,'%Y-%m-%d') = '" + queryDate + "'";
		}
		hql =  hql+ " order by xAxis,yAxis,FROM_UNIXTIME(insertTime/1000) ";
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
        List<Map> list = query.list();
        session.flush();
        session.close();
		return list;
	}

	public void savePatrolNodeRec(PatrolNodeRecEntity pnre) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(pnre);
		session.flush();
		session.close();
	}

	public PatrolNodeRecEntity queryPatrolNodeRecEntityById(int patrolNodeId, String patrolRecId) {
		String hql = " from  PatrolNodeRecEntity where 1=1";
		if (patrolNodeId > 0) {
			hql += " and patrolNodeId = '" + patrolNodeId + "'";
		}
		if (patrolRecId.length() > 0) {
			hql += " and patrolRecId = '" + patrolRecId + "'";
		}
		return (PatrolNodeRecEntity) this.query(hql).get(0);
	}
}
