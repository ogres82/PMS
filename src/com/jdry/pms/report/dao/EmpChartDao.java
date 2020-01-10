package com.jdry.pms.report.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

@Repository
@Transactional
public class EmpChartDao extends HibernateDao{

	@SuppressWarnings("unchecked")
	public List<Object> empSexChart() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(t.emp_id) as value,case when t.emp_sex='0' then '女' when t.emp_sex ='1' then '男' else '未知' end name from t_emp t group by t.emp_sex";
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
	public List<Object> empDegreeChart() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(t.emp_id) value,t.emp_education_name from v_t_emp t where t.emp_education is not null and t.emp_education !='' group by t.emp_education";
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
	public List<Object> empAgeChart() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "SELECT "+
				"count(*) AS '人数', "+
				"age AS '年龄段' "+
				"FROM "+
				"( "+
						"SELECT "+
						"CASE "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('2010-01-01','%Y-%m-%d') "+
						"THEN "+
						"'10后' "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('2000-01-01','%Y-%m-%d') "+
						"AND date_format(emp_birth,'%Y-%m-%d') < DATE_FORMAT('2010-01-01','%Y-%m-%d') THEN "+
						"'00后' "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('1990-01-01','%Y-%m-%d') "+
						"AND date_format(emp_birth,'%Y-%m-%d') < DATE_FORMAT('2000-01-01','%Y-%m-%d') THEN "+
						"'90后' "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('1980-01-01','%Y-%m-%d') "+
						"AND date_format(emp_birth,'%Y-%m-%d') < DATE_FORMAT('1990-01-01','%Y-%m-%d') THEN "+
						"'80后' "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('1970-01-01','%Y-%m-%d') "+
						"AND date_format(emp_birth,'%Y-%m-%d') < DATE_FORMAT('1980-01-01','%Y-%m-%d') THEN "+
						"'70后' "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('1960-01-01','%Y-%m-%d') "+
						"AND date_format(emp_birth,'%Y-%m-%d') < DATE_FORMAT('1970-01-01','%Y-%m-%d') THEN "+
						"'60后' "+
						"WHEN date_format(emp_birth,'%Y-%m-%d') >= DATE_FORMAT('1950-01-01','%Y-%m-%d') "+
						"AND date_format(emp_birth,'%Y-%m-%d') < DATE_FORMAT('1960-01-01','%Y-%m-%d') THEN "+
						"'50后' "+
						"else '其他' "+
							"END AS age "+
							"FROM "+
							"t_emp ) t group by age";
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
	public List<Object> empDeptChart() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(t.emp_dept_id) value,t.dept_name name from v_t_emp t group by t.emp_dept_id";
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
