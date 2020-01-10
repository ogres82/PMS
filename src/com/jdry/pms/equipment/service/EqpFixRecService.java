package com.jdry.pms.equipment.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.equipment.pojo.EqpFixRec;
import com.jdry.pms.equipment.pojo.VEqpFixRec;

@Repository
public interface EqpFixRecService {

	public Collection<VEqpFixRec> queryAllRec();
	public void addEqpFixRec(EqpFixRec rec);
	public Collection<VEqpFixRec> queryEqpFixRecByParam(Map<String, Object> parameter);
	public void editEqpFixRec(EqpFixRec rec);
	public void delEqpFixRecById(String recIds);
}
