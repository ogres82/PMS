package com.jdry.pms.assignWork.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

@Repository
@Transactional
public class AssignWorkServiceInterfaceDao extends HibernateDao {

	public List queryBasicInfo(String phone) {
		String sql = "select * from v_area_build_house_allowner_rela  t where t.phone='"+phone+"'";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		session.close();
		return result;

	}

	@SuppressWarnings("rawtypes")
	public List queryComplaintHistoryByPhone(String phone)
	{
		String sql="select tr.mtn_id,tr.comp_createTime,tr.comp_status,t.code_detail_name,m.createTime, m.event_content as comp_detail,tr.comp_reply from T_R_MAINTAIN_COMPLAINT tr, dir_directorydetail t,t_r_maintain m  where  EXISTS(select t.rpt_id from t_r_maintain t where t.in_call='"+phone+"' and t.rpt_id=tr.mtn_id) " +
				" and tr.comp_status=t.code_detail and  t.`code`='main_mtn_dispatch_status' and m.rpt_id=tr.mtn_id ORDER BY m.createTime desc";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		session.close();
		return result;
	}

	@SuppressWarnings("rawtypes")
	public List queryComplaintDetailByRptId(String mtn_id) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select dd.event_content as comp_detail,dd.createTime,dd.comp_status,dd.comp_visit_record,dd.comp_operator,dd.comp_createTime,dd.MOBILE_,b.NAME_,dd.comp_result from ( SELECT ");
		sql.append(" cc.event_content,cc.comp_detail,cc.createTime,cc.comp_status,cc.comp_visit_record,cc.comp_operator,cc.comp_createTime,cc.MOBILE_, cc.USERNAME_, cc.comp_result");
		sql.append(" FROM( SELECT  mt.event_content,t.mtn_id,t.comp_detail,mt.createTime,	t.comp_status,t.comp_reply as comp_visit_record,u.CNAME_ AS comp_operator,t.comp_createTime,  u.MOBILE_, u.USERNAME_ ,t.comp_result FROM t_r_maintain mt ");
		sql.append(" LEFT JOIN T_R_MAINTAIN_COMPLAINT t ON t.mtn_id = mt.rpt_id");
		sql.append(" LEFT JOIN bdf2_user u ON t.comp_operator_id = u.USERNAME_) cc ");
		sql.append(" where cc.mtn_id = '"+mtn_id+"' order by cc.createTime desc");
		sql.append(" )dd LEFT JOIN bdf2_user_dept ud on ud.USERNAME_=dd.USERNAME_");
		sql.append("  left join  bdf2_dept b on b.ID_=ud.DEPT_ID_");
		/*String sql="SELECT cc.comp_detail, cc.createTime,cc.comp_status,cc.comp_visit_record,cc.comp_operator,cc.comp_createTime" +
				" FROM(SELECT t.mtn_id,t.comp_detail,mt.createTime,t.comp_status,t.comp_visit_record,u.CNAME_ AS comp_operator,t.comp_createTime FROM" +
				" t_r_maintain mt LEFT JOIN T_R_MAINTAIN_COMPLAINT t ON t.mtn_id = mt.rpt_id LEFT JOIN bdf2_user u ON t.comp_operator_id = u.USERNAME_) cc" +
				"  where cc.mtn_id = '"+mtn_id+"' order by cc.createTime desc" ;*/
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql.toString()).list();
		session.close();
		return result;
	}

}
