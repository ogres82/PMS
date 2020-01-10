package com.jdry.pms.equipment.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.equipment.pojo.EqpFile;
import com.jdry.pms.equipment.pojo.EqpMtnRec;
@Repository
public interface EqpFileService {
	public Collection<EqpFile> queryEqpFileAll();
	public void addEqp(EqpFile eqpFile);
	public Collection<EqpFile> queryEqpFileByParam(Map<String, Object> parameter);
	public void delEqpById(String eqpIds);
	public float getCurrentVal(float originVal,float valRate,int lifeTime,int usedMonths);
	public void setEqpCondition();
	public Collection queryEqpFileByName(String eqpName);
	public void setEqpMtnCon(EqpFile eqpFile,EqpMtnRec rec);
	public void setEqpFixTimes(EqpFile eqpFile);
}
