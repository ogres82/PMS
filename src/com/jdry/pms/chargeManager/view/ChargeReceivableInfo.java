package com.jdry.pms.chargeManager.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.dir.util.DirctoryComm;


@Component
public class ChargeReceivableInfo {
	
	@Resource
	private ChargeInfoService service;
	
	@Resource
	DirctoryComm dirctoryComm;
	
	//应收项目管理
	@DataProvider
	public void queryAll(Page<ChargeInfoEntity> page, Map<String, Object> parameter, Criteria criteria)
	{
		service.queryAll(page, parameter, "01");
	}
	
	@DataResolver
    public void saveAll(Collection<ChargeInfoEntity> chargeTypes){
		service.saveAll(chargeTypes);
    }
	
	@DataResolver
	public void delete(ChargeInfoEntity chargeType){
		service.delete(chargeType);
	}
	
	@DataResolver
	public void paidInfoSave(Map paidInfo,  List<ChargeInfoEntity> chargeInfo){
		service.paidInfoSave(paidInfo, chargeInfo);
	}
	
	/**
	 * 开票方式
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getRoomType() {
	    return dirctoryComm.getDirByCode("room_type");
	}
	
			
}
