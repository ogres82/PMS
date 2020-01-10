package com.jdry.pms.TimeTask;

import java.util.TimerTask;

import javax.annotation.Resource;

import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.equipment.service.EqpFileService;

public class EqpDataTimerTask extends TimerTask{
	@Resource
	EqpFileService service;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(service == null){
			service = (EqpFileService) SpringUtil.getObjectFromApplication("eqpFileServiceImpl");
		}
		service.setEqpCondition();
		System.out.println("任务执行。。。");
	}

}
