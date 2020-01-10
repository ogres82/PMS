package com.jdry.pms.msgandnotice.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.msgandnotice.pojo.MessageEntity;
import com.jdry.pms.msgandnotice.service.MessageService;
/**
*
* @author 钟涛
*
*/
@Component
public class MessageView {
	@Resource
	MessageService messageService;
	@DataProvider
	public void getMessage(Page<MessageEntity> page, Map<String, Object> parameter,Criteria criteria){
		try {
			
			messageService.getMessage(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@DataResolver
	public void saveMessage(Collection<MessageEntity> mes){
		messageService.saveMessage(mes);
	}
	@Expose
    public String getBusinessId() {
		CommUtil cu=new CommUtil();
		String businessId="wokao";
        return businessId;
    }
}
