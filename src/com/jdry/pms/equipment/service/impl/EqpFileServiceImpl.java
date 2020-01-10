package com.jdry.pms.equipment.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.equipment.dao.EqpFileDao;
import com.jdry.pms.equipment.pojo.EqpFile;
import com.jdry.pms.equipment.pojo.EqpMtnRec;
import com.jdry.pms.equipment.service.EqpFileService;
@Repository
@Component
public class EqpFileServiceImpl implements EqpFileService{
	@Resource
	EqpFileDao dao;
	@Override
	public Collection<EqpFile> queryEqpFileAll(){
		List<EqpFile> list = (List<EqpFile>) dao.queryEqpFileAll();
		for(int i=0;i<list.size();i++){
			list.get(i).setEqpMtnMark("0");
		}
//		return dao.queryEqpFileAll();
		return list;
	}
	@Override
	public void addEqp(EqpFile eqpFile) {
		dao.addEqpFile(eqpFile);
		
	}
	@Override
	public Collection<EqpFile> queryEqpFileByParam(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryEqpFileByParam(parameter);
	}
	@Override
	public void delEqpById(String eqpIds) {
		// TODO Auto-generated method stub
		dao.delEqpById(eqpIds);
	}
	/**
	 * 直线法计算设备当前价值
	 */
	@Override
	public float getCurrentVal(float originVal,float valRate,int lifeTime,int usedMonths) {
		// TODO Auto-generated method stub
		float res = (float) (originVal*(100-valRate)/lifeTime*0.01/12);
		float currentVal = originVal-res*usedMonths;
		return currentVal;
	}
	/**
	 * 设置设备使用情况，使用月数、当前价值等
	 */
	@Override
	public void setEqpCondition() {
		// TODO Auto-generated method stub
		List<EqpFile> list = (List<EqpFile>) this.queryEqpFileAll();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				EqpFile ef = list.get(i);
				float originVal = ef.getEqpOriginValue();
				float valRate = ef.getEqpValueRate();
				int liftime = ef.getEqpLifetime();
				long dateNow = DateUtil.getDateNumber(new Date());
				long dateInstall = DateUtil.getDateNumber(ef.getEqpInstallDate());
				int usedMonths = (int) Math.rint((dateNow-dateInstall)/DateUtil.MONTH);
				float currentVal = getCurrentVal(originVal,valRate,liftime,usedMonths);
				ef.setEqpUsedMonths(usedMonths);
				ef.setEqpCurrentValue(currentVal);
				this.addEqp(ef);
			}
		}
		
	}
	@Override
	public Collection queryEqpFileByName(String eqpName) {
		// TODO Auto-generated method stub
		return dao.queryEqpFileByName(eqpName);
	}
	@Override
	public void setEqpMtnCon(EqpFile eqpFile,EqpMtnRec rec) {
		// TODO Auto-generated method stub
		if(rec.getEqpMtnType().equals("0")){
			SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
			Date date = rec.getEqpMtnEnddate();
			eqpFile.setEqpMtnTimes(eqpFile.getEqpMtnTimes()+1);//设备保养次数+1
			eqpFile.setEqpMtnLast(date);
			Calendar calendar = Calendar.getInstance();
			String cycle = eqpFile.getEqpMtnCycle();
			if(cycle != null && !cycle.equals("")){
				if(cycle.equals("0")){
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, 1);
				}else if(cycle.equals("1")){
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, 3);
				}else{
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, 12);
				}
				try {
					eqpFile.setEqpMtnNext(sdf.parse(sdf.format(calendar.getTime())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			eqpFile.setEqpMtnTimes(eqpFile.getEqpMtnTimes()+1);
		}
		
		this.addEqp(eqpFile);
	}
	@Override
	public void setEqpFixTimes(EqpFile eqpFile) {
		// TODO Auto-generated method stub
		eqpFile.setEqpFixedTimes(eqpFile.getEqpFixedTimes()+1);
		this.addEqp(eqpFile);
	}

}
