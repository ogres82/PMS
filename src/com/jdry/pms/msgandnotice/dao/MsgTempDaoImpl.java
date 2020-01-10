package com.jdry.pms.msgandnotice.dao;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;

@Repository
@Transactional
public class MsgTempDaoImpl extends HibernateDao{
	
	public void saveMsgTemp(Collection<MessageTempMain> mtms) {

		Session session = this.getSessionFactory().openSession();
	    try{
	        for (MessageTempMain emp : mtms) {
	            	CommUtil cu=new CommUtil();
	            	String id=CommUtil.getGuuid();
	            	emp.setMsgTempId(id);
	                session.save(emp);
	            
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	
		
	}

	public void getMsgTemp(Page<MessageTempMain> page,
			String sql,String sqlCount,Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Collection<MessageTempMain> getAllMsgTemp(String hql){
		return this.query(hql);
	}
	
	public Collection<MessageTempMain> getTempById(String hql,Map parameterMap){
		return this.query(hql, parameterMap);
	}
	
	public void updateTemp(Collection<MessageTempMain> emps) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (MessageTempMain emp : emps) {
	        	/*String status=emp.getNtcStatus();
        	    Long processInstanceId=emp.getProcessInstanceId();
        	    if(status.equals("20")){
            	    flowUtil.completeTask(processInstanceId, null);
        	    }else if(status.equals("30")){
            	    flowUtil.completeTask(processInstanceId, null);
        	   }*/
            session.update(emp);
            }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public void deleteTempById(String tempId){

		Session session = this.getSessionFactory().openSession();
		String businessId="";
		MessageTempMain notice=null;
	    try{
	    	notice=(MessageTempMain) session.get(MessageTempMain.class,tempId);
	    	session.delete(notice);
	    }finally{
	        session.flush();
	        session.close();
	    }		
	
	}
}
