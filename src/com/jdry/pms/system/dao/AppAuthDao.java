package com.jdry.pms.system.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.system.pojo.AppAuth;
import com.jdry.pms.system.pojo.AppUrl;
import com.jdry.pms.system.pojo.VAppAuth;

@Repository
@Transactional
public class AppAuthDao extends HibernateDao{
	
	public List<AppUrl> getAllUrl(){
		String hql = "from AppUrl";
		return this.query(hql);
	}
	public List loadUrlByRole(String roleId){
		String sql = "";
		sql = "select b.id_,b.role_id,a.url_id,a.url_name,a.name,a.order_no from system_app_url a left join"+
					 " (select * from system_app_roleurl t where t.role_id='"+roleId+"') b on a.url_id=b.url_id";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	public List<VAppAuth> getAppAuthByRole(String roleId){
		String hql = "from VAppAuth where roleId='"+roleId+"'";
		return this.query(hql);
	}
	public void saveAppAuth(AppAuth appAuth){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(appAuth);
		session.flush();
		session.close();
	}
	
	public void deleteAppAuth(String id){
		Session session = this.getSessionFactory().openSession();
		AppAuth appAuth = getAppAuthById(id);
		if(appAuth != null){
			session.delete(appAuth);
		}
		session.flush();
		session.close();
	}
	public AppAuth getAppAuthById(String id){
		String hql = "from AppAuth where id = '"+id+"'";
		return (AppAuth) this.query(hql).get(0);
	}

}
