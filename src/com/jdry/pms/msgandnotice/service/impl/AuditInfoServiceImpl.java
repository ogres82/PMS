package com.jdry.pms.msgandnotice.service.impl;

import java.util.ArrayList;
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

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.dao.AuditInfoDaoImpl;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.AuditInfoService;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.system.service.PrivilegeConfigService;
@Repository
@Component
public class AuditInfoServiceImpl implements AuditInfoService{

	@Resource
	private AuditInfoDaoImpl auditInfoDaoImpl;
	@Resource
    public NoticeService noticeService;
	@Resource
	PrivilegeConfigService configService;
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
		auditInfoDaoImpl.findAuditInfo(page,sql,sqlCount,map);
		// TODO Auto-generated method stub
		
	}
	@Override
	@DataResolver
	public void saveInfo(Collection<MsgandnoticeNoticeAuditinfo> mes) {
		auditInfoDaoImpl.saveAuditInfo(mes);
		
	}
	@Override
	public void saveSingleInfo(MsgandnoticeNoticeAuditinfo mes) {
		auditInfoDaoImpl.saveSingleInfo(mes);		
	}
	///根据用户ID统计该用户的待审核公告数量
	@Override
	public int getCountAuditNotice(String userId) {
		Page<MsgandnotNoticeMain> page =new Page<MsgandnotNoticeMain>(200, 1);
		String wyfzPosiId = configService.loadPCByType("01").getPositionId(); //获取一级审核岗位ID
		String wylzPosiId = configService.loadPCByType("02").getPositionId(); //获取二级审核岗位ID
//		Properties property = PositionServiceImpl.getProperty();
//		String wyfzPosiId = property.getProperty(MsgAndNoticeConstant.PROPERTY_WYFZ_KEY);
//		String wylzPosiId = property.getProperty(MsgAndNoticeConstant.PROPERTY_WYLZ_KEY);
		boolean isWyfz=noticeService.isAuthorityAudit(userId, wyfzPosiId);
		boolean isWylz=noticeService.isAuthorityAudit(userId,wylzPosiId);
		if(isWyfz){
			noticeService.getWyfzAuditNotice(page, null, null,userId);
		}else if(isWylz){
			noticeService.getWylzAuditNotice(page, null, null,userId);					
		}else{
			List<MsgandnotNoticeMain> noticeList=new ArrayList<MsgandnotNoticeMain>();
			page.setEntities(noticeList);
		}
		int count=page.getEntityCount();
		return count;
	}
	
}
