package com.jdry.pms.msgandnotice.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.service.MsgTempService;

@Component
public class MessageTemplateView {
	@Resource
    public MsgTempService msgTempService;
	@DataProvider
	public void getMsgTemp(Page<MessageTempMain> page, Map<String, Object> parameter,Criteria criteria){
		msgTempService.getMsgTemp(page, parameter, criteria);
	}
	@DataResolver
	public void saveMsgTemp(Collection<MessageTempMain> mes){
		msgTempService.saveMsgTemp(mes);
	}
}
