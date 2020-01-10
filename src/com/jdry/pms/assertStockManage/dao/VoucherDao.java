package com.jdry.pms.assertStockManage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.comm.util.CommUser;

@Repository
@Transactional
public class VoucherDao extends HibernateDao{
	
	public boolean save(InstockModel stock)
	{
		    
		    Session session = this.getSessionFactory().openSession();
		    String item_id="";
		    boolean flag=false;
		    String sqlStr="select * from t_instock where item_id='"+stock.getItem_id()+"'";
		    List<InstockModel> list = session.createSQLQuery(sqlStr.toString()).addEntity("b",InstockModel.class).list();
			if(list != null && list.size()>0){
				InstockModel ins = list.get(0);
				if(null != ins){
					item_id = ins.getItem_id();
				}
			}
			if(!item_id.equals(stock.getItem_id())){
			 flag=true;
			 stock.setOper_id(CommUser.getUserName());
			 session.saveOrUpdate(stock);
			}
		    session.flush();
		    session.close();
			return flag;	

	}
	
	/**
	 * 库存信息展示
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @return
	 */
	public List quaryInstockInfo(Page<InstockModel> page,
			Map<String, Object> parameter, Criteria criteria)
	{
	
	         String sql="select t.*,f.stock_lowerlimit,f.stock_uplimit,u.CNAME_,sup.type_name from t_instock t ,t_itmsfiles f, bdf2_user u, t_supplytype sup where t.item_code=f.item_code  and t.oper_id=u.USERNAME_ and sup.supply_code=t.item_type";
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
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public boolean updateInstockInfo(InstockModel ins) throws SQLException
	{
		 boolean flag=false;
		 Session session = this.getSessionFactory().openSession();
		 Connection con0=session.connection();
		 Statement stmt0 = con0.createStatement();
		 String sql0="select t.stock_uplimit from t_itmsfiles  t  where t.item_code='"+ins.getItem_code()+"'";
		 String sql01="select  sum(t.suppliy_num) as suppliynum from t_instock_his t where t.item_code='"+ins.getItem_code()+"' group by t.item_id ";
		 List list=this.getSessionFactory().getCurrentSession().createSQLQuery(sql0.toString()).list();
		 List list01=this.getSessionFactory().getCurrentSession().createSQLQuery(sql01.toString()).list();
		 
		 int stock_uplimit0=0;
		 int suppliynum=0;
		 for(int i=0;i<list.size();i++)
		 {
			    Double obj = (Double)list.get(i);	
			    stock_uplimit0=(int)obj.doubleValue();
				
		 }
		 
		 for(int i=0;i<list01.size();i++)
		 {
			    BigDecimal obj = (BigDecimal)list01.get(i);
			    String suppliy_num = null == obj?"":obj.toString();
				BigDecimal bg=new BigDecimal(suppliy_num);
			    suppliynum=bg.intValue();
				
		 }
		 
		 if(stock_uplimit0>=(ins.getSuppliy_num()+suppliynum))
		 {
			 flag=operationStock(ins);
		 }else
		 {
			 
			 flag=false;
		 }
		 
		return flag;
	}
	
	/**
	 * 
	 * @param ins
	 * @return
	 * @throws SQLException
	 */
	public boolean operationStock(InstockModel ins) throws SQLException
	{
		
		 boolean flag=false;
		 Session session = this.getSessionFactory().openSession();
		 Connection con=session.connection();
		 Statement stmt = con.createStatement(); 
		 String sqlStr="update t_instock set suppliy_num="+ins.getSuppliy_num()+",unit_price="+ins.getUnit_price()+",sum_price="+ins.getSum_price()+" where item_code='"+ins.getItem_code()+"' and instock_id='"+ins.getInstock_id()+"'";
		 con.setAutoCommit(false);
		 int type=stmt.executeUpdate(sqlStr);
		 con.commit();
		 stmt.close();
		 con.close();
		 
		if(type>0)
		{
			// flag=true;
			 Connection con2=session.connection();
			 Statement stmt2 = con2.createStatement(); 
			 String sql="insert into t_instock_his(instock_id,suppliy_code,voucher_id,suppliy_name,unit_price,instock_time,suppliy_num,item_code,item_name,sum_price,item_type,oper_id,item_id) select instock_id,suppliy_code,voucher_id,suppliy_name,unit_price,instock_time,suppliy_num,item_code,item_name,sum_price,item_type,oper_id,item_id from t_instock t where t.instock_id='"+ins.getInstock_id()+"'";
			 con2.setAutoCommit(false);
			 int excuteType=stmt2.executeUpdate(sql);
			 con2.commit();
			 stmt2.close();
			 con2.close();
			 if(excuteType>0) {

				 flag=true; 
			 }
		}
		 
		 return flag;
		
	}
	
	public boolean saveVoucherAndInstock(VoucherModel vo,List<InstockModel> instockList) throws SQLException, ParseException
	{
		 boolean flag=false;
	     Session sess = this.getSessionFactory().openSession();
		 Connection con=sess.connection();
		 if(vo!=null){
		 Statement stmt = con.createStatement(); 
		 String sqlStr=" insert into t_voucher(voucher_id,voucher_code,owne_stock,t_handler,suppliy_code,occurren_date,instok_type)values('"+UUID.randomUUID()+"','"+vo.getVoucher_code()+"','"+vo.getOwne_stock()+"','"+vo.getT_handler()+"','"+vo.getSuppliy_code()+"','"+vo.getOccurren_date()+"','01' ) ";
		 con.setAutoCommit(false);
		 stmt.executeUpdate(sqlStr);
		 con.commit();
		 stmt.close();
		 con.close();
		 flag=true;
		 }
		 SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String date2String=sp.format(new Date());
		  
		 for(InstockModel ins:instockList){
		 //更新单据和入库表之间的关系
		 Session session = this.getSessionFactory().openSession();
		 Connection conn=session.connection();
		System.out.println(ins.getAdd_num());
         String insSql="insert into t_instock_his(instock_id, suppliy_code,voucher_id,suppliy_name,unit_price,instock_time,suppliy_num,item_code,item_name,sum_price,item_type,oper_id,item_id,instok_type)" +
         		"values(?,?,?,?,?,?,?,?,?,?,?,?,?,'01')";
		 //String sql=" update t_instock_his set voucher_id='"+vo.getVoucher_code()+"',suppliy_code='"+vo.getSuppliy_code()+"',instok_type='01' where instock_id='"+ins.getInstock_id()+"'";
         PreparedStatement ps = conn.prepareStatement(insSql);
         ps.setString(1, ins.getInstock_id());
         ps.setString(2, ins.getSuppliy_code());
         ps.setString(3, vo.getVoucher_code());
         ps.setString(4, ins.getSuppliy_name());
         ps.setDouble(5, ins.getUnit_price());
         ps.setString(6, date2String);
         ps.setInt(7, ins.getAdd_num());
         ps.setString(8, ins.getItem_code());
         ps.setString(9, ins.getItem_name());
         ps.setDouble(10, ins.getSum_price());
         ps.setString(11, ins.getItem_type());
         ps.setString(12, ins.getOper_id());
         ps.setString(13, ins.getItem_id());
         session.beginTransaction();
         conn.setAutoCommit(false);
		 flag=ps.execute();
		 conn.commit();
		 ps.close();
		 conn.close();
		 session.close();
		 flag=true;
		 }
		return flag;
		
	}
	
	public boolean deleteStockInfo()
	{
		
		 Session session = this.getSessionFactory().openSession();
		 Connection con=session.connection();
		 Statement stmt;
		 boolean flag=false;
		try {
			 stmt = con.createStatement();
			 String sqlStr="delete from t_instock";
			 con.setAutoCommit(false);
			 int i=stmt.executeUpdate(sqlStr);
			 if(i<=0)
			 {
				 flag=false;	 
			 }else{
		      flag=true;
			 }	 //execute(sqlStr);
			 con.commit();
			 stmt.close();
			 con.close();
		} catch (SQLException e) 
		{
			flag=false;
		}
		return flag;
		
	}
	

}
