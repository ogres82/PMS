package com.jdry.pms.comm.util;

import java.util.List;

import org.hibernate.Session;

/**
 * 分页工具类
 * 
 * @author wangl
 * 
 * @param limit 每页显示的记录数;offset 偏移量 sql语句起始索引
 * 
 * @Ohter currentPage 当前页数;totalPage 总页数;totalCount 总记录数;entityList 每页显示数据记录的集合
 */
public class PageUtil<T> {
	private int currentPage; // 当前页数
	private int totalPage; // 总页数
	private int totalCount; // 总记录数
	private int limit; // 每页显示的记录数
	private int offset; // 偏移量 sql语句起始索引
	private List<T> entityList; // 每页显示数据记录的集合；

	public int getCurrentPage() {
		if (offset !=  0) {
			currentPage = offset / limit;
		}
		currentPage += 1;
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		if (totalCount != 0)
			totalPage = (totalCount + limit - 1) / limit;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<T> getEntityList() {
		return entityList;
	}

	@SuppressWarnings({ "unchecked" })
	public void setEntityList(StringBuilder sql, StringBuilder sqlCount, Session session) {
		if (sql != null) {
			entityList = session.createSQLQuery(sql.toString()).list(); // 查询数据
		}
		String countStr = "0";
		if (sqlCount != null) {
			countStr = session.createSQLQuery(sqlCount.toString()).list().get(0).toString(); // 查询数据总条数
		}
		totalCount = Integer.parseInt(countStr);
		session.flush();
		session.close();
	}

	@SuppressWarnings({ "unchecked" })
	public void setEntityList(String sql, String sqlCount, Session session) {
		if (sql != null) {
			entityList = session.createSQLQuery(sql).list(); // 查询数据
		}
		String countStr = "0";
		if (sqlCount != null) {
			countStr = session.createSQLQuery(sqlCount).list().get(0).toString(); // 查询数据总条数
		}
		totalCount = Integer.parseInt(countStr);
		session.flush();
		session.close();
	}

	public void setEntityList(String sql, String offset, String pageSizeStr, Session session) {
		String limit = " ";
		if (null != offset && !offset.isEmpty() && null != pageSizeStr && !pageSizeStr.isEmpty()) {
			limit = " limit " + Integer.parseInt(offset) + "," + Integer.parseInt(pageSizeStr) + " ";
		}
		String sqlCount = "Select count(*) from  (" + sql + ") as tem ";
		sql += limit;
		setEntityList(sql, sqlCount, session);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setEntityList(List list,StringBuilder sqlCount,Session session) {
		String countStr = "0";
		if (sqlCount != null) {
			countStr = session.createSQLQuery(sqlCount.toString()).list().get(0).toString(); // 查询数据总条数
		}
		totalCount = Integer.parseInt(countStr);
		session.flush();
		session.close();
		
		entityList = list;
	}
}