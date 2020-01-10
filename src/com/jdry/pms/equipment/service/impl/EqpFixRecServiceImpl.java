package com.jdry.pms.equipment.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jdry.pms.equipment.dao.EqpFileDao;
import com.jdry.pms.equipment.dao.EqpFixRecDao;
import com.jdry.pms.equipment.pojo.EqpFile;
import com.jdry.pms.equipment.pojo.EqpFixRec;
import com.jdry.pms.equipment.pojo.VEqpFixRec;
import com.jdry.pms.equipment.service.EqpFileService;
import com.jdry.pms.equipment.service.EqpFixRecService;
@Component
public class EqpFixRecServiceImpl implements EqpFixRecService{

	@Resource
	EqpFixRecDao dao;
	@Resource
	EqpFileDao eqpDao;
	@Resource
	EqpFileService eqpService;
	
	@Override
	public Collection<VEqpFixRec> queryAllRec() {
		// TODO Auto-generated method stub
		return dao.queryAllRec();
	}
	@Override
	public void addEqpFixRec(EqpFixRec rec) {
		// TODO Auto-generated method stub
		dao.addEqpFixRec(rec);
		EqpFile eqp = eqpDao.getEqpFileById(rec.getEqpId());
		eqpService.setEqpFixTimes(eqp);
	}
	@Override
	public Collection<VEqpFixRec> queryEqpFixRecByParam(
			Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryEqpFixRecByParam(parameter);
	}
	@Override
	public void editEqpFixRec(EqpFixRec rec) {
		// TODO Auto-generated method stub
		dao.addEqpFixRec(rec);
	}
	@Override
	public void delEqpFixRecById(String recIds) {
		// TODO Auto-generated method stub
		dao.delEqpFixRecById(recIds);
	}
	
	
}
