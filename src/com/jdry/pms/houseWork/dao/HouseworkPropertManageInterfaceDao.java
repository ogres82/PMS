package com.jdry.pms.houseWork.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;
/**
 * 描述：获取家政服务——业主APP
 * @author hezuping
 * 2016年5月25日23:20:14
 */
@Repository
@Transactional
public class HouseworkPropertManageInterfaceDao extends HibernateDao
{

	public List queryHouseKeeping(String oper_id) 
	{
		Session session = this.getSessionFactory().openSession();
		String sql=" select cc.*,chg.state from(" +
				" select * from (SELECT t.event_no,t.link_man_name,t.pre_time,t.user_address,t.event_state,di.oper_id,t.accept_time, t.other" +
				" FROM t_housework_event t LEFT JOIN t_housework_dispatch di ON t.id = di.event_id" +
				" ) temp where temp.oper_id='"+oper_id+"' ORDER BY temp.accept_time desc" +
				" )cc LEFT JOIN t_charge_info chg on cc.event_no=chg.work_id";
		
		List ls=session.createSQLQuery(sql).list();
		session.close();
		return ls;
	}

	public Object getHouseKeepingDeatail(String event_no)
	{
		Session session = this.getSessionFactory().openSession();
		String sql="SELECT DISTINCT(t.event_no) as event_no,t.event_content,t.pre_time,t.user_address,t.link_man_name,t.call_phone, di.houseKeepingPay as houseworkfee, t.event_state" +
				  " FROM t_housework_event t,t_housework_dispatch di where t.id=di.event_id and t.event_no='"+event_no+"'";
		Object obj=session.createSQLQuery(sql).uniqueResult();
		session.close();
		if(obj==null)
		{
		 return "";
		}
		else{
		 return obj;
		}
	}

	
	public String getRoomNoById(String room_id) 
	{
		Session session = this.getSessionFactory().openSession();
		String sql="select DISTINCT(t.room_no) as room_no from v_area_build_house_owner_rela t where t.room_id='"+room_id+"'";
		Object obj=session.createSQLQuery(sql).uniqueResult();
		if(obj==null)
		{
		 return "";
		}
		else{
		 return obj.toString();
		}
	}

	public HouseworkVisitEntity quaryVistInfoById(String id)
	{
		String hql="from " + HouseworkVisitEntity.class.getName()
						+ " where event_id='" + id + "'";
		List<HouseworkVisitEntity> lists=this.query(hql);
		if (null != lists && lists.size() > 0)
		{
			return lists.get(0);
		} else {
			return null;
		}
	}

}
