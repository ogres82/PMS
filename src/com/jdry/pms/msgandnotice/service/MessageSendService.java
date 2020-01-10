package com.jdry.pms.msgandnotice.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.msgandnotice.pojo.MessageSendMain;

@Repository
public interface MessageSendService {
	public void getSendMessage(Page<MessageSendMain> page, Map<String, Object> parameter,Criteria criteria)
            throws Exception;
	public MessageSendMain getSendMessageById(String id)
            throws Exception;
	public void saveSendMessage(Collection<MessageSendMain> mes);
	
	public void saveAll(Collection<MessageSendMain> customer) throws IOException;
	
    public void saveSingleSendMessage(MessageSendMain mes);
    
    public Collection<DefaultUser> getUser();

	public void queryOwnerInfo(Page page,Map<String, Object> parameter,Criteria criteria);
	
	public List queryAllOwnerInfo();
	
	public String saveChargeMessage(PropertyOwner propertyOwner,Date dateline, float arrears);
	
	public String sendArrearMessage(List<Map<String, Object>> arrayList,String chargeMng);
	
	public PropertyOwner queryOwnerById(String ownerId);
	
	public PropertyOwner queryOwnerByPhone(String phone);
	
	public List<MessageSendMain> queryMessagesByPhone(String phone);
	
	public void deleteMsgByIds(String id);
}
