package com.jdry.pms.leaseManage.service;

import java.util.List;
import java.util.Map;
import com.jdry.pms.leaseManage.pojo.LeaseInfo;

public interface LeaseService  {
	
	@SuppressWarnings("rawtypes")
	public List<Map> getLeaseInfoAll(Map<String, Object> parameter);
	
	public void saveLeaseInfo(LeaseInfo li,String operate);
	
	public void delLeaseInfo(String Id);
	
	public List<Map> queryShopInfo(String keyword);
	

}
