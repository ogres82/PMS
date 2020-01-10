package com.jdry.pms.houseWork.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;

@Repository
@Transactional
public class HouseworkEventVisitDao extends HibernateDao {

	/**
	 * 查询回访及其待归档的数据
	 * 
	 * @param page
	 * @param parameter
	 * @param object
	 * @return
	 */
	public List queryHouseWorkEventVisitInfo(Page page, Map<String, Object> parameter, Object object) {
		Session session = this.getSessionFactory().openSession();
		String sql = "select t.* from( select t.*,chg.state,u.CNAME_ from v_t_housework_eventsend t  LEFT JOIN t_charge_info chg  on t.event_no=chg.work_id" + " LEFT JOIN bdf2_user u on t.verify_oper_id=u.USERNAME_ ) t where  t.event_state in(3,4) and (t.other='物管拒单' or t.state=02)";
		// String sql="select t.* from v_t_housework_eventsend t where 1=1 and t.event_state in(3,4)";
		StringBuffer sqlStr = new StringBuffer();

		if (parameter != null) {// 模糊查询
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();

			String businessFromSearch = parameter.get("businessFromSearch") == null ? "" : parameter.get("businessFromSearch").toString();
			String CommNameSearch = parameter.get("CommNameSearch") == null ? "" : parameter.get("CommNameSearch").toString();
			String dateSearch = parameter.get("dateSearch") == null ? "" : parameter.get("dateSearch").toString();
			
			if (!search.equals("")) {
				sql += " and t.event_no like" + "'%" + search + "%'" + " or t.rpt_name like" + "'%" + search + "%'" + " or t.call_phone like" + "'%" + search + "%'" + " or t.user_address like" + "'%" + search + "%'" + " or t.event_source_name like" + "'%" + search + "%'";
			}
			if (!businessFromSearch.equals("")) {
				sql += " and t.event_from ='" + businessFromSearch + "'";
			}
			if (!CommNameSearch.equals("")) {
				sql += " and t.user_address like'%" + CommNameSearch + "%'";
			}
			if (!dateSearch.equals("")) {
				sql += " and t.accept_time like'%" + dateSearch + "%'";
			}
		}
		sqlStr.append(sql + " ORDER BY t.accept_time desc");
		int shownum = page.getLastEntityIndex() - page.getFirstEntityIndex();
		sqlStr.append(" LIMIT " + page.getFirstEntityIndex() + ", " + shownum);
		page.setEntityCount(getCount(sql));
		List result = session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		return result;
	}

	/**
	 * 分页统计
	 * 
	 * @param sql
	 * @return
	 */
	public int getCount(String sql) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(*) from(");
		sqlStr.append(sql);
		sqlStr.append(") aa");
		int count = Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sqlStr.toString()).uniqueResult() + "");
		return count;
	}

	/**
	 * 保存回访信息
	 * 
	 * @param vi
	 * @return
	 */
	public boolean saveEventVisit(HouseworkVisitEntity vi) {
		Session session = this.getSessionFactory().openSession();
		try {
			session.saveOrUpdate(vi);
			session.flush();
			session.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public HouseworkVisitEntity queryVisitInfo(String event_no) {
		String hql = " select t.* from t_housework_visit t where t.event_id in (select id from t_housework_event where event_no='" + event_no + "')";
		List<HouseworkVisitEntity> ls = this.getSessionFactory().getCurrentSession().createSQLQuery(hql).addEntity("t", HouseworkVisitEntity.class).list();
		HouseworkVisitEntity we = null;
		if (ls.size() > 0) {
			we = ls.get(0);

		}
		return we;

	}
}
