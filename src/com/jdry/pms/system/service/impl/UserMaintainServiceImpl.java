package com.jdry.pms.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.system.dao.UserMaintainDao;
import com.jdry.pms.system.pojo.DeptAndPos;
import com.jdry.pms.system.pojo.VBdf2User;
import com.jdry.pms.system.service.UserMaintainService;

@Repository
@Component
public class UserMaintainServiceImpl implements UserMaintainService {

	@Resource
	UserMaintainDao dao;

	@Override
	public List<VBdf2User> queryUserByParam(int rows) {
		return dao.queryUserByParam(rows);
	}

	@Override
	public DefaultUser getUserById(String username) {
		return dao.getUserById(username);
	}

	@Override
	public void deleteUser(String username) {
		dao.deleteUser(username);
	}

	@Override
	public void saveUser(DefaultUser duser) {
		dao.saveUser(duser);
	}

	@Override
	public VBdf2User getVBdf2UserById(String username) {
		return dao.getVBdf2UserById(username);
	}

	@Override
	public Map<String, Object> getUserDetailById(String userName) {
		return dao.getUserDetailById(userName);
	}

	@Override
	public List getDeptAndPos() {
		// TODO Auto-generated method stub
		List<DeptAndPos> lists = dao.getDeptAndPos();

		// 定义一个以deptId为key的dept对象
		Map<String, Map<String, Object>> deptsMap = new HashMap<String, Map<String, Object>>();
		// 第一条数据默认插入

		for (int i = 0, len = lists.size(); i < len; i++) {
			String key = lists.get(i).getDeptId();
			boolean contains = deptsMap.containsKey(key);
			Map<String, Object> deptItem;
			if (contains) {
				deptItem = deptsMap.get(key);
			} else {
				deptItem = new HashMap<String, Object>();
				deptItem.put("n", lists.get(i).getDeptName());
				deptItem.put("v", lists.get(i).getDeptId());
				deptItem.put("s", new ArrayList());
				deptsMap.put(key, deptItem);
			}
			Map<String, Object> posItem = new HashMap<String, Object>();
			posItem.put("n", lists.get(i).getPosName());
			posItem.put("v", lists.get(i).getPosId());
			((List) deptItem.get("s")).add(posItem);
		}
		Collection<Map<String, Object>> valueCollection = deptsMap.values();
		List<Map<String, Object>> deptList = new ArrayList<Map<String, Object>>(valueCollection);
		return deptList;
	}

}
