package com.jdry.pms.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.AllAreaDao;

@Repository
@Transactional
public class RoleMantainDao extends HibernateDao{
	static Logger log = Logger.getLogger(AllAreaDao.class);
	
	public void queryRoles(Page<Role> page, Map<String, Object> parameter,
			String type) {

		String hql = " from "+Role.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where type='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}		
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+Role.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.name like:name "+
							 " or a.desc like:desc";
				sqlCount += " and b.name like:name "+
						    " or b.desc like:desc";
				map.put("name", "%"+search+"%");
				map.put("desc", "%"+search+"%");
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

	public Role getRoleById(String roleId) {

		List<Role> lists = this
				.query("from " + Role.class.getName()
						+ " where id='" + roleId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addRole(Role role) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(role);
		session.flush();
		session.close();
		log.info("新增或编辑角色 "+role.getId());
	}

	public void deleteRole(String roleId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = roleId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			Role role = getRoleById(ids[i]);
			if(null != role){
				session.delete(role);
				log.info("删除角色 "+role.getId());
			}
		}
		session.flush();
		session.close();
	}
	
	//提供给用户管理界面使用 rows获取数据条数，获取全部rows为0
	public List<Role> getRoles(int rows){
		String hql = "FROM Role WHERE 1=1"; 
		Session session = this.getSessionFactory().openSession();
	    Query query = session.createQuery(hql);     
	    if(rows>0){
		    query.setMaxResults(rows);
	    }  
	    List<Role> list = query.list();  
	    session.close();  
	    return list;  
	}

}
