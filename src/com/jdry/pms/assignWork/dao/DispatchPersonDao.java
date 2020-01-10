package com.jdry.pms.assignWork.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assignWork.pojo.DispatchPerson;
@Repository
@Transactional
public class DispatchPersonDao extends HibernateDao
{
    /**
     * 添加 ：把处理人和关系加入库
     * @param person
     */
	public void addDisptchPersonInfo(DispatchPerson person)
	{
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(person);
		session.flush();
		session.close();
		
	}
	
	public List queryAllHandler()
	{
		Session session = this.getSessionFactory().openSession();
		String sql = "select * from v_user_dept_pos where DEPT_ID='006'"; 
		List result = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return result;
	}
	
	public void deleteDispatchPersonById(String rptId){
		List<DispatchPerson> list = this.query("from DispatchPerson t where t.rpt_id='"+rptId+"'");
		Session session = this.getSessionFactory().openSession();
		for(int i=0;i<list.size();i++){
			session.delete(list.get(i));
		}
		session.flush();
		session.close();
	}
}
