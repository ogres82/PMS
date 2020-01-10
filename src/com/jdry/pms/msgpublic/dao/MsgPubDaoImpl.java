package com.jdry.pms.msgpublic.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.bdf2.core.service.impl.DefaultDeptService;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.controller.PushClient;
import com.jdry.pms.msgandnotice.pojo.MsgandNoticePicture;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.MsgAndNoticeConstant;
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.msgandnotice.service.impl.PositionServiceImpl;
import com.jdry.pms.msgpublic.pojo.MsgPubMain;
import com.soft.service.GrapService;
@Repository
@Transactional
public class MsgPubDaoImpl extends HibernateDao{
	@Resource
	CommUtil commUtil;
	@Resource
	PositionService positionService;
	@Resource
	DefaultDeptService deptService;
	@Resource
	GrapService grapService;
	@Resource
	TaskService taskService;
	@SuppressWarnings("unused")
	//保存公告内容
	public void saveNotice(Collection<MsgandnotNoticeMain> emps,String savePath) throws Exception {
		Session session = this.getSessionFactory().openSession();
		String businessId="";
	    try{
	        for (MsgandnotNoticeMain emp : emps) {
	            	String id=CommUtil.getGuuid();
	            	businessId = commUtil.getBusinessId("bx");
	            	String processInstanceId = grapService.startBussniseTask("gonggao");
	            	DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
	            	//直接把查询方法都放在positionService里面了
	            	UserDept userDept= positionService.findUserDeptByUserId(user.getUsername());
	            	DefaultDept dept=new DefaultDept();
	            	if(userDept!=null){
	            		String deptId=userDept.getDeptId();
	            		dept=(DefaultDept) deptService.loadDeptById(deptId);
	            	}
	            	//UserPosition userPosition=positionService.findUserByPoId("20160407001");
	            	//String curAuditer=userPosition.getUsername();
	            	emp.setNtcId(id);
	            	emp.setProcessInstanceId(processInstanceId);
	            	//emp.setCurrentAuditer(curAuditer);
	            	emp.setNtcDept(dept.getName());
	            	if(!StringUtil.isEmpty(savePath)){
						String[] paths=savePath.split(";");
	            		Set<MsgandNoticePicture> picSet=new HashSet<MsgandNoticePicture>();
	            		for(int i=0;i<paths.length;i++){
	            			MsgandNoticePicture msgandNoticePicture=new MsgandNoticePicture();
	            			msgandNoticePicture.setFilePath(paths[i]);
	            			msgandNoticePicture.setNtcId(id);
	            			msgandNoticePicture.setNtcNotice(emp);
	            			session.save(msgandNoticePicture);
	            			picSet.add(msgandNoticePicture);
	            		}
	            		emp.setMsgandnoticePics(picSet);
					}
	            	session.save(emp);
	            	grapService.completeBussniseTask(processInstanceId, user.getCname(),null);
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	
	public void updateNotice(Collection<MsgPubMain> mes) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (MsgPubMain emp : mes) {
	        	updateSingleNotice(emp,null,null);
            }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	
	public void deleteNotice(Collection<MsgandnotNoticeMain> emps) {
		Session session = this.getSessionFactory().openSession();
		String businessId="";
	    try{
	        for (MsgandnotNoticeMain emp : emps) {	            	
	        	session.delete(emp);
            }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public void deleteNoticeById(String noticeId) {
		Session session = this.getSessionFactory().openSession();
		String businessId="";
		MsgPubMain notice=null;
	    try{
	    	notice=(MsgPubMain) session.get(MsgPubMain.class,noticeId);
	    	session.delete(notice);
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public MsgPubMain getNoticeById(String id){
		Session session = this.getSessionFactory().openSession();
		MsgPubMain notice=null;
		try{
			 notice=(MsgPubMain) session.get(MsgPubMain.class,id);
	    }finally{
	        session.flush();
	        session.close();
	    }	
		return notice;
	}
	public void findAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page,String sql,String sqlCount,Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void saveSingleNotice(MsgandnotNoticeMain emps) {
		Session session = this.getSessionFactory().openSession();
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
	    try{
	            EntityState state=EntityUtils.getState(emps);
	            if (state.equals(EntityState.NEW)) {
	            	CommUtil cu=new CommUtil();
	            	String id=CommUtil.getGuuid();
	            	emps.setNtcId(id);
	                session.save(emps);
	            } else if (state.equals(EntityState.MODIFIED)) {
	            	String status=emps.getNtcStatus();
	            	String processInstanceId=emps.getProcessInstanceId();
	            	Map condition=new HashMap();
	            	if(status.equals("21")){
	            		condition.put("passFlag", 0);
	            		grapService.completeBussniseTask(processInstanceId,user.getCname(),condition);

	            	}else if(status.equals("11")){
	            		condition.put("passFlag", 1);
	            		grapService.completeBussniseTask(processInstanceId,user.getCname(),condition);
	            	}
	                session.update(emps);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(emps);
	            }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public List findAllNotice(String sql,Map map) {
		List result=null;
		Session session = this.getSessionFactory().openSession();
		List<MsgPubMain> rtnList=new ArrayList<MsgPubMain>();
		try {
			result=session.createSQLQuery(sql).list();
			if(result!=null){
				for (int i=0;i<result.size();i++){
					MsgPubMain msgPubMain=new MsgPubMain();
					Object[] obj = (Object[])result.get(i);
					String ntc_id = obj[0]==null?"":obj[0].toString();
					String ntc_content=obj[1]==null?"":obj[1].toString();
					String ntc_create_time=obj[2]==null?"":obj[2].toString();
					Date ntcCreateTime=DateUtil.convertStringToDate(ntc_create_time, "yyyy-MM-dd HH:mm:ss");
					String ntc_subject=obj[3]==null?"":obj[3].toString();
					String ntc_status=obj[4]==null?"":obj[4].toString();
					String ntc_creator=obj[5]==null?"":obj[5].toString();
					String ntc_dept=obj[6]==null?"":obj[6].toString();
					msgPubMain.setNtcId(ntc_id);
					msgPubMain.setNtcContent(ntc_content);
					msgPubMain.setNtcCreateTime(ntcCreateTime);
					msgPubMain.setNtcSubject(ntc_subject);
					msgPubMain.setNtcStatus(ntc_status);
					msgPubMain.setNtcCreator(ntc_creator);
					msgPubMain.setNtcDept(ntc_dept);
					rtnList.add(msgPubMain);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
	        session.flush();
	        session.close();
	    }	
		return rtnList;
		/*List result=null;
		try {
			return this.query(sql, map);
			//this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;*/
	}
	public List findAllNoticeApp(String sql,Map map,String userId) {
		List result=null;
		Session session = this.getSessionFactory().openSession();
		SQLQuery query=session.createSQLQuery(sql);
		query.setParameter(0, userId);
		List list = query.list();
		//query.setParameter("", "20");
		try {
			return list;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;
	}
	public void updateSingleNotice(MsgPubMain emp,String passFlag,String userId) {
		Session session = this.getSessionFactory().openSession();
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();

		try{
			String proInsId=emp.getProcessInstanceId();
			Map condition=new HashMap();
			condition.put("passFlag",passFlag);
			Task task = taskService.createTaskQuery().processInstanceId(proInsId).singleResult();
			if(null != task){
				String taskName=task.getName();
				if(null != taskName && taskName.equals("部门审核")&&passFlag.equals("0")){
					 emp.setNtcStatus("11");
					 emp.setCurrentAuditer(emp.getNtcCreatorId());
				  }else if(null != taskName && taskName.equals("部门审核")&&passFlag.equals("1")){
					  emp.setNtcStatus("22");
					  Properties property = PositionServiceImpl.getProperty();
					  String wylzPosiId = property.getProperty(MsgAndNoticeConstant.PROPERTY_WYLZ_KEY);
					  UserPosition userPosition=positionService.findUserByPoId(wylzPosiId);
					  emp.setCurrentAuditer(userPosition.getUsername());
					  emp.setNtcAuditor(wylzPosiId);
				  }else if(null != taskName && taskName.equals("分管领导审核")&&passFlag.equals("0")){
					  emp.setNtcStatus("11");
					  emp.setNtcAuditor("20160407001");
					  emp.setCurrentAuditer(emp.getNtcCreatorId());
				  }else if(null != taskName && taskName.equals("分管领导审核")&&passFlag.equals("1")){
					  emp.setNtcStatus("21");
					  emp.setCurrentAuditer(emp.getNtcCreatorId());
				}else if(null != taskName && taskName.equals("公告、消息编辑")){
					  emp.setNtcStatus("20");
					  UserPosition userPosition=positionService.findUserByPoId("20160407001");
					  emp.setCurrentAuditer(userPosition.getUsername());
				}else if(null != taskName && taskName.equals("公告消息发布")){
					  emp.setNtcStatus("30");
					  emp.setCurrentAuditer("");
					  emp.setNtcPublishTime(new Date());
					  Map ext=new HashMap();
					  ext.put("id", emp.getNtcId());
					  PushClient.sendPush(emp.getNtcSubject(), "您收到一条最新公告", ext,null);

				}
				
			}
			if(user!=null){
				grapService.completeBussniseTask(proInsId, user.getCname(), condition);
			}else{
				grapService.completeBussniseTask(proInsId, userId, condition);
			}
            session.update(emp);
            
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public MsgandnotNoticeMain getLastesdNotice(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
	public void find(Page<MsgPubMain> page, String sql, String sqlCount, Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String saveNotice(List<MsgPubMain> saveList, String ntcPicPath) {

		Session session = this.getSessionFactory().openSession();
		String businessId="";
		String ntcId="";
	    try{
	        for (MsgPubMain emp : saveList) {
	            	String id=CommUtil.getGuuid();
	            	ntcId=id;
	            	businessId = commUtil.getBusinessId("bx");
	            	String processInstanceId = grapService.startBussniseTask("gonggao");
	            	DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
	            	//直接把查询方法都放在positionService里面了
	            	UserDept userDept= positionService.findUserDeptByUserId(user.getUsername());
	            	DefaultDept dept=new DefaultDept();
	            	if(userDept!=null){
	            		String deptId=userDept.getDeptId();
	            		dept=(DefaultDept) deptService.loadDeptById(deptId);
	            	}
	            	emp.setNtcId(id);
	            	emp.setProcessInstanceId(processInstanceId);
	            	//emp.setCurrentAuditer(curAuditer);
	            	emp.setNtcDept(dept.getName());
	            	session.save(emp);
	            	ntcId=emp.getNtcId();
	            	grapService.completeBussniseTask(processInstanceId, user.getCname(),null);
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }
	    return ntcId;
		
	}
}
