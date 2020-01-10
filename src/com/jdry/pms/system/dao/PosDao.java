package com.jdry.pms.system.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.equipment.controller.EqpFileController;
@Repository
@Transactional
public class PosDao extends HibernateDao{
	
	static Logger log = Logger.getLogger(EqpFileController.class);
	
	public List<DefaultPosition> loadPosition(){
		String hql = "from DefaultPosition where companyId = 'jdry'";
		return this.query(hql);
	}
	
	public void savePosition(DefaultPosition pos){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(pos);
		session.flush();
		session.close();
	}
	
	public void deletePosition(String ids){
		Session session = this.getSessionFactory().openSession();
		String[] posIds = ids.split(",");
		
		for(int i = 0;i<posIds.length;i++){
			DefaultPosition position = getPosById(posIds[i]);
			if(null != position){
				session.delete(position);
				log.info("删除岗位--"+position.getId());
			}
		}
		session.flush();
		session.close();
	}
	
	public DefaultPosition getPosById(String id){
		String hql = "from DefaultPosition where id = '"+id+"'";
		return (DefaultPosition) this.query(hql).get(0);
	}
}
