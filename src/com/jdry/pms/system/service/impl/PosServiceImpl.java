package com.jdry.pms.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultPosition;
import com.jdry.pms.system.dao.PosDao;
import com.jdry.pms.system.service.PosService;
@Repository
@Component
public class PosServiceImpl implements PosService {

	@Resource
	PosDao dao;

	@Override
	public void savePosition(DefaultPosition pos) {
		// TODO Auto-generated method stub
		dao.savePosition(pos);
	}

	@Override
	public void deletePosition(String id) {
		// TODO Auto-generated method stub
		dao.deletePosition(id);
	}

	@Override
	public List<DefaultPosition> loadPosition() {
		// TODO Auto-generated method stub
		return dao.loadPosition();
	}
	

}
