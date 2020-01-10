package com.jdry.pms.msgandnotice.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MessageEntity;
/**
*
* @author 钟涛
*
*/
@Repository
public interface MessageService {
	public void getMessage(Page<MessageEntity> page, Map<String, Object> parameter,Criteria criteria)
            throws Exception;
	public void saveMessage(Collection<MessageEntity> mes);
	
	public void saveAll(List<MessageEntity> customer);
	
}
