package com.jdry.pms.msgandnotice.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.service.NoticeService;

/**
*
* @author 钟涛
*
*/
@Component
public class NoticeAuditView {
	@Resource
    public NoticeService noticeService;
	@DataProvider
    public void getMyAuditNotice(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getMyAuditNotice(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@DataResolver
	public void saveAudit(Collection<MsgandnotNoticeMain> mes){
    	noticeService.saveAudit(mes);
	}
}
