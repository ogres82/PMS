package com.jdry.pms.msgandnotice.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;

@Repository
public interface AuditInfoService {
	public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page, 
			Map<String, Object> parameter,Criteria criteria)
            throws Exception;
	
	public void saveInfo(Collection<MsgandnoticeNoticeAuditinfo> mes);
	
	
	
	public void saveSingleInfo(MsgandnoticeNoticeAuditinfo mes);
	
	
	public int getCountAuditNotice(String userId);

}
