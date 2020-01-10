package com.jdry.pms.houseWork.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;

@Repository
@Transactional
public class EventSearchDao extends HibernateDao {

	public List queryHouseWorkEvent(Page page, Map<String, Object> parameter, Object object) {
		Session session = this.getSessionFactory().openSession();
		String sql = "select * from (SELECT t.*, vi.visit_content,vi.visit_evaluate,vi.visit_from,vi.visit_record,vi.visit_time,vi.visist_levcontent,u.CNAME_" + " FROM v_t_eventsearch t LEFT JOIN t_housework_visit vi ON t.id = vi.event_id LEFT JOIN bdf2_user u on t.verify_oper_id=u.USERNAME_ ) Vis where 1=1";
		StringBuffer sqlStr = new StringBuffer();

		if (parameter != null) {// 模糊查询
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			String businessFromSearch = parameter.get("businessFromSearch") == null ? "" : parameter.get("businessFromSearch").toString();
			String CommNameSearch = parameter.get("CommNameSearch") == null ? "" : parameter.get("CommNameSearch").toString();
			String dateSearch = parameter.get("dateSearch") == null ? "" : parameter.get("dateSearch").toString();
			String eventStatusSearch = parameter.get("eventStatusSearch") == null ? "" : parameter.get("eventStatusSearch").toString();
			if (!search.equals("")) {
				sql += " and Vis.event_no like" + "'%" + search + "%'" + " or Vis.rpt_name like" + "'%" + search + "%'" + " or Vis.call_phone like" + "'%" + search + "%'" + " or Vis.user_address like" + "'%" + search + "%'" + " or Vis.send_no like" + "'%" + search + "%'" + " or Vis.event_title like" + "'%" + search + "%'" + " or Vis.event_source_name like" + "'%" + search + "%'";
			}
			if (!businessFromSearch.equals("")) {
				sql += " and Vis.event_from ='" + businessFromSearch + "'";
			}
			if (!CommNameSearch.equals("")) {
				sql += " and Vis.user_address like'%" + CommNameSearch + "%'";
			}
			if (!dateSearch.equals("")) {
				sql += " and Vis.createTime like'%" + dateSearch + "%'";
			}
			if (!eventStatusSearch.equals("")) {
				sql += " and Vis.event_state = '" + eventStatusSearch + "'";
			}
		}
		sqlStr.append(sql);
		sqlStr.append(" order by Vis.accept_time desc");
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
}
