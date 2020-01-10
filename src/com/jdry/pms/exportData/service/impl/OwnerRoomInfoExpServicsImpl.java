package com.jdry.pms.exportData.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.exportData.dao.OwnerRoomInfoExpDao;
import com.jdry.pms.exportData.pojo.OwnerRoomInfoExp;
import com.jdry.pms.exportData.service.OwnerRoomInfoExpServics;
@Repository
@Component
public class OwnerRoomInfoExpServicsImpl implements OwnerRoomInfoExpServics {
	@Resource
	OwnerRoomInfoExpDao dao;
	
	@Override
	public List<OwnerRoomInfoExp> queryOwnerRoomInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryOwnerRoomInfo(parameter);
	}

}
