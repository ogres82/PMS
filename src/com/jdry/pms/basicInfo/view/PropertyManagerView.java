package com.jdry.pms.basicInfo.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.basicInfo.service.PropertyManagerService;

@Component
public class PropertyManagerView {

	@Resource
	PropertyManagerService service;
	
	@Resource
	ValidatorsDao vDao;
	
	@DataProvider
	public void queryPropertyManager(Page<Emp> page,Map<String, Object> parameter,Criteria criteria){
		service.query(page,parameter,criteria);
	}
	
	@Expose
	public Emp getTemp(Map<String, Object> parameter){
		return service.getTemp(parameter);
	}
	
	@Expose
	public String delTemp(String obj){

		
		Collection<Emp> emps = new ArrayList<Emp>();
		if(obj.contains(",")){
			String[] empIds = obj.split(",");
			for(int i=0;i<empIds.length;i++){
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("empId", empIds[i]);
				Emp emp = service.getTemp(map);
				if(emp != null){
					try {
						emp = EntityUtils.toEntity(emp);
					} catch (Exception e) {
						e.printStackTrace();
					}
					EntityUtils.setState(emp, EntityState.DELETED);
					emps.add(emp);
				}
			}
			
		}else{
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("empId", obj);
			Emp emp = service.getTemp(map);
			if(emp != null){
				try {
					emp = EntityUtils.toEntity(emp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				EntityUtils.setState(emp, EntityState.DELETED);
				emps.add(emp);
			}
			
		}
		
		savePropertyManager(emps);
	
		return "success";
	
	}
	
	@DataResolver
	public void savePropertyManager(Collection<Emp> emps){
		
	    service.savePropertyManager(emps);
	}
	
	@Expose
	public String add_Emp(Emp emp) throws Exception{
		Collection<Emp> emps = new ArrayList<Emp>();
		if(emp != null){
			emp = EntityUtils.toEntity(emp);
			EntityUtils.setState(emp, EntityState.NEW);
			emps.add(emp);
			service.savePropertyManager(emps);
			return "success";
		}
		return "failed";
	}
	
	@Expose
	public String update_Emp(Emp emp) throws Exception{
		Collection<Emp> emps = new ArrayList<Emp>();
		if(emp != null){
			emp = EntityUtils.toEntity(emp);
			EntityUtils.setState(emp, EntityState.MODIFIED);
			emps.add(emp);
			service.savePropertyManager(emps);
			return "success";
		}
		return "failed";
	}
	
	@DataProvider
	public Collection<DefaultDept> queryDept(Map<String, Object> parameter){
		return service.queryDept(parameter);
	}
	
	@DataProvider
	public Collection<DefaultPosition> queryPosition(Map<String, Object> parameter){
		return service.queryPosition(parameter);
	}
	
	/**
	 * 获取部门对应的ID
	 * @param code
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getDeptId() {
		Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		List<DefaultDept> depts = (List<DefaultDept>) service.queryDept(parameter);
	    for(DefaultDept dept:depts){
	    	mapValue.put(dept.getId(), dept.getName());
	    }
	    return mapValue;
	}
	
	@DataProvider
	public Map<Object, String> getPostId() {
		Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		List<DefaultPosition> positions = (List<DefaultPosition>) service.queryPosition(parameter);
	    for(DefaultPosition pos:positions){
	    	mapValue.put(pos.getId(), pos.getName());
	    }
	    return mapValue;
	}
	
	@Expose
	public String checkEmpName(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from Emp where empName='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "姓名\""+parameter+"\"" + isExist;
		}
	}
	
}
