package com.jdry.pms.basicInfo.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.PropertyManagerDao;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

@Repository
@Component
public class PropertyManagerServiceImpl implements PropertyManagerService {

	@Resource
	PropertyManagerDao dao;

	@Override
	public void query(Page<Emp> page, Map<String, Object> parameter, Criteria criteria) {
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
	public Collection<DefaultPosition> queryPosition(Map<String, Object> parameter) {

		return dao.queryPosition(parameter);
	}

	@Override
	public Emp getTemp(Map<String, Object> parameter) {

		return dao.getTemp(parameter);
	}

	@Override
	public void queryPropertyByParam(Page<VEmp> page, Map<String, Object> parameter, String type) {
		dao.queryPropertyByParam(page, parameter, type);
	}

	// @Override
	// public List<VEmp> queryAll(Map<String, Object> parameter, String type) {
	// return dao.queryAll(parameter,type);
	// }

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
	public void addEmp(Emp emp) {

		dao.addEmp(emp);
	}

	@Override
	public void deleteEmp(String empId) {
		dao.deleteEmp(empId);
	}

	@Override
	public Emp getEmpById(String empId) {

		return dao.getEmpById(empId);
	}

	@Override
	public List<Object> queryEmpDept(Map<String, Object> parameter) {

		return dao.queryEmpDept(parameter);
	}

	@Override
	public List<Emp> getEmpByDeptId(String deptId) {
		return dao.getEmpByDeptId(deptId);
	}

	@Override
	public List<Emp> getEmpAllNum() {
		return dao.getEmpAllNum();
	}

	@Override
	public List<VEmp> queryEmpByKeyword(Map<String, Object> param) {
		return dao.queryEmpByKeyword(param);
	}

	@Override
	public List<VEmp> queryEmpAll(Map<String, Object> parameter) {
		return dao.queryEmpAll(parameter);
	}

	@Override
	public String getEmpContacts(String data) {
		if (dao == null) {
			dao = (PropertyManagerDao) SpringUtil.getObjectFromApplication("propertyManagerDao");
		}
		return dao.getEmpContacts();
	}

	@Override
	public List getSearchEmp(String keyword) {
		// TODO Auto-generated method stub
		return dao.getSearchEmp(keyword);
	}

	@Override
	public List<Map> getEmpInfoForSMS() {
		// TODO Auto-generated method stub
		return dao.getEmpInfoForSMS();
	}

	@Override
	public String getEmpForApp(String data) {
		// TODO Auto-generated method stub
		if (dao == null) {
			dao = (PropertyManagerDao) SpringUtil.getObjectFromApplication("propertyManagerDao");
		}
		List<Map> list = dao.getEmpForApp(data);

		if (list != null && list.size() > 0) {
			String jsonString = JSON.toJSONString(list);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";
		} else {
			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}

}
