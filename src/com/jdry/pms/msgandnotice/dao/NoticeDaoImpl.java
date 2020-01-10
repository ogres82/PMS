package com.jdry.pms.msgandnotice.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.system.service.PrivilegeConfigService;
import com.soft.service.GrapService;
@Repository
@Transactional
public class NoticeDaoImpl extends HibernateDao{
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
	@Resource
	PrivilegeConfigService configService;
	@SuppressWarnings("unused")
	public void find(Page<MsgandnotNoticeMain> page,String sql,String sqlCount,Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	//保存公告内容
	public void saveNotice(Collection<MsgandnotNoticeMain> emps,String savePath) throws Exception {
		Session session = this.getSessionFactory().openSession();
		String businessId="";
	    try{
	        for (MsgandnotNoticeMain emp : emps) {
	            	String id=CommUtil.getGuuid();
	            	businessId = commUtil.getBusinessId("GG");
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
	            	emp.setRptId(businessId);
	            	emp.setProcessInstanceId(processInstanceId);
	            	emp.setLastUpdateTime(new Date());
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
	
	public void updateNotice(Collection<MsgandnotNoticeMain> emps) {
		Session session = this.getSessionFactory().openSession();
	    try{
	        for (MsgandnotNoticeMain emp : emps) {
	        	updateSingleNotice(emp,null,null);
            }
	    } catch (Exception e) {
			e.printStackTrace();
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
		MsgandnotNoticeMain notice=null;
	    try{
	    	notice=(MsgandnotNoticeMain) session.get(MsgandnotNoticeMain.class,noticeId);
	    	session.delete(notice);
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public MsgandnotNoticeMain getNoticeById(String id){
		Session session = this.getSessionFactory().openSession();
		MsgandnotNoticeMain notice=null;
		try{
			 notice=(MsgandnotNoticeMain) session.get(MsgandnotNoticeMain.class,id);
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
		List<MsgandnotNoticeMain> rtnList=new ArrayList<MsgandnotNoticeMain>();
		try {
			if(map!=null){
				SQLQuery sqlQuery=session.createSQLQuery(sql);
				/*sqlQuery.setDate("lastMonth", (Date)map.get("lastMonth"));
				sqlQuery.setDate("curDate", (Date)map.get("curDate"));*/
				result=sqlQuery.list();
			}else{
				result=session.createSQLQuery(sql).list();
			}
			if(result!=null){
				for (int i=0;i<result.size();i++){
					MsgandnotNoticeMain msgandnotNoticeMain=new MsgandnotNoticeMain();
					Object[] obj = (Object[])result.get(i);
					String ntc_id = obj[0]==null?"":obj[0].toString();
					String ntc_content=obj[1]==null?"":obj[1].toString();
					String ntc_create_time=obj[1]==null?"":obj[2].toString();
					Date ntcCreateTime=DateUtil.convertStringToDate(ntc_create_time, "yyyy-MM-dd HH:mm:ss");
					String  last_updatetime=obj[1]==null?"":obj[3].toString();
					Date lastUpdatetime=DateUtil.convertStringToDate(last_updatetime, "yyyy-MM-dd HH:mm:ss");
					String ntc_subject=obj[4]==null?"":obj[4].toString();
					String ntc_status=obj[5]==null?"":obj[5].toString();
					String ntc_creator=obj[6]==null?"":obj[6].toString();
					String ntc_creator_id=obj[7]==null?"":obj[7].toString();
					String ntc_dept=obj[8]==null?"":obj[8].toString();
					msgandnotNoticeMain.setNtcId(ntc_id);
					msgandnotNoticeMain.setNtcContent(ntc_content);
					msgandnotNoticeMain.setNtcCreateTime(ntcCreateTime);
					msgandnotNoticeMain.setLastUpdateTime(lastUpdatetime);
					msgandnotNoticeMain.setNtcSubject(ntc_subject);
					msgandnotNoticeMain.setNtcStatus(ntc_status);
					msgandnotNoticeMain.setNtcCreator(ntc_creator);
					msgandnotNoticeMain.setNtcCreatorId(ntc_creator_id);
					msgandnotNoticeMain.setNtcDept(ntc_dept);
					rtnList.add(msgandnotNoticeMain);
				}
			}
			//return rtnList;
			//return this.query(sql, map);
			//this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
	        session.flush();
	        session.close();
	    }	
		return rtnList;
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
	public void updateSingleNotice(MsgandnotNoticeMain notice,String passFlag,String userId) throws Exception {
		Session session = this.getSessionFactory().openSession();
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		
		
		//待修改：配置审核岗位
		String wyfzId = configService.loadPCByType("01").getPositionId(); //获取一级审核岗位ID
		String wylzId = configService.loadPCByType("02").getPositionId(); //获取二级审核岗位ID
//		String wyfzId=FileUtil.getProperties("com/jdry/pms/msgandnotice/ApplicationResources.properties").getProperty("wyfzPosiId");
//		String wylzId=FileUtil.getProperties("com/jdry/pms/msgandnotice/ApplicationResources.properties").getProperty("wylzPosiId");
		try{
			String proInsId=notice.getProcessInstanceId();
			Map condition=new HashMap();
			condition.put("passFlag",passFlag);
			Task task = taskService.createTaskQuery().processInstanceId(proInsId).singleResult();
			if(null != task){
				String taskName=task.getName();
				if(null != taskName && taskName.equals("部门审核")&&passFlag.equals("0")){
					 notice.setNtcStatus("11");
					 notice.setCurrentAuditer(notice.getNtcCreatorId());
					 notice.setNtcAuditor(wyfzId);
					 notice.setLastUpdateTime(new Date());
				  }else if(null != taskName && taskName.equals("部门审核")&&passFlag.equals("1")){
					  notice.setNtcStatus("22");
					  notice.setNtcAuditor(wylzId);
					  notice.setLastUpdateTime(new Date());
				  }else if(null != taskName && taskName.equals("分管领导审核")&&passFlag.equals("0")){
					  notice.setNtcStatus("11");
					  notice.setNtcAuditor(wyfzId);
					  notice.setCurrentAuditer(notice.getNtcCreatorId());
					  notice.setLastUpdateTime(new Date());
				  }else if(null != taskName && taskName.equals("分管领导审核")&&passFlag.equals("1")){
					  notice.setNtcStatus("21");
					  notice.setCurrentAuditer(notice.getNtcCreatorId());
					  notice.setLastUpdateTime(new Date());
				}else if(null != taskName && taskName.equals("公告、消息编辑")){
					  notice.setNtcStatus("20");
					  
					  
					//待修改：配置审核岗位
					  UserPosition userPosition=positionService.findUserByPoId(wyfzId);
					  notice.setCurrentAuditer(userPosition.getUsername());
					  notice.setLastUpdateTime(new Date());
				}else if(null != taskName && taskName.equals("公告消息发布")){
					  notice.setNtcStatus("30");
					  notice.setCurrentAuditer("");
					  notice.setNtcPublishTime(new Date());
					  notice.setLastUpdateTime(new Date());
					  Map ext=new HashMap();
					  ext.put("id", notice.getNtcId());
					  PushClient.sendPush(notice.getNtcSubject(), "您收到一条最新公告", ext,null);

				}
				
			}
			if(user!=null){
				grapService.completeBussniseTask(proInsId, user.getCname(), condition);
			}else{
				grapService.completeBussniseTask(proInsId, userId, condition);
			}
            session.update(notice);
            
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public MsgandnotNoticeMain getLastesdNotice(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
}
