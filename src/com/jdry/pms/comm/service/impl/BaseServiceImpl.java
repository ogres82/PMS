package com.jdry.pms.comm.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jdry.pms.comm.dao.BaseDao;
import com.jdry.pms.comm.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	/** 
     * 注入BaseDao 
     */   
    @Resource  
    private BaseDao<T> dao; 
	
	@Override
	public void saveEntity(T t) {
		// TODO Auto-generated method stub
		dao.saveEntity(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		// TODO Auto-generated method stub
		dao.saveOrUpdateEntity(t);
	}

	@Override
	public void updateEntity(T t) {
		// TODO Auto-generated method stub
		dao.updateEntity(t);
	}

	@Override
	public void deleteEntity(T t) {
		// TODO Auto-generated method stub
		dao.deleteEntity(t);
	}

	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Map<String,Object> objects) {
		// TODO Auto-generated method stub
		return dao.findEntityByHQL(hql, objects);
	}

	@Override
	public List<T> findEntityList(Class<T> cls) {
		// TODO Auto-generated method stub
		return dao.findEntityList(cls);
	}

	@Override
	public int batchUpdateOrDelete(String sql, Object... values) {
		// TODO Auto-generated method stub
		return dao.batchUpdateOrDelete(sql, values);
	}

}
