package com.jdry.pms.system.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IDept;
import com.bstek.bdf2.core.business.IPosition;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.security.ISecurityInterceptor;
import com.jdry.pms.system.dao.SystemLogDao;
import com.jdry.pms.system.pojo.SystemLog;
import com.jdry.pms.system.pojo.VBdf2User;
import com.jdry.pms.system.service.UserMaintainService;

@Component
public class SystemLogListener implements ISecurityInterceptor{
	
	@Resource
	SystemLogDao dao;
	
	@Resource
	UserMaintainService userService;

	public SystemLogListener(){
		
	}
	@Override
	public void authorizationFailure(HttpRequestResponseHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void authorizationSuccess(HttpRequestResponseHolder arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeAuthorization(HttpRequestResponseHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeLogin(HttpRequestResponseHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginFailure(HttpRequestResponseHolder arg0) {
		String userName = arg0.getRequest().getParameter("username_");
		VBdf2User user = userService.getVBdf2UserById(userName);
		if(user!=null){
			SystemLog log = new SystemLog();
			log.setUserCname(user.getCname());
			log.setUserName(user.getUsername());
			log.setUserPhone(user.getMobile());
			log.setUserDept(user.getDeptName());
			log.setUserPos(user.getPositionName());
			log.setUserOperation("登陆失败");
			log.setCreateTime(new Date());
			dao.createSystemLog(log);
		}
	}

	@Override
	public void loginSuccess(HttpRequestResponseHolder arg0) {
		IUser user = ContextHolder.getLoginUser();
		user.getCname();
		SystemLog log = new SystemLog();
		log.setUserCname(user.getCname());
		log.setUserName(user.getUsername());
		log.setUserPhone(user.getMobile());
		List<IDept> depts = user.getDepts();
		String deptName = "";
		for(int i=0;i<depts.size();i++){
			if(i==0){
				deptName = depts.get(i).getName();
			}else{
				deptName += ","+depts.get(i).getName();
			}
		}
		List<IPosition> positions = user.getPositions();
		String posName = "";
		for(int i=0;i<positions.size();i++){
			if(i==0){
				posName = positions.get(i).getName();
			}else{
				posName += ","+positions.get(i).getName();
			}
		}
		log.setUserDept(deptName);
		log.setUserPos(posName);
		log.setUserOperation("登陆成功");
		log.setCreateTime(new Date());
		
		dao.createSystemLog(log);
	}

}
