package com.jdry.pms.assertStockManage.dao;

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
public class InstockInformationDao extends HibernateDao{
	
	public List getInsockInfomation(Map<String, Object> parameter, Page page,Criteria criteria)
	{
		String sql="";
		if(parameter == null)
		 {
			parameter = new HashMap<String,Object>();
	     }

		 String item_code = parameter.get("item_code")!=null?parameter.get("item_code").toString():"";
		 String item_name = parameter.get("item_name")!=null?parameter.get("item_name").toString():"";
		 String suppliy_code = parameter.get("suppliy_code")!=null?parameter.get("suppliy_code").toString():"";
		//加分组
		 sql="select * from(select  sup.type_name,a.item_id,a.item_name,it.item_type,a.unit_price, a.sum_price,a.suppliy_num,vo.owne_stock, itm. bar_code,itm.item_unit,itm.item_flag,a.instock_id,waa.warehouse_name, di.code_detail_name,itm.stock_lowerlimit,itm.stock_uplimit from " +
				" (select suppliy_code, t.voucher_id,t.item_id,t.item_name,t.unit_price,sum(t.unit_price*t.suppliy_num)  as sum_price,sum(t.suppliy_num)  as suppliy_num,t.instock_id, t.item_type from t_instock_his t  group by t.item_id  ) as a " +
				" left  join t_itmsfiles it on a.item_id=it.item_id " +
				" left join t_supplytype sup on a.item_type=sup.supply_code" +
				" left join t_itmsfiles itm on a.item_id=itm.item_id " +
				"left join t_voucher vo on a.voucher_id=vo.voucher_code " +
				" left join t_warehouse waa on vo.owne_stock=waa.warehouse_code " +
				"  left join dir_directorydetail di on itm.item_flag=di.code_detail and di.`code` like'%item_type%') cc  where 1=1";
		
		 
		 if(item_code!=null&&!"".equals(item_code))
		 {
			 sql+=" and cc.bar_code like "+"'%"+item_code+"%'";
		 }
		 if(item_name!=null&&!"".equals(item_name))
		 {
			 sql+=" and cc.item_name like "+"'%"+item_name+"%'";
		 }
		 if(suppliy_code!=null&&!"".equals(suppliy_code))
		 {
			 sql+="  and cc.item_flag like "+"'%"+suppliy_code+"%'";
		 }
		 if(parameter != null)
		 {//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
				if(!search.equals("")){
			 sql+=" and cc.bar_code like"+"'%"+search+"%'"+
					 " or cc.code_detail_name like"+"'%"+search+"%'"+
					 " or cc.item_name like"+"'%"+search+"%'"; 
			}
		 }
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

	public Object quaryInstockInfo(Map map)
	{
		String sql="SELECT * FROM(SELECT sup.type_name," +
				"a.item_id,a.item_name,it.item_type,a.unit_price," +
				"a.sum_price,a.suppliy_num,vo.owne_stock,itm.bar_code," +
				"itm.item_unit,itm.item_flag,a.instock_id,wa.warehouse_name,itm.item_Ptype,itm.stock_lowerlimit,itm.stock_uplimit " +
				"FROM(SELECT suppliy_code,t.voucher_id,t.item_id," +
				"t.item_name,t.unit_price,sum(t.unit_price * t.suppliy_num) AS sum_price," +
				"sum(t.suppliy_num) AS suppliy_num,t.instock_id " +
				"FROM t_instock_his t GROUP BY t.item_id ) AS a" +
				" LEFT JOIN t_itmsfiles it ON a.item_id = it.item_id " +
				" LEFT JOIN t_supplytype sup ON a.suppliy_code = sup.supply_code " +
				" LEFT JOIN t_itmsfiles itm ON a.item_id = itm.item_id " +
				" LEFT JOIN t_voucher vo ON a.voucher_id = vo.voucher_code " +
				" left join t_warehouse wa on vo.owne_stock=wa.warehouse_code) cc WHERE cc.instock_id='"+map.get("instock_id")+"'";
		     return this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).uniqueResult();
	}

}
