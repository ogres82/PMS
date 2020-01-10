package com.jdry.pms.environment.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentPlant;
import com.jdry.pms.environment.pojo.VEnvironmentPlant;

@Repository
@Transactional
public class PlantDao extends HibernateDao{

	public void queryPlantByParam(Page<VEnvironmentPlant> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VEnvironmentPlant.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VEnvironmentPlant.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and ( a.plantName like:plantName "+
							 " or a.plantTypeName like:plantTypeName )";
				sqlCount += " and ( b.plantName like:plantName "+
							 " or b.plantTypeName like:plantTypeName)";
						    
				map.put("plantName", "%"+search+"%");
				map.put("plantTypeName", "%"+search+"%");
			}
		}		
		hql += whereCase;				
		System.out.println("hql==="+hql);
		
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	public VEnvironmentPlant getVEnvironmentPlantById(String plantId) {

		List<VEnvironmentPlant> lists = this
				.query("from " + VEnvironmentPlant.class.getName()
						+ " where plantId='" + plantId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public EnvironmentPlant getEnvironmentPlantById(String plantId) {

		List<EnvironmentPlant> lists = this
				.query("from " + EnvironmentPlant.class.getName()
						+ " where plantId='" + plantId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addPlant(EnvironmentPlant plant) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(plant);
		session.flush();
		session.close();
		session = null;
	}

	public void deletePlant(String plantId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = plantId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			EnvironmentPlant plant = getEnvironmentPlantById(ids[i]);
			if(null != plant){
				session.delete(plant);
			}
		}
		session.flush();
		session.close();
		
	}

}
