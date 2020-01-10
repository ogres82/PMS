package com.jdry.pms.environment.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.environment.pojo.Bdf2User;
import com.jdry.pms.environment.pojo.EnvironmentArea;
import com.jdry.pms.environment.pojo.EnvironmentAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlant;
import com.jdry.pms.environment.pojo.VAreaPlantNum;

@Repository
@Transactional
public class AreaPlantDao extends HibernateDao{

	public void queryVAreaPlantByParam(Page<VAreaPlant> page,
			Map<String, Object> parameter, String type) {
		
		String hql = " from "+VAreaPlant.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VAreaPlant.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			String areaId = parameter.get("areaId")==null?"":parameter.get("areaId").toString();
			if(!areaId.equals("")){
				whereCase += " and a.areaId =:areaId ";
				sqlCount += " and b.areaId =:areaId ";
				map.put("areaId", areaId);
			}
			if(!search.equals("")){
				whereCase += " and ( a.areaName like:areaName "+
							 " or a.areaResPersonName like:areaResPersonName )";
				sqlCount += " and ( b.areaName like:areaName "+
							 " or b.areaResPersonName like:areaResPersonName)";
						    
				map.put("areaName", "%"+search+"%");
				map.put("areaResPersonName", "%"+search+"%");
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

	public VAreaPlant getAreaPlantById(String areaId) {
		List<VAreaPlant> lists = this
				.query("from " + VAreaPlant.class.getName()
						+ " where areaId='" + areaId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public EnvironmentArea getAreaById(String areaId) {
		List<EnvironmentArea> lists = this
				.query("from " + EnvironmentArea.class.getName()
						+ " where areaId='" + areaId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public String addArea(EnvironmentArea area) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(area);
		session.flush();
		session.close();
		return area.getAreaId();
	}

	public void deleteArea(String areaId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = areaId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			EnvironmentArea plant = getAreaById(ids[i]);
			if(null != plant){
				session.delete(plant);
			}
		}
		session.flush();
		session.close();
	}

	public void addAreaPlant(EnvironmentAreaPlant area) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(area);
		session.flush();
		session.close();
	}

	public boolean checkUnique(Map<String, Object> param) {
		String areaId = param.get("areaId")==null?"":param.get("areaId").toString();
		String plantId = param.get("plantId")==null?"":param.get("plantId").toString();
		
		List<EnvironmentAreaPlant> lists = this
				.query("from " + EnvironmentAreaPlant.class.getName()
						+ " where areaId='" + areaId + "' and plantId ='"+plantId+"'");
		if (null != lists && lists.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public Collection<EnvironmentArea> queryAreaByParam(
			Map<String, Object> param) {
		String areaId = param.get("areaId")==null?"":param.get("areaId").toString();
		
		String sql  = "from " + EnvironmentArea.class.getName()+ " where 1=1 ";
		if(areaId!=null && !areaId.isEmpty()){
			sql += " and areaId = '"+areaId+"' ";
		}
		List<EnvironmentArea> lists = this.query(sql);
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public void queryVAreaPlantNumByParam(Page<VAreaPlantNum> page,
			Map<String, Object> parameter) {
		String hql = " from "+VAreaPlantNum.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VAreaPlantNum.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			String areaId = parameter.get("areaId")==null?"":parameter.get("areaId").toString();
			whereCase += " and a.areaId =:areaId ";
			sqlCount += " and b.areaId =:areaId ";
			map.put("areaId", areaId);
			if(!search.equals("")){
				whereCase += " and ( a.areaName like:areaName "+
							 " or a.areaResPersonName like:areaResPersonName )";
				sqlCount += " and ( b.areaName like:areaName "+
							 " or b.areaResPersonName like:areaResPersonName)";
						    
				map.put("areaName", "%"+search+"%");
				map.put("areaResPersonName", "%"+search+"%");
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

	public List<Bdf2User> queryBdf2UserByParam(Map<String, Object> param) {
		String keyword = param.get("keyword")!=null?param.get("keyword").toString():"";
		String search = param.get("search")!=null?param.get("search").toString():"";

		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+Bdf2User.class.getName()+" du where 1=1";
	    
	    if(keyword!=null&&!"".equals(keyword)){
	    	map.put("username", "%"+keyword+"%");
	    	sql+=" and ( du.username like:username";
	    	map.put("cname", "%"+keyword+"%");
	    	sql+=" or du.cname like:cname ";
	    	map.put("deptName", "%"+keyword+"%");
	    	sql+=" or du.deptName like:deptName ";
	    	map.put("positionName", "%"+keyword+"%");
	    	sql+=" or du.positionName like:positionName ) ";
	    }
	    
	    if(search!=null&&!"".equals(search)){
	    	map.put("username", "%"+search+"%");
	    	sql+=" and ( du.username like:username";
	    }
	    
	    System.out.println("查询sql:"+sql);
		return  this.query(sql, map);
	}

	public VAreaPlant getAreaByGisId(String areaGisId) {
		List<VAreaPlant> areas = this.query("from VAreaPlant where areaGisId = '"+areaGisId+"'");
		if(areas.size()>0){
			return areas.get(0);
		}else{
			return null;
		}
	}

	public List<VEmp> queryEmpByKeyword(Map<String, Object> param) {
		String hql = " from "+VEmp.class.getName();		
		String whereCase = " a where 1=1 ";
		Map<String,Object> map =new HashMap<String,Object>();		
		if(param != null){
			String search = param.get("keyword")==null?"":param.get("keyword").toString();
			String depart = param.get("depart")==null?"":param.get("depart").toString();
			if(!search.equals("")){
				whereCase += " and ( a.empNo like:empNo "+
							 " or a.empName like:empName" +
							 " or a.empDeptName like:empDeptName" +
							 " or a.empPostName like:empPostName)";
						    
				map.put("empNo", "%"+search+"%");
				map.put("empName", "%"+search+"%");
				map.put("empDeptName", "%"+search+"%");
				map.put("empPostName", "%"+search+"%");
			}
			if(!depart.isEmpty()){
				whereCase += " and a.empDeptId ='007'";
			}
		}		
		hql += whereCase;				
		System.out.println("hql==="+hql);
		
		return this.query(hql,map);
	}

	public void deleteAreaPlant(String areaId) {
		Session session = this.getSessionFactory().openSession();
		List<EnvironmentAreaPlant> areaPlants = this.getAreaPlantList(areaId);
		for(int i=0;i<areaPlants.size();i++){
			session.delete(areaPlants.get(i));
		}
		session.flush();
		session.close();
	}

	private List<EnvironmentAreaPlant> getAreaPlantList(String areaId) {
		return this.query("from EnvironmentAreaPlant where areaId = '"+areaId+"'");
	}

}
