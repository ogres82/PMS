package com.jdry.pms.basicInfo.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.service.AllAreaService;

@Component
public class AllAreaView {

	@Resource
	AllAreaService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@DataProvider
	public void queryAllArea(Page<AllArea> page,Map<String, Object> parameter,Criteria criteria){
		service.query(page,parameter,criteria);
	}
	
	@DataProvider
	public Collection<AllArea> queryAllAreaByParam(Map<String, Object> parameter){
		return service.queryAllAreaByParam(parameter);
	}
	
	
	@DataResolver
	public void saveAllArea(Collection<AllArea> areas){
	    service.saveAllArea(areas);
	}
	
	@Expose
	public String checkAllArea(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from AllArea where buildName='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "楼盘名称\""+parameter+"\"" + isExist;
		}
	}
}
