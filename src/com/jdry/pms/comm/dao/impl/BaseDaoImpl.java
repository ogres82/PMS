package com.jdry.pms.comm.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.jdry.pms.comm.dao.BaseDao;

@SuppressWarnings("unchecked")
@Transactional

public class BaseDaoImpl<T> implements BaseDao<T> {

	private Class<T> clazz;

	/**
	 * 向DAO层注入SessionFactory
	 */
	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 通过构造方法指定DAO的具体实现类
	 */
	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		// System.out.println("DAO的真实实现类是：" + this.clazz.getName());
	}

	/**
	 * 获取当前工作的Session
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void saveEntity(T t) {
		// TODO Auto-generated method stub
		this.getSession().save(t);

	}

	@Override
	public void saveOrUpdateEntity(T t) {
		// TODO Auto-generated method stub
		this.getSession().saveOrUpdate(t);
	}

	@Override
	public void updateEntity(T t) {
		// TODO Auto-generated method stub
		this.getSession().update(t);
	}

	@Override
	public void deleteEntity(T t) {
		// TODO Auto-generated method stub
		this.getSession().delete(t);
	}

	/*
	 * @Override public void batchEntityByHQL(String hql, Object... objects) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void executeSQL(String sql, Object... objects) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public T loadEntity(Integer id) { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public T getEntity(Integer id) { // TODO Auto-generated method stub return null; }
	 */
	@Override
	public List<T> findEntityByHQL(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery(hql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.list();
	}

	/*
	 * @Override public Object uniqueResult(String hql, Object... objects) { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public List<?> executeSQLQuery(Class<?> clazz, String sql, Object... objects) { // TODO Auto-generated method stub return null; }
	 */
	@Override
	public List<T> findEntityList(Class<T> cls) {
		// TODO Auto-generated method stub
		String hql = "FROM " + cls.getName();

		List<T> list = this.findEntityByHQL(hql, null);
		return list;
	}

	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return (T) this.getSession().get(this.clazz, id);
	}

	@Override
	public int batchUpdateOrDelete(final String sql, final Object... values) {
		// 是否是合法的sql插入语句
		if (sql.indexOf("update") != -1||sql.indexOf("delete") != -1) {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
			return query.executeUpdate();
		}
		return 0;
	}

}
