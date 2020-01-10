package com.jdry.pms.basicInfo.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.basicInfo.pojo.VUser;
@Repository
@Transactional
public class ProManagerDao extends HibernateDao{
	public String managerLogin(String userName,String pwd){
		String sql = "select PASSWORD_ from bdf2_user where USERNAME_='"+userName+"'";
		List list = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).list();
		if(list.size()==0){
			return "0";
		}else{
			 if(list.get(0).equals(pwd)){
				 return "1";
			 }else{
				 return "0";
			 }
			
		}
		
	}
	public String getSalt(String userName){
		String sql = "select SALT_ from bdf2_user where USERNAME_='"+userName+"'";
		List list = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).list();
		if(list.size()==0){
			return "";
		}else{
			return (String) list.get(0);
		}		
	}

	public Collection<VUser> getPositionByName(String userName){
		String hql = "from VUser where userName='"+userName+"'";
		return this.query(hql);
	}
}
