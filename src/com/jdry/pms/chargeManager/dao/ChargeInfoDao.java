package com.jdry.pms.chargeManager.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.comm.util.CommUser;

@Repository
public class ChargeInfoDao extends HibernateDao {
	static Logger log = Logger.getLogger(ChargeInfoDao.class);

	@Resource
	private HousePropertyDao houseDao;

	// 新增和修改
	public void saveAll(Collection<ChargeInfoEntity> chargeTypes) {
		Session session = this.getSessionFactory().openSession();
		for (ChargeInfoEntity chargeType : chargeTypes) {
			// if(chargeType.getCharge_type_id() == null || chargeType.getCharge_type_id().equals("")){
			// chargeType.setCharge_type_id(UUID.randomUUID().toString());
			// }
			// chargeType.setUpdate_date(new Date());
			// chargeType.setUpdate_emp_id(CommUser.getUserName());
			session.saveOrUpdate(chargeType);
		}

		session.flush();
		session.close();
	}

	// 新增(接口)
	public boolean addCharge(ChargeInfoEntity chargeInfo) {
		Session session = null;
		Transaction tx = null;
		try {
			session = this.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(chargeInfo);
			tx.commit();
			log.info("账单新增（接口侧）成功--" + chargeInfo.getWork_id());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.info("账单新增（接口侧）失败--" + chargeInfo.getWork_id());
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	public void delete(ChargeInfoEntity chargeType) {
		Session session = this.getSessionFactory().openSession();
		session.delete(chargeType);
		log.info("账单删除--" + chargeType.getCharge_id());
		session.flush();
		session.close();
	}

	public void paidInfoSave(Map paidInfo, List<ChargeInfoEntity> chargeInfo) {
		String paid_mode = paidInfo.get("paid_mode") == null ? "" : paidInfo.get("paid_mode").toString();
		String ticket_mode = paidInfo.get("ticket_mode") == null ? "" : paidInfo.get("ticket_mode").toString();

		String chargeInfoIds = "";

		Session session = this.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (ChargeInfoEntity charge : chargeInfo) {
				chargeInfoIds += charge.getCharge_id() + ",";
				String begin_time = sdf.format(charge.getBegin_time());
				String end_time = sdf.format(charge.getEnd_time());
				// String room_id = charge.getRoom_id();
				// VHouseProperty vhp = houseDao.getVHousePropertyById(room_id);
				String sqlUpdate = " update t_charge_info c set c.state='02', c.paid_mode='" + paid_mode + "', c.paid_amount='" + charge.getReceive_amount() + "', c.paid_date=SYSDATE() where c.charge_no='" + charge.getCharge_no() + "' ";
				String sqlInsert = "INSERT INTO t_charge_serial_info(serial_id,room_id,room_no,owner_id,owner_name, charge_type_no,charge_type_name, " + "charge_type,state,receive_amount,paid_amount,paid_date,paid_mode,oper_emp_id,update_date, begin_date, end_date, charge_info_id, ticket_mode) " + " values(UUID(),'" + charge.getRoom_id() + "','" + charge.getRoom_no() + "','" + charge.getOwner_id() + "','" + charge.getOwner_name() + "','" + charge.getCharge_type_no() + "','" + charge.getCharge_type_name() + "','01', '01'," + charge.getReceive_amount() + "," + charge.getReceive_amount() + ",SYSDATE(),'" + paid_mode + "','" + CommUser.getUserName() + "',SYSDATE(),date_format('" + begin_time + "','%Y-%m-%d'),date_format('" + end_time + "','%Y-%m-%d'),'" + charge.getCharge_id() + "','"
						+ ticket_mode + "','" + charge.getRoom_type() + "','" + charge.getStoried_build_name() + "','" + charge.getCommunity_name() + "')";
				System.out.println(sqlUpdate);
				System.out.println(sqlInsert);
				// 执行
				session.createSQLQuery(sqlUpdate).executeUpdate();
				session.createSQLQuery(sqlInsert).executeUpdate();
			}
			tx.commit();

			log.info("账单缴费成功--" + chargeInfoIds);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			log.info("账单缴费失败--" + chargeInfoIds);
		} finally {
			if (session.isOpen()) {
				session.close();

			}
		}
	}

	public List<ChargeInfoViewEntity> queryChargeBySerial(String serialId) {
		String hql = " select {c.*} from v_charge_info as c, t_charge_serial_detail t where t.charge_id=c.charge_id and t.charge_serial_id='" + serialId + "' ";

		Session session = this.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<ChargeInfoViewEntity> list = session.createSQLQuery(hql).addEntity("c", ChargeInfoViewEntity.class.getName()).list();
		session.flush();
		session.close();

		return list;

	}

	public void queryChargeByParam(Page<ChargeInfoViewEntity> page, Map<String, Object> parameter, String type) {
		String hql = " from " + ChargeInfoViewEntity.class.getName();
		Map<String, Object> map = new HashMap<String, Object>();

		String whereCase = " a ";
		String sqlCount = "select count(*) from " + ChargeInfoViewEntity.class.getName() + " b ";
		if (type != null && !type.isEmpty()) {
			whereCase += " where a.state =:type ";
			sqlCount += " where b.state =:type ";
			map.put("type", type);
		} else {
			whereCase += " where 1=1 ";
			sqlCount += " where 1=1 ";
		}

		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			String begin_time = parameter.get("begin_time") == null ? "" : parameter.get("begin_time").toString();
			String end_time = parameter.get("end_time") == null ? "" : parameter.get("end_time").toString();
			String charge_type_name_select = parameter.get("charge_type_name_select") == null ? "" : parameter.get("charge_type_name_select").toString();
			String paid_mode_select = parameter.get("paid_mode_select") == null ? "" : parameter.get("paid_mode_select").toString();
			String room_id = parameter.get("room_id") == null ? "" : parameter.get("room_id").toString();

			if (!room_id.equals("")) {
				whereCase += " and a.room_id =:room_id ";
				sqlCount += " and b.room_id =:room_id ";
				map.put("room_id", room_id);
			}

			if (!charge_type_name_select.equals("")) {
				whereCase += " and a.charge_type_no =:charge_type_no ";
				sqlCount += " and b.charge_type_no =:charge_type_no ";
				map.put("charge_type_no", charge_type_name_select);
			}

			if (!paid_mode_select.equals("")) {
				whereCase += " and a.paid_mode =:paid_mode ";
				sqlCount += " and b.paid_mode =:paid_mode ";
				map.put("paid_mode", paid_mode_select);
			}

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (!begin_time.equals("")) {
				Date beginTime = null;
				try {
					beginTime = formatter.parse(begin_time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				whereCase += " and a.end_time >=:beginTime ";
				sqlCount += " and b.end_time >=:beginTime ";

				map.put("beginTime", beginTime);
			}

			if (!end_time.equals("")) {
				Date endTime = null;
				try {
					endTime = formatter.parse(end_time);
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(endTime);
					calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.
					endTime = calendar.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				whereCase += " and a.end_time <:endTime ";
				sqlCount += " and b.end_time <:endTime ";

				map.put("endTime", endTime);
			}

			if (!search.equals("")) {
				whereCase += " and (a.owner_name like:owner_name " + " or a.room_no like:room_no" + " or a.charge_type_name like:charge_type_name" + " or a.oper_emp_id like:oper_emp_id)";
				sqlCount += " and (b.owner_name like:owner_name " + " or b.room_no like:room_no" + " or b.charge_type_name like:charge_type_name" + " or b.oper_emp_id like:oper_emp_id)";
				map.put("owner_name", "%" + search + "%");
				map.put("room_no", "%" + search + "%");
				map.put("charge_type_name", "%" + search + "%");
				map.put("oper_emp_id", "%" + search + "%");
			}

		}
		hql += whereCase;

		hql += " ORDER BY begin_time DESC";
		sqlCount += " ORDER BY begin_time DESC";

		System.out.println("hql===" + hql);
		// List<ChargeInfoEntity> list = this.query(hql);
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return list;

	}

	/**
	 * 根据房间ID来查询该房间的未收及欠费账单
	 * 
	 * @param roomId
	 * @return
	 */
	public List<ChargeInfoEntity> queryChargeByRoomId(String roomId) {
		// 条件查询
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from " + ChargeInfoEntity.class.getName();

		if (roomId != null && !roomId.isEmpty()) {
			hql += " where room_id=:roomId and state<>'02' ";
			map.put("roomId", roomId);
		} else {
			hql += " where 0=1 ";
		}

		List<ChargeInfoEntity> list = this.query(hql, map);

		return list;
	}

	public List<ChargeInfoEntity> queryChargeByWorkId(String WorkId) {
		// 条件查询
		Map<String, Object> map = new HashMap<String, Object>();
		// String hql = " from "+ChargeInfoEntity.class.getName();
		String hql = " FROM ChargeInfoEntity t WHERE 1=1 ";

		Session session = this.getSessionFactory().openSession();
		if (WorkId != null && !WorkId.isEmpty()) {
			hql += "  and t.work_id= '" + WorkId + "'";
		}

		// List<ChargeInfoEntity> list = this.query(hql, map);
		@SuppressWarnings("unchecked")
		// List<ChargeInfoEntity> list = (List<ChargeInfoEntity>)session.createSQLQuery(hql).addEntity("t", ChargeInfoEntity.class.getName()).list();
		List<ChargeInfoEntity> list = this.query(hql);
		session.flush();
		session.close();
		return list;
	}

	public List<ChargeInfoEntity> queryChargeArrearByRoomId(String RoomId) {
		// 条件查询
		/*
		 * String hql = "SELECT t.charge_type_no,	t.charge_type_name,	t.work_id, t.owner_id, t.owner_name,t.room_id, t.room_no," +
		 * "t.begin_time AS begin_time,	t.end_time AS end_time,t.arrearage_amount AS arrearage_amount,t.charge_id " + "FROM t_charge_info t WHERE t.state = '03' " +
		 * "AND t.room_id = '" + RoomId +"' " + "ORDER BY t.work_id ";
		 */
		String hql = " SELECT t.charge_type_no,t.charge_type_name,t.work_id,t.owner_id,t.owner_name,t.room_id,	t.room_no,min(t.begin_time) AS begin_time,max(t.end_time) AS end_time,sum(t.arrearage_amount) AS arrearage_amount,"
				+ "GROUP_CONCAT(t.charge_id) as charge_id "
				+ "FROM v_charge_info_app t	"
				+ "WHERE t.room_id = '"+RoomId+"'"
				+ "group by t.room_id, t.work_id ,t.charge_type_name";

		Session session = this.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<ChargeInfoEntity> list = session.createSQLQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}

	public List queryChargeStoreAmountByRoomId(String RoomId) {

		String hql = " SELECT  a.charge_type_no, a.charge_type_name, a.build_area, a.charge_price, a.total_price, a.amount " + " FROM v_charge_build_area a WHERE a.type_flag='01' AND a.room_id='" + RoomId + "' ";
		Session session = this.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List list = session.createSQLQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}

	public List<ChargeInfoEntity> getChargeInfoByIds(String ids) {

		String[] charges = ids.split(",");
		String chargeIds = "";
		for (int i = 0; i < charges.length; i++) {
			chargeIds += "'" + charges[i] + "',";
		}
		chargeIds = chargeIds.substring(0, chargeIds.length() - 1);

		String hql = "from ChargeInfoEntity t where t.charge_id in (:strs) ";
		System.out.println("sql==============" + hql);
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		query.setParameterList("strs", charges);
		List<ChargeInfoEntity> result = query.list();
		session.flush();
		session.close();

		return result;
	}

	public BigDecimal getPayCountByIds(String ids) {

		String[] charges = ids.split(",");
		String chargeIds = "";
		for (int i = 0; i < charges.length; i++) {
			chargeIds += "'" + charges[i] + "',";
		}
		chargeIds = chargeIds.substring(0, chargeIds.length() - 1);

		String sql = " SELECT SUM(t.receive_amount)*100 FROM t_charge_info t WHERE t.state='03' AND t.charge_id IN (" + chargeIds + ") ";

		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();

		session.flush();
		session.close();
		System.out.println(sql);
		BigDecimal r = (BigDecimal) result.get(0);
		return r;
	}

	public BigDecimal getArrearAmountByRoomId(String id) {

		String sql = " SELECT SUM(t.receive_amount) FROM t_charge_info t, t_charge_type_setting s " + "WHERE t.charge_type_no=s.charge_type_no AND s.type_flag='01' AND t.state='03' " + "and t.room_id='" + id + "' AND t.begin_time>date_add(curdate(), interval - day(curdate()) + 1 day) ";

		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		System.out.println(sql);
		BigDecimal r = (BigDecimal) result.get(0);
		return r;
	}

	public List<ChargeInfoEntity> queryChargeArrearByWorkId(String workId) {
		// 条件查询
		String hql = " SELECT t.work_id, t.owner_id, t.owner_name, t.room_id, t.room_no, SUM(t.arrearage_amount) arrearage_total," + " SUM(t.receive_amount) receive_total, group_concat(t.charge_type_no) charge_type_no, group_concat(t.charge_type_name) charge_type_name," + " group_concat(t.arrearage_amount) arrearage_amount, group_concat(t.receive_amount) receive_amount, group_concat(t.charge_id) charge_ids, " + " MIN(t.begin_time) begin_time, MAX(t.end_time) end_time FROM t_charge_info t WHERE t.work_id='" + workId + "' ";
		Session session = this.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<ChargeInfoEntity> list = session.createSQLQuery(hql).list();
		session.flush();
		session.close();

		return list;
	}
	
	/**
	 * 根据房间ID来查询该房间的未收及欠费账单
	 * 
	 * @param operId string 操作员id
	 * @param roomId string 房间ID
	 * @param startTime string 账单开始时间
	 * @param endTime string 账单介绍时间
	 * @return 0 为成功 1为不成功
	 */
	public int manOwnerOfCharge(String operId,String roomId,String startTime,String endTime) {
		int intError = 0;
		Session session = this.getSessionFactory().openSession();
		SQLQuery query =   session.createSQLQuery("{CALL pro_createChargeInfo_manual(?,?,?,?)}");
		query.setString(0,startTime);
		query.setString(1,endTime);
		query.setString(2,roomId);
		query.setString(3,operId);
		intError = (int) query.list().get(0);
		return intError;
	}
}
