package com.jdry.pms.msgandnotice.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.AuditInfoService;
@Component
public class InfoView {
	@Resource
	AuditInfoService auditInfoService;
	@DataProvider
	public void getInfo(Page<MsgandnoticeNoticeAuditinfo> page,Map<String, Object> parameter,Criteria criteria){
		try {
			auditInfoService.getAuditInfo(page, parameter, criteria);
			List<MsgandnoticeNoticeAuditinfo> results = new ArrayList<MsgandnoticeNoticeAuditinfo>();
		    Collection<MsgandnoticeNoticeAuditinfo> products = page.getEntities();
		    for (MsgandnoticeNoticeAuditinfo product:products){
		    	MsgandnoticeNoticeAuditinfo targetProduct = EntityUtils.toEntity(product);
		        EntityUtils.setValue(targetProduct, "ntcNoticeSub", product.getNtcNotice().getNtcSubject());
		        results.add(targetProduct);
		   }
		    page.setEntities(results);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@DataResolver
	public void saveInfo(Collection<MsgandnoticeNoticeAuditinfo> mes){
		auditInfoService.saveInfo(mes);
	}
}
