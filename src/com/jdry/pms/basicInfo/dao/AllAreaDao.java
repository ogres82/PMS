package com.jdry.pms.basicInfo.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.AllArea;

@Repository
@Transactional
public class AllAreaDao extends HibernateDao{

	static Logger log = Logger.getLogger(AllAreaDao.class);
	public void query(Page<AllArea> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String buildName = parameter.get("buildName")!=null?parameter.get("buildName").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+AllArea.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+AllArea.class.getName()+" du where 1=1";
	    if(buildName!=null&&!"".equals(buildName)){
	        map.put("buildName", "%"+buildName+"%");
	        sql+=" and du.buildName like:buildName";
	        sqlCount+=" and du.buildName like:buildName";
	    }
	    
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void saveAllArea(Collection<AllArea> areas) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (AllArea area : areas) {
	            EntityState state=EntityUtils.getState(area);
	            if (state.equals(EntityState.NEW)) {
	                session.save(area);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(area);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(area);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
		
	}

	public Collection<AllArea> queryAllAreaByParam(Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String buildId = parameter.get("buildId")!=null?parameter.get("buildId").toString():"";
	    String sql="from "+AllArea.class.getName()+" du where 1=1";
	    if(buildId!=null&&!"".equals(buildId)){
	        sql+=" and du.buildId =:buildId";
	    }
	   
		return  this.query(sql);
	}

	public void queryPropertyByParam(Page<AllArea> page,
			Map<String, Object> parameter, String type) {

		String hql = " from "+AllArea.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where state='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}		
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+AllArea.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.buildName like:buildName "+
							 " or a.remark like:remark";
				sqlCount += " and b.buildName like:buildName "+
						    " or b.remark like:remark";
				map.put("buildName", "%"+search+"%");
				map.put("remark", "%"+search+"%");
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

	public AllArea getAllAreaById(String buildId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buildId", buildId);

		List<AllArea> lists = this
				.query("from " + AllArea.class.getName()
						+ " where buildId='" + buildId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	
	}

	public String addAllArea(AllArea allArea) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(allArea);
		session.flush();
		session.close();
		log.info("新增或编辑楼盘 "+allArea.getBuildId());
		return allArea.getBuildId();
	}

	public void deleteAllArea(String buildId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = buildId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			AllArea allArea = getAllAreaById(ids[i]);
			if(null != allArea){
				session.delete(allArea);
				log.info("删除楼盘 "+allArea.getBuildId());
			}
		}
		session.flush();
		session.close();
		
	}

	public AllArea getAllAreaByName(String buildName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buildName", buildName);

		List<AllArea> lists = this
				.query("from " + AllArea.class.getName()
						+ " where buildName='" + buildName + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
}
