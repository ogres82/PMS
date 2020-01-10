package com.jdry.pms.secManage.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.secManage.dao.SecurityChargerDutyDao;
import com.jdry.pms.secManage.pojo.SecurityChargerDuty;
import com.jdry.pms.secManage.service.SecurityChargerDutyService;

@Repository
@Component
public class SecurityChargerDutyServiceImpl implements SecurityChargerDutyService{
	@Resource
	SecurityChargerDutyDao dao;

	@Override
	public void query(Page<Emp> page,
			Map<String, Object> parameter, Criteria criteria) {
		dao.query(page, parameter, criteria);
	}

	@Override
	public String getLineName(String transferId) {
		
		return null;
	}

	@Override
	public Collection<Emp> getPropertyManager() {
		
		return dao.getPropertyManager();
	}

	@Override
	public void savePropertyManager(Collection<Emp> emps) {
		
		dao.savePropertyManager(emps);
	}

	@Override
	public Collection<DefaultDept> queryDept(Map<String, Object> parameter) {
		
		return dao.queryDept(parameter);
	}
	
	@Override
	public Collection<DefaultPosition> queryPosition(
			Map<String, Object> parameter) {
		
		return dao.queryPosition(parameter);
	}

	@Override
	public Emp getTemp(Map<String, Object> parameter) {
		
		return dao.getTemp(parameter);
	}

	@Override
	public void queryChargerDutyByParam(Page<SecurityChargerDuty> page,
			Map<String, Object> parameter, String type) {
		dao.queryChargerDutyByParam(page,parameter,type);
	}

	@Override
	public List<Emp> queryAll(Map<String, Object> parameter, String type) {
		return dao.queryAll(parameter,type);
	}

	@Override
	public void saveAll(Collection<Emp> emps) {
		// TODO Auto-generated method stub
		dao.saveAll(emps);
	}

	@Override
	public List<DirDirectoryDetail> getDirectoryLikeCode(String code) {
		// TODO Auto-generated method stub
		return dao.getDirectoryLikeCode(code);
	}
	@Override
	public void deleteEmp(String empId) {
		dao.deleteEmp(empId);
	}

	@Override
	public SecurityChargerDuty getChargerDutyById(String empId) {
		
		return dao.getChargerDutyById(empId);
	}

	@Override
	public List<DefaultUser> getAllUser() {
		// TODO Auto-generated method stub
		return dao.getAllUser();
	}

	@Override
	public void addSecurityChargerDuty(SecurityChargerDuty scd) {
		dao.addSecurityChargerDuty(scd);
		
	}

	@Override
	public String getUserNameById(String userId) {
		IUserService userService=(IUserService) SpringUtil.getObjectFromApplication("bdf2.userService");
		DefaultUser user=(DefaultUser) userService.loadUserByUsername(userId);
		return user.getCname();
	}
	
}
