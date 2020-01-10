package com.jdry.pms.msgpublic.service.impl;

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
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.AuditInfoService;
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.msgpublic.dao.MsgPubDaoImpl;
import com.jdry.pms.msgpublic.pojo.MsgPubMain;
import com.jdry.pms.msgpublic.service.MsgPubService;
@Repository
@Component
public class MsgPubServiceImpl implements MsgPubService{
	@Resource
    private MsgPubDaoImpl msgPubDaoImpl;
	@Resource
	private AuditInfoService auditInfoService;
	@Resource
    private PositionService positionService;
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
        msgPubDaoImpl.findAuditInfo(page,sql,sqlCount,map);
 
	}
	
	@Override
	public void updateNotice(Collection<MsgPubMain> mes) {
		msgPubDaoImpl.updateNotice(mes);
		
	}
	@Override
	public void deleteNotice(Collection<MsgandnotNoticeMain> mes) {
		msgPubDaoImpl.deleteNotice(mes);
		
	}
	@Override
	public void deleteNoticeById(String noticeId) {
		msgPubDaoImpl.deleteNoticeById(noticeId);
		
	}
	public void saveSingleNotice(MsgandnotNoticeMain mes) {
		msgPubDaoImpl.saveSingleNotice(mes);
		
	}
	@Override
	public MsgPubMain getNoticeById(String id){
		return msgPubDaoImpl.getNoticeById(id);
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
	//app获取最新的已发布的公告
	@Override
	public void getAllPubNotice(Page<MsgPubMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgPubMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus='30' order by notice.ntcCreateTime desc";
	    String sqlCount="select count(*) from "+MsgPubMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus='30'";
        msgPubDaoImpl.find(page,sql,sqlCount,map);
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
	
	@Override
	public List getAllNotice() {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1";
	    String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1";
	    /*String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 order by notice.lastUpdateTime desc";*/
	    String sql1="select notice.ntc_id,notice.ntc_content,notice.ntc_create_time," +
	    		"notice.ntc_subject,notice.ntc_status,notice.ntc_creator,notice.ntc_dept"+
	    		" from t_msgpub_ntc notice where notice.ntc_status='30' order by notice.ntc_create_time desc";
	    String sql2="select *"+
	    		" from t_msgpub_ntc notice order by notice.ntc_create_time desc";
        return msgPubDaoImpl.findAllNotice(sql1,map);
	
	    
	    
        //return msgPubDaoImpl.findAllNotice(sql,map);
	}
	@Override
	public void updateSingleNotice(MsgPubMain notice,String passFlag,String userId) {
		msgPubDaoImpl.updateSingleNotice(notice,passFlag,null);
		
	}
	/*@Override
	public void getWyfzAuditNotice(Page page, Object object, Object object2) {
		String sql="from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		String sqlCount="select count(*) from "+MsgandnotNoticeMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		Map map =new HashMap<String,Object>();
		map.put("ntcStatus","20");
		msgPubDaoImpl.find(page,sql,sqlCount,map);
	}*/
	@Override
	public void getWyfzAuditNotice(Page page, Object object, Object object2,String userId) {
		String sql="from "+MsgPubMain.class.getName()+" notice where 1=1 and notice.ntcStatus=:ntcStatus order by notice.ntcCreateTime desc";
        String sqlCount="select count(*) from "+MsgPubMain.class.getName()+" notice where 1=1 and notice.ntcStatus=:ntcStatus";
        Map map =new HashMap<String,Object>();
        map.put("ntcStatus","20");
        msgPubDaoImpl.find(page,sql,sqlCount,map);
      }
	@Override
	public void getWylzAuditNotice(Page page, Object object, Object object2,String userId) {
		String sql="from "+MsgPubMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		String sqlCount="select count(*) from "+MsgPubMain.class.getName()+
	    		" notice where 1=1 and notice.ntcStatus=:ntcStatus";
		Map map =new HashMap<String,Object>();
		map.put("ntcStatus","22");
		msgPubDaoImpl.find(page,sql,sqlCount,map);
		
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
	public void getAllNotice(Page<MsgPubMain> page, Object parameter,
			Object criteria) {

		Map map =new HashMap<String,Object>();
	    String sql="from "+MsgPubMain.class.getName()+
	    		" notice where 1=1 order by notice.ntcCreateTime desc";
	    String sqlCount="select count(*) from "+MsgPubMain.class.getName()+
	    		" notice where 1=1";
        msgPubDaoImpl.find(page,sql,sqlCount,map);
	
		
	}
	@Override
	public void getNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getMyAuditNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String saveNotice(List<MsgPubMain> saveList, String ntcPicPath) {
		String ntcId="";
		try {
			ntcId=msgPubDaoImpl.saveNotice(saveList,ntcPicPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ntcId;
	}
}
