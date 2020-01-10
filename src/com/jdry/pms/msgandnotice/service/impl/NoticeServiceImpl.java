package com.jdry.pms.msgandnotice.service.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.dao.NoticeDaoImpl;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.AuditInfoService;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.msgandnotice.service.PositionService;
@Repository
@Component
public class NoticeServiceImpl implements NoticeService{
	@Resource
    private NoticeDaoImpl noticeDaoImpl;
	@Resource
	private AuditInfoService auditInfoService;
	@Resource
    private PositionService positionService;
	@Override
	@DataProvider
	public void getNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(MsgandnotNoticeMain.class);
        if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
        String msgCreater = parameter.get("msgCreater")!=null?parameter.get("msgCreater").toString():"";
		String msgStatus = parameter.get("msgStatus")!=null?parameter.get("msgStatus").toString():"";
		String msgCreateTimeStart = parameter.get("msgCreateTime")!=null?parameter.get("msgCreateTime").toString():"";
		String msgCreateTimeEnd = parameter.get("msgCreateTimeEnd")!=null?parameter.get("msgCreateTimeEnd").toString():"";
		String msgBusinessId = parameter.get("msgBusinessId")!=null?parameter.get("msgBusinessId").toString():"";
		String msgSubject = parameter.get("msgSubject")!=null?parameter.get("msgSubject").toString():"";
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+" notice where 1=1 and " +
	    		"notice.ntcCreatorId=:ntcCreatorId and notice.ntcStatus=:ntcStatus";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+" notice where 1=1 and " +
	    		"notice.ntcCreatorId=:ntcCreatorId and notice.ntcStatus=:ntcStatus";
	    map.put("ntcCreatorId",CommUser.getUserName());
	    map.put("ntcStatus","20");
	    if(!StringUtil.isEmpty(msgCreater)){
	        map.put("msgCreater",msgCreater);
	        sql+=" and notice.msgCreater =:msgCreater";
	        sqlCount+=" and notice.msgCreater =:msgCreater";
	    }
	    if(!StringUtil.isEmpty(msgStatus)){
	        map.put("msgStatus",msgStatus);
	        sql+=" and notice.msgStatus =:msgStatus";
	        sqlCount+=" and notice.msgStatus =:msgStatus";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeStart)){
			Date msgCreateTimeStartDate = DateUtil.parseToDate(msgCreateTimeStart);
	        map.put("msgCreateTime", msgCreateTimeStartDate);
	        sql+=" and notice.msgCreateTime >=:msgCreateTime";
	        sqlCount+=" and notice.msgCreateTime >=:msgCreateTime";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeEnd)){
			Date msgCreateTimeEndDate = DateUtil.parseToDate(msgCreateTimeEnd);
	        map.put("msgCreateTimeEnd", msgCreateTimeEndDate);
	        sql+=" and notice.msgCreateTime <=:msgCreateTimeEnd";
	        sqlCount+=" and notice.msgCreateTime <=:msgCreateTimeEnd";
	    }
	    if(!StringUtil.isEmpty(msgBusinessId)){
	        map.put("msgBusinessId", msgBusinessId);
	        sql+=" and notice.msgBusinessId =:msgBusinessId";
	        sqlCount+=" and notice.msgBusinessId=:msgBusinessId";
	    }
	    if(!StringUtil.isEmpty(msgSubject)){
	        map.put("msgSubject", "%"+msgSubject+"%");
	        sql+=" and notice.msgSubject like:msgSubject";
	        sqlCount+=" and notice.msgSubject like:msgSubject";
	    }
	    
        if (criteria != null) {
            HibernateUtils.createFilter(detachedCriteria, criteria);
        }
        noticeDaoImpl.find(page,sql,sqlCount,map);
 
	}
	@Override
	@DataProvider
	public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(MsgandnoticeNoticeAuditinfo.class);
        if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
        String msgCreater = parameter.get("msgCreater")!=null?parameter.get("msgCreater").toString():"";
		String msgStatus = parameter.get("msgStatus")!=null?parameter.get("msgStatus").toString():"";
		String msgCreateTimeStart = parameter.get("msgCreateTime")!=null?parameter.get("msgCreateTime").toString():"";
		String msgCreateTimeEnd = parameter.get("msgCreateTimeEnd")!=null?parameter.get("msgCreateTimeEnd").toString():"";
		String msgBusinessId = parameter.get("msgBusinessId")!=null?parameter.get("msgBusinessId").toString():"";
		String msgSubject = parameter.get("msgSubject")!=null?parameter.get("msgSubject").toString():"";
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnoticeNoticeAuditinfo.class.getName()+" notice where 1=1";
	    String sqlCount="select count(*) from "+MsgandnoticeNoticeAuditinfo.class.getName()+" notice where 1=1";
	    if(!StringUtil.isEmpty(msgCreater)){
	        map.put("msgCreater",msgCreater);
	        sql+=" and notice.msgCreater =:msgCreater";
	        sqlCount+=" and notice.msgCreater =:msgCreater";
	    }
	    if(!StringUtil.isEmpty(msgStatus)){
	        map.put("msgStatus",msgStatus);
	        sql+=" and notice.msgStatus =:msgStatus";
	        sqlCount+=" and notice.msgStatus =:msgStatus";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeStart)){
			Date msgCreateTimeStartDate = DateUtil.parseToDate(msgCreateTimeStart);
	        map.put("msgCreateTime", msgCreateTimeStartDate);
	        sql+=" and notice.msgCreateTime >=:msgCreateTime";
	        sqlCount+=" and notice.msgCreateTime >=:msgCreateTime";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeEnd)){
			Date msgCreateTimeEndDate = DateUtil.parseToDate(msgCreateTimeEnd);
	        map.put("msgCreateTimeEnd", msgCreateTimeEndDate);
	        sql+=" and notice.msgCreateTime <=:msgCreateTimeEnd";
	        sqlCount+=" and notice.msgCreateTime <=:msgCreateTimeEnd";
	    }
	    if(!StringUtil.isEmpty(msgBusinessId)){
	        map.put("msgBusinessId", msgBusinessId);
	        sql+=" and notice.msgBusinessId =:msgBusinessId";
	        sqlCount+=" and notice.msgBusinessId=:msgBusinessId";
	    }
	    if(!StringUtil.isEmpty(msgSubject)){
	        map.put("msgSubject", "%"+msgSubject+"%");
	        sql+=" and notice.msgSubject like:msgSubject";
	        sqlCount+=" and notice.msgSubject like:msgSubject";
	    }
	    
        if (criteria != null) {
            HibernateUtils.createFilter(detachedCriteria, criteria);
        }
        noticeDaoImpl.findAuditInfo(page,sql,sqlCount,map);
 
	}
	@Override
	public void saveNotice(Collection<MsgandnotNoticeMain> mes,String savePath) {
		try {
			noticeDaoImpl.saveNotice(mes,savePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateNotice(Collection<MsgandnotNoticeMain> mes) {
		noticeDaoImpl.updateNotice(mes);
		
	}
	@Override
	public void deleteNotice(Collection<MsgandnotNoticeMain> mes) {
		noticeDaoImpl.deleteNotice(mes);
		
	}
	@Override
	public void deleteNoticeById(String noticeId) {
		noticeDaoImpl.deleteNoticeById(noticeId);
		
	}
	public void saveSingleNotice(MsgandnotNoticeMain mes) {
		noticeDaoImpl.saveSingleNotice(mes);
		
	}
	@Override
	public MsgandnotNoticeMain getNoticeById(String id){
		return noticeDaoImpl.getNoticeById(id);
	}

	@Override
	public void getMyAuditNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		String username=user.getUsername();
		Map map =new HashMap<String,Object>();
		map.put("ntcAuditor", username);
		map.put("ntcStatus", "20");
	    String sql="from "+MsgandnotNoticeMain.class.getName()+" notice where notice.currentAuditer=:ntcAuditor and notice.ntcStatus=:ntcStatus";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+" notice where notice.currentAuditer=:ntcAuditor and notice.ntcStatus=:ntcStatus";
        noticeDaoImpl.find(page,sql,sqlCount,map);
	}
	@Override
	@Transactional
	/*
	 * 保存审核信息
	 * 
	 */
	public void saveAudit(Collection<MsgandnotNoticeMain> MsgandnotNoticeMains) {
		for (MsgandnotNoticeMain mes:MsgandnotNoticeMains){
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			String username=user.getUsername();
			String userCname=user.getCname();
	        String auditContent = EntityUtils.getValue(mes, "auditContent");
	        String passFlag = EntityUtils.getValue(mes, "passFlag");
	        if(!StringUtil.isEmpty(passFlag)){
	        	MsgandnoticeNoticeAuditinfo mna=new MsgandnoticeNoticeAuditinfo();
	        	mna.setNtcAuditor(userCname);
	        	mna.setNtcAuditorId(username);
	        	mna.setNtcAuditContnt(auditContent);
	        	mna.setNtcCreateTime(new Date());
	        	mna.setNtcNoticeId(mes.getNtcId());
	        	mna.setNtcPassFlag(passFlag);
	        	auditInfoService.saveSingleInfo(mna);
	        	if(passFlag.equals("1")){
	        		mes.setNtcStatus("21");
	        	}else{
	        		mes.setNtcStatus("11");
	        	}
	        	saveSingleNotice(mes);
	        }
	    }
		
	}
	@Override
	/*
     * 获取我被驳回的公告
     */
	public void getMyNoticeReturn(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcCreatorId=:ntcCreatorId and notice.ntcStatus=:ntcStatus";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcCreatorId=:ntcCreatorId and notice.ntcStatus=:ntcStatus";
	    map.put("ntcCreatorId",CommUser.getUserName());
	    map.put("ntcStatus","11");
        noticeDaoImpl.find(page,sql,sqlCount,map);

	}
	@Override
	public void getMyWaitPubNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria,String userID) {
		Map map =new HashMap<String,Object>();
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcCreatorId=:ntcCreatorId and (notice.ntcStatus=:ntcStatus or notice.ntcStatus=:ntcStatus1)";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcCreatorId=:ntcCreatorId and (notice.ntcStatus=:ntcStatus or notice.ntcStatus=:ntcStatus1)";
	    if(user!=null){
		    map.put("ntcCreatorId",user.getUsername());
	    }else{
		    map.put("ntcCreatorId",userID);
	    }
	    map.put("ntcStatus","21");
	    map.put("ntcStatus1","30");
        noticeDaoImpl.find(page,sql,sqlCount,map);
		
	}
	@Override
	public void getAllNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 order by notice.ntcCreateTime desc";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1";
        noticeDaoImpl.find(page,sql,sqlCount,map);
	}
	//app获取最新的已发布的公告
	@Override
	public void getAllPubNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus='30' order by notice.lastUpdateTime desc";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus='30'";
        noticeDaoImpl.find(page,sql,sqlCount,map);
	}
	//判断该用户是否具有审核权限
	@Override
	public boolean isAuthorityAudit(String UserId,String posiId) {
		boolean result =false;
		
        List<UserPosition> upList=positionService.findUserPositionsByPoId(posiId);
        for(UserPosition up:upList){
        	if(UserId.equals(up.getUsername())){
        		result=true;
        		break;
        	}
        }
        return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getLastMonthNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.MONTH, -1);
		Date dat1=calendar.getTime();
		String dat=DateUtil.convertDateToString(dat1, "yyyy-MM-dd");//得到前一个月 
		Date dat2=DateUtil.convertStringToDate(dat, "yyyy-MM-dd");
		Date curDate=new Date();
		map.put("lastMonth", dat2);
		map.put("curDate", curDate);
	    String sql="select notice.ntc_id,notice.ntc_content,notice.ntc_create_time,notice.ntc_lastupdatetime,notice.ntc_subject,notice.ntc_status,notice.ntc_creator,notice.ntc_creator_id,notice.ntc_dept"+
	    		" from t_msgandnotice_ntc notice where 1=1 and notice.ntc_status='30' and notice.ntc_publish_time >= DATE_ADD(now(), Interval -1 month) and notice.ntc_publish_time<=date_format(now(),'%Y%m%d%H%i%S') order by notice.ntc_lastupdatetime desc";
	   // String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    	//	" notice where 1=1 and notice.ntcStatus='30' and notice.ntcPublishTime >=:lastMonth and notice.ntcPublishTime<=:curDate";
	    List<MsgandnotNoticeMain> rtnList=noticeDaoImpl.findAllNotice(sql,map);
	    page.setEntities(rtnList);
	}
	@Override
	public void getMyCaogao(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus and notice.ntcCreatorId=:ntcCreatorId";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus and notice.ntcCreatorId=:ntcCreatorId";
	    map.put("ntcStatus","10");
	    map.put("ntcCreatorId",CommUser.getUserName());
        noticeDaoImpl.find(page,sql,sqlCount,map);
		
	}
	@Override
	public List getAllNotice() {
		Map map =new HashMap<String,Object>();
	    /*String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 order by notice.lastUpdateTime desc";*/
	    String sql1="select notice.ntc_id,notice.ntc_content,notice.ntc_create_time,notice.ntc_lastupdatetime,notice.ntc_subject,notice.ntc_status,notice.ntc_creator,notice.ntc_creator_id,notice.ntc_dept"+
	    		" from t_msgandnotice_ntc notice order by notice.ntc_lastupdatetime desc";
	    String sql2="select *"+
	    		" from t_msgandnotice_ntc notice order by notice.ntc_lastupdatetime desc";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1";
        return noticeDaoImpl.findAllNotice(sql1,null);
	}
	@Override
	public void updateSingleNotice(MsgandnotNoticeMain notice,String passFlag,String userId) {
		try {
			noticeDaoImpl.updateSingleNotice(notice,passFlag,null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	/*@Override
	public void getWyfzAuditNotice(Page page, Object object, Object object2) {
		String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		Map map =new HashMap<String,Object>();
		map.put("ntcStatus","20");
		noticeDaoImpl.find(page,sql,sqlCount,map);
	}*/
	@Override
	public void getWyfzAuditNotice(Page page, Object object, Object object2,String userId) {
		String sql="from "+MsgandnotNoticeMain.class.getName()+" notice where 1=1 and notice.ntcStatus=:ntcStatus order by notice.ntcCreateTime desc";
        String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+" notice where 1=1 and notice.ntcStatus=:ntcStatus";
        Map map =new HashMap<String,Object>();
        map.put("ntcStatus","20");
        noticeDaoImpl.find(page,sql,sqlCount,map);
      }
	@Override
	public void getWylzAuditNotice(Page page, Object object, Object object2,String userId) {
		String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus order by notice.ntcCreateTime desc";
		String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		Map map =new HashMap<String,Object>();
		map.put("ntcStatus","22");
		noticeDaoImpl.find(page,sql,sqlCount,map);
		
	}
	@Override
	public String getauditer(String type,String posiId) {
	    List<UserPosition> upList=positionService.findUserPositionsByPoId(posiId);
	    String rtnStr="";
	    for(UserPosition up:upList){
	    	rtnStr+=up.getUsername()+",";
	     }
	    int length=rtnStr.length();
	    String rtnStr1=rtnStr.substring(0,length-1);
	    return rtnStr1;
	}
	@Override
	public MsgandnotNoticeMain getNoticeByNo(String ntcNo) {
		Page<MsgandnotNoticeMain> page =new Page<MsgandnotNoticeMain>(3, 1);
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.rptId=:rptId";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.rptId=:rptId";
	    map.put("rptId", ntcNo);
        noticeDaoImpl.find(page,sql,sqlCount,map);
        List<MsgandnotNoticeMain> rtnList=(List<MsgandnotNoticeMain>) page.getEntities();
        MsgandnotNoticeMain msgandnotNoticeMain=rtnList.get(0);
        return msgandnotNoticeMain;
	}
}
