package com.jdry.pms.equipment.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.equipment.pojo.EqpMtnRec;
import com.jdry.pms.equipment.pojo.VEqpMtnRec;

@Repository
public interface EqpMtnRecService {

	public Collection<VEqpMtnRec> queryAllRec();
	public void addEqpMtnRec(EqpMtnRec rec);
	public Collection<VEqpMtnRec> queryEqpMtnRecByParam(Map<String, Object> parameter);
	public void editEqpMtnRec(EqpMtnRec rec);
	public void delEqpMtnRecById(String recIds);
}
