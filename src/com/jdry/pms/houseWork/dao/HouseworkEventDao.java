package com.jdry.pms.houseWork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.dao.AssignWorkDispatchDao;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.pojo.VHouseWorkEventSendEnitiy;

/**
 * 家政持久层
 * 
 * @author hezuping
 *
 */
@Repository
@Transactional
public class HouseworkEventDao extends HibernateDao {
	static Logger log = Logger.getLogger(AssignWorkDispatchDao.class);

	public boolean saveHouseWorkEvent(HouseworkEventEntity work) {
		Session session = this.getSessionFactory().openSession();
		try {
			session.saveOrUpdate(work);
			log.info("客服家政申请：" + work.getEvent_no());
			session.flush();
			session.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 查询临时保存数据
	 * 
	 * @param page
	 * @param parameter
	 * @param object
	 * @return
	 */
	public List queryHouseWorkEventInfo(Page page, Map<String, Object> parameter, Object object) {
		Session session = this.getSessionFactory().openSession();
		StringBuffer sqlStr = new StringBuffer();
		String sql = "select * from v_t_housework t where 1=1";
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
		sqlStr.append(sql);
		int shownum = page.getLastEntityIndex() - page.getFirstEntityIndex();
		sqlStr.append(" LIMIT " + page.getFirstEntityIndex() + ", " + shownum);
		page.setEntityCount(getCount(sql));
		List result = session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		return result;
	}

	// 统计条数
	public int getCount(String sql) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(*) from(");
		sqlStr.append(sql);
		sqlStr.append(") aa");
		int count = Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sqlStr.toString()).uniqueResult() + "");
		return count;
	}

	public String findEventNOByNo(String event_no) {
		String sql = "select t.event_no from t_housework_event t where t.event_no='" + event_no + "'";
		Session session = this.getSessionFactory().openSession();
		Object obj = session.createSQLQuery(sql).uniqueResult();
		session.close();
		if (obj == null) {
			return "noValue";
		}
		return obj.toString();

	}

	public HouseworkEventEntity queryHouseWorkEventInfoByNo(String event_no) {
		String sql = "select t.*  from t_housework_event t where t.event_no='" + event_no + "'";
		Session session = this.getSessionFactory().openSession();
		HouseworkEventEntity work = (HouseworkEventEntity) session.createSQLQuery(sql.toString()).addEntity("t", HouseworkEventEntity.class).uniqueResult();
		session.close();
		return work;
	}

	// 存储派工信息
	public boolean saveHouseWorkSend(HouseWorkEventSendEntity send) {
		Session session = this.getSessionFactory().openSession();
		try {
			session.saveOrUpdate(send);
			session.flush();
			session.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void deleteHouseWorkInfo(String houseWorkId) throws SQLException {
		String deleteHouseWorkEvent = "delete  from t_housework_event  where  id=?";
		String deleteHouseWorkDispatch = "delete  from t_housework_dispatch where  event_id=?";
		Session session = this.getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement ps = conn.prepareStatement(deleteHouseWorkEvent);
		ps.setString(1, houseWorkId);
		session.beginTransaction();
		conn.setAutoCommit(false);
		ps.executeUpdate();
		conn.commit();
		conn.close();
		ps.close();
		log.info("客服删除家政事件单：" + houseWorkId);

		Connection conn2 = session.connection();
		PreparedStatement ps2 = conn2.prepareStatement(deleteHouseWorkDispatch);
		ps2.setString(1, houseWorkId);
		session.beginTransaction();
		conn2.setAutoCommit(false);
		ps2.executeUpdate();
		conn2.commit();
		conn2.close();
		ps2.close();
		log.info("客服删除家政派工单：" + houseWorkId);

	}

	public VHouseWorkEventSendEnitiy queryhouseWorkInfoByNo(String event_no) {
		String hql = "from " + VHouseWorkEventSendEnitiy.class.getName() + " vh where vh.event_no='" + event_no + "'";
		List<VHouseWorkEventSendEnitiy> vh = this.query(hql);
		if (null != vh && vh.size() > 0) {
			return vh.get(0);
		} else {
			return null;
		}
	}
}
