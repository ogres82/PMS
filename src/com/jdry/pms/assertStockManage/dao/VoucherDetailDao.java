package com.jdry.pms.assertStockManage.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

@Repository
@Transactional
public class VoucherDetailDao extends HibernateDao 
{
	public List findVoucherDetailInfo(Map<String, Object> parameter, Page page,Criteria criteria)
	{
		 if(parameter == null)
		 {
			parameter = new HashMap<String,Object>();
	     }
		 String voucher_code = parameter.get("voucher_code")!=null?parameter.get("voucher_code").toString():"";
		 String warehouse_name = parameter.get("warehouse_name")!=null?parameter.get("warehouse_name").toString():"";
		 String suppliy_code = parameter.get("suppliy_code")!=null?parameter.get("suppliy_code").toString():"";
		 String t_handler = parameter.get("t_handler")!=null?parameter.get("t_handler").toString():"";
		 String occurren_date = parameter.get("occurren_date")!=null?parameter.get("occurren_date").toString():"";
		 SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
		 Date date=null;
		 try{
		     date = sdf.parse(occurren_date);
		 }catch(Exception e){}
		 
		 SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		 String occurrenDate="";
		 if(date!=null){
		  occurrenDate=sp.format(date);
		 }
		 
		 String instok_type=parameter.get("instok_type")!=null?parameter.get("instok_type").toString():"";
		 
		 /*String sql="select * from(select t.*,sup.suppliy_name,d.code_detail_name,w.warehouse_name from t_voucher t  left join t_supplieinfo sup on t.suppliy_code=sup.suppliy_code" +
		 		"  left join  dir_directorydetail d on t.instok_type=d.code_detail  left join t_warehouse  w on t.owne_stock=w.warehouse_code and d.`code`='instok_type')  vo where 1=1";*/
		
		 String sql="select * from (select vo1.*,sup.suppliy_name,w.warehouse_name from " +
		 		" (select t.*,d.code_detail_name from t_voucher t,dir_directorydetail d  where t.instok_type=d.code_detail AND d.`code` = 'instok_type')vo1" +
		 		" left join t_supplieinfo sup on vo1.suppliy_code=sup.suppliy_code" +
		 		" LEFT JOIN t_warehouse w ON vo1.owne_stock = w.warehouse_code" +
		 		" )vo where 1=1";
		 
		 if(voucher_code!=null&&!"".equals(voucher_code))
		 {
			 sql+=" and vo.voucher_code like "+"'%"+voucher_code+"%'";
		 }
		 if(warehouse_name!=null&&!"".equals(warehouse_name))
		 {
			 sql+=" and vo.warehouse_name like "+"'%"+warehouse_name+"%'";
		 }
		 if(suppliy_code!=null&&!"".equals(suppliy_code))
		 {
			 sql+=" and vo.suppliy_code like "+"'%"+suppliy_code+"%'";
		 }
		 if(t_handler!=null&&!"".equals(t_handler))
		 {
			 sql+=" and vo.t_handler like "+"'%"+t_handler+"%'";
		 }
		 if(occurrenDate!=null&&!"".equals(occurrenDate))
		 {
			 sql+=" and vo.occurren_date='"+occurrenDate+"'";
		 }
		 if(instok_type!=null&&!"".equals(instok_type))
		 {
			 sql+=" and vo.instok_type ='"+instok_type+"'";
		 }
		 if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
				if(!search.equals("")){
			      sql+=" and vo.voucher_code like"+"'%"+search+"%'"+
					 " or vo.warehouse_name like"+"'%"+search+"%'"+
					 " or vo.suppliy_code like"+"'%"+search+"%'"+
					 " or vo.t_handler like"+"'%"+search+"%'"+
					 " or vo.code_detail_name like"+"'%"+search+"%'";
				}
		 }
		 sql+=" order by  vo.occurren_date desc";
		 StringBuffer sqlStr=new StringBuffer();
		 Map<String,Object> map =new HashMap<String,Object>();
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

	public Object quaryVoucherInfo(Map map) 
	{
		String sql="SELECT * FROM(SELECT dd.*, sup.suppliy_name,w.warehouse_name FROM" +
				" (SELECT t.*, d.code_detail_name FROM t_voucher t,dir_directorydetail d" +
				" WHERE t.instok_type = d.code_detail AND d.`code` = 'instok_type') dd" +
				" LEFT JOIN t_supplieinfo sup ON dd.suppliy_code = sup.suppliy_code" +
				" LEFT JOIN t_warehouse w ON dd.owne_stock = w.warehouse_code ) vo " +
				" WHERE 1 = 1 AND vo.voucher_code = '"+map.get("voucher_code")+"' ORDER BY	vo.occurren_date DESC";
		
		return this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).uniqueResult();
	}

}
