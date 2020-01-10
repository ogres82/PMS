package com.jdry.pms.assignWork.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assignWork.pojo.DispatchPerson;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;

/**
 * 报障报修物管端持久层
 * 
 * @author hezuping
 *
 */
@Repository
@Transactional
public class ImpairedRepairDao extends HibernateDao {

	/**
	 * 描述：获取历史表单
	 * 
	 * @param handleId
	 * @return
	 */
	public List getProptyImpairRepHistory(String handleId) {
		String sql = "select DISTINCT(DD.rpt_id),DD.createTime,DD.event_content,DD.address,DD.dispatch_status,dispatch_handle_id,DD.state from("
				+ " select CC.*,t.state from("
				+ " select t.rpt_id,t.createTime,t.event_content,t.address,di.dispatch_status,di.dispatch_handle_id from t_r_maintain t , t_r_maintain_dispatch di"
				+ "  where t.rpt_id=di.mtn_id and di.dispatch_handle_id='" + handleId + "' ORDER BY t.createTime desc"
				+ " ) CC LEFT JOIN t_charge_info t on CC.rpt_id=t.work_id" + " ) DD";

		/*
		 * String
		 * sql="select t.rpt_id,t.createTime,t.event_content,t.address,di.dispatch_status,di.dispatch_handle_id from t_r_maintain t , t_r_maintain_dispatch di"
		 * + " where t.rpt_id=di.mtn_id and di.dispatch_handle_id='"
		 * +handleId+"' ORDER BY t.createTime desc";
		 */
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.close();
		return list;

	}

	/**
	 * 获取报修单
	 * 
	 * @param rpt_id
	 * @return
	 */
	public WorkMainEntity getProptyImpairRepDetailByEventId(String rpt_id) {
		if (rpt_id != null && (!rpt_id.equals(""))) {
			/*
			 * String sql="select CC.*,t.state from (" +
			 * " SELECT bb.* FROM(SELECT t.createTime,t.event_time,	t.rpt_name,	t.in_call,t.address,FORMAT(di.dispatch_expense, 2) AS sum_price, 	FORMAT(di.labor_cost, 2) as labor_cost, FORMAT(di.material_cost, 2) as material_cost,t.rpt_id, t.event_content, t.event_state, t.img_url,di.dispatch_evaluate,di.dispatch_visit_lev,di.dispatch_visit_record,di.dispatch_visit_time, di.dispatch_result"
			 * +
			 * "  FROM t_r_maintain t LEFT JOIN t_r_maintain_dispatch di ON t.rpt_id = di.mtn_id) bb"
			 * + "  WHERE bb.rpt_id = '"
			 * +rpt_id+"') CC LEFT JOIN t_charge_info t on CC.rpt_id=t.work_id";
			 */
			String sql = "SELECT bb.* FROM(SELECT t.createTime,t.event_time,	t.rpt_name,	t.in_call,t.address,FORMAT(di.dispatch_expense, 2) AS sum_price, 	FORMAT(di.labor_cost, 2) as labor_cost, FORMAT(di.material_cost, 2) as material_cost,t.rpt_id, t.event_content, t.event_state, t.img_url,di.dispatch_evaluate,di.dispatch_visit_lev,di.dispatch_visit_record,di.dispatch_visit_time, di.dispatch_result,di.finish_img_url,di.dispatch_tools "
					+ " FROM t_r_maintain t LEFT JOIN t_r_maintain_dispatch di ON t.rpt_id = di.mtn_id) bb"
					+ " WHERE	bb.rpt_id = '" + rpt_id + "'";
			Session session = this.getSessionFactory().openSession();
			Object[] obj = (Object[]) session.createSQLQuery(sql).uniqueResult();
			session.close();
			WorkMainEntity w = new WorkMainEntity();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				if (obj[0] != null) {
					w.setCreateTime(sd.parse(obj[0].toString()));
				}
				if (obj[1] != null) {
					w.setEvent_time(sd.parse(obj[1].toString()));
				}

			} catch (ParseException e) {
			}
			w.setRpt_name(obj[2].toString());
			if (obj[3] != null) {
				w.setIn_call(obj[3].toString());
			} else {
				w.setIn_call("");
			}

			if (obj[4] != null) {
				w.setAddress(obj[4].toString());
			} else {
				w.setAddress("");
			}

			if (obj[5] != null) {
				w.setSum_price(obj[5].toString());
			} else {
				w.setSum_price("0.00");
			}

			if (obj[6] != null) {
				w.setLabor_cost(obj[6].toString());
			} else {
				w.setLabor_cost("0.00");
			}
			if (obj[7] != null) {
				w.setMaterial_cost(obj[7].toString());
			} else {
				w.setMaterial_cost("0.00");
			}
			if (obj[8] != null) {
				w.setRpt_id(obj[8].toString());
			} else {
				w.setRpt_id("");
			}
			String content = null == obj[9] ? "" : obj[9].toString();
			w.setEvent_content(content);

			if (obj[10] != null) {
				w.setEvent_state(obj[10].toString());
			} else {
				w.setEvent_state("");
			}
			if (obj[11] != null) {
				w.setImg_url(obj[11].toString());
			} else {
				w.setImg_url("");
			}
			if (obj[12] != null) {
				w.setDispatch_evaluate(obj[12].toString());
			} else {
				w.setDispatch_evaluate("");
			}
			if (obj[13] != null) {
				w.setDispatch_visit_lev(obj[13].toString());
			} else {
				w.setDispatch_visit_lev("");
			}
			if (obj[14] != null) {
				w.setDispatch_visit_record(obj[14].toString());
			} else {
				w.setDispatch_visit_record("");
			}
			if (obj[15] != null) {
				String dispatch_visit_time = "";

				dispatch_visit_time = sd.format(obj[15]);

				// String dispatch_visit_time= work.getDispatch_visit_time();

				w.setDispatch_visit_time(dispatch_visit_time);
			} else {
				w.setDispatch_visit_time("");
			}

			if (obj[16] != null) {
				String reuslt = null == obj[16] ? "" : obj[16].toString();
				w.setDispatch_result(reuslt);
			}
			if (obj[17] != null) {
				String finishImgUrl = null == obj[17] ? "" : obj[17].toString();
				w.setFinish_img_url(finishImgUrl);
			}
			if (obj[18] != null) {
				String dispatch_tools = null == obj[18] ? "" : obj[18].toString();
				w.setDispatch_tools(dispatch_tools);
			}
			return w;
		} else {
			return null;

		}

	}

	@SuppressWarnings("rawtypes")
	public List getDispatchStepByEventId(String rpt_id) {
		String sql = "select * from(SELECT t.handle_time,u.CNAME_ AS cname,t.handler_dept,t.handler_phone,	t.`status`,t.event_id,	t.mtn_type,t.handlers, u.ADDRESS_"
				+ " FROM t_r_eventsend t LEFT JOIN bdf2_user u on t.handlers = u.USERNAME_ ORDER BY t.`status` DESC"
				+ " ) cc where cc.event_id = '" + rpt_id + "'";

		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.close();
		return list;
	}

	/**
	 * 获取所有未派工工单
	 * 
	 * @param rpt_id
	 * @return
	 */
	public List<?> queryAllNoDispatch() {
		String hql = "select t.rpt_id,t.createTime,t.event_content,t.address,di.dispatch_status,di.dispatch_handle_id from t_r_maintain t , t_r_maintain_dispatch di"
				+ " where t.rpt_id=di.mtn_id and di.dispatch_status='0'  ORDER BY t.createTime desc";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}

	/**
	 * 客户端接单操作 接单后，工单推进流程至已派工
	 * 
	 * @throws Exception
	 */
	public String dipatchByClient(Map<Object, Object> parameter) throws Exception {
		String rptId = parameter.get("rptId").toString();
		WorkMainDispatchEntity dispatch = (WorkMainDispatchEntity) parameter.get("dispatch");
		EventSendEntity send = (EventSendEntity) parameter.get("send");
		DispatchPerson person = (DispatchPerson) parameter.get("person");
		String hql = "update WorkMainEntity t set t.event_state='1' where t.rpt_id='" + rptId + "'";
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		try {
			query.executeUpdate();
			session.saveOrUpdate(dispatch);
			session.save(send);
			session.save(person);
			// session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception("抢单失败！请稍后再试。");
		} finally {
			session.flush();
			session.close();
		}
		return "";
	}

	public boolean varifyDispatch(String rptId) {
		String sql = "select t.event_state from t_r_maintain t where t.rpt_id='" + rptId + "'";
		Session session = this.getSessionFactory().openSession();
		String state = session.createSQLQuery(sql).uniqueResult().toString();
		session.close();
		if (state.equals("0"))
			return true;
		return false;
	}

}
