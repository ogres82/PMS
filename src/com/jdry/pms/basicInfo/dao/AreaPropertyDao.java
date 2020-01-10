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
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.VAreaProperty;

@Repository
@Transactional
public class AreaPropertyDao extends HibernateDao{

	public void query(Page<AreaProperty> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String communityName = parameter.get("communityName")!=null?parameter.get("communityName").toString():"";
		String belongBuildId = parameter.get("belongBuildId")!=null?parameter.get("belongBuildId").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+AreaProperty.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+AreaProperty.class.getName()+" du where 1=1";
	    if(communityName!=null&&!"".equals(communityName)){
	        map.put("communityName", "%"+communityName+"%");
	        sql+=" and du.communityName like:communityName";
	        sqlCount+=" and du.communityName like:communityName";
	    }
	    if(belongBuildId!=null&&!"".equals(belongBuildId)){
	        map.put("belongBuildId", belongBuildId);
	        sql+=" and du.belongBuildId =:belongBuildId";
	        sqlCount+=" and du.belongBuildId =:belongBuildId";
	    }
	    
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void saveAreaProperty(Collection<AreaProperty> areaPropertys) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (AreaProperty ap : areaPropertys) {
	            EntityState state=EntityUtils.getState(ap);
	            if (state.equals(EntityState.NEW)) {
	                session.save(ap);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(ap);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(ap);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
		
	}

	public List<AllArea> getAllAreaId(Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String id = parameter.get("id") != null?parameter.get("id").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+AllArea.class.getName()+" du where 1=1";
		if(id!=null&&!"".equals(id)){
			map.put("id", id);
			sql += " and du.id =:id ";
		}
		return this.query(sql, map);
	}

	public Collection<AreaProperty> queryAreaPropertyByParam(
			Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String belongBuildId = parameter.get("buildId")!=null?parameter.get("buildId").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+AreaProperty.class.getName()+" du where 1=1";
	    if(belongBuildId!=null&&!"".equals(belongBuildId)){
	        map.put("belongBuildId", belongBuildId);
	        sql+=" and du.belongBuildId =:belongBuildId";
	    }
	   
		return  this.query(sql, map);
	}

	public Collection<AreaProperty> queryAreaPropertyByParent(String id) {
		String belongBuildId = id!=null?id:"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+AreaProperty.class.getName()+" du where 1=1";
	    if(belongBuildId!=null&&!"".equals(belongBuildId)){
	        map.put("belongBuildId", belongBuildId);
	        sql+=" and du.belongBuildId =:belongBuildId";
	    }
	   
		return  this.query(sql, map);
	}

	public void queryAreaPropertyByParam(Page<VAreaProperty> page,
			Map<String, Object> parameter, String type) {
		String hql = " from "+VAreaProperty.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where state='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}		
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VAreaProperty.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.buildName like:buildName "+
							 " or a.communityName like:communityName";
				sqlCount += " and b.buildName like:buildName "+
						    " or b.communityName like:communityName";
				map.put("buildName", "%"+search+"%");
				map.put("communityName", "%"+search+"%");
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

	public String addAreaProperty(AreaProperty areaProperty) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(areaProperty);
		session.flush();
		session.close();
		return areaProperty.getCommunityId();
	}

	public void deleteAreaProperty(String buildId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = buildId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			AreaProperty areaProperty = getAreaPropertyById(ids[i]);
			if(null != areaProperty){
				session.delete(areaProperty);
			}
		}
		session.flush();
		session.close();
	}

	public AreaProperty getAreaPropertyById(String communityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("communityId", communityId);

		List<AreaProperty> lists = this
				.query("from " + AreaProperty.class.getName()
						+ " where communityId='" + communityId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public AreaProperty getAreaPropertyByName(String communityName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("communityName", communityName);

		List<AreaProperty> lists = this
				.query("from " + AreaProperty.class.getName()
						+ " where communityName='" + communityName + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
}
