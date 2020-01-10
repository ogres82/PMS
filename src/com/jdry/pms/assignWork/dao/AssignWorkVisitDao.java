package com.jdry.pms.assignWork.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.assignWork.pojo.VWorkVisitEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;

@Repository
public class AssignWorkVisitDao extends HibernateDao {

	/**
	 * 得到所有待回访的派工单
	 * 
	 * @Title: getAll @Description: TODO(这里用一句话描述这个方法的作用) @param @param page @param @param parameter @param @param criteria @param @throws Exception 设定文件 @return void 返回类型 @author
	 * hyq 2906472@qq.com @date 2016-4-6 下午11:09:16 @throws
	 */
	public void getWorkVisit(Page<VWorkVisitEntity> page, Map<String, Object> parameter, Criteria criteria) throws Exception {
		String sqlSt = "";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VWorkVisitEntity.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + VWorkVisitEntity.class.getName() + " work where 1=1 and dispatch_status='3' and( state='02' or dispatch_result in('拒绝维修') or rpt_id like '%TS%') ";
		String sqlCount = "select count(*) from " + VWorkVisitEntity.class.getName() + " work where 1=1 and dispatch_status='3' and( state='02' or dispatch_result in('拒绝维修') or rpt_id like '%TS%') ";
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

	/**
	 * 查询条件模糊查询
	 * 
	 * @param parameter
	 * @return
	 */
	public String sqlParmeter(Map<String, Object> parameter) {

		String sql = "";
		if (parameter != null) {// 模糊查询
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			String businessFromSearch = parameter.get("businessFromSearch") == null ? "" : parameter.get("businessFromSearch").toString();
			String eventTypeSearch = parameter.get("eventTypeSearch") == null ? "" : parameter.get("eventTypeSearch").toString();
			String CommNameSearch = parameter.get("CommNameSearch") == null ? "" : parameter.get("CommNameSearch").toString();
			String dateSearch = parameter.get("dateSearch") == null ? "" : parameter.get("dateSearch").toString();
			if (!search.equals("")) {
				sql += " and work.rpt_id like" + "'%" + search + "%'" + " or work.in_call like" + "'%" + search + "%'" + " or work.rpt_name like" + "'%" + search + "%'" + " or work.addres like" + "'%" + search + "%'" + " or work.event_type_name like" + "'%" + search + "%'" + " or work.event_source_name like" + "'%" + search + "%'" + " or work.dispatch_status_name like" + "'%" + search + "%'";
			}
			if (!businessFromSearch.equals("")) {
				sql += " and work.event_source ='"+businessFromSearch+"'";
			}
			if (!eventTypeSearch.equals("")) {
				sql += " and work.event_type ='"+eventTypeSearch+"'";
			}
			if (!CommNameSearch.equals("")) {
				sql += " and work.addres like'%"+CommNameSearch+"%'";
			}
			if (!dateSearch.equals("")) {
				sql += " and work.createTime like'%"+dateSearch+"%'";
			}

		}
		return sql;

	}

	/**
	 * 待回访的明细
	 * 
	 * @Title: getVisitWorkById @Description: TODO(这里用一句话描述这个方法的作用) @param @param rpt_id @param @return 设定文件 @return List 返回类型 @author hyq 2906472@qq.com @date 2016-4-7
	 * 下午7:30:54 @throws
	 */
	public List getVisitWorkById(String rpt_id) {
		Session session = this.getSessionFactory().openSession();
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" ");
		bufferSql.append(" select {t.*}, {d.*}, {c.*} ");
		bufferSql.append(" from t_r_maintain t left join t_r_maintain_dispatch d on t.rpt_id=d.mtn_id left join t_r_maintain_complaint c on t.rpt_id=c.mtn_id");
		bufferSql.append(" where t.rpt_id='" + rpt_id + "'");
		List list = null;
		list = session.createSQLQuery(bufferSql.toString()).addEntity("t", WorkMainEntity.class).addEntity("d", WorkMainDispatchEntity.class).addEntity("c", WorkComplaintEntity.class).list();
		if (null == list) {
			list = new ArrayList();
		}
		session.flush();
		session.close();
		return list;
	}
}
