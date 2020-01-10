package com.jdry.pms.leaseManage.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.leaseManage.pojo.LeaseInfo;
import com.jdry.pms.basicInfo.pojo.VHouseProperty;

@Repository
public class LeaseDao extends HibernateDao {

	@SuppressWarnings("rawtypes")
	public List<Map> getLeaseInfoAll(Map<String, Object> parameter) {
		String sql = "SELECT * FROM v_lease_manage_info WHERE 1=1";

		/*
		 * String empDeptId = parameter.get("empDeptId").toString(); String insertDate =
		 * parameter.get("insertDate").toString(); String operId =
		 * parameter.get("operId").toString(); String recId =
		 * parameter.get("recId").toString(); String kpiType =
		 * parameter.get("kpiType").toString(); if (!"".equals(empDeptId)) { sql +=
		 * " AND empDeptId = '" + empDeptId + "'"; } if (!"".equals(insertDate)) { sql
		 * += " AND substring(insertDate,1,10) = '" + insertDate + "'"; } if
		 * (!"".equals(operId)) { sql += " AND operId = '" + operId + "'"; } if
		 * (!"".equals(recId)) { sql += " AND recId = '" + recId + "'"; } if
		 * (!"".equals(kpiType)) { sql += " AND kpiType = '" + kpiType + "'"; }
		 */
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		return list;
	}

	public void saveLeaseInfo(LeaseInfo li, String operate) {
		Session session = this.getSessionFactory().openSession();
		if("renew".equals(operate)) {
			String Id = li.getLeaseId();
			LeaseInfo li1 = getLeaseInfoById(Id);
			if (null != li1) {
				li1.setState("0");
				session.saveOrUpdate(li1);
			}
			li.setLeaseId(null);	
		}		
		session.saveOrUpdate(li);
		session.flush();
		session.close();
	}

	public void delLeaseInfo(String Id) {
		String[] ids = Id.split(",");
		LeaseInfo li = new LeaseInfo();
		for (int i = 0; i < ids.length; i++) {
			li = getLeaseInfoById(ids[i]);
			if (null != li) {
				li.setDeleteIs("1");
				this.saveLeaseInfo(li, "update");
			}
		}
	}

	public LeaseInfo getLeaseInfoById(String Id) {
		if ("".equals(Id) && Id == null) {
			return null;
		} else {
			LeaseInfo li = new LeaseInfo();
			String hql = " FROM LeaseInfo WHERE 1=1 AND leaseId ='" + Id + "'";
			li = (LeaseInfo) this.query(hql).get(0);
			return li;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<Map> queryShopInfo(String keyword) {

		String sql = "SELECT distinct buildName,communityName,storiedBuildName,roomNo,roomId,roomAddr,buildArea FROM v_lease_manage_info WHERE 1=1 ";

		if (null != keyword && !"".equals(keyword)) {
			sql += " and (roomNo like '%" + keyword + "%'";
			sql += " or communityName like '%" + keyword + "%'  ";
			sql += " or storiedBuildName like '%" + keyword + "%'  ";
			sql += " or buildName like '%" + keyword + "%'  ";
			sql += ")";
		}
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		return list;
	}
}
