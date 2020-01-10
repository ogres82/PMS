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
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
/**
 * 物资类别操作持久层
 * @author hezuping
 * 时间：2016-01-06
 */
@Repository
@Transactional
public class SupplieTypeDao extends HibernateDao{
	/**
	 * 查询物资类别信息
	 * @param page
	 * @param parameter
	 * @param criteria
	 */
	@SuppressWarnings("unused")
	public List querySupplieTypeInfo(Page<SupplieType> page,Map<String, Object> parameter, Criteria criteria)
	{
		
		String hql = " from "+SupplieType.class.getName();		
		String where =" where 1=1 ";
			
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+SupplieType.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.type_name like:type_name "+
							 " or a.supply_code like:supply_code" +
							 " or a.type_orther like:type_orther";
				sqlCount += " and b.type_name like:type_name "+
						    " or b.supply_code like:supply_code" +
						    " or b.type_orther like:type_orther";
				map.put("type_name", "%"+search+"%");
				map.put("supply_code", "%"+search+"%");
				map.put("type_orther", "%"+search+"%");
			}
		}		
		hql += whereCase;				
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
		
		
		
		
		
		
		
		
		
		/*if(parameter == null){
		   parameter = new HashMap<String,Object>();
		  }
		  String type_name = parameter.get("type_name")!=null?parameter.get("type_name").toString():"";
		  String supply_code = parameter.get("supply_code")!=null?parameter.get("supply_code").toString():"";
		  String sql="from "+SupplieType.class.getName()+" sup  where 1=1";
		 // String sqlCount="select count(*) from "+SupplieType.class.getName()+" sup where 1=1";
		  Map<String,Object> map =new HashMap<String,Object>();
		  if(type_name!=null&&!"".equals(type_name)){
		        sql+=" and sup.type_name like "+"'%"+type_name+"%'";
		        //sqlCount+=" and sup.type_name like "+"'%"+type_name+"%'";
		    }
		  if(supply_code!=null&&!"".equals(supply_code)){
		        sql+=" and sup.supply_code like"+"'%"+supply_code+"%'";
		       // sqlCount+=" and sup.supply_code like"+"'%"+supply_code+"%'";
		    }
		  try {
			  return this.query(sql,map);
			//this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;*/
	}
	
	/**
	 * 
	 * 物资类别信息的操作
	 * 
	 * @param supplieTypes
	 */
	public String saveSupplieType(SupplieType supp,String state1)
	{
		Session session = this.getSessionFactory().openSession();
		String flagtype="0";
	    try{
	       // for (SupplieType supp : supplieTypes) {
	        	EntityState state=EntityUtils.getState(supp);
	            if (supp.getSupplytype_id().equals("")) {
	            	String type_name = supp.getType_name()!=null?supp.getType_name().toString():"";
	            	boolean flag=type_name.equals("");
	            	if(!flag){
	                 session.save(supp);
	                 flagtype="1";
	                 return flagtype;
	            	}
	            } else if (!supp.getSupplytype_id().equals("")) {
	                session.update(supp);
	                flagtype="1";
	                 return flagtype;
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(supp);
	                return flagtype;
	            }
	        //}
	    }finally{
	        session.flush();
	        session.close();
	    }
		return state1;		
		
		
	}

	public SupplieType getSupplieTypeInfo(Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String supply_code = parameter.get("supply_code") != null?parameter.get("supply_code").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+SupplieType.class.getName()+" du where 1=1 ";
		if(supply_code!=null&&!"".equals(supply_code)){
			map.put("supply_code", supply_code);
			sql += " and du.supply_code =:supply_code ";
		}
		return (SupplieType) this.query(sql, map).get(0);
	}

	public boolean deleteSupplieTypeById(String id) throws SQLException {
		 boolean flag=false;
		 Session session = this.getSessionFactory().openSession();
		 Connection conn=session.connection();
		 String sql="delete from t_supplytype  where supply_code=?";
		 PreparedStatement ps = conn.prepareStatement(sql);
		 ps.setString(1, id);
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

	public Collection<SupplieType> querySuppTypeList()
	{
		
		String hql = " from "+SupplieType.class.getName();	
		List<SupplieType> list = this.query(hql);
		return list;
	}

	public Collection<DirDirectoryDetail> getDirectoryLikeCode(String code) {

	    String sql="from "+DirDirectoryDetail.class.getName() +" di";
	    if(code!=null&&!"".equals(code)){
	        sql+=" where di.code like '"+code+"%' order by code";
	    }
	    return this.query(sql);
	}
	

}
