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
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingHousetype;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.VBuildingProperty;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;

@Repository
@Transactional
public class BuildingPropertyDao extends HibernateDao{

	public void query(Page<BuildingProperty> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String storiedBuildName = parameter.get("storiedBuildName")!=null?parameter.get("storiedBuildName").toString():"";
		String belongCommId = parameter.get("belongCommId")!=null?parameter.get("belongCommId").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+BuildingProperty.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+BuildingProperty.class.getName()+" du where 1=1";
	    if(storiedBuildName!=null&&!"".equals(storiedBuildName)){
	        map.put("storiedBuildName", "%"+storiedBuildName+"%");
	        sql+=" and du.storiedBuildName like:storiedBuildName";
	        sqlCount+=" and du.storiedBuildName like:storiedBuildName";
	    }
	    if(belongCommId!=null&&!"".equals(belongCommId)){
	        map.put("belongCommId", belongCommId);
	        sql+=" and du.belongCommId =:belongCommId";
	        sqlCount+=" and du.belongCommId =:belongCommId";
	    }
	    
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void saveBuildingProperty(Collection<BuildingProperty> builds) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (BuildingProperty build : builds) {
	            EntityState state=EntityUtils.getState(build);
	            if (state.equals(EntityState.NEW)) {
	                session.save(build);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(build);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(build);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
		
	}

	public List<AreaProperty> getCommId(Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String communityId = parameter.get("id") != null?parameter.get("id").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+AreaProperty.class.getName()+" du where 1=1";
		if(communityId!=null&&!"".equals(communityId)){
			map.put("communityId", communityId);
			sql += " and du.communityId =:communityId ";
		}
		return this.query(sql, map);
	}

	public Collection<BuildingProperty> queryBuildingPropertyByParam(
			Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String belongCommId = parameter.get("communityId")!=null?parameter.get("communityId").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+BuildingProperty.class.getName()+" du where 1=1";
	    if(belongCommId!=null&&!"".equals(belongCommId)){
	        map.put("belongCommId", belongCommId);
	        sql+=" and du.belongCommId =:belongCommId";
	    }
	   
		return  this.query(sql, map);
	}

	public Collection<BuildingProperty> queryBuildingPropertyByParent(String id) {
		
		String belongCommId = id!=null?id:"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+BuildingProperty.class.getName()+" du where 1=1";
	    if(belongCommId!=null&&!"".equals(belongCommId)){
	        map.put("belongCommId", belongCommId);
	        sql+=" and du.belongCommId =:belongCommId";
	    }
	   
		return  this.query(sql, map);
	}

	public void queryBuildingPropertyByParam(Page<VBuildingProperty> page,
			Map<String, Object> parameter, String type) {

		String hql = " from "+VBuildingProperty.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where state='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}		
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VBuildingProperty.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.community_name like:community_name "+
							 " or a.build_name like:build_name" +
							 " or a.storiedBuildName like:storiedBuildName";
				sqlCount += " and b.community_name like:community_name "+
						    " or b.build_name like:build_name" +
						    " or b.storiedBuildName like:storiedBuildName";
				map.put("community_name", "%"+search+"%");
				map.put("build_name", "%"+search+"%");
				map.put("storiedBuildName", "%"+search+"%");
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

	public BuildingProperty getBuildingPropertyById(String storiedBuildId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storiedBuildId", storiedBuildId);

		List<BuildingProperty> lists = this
				.query("from " + BuildingProperty.class.getName()
						+ " where storiedBuildId='" + storiedBuildId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	
	}
	
	public VBuildingProperty getVBuildingPropertyById(String storiedBuildId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storiedBuildId", storiedBuildId);

		List<VBuildingProperty> lists = this
				.query("from " + VBuildingProperty.class.getName()
						+ " where storiedBuildId='" + storiedBuildId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	
	}

	public String addBuildingProperty(BuildingProperty buildingProperty) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(buildingProperty);
		session.flush();
		session.close();
		return buildingProperty.getStoriedBuildId();
	}

	public void deleteBuildingProperty(String storiedBuildId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = storiedBuildId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			BuildingProperty buildingProperty = getBuildingPropertyById(ids[i]);
			if(null != buildingProperty){
				session.delete(buildingProperty);
			}
		}
		session.flush();
		session.close();
	}

	public BuildingProperty getBuildingPropertyByName(String storiedBuildName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storiedBuildName", storiedBuildName);

		List<BuildingProperty> lists = this
				.query("from " + BuildingProperty.class.getName()
						+ " where storiedBuildName='" + storiedBuildName + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public List<VBuildingProperty> queryBuildingByName(String buildingName){
		String hql = "from VBuildingProperty where storiedBuildName like '%"+buildingName+"%'";
		return this.query(hql);
	}
	public List<VHouseOwner> queryOwnerByBuild(String buildingId){
		String hql = "from VHouseOwner where belongSbId = '"+buildingId+"'";
		return this.query(hql);
	}

	public List<BuildingHousetype> queryHouseType() {
		return this.query("from BuildingHousetype");
	}
	
	public List queryBuildingImgById(String buildingId){
		String sql = "SELECT b.type_name,a.img_name,a.img_url from t_building_img a LEFT JOIN t_building_housetype b ON a.type_id=b.type_id " +
					"LEFT JOIN t_building_property c ON b.type_id=c.house_type_id WHERE c.storied_build_id = '"+buildingId+"'";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
}
