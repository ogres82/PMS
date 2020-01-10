package com.jdry.pms.comm.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {
	
	public void saveEntity(T t);

	public void saveOrUpdateEntity(T t);

	public void updateEntity(T t);

	public void deleteEntity(T t);

	public T findById(Serializable id);

	public List<T> findEntityByHQL(String hql, Map<String,Object> objects);	

	public List<T> findEntityList(Class<T> cls);

	public int batchUpdateOrDelete( String sql,  Object... values);
}
