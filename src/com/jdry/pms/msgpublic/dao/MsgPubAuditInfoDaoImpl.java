package com.jdry.pms.msgpublic.dao;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgpublic.pojo.MsgPubAuditinfo;
@Repository
@Transactional
public class MsgPubAuditInfoDaoImpl extends HibernateDao{
	public void findAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page,String sql,String sqlCount,Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void saveAuditInfo(Collection<MsgandnoticeNoticeAuditinfo> emps) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (MsgandnoticeNoticeAuditinfo emp : emps) {
	            EntityState state=EntityUtils.getState(emp);
	            if (state.equals(EntityState.NEW)) {
	            	CommUtil cu=new CommUtil();
	            	String id=CommUtil.getGuuid();
	            	emp.setNtcAuditId(id);
	                session.save(emp);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(emp);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(emp);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	
	public void saveSingleInfo(MsgPubAuditinfo emp) {
		Session session = this.getSessionFactory().openSession();
	    try{
	            EntityState state=EntityUtils.getState(emp);
	            if (state.equals(EntityState.NEW)) {
	            	CommUtil cu=new CommUtil();
	            	String id=CommUtil.getGuuid();
	            	emp.setNtcAuditId(id);
	                session.save(emp);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(emp);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(emp);
	            }else{
	            	CommUtil cu=new CommUtil();
	            	String id=CommUtil.getGuuid();
	            	emp.setNtcAuditId(id);
	                session.save(emp);
	            }
	        
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
}
