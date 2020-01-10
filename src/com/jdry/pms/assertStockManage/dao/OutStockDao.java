package com.jdry.pms.assertStockManage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;

/**
 * 描述：出库操作
 * 
 * @author hezuping
 * 
 */
@Repository
@Transactional
public class OutStockDao extends HibernateDao {

	public boolean OutStockInfo(List<InstockModel> instockList, VoucherModel vo)
			throws SQLException {
		boolean flag=false;
		Session session = this.getSessionFactory().openSession();
		for (InstockModel stock : instockList) {
			String sql=" SELECT DISTINCT suppliy_num FROM( SELECT	a.suppliy_num " +
					" FROM(	SELECT t.voucher_id,	t.item_id,t.item_name,sum(t.suppliy_num) AS suppliy_num" +
					" FROM	t_instock_his t GROUP BY	t.item_id ) AS a" +
					" LEFT JOIN t_itmsfiles it ON a.item_id = it.item_id" +
					" LEFT JOIN t_voucher vo ON a.voucher_id = vo.voucher_code) cc";
			/*// 加分组
			String sql = "select distinct (cc.suppliy_num)  from(select  sup.type_name,a.item_id,a.item_name,it.item_type,a.unit_price, a.sum_price,a.suppliy_num,vo.owne_stock, itm. bar_code,itm.item_unit,itm.item_flag from "
					+ " (select suppliy_code, t.voucher_id,t.item_id,t.item_name,t.unit_price,sum(t.unit_price*t.suppliy_num)  as sum_price,sum(t.suppliy_num)  as suppliy_num from t_instock_his t  group by t.item_id  ) as a "
					+ " left  join t_itmsfiles it on a.item_id=it.item_id "
					+ " left join t_supplytype sup on a.suppliy_code=sup.supply_code"
					+ " left join t_itmsfiles itm on a.item_id=itm.item_id "
					+ " left join t_voucher vo on a.voucher_id=vo.voucher_code ) cc "
					+ " where cc.owne_stock='"
					+ vo.getOwne_stock()
					+ "' and cc.item_flag in(select  itm.item_flag  from  t_itmsfiles  itm where itm.item_code='"
					+ stock.getItem_code() + "')";*/
			List list = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).list();
			for (int i = 0; i < list.size(); i++) {
				BigDecimal obj = (BigDecimal) list.get(i);
				String suppliy_num = null == obj ? "" : obj.toString();
				BigDecimal bg = new BigDecimal(suppliy_num);
				boolean isFlag = java.lang.Math.abs(stock.getAdd_num()) != 0
						|| java.lang.Math.abs(stock.getAdd_num()) > 0;
						if (java.lang.Math.abs(stock.getAdd_num()) <= bg.intValue()&& isFlag)
						{
							Connection con=session.connection();
							Statement stmt = con.createStatement(); 
							String sqlStr=" insert into t_voucher(voucher_id,voucher_code,owne_stock,t_handler,suppliy_code,occurren_date,instok_type)values('"+UUID.randomUUID()+"','"+vo.getVoucher_code()+"','"+vo.getOwne_stock()+"','"+vo.getT_handler()+"','"+vo.getSuppliy_code()+"','"+vo.getOccurren_date()+"','02' ) ";
							con.setAutoCommit(false);
							stmt.executeUpdate(sqlStr);
							con.commit();
							stmt.close();
							con.close(); 

							SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String date2String=sp.format(new Date());
							 flag=true;
							for(InstockModel ins:instockList){
								//更新单据和入库表之间的关系
								Session session1 = this.getSessionFactory().openSession();
								Connection conn=session.connection();

								String insSql="insert into t_instock_his(instock_id, suppliy_code,voucher_id,suppliy_name,unit_price,instock_time,suppliy_num,item_code,item_name,sum_price,item_type,oper_id,item_id,instok_type)" +
										"values(?,?,?,?,?,?,?,?,?,?,?,?,?,'02')";
								PreparedStatement ps = conn.prepareStatement(insSql);
								ps.setString(1, ins.getInstock_id());
								ps.setString(2, ins.getSuppliy_code());
								ps.setString(3, vo.getVoucher_code());
								ps.setString(4, ins.getSuppliy_name());
								ps.setDouble(5, ins.getUnit_price());
								ps.setString(6, date2String);
								ps.setInt(7, -ins.getAdd_num());
								ps.setString(8, ins.getItem_code());
								ps.setString(9, ins.getItem_name());
								ps.setDouble(10, ins.getSum_price());
								ps.setString(11, ins.getItem_type());
								ps.setString(12, ins.getOper_id());
								ps.setString(13, ins.getItem_id());
								session1.beginTransaction();
								conn.setAutoCommit(false);
								flag=ps.execute();
								conn.commit();
								ps.close();
								conn.close();
								session1.close();
								flag=true;
                              
							}
							return flag;

						}

			}
		}
		return  flag;
	}
}
