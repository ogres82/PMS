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
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.WareHouseFiles;
/**
 * 仓库档案信息
 * @author hezuping
 *
 */
@Repository
@Transactional
public class WareHouseFilesDao extends HibernateDao
{

	public void quaryWareHouseFilesInfo(Page<WareHouseFiles> page,Map<String, Object> parameter, Criteria criteria) 
	{
		
		
		String hql = " from "+WareHouseFiles.class.getName();		
		String where =" where 1=1 ";
			
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+WareHouseFiles.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.warehouse_code like:warehouse_code "+
							 " or a.warehouse_name like:warehouse_name" +
							 " or a.warehouse_address like:warehouse_address"+
							 " or a.link_man like:link_man"+
							 " or a.link_phone like:link_phone";
				sqlCount += " and b.warehouse_code like:warehouse_code "+
						    " or b.warehouse_name like:warehouse_name" +
						    " or b.link_man like:link_man"+
						    " or b.warehouse_address like:warehouse_address"+
						    " or b.link_phone like:link_phone";
				map.put("warehouse_code", "%"+search+"%");
				map.put("warehouse_name", "%"+search+"%");
				map.put("warehouse_address", "%"+search+"%");
				map.put("link_man", "%"+search+"%");
				map.put("link_phone", "%"+search+"%");
			}
		}		
		hql += whereCase;				
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*if(parameter == null)
		{
			parameter = new HashMap<String,Object>();
		}
		String warehouse_code=parameter.get("warehouse_code")!=null?parameter.get("warehouse_code").toString():"";
		String warehouse_name=parameter.get("warehouse_name")!=null?parameter.get("warehouse_name").toString():"";
		String warehouse_address=parameter.get("warehouse_address")!=null?parameter.get("warehouse_address").toString():"";
		String link_man=parameter.get("link_man")!=null?parameter.get("link_man").toString():"";
		String link_phone=parameter.get("link_phone")!=null?parameter.get("link_phone").toString():"";
		
		String sql="from "+WareHouseFiles.class.getName()+" ware  where 1=1";
		String sqlCount="select count(*) from "+WareHouseFiles.class.getName()+" ware where 1=1";
		Map<String,Object> map =new HashMap<String,Object>();
		if(warehouse_code!=null&&!"".equals(warehouse_code)){
	        sql+=" and ware.warehouse_code like "+"'%"+warehouse_code+"%'";
	        sqlCount+="and  ware.warehouse_code like "+"'%"+warehouse_code+"%'";
	    }
		
		if(warehouse_name!=null&&!"".equals(warehouse_name)){
	        sql+=" and ware.warehouse_name like "+"'%"+warehouse_name+"%'";
	        sqlCount+=" ware.warehouse_name like "+"'%"+warehouse_name+"%'";
	    }
		
		if(warehouse_address!=null&&!"".equals(warehouse_address)){
	        sql+=" and ware.warehouse_address like "+"'%"+warehouse_address+"%'";
	        sqlCount+=" and ware.warehouse_address like "+"'%"+warehouse_address+"%'";
	    }
		if(link_man!=null&&!"".equals(link_man)){
	        sql+=" and ware.link_man like "+"'%"+link_man+"%'";
	        sqlCount+=" and ware.link_man like "+"'%"+link_man+"%'";
	    }
		if(link_phone!=null&&!"".equals(link_phone)){
	        sql+=" and ware.link_phone like "+"'%"+link_phone+"%'";
	        sqlCount+="and  ware.link_phone like "+"'%"+link_phone+"%'";
	    }
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}*/
	}

	/**
	 * 操作仓库档案信息
	 * @param wareHouseFiles
	 */
	public void operationWareHouseFilesInfo(Collection<WareHouseFiles> wareHouseFiles)
	{
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (WareHouseFiles wareHouse : wareHouseFiles) {
	            if (wareHouse.getWarehouse_id().equals("")) {
	                 session.save(wareHouse);
	            } else if (!wareHouse.getWarehouse_id().equals("")) {
	                session.update(wareHouse);
	            } 
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		

	}

	public WareHouseFiles findWareHouseFilesByCode(Map<String, Object> parameter)
	{
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String warehouse_code = parameter.get("warehouse_code") != null?parameter.get("warehouse_code").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+WareHouseFiles.class.getName()+" du where 1=1 ";
		if(warehouse_code!=null&&!"".equals(warehouse_code)){
			map.put("warehouse_code", warehouse_code);
			sql += " and du.warehouse_code =:warehouse_code ";
		}
		return (WareHouseFiles) this.query(sql, map).get(0);
	}

	public boolean deleteSupplieInfoById(String warehouse_code) throws SQLException
	{
		 boolean flag=false;
		 Session session = this.getSessionFactory().openSession();
		 Connection conn=session.connection();
		 String sql="delete from t_warehouse  where warehouse_code=?";
		 PreparedStatement ps = conn.prepareStatement(sql);
		 ps.setString(1, warehouse_code);
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

	public Collection<WareHouseFiles> queryWarHouesInfos()
	{
		String hql = "select warehouse_code,warehouse_name from "+WareHouseFiles.class.getName();	
		List<WareHouseFiles> list = this.query(hql);
		return list;
	}

}
