package com.jdry.pms.equipment.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.equipment.pojo.EqpFile;

@Repository
@Transactional
public class EqpFileDao extends HibernateDao{
	
	static Logger log = Logger.getLogger(EqpFileDao.class);
	
	public Collection<EqpFile> queryEqpFileAll() {
		Session session = this.getSessionFactory().openSession();
		String hql = "from EqpFile du where 1=1";
		List<EqpFile> list = session.createQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public void addEqpFile(EqpFile eqpFile){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(eqpFile);
		log.info("新增设备--"+eqpFile.getEqpId());
		session.flush();
		session.close();
	}
	
	public Collection<EqpFile> queryEqpFileByParam(Map<String, Object> parameter){
		Session session = this.getSessionFactory().openSession();
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String eqpId = parameter.get("eqpId")!=null?parameter.get("eqpId").toString():"";
		String hql = "from EqpFile du where 1=1";
		if(eqpId != null && !"".equals(eqpId)){
			hql+=" and du.eqpId='"+eqpId+"'";
		}
		List<EqpFile> list = session.createQuery(hql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public EqpFile getEqpFileById(String eqpId){
		String hql = "from EqpFile du where du.eqpId='"+eqpId+"'";
		return (EqpFile) this.query(hql).get(0);
	}
	
	public void delEqpById(String eqpIds){
		Session session = this.getSessionFactory().openSession();
		String[] ids = eqpIds.split(",");
		
		for(int i = 0;i<ids.length;i++){
			EqpFile eqpFile = getEqpFileById(ids[i]);
			if(null != eqpFile){
				session.delete(eqpFile);
				log.info("删除设备--"+eqpFile.getEqpId());
			}
		}
		session.flush();
		session.close();
	}
	
	public Collection queryEqpFileByName(String eqpName){
		String sql = "select eqp_name,eqp_id from t_eqp_file where eqp_name like '%"+eqpName+"%' limit 10";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("eqp_id", StandardBasicTypes.STRING)
												.addScalar("eqp_name", StandardBasicTypes.STRING)
												.list();
		session.flush();
		session.close();
		return result;
	}

}
