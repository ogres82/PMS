package com.jdry.pms.basicInfo.dao;

import java.util.ArrayList;
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
import com.jdry.pms.basicInfo.pojo.Parking;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.ParkingOwner;
import com.jdry.pms.basicInfo.pojo.VParking;

@Repository
@Transactional
public class ParkingDao extends HibernateDao{
	public void query(Page<Parking> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String carportNo = parameter.get("carportNo")!=null?parameter.get("carportNo").toString():"";
		String ownerName = parameter.get("ownerName")!=null?parameter.get("ownerName").toString():"";
		String belongParkNo = parameter.get("belongParkNo")!=null?parameter.get("belongParkNo").toString():"";
		String carportStatus = parameter.get("carportStatus")!=null?parameter.get("carportStatus").toString():"";
		String licensePlateNo = parameter.get("licensePlateNo")!=null?parameter.get("licensePlateNo").toString():"";
		String buildId = parameter.get("buildId")!=null?parameter.get("buildId").toString():"";
		String areaId = parameter.get("belongComId")!=null?parameter.get("belongComId").toString():"";
		
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+Parking.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+Parking.class.getName()+" du where 1=1";
	    if(carportNo!=null&&!"".equals(carportNo)){
	        map.put("carportNo", "%"+carportNo+"%");
	        sql+=" and du.carportNo like:carportNo";
	        sqlCount+=" and du.carportNo like:carportNo";
	    }
	    
	    if(belongParkNo!=null&&!"".equals(belongParkNo)){
	        map.put("belongParkNo", belongParkNo);
	        sql+=" and du.belongParkNo =:belongParkNo";
	        sqlCount+=" and du.belongParkNo =:belongParkNo";
	    }else{
	    	
	    	if(areaId!=null&&!"".equals(areaId)){
		        List<String> a = new ArrayList<String>();
		        Map<String,String> mapArea = new HashMap<String,String>();
		        mapArea.put("areaId", areaId);
		    	Collection<ParkingArea> parks = this.queryParkingAreaByJdbc(mapArea);
		    	for(ParkingArea park:parks){
		    		a.add(park.getParkId());
		    	}
		    	map.put("a", a);
		        sql+=" and du.belongParkNo in (:a)";
		        sqlCount+=" and du.belongParkNo in (:a)";
		    }else{
		    	 if(buildId!=null&&!"".equals(buildId)){
		    		 
		    		 List<String> b = new ArrayList<String>();
		    		 Map<String,String> mapArea = new HashMap<String,String>();
		    		 mapArea.put("buildId", buildId);
		    		 Collection<ParkingArea> parks = this.queryParkingAreaByJdbc(mapArea);
		    		 for(ParkingArea park:parks){
				    		b.add(park.getParkId());
				     }
		    		 
		    		 map.put("b", b);
		    		 sql+=" and du.belongParkNo in (:b)";
		    		 sqlCount+=" and du.belongParkNo in (:b)";
		 	    }
		    }
	    }
	    
	    
	    if(ownerName!=null&&!"".equals(ownerName)){
	        map.put("ownerName", "%"+ownerName+"%");
	        sql+=" and du.ownerName like:ownerName";
	        sqlCount+=" and du.ownerName like:ownerName";
	    }
	    if(carportStatus!=null&&!"".equals(carportStatus)){
	        map.put("carportStatus", carportStatus);
	        sql+=" and du.carportStatus like:carportStatus";
	        sqlCount+=" and du.carportStatus like:carportStatus";
	    }
	    if(licensePlateNo!=null&&!"".equals(licensePlateNo)){
	        map.put("licensePlateNo", "%"+licensePlateNo+"%");
	        sql+=" and du.licensePlateNo like:licensePlateNo";
	        sqlCount+=" and du.licensePlateNo like:licensePlateNo";
	    }
	    
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<ParkingArea> queryParkingAreaByJdbc(Map<String,String> param){
		Session session = this.getSessionFactory().openSession();
		
		String buildId = param.get("buildId")!=null?param.get("buildId"):"";
		String areaId = param.get("areaId")!=null?param.get("areaId"):"";
		
		StringBuffer sqlStr = new StringBuffer();
		if(buildId!=null&&!"".equals(buildId)){
			sqlStr.append(" select * from t_parking_area p where p.belong_com_id in ");
			sqlStr.append(" (select h.community_id from t_area_property h where h.belong_build_id = '"+buildId+"')");
		}
		if(areaId!=null&&!"".equals(areaId)){
			sqlStr.append(" select * from t_parking_area p where p.belong_com_id ='"+areaId+"' ");
		}
		Collection<ParkingArea> allAreas = session.createSQLQuery(sqlStr.toString()).addEntity(ParkingArea.class).list();
		session.close();
		return allAreas;
	}
	
	
	public void saveParking(Collection<Parking> parkings) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (Parking parking : parkings) {
	            EntityState state=EntityUtils.getState(parking);
	            if (state.equals(EntityState.NEW)) {
	                session.save(parking);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(parking);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(parking);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
		
	}

	public void queryParkingByParam(Page<VParking> page,
			Map<String, Object> parameter, String type) {
		String hql = " from "+VParking.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where state='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}		
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VParking.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.build_name like:build_name "+
							 " or a.community_name like:community_name" +
							 " or a.park_name like:park_name" +
							 " or a.ownerName like:ownerName" +
							 " or a.carport_status_name like:carport_status_name" +
							 " or a.ownerPhone like:ownerPhone";
				sqlCount += " and b.build_name like:build_name "+
						    " or b.community_name like:community_name" +
						    " or b.park_name like:park_name" +
						    " or b.ownerName like:ownerName" +
						    " or b.carport_status_name like:carport_status_name" +
						    " or b.phone like:phone";
				map.put("build_name", "%"+search+"%");
				map.put("community_name", "%"+search+"%");
				map.put("park_name", "%"+search+"%");
				map.put("ownerName", "%"+search+"%");
				map.put("carport_status_name", "%"+search+"%");
				map.put("phone", "%"+search+"%");
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

	public Parking getParkingById(String carportId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carportId", carportId);

		List<Parking> lists = this
				.query("from " + Parking.class.getName()
						+ " where carportId='" + carportId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public VParking getVParkingById(String carportId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carportId", carportId);

		List<VParking> lists = this
				.query("from " + VParking.class.getName()
						+ " where carportId='" + carportId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public String addParking(Parking parking) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(parking);
		session.flush();
		session.close();
		session = null;
		return parking.getCarportId();
	}

	public void deleteParking(String carportId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = carportId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			Parking parking = getParkingById(ids[i]);
			if(null != parking){
				session.delete(parking);
			}
		}
		session.flush();
		session.close();
	}
	
	public void addParkingOwner(ParkingOwner po){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(po);
		session.flush();
		session.close();
		session = null;
		
	}
	
	public void deleteParkingOwner(String carportId){
		Session session = this.getSessionFactory().openSession();
		String[] ids = carportId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			ParkingOwner po = getParkingOwnerById(ids[i]);
			if(null != po){
				session.delete(po);
			}
		}
		session.flush();
		session.close();
	}
	
	public ParkingOwner getParkingOwnerById(String carportId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carportId", carportId);

		List<ParkingOwner> lists = this
				.query("from " + ParkingOwner.class.getName()
						+ " where carportId='" + carportId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

}
