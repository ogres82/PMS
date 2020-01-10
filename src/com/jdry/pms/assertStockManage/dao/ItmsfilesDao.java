package com.jdry.pms.assertStockManage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.pojo.Titmsfiles;
import com.jdry.pms.comm.util.CommUtil;
@Repository
@Transactional
public class ItmsfilesDao  extends HibernateDao
{
	@Resource
	CommUtil commUtil;
	
	public List quaryItmsfilesInfo(Page<Titmsfiles> page,Map<String, Object> parameter, Criteria criteria)
	{
		
		 if(parameter == null)
		 {
			parameter = new HashMap<String,Object>();
	     }
		 String item_code = parameter.get("item_code")!=null?parameter.get("item_code").toString():"";
		 String bar_code = parameter.get("bar_code")!=null?parameter.get("bar_code").toString():"";
		 String item_name = parameter.get("item_name")!=null?parameter.get("item_name").toString():"";
		 String item_type = parameter.get("item_type")!=null?parameter.get("item_type").toString():"";
		 String item_unit = parameter.get("item_unit")!=null?parameter.get("item_unit").toString():"";
		 String item_flag = parameter.get("item_flag")!=null?parameter.get("item_flag").toString():"";
		
		
		 
		 
     String sql="select * from (select itms.*, aa.suppliy_num, sup.type_name " +
		" from t_itmsfiles itms " +
		"left join (select * from (select a.item_id, a.item_name, a.suppliy_num" +
		" from (select suppliy_code,t.voucher_id,t.item_id,t.item_name,t.unit_price," +
		" sum(t.unit_price * t.suppliy_num) as sum_price,sum(t.suppliy_num) as suppliy_num" +
		" from t_instock_his t group by t.item_id) as a " +
		" left join t_itmsfiles it on a.item_id = it.item_id " +
		" left join t_supplytype sup on a.suppliy_code = sup.supply_code " +
		" left join t_itmsfiles itm on a.item_id = itm.item_id " +
		" left join t_voucher vo on a.voucher_id = vo.voucher_code) cc where 1 = 1) aa " +
		" on itms.item_id = aa.item_id left join t_supplytype sup " +
		"on itms.item_type = sup.supply_code) its where 1 = 1";
		 
		 if(item_code!=null&&!"".equals(item_code))
		 {
			 sql+=" and its.item_code like "+"'%"+item_code+"%'";
		 }
		 if(bar_code!=null&&!"".equals(bar_code))
		 {
			 sql+=" and its.bar_code like "+"'%"+bar_code+"%'";
		 }
		 if(item_name!=null&&!"".equals(item_name))
		 {
			 sql+=" and its.item_name like "+"'%"+item_name+"%'";
		 }
		 
		 if(item_type!=null&&!"".equals(item_type))
		 {
			 sql+=" and its.item_type like "+"'%"+item_type+"%'";
		 }
		 if(item_unit!=null&&!"".equals(item_unit))
		 {
			 sql+=" and its.item_unit like "+"'%"+item_unit+"%'";
		 }
		 if(item_flag!=null&&!"".equals(item_flag))
		 {
			 sql+=" and its.item_flag ='"+item_flag+"'";
		 }
		 if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
				if(!search.equals("")){
			 sql+=" and its.item_code like"+"'%"+search+"%'"+
					 " or its.bar_code like"+"'%"+search+"%'"+
					 " or its.item_name like"+"'%"+search+"%'"; 
				}
		 }
		 
		 Map<String,Object> map =new HashMap<String,Object>();
		 StringBuffer sqlStr=new StringBuffer();
		 sqlStr.append(sql);
		 sqlStr.append(" LIMIT "+page.getFirstEntityIndex()+", "+page.getLastEntityIndex());
		 page.setEntityCount(getCount(sql));
		 return this.getSessionFactory().getCurrentSession().createSQLQuery(sqlStr.toString()).list();

				 
	}
	
	public int getCount(String sql){
		StringBuffer sqlStr=new StringBuffer();
		sqlStr.append("select count(*) from(");
		sqlStr.append(sql);
		sqlStr.append(") aa");
		int count= Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sqlStr.toString()).uniqueResult()+"");
		return count;
	}
	
	public String  saveItmsfilesInfo(Collection<Titmsfiles> itemfiles)
	{
		String flag="保存失败";
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (Titmsfiles item : itemfiles) {
	            EntityState state=EntityUtils.getState(item);
	            if(item.getItem_id().equals("")){
	           
	            	String item_code = commUtil.getBusinessId("WPDA","D"); 
	            	item.setItem_code(item_code);
	                 session.save(item);
	                 flag= "保存成功";
	             }else
	            {
	            	 session.update(item);
		             flag= "修改成功";
	            	
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }
		return flag;		 
	 	
		
	}

	public Titmsfiles getItmsFiles(Map<String, Object> parameter)
	{
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String item_id = parameter.get("item_id") != null?parameter.get("item_id").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+Titmsfiles.class.getName()+" du where 1=1 ";
		if(item_id!=null&&!"".equals(item_id)){
			map.put("item_id", item_id);
			sql += " and du.item_id =:item_id ";
		}
		return (Titmsfiles) this.query(sql, map).get(0);
	}

	public Collection<SupplieType> getDirectoryLikeCode(String code)
	{
		String sql="from "+SupplieType.class.getName()+" di";
	
	    if(code!=null&&!"".equals(code)){
	        sql+=" where di.parent_supp_id ='"+code+"'";
	    }
	    return this.query(sql);
	}

	public Titmsfiles findItmsFilesByCode(Map parameter)
	{
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String bar_code = parameter.get("bar_code") != null?parameter.get("bar_code").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+Titmsfiles.class.getName()+" du where 1=1 ";
		if(bar_code!=null&&!"".equals(bar_code)){
			map.put("bar_code", bar_code);
			sql += " and du.bar_code =:bar_code ";
		}
		return (Titmsfiles) this.query(sql, map).get(0);
	}

	public boolean deleteItmsFilesInfoById(String bar_code) throws SQLException
	{
		 boolean flag=false;
		 Session session = this.getSessionFactory().openSession();
		 Connection conn=session.connection();
		 String sql="delete from t_itmsfiles  where bar_code=?";
		 PreparedStatement ps = conn.prepareStatement(sql);
		 ps.setString(1, bar_code);
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
}
