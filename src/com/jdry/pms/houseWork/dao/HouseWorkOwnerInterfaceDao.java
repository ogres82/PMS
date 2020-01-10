package com.jdry.pms.houseWork.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assignWork.dao.AssignWorkDispatchDao;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;

/**
 * 描述：业主端家政服务持久层
 * @author hezuping
 *
 */
@Repository
@Transactional
public class HouseWorkOwnerInterfaceDao extends HibernateDao
{

	/**
	 * 描述：家政业主端申请
	 * @param work
	 * @return
	 */
	static Logger log=Logger.getLogger(AssignWorkDispatchDao.class);
	public boolean supplyHouseWorkEventInfo(HouseworkEventEntity work)
	{
		boolean flag=false;
		try{
			Session session = this.getSessionFactory().openSession();
			session.saveOrUpdate(work);
			log.info("业主家政申请："+work.getEvent_no());
			session.flush();
			session.close();
			flag=true;	
		}catch(Exception e)
		{
			flag=false;	
		}
		return flag;
	}

	/**
	 * 描述：通过业主电话号码获取家政信息
	 * @param phone
	 * @return
	 */
	public List getAllHouseWorkEventInfoByPhone(String phone)
	{

		String sql=" select bb.*,chg.state from (" +
				"select t.event_no,t.event_content,t.accept_time,t.event_state,t.other from t_housework_event t  where  t.call_phone='"+phone+"' ORDER BY t.accept_time DESC" +
				" ) bb LEFT JOIN t_charge_info chg on bb.event_no=chg.work_id";
		Session session = this.getSessionFactory().openSession();
		List ls=session.createSQLQuery(sql).list();
		session.close();
		return ls;
	}

	public HouseworkEventEntity getHouseWorkEventByNo(String event_no) 
	{
		String sql="select * from v_t_housework_detail t where t.event_no='"+event_no+"'";
		Session session = this.getSessionFactory().openSession();
		Object[] obj=(Object[])session.createSQLQuery(sql).uniqueResult();
		session.close();
		HouseworkEventEntity house=new HouseworkEventEntity();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(obj==null)
		{
			return null ;
		}else
		{
			house.setEvent_no(obj[0].toString());
			String other=null==obj[7]?"":obj[7].toString();
			String content=null==obj[1]?"":obj[1].toString();
			if(!other.equals(""))
			{
				house.setEvent_content(""+content+other);
			}else
			{
				house.setEvent_content(content);
			}
			
			if(obj[2]!=null)
			{
				try {
					if(obj[6]!=null)//如果已经派工，则以派工时间为准
					{
						house.setAccept_time(sd.parse(obj[2].toString()));
					}else
					{
						house.setAccept_time(sd.parse(obj[2].toString()));
					}

				} catch (ParseException e)
				{

				}
			}
			if(obj[8]!=null)//
			{
				try {
					house.setPre_time(sd.parse(obj[8].toString()));
				} catch (ParseException e)
				{
					house.setPre_time(null);
				}
			}
			house.setEvent_state(null==obj[3]?"":obj[3].toString());
			house.setCall_phone(null==obj[4]?"":obj[4].toString());
			house.setUser_address(null==obj[5]?"":obj[5].toString());
			house.setOther(other);
			return house;
		}
	}

	/**
	 * 流程历史纪录——APP
	 * @param event_no
	 * @return
	 */
	public List getHouseWorkDispatchDetailByNo(String event_no)
	{
		String sql="select * from(SELECT t.handle_time,u.CNAME_ AS cname,t.handler_dept,t.handler_phone,	t.`status`,t.event_id,	t.mtn_type,t.handlers,u.ADDRESS_" +
				" FROM t_r_eventsend t LEFT JOIN bdf2_user u on (t.handlers = u.USERNAME_ or t.handlers=u.cname_) ORDER BY t.`status` asc" +
				" ) cc where cc.event_id = '"+event_no+"'";
		Session session = this.getSessionFactory().openSession();
		return session.createSQLQuery(sql).list();
	}
    /**
     * 家政服务项目
     * @return
     */
	public List getHouseWorkPropery()
	{
		String sql="select t.id,t.project_name from t_housework_type t ";
		Session session = this.getSessionFactory().openSession();
		return session.createSQLQuery(sql).list();
	}
}
