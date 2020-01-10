package com.jdry.pms.topTask.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.service.AuditInfoService;

/**
 * 描述：代办任务
 * @author hezuping
 *
 */
@Repository
@Transactional
public class TopTaskDao extends HibernateDao
{
	@Resource
	
   AuditInfoService auditInfoService;
	/**
	 * 代办总条数
	 * @param userId
	 * @return
	 */
	public int getTaskCountByUserId(String userId)
	{
		int sumcount=0;
		int count=getAssinWorkCountByUserId(userId);
		int countDispatch=getAssinWorkDispacthCountByUserId(userId);
		int countHouseWork=getHouseWorkCountByUserId(userId);
		int countNotice=getmsgNotice(userId);
		sumcount=count+countDispatch+countHouseWork+countNotice;
		return sumcount;
	}
	
	//咨询建议
	public int getAssinWorkCountByUserId(String userId)
	{
		String sql="select count(t.rpt_id) from t_r_maintain t ,t_r_maintain_complaint tm where t.rpt_id=tm.mtn_id and  tm.comp_operator_id='"+userId+"' and  t.event_state not in(0,3,4,5)";
		Session session = this.getSessionFactory().openSession();
		Object obj=session.createSQLQuery(sql).uniqueResult();
		session.close();
		if(obj==null)
		{   
			return 0;
		}else
		return Integer.parseInt(obj.toString());
	}
	//报障保修
	public int getAssinWorkDispacthCountByUserId(String userId)
	{
		String sql="SELECT count(t.rpt_id) FROM t_r_maintain t,t_r_maintain_dispatch td WHERE	t.rpt_id = td.mtn_id AND td.dispatch_handle_id = '"+userId+"' AND t.event_state not in(0,3,4,5)";
		Session session = this.getSessionFactory().openSession();
		Object obj=session.createSQLQuery(sql).uniqueResult();
		session.close();
		if(obj==null)
		{   
			return 0;
		}else
		return Integer.parseInt(obj.toString());
		
	}
   //家政服务
	public int getHouseWorkCountByUserId(String userId)
	{
		String sql="select count(t.event_no) from t_housework_event t,t_housework_dispatch dis where t.id=dis.event_id and t.event_state not in(0,3,4,5) and dis.oper_id='"+userId+"'";
		Session session = this.getSessionFactory().openSession();
		Object obj=session.createSQLQuery(sql).uniqueResult();
		session.close();
		if(obj==null)
		{   
			return 0;
		}else
		return Integer.parseInt(obj.toString());
	}
	
	public int getmsgNotice(String userId)
	{
		
		return auditInfoService.getCountAuditNotice(userId);
	}

	/**
	 * 任务列表
	 * @param page
	 * @param parameter
	 * @param object
	 * @return
	 */
	public List queryTopTaskInfo(Page page, Map<String, Object> parameter,Object object) 
	{
		
		 Session session = this.getSessionFactory().openSession();
		 StringBuffer sqlStr=new StringBuffer();
		 String userId = parameter.get("userId")==null?"":parameter.get("userId").toString();
		 String sql="select t.*,b.CNAME_ from v_toptasklist t   LEFT JOIN bdf2_user b on t.comp_operator_id=b.USERNAME_ where 1=1 and  ( t.comp_operator_id='"+userId+"'";
		 List res=getPositionId(userId);
		 if(res.size()>0)
		 {
			 for(int i=0;i<res.size();i++)
				{
					Map map = new HashMap();
					Object obj = res.get(i);
					String position_id = null == obj?"":obj.toString();
				    sql+=" or t.comp_operator_id='"+position_id+"'";
					
				}
			 
		 }
		 sql+=") ";
		 if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
				if(!search.equals("")){
			      sql+=" and t.rpt_id like"+"'%"+search+"%'"+
					 " or t.address like"+"'%"+search+"%'";
					// " or t.call_phone like"+"'%"+search+"%'"+
					// " or t.user_address like"+"'%"+search+"%'"+
					// " or t.event_source_name like"+"'%"+search+"%'";
				}
		 }
		page.setEntityCount(getCount(sql));
		sql+=" ORDER BY t.createTime desc";
	    sqlStr.append(sql);
	    int shownum=page.getLastEntityIndex()-page.getFirstEntityIndex();
		sqlStr.append(" LIMIT "+page.getFirstEntityIndex()+", "+shownum);
		
		List result = session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		return result;
	}
	
	
	//统计条数
		public int getCount(String sql){
			StringBuffer sqlStr=new StringBuffer();
			sqlStr.append("select count(*) from(");
			sqlStr.append(sql);
			sqlStr.append(") aa");
			int count= Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sqlStr.toString()).uniqueResult()+"");
			return count;
		}

		/**
		 * APP 端列表
		 * @param userId
		 * @return
		 */
		public List queryTopTaskInfoByUserId(String userId)
		{
			 Session session = this.getSessionFactory().openSession();
			 String sql="select * from v_toptasklist t where 1=1 and  ( t.comp_operator_id='"+userId+"'";
			 List res=getPositionId(userId);
			 if(res.size()>0)
			 {
				 for(int i=0;i<res.size();i++)
					{
						Map map = new HashMap();
						Object obj = res.get(i);
						String position_id = null == obj?"":obj.toString();
					    sql+=" or t.comp_operator_id='"+position_id+"'";
						
					}
				 
			 }
			 sql+=") order by t.createTime desc";
			 List result = session.createSQLQuery(sql).list();
			 session.close();
			 return result;
		}
		
		public List getPositionId(String userId)
		{
			
			 Session session = this.getSessionFactory().openSession();
			 String sql="select t.POSITION_ID_ from bdf2_user_position t where t.USERNAME_='"+userId+"'";
			 List result = session.createSQLQuery(sql).list();
			 session.close();
			 return result;	
		}
	
	
	
}

