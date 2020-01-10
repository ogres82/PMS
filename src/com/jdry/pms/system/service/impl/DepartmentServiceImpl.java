package com.jdry.pms.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.jdry.pms.system.dao.DepartmentDao;
import com.jdry.pms.system.service.DepartmentService;
@Repository
@Component
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	DepartmentDao dao;

	@Override
	public void saveDept(DefaultDept dept) {
		// TODO Auto-generated method stub
		dao.saveDept(dept);
	}

	@Override
	public void deleteDept(String id) {
		// TODO Auto-generated method stub
		dao.deleteDept(id);
	}
	

}
