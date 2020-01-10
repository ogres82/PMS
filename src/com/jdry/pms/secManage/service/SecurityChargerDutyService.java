package com.jdry.pms.secManage.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.secManage.pojo.SecurityChargerDuty;
@Repository
@Component
public interface SecurityChargerDutyService {

	public void query(Page<Emp> page,Map<String, Object> parameter, Criteria criteria);

	public String getLineName(String transferId);

	public Collection<Emp> getPropertyManager();

	public void savePropertyManager(Collection<Emp> emps);

	public Collection<DefaultDept> queryDept(Map<String, Object> parameter);

	public Collection<DefaultPosition> queryPosition(Map<String, Object> parameter);

	public Emp getTemp(Map<String, Object> parameter);

	public void queryChargerDutyByParam(Page<SecurityChargerDuty> page,
			Map<String, Object> parameter, String type);

	public List<Emp> queryAll(Map<String, Object> parameter, String type);

	public void saveAll(Collection<Emp> emps);

	public List<DirDirectoryDetail> getDirectoryLikeCode(String code);

	public void addSecurityChargerDuty(SecurityChargerDuty scd);

	public void deleteEmp(String empId);

	public SecurityChargerDuty getChargerDutyById(String empId);

	public List<DefaultUser> getAllUser();

	public String getUserNameById(String userId);


}
