package com.jdry.pms.assignWork.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;

/**
 * 	物业派工物管端（投诉、建议）持久层
 * @author hezuping
 *
 */
@Repository
public class PropertyManageComplaintDao extends HibernateDao 
{

	public List getDispatchList(String dispatch_id)
	{
		
		
		return null;	
	}
   
	public List getDispatchHistory(String handle_id)
	{
		
		/*String sql="select * from (SELECT tm.addres,tm.event_content,tm.createTime,tm.event_state,di.code_detail_name ,tm.createby,tm.rpt_id FROM t_r_maintain tm"+
				" LEFT JOIN dir_directorydetail di ON tm.event_state = di.code_detail AND di.`code` = 'main_mtn_dispatch_status') cc" +
				" where cc.createby='"+handle_id+"' and cc.rpt_id like '%TS%' order by createTime desc";*/
		
		String sqlStr="SELECT * FROM(SELECT temp.addres,temp.event_content,temp.createTime,temp.event_state,di.code_detail_name,temp.createby,temp.rpt_id" +
				" FROM(SELECT tm.address as addres,tm.event_content,tm.createTime,tm.event_state,tt.comp_operator_id as createby,tm.rpt_id FROM t_r_maintain tm,t_r_maintain_complaint tt" +
				" WHERE tm.rpt_id = tt.mtn_id AND tt.comp_emergency != 0) temp LEFT JOIN dir_directorydetail di ON temp.event_state = di.code_detail" +
				" AND di.`code` = 'main_mtn_dispatch_status') cc WHERE" +
				" cc.createby ='"+handle_id+"' AND cc.rpt_id LIKE '%TS%'ORDER BY createTime DESC";
		
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sqlStr).list();
		session.flush();
		session.close();
		return list;
	}

	public List getDisPatchComplaintDetail(String mtr_id)
	{
		Session session = this.getSessionFactory().openSession();
		String sql="select * from (" +
				" SELECT tm.event_content as comp_detail,t.comp_createTime,t.comp_status,t.comp_reply, tm.rpt_name, tm.in_call,tm.address,t.mtn_id, t.comp_finish_time,t.comp_result FROM T_R_MAINTAIN_COMPLAINT t LEFT JOIN t_r_maintain tm ON t.mtn_id = tm.rpt_id" +
				" ) cc where 1=1 and cc.mtn_id='"+mtr_id+"'";
		List list=session.createSQLQuery(sql).list();
		session.close();
		return list;
	}

	public VWorkCompEntity getComplaintHandle(String mtn_id)
	{
		String sql="from "+VWorkCompEntity.class.getName()+" comp where  comp.mtn_id='"+mtn_id+"'";
		VWorkCompEntity vw=(VWorkCompEntity) this.query(sql).get(0);
		return vw;
	}
}
