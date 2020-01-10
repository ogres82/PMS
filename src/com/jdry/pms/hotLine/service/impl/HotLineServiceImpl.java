package com.jdry.pms.hotLine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.hotLine.dao.HotLineDao;
import com.jdry.pms.hotLine.pojo.HotLine;
import com.jdry.pms.hotLine.service.HotLineService;

@Repository
@Component
public class HotLineServiceImpl implements HotLineService {
	@Resource
	HotLineDao dao;
	
	@Override
	public String getHotLineOfApp(String data) {
		if(dao == null){
			dao = (HotLineDao) SpringUtil.getObjectFromApplication("hotLineDao");
		}
		// TODO Auto-generated method stub
		return dao.getHotLineOfApp(data);
	}

	@Override
	public List<HotLine> getHotLineList() {
		// TODO Auto-generated method stub
		return dao.getHotLineList();
	}

	@Override
	public void delHotLineInfo(String Id) {
		// TODO Auto-generated method stub
		dao.delHotLineInfo(Id);
	}

	@Override
	public boolean saveHotLineInfo(HotLine hotline) {
		// TODO Auto-generated method stub
		return dao.saveHotLineInfo(hotline);
	}

}
