package com.jdry.pms.gis.web.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.gis.app.entity.GisUserLocation;

@Repository
public class WebGisDao extends HibernateDao{

	
	public List getWorkers(String keyword){
		String sql = "select USERNAME_,CNAME_,DEPT_NAME from v_user_dept_pos where CNAME_ like '%"+keyword+"%'"; 
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("USERNAME_", StandardBasicTypes.STRING)
												.addScalar("CNAME_", StandardBasicTypes.STRING)
												.addScalar("DEPT_NAME", StandardBasicTypes.STRING)
												.list();
		session.flush();
		session.close();
		return result;
	}
	
	public List<GisUserLocation> getUserLocation(String userName,long startTime,long endTime){
		String hql = "from GisUserLocation where username = '"+userName+"' and realtime >= '"+startTime+"' and realtime <= '"+endTime+"'";
		return this.query(hql);
	}

	@SuppressWarnings("rawtypes")
	public List<GisUserLocation> getAllUserLatestLocation() {
		Session session = this.getSessionFactory().openSession();
		String sql = "SELECT t.id,t.username,t.realtime,t.x_axis,t.y_axis,t.cname FROM(SELECT * FROM t_gis_user_location a ORDER BY a.realtime DESC) t "+
			  "GROUP BY t.username ORDER BY t.realtime ";
		List<GisUserLocation> list = session.createSQLQuery(sql).addEntity(GisUserLocation.class).list();
		session.flush();
		session.close();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getAllUserName(String deptId){
		Session session = this.getSessionFactory().openSession();
		String whereCase = "";
		String sql = "SELECT a.USERNAME_ as userName,a.CNAME_ as cname,b.DEPT_ID_ as deptId,0 as xAxis,0 as yAxis,UNIX_TIMESTAMP(NOW()) * 1000 as realTime FROM bdf2_user a LEFT JOIN bdf2_user_dept b ON a.USERNAME_=b.USERNAME_";
		if(!deptId.isEmpty()){
			whereCase += " where b.DEPT_ID_ = '"+deptId+"'";
		}
		@SuppressWarnings("rawtypes")
		Query query = session.createSQLQuery(sql+whereCase).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.list();
		session.flush();
		session.close();
		return list;
	}
	
	public List<DefaultDept> getAllDept(){
		return this.query("from DefaultDept");
	}
}
