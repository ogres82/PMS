package com.jdry.pms.msgandnotice.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
/**
*
* @author 钟涛
*
*/
@Repository
public interface NoticeService {
	public void getNotice(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria)throws Exception;
	
	public void saveNotice(Collection<MsgandnotNoticeMain> mes, String savePath);
	
	
	public void updateNotice(Collection<MsgandnotNoticeMain> mes);
	
	
	public void deleteNotice(Collection<MsgandnotNoticeMain> mes);
	
	
	public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page, 
			Map<String, Object> parameter,Criteria criteria) throws Exception;
	
	public void getMyAuditNotice(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria);
	
	public void saveAudit(Collection<MsgandnotNoticeMain> mes);
	
	
	public void getMyNoticeReturn(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria);
	
	
	public void getMyWaitPubNotice(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria,String userID);
			
	public void getAllNotice(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria);
			
	public void getMyCaogao(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria);
	
	public List getAllNotice();
	
	public MsgandnotNoticeMain getNoticeById(String id);
	
	public void deleteNoticeById(String noticeId);

	public void updateSingleNotice(MsgandnotNoticeMain notice,String passFlag,String userId);

	public void getLastMonthNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria);

	public void getAllPubNotice(Page<MsgandnotNoticeMain> page,
			Map<String, Object> parameter, Criteria criteria);

	public void getWyfzAuditNotice(Page page, Object object, Object object2,String userId);

	public void getWylzAuditNotice(Page page, Object object, Object object2,String userId);

	boolean isAuthorityAudit(String UserId, String posiId);

	public String getauditer(String type,String posiId);

	public MsgandnotNoticeMain getNoticeByNo(String ntcNo);



}
