package com.jdry.pms.equipment.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.equipment.pojo.EqpMtnRec;
import com.jdry.pms.equipment.pojo.VEqpMtnRec;

@Repository
public class EqpMtnRecDao extends HibernateDao{

	public Collection<VEqpMtnRec> queryAllRec(){
		Session session = this.getSessionFactory().openSession();
		String hql = "from VEqpMtnRec du where 1=1";
		List<VEqpMtnRec> list = session.createQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public Collection<VEqpMtnRec> queryEqpMtnRecByParam(Map<String, Object> parameter){
		Session session = this.getSessionFactory().openSession();
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String mtnRecId = parameter.get("mtnRecId")!=null?parameter.get("mtnRecId").toString():"";
		String hql = "from VEqpMtnRec du where 1=1";
		if(mtnRecId != null && !"".equals(mtnRecId)){
			hql+=" and du.mtnRecId='"+mtnRecId+"'";
		}
		List<VEqpMtnRec> list = session.createQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public void addEqpMtnRec(EqpMtnRec rec){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(rec);
		session.flush();
		session.close();
	}
	
	public EqpMtnRec getEqpMtnRecById(String recId){
		String hql = "from EqpMtnRec du where du.mtnRecId='"+recId+"'";
		EqpMtnRec rec = (EqpMtnRec) this.query(hql).get(0);
		return (EqpMtnRec) this.query(hql).get(0);
	}
	
	public void delEqpMtnRecById(String recIds){
		Session session = this.getSessionFactory().openSession();
		String[] ids = recIds.split(",");
		
		for(int i = 0;i<ids.length;i++){
			EqpMtnRec rec = getEqpMtnRecById(ids[i]);
			if(null != rec){
				session.delete(rec);
			}
		}
		session.flush();
		session.close();
	}
}
