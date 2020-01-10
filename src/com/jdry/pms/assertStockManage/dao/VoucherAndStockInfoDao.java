package com.jdry.pms.assertStockManage.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.ViewiteminfoModel;

@Repository
@Transactional
public class VoucherAndStockInfoDao extends HibernateDao
{

	public void quaryInstockHisInfo(Page<ViewiteminfoModel> page,Map<String, Object> parameter, Criteria criteria)
	{
		 String voucher_id = parameter.get("voucher_id")!=null?parameter.get("voucher_id").toString():"";
		 if(voucher_id!=""){
			
			 String sql="from "+ViewiteminfoModel.class.getName()+" stock where 1=1";
			 String sqlCount="select count(*) from "+ViewiteminfoModel.class.getName()+" stock where 1=1";
			 Map<String,Object> map =new HashMap<String,Object>();
			 if(voucher_id!=null&&!"".equals(voucher_id))
			 {
				 sql+=" and stock.voucher_id='"+voucher_id+"'";
				 sqlCount+=" and stock.voucher_id='"+voucher_id+"'";
			 }
			 try{
				 this.pagingQuery(page, sql, sqlCount, map);
				}catch (Exception e) 
				{
					e.printStackTrace();
				}	 
			 
		 }
		
	}
	
	
}
