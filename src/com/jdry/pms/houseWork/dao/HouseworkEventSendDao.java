package com.jdry.pms.houseWork.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.dao.ProptyUtil;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
/**
 * 描述：派工处理持久层
 * @author hezuping
 *
 */
@Repository
@Transactional
public class HouseworkEventSendDao extends HibernateDao
{

	public List queryHouseWorkEventSendInfo(Page page,Map<String, Object> parameter, Object object)
	{
		Session session = this.getSessionFactory().openSession();
		String userId = parameter.get("userId")==null?"":parameter.get("userId").toString();
		String postionId=getUserOfRole(userId);
		ProptyUtil p=new ProptyUtil();
		String rePostionId= p.getProperty("JDRY.KFpostionId");
		String sql="";
		 if(postionId.equals(rePostionId))//客户岗位
		 {
			sql="select t.*,u.CNAME_ from v_t_housework_eventsend t LEFT JOIN bdf2_user u on t.verify_oper_id=u.USERNAME_ where 1=1 and t.event_state in(1,2)"; 
			 
		 }else if(!(rePostionId).equals(postionId))
		  {
			 sql="select t.*,u.CNAME_ from v_t_housework_eventsend t LEFT JOIN bdf2_user u on t.verify_oper_id=u.USERNAME_ where 1=1 and t.oper_id='"+userId+"' and t.event_state in(1,2)";  
		  }
		
		
		StringBuffer sqlStr=new StringBuffer();
		
		if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
				if(!search.equals("")){
			      sql+=" and t.event_no like"+"'%"+search+"%'"+
					 " or t.rpt_name like"+"'%"+search+"%'"+
					 " or t.call_phone like"+"'%"+search+"%'"+
					 " or t.user_address like"+"'%"+search+"%'"+
					 " or t.event_source_name like"+"'%"+search+"%'";
				}
		 }
		
		sqlStr.append(sql);
		sqlStr.append(" order by t.accept_time desc");
		int shownum=page.getLastEntityIndex()-page.getFirstEntityIndex();
		sqlStr.append(" LIMIT "+page.getFirstEntityIndex()+", "+shownum);
		page.setEntityCount(getCount(sql));
		List result = session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		return result;
		
	}
    /**
     * 分页统计
     * @param sql
     * @return
     */
	public int getCount(String sql){
		StringBuffer sqlStr=new StringBuffer();
		sqlStr.append("select count(*) from(");
		sqlStr.append(sql);
		sqlStr.append(") aa");
		int count= Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sqlStr.toString()).uniqueResult()+"");
		return count;
	}
	/**
	 * 查询派工单
	 * @param send_id
	 * @return
	 */
	public HouseWorkEventSendEntity queryHouseWorkSendInfoById(String send_id)
	{
		String sql="select  t.* from t_housework_dispatch t   where t.id='"+send_id+"'";
		Session session = this.getSessionFactory().openSession();
		HouseWorkEventSendEntity send=(HouseWorkEventSendEntity) session.createSQLQuery(sql.toString()).addEntity("t",HouseWorkEventSendEntity.class).uniqueResult();
		session.close();
		return send;
	}
	
	public HouseWorkEventSendEntity queryHouseWorkById(String eventId)
	{
		String sql="select  t.* from t_housework_dispatch t   where t.event_id='"+eventId+"'";
		Session session = this.getSessionFactory().openSession();
		HouseWorkEventSendEntity send=(HouseWorkEventSendEntity) session.createSQLQuery(sql.toString()).addEntity("t",HouseWorkEventSendEntity.class).uniqueResult();
		session.close();
		return send;
	}
	
	public HouseWorkEventSendEntity queryHouseWorkSendInfoByEventNo(String event_no) {
		String sql="select DISTINCT(t.event_id) as event_id,t.id,t.send_no,t.oper_id,t.send_time,t.arrv_time,t.delete_time,t.send_state,t.handle_content,t.oper_name,t.houseKeepingPay " +
				" from t_housework_dispatch t  where  EXISTS( select d.id from t_housework_event d where t.event_id=d.id and d.event_no='"+event_no+"')";
		Session session = this.getSessionFactory().openSession();
		HouseWorkEventSendEntity send=(HouseWorkEventSendEntity) session.createSQLQuery(sql.toString()).addEntity("t",HouseWorkEventSendEntity.class).uniqueResult();
		session.close();
		return send;
	}
	
	/**
	 * 根据岗位查数据
	 * @param userId
	 * @return
	 */
	public String getUserOfRole(String userId)
	{
		String sql="select u.POSITION_ID_ from bdf2_user_position u where u.USERNAME_='"+userId+"'";
		Session session = this.getSessionFactory().openSession();
		List ls=session.createSQLQuery(sql).list();
		session.close();
		if(ls==null||ls.size()<=0)
		{   
			return "";
		}else
		
		return ls.get(0).toString();
	}
}
