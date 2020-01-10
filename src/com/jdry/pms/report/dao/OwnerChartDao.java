package com.jdry.pms.report.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

@Repository
@Transactional
public class OwnerChartDao extends HibernateDao{

	@SuppressWarnings("unchecked")
	public List<Object> ownerInByRoomType() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(t.room_id) value,b.code_detail_name name from t_house_property t LEFT JOIN dir_directorydetail b on b.code_detail = t.room_type where b.`code`='room_type' group by t.room_type ";
		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> ownerInByRoomState() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(*) value,'入住' name from  t_house_property t where room_type='0' and t.room_state=3 UNION ALL "+
						"select count(*) value,'未入住' name from  t_house_property t where room_type='0' and t.room_state!=3 UNION ALL "+
						"select count(*) value,'入住' name from  t_house_property t where room_type='1' and t.room_state=3 UNION ALL "+
						"select count(*) value,'未入住' name from  t_house_property t where room_type='1' and t.room_state!=3 UNION ALL "+
						"select count(*) value,'入住' name from  t_house_property t where room_type='2' and t.room_state=3 UNION ALL "+
						"select count(*) value,'未入住' name from  t_house_property t where room_type='2' and t.room_state!=3 ";

		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> communityAndRoom() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "SELECT count(c.room_id) value,a.community_name "+
						"FROM t_area_property a "+
						"LEFT JOIN t_building_property b ON a.community_id = b.belong_comm_id "+
						"LEFT JOIN t_house_property c on b.storied_build_id = c.belong_sb_id GROUP BY a.community_id ";

		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> communityAndIns() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "SELECT count(c.room_id) value,a.community_name "+
						"FROM t_area_property a "+
						"LEFT JOIN t_building_property b ON a.community_id = b.belong_comm_id "+
						"LEFT JOIN (select room_id,belong_sb_id,room_state from t_house_property where room_state='3') c on b.storied_build_id = c.belong_sb_id GROUP BY a.community_id ";

		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> ownerInByMonth() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "SELECT count(t.room_id) value,DATE_FORMAT(t.make_room_date,'%Y%m') name "+
						"FROM t_house_property t "+
						"WHERE t.room_state = '3' "+
//						"AND DATE_FORMAT(t.make_room_date,'%Y') = date_format(now(),'%Y') "+
						"GROUP BY DATE_FORMAT(t.make_room_date, '%Y%m') ";

		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> roomAndInRate() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(*) from t_house_property t where t.room_state ='3' union all "+
						"select count(*) from t_house_property t ";

		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> ownerAgeChart() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "SELECT "+
				"count(*) AS '人数', "+
				"age AS '年龄段' "+
				"FROM "+
				"( "+
						"SELECT "+
						"CASE "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('2010-01-01','%Y-%m-%d') "+
						"THEN "+
						"'10后' "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('2000-01-01','%Y-%m-%d') "+
						"AND date_format(birth_date,'%Y-%m-%d') < DATE_FORMAT('2010-01-01','%Y-%m-%d') THEN "+
						"'00后' "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('1990-01-01','%Y-%m-%d') "+
						"AND date_format(birth_date,'%Y-%m-%d') < DATE_FORMAT('2000-01-01','%Y-%m-%d') THEN "+
						"'90后' "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('1980-01-01','%Y-%m-%d') "+
						"AND date_format(birth_date,'%Y-%m-%d') < DATE_FORMAT('1990-01-01','%Y-%m-%d') THEN "+
						"'80后' "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('1970-01-01','%Y-%m-%d') "+
						"AND date_format(birth_date,'%Y-%m-%d') < DATE_FORMAT('1980-01-01','%Y-%m-%d') THEN "+
						"'70后' "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('1960-01-01','%Y-%m-%d') "+
						"AND date_format(birth_date,'%Y-%m-%d') < DATE_FORMAT('1970-01-01','%Y-%m-%d') THEN "+
						"'60后' "+
						"WHEN date_format(birth_date,'%Y-%m-%d') >= DATE_FORMAT('1950-01-01','%Y-%m-%d') "+
						"AND date_format(birth_date,'%Y-%m-%d') < DATE_FORMAT('1960-01-01','%Y-%m-%d') THEN "+
						"'50后' "+
						"else '其他' "+
							"END AS age "+
							"FROM "+
							"t_property_owner ) t group by age";
		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

}
