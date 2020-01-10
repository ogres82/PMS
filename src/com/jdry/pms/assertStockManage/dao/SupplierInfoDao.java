package com.jdry.pms.assertStockManage.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.jdry.pms.assertStockManage.pojo.SupplieInfo;

/**
 * 供应商操作持久层
 * @author hezuping
 *
 */
@Repository
@Transactional
public class SupplierInfoDao extends HibernateDao
{

	/**
	 * 查询供应商信息
	 * @param page
	 * @param parameter
	 * @param criteria
	 */
 public List querySupplierInfoByProperty(Page<SupplieInfo> page,Map<String, Object> parameter, Criteria criteria)
 {
	 

		String hql = " from "+SupplieInfo.class.getName();		
		String where =" where 1=1 ";
			
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+SupplieInfo.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.suppliy_code like:suppliy_code "+
							 " or a.suppliy_name like:suppliy_name" +
							 " or a.link_name like:link_name";
				sqlCount += " and b.suppliy_code like:suppliy_code "+
						    " or b.suppliy_name like:suppliy_name" +
						    " or b.link_name like:link_name";
				map.put("suppliy_code", "%"+search+"%");
				map.put("suppliy_name", "%"+search+"%");
				map.put("link_name", "%"+search+"%");
			}
		}		
		hql += whereCase;				
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	 
	 
	/* if(parameter == null)
	 {
		parameter = new HashMap<String,Object>();
     }
	  String suppliy_code = parameter.get("suppliy_code")!=null?parameter.get("suppliy_code").toString():"";
	  String suppliy_name = parameter.get("suppliy_name")!=null?parameter.get("suppliy_name").toString():"";
	  String link_name= parameter.get("link_name")!=null?parameter.get("link_name").toString():"";
	  String link_phone= parameter.get("link_phone")!=null?parameter.get("link_phone").toString():"";
	  String sql="from "+SupplieInfo.class.getName()+" sup  where 1=1";
	//  String sqlCount="select count(*) from "+SupplieInfo.class.getName()+" sup where 1=1";
	  Map<String,Object> map =new HashMap<String,Object>();
	  if(suppliy_code!=null&&!"".equals(suppliy_code))
		 {
			 sql+=" and sup.suppliy_code like "+"'%"+suppliy_code+"%'";
		   //  sqlCount+=" and sup.suppliy_code like "+"'%"+suppliy_code+"%'";
		 }
	  if(suppliy_name!=null&&!"".equals(suppliy_name))
		 {
			 sql+=" and sup.suppliy_name like "+"'%"+suppliy_name+"%'";
		    // sqlCount+=" and sup.suppliy_name like "+"'%"+suppliy_name+"%'";
		 }
	  if(link_name!=null&&!"".equals(link_name))
		 {
			 sql+=" and sup.link_name like "+"'%"+link_name+"%'";
		   //  sqlCount+=" and sup.link_name like "+"'%"+link_name+"%'";
		 }
	  if(link_phone!=null&&!"".equals(link_phone))
		 {
			 sql+=" and sup.link_phone like "+"'%"+link_phone+"%'";
		    // sqlCount+=" and sup.link_phone like "+"'%"+link_phone+"%'";
		 }
	  try{
		return this.query(sql, map);
		}catch (Exception e) 
		{
			e.printStackTrace();
		}*/
	return null;
 }
	
 /**
  * 供应商信息编辑，添加，删除操作
  * @param supplieInfos
  */
 public void saveOrUpdateSupplierInfo(Collection<SupplieInfo> supplieInfos)
 {
	 Session session = this.getSessionFactory().openSession();
	    try{
	        for (SupplieInfo supp : supplieInfos) {
	            EntityState state=EntityUtils.getState(supp);
	            if (state.equals(EntityState.NEW)) {
	            	String type_name = supp.getSuppliy_code()!=null?supp.getSuppliy_code().toString():"";
	            	boolean flag=type_name.equals("");
	            	if(!flag){
	                 session.save(supp);
	            	}
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(supp);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(supp);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		 
	 
 }

public SupplieInfo getSupplieInfo(Map<String, Object> parameter)
{
	if(parameter == null){
		parameter = new HashMap<String,Object>();
	}
	String suppliy_code = parameter.get("suppliy_code") != null?parameter.get("suppliy_code").toString():"";
	Map<String,Object> map = new HashMap<String,Object>();
	String sql = "from "+SupplieInfo.class.getName()+" du where 1=1 ";
	if(suppliy_code!=null&&!"".equals(suppliy_code)){
		map.put("suppliy_code", suppliy_code);
		sql += " and du.suppliy_code =:suppliy_code ";
	}
	return (SupplieInfo) this.query(sql, map).get(0);
  }

	public boolean save(SupplieInfo sup) 
	{
		Session session = this.getSessionFactory().openSession();
		try{
		session.saveOrUpdate(sup);
		}
		finally{
	        session.flush();
	        session.close();
	    }
		return true;
	}

	public boolean deleteSupplieInfoById(String suppliy_code) throws SQLException 
	{
		 boolean flag=false;
		 Session session = this.getSessionFactory().openSession();
		 Connection conn=session.connection();
		 String sql="delete from t_supplieinfo  where suppliy_code=?";
		 PreparedStatement ps = conn.prepareStatement(sql);
		 ps.setString(1, suppliy_code);
		 session.beginTransaction();
		 conn.setAutoCommit(false);
		 int a=ps.executeUpdate();
		 if(a>0)
		 {
			 flag=true; 
		 }else
		 {
			 flag=false; 
		 }
		 conn.commit();
		 conn.close();
		 ps.close();
		return flag;
	}

	public Collection<SupplieInfo> querySulierInfos() {
		String hql = "select suppliy_code,suppliy_name from "+SupplieInfo.class.getName();	
		List<SupplieInfo> list = this.query(hql);
		return list;
	}
	
}
