package com.jdry.pms.report.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
@Repository
@Transactional
public class ChargeReportDao extends HibernateDao{
	
	public Collection getChargeInfoByArea(String month){
		Session session = this.getSessionFactory().openSession();
		List list = new ArrayList();
		if(month.isEmpty()){
			String sql = "SELECT v.community_name,v.community_id,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount) FROM v_report_charge v"+
					     " GROUP BY v.community_id";
			list = session.createSQLQuery(sql).list();
		}else if(month.length()==6){
			String sql = "SELECT v.community_name,v.community_id,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount),DATE_FORMAT(v.end_time, '%Y%m') months FROM v_report_charge v"+
			             " WHERE DATE_FORMAT(v.end_time, '%Y%m')='"+month+"' GROUP BY months,v.community_id";
			list = session.createSQLQuery(sql).list();
		}else{
			String sql = "SELECT v.community_name,v.community_id,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount),DATE_FORMAT(v.end_time, '%Y') years FROM v_report_charge v"+
					     " WHERE DATE_FORMAT(v.end_time, '%Y')='"+month+"' GROUP BY years,v.community_id";
			list = session.createSQLQuery(sql).list();
		}
		session.flush();
		session.close();
		return list;
	}
	
	public Collection getChargeInfoByBuild(String date,String communityId){
		Session session = this.getSessionFactory().openSession();
		List list = new ArrayList();
		if(date.isEmpty()){
			String sql = "SELECT v.community_name,v.community_id,v.storied_build_id,v.storied_build_name,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount)"+
					     " FROM v_report_charge v WHERE v.community_id='"+communityId+"' GROUP BY v.storied_build_id";
			list = session.createSQLQuery(sql).list();
		}else if(date.length()==6){
			String sql = "SELECT v.community_name,v.community_id,v.storied_build_id,v.storied_build_name,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount),DATE_FORMAT(v.end_time, '%Y%m') months FROM v_report_charge v"+
			             " WHERE DATE_FORMAT(v.end_time, '%Y%m')='"+date+"' AND v.community_id='"+communityId+"' GROUP BY months,v.storied_build_id";
			list = session.createSQLQuery(sql).list();
		}else{
			String sql = "SELECT v.community_name,v.community_id,v.storied_build_id,v.storied_build_name,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount),DATE_FORMAT(v.end_time, '%Y') years FROM v_report_charge v"+
					     " WHERE DATE_FORMAT(v.end_time, '%Y')='"+date+"' AND v.community_id='"+communityId+"' GROUP BY years,v.storied_build_id";
			list = session.createSQLQuery(sql).list();
		}
		session.flush();
		session.close();
		return list;
	}
	
	public Collection getChargeInfoByType(String month){
		Session session = this.getSessionFactory().openSession();
		List list = new ArrayList();
		if(month.isEmpty()){
			String sql = "SELECT v.charge_type_name,v.charge_type_no,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount) FROM v_report_charge v"+
	                 " GROUP BY v.charge_type_no";
			list = session.createSQLQuery(sql).list();
		}else if(month.length()==6){
			String sql = "SELECT v.charge_type_name,v.charge_type_no,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount), DATE_FORMAT(v.end_time, '%Y%m') months FROM v_report_charge v"+
	                 " WHERE DATE_FORMAT(v.end_time, '%Y%m')='"+month+"' GROUP BY months,v.charge_type_no";
			list = session.createSQLQuery(sql).list();
		}else{
			String sql = "SELECT v.charge_type_name,v.charge_type_no,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount), DATE_FORMAT(v.end_time, '%Y') years FROM v_report_charge v"+
	                 " WHERE DATE_FORMAT(v.end_time, '%Y')='"+month+"' GROUP BY years,v.charge_type_no";
			list = session.createSQLQuery(sql).list();
		}
		session.flush();
		session.close();
		return list;
	}
	public Collection getChargeInfoByHis(){
		String sql = "SELECT DATE_FORMAT(v.end_time,'%Y%m') months,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount) FROM v_report_charge v"+
	                 " GROUP BY months";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public Collection getChargeInfoByYear(){
		String sql = "SELECT DATE_FORMAT(v.end_time,'%Y') years,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount) FROM v_report_charge v"+
	                 " GROUP BY years";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	public Collection getChargeInfoByMonth(String year){
		String sql = "SELECT * FROM (SELECT DATE_FORMAT(v.end_time,'%Y%m') months,SUM(v.paid_amount),SUM(v.receive_amount),SUM(v.arrearage_amount) FROM v_report_charge v"+
	                 " GROUP BY months) a where a.months LIKE '%"+year+"%'";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	//年度审计报表明细
	public List<Map<String,Object>> queryAuditDetail(String beginDate,String endDate) {

		Session session = this.getSessionFactory().openSession();	
		Transaction tx= session.beginTransaction(); 
	    SQLQuery query = session.createSQLQuery("{CALL pro_audit_select(?,?)}");
		query.setString(0,beginDate);
		query.setString(1, endDate);
	    query.executeUpdate();
	    tx.commit();
		String sql = "select a.* FROM T_AUDIT_PROPERTY_REP a Where 1=1 ";	
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();		  
		session.flush();
		session.close();
		return list;
	} 
	//年度审计报表统计
	public List<Map<String,Object>> queryAuditRep(String beginDate,String endDate) {
	
		Session session = this.getSessionFactory().openSession();	
		Transaction tx= session.beginTransaction(); 
	    SQLQuery query = session.createSQLQuery("{CALL pro_audit_select(?,?)}");
		query.setString(0,beginDate);
		query.setString(1, endDate);
	    query.executeUpdate();
	    tx.commit();
	    
		String sql = "select a.* FROM V_AUDIT_PROPERTY_REP a Where 1=1 ";	
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();		  
		session.flush();
		session.close();
		return list;
	} 
}
