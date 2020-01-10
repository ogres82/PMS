package com.jdry.pms.basicInfo.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.VParkingArea;

@Repository
@Transactional
public class ParkingAreaDao extends HibernateDao{

	public void query(Page<ParkingArea> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String belongComId = parameter.get("belongComId")!=null?parameter.get("belongComId").toString():"";
		String parkName = parameter.get("parkName")!=null?parameter.get("parkName").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+ParkingArea.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+ParkingArea.class.getName()+" du where 1=1";
	    if(parkName!=null&&!"".equals(parkName)){
	        map.put("parkName", "%"+parkName+"%");
	        sql+=" and du.parkName like:parkName";
	        sqlCount+=" and du.parkName like:parkName";
	    }
	    if(belongComId!=null&&!"".equals(belongComId)){
	        map.put("belongComId", belongComId);
	        sql+=" and du.belongComId =:belongComId";
	        sqlCount+=" and du.belongComId =:belongComId";
	    }
	    
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void saveParkingArea(Collection<ParkingArea> parkingAeas) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (ParkingArea park : parkingAeas) {
	            EntityState state=EntityUtils.getState(park);
	            if (state.equals(EntityState.NEW)) {
	                session.save(park);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(park);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(park);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
		
	}

	public Collection<ParkingArea> queryParkingAreaByParam(
			Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String belongComId = parameter.get("communityId")!=null?parameter.get("communityId").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+ParkingArea.class.getName()+" du where 1=1";
	    if(belongComId!=null&&!"".equals(belongComId)){
	        map.put("belongComId", belongComId);
	        sql+=" and du.belongComId =:belongComId";
	    }
	   
		return  this.query(sql, map);
	}

	public void queryParkingAreaByParam(Page<VParkingArea> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VParkingArea.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where state='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}		
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VParkingArea.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.parkName like:parkName "+
							 " or a.build_name like:build_name" +
							 " or a.community_name like:community_name";
				sqlCount += " and b.parkName like:parkName "+
						    " or b.build_name like:build_name" +
						    " or b.community_name like:community_name";
				map.put("parkName", "%"+search+"%");
				map.put("build_name", "%"+search+"%");
				map.put("community_name", "%"+search+"%");
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

	public ParkingArea getParkingAreaById(String parkId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parkId", parkId);

		List<ParkingArea> lists = this
				.query("from " + ParkingArea.class.getName()
						+ " where parkId='" + parkId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public VParkingArea getVParkingAreaById(String parkId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parkId", parkId);

		List<VParkingArea> lists = this
				.query("from " + VParkingArea.class.getName()
						+ " where parkId='" + parkId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addParkingArea(ParkingArea parkingArea) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(parkingArea);
		session.flush();
		session.close();
		session = null;
	}

	public void deleteParkingArea(String parkId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = parkId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			ParkingArea parkingArea = getParkingAreaById(ids[i]);
			if(null != parkingArea){
				session.delete(parkingArea);
			}
		}
		session.flush();
		session.close();
	}
	
	public List<VParkingArea> getAllParkingArea(){
		String hql = "from VParkingArea";
		return this.query(hql);
	}
}
