package com.jdry.pms.secManage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.secManage.dao.SecurityChargerDutyDao;
import com.jdry.pms.secManage.dao.SecurityFirecontrolDutyDao;
import com.jdry.pms.secManage.pojo.SecurityFirecontrolDuty;
import com.jdry.pms.secManage.service.SecurityFirecontrolDutyService;


@Repository
@Component
public class SecurityFirecontrolDutyServiceImpl implements SecurityFirecontrolDutyService{
	@Resource
	SecurityFirecontrolDutyDao dao;
	@Resource
	SecurityChargerDutyDao chargeDao;
	@Override
	public void queryFirecontrolDutyByParam(Page<SecurityFirecontrolDuty> page,
			Map<String, Object> parameter, String type) {
		dao.queryFirecontrolDutyByParam(page,parameter,type);
	}
	@Override
	public void deleteEmp(String empId) {
		dao.deleteEmp(empId);
	}

	@Override
	public SecurityFirecontrolDuty getFirecontrolDutyById(String empId) {
		
		return dao.getFirecontrolDutyById(empId);
	}

	@Override
	public List<DefaultUser> getAllUser() {
		// TODO Auto-generated method stub
		return chargeDao.getAllUser();
	}

	@Override
	public void addSecurityFirecontrolDuty(SecurityFirecontrolDuty scd) {
		dao.addSecurityFirecontrolDuty(scd);
		
	}

	@Override
	public String getUserNameById(String userId) {
		IUserService userService=(IUserService) SpringUtil.getObjectFromApplication("bdf2.userService");
		DefaultUser user=(DefaultUser) userService.loadUserByUsername(userId);
		return user.getCname();
	}
	
}

