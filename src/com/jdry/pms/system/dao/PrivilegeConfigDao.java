package com.jdry.pms.system.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.system.pojo.PrivilegeConfig;

@Repository
@Transactional
public class PrivilegeConfigDao extends HibernateDao{
	public void saveOrUpdateConfig(List<PrivilegeConfig> list){
		Session session = this.getSessionFactory().openSession();
		for(int i=0;i<list.size();i++){
			session.saveOrUpdate(list.get(i));
		}
		session.flush();
		session.close();
	}
	
	public List<PrivilegeConfig> loadPrivilegeConfig(){
		String hql = "from PrivilegeConfig";
		return this.query(hql);
	}
	
	public PrivilegeConfig loadPCByType(String type){
		String hql = "from PrivilegeConfig where type='"+type+"'";
		return (PrivilegeConfig) this.query(hql).get(0);
	}

}
