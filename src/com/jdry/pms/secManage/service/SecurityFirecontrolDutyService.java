package com.jdry.pms.secManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.secManage.pojo.SecurityFirecontrolDuty;

@Repository
@Component
public interface SecurityFirecontrolDutyService {

	public void queryFirecontrolDutyByParam(Page<SecurityFirecontrolDuty> page,
			Map<String, Object> parameter, String type);

	public void addSecurityFirecontrolDuty(SecurityFirecontrolDuty scd);

	public void deleteEmp(String empId);

	public SecurityFirecontrolDuty getFirecontrolDutyById(String empId);

	public List<DefaultUser> getAllUser();

	public String getUserNameById(String userId);

}
