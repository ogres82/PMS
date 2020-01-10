package com.jdry.pms.kpi.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.kpi.pojo.AssessmentRec;

@Repository
public class AssessmentRecDao extends BaseDaoImpl<AssessmentRec> {
	public AssessmentRecDao() {
		super();
	}

	// 返回考核记录全部字段
	@SuppressWarnings("rawtypes")
	public List<Map> queryAssessmentRecAll(Map<String, Object> parameter) {
		String sql = "SELECT * FROM v_assessment_rec WHERE 1=1";

		String empDeptId = parameter.get("empDeptId").toString();
		String insertDate = parameter.get("insertDate").toString();
		String operId = parameter.get("operId").toString();
		String recId = parameter.get("recId").toString();
		String kpiType = parameter.get("kpiType").toString();
		if (!"".equals(empDeptId)) {
			sql += " AND empDeptId = '" + empDeptId + "'";
		}
		if (!"".equals(insertDate)) {
			sql += " AND substring(insertDate,1,10) = '" + insertDate + "'";
		}
		if (!"".equals(operId)) {
			sql += " AND operId = '" + operId + "'";
		}
		if (!"".equals(recId)) {
			sql += " AND recId = '" + recId + "'";
		}
		if (!"".equals(kpiType)) {
			sql += " AND kpiType = '" + kpiType + "'";
		}
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		return list;
	}

	// 查询每月扣分记录
	@SuppressWarnings("rawtypes")
	public List<Map> queryAssessmentRecMonthly(Map<String, Object> parameter) {
		String sql = "select empId,empName,empSex,empDeptId,empDeptName,empPostId,empPostName,sum(kpiValue) as kpiValue,months " + "from v_assessment_monthly WHERE 1=1";

	

		String empDeptId = parameter.get("empDeptId").toString();
		String months = parameter.get("months").toString();

		if (!"".equals(empDeptId)) {
			sql += " AND empDeptId = '" + empDeptId + "'";
		}
		if (!"".equals(months)) {
			sql += " AND months = '" + months + "'";
		}
		sql += " GROUP BY empId,months";
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		return list;
	}
}