package com.jdry.pms.system.service;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
@Repository
public interface DepartmentService {

	public void saveDept(DefaultDept dept);
	public void deleteDept(String id);
}
