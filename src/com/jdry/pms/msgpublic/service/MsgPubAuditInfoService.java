package com.jdry.pms.msgpublic.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgpublic.pojo.MsgPubAuditinfo;

@Repository
public interface MsgPubAuditInfoService {
	public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page, 
			Map<String, Object> parameter,Criteria criteria)
            throws Exception;
	
	public void saveInfo(Collection<MsgandnoticeNoticeAuditinfo> mes);
	
	
	public void saveSingleInfo(MsgPubAuditinfo mes);


}
