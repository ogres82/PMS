package com.jdry.pms.equipment.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jdry.pms.equipment.dao.EqpFileDao;
import com.jdry.pms.equipment.dao.EqpMtnRecDao;
import com.jdry.pms.equipment.pojo.EqpFile;
import com.jdry.pms.equipment.pojo.EqpMtnRec;
import com.jdry.pms.equipment.pojo.VEqpMtnRec;
import com.jdry.pms.equipment.service.EqpFileService;
import com.jdry.pms.equipment.service.EqpMtnRecService;
@Component
public class EqpMtnRecServiceImpl implements EqpMtnRecService{

	@Resource
	EqpMtnRecDao dao;
	@Resource
	EqpFileDao eqpDao;
	@Resource
	EqpFileService eqpService;
	
	@Override
	public Collection<VEqpMtnRec> queryAllRec() {
		// TODO Auto-generated method stub
		return dao.queryAllRec();
	}

	@Override
	public void addEqpMtnRec(EqpMtnRec rec) {
		// TODO Auto-generated method stub
		dao.addEqpMtnRec(rec);
		EqpFile eqp = eqpDao.getEqpFileById(rec.getEqpId());
		eqpService.setEqpMtnCon(eqp,rec);
	
	}

	@Override
	public Collection<VEqpMtnRec> queryEqpMtnRecByParam(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryEqpMtnRecByParam(parameter);
	}

	@Override
	public void editEqpMtnRec(EqpMtnRec rec) {
		// TODO Auto-generated method stub
		dao.addEqpMtnRec(rec);
	}

	@Override
	public void delEqpMtnRecById(String recIds) {
		// TODO Auto-generated method stub
		dao.delEqpMtnRecById(recIds);
	}

}
