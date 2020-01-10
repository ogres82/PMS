package com.jdry.pms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jdry.pms.basicInfo.dao.BuildingPropertyDao;
import com.jdry.pms.basicInfo.pojo.VBuildingProperty;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.lzmh.service.LzmhService;


public class SycnBuildingProp {
	 @Autowired 
	static LzmhService lzmhService;

	 @Autowired 
	static BuildingPropertyDao bpDao;
    @Test
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (bpDao == null) {
			bpDao = (BuildingPropertyDao) SpringUtil.getObjectFromApplication("buildingPropertyDao");
		}
		if (lzmhService == null) {
			lzmhService = (LzmhService) SpringUtil.getObjectFromApplication("lzmhServiceImpl");
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		String hql = "from VBuildingProperty du where 1=1";
		List<VBuildingProperty> bpList = bpDao.query(hql, parameter);
		System.out.print(bpList.size());
	}
}
