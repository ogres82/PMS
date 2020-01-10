package com.jdry.pms.environment.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.environment.pojo.EnvironmentPlantMtnRec;
import com.jdry.pms.environment.pojo.VEnvironmentPlantMtnRec;

@Repository
@Transactional
public class PlantMtnRecDao extends HibernateDao{

	public void queryPlantMtnRecByParam(Page<VEnvironmentPlantMtnRec> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VEnvironmentPlantMtnRec.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VEnvironmentPlantMtnRec.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and ( a.areaName like:areaName "+
							 " or a.plantMtnPersonName like:plantMtnPersonName" +
							 " or a.plantMtnDetail like:plantMtnDetail)";
				sqlCount += " and ( b.plantName like:plantName "+
							 " or b.plantTypeName like:plantTypeName " +
							 " or b.plantMtnDetail like:plantMtnDetail)";
						    
				map.put("areaName", "%"+search+"%");
				map.put("plantTypeName", "%"+search+"%");
				map.put("plantMtnDetail", "%"+search+"%");
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

	public VEnvironmentPlantMtnRec getVPlantMtnRecById(String recId) {
		List<VEnvironmentPlantMtnRec> lists = this
				.query("from " + VEnvironmentPlantMtnRec.class.getName()
						+ " where recId='" + recId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public EnvironmentPlantMtnRec getPlantMtnRecById(String recId) {
		List<EnvironmentPlantMtnRec> lists = this
				.query("from " + EnvironmentPlantMtnRec.class.getName()
						+ " where recId='" + recId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addPlantMtnRec(EnvironmentPlantMtnRec mtnRec) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(mtnRec);
		session.flush();
		session.close();
		session = null;
	}

	public void deletePlantMtnRec(String recId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = recId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			EnvironmentPlantMtnRec mtnRec = getPlantMtnRecById(ids[i]);
			if(null != mtnRec){
				session.delete(mtnRec);
			}
		}
		session.flush();
		session.close();
	}

}
