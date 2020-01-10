package com.jdry.pms.assignWork.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

/**
 * 报賬报修对外接口持久层
 * @author hezuping
 *
 */
@Repository
public class ReportDetailInterfaceDao extends HibernateDao
{

	public List getReportDetailList(String mtr_id)
	{
		
		String sql="SELECT t.createTime AS repair_time,t.event_content AS repair_content,t.addres,t.event_state " +
				" FROM T_R_MAINTAIN t WHERE t.rpt_id = '"+mtr_id+"'";
		Session session = this.getSessionFactory().openSession();
		List list=session.createSQLQuery(sql).list();
		session.close();
		return list;
	}

	/**
	 * 状态详情
	 * @param mtr_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getReportDeailInfoList(String mtr_id)
	{
		String sql="select cc.dispatch_status,cc.createby,cc.handler_dept,cc.dispatch_sl_time,cc.handler_phone,cc.handlers, cc.mtn_type,cc.ADDRESS_" +
				" from (SELECT t.`status` AS dispatch_status,u.CNAME_ AS createby,t.handler_dept AS handler_dept," +
				" t.handle_time AS dispatch_sl_time,t.handler_phone AS handler_phone,t.event_id, t.handlers,t.mtn_type, u.ADDRESS_" +
				" FROM t_r_eventsend t LEFT JOIN bdf2_user u on	t.handlers = u.USERNAME_" +
				" ORDER BY t.`status` ASC ) cc where cc.event_id='"+mtr_id+"'";
		 Session session = this.getSessionFactory().openSession();
		 List list=session.createSQLQuery(sql).list();
		session.close();
		return list;
	}
    /**
     * 
     */
	public List getRepairHistoryByPhone(String moble)
	{
		/*String sql=" select DISTINCT(dd.rpt_id),dd.createTime,dd.mtn_detail,dd.dispatch_status,dd.code_detail_name,dd.dispatch_result,dd.state  from(" +
				" select CC.*,t.state from (" +
				" SELECT aa.rpt_id,aa.createTime,aa.mtn_detail," +
				" aa.dispatch_status,aa.code_detail_name,aa.dispatch_result" +
				" FROM(SELECT tp.*, di.code_detail_name FROM(" +
				" SELECT t.rpt_id,t.createTime,tm.mtn_detail,tm.dispatch_status,	t.in_call,tm.dispatch_result" +
				" FROM t_r_maintain t,t_r_maintain_dispatch tm WHERE	t.rpt_id = tm.mtn_id) tp" +
				" LEFT JOIN dir_directorydetail di ON di.code_detail = tp.dispatch_status" +
				" 	AND di.`code` = 'main_mtn_dispatch_status'" +
				"  ) aa where aa.in_call='"+moble+"' and aa.rpt_id LIKE '%BX%'" +
				" ORDER BY aa.createTime DESC" +
				" ) CC LEFT JOIN t_charge_info t ON CC.rpt_id= t.work_id" +
				" ) dd";*/
		//2016-7-11
		/*String sql="select aa.rpt_id,aa.createTime,aa.mtn_detail,aa.dispatch_status,aa.code_detail_name,aa.dispatch_result from (SELECT tp.*, di.code_detail_name FROM " +
				" (SELECT	t.rpt_id,t.createTime,tm.mtn_detail,tm.dispatch_status,t.in_call, tm.dispatch_result" +
				" FROM t_r_maintain t,t_r_maintain_dispatch tm WHERE t.rpt_id = tm.mtn_id" +
				" ) tp LEFT JOIN dir_directorydetail di ON di.code_detail = tp.dispatch_status" +
				" AND di.`code` = 'main_mtn_dispatch_status') aa where aa.in_call='"+moble+"' and  aa.rpt_id like'%BX%' ORDER BY aa.createTime desc";*/
		
		String sql=" SELECT DISTINCT a.rpt_id,a.createTime,b.mtn_detail,b.dispatch_status,b.dispatch_result, c.code_detail_name, d.state "
				+ "FROM t_r_maintain a "
				+ "LEFT JOIN t_r_maintain_dispatch b ON a.rpt_id = b.mtn_id "
				+ "LEFT JOIN dir_directorydetail c on b.dispatch_status = c.code_detail AND c.code = 'main_mtn_dispatch_status' "
				+ "LEFT JOIN t_charge_info d ON a.rpt_id = d.work_id "
				+ "WHERE substring(a.rpt_id,1,2)= 'BX' "
				+ "AND a.in_call ='"+moble+"'";
		Session session = this.getSessionFactory().openSession();
		List list=session.createSQLQuery(sql).list();
		session.close();
		return list;
	}
}
