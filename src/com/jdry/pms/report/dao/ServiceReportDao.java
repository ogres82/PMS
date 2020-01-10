package com.jdry.pms.report.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
@Repository
@Transactional
public class ServiceReportDao extends HibernateDao{
	
	public List getMtnInfo(){
		Session session = this.getSessionFactory().openSession();
		List list = new ArrayList();
		String sql = "SELECT v.code_detail_name,v.event_type,DATE_FORMAT(v.rpt_time, '%Y%m') months,COUNT(1),FORMAT(SUM(CASE WHEN v.dispatch_visit_lev=0 THEN 1" +
																											   " WHEN v.dispatch_visit_lev=1 THEN 0.75" +
																											   " ELSE 0 END)/COUNT(1)*100,2) " +
																											   "FROM v_report_mtn v GROUP BY months";
		list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public List getCompInfo(){
		Session session = this.getSessionFactory().openSession();
		List list = new ArrayList();
		String sql = "SELECT v.code_detail_name,v.event_type,DATE_FORMAT(v.rpt_time, '%Y%m') months,COUNT(1),FORMAT(SUM(CASE WHEN v.comp_visit_lev=0 THEN 1" +
																											   " WHEN v.comp_visit_lev=1 THEN 0.75" +
																											   " ELSE 0 END)/COUNT(1)*100,2) " +
																											   "FROM v_report_comp v GROUP BY months";
		list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public List getHouseworkInfo(){
		Session session = this.getSessionFactory().openSession();
		List list = new ArrayList();
		String sql = "SELECT v.type,v.event_type,DATE_FORMAT(v.accept_time, '%Y%m') months,COUNT(1),FORMAT(SUM(CASE WHEN v.visit_evaluate=0 THEN 1" +
																								   " WHEN v.visit_evaluate=1 THEN 0.75" +
																								   " ELSE 0 END)/COUNT(1)*100,2) " +
																								   "FROM v_report_housework v GROUP BY months";
		list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}

	/**
	 * 报章报修统计
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getImpairInfo() 
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from (select c.*,tm.month from t_past_12_month_view tm LEFT JOIN (");
		sb.append(" select * from (");
		sb.append(" select count(1) as num,DATE_FORMAT(cc.createTime,'%Y%m') as month1,cc.flag from (");
		sb.append(" select tr.*,SUBSTRING(tr.rpt_id,1,2) as flag from t_r_maintain tr ");
		sb.append(" ) cc  GROUP BY cc.flag,DATE_FORMAT(cc.createTime,'%Y%m')");
		sb.append(" union ALL");
		sb.append(" select count(1), DATE_FORMAT(th.accept_time,'%Y%m'),'IM' as flag from t_housework_event th ");
		sb.append(" GROUP BY DATE_FORMAT(th.accept_time,'%Y%m')");
		sb.append(" ) t where t.flag='BX'");
		sb.append(" ) c  on tm.month=c.month1 ) d ORDER BY d.`MONTH` asc");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getAssignWrokInfo()
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append("select * from ( select c.*,tm.month from t_past_12_month_view tm LEFT JOIN (");
		sb.append(" select * from (");
		sb.append(" select count(1) as num,DATE_FORMAT(cc.createTime,'%Y%m') as month1,cc.flag from (");
		sb.append(" select tr.*,SUBSTRING(tr.rpt_id,1,2) as flag from t_r_maintain tr ");
		sb.append(" ) cc  GROUP BY cc.flag,DATE_FORMAT(cc.createTime,'%Y%m')");
		sb.append(" union ALL");
		sb.append(" select count(1), DATE_FORMAT(th.accept_time,'%Y%m'),'IM' as flag from t_housework_event th ");
		sb.append(" GROUP BY DATE_FORMAT(th.accept_time,'%Y%m')");
		sb.append(" ) t where t.flag='TS'");
		sb.append(" ) c  on tm.month=c.month1 ) d ORDER BY d.`MONTH` asc");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public List<Object> getHouseWorkInfo()
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from (select c.*,tm.month from t_past_12_month_view tm LEFT JOIN (");
		sb.append(" select * from (");
		sb.append(" select count(1) as num,DATE_FORMAT(cc.createTime,'%Y%m') as month1,cc.flag from (");
		sb.append(" select tr.*,SUBSTRING(tr.rpt_id,1,2) as flag from t_r_maintain tr ");
		sb.append(" ) cc  GROUP BY cc.flag,DATE_FORMAT(cc.createTime,'%Y%m')");
		sb.append(" union ALL");
		sb.append(" select count(1), DATE_FORMAT(th.accept_time,'%Y%m'),'IM' as flag from t_housework_event th ");
		sb.append(" GROUP BY DATE_FORMAT(th.accept_time,'%Y%m')");
		sb.append(" ) t where t.flag='IM'");
		sb.append(" ) c  on tm.month=c.month1 ) d ORDER BY d.`MONTH` asc");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List getSatisfaction()
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append("select * from( SELECT FORMAT(c.rate,2),c.month1,c.flag, tm. MONTH ");
		sb.append(" FROM t_past_12_month_view tm");
		sb.append(" LEFT JOIN (SELECT * FROM");
		sb.append(" (SELECT sum(cc.lev) / count(1) AS rate,");
		sb.append(" DATE_FORMAT(cc.dispatch_finish_time,'%Y%m') AS month1,cc.flag FROM");
		sb.append(" (SELECT SUBSTRING(t.mtn_id, 1, 2) AS flag,t.mtn_id,t.dispatch_finish_time,");
		sb.append("  CASE WHEN ( t.dispatch_visit_lev IS NULL OR t.dispatch_visit_lev = '0'	) THEN 100");
		sb.append(" WHEN t.dispatch_visit_lev = '1' THEN 75 ELSE 50 END AS lev");
		sb.append(" FROM t_r_maintain_dispatch t WHERE t.dispatch_status = '5' AND SUBSTRING(t.mtn_id, 1, 2) = 'BX' ) cc");
		sb.append(" GROUP BY cc.flag,DATE_FORMAT(cc.dispatch_finish_time,'%Y%m'	)) t");
		sb.append(" ) c ON tm. MONTH = c.month1) d ORDER BY d. MONTH ASC");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getZXSatisfaction() 
	{
	Session session = this.getSessionFactory().openSession();
	StringBuffer sb=new StringBuffer();
	sb.append("select * from( SELECT FORMAT(c.rate,2),c.month1,c.flag, tm. MONTH ");
	sb.append(" FROM t_past_12_month_view tm");
	sb.append(" LEFT JOIN (SELECT * FROM");
	sb.append(" (SELECT sum(cc.lev) / count(1) AS rate,");
	sb.append(" DATE_FORMAT(cc.comp_finish_time,'%Y%m') AS month1,cc.flag FROM");
	sb.append(" (SELECT SUBSTRING(t.mtn_id, 1, 2) AS flag,t.mtn_id,t.comp_finish_time,");
	sb.append("  CASE WHEN ( t.comp_visit_lev IS NULL OR t.comp_visit_lev = '0'	) THEN 100");
	sb.append(" WHEN t.comp_visit_lev = '1' THEN 75 ELSE 50 END AS lev");
	sb.append(" FROM t_r_maintain_complaint t WHERE t.comp_status = '5' AND SUBSTRING(t.mtn_id, 1, 2) = 'TS' ) cc");
	sb.append(" GROUP BY cc.flag,DATE_FORMAT(cc.comp_finish_time,'%Y%m'	)) t");
	sb.append(" ) c ON tm. MONTH = c.month1) d ORDER BY d. MONTH ASC");
	List<Object> lists = session.createSQLQuery(sb.toString()).list();
	session.flush();
	session.close();
	if (null != lists && lists.size() > 0) {
		return lists;
	} else {
		return null;
	}
	}
    
	
	@SuppressWarnings("unchecked")
	public List<Object> getJZSatisfaction() 
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from(SELECT FORMAT(c.rate, 2),c.month1,c.flag,	tm. MONTH");
		sb.append(" FROM t_past_12_month_view tm");
		sb.append(" LEFT JOIN (SELECT	* FROM(	SELECT sum(cc.lev) / count(1) AS rate,DATE_FORMAT(	cc.visit_time,	'%Y%m') AS month1,	cc.flag");
		sb.append(" FROM(SELECT	SUBSTRING(t.event_no, 1, 2) AS flag,t.event_no,	t.visit_time,");
		sb.append(" CASE WHEN (	t.visit_evaluate IS NULL OR t.visit_evaluate = '0'	) THEN	100");
		sb.append(" WHEN t.visit_evaluate = '1' THEN	75 ELSE 50	END AS lev");
		sb.append(" FROM v_housework_repert t");
		sb.append(" WHERE t.event_state = '5' AND SUBSTRING(t.event_no, 1, 2) = 'IM') cc ");
		sb.append(" GROUP BY cc.flag,DATE_FORMAT(cc.visit_time,	'%Y%m'	)) t) c ON tm. MONTH = c.month1) d");
		sb.append(" ORDER BY d.month asc");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getEventAnalysis(String event_type)
	{
		
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.community_name,case when t1.num is null then 0 else t1.num end as num");
		sb.append(" from (select t.community_name from t_house_property t GROUP BY t.community_name) t");
		sb.append(" LEFT JOIN (Select * from v_event_report t2 where t2.type='"+event_type+"') t1 on t.community_name=t1.area");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) 
		{
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Object getEventSatisfaAnalysis(String name)
	{
		
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append("select count(1) as num from v_event_satisfa  where visit_lev='"+name+"'");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) 
		{
			return lists.get(0);
		} else {
			return null;
		}
		
		
	}
    /**
     * 事件响应时长分析
     * @param i
     * @return
     */
	public List getEventResponse(int i) 
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(1) as num,'报障报修' from(");
		sb.append(" select t.mtn_id,TIMESTAMPDIFF(MINUTE,(DATE_FORMAT(t.createTime,'%Y-%m-%d %H:%i')),(DATE_FORMAT(t.dispatch_finish_time,'%Y-%m-%d %H:%i'))) as minu from t_r_maintain_dispatch t");
		if(i==30)
		{
			sb.append(" ) c where c.minu<="+i);
		}else if(i==60)
		{
			sb.append(" ) c where c.minu>30 and c.minu<="+i);
		}
		else if(i==90)
		{
			sb.append(" ) c where c.minu>60 and c.minu<="+i);
		}
		else if(i==120)
		{
			sb.append(" ) c where c.minu>90 and c.minu<="+i);	
		}
		else if(i==150)
		{
			sb.append(" ) c where c.minu>120 and c.minu<="+i);
		}
		else if(i==180)
		{
			sb.append(" ) c where c.minu>150 and c.minu<="+i);
		}
		sb.append(" UNION all");
		sb.append(" select count(1) as num, '咨询建议' from (");
		sb.append(" select mc.mtn_id, TIMESTAMPDIFF(MINUTE,(DATE_FORMAT(mc.comp_createTime,'%Y-%m-%d %H:%i')),(DATE_FORMAT(mc.comp_finish_time,'%Y-%m-%d %H:%i'))) as minu from t_r_maintain_complaint mc");
		if(i==30)
		{
			sb.append(" ) d where d.minu<="+i);
		}else if(i==60)
		{
			sb.append(" ) d where d.minu>30 and d.minu<="+i);
		}
		else if(i==90)
		{
			sb.append(" ) d where d.minu>60 and d.minu<="+i);
		}
		else if(i==120)
		{
			sb.append(" ) d where d.minu>90 and d.minu<="+i);	
		}
		else if(i==150)
		{
			sb.append(" ) d where d.minu>120 and d.minu<="+i);
		}
		else if(i==180)
		{
			sb.append(" ) d where d.minu>150 and d.minu<="+i);
		}
		sb.append(" UNION all");
		sb.append(" select count(1) as num,'家政服务' from (");
		sb.append(" select hd.event_id,TIMESTAMPDIFF(MINUTE,(DATE_FORMAT(hd.send_time,'%Y-%m-%d %H:%i')),(DATE_FORMAT(hd.arrv_time,'%Y-%m-%d %H:%i'))) as minu  from t_housework_dispatch hd");
		if(i==30)
		{
			sb.append(" ) e where e.minu<="+i);
		}else if(i==60)
		{
			sb.append(" ) e where e.minu>30 and e.minu<="+i);
		}
		else if(i==90)
		{
			sb.append(" ) e where e.minu>60 and e.minu<="+i);
		}
		else if(i==120)
		{
			sb.append(" ) e where e.minu>90 and e.minu<="+i);	
		}
		else if(i==150)
		{
			sb.append(" ) e where e.minu>120 and e.minu<="+i);
		}
		else if(i==180)
		{
			sb.append(" ) e where e.minu>150 and e.minu<="+i);
		}
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) 
		{
			return lists;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getEventTimeAnalysis(int week) 
	{
		Session session = this.getSessionFactory().openSession();
		StringBuffer sb=new StringBuffer();
		sb.append("select * from (");
		sb.append(" select * from (");
		sb.append(" select count(1) as num,hours,myweek from(");
		sb.append(" select t.rpt_id, DATE_FORMAT(t.createTime,'%k') as hours,DAYOFWEEK(t.createTime)-1 as myweek ,DATE_FORMAT(t.createTime,'%y-%m-%d')as days from t_r_maintain t ");
		sb.append(" ) c GROUP BY hours,myweek ORDER BY myweek asc");
		sb.append(" ) cc ");
		sb.append(" union all");
		sb.append(" select * from ");
		sb.append("(select count(1) as num,hours,myweek ");
		sb.append(" from( select t.event_no, DATE_FORMAT(t.accept_time,'%k') as hours,DAYOFWEEK(t.accept_time)-1 as myweek ,DATE_FORMAT(t.accept_time,'%y-%m-%d')as days");
		sb.append(" from t_housework_event t  ) c ");
		sb.append(" GROUP BY hours,myweek ORDER BY myweek asc ) cc ");
		sb.append(" ) dd   where dd.myweek="+week+" GROUP BY dd.hours,dd.myweek");
		List<Object> lists = session.createSQLQuery(sb.toString()).list();
		session.flush();
		session.close();
		if (null != lists && lists.size() > 0) 
		{
			return lists;
		} else {
			return null;
		}
	}
	
	public List<WorkMainEntity> queryMtnList(String finishFlag){
		String hql = "";
		if("0".equals(finishFlag)){
			hql = "select t.rpt_kid,t.rpt_id,t.createTime,t.event_content from WorkMainEntity t where t.event_type='0' and t.event_state in ('0','1','2') order by t.createTime";
		}else{
			hql = "select t.rpt_kid,t.rpt_id,t.createTime,t.event_content from WorkMainEntity t where t.event_type='0' and t.event_state in ('3','4','5') order by t.createTime";
		}
		return this.query(hql);
	}
	
	public List<WorkMainEntity> queryCompList(String finishFlag){
		String hql = "";
		if("0".equals(finishFlag)){
			hql = "select t.rpt_kid,t.rpt_id,t.createTime,t.event_content from WorkMainEntity t where t.event_type='1' and t.event_state in ('0','1','2') order by t.createTime";
		}else{
			hql = "select t.rpt_kid,t.rpt_id,t.createTime,t.event_content from WorkMainEntity t where t.event_type='1' and t.event_state in ('3','4','5') order by t.createTime";
		}
		return this.query(hql);
	}
	
	public List<HouseworkEventEntity> queryHouseworkList(String finishFlag){
		String hql = "";
		if("0".equals(finishFlag)){
			hql = "select t.id,t.event_no,t.accept_time,t.event_content from HouseworkEventEntity t where t.event_state in ('0','1','2') order by t.accept_time";
		}else{
			hql = "select t.id,t.event_no,t.accept_time,t.event_content from HouseworkEventEntity t where t.event_state in ('3','4','5') order by t.accept_time";
		}
		return this.query(hql);
	}
}
