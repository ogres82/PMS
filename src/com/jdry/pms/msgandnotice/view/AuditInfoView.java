package com.jdry.pms.msgandnotice.view;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.AuditInfoService;

@Component
public class AuditInfoView {
	@Resource
	AuditInfoService auditInfoService;
	@DataProvider
    public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page, Map<String, Object> parameter,Criteria criteria){
         try {
        	 auditInfoService.getAuditInfo(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
