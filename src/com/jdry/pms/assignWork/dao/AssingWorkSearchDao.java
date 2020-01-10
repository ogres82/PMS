package com.jdry.pms.assignWork.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.assignWork.pojo.VWorkMainEntity;

/**
 * 综合查询
 * 
 * @author hezuping 2016年7月13日14:57:03
 */
@Repository
public class AssingWorkSearchDao extends HibernateDao {
	/**
	 * 综合查询
	 * 
	 * @param page
	 * @param parameter
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void assignWorkSearch(Page page, Map<String, Object> parameter, Object object, Criteria criteria) throws Exception {
		String sqlSt = "";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VWorkMainEntity.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + VWorkMainEntity.class.getName() + " work where 1=1";
		String sqlCount = "select count(*) from " + VWorkMainEntity.class.getName() + " work where 1=1  ";
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer sqlCountStr = new StringBuffer();
		if (parameter != null) {
			sqlSt = sqlParmeter(parameter);
		}

		sqlStr.append(sql);
		sqlStr.append(sqlSt);
		sqlStr.append("  order by createTime desc");

		sqlCountStr.append(sqlCount);
		sqlCountStr.append(sqlSt);

		if (criteria != null) {
			HibernateUtils.createFilter(detachedCriteria, criteria);
		}
		this.pagingQuery(page, sqlStr.toString(), sqlCountStr.toString(), map);

	}

	public String sqlParmeter(Map<String, Object> parameter) {

		String sql = "";
		if (parameter != null) {// 模糊查询
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			String businessFromSearch = parameter.get("businessFromSearch") == null ? "" : parameter.get("businessFromSearch").toString();
			String eventTypeSearch = parameter.get("eventTypeSearch") == null ? "" : parameter.get("eventTypeSearch").toString();
			String CommNameSearch = parameter.get("CommNameSearch") == null ? "" : parameter.get("CommNameSearch").toString();
			String dateSearch = parameter.get("dateSearch") == null ? "" : parameter.get("dateSearch").toString();
			String eventStatusSearch = parameter.get("eventStatusSearch") == null ? "" : parameter.get("eventStatusSearch").toString();
			if (!search.equals("")) {
				sql += " and work.rpt_id like" + "'%" + search + "%'" + " or work.rpt_name like" + "'%" + search + "%'" + " or work.event_source_name like" + "'%" + search + "%'" + " or work.event_type_name like" + "'%" + search + "%'" + " or work.addres like" + "'%" + search + "%'" + " or work.createby_name like" + "'%" + search + "%'" + " or work.event_state_name like" + "'%" + search + "%'";
			}			
			if (!businessFromSearch.equals("")) {
				sql += " and work.event_source ='" + businessFromSearch + "'";
			}
			if (!eventTypeSearch.equals("")) {
				sql += " and work.event_type ='" + eventTypeSearch + "'";
			}
			if (!CommNameSearch.equals("")) {
				sql += " and work.addres like'%" + CommNameSearch + "%'";
			}
			if (!dateSearch.equals("")) {
				sql += " and work.createTime like'%" + dateSearch + "%'";
			}
			if (!eventStatusSearch.equals("")) {
				sql += " and work.event_state like'%" + eventStatusSearch + "%'";
			}

		}
		return sql;

	}
}
