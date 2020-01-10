package com.jdry.pms.msgandnotice.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;

@Repository
public interface MsgTempService {
	
	public void saveMsgTemp(Collection<MessageTempMain> mes);
	
	
	public void getMsgTemp(Page<MessageTempMain> page, Map<String, Object> parameter,Criteria criteria);
	
	public Collection<MessageTempMain> getAllMsgTemp();
	
	public String findTempById(String tempId);
	
	public MessageTempMain getTempById(String tempId);
	
	public void updateTemp(Collection<MessageTempMain> mes);
	
	public void deleteTempById(String tempId);
		
}
