package com.jdry.pms.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.pojo.SystemBtnUrl;
import com.jdry.pms.system.pojo.VSystemBtnUrl;

@Repository
public interface SystemBtnUrlService {

	public void querySystemBtnUrlByPage(Page<VSystemBtnUrl> page,Map<String, Object> parameter);

	public void saveSystemBtnUrl(SystemBtnUrl btnUrl);

	public void deleteSystemBtnUrl(String id);

	public List<Map<String, Object>> getPageList(Map<String, Object> param);

}
