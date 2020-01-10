package com.jdry.pms.equipment.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.equipment.pojo.EqpFixRec;
import com.jdry.pms.equipment.pojo.VEqpFixRec;

@Repository
public class EqpFixRecDao extends HibernateDao{

	static Logger log = Logger.getLogger(EqpFixRecDao.class);
	
	public Collection<VEqpFixRec> queryAllRec(){
		Session session = this.getSessionFactory().openSession();
		String hql = "from VEqpFixRec du where 1=1";
		List<VEqpFixRec> list = session.createQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public Collection<VEqpFixRec> queryEqpFixRecByParam(Map<String, Object> parameter){
		Session session = this.getSessionFactory().openSession();
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String fixRecId = parameter.get("fixRecId")!=null?parameter.get("fixRecId").toString():"";
		String hql = "from VEqpFixRec du where 1=1";
		if(fixRecId != null && !"".equals(fixRecId)){
			hql+=" and du.fixRecId='"+fixRecId+"'";
		}
		List<VEqpFixRec> list = session.createQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public void addEqpFixRec(EqpFixRec rec){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(rec);
		log.info("新增设备运维记录--"+rec.getFixRecId());
		session.flush();
		session.close();
	}
	
	public EqpFixRec getEqpFixRecById(String recId){
		String hql = "from EqpFixRec du where du.fixRecId='"+recId+"'";
		EqpFixRec rec = (EqpFixRec) this.query(hql).get(0);
		return (EqpFixRec) this.query(hql).get(0);
	}
	
	public void delEqpFixRecById(String recIds){
		Session session = this.getSessionFactory().openSession();
		String[] ids = recIds.split(",");
		
		for(int i = 0;i<ids.length;i++){
			EqpFixRec rec = getEqpFixRecById(ids[i]);
			if(null != rec){
				session.delete(rec);
				log.info("删除设备运维记录--"+rec.getFixRecId());
			}
		}
		session.flush();
		session.close();
	}
}
