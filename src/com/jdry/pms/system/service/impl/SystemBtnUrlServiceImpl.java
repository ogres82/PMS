package com.jdry.pms.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.dao.SystemBtnUrlDao;
import com.jdry.pms.system.pojo.SystemBtnUrl;
import com.jdry.pms.system.pojo.VSystemBtnUrl;
import com.jdry.pms.system.service.SystemBtnUrlService;

@Component
@Repository
public class SystemBtnUrlServiceImpl implements SystemBtnUrlService{

	@Resource 
	SystemBtnUrlDao dao;

	@Override
	public void querySystemBtnUrlByPage(Page<VSystemBtnUrl> page,
			Map<String, Object> parameter) {
		dao.querySystemBtnUrlByPage(page,parameter);
	}

	@Override
	public void saveSystemBtnUrl(SystemBtnUrl btnUrl) {
		dao.saveSystemBtnUrl(btnUrl);
	}

	@Override
	public void deleteSystemBtnUrl(String id) {
		dao.deleteSystemBtnUrl(id);
	}

	@Override
	public List<Map<String, Object>> getPageList(Map<String, Object> param) {
		return dao.getPageList(param);
	}
	
	
}
