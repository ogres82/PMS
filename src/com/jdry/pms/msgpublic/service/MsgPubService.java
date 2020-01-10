package com.jdry.pms.msgpublic.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgpublic.pojo.MsgPubMain;
/**
*
* @author 钟涛
*
*/
@Repository
public interface MsgPubService {
	public void getNotice(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria)throws Exception;	
	
	public void updateNotice(Collection<MsgPubMain> updateList);
	
	
	public void deleteNotice(Collection<MsgandnotNoticeMain> mes);
	
	
	public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page, 
			Map<String, Object> parameter,Criteria criteria) throws Exception;
	
	public void getMyAuditNotice(Page<MsgandnotNoticeMain> page, 
			Map<String, Object> parameter,Criteria criteria);
	
	public void saveAudit(Collection<MsgandnotNoticeMain> mes);
	
			
	public List getAllNotice();
	
	public MsgPubMain getNoticeById(String id);
	
	public void deleteNoticeById(String noticeId);

	public void updateSingleNotice(MsgPubMain notice,String passFlag,String userId);

	public void getWyfzAuditNotice(Page page, Object object, Object object2,String userId);

	public void getWylzAuditNotice(Page page, Object object, Object object2,String userId);

	boolean isAuthorityAudit(String UserId, String posiId);

	public String getauditer(String type,String posiId);

	public void getAllNotice(Page<MsgPubMain> page, Object parameter,
			Object criteria);

	void getAllPubNotice(Page<MsgPubMain> page, Map<String, Object> parameter,
			Criteria criteria);

	public String saveNotice(List<MsgPubMain> saveList, String ntcPicPath);



}
