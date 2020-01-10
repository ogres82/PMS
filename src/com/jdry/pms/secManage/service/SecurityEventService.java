package com.jdry.pms.secManage.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.secManage.pojo.SecurityEvent;
@Repository
@Component
public interface SecurityEventService {

	public void query(Page<Emp> page,Map<String, Object> parameter, Criteria criteria);

	public String getLineName(String transferId);

	public Collection<Emp> getPropertyManager();

	public void savePropertyManager(Collection<Emp> emps);

	public Collection<DefaultDept> queryDept(Map<String, Object> parameter);

	public Collection<DefaultPosition> queryPosition(Map<String, Object> parameter);

	public Emp getTemp(Map<String, Object> parameter);

	public void queryEventByParam(Page<SecurityEvent> page,
			Map<String, Object> parameter, String type);

	public List<Emp> queryAll(Map<String, Object> parameter, String type);

	public void saveAll(Collection<Emp> emps);

	public List<DirDirectoryDetail> getDirectoryLikeCode(String code);

	public void addSecurityEvent(SecurityEvent securityEvent);

	public void deleteEmp(String empId);

	public SecurityEvent getEventById(String empId);

	public void queryEventByParams(Page<SecurityEvent> page,
			Map<String, Object> parameter, Object object);

	public List getAllEvent();



}
