package com.jdry.pms.assignWork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.assignWork.pojo.DispatchImageEntity;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkMainEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.pojo.WorkMainWorkerStateEntity;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.dir.service.DirectoryService;

@Transactional
@Repository
public class AssignWorkDao extends HibernateDao {

	@Resource
	DirectoryService directoryService;

	/**
	 * @throws Exception 查询所有
	 * 
	 * @Title: getAll @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 * 设定文件 @return List<WorkMainEntity> 返回类型 @author hyq 2906472@qq.com @date
	 * 2016-3-23 下午10:16:27 @throws
	 */
	public void getAll(Page<VWorkMainEntity> page, Map<String, Object> parameter, Criteria criteria) throws Exception {
		String sqlSt = "";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VWorkMainEntity.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + VWorkMainEntity.class.getName()
				+ " work where 1=1 and ( event_state='0' or event_state is null)";
		String sqlCount = "select count(*) from " + VWorkMainEntity.class.getName()
				+ " work where 1=1 and ( event_state='0' or event_state is null) ";
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
			String businessFromSearch = parameter.get("businessFromSearch") == null ? ""
					: parameter.get("businessFromSearch").toString();
			String eventTypeSearch = parameter.get("eventTypeSearch") == null ? ""
					: parameter.get("eventTypeSearch").toString();
			if (!search.equals("")) {
				sql += " and work.rpt_id like" + "'%" + search + "%'" + " or work.rpt_name like" + "'%" + search + "%'"
						+ " or work.event_source_name like" + "'%" + search + "%'" + " or work.event_type_name like"
						+ "'%" + search + "%'" + " or work.addres like" + "'%" + search + "%'"
						+ " or work.createby_name like" + "'%" + search + "%'";
			}
			if (!businessFromSearch.equals("")) {
				sql += " and work.event_source = '" + businessFromSearch + "'";
			}
			if (!eventTypeSearch.equals("")) {
				sql += " and work.event_type = '" + eventTypeSearch + "'";
			}

		}
		return sql;

	}

	public WorkMainEntity getWorkMainById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rpt_id", id);

		List<WorkMainEntity> lists = this
				.query("from " + WorkMainEntity.class.getName() + " where rpt_id='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 保存数据
	 * 
	 * @Title: addWorkMain @Description: TODO(这里用一句话描述这个方法的作用) @param @param
	 * mainEntity 设定文件 @return void 返回类型 @author hyq 2906472@qq.com @date
	 * 2016-3-25 上午11:15:13 @throws
	 */
	public void addWorkMain(WorkMainEntity mainEntity) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(mainEntity);
		session.flush();
		session.close();
	}

	/**
	 * 删除主表
	 * 
	 * @Title: deleteWorkMain @Description: TODO(这里用一句话描述这个方法的作用) @param @param
	 * id 设定文件 @return void 返回类型 @author hyq 2906472@qq.com @date 2016-4-1
	 * 下午12:46:27 @throws
	 */
	public void deleteWorkMain(String id) {
		Session session = this.getSessionFactory().openSession();
		WorkMainEntity mainEntity = getWorkMainById(id);
		if (null != mainEntity) {
			session.delete(mainEntity);
		}
		session.flush();
		session.close();
	}

	/**
	 * 
	 * @Title: @Description: 获取报修派工单处理人信息 @param @param id 设定文件 @return List
	 * 返回类型 @author @date @throws
	 */
	public List getHandleWorkers(String keyword, String type) {
		String sql = "";
		if (type.equals("1")) {
			// add huanglin 20170605 物业需求变更需要对报障工单可以对环境部进行派单 所以添加 环境部id
			sql = "select * from v_user_dept_pos where CNAME_ like '%" + keyword + "%' and DEPT_ID in ('006','007')";
		} else if (type.equals("2")) {
			sql = "select * from v_user_dept_pos where CNAME_ like '%" + keyword + "%' and DEPT_ID='003'";
		} else {
			sql = "select * from v_user_dept_pos where DEPT_NAME like '%" + keyword + "%'";
		}
		sql += " order by DEPT_ID,POSITION_ID,CNAME_";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("USERNAME_", StandardBasicTypes.STRING)
				.addScalar("CNAME_", StandardBasicTypes.STRING).addScalar("DEPT_ID", StandardBasicTypes.STRING)
				.addScalar("DEPT_NAME", StandardBasicTypes.STRING).addScalar("POSITION_ID", StandardBasicTypes.STRING)
				.addScalar("POSITION", StandardBasicTypes.STRING).addScalar("work_state", StandardBasicTypes.STRING)
				.addScalar("work_times", StandardBasicTypes.STRING).list();
		session.flush();
		session.close();
		return result;
	}

	/**
	 * 保存工单条数
	 * 
	 * @Title: addWorkWorkState @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param @param mainWorkerStateEntity 设定文件 @return
	 * void 返回类型 @author hyq 2906472@qq.com @date 2016-4-8 上午9:06:02 @throws
	 */
	public void addWorkWorkState(WorkMainWorkerStateEntity mainWorkerStateEntity) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(mainWorkerStateEntity);
		session.flush();
		session.close();
	}

	/**
	 * 得到当派工统计值
	 * 
	 * @Title: getWorkMainByUser @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param @param user @param @return 设定文件 @return
	 * WorkMainWorkerStateEntity 返回类型 @author hyq 2906472@qq.com @date 2016-4-8
	 * 上午9:08:52 @throws
	 */
	public WorkMainWorkerStateEntity geWorkWorkStateByUser(String user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rpt_id", user);

		List<WorkMainWorkerStateEntity> lists = this
				.query("from " + WorkMainWorkerStateEntity.class.getName() + " where user_name='" + user + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 报障页面显示状态
	 * 
	 * @param mtn_id
	 * @return
	 */
	public String getWorkMainState(String mtn_id) {
		String workState = "";
		Session session = this.getSessionFactory().openSession();
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" select {t.*}, {d.*}, {c.*} ");
		bufferSql.append(
				" from t_r_maintain t left join t_r_maintain_dispatch d on t.rpt_id=d.mtn_id left join t_r_maintain_complaint c on t.rpt_id=c.mtn_id");
		bufferSql.append(" where t.rpt_id='" + mtn_id + "'");
		List list = null;
		list = session.createSQLQuery(bufferSql.toString()).addEntity("t", WorkMainEntity.class)
				.addEntity("d", WorkMainDispatchEntity.class).addEntity("c", WorkComplaintEntity.class).list();
		if (null == list) {
			list = new ArrayList();
		}
		WorkComplaintEntity complaintEntity = null;
		WorkMainDispatchEntity dispatchEntity = null;

		Object[] objs = (Object[]) list.get(0);
		if (null != objs && objs.length > 2) {
			dispatchEntity = (WorkMainDispatchEntity) objs[1];
			complaintEntity = (WorkComplaintEntity) objs[2];
		}

		if (null == dispatchEntity) {
			dispatchEntity = new WorkMainDispatchEntity();
		}
		if (null == complaintEntity) {
			complaintEntity = new WorkComplaintEntity();
		}
		workState = dispatchEntity.getDispatch_status();
		if (null == workState || workState.equals("")) {
			workState = complaintEntity.getComp_status();
		}
		workState = directoryService.getDetailName("main_mtn_dispatch_status", workState);
		// Object obj = (Object) objs[0];
		session.flush();
		session.close();
		return workState;
	}

	public void addEventSend(EventSendEntity send) {

		String sql = "select count(1) as num  from t_r_eventsend where  status='" + send.getStatus()
				+ "' and event_id='" + send.getEvent_id() + "'";
		Session session2 = this.getSessionFactory().openSession();
		Object result = session2.createSQLQuery(sql).uniqueResult();
		if (result != null) {
			int num = Integer.parseInt(result.toString());
			if (num <= 0) {
				Session session = this.getSessionFactory().openSession();
				session.saveOrUpdate(send);
				session.flush();
				session.close();
			}
		}
		session2.clear();
		session2.close();
	}

	public String getDept(String createby) {
		String sql = "select t.DEPT_NAME from v_user_dept_pos t where t.USERNAME_='" + createby + "'";
		Session session = this.getSessionFactory().openSession();
		Object result = session.createSQLQuery(sql).uniqueResult();
		session.clear();
		session.close();
		if (result == null) {

			return "";
		} else
			return result.toString();
	}

	public String getHandlePhone(String dispatch_handle_id) {
		String sql = "select t.MOBILE_ from  bdf2_user t where t.USERNAME_='" + dispatch_handle_id + "'";
		Session session = this.getSessionFactory().openSession();
		Object result = session.createSQLQuery(sql).uniqueResult();
		String res = (String) result;
		String temp = null == res ? "" : res;
		session.clear();
		session.close();
		return temp;
	}

	public void updateWorkMainState(String mtn_id, String state) throws SQLException {
		String sql = "update t_r_maintain t set t.event_state=? where t.rpt_id=?";
		Session session = this.getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, state);
		ps.setString(2, mtn_id);
		session.beginTransaction();
		conn.setAutoCommit(false);
		ps.executeUpdate();
		conn.commit();
		conn.close();
		ps.close();
		session.close();
	}

	public void saveImg(DispatchImageEntity img) {
		Session session = this.getSessionFactory().openSession();
		try {
			session.saveOrUpdate(img);
		} finally {
			session.flush();
			session.close();
		}

	}

	public List<DispatchImageEntity> getEventImgByRptId(String rpt_id) {
		String hql = " from " + DispatchImageEntity.class.getName() + " t where t.event_id='" + rpt_id + "'";
		List<DispatchImageEntity> list = this.query(hql);
		return list;
	}

	public void updateWorkMainHanders(String rpt_id, String handerNames) throws SQLException {
		String sql = "update t_r_maintain t set t.hander_names=? where t.rpt_id=?";
		Session session = this.getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, handerNames);
		ps.setString(2, rpt_id);
		session.beginTransaction();
		conn.setAutoCommit(false);
		ps.executeUpdate();
		conn.commit();
		conn.close();
		ps.close();
		session.close();
	}
	
	public void updateDispatchWorkMainHanders(String rpt_id, String handerName,String handlerId) throws SQLException {
		String sql = "update t_r_maintain_dispatch t set t.dispatch_handle_id=?,t.dispatch_handle_name=? where t.mtn_id=?";
		Session session = this.getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, handlerId);
		ps.setString(2, handerName);
		ps.setString(3, rpt_id);
		session.beginTransaction();
		conn.setAutoCommit(false);
		ps.executeUpdate();
		conn.commit();
		conn.close();
		ps.close();
		session.close();
	}

	public PropertyOwner getCustName(String cust_id) {
		String hql = " from PropertyOwner t where t.ownerId='" + cust_id + "'";
		List<PropertyOwner> list = this.query(hql);
		PropertyOwner po = null;
		if (list.size() > 0) {
			po = list.get(0);
		}
		return po;

	}
}
