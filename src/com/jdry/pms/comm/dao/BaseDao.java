package com.jdry.pms.comm.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	
	public void saveEntity(T t);

	public void saveOrUpdateEntity(T t);

	public void updateEntity(T t);

	public void deleteEntity(T t);
	
	 
	public int batchUpdateOrDelete( String sql,  Object... values); 
	
	public T findById(Serializable id); 
	
	public List<T> findEntityByHQL(String hql, Map<String,Object> objects);
	
	public List<T> findEntityList(Class<T> cls);
	

	// 按照HQL语句进行批量更新
	//public void batchEntityByHQL(String hql, Object... objects);

	// 执行原生的sql语句
	//public void executeSQL(String sql, Object... objects);

	// 读操作
	//public T loadEntity(Integer id);

	//public T getEntity(Integer id);


	// 单值检索,确保查询结果有且只有一条记录
	//public Object uniqueResult(String hql, Object... objects);

	// 执行原生的sql查询(可以指定是否封装成实体)
	//public List<?> executeSQLQuery(Class<?> clazz, String sql, Object... objects);

}
