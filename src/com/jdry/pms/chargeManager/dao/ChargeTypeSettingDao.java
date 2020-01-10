package com.jdry.pms.chargeManager.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;
import com.jdry.pms.comm.util.CommUser;

@Repository
public class ChargeTypeSettingDao extends HibernateDao {

	static Logger log = Logger.getLogger(ChargeTypeSettingDao.class);

	public void queryAll(Page<ChargeTypeSettingViewEntity> page, Map<String, Object> parameter) {

		String hql = " from " + ChargeTypeSettingViewEntity.class.getName();
		String whereCase = " a where 1=1 ";
		String sqlCount = "select count(*) from " + ChargeTypeSettingViewEntity.class.getName() + " b where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();

		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();

			if (!search.equals("")) {
				whereCase += " and (a.charge_type_no like:charge_type_no "
						+ " or a.drop_charge_mode like:drop_charge_mode "
						+ " or a.drop_charge_way like:drop_charge_way "
						+ " or a.drop_charge_type like:drop_charge_type "
						+ " or a.drop_charge_cycle_unit like:drop_charge_cycle_unit "
						+ " or a.charge_type_name like:charge_type_name) ";
				sqlCount += " and (b.charge_type_no like:charge_type_no "
						+ " or b.drop_charge_mode like:drop_charge_mode "
						+ " or b.drop_charge_way like:drop_charge_way "
						+ " or b.drop_charge_type like:drop_charge_type "
						+ " or b.drop_charge_cycle_unit like:drop_charge_cycle_unit "
						+ " or b.charge_type_name like:charge_type_name) ";
				;

				map.put("charge_type_no", "%" + search + "%");
				map.put("charge_type_name", "%" + search + "%");
				map.put("drop_charge_mode", "%" + search + "%");
				map.put("drop_charge_way", "%" + search + "%");
				map.put("drop_charge_type", "%" + search + "%");
				map.put("drop_charge_cycle_unit", "%" + search + "%");
			}
		}

		hql += whereCase;

		System.out.println("hql===" + hql);
		// List<ChargeTypeSettingEntity> list = this.query(hql);
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return list;
	}

	// 新增和修改
	public void saveAll(ChargeTypeSettingEntity chargeType) {
		Session session = this.getSessionFactory().openSession();

		if (chargeType.getCharge_type_id() == null || chargeType.getCharge_type_id().equals("")) {
			chargeType.setCharge_type_id(UUID.randomUUID().toString());
		}
		chargeType.setUpdate_date(new Date());
		chargeType.setUpdate_emp_id(CommUser.getUserName());
		session.saveOrUpdate(chargeType);

		session.flush();
		session.close();

		log.info("收费项设置--" + chargeType.getCharge_type_no());
	}

	public void delete(List<ChargeTypeSettingEntity> chargeTypes) {
		Session session = null;
		Transaction tx = null;

		String chargeTypeIds = "";
		try {
			session = this.getSessionFactory().openSession();
			tx = session.beginTransaction();
			for (ChargeTypeSettingEntity type : chargeTypes) {
				session.delete(type);
				chargeTypeIds += type.getCharge_type_no() + ",";
			}
			tx.commit();
			log.info("收费项删除--" + chargeTypeIds);
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.info("收费项删除失败--" + chargeTypeIds);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	// 下拉框：收费项
	public List queryChargeTypeInfo(String keyword, String type) {
		String sql = " SELECT t.* from t_charge_type_setting t where 1=1 ";
		if (type != null && !type.isEmpty()) {
			sql += " and t.charge_way='" + type + "' ";
		}

		if (keyword != null && !keyword.equals("")) {
			sql += " and (t.charge_type_name like '%" + keyword + "%' " + "or t.charge_type_no like '%" + keyword + "%') ";
		}
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("charge_type_id", StandardBasicTypes.STRING)
				.addScalar("charge_type_no", StandardBasicTypes.STRING)
				.addScalar("charge_type_name", StandardBasicTypes.STRING)
				.addScalar("type_flag", StandardBasicTypes.STRING)
				.addScalar("charge_mode", StandardBasicTypes.STRING)
				.addScalar("charge_price", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("charge_way", StandardBasicTypes.STRING).list();
		session.flush();
		session.close();
		return result;

	}

	// 下拉框：房间及人员
	public List queryHourseOwnerInfo(String keyword) {
		String sql = " SELECT t.* from v_room_billing_info t where 1=1 ";
		if (keyword != null && !keyword.equals("")) {
			sql += " and (t.room_no like '%" + keyword + "%' " + "or t.phone like '%" + keyword + "%'"
					+ "or t.storied_build_name like '%" + keyword + "%'" + "or t.owner_name like '%" + keyword + "%') ";
		}

		sql += " limit 0, 20";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("community_name", StandardBasicTypes.STRING)
				.addScalar("storied_build_name", StandardBasicTypes.STRING)
				.addScalar("unit_name", StandardBasicTypes.STRING)
				.addScalar("room_no", StandardBasicTypes.STRING)
				.addScalar("owner_name", StandardBasicTypes.STRING)
				.addScalar("phone", StandardBasicTypes.STRING)				
				.addScalar("room_state", StandardBasicTypes.STRING)
				.addScalar("room_state_name", StandardBasicTypes.STRING)
				.addScalar("room_type", StandardBasicTypes.STRING)
				.addScalar("charge_date", StandardBasicTypes.DATE)
				.addScalar("charge_price", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("months_price", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("owner_id", StandardBasicTypes.STRING)
				.addScalar("room_id", StandardBasicTypes.STRING)
				.addScalar("build_area", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("exp_date", StandardBasicTypes.DATE)
				.addScalar("arr_amount", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("room_addrs", StandardBasicTypes.STRING)
				.addScalar("charge_type_no", StandardBasicTypes.STRING)
				.addScalar("make_room_date", StandardBasicTypes.DATE)
				.addScalar("receive_room_date", StandardBasicTypes.DATE)
				.list();
		session.flush();
		session.close();
		System.out.println(sql);
		return result;

	}

	// 下拉框：管理处（小区）
	public List queryBelongUnitInfo(String keyword) {
		String sql = " SELECT t.* from t_area_property t where 1=1 ";
		if (keyword != null && !keyword.equals("")) {
			sql += " and (t.community_name like '%" + keyword + "%') ";
		}

		sql += " limit 0, 10 ";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("community_id", StandardBasicTypes.STRING)
				.addScalar("community_name", StandardBasicTypes.STRING).list();
		session.flush();
		session.close();
		System.out.println(sql);
		return result;

	}

	// 下拉框：楼宇
	public List queryStoriedBuildInfo(String keyword) {
		String sql = " SELECT t.* from t_building_property t where 1=1 ";
		if (keyword != null && !keyword.equals("")) {
			sql += " and (t.storied_build_name like '%" + keyword + "%') ";
		}

		sql += " limit 0, 10 ";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("storied_build_id", StandardBasicTypes.STRING)
				.addScalar("storied_build_name", StandardBasicTypes.STRING).list();
		session.flush();
		session.close();
		System.out.println(sql);
		return result;

	}

	// 收费编号的唯一性验证
	public boolean validateTpyeNo(String type) {
		boolean flag = true;

		String sql = " SELECT * FROM t_charge_type_setting s where s.charge_type_no='" + type + "' ";

		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		System.out.println(sql);

		if (result.size() > 0) {
			flag = false;
		}

		return flag;
	}

}
