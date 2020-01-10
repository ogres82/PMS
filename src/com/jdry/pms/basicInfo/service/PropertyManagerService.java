package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

@Repository
public interface PropertyManagerService {
	public void query(Page<Emp> page,Map<String, Object> parameter, Criteria criteria);

	public String getLineName(String transferId);

	public Collection<Emp> getPropertyManager();

	public void savePropertyManager(Collection<Emp> emps);

	public Collection<DefaultDept> queryDept(Map<String, Object> parameter);

	public Collection<DefaultPosition> queryPosition(Map<String, Object> parameter);

	public Emp getTemp(Map<String, Object> parameter);

	public void queryPropertyByParam(Page<VEmp> page,
			Map<String, Object> parameter, String type);

	//public List<VEmp> queryAll(Map<String, Object> parameter, String type);
	
	public List<VEmp> queryEmpAll(Map<String, Object> parameter);

	public void saveAll(Collection<Emp> emps);

	public List<DirDirectoryDetail> getDirectoryLikeCode(String code);

	public void addEmp(Emp emp);

	public void deleteEmp(String empId);

	public Emp getEmpById(String empId);

	public List<Object> queryEmpDept(Map<String, Object> parameter);

	public List<Emp> getEmpByDeptId(String deptId);

	public List<Emp> getEmpAllNum();

	public List<VEmp> queryEmpByKeyword(Map<String, Object> param);
	
	public String getEmpContacts(String data);//获取通信录

	public List getSearchEmp(String keyword);

	public List<Map> getEmpInfoForSMS();
	
	public String getEmpForApp(String data);

}
