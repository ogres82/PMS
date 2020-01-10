package com.jdry.pms.basicInfo.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

@Repository
@Transactional
public class PropertyManagerDao extends HibernateDao {

	public void query(Page<Emp> page, Map<String, Object> parameter, Criteria criteria) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String empNo = parameter.get("empNo") != null ? parameter.get("empNo").toString() : "";
		String empIdNo = parameter.get("empIdNo") != null ? parameter.get("empIdNo").toString() : "";
		String empName = parameter.get("empName") != null ? parameter.get("empName").toString() : "";
		String empSex = parameter.get("empSex") != null ? parameter.get("empSex").toString() : "";
		String empDeptId = parameter.get("empDeptId") != null ? parameter.get("empDeptId").toString() : "";
		String empPostId = parameter.get("empPostId") != null ? parameter.get("empPostId").toString() : "";
		Date empEntryTime = (Date) (parameter.get("empEntryTime") != null ? parameter.get("empEntryTime") : null);
		Date empQuitTime = (Date) (parameter.get("empQuitTime") != null ? parameter.get("empQuitTime") : null);
		String empStatus = parameter.get("empStatus") != null ? parameter.get("empStatus").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + Emp.class.getName() + " du where 1=1";
		String sqlCount = "select count(*) from " + Emp.class.getName() + " du where 1=1";
		if (empNo != null && !"".equals(empNo)) {
			map.put("empNo", "%" + empNo + "%");
			sql += " and du.empNo like:empNo";
			sqlCount += " and du.empNo like:empNo";
		}
		if (empIdNo != null && !"".equals(empIdNo)) {
			map.put("empIdNo", "%" + empIdNo + "%");
			sql += " and du.empIdNo like:empIdNo";
			sqlCount += " and du.empIdNo like:empIdNo";
		}
		if (empName != null && !"".equals(empName)) {
			map.put("empName", "%" + empName + "%");
			sql += " and du.empName like:empName";
			sqlCount += " and du.empName like:empName";
		}
		if (empSex != null && !"".equals(empSex)) {
			map.put("empSex", empSex);
			sql += " and du.empSex=:empSex";
			sqlCount += " and du.empSex=:empSex";
		}
		if (empDeptId != null && !"".equals(empDeptId)) {
			map.put("empDeptId", empDeptId);
			sql += " and du.empDeptId =:empDeptId";
			sqlCount += " and du.empDeptId =:empDeptId";
		}
		if (empPostId != null && !"".equals(empPostId)) {
			map.put("empPostId", empPostId);
			sql += " and du.empPostId =:empPostId";
			sqlCount += " and du.empPostId =:empPostId";
		}

		if (empEntryTime != null && !"".equals(empEntryTime)) {
			map.put("empEntryTime", df.format(empEntryTime));
			sql += " and date_format(du.empEntryTime,'%Y-%m-%d')=:empEntryTime";
			sqlCount += " and date_format(du.empEntryTime,'%Y-%m-%d')=:empEntryTime";
		}
		if (empQuitTime != null && !"".equals(empQuitTime)) {
			map.put("empQuitTime", df.format(empQuitTime));
			sql += " and date_format(du.empQuitTime,'%Y-%m-%d')=:empQuitTime";
			sqlCount += " and date_format(du.empQuitTime,'%Y-%m-%d')=:empQuitTime";
		}

		if (empStatus != null && !"".equals(empStatus)) {
			map.put("empStatus", empStatus);
			sql += " and du.empStatus=:empStatus";
			sqlCount += " and du.empStatus=:empStatus";
		}

		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public Collection<Emp> getPropertyManager() {
		@SuppressWarnings("unused")
		String sql = "from Temp t";

		return null;
	}

	public void savePropertyManager(Collection<Emp> emps) {
		Session session = this.getSessionFactory().openSession();
		try {
			for (Emp emp : emps) {
				EntityState state = EntityUtils.getState(emp);
				if (state.equals(EntityState.NEW)) {
					session.save(emp);
				} else if (state.equals(EntityState.MODIFIED)) {
					session.update(emp);
				} else if (state.equals(EntityState.DELETED)) {
					session.delete(emp);
				}
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	public String checkEmpName(String parameter) throws InterruptedException {

		List<Object> object = this.query("from Emp where empName='" + parameter + "'");

		if (object.size() == 0) {
			return null;
		} else {
			return "姓名\"" + parameter + "\"已经被人注册了 。";
		}
	}

	public Collection<DefaultPosition> queryPosition(Map<String, Object> parameter) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		String id = parameter.get("id") != null ? parameter.get("id").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + DefaultPosition.class.getName() + " du where 1=1 ";
		if (id != null && !"".equals(id)) {
			map.put("id", id);
			sql += " and du.id =:id ";
		}
		return this.query(sql, map);
	}

	public Collection<DefaultDept> queryDept(Map<String, Object> parameter) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		String id = parameter.get("id") != null ? parameter.get("id").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + DefaultDept.class.getName() + " du where 1=1";
		if (id != null && !"".equals(id)) {
			map.put("id", id);
			sql += " and du.id =:id ";
		}
		return this.query(sql, map);
	}

	public Emp getTemp(Map<String, Object> parameter) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		String empId = parameter.get("empId") != null ? parameter.get("empId").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + Emp.class.getName() + " du where 1=1 ";
		if (empId != null && !"".equals(empId)) {
			map.put("empId", empId);
			sql += " and du.empId =:empId ";
		}
		return (Emp) this.query(sql, map).get(0);

	}

	public void queryPropertyByParam(Page<VEmp> page, Map<String, Object> parameter, String type) {
		String hql = " from " + VEmp.class.getName();
		String where = "";
		if (type != null && !type.isEmpty()) {
			where = " where state='" + type + "' ";
		} else {
			where = " where 1=1 ";
		}
		String whereCase = " a " + where;
		String sqlCount = "select count(*) from " + VEmp.class.getName() + " b " + where;
		Map<String, Object> map = new HashMap<String, Object>();
		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			if (!search.equals("")) {
				whereCase += " and a.empNo like:empNo " + " or a.empName like:empName" + " or a.dept_name like:dept_name" + " or a.post_name like:post_name " + " or a.status like:status";
				sqlCount += " and b.empNo like:empNo " + " or b.empName like:empName" + " or b.dept_name like:dept_name" + " or b.post_name like:post_name " + " or b.status like:status";

				map.put("empNo", "%" + search + "%");
				map.put("empName", "%" + search + "%");
				map.put("dept_name", "%" + search + "%");
				map.put("post_name", "%" + search + "%");
				map.put("status", "%" + search + "%");
			}
		}
		hql += whereCase;
		System.out.println("hql===" + hql);

		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public List<VEmp> queryAll(Map<String, Object> parameter, String type) {
		String hql = " from " + VEmp.class.getName();
		String where = "";
		if (type != null && !type.isEmpty()) {
			where = " where state='" + type + "' ";
		} else {
			where = " where 1=1 ";
		}

		String whereCase = " a " + where;
		Map<String, Object> map = new HashMap<String, Object>();

		// 条件查询
		if (parameter != null) {
			/*
			 * String owner_name = parameter.get("owner_name")==null?"":parameter.get("owner_name").toString(); String charge_type_name =
			 * parameter.get("charge_type_name")==null?"":parameter.get("charge_type_name").toString(); String room_no =
			 * parameter.get("room_no")==null?"":parameter.get("room_no").toString();
			 * 
			 * if(!owner_name.equals("")){ whereCase += " and a.owner_name like:owner_name "; sqlCount += " and b.owner_name like:owner_name "; map.put("owner_name",
			 * "%"+owner_name+"%"); }
			 * 
			 * if(!charge_type_name.equals("")){ whereCase += " and a.charge_type_name like:charge_type_name "; sqlCount += " and b.charge_type_name like:charge_type_name ";
			 * map.put("charge_type_name", "%"+charge_type_name+"%"); }
			 * 
			 * if(!room_no.equals("")){ whereCase += " and a.room_no like:room_no "; sqlCount += " and b.room_no like:room_no "; map.put("room_no", "%"+room_no+"%"); }
			 */
		}
		hql += whereCase;
		System.out.println("hql===" + hql);

		List<VEmp> list = this.query(hql, map);
		return list;
	}

	public void saveAll(Collection<Emp> emps) {

		Session session = this.getSessionFactory().openSession();
		for (Emp emp : emps) {
			session.saveOrUpdate(emp);
		}
		session.flush();
		session.close();
	}

	public List<DirDirectoryDetail> getDirectoryLikeCode(String code) {
		String sql = "select code,code_detail,code_detail_name from " + DirDirectoryDetail.class.getName() + " di";
		if (code != null && !"".equals(code)) {
			sql += " where di.code like '" + code + "%' order by code";
		}
		System.out.println(sql);
		return this.query(sql);
	}

	public void addEmp(Emp emp) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(emp);
		session.flush();
		session.close();
		session = null;
	}

	public void deleteEmp(String empId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = empId.split(",");

		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
			Emp emp = getEmpById(ids[i]);
			if (null != emp) {
				session.delete(emp);
			}
		}
		session.flush();
		session.close();
	}

	// add huanglin
	public List<VEmp> queryEmpAll(Map<String, Object> parameter) {

		String hql = "from VEmp where 1=1";
		if (parameter.size() > 0) {
			if (!"".equals(parameter.get("empDeptId"))) {
				hql += " and empDeptId = '" + parameter.get("empDeptId") + "' ";
			}
			if (!"".equals(parameter.get("empPosId"))) {
				hql += " and empPostId = '" + parameter.get("empPosId") + "' ";
			}
			if (!"".equals(parameter.get("empState"))) {
				hql += " and empStatus = '" + parameter.get("empState") + "' ";
			}
			if (!"".equals(parameter.get("startDate")) && !"".equals(parameter.get("endDate"))) {
				hql += "and empEntryTime BETWEEN '" + parameter.get("startDate") + "' AND '" + parameter.get("endDate") + "'";
				hql += "or empQuitTime BETWEEN '" + parameter.get("startDate") + "' AND '" + parameter.get("endDate") + "'";

			}
		}

		List<VEmp> list = this.query(hql);
		return list;
	}

	public Emp getEmpById(String empId) {

		List<Emp> lists = this.query("from " + Emp.class.getName() + " where empId='" + empId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> queryEmpDept(Map<String, Object> parameter) {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "select count(a.emp_id) empNum,a.emp_dept_id,a.dept_name,b.DESC_ from v_t_emp a left join bdf2_dept b on a.emp_dept_id = b.ID_ GROUP BY a.emp_dept_id order by a.emp_dept_id desc";
		List<Object> lists = session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public List<Emp> getEmpByDeptId(String deptId) {

		List<Emp> lists = this.query("from " + Emp.class.getName() + " where empDeptId='" + deptId + "'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public List<Emp> getEmpAllNum() {
		List<Emp> lists = this.query("from " + Emp.class.getName());
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public List<VEmp> queryEmpByKeyword(Map<String, Object> param) {

		String hql = " from " + VEmp.class.getName();
		String whereCase = " a where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (param != null) {
			String search = param.get("keyword") == null ? "" : param.get("keyword").toString();
			if (!search.equals("")) {
				whereCase += " and ( a.empNo like:empNo " + " or a.empName like:empName" + " or a.empPhone like:empPhone" + " or a.empDeptName like:empDeptName " + " or a.empPostName like:empPostName ) ";

				map.put("empNo", "%" + search + "%");
				map.put("empName", "%" + search + "%");
				map.put("empPhone", "%" + search + "%");
				map.put("empDeptName", "%" + search + "%");
				map.put("empPostName", "%" + search + "%");
			}
		}
		hql += whereCase;
		System.out.println("hql===" + hql);
		return this.query(hql, map);
	}

	// 获取物业人员电话
	public String getEmpContacts() {
		Session session = this.getSessionFactory().openSession();
		String sqlStr = "SELECT emp_dept_name,emp_name,emp_post_name,emp_mobile_phone FROM	v_t_emp " + "WHERE isnull(emp_quit_time) = 1 " + " ORDER BY emp_dept_id DESC,IFNULL(emp_position_levl, 0) DESC,emp_name DESC";
		List list = session.createSQLQuery(sqlStr.toString()).list();
		session.close();

		if (list != null && list.size() > 0) {
			List contacts = new ArrayList();
			Map<String, Object> mapContacts = new HashMap<String, Object>();
			String deptName = "";

			for (int i = 0; i < list.size(); i++) {
				List items = new ArrayList();
				Map<String, Object> mapItem = new HashMap<String, Object>();
				Object[] contactsObj = (Object[]) list.get(i);
				mapItem.put("empName", contactsObj[1]);
				mapItem.put("empCName", com.jdry.pms.comm.util.ChineseToEnglish.getPingYin(contactsObj[1].toString()));
				mapItem.put("empFName", com.jdry.pms.comm.util.ChineseToEnglish.getPinYinHeadChar(contactsObj[1].toString()));
				mapItem.put("empPost", contactsObj[2]);
				mapItem.put("empPhone", contactsObj[3]);
				// 部门为空的情况处理
				if ((String) contactsObj[0] == null) {
					contactsObj[0] = "";
				}

				if (!deptName.equals(contactsObj[0])) {
					items.add(mapItem);
					String itemString = JSON.toJSONString(items);
					JSONArray itemArray = JSON.parseArray(itemString);
					mapContacts = new HashMap<String, Object>();
					mapContacts.put("deptName", contactsObj[0]);
					mapContacts.put("empInfo", itemArray);
					contacts.add(mapContacts);
					deptName = (String) contactsObj[0];
				} else {
					JSONArray array = (JSONArray) mapContacts.get("empInfo");
					array.add(JSON.toJSON(mapItem));
					mapContacts.put("empInfo", array);
				}

			}
			String jsonString = JSON.toJSONString(contacts);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";

		} else {

			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}

	public List getSearchEmp(String keyword) {

		String sql = "SELECT emp_name,emp_phone,emp_sex,emp_dept_id,emp_dept_name,emp_post_id,emp_post_name,emp_birth,emp_status_name,IFNULL(emp_address,'') FROM v_t_emp where 1=1 and emp_name like '%" + keyword + "%' " + "or emp_phone like '%" + keyword + "%' " + "or emp_dept_name like '%" + keyword + "%' limit 10";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		System.out.println(sql);
		session.flush();
		session.close();
		return result;
	}

	// 获取员工生日以及员工在公司周年
	public List<Map> getEmpInfoForSMS() {
		String sql = "SELECT empName,empPhone,empYear,smsType FROM v_emp_blessing_info";

		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	// 获取员工信息以及部门信息，用于员工考核APP接口
	public List<Map> getEmpForApp(String data) {

		JSONObject jsonData = JSON.parseObject(data);
		String kpiType = jsonData.getString("kpiType");
		String sql ="";
		if ("0".equals(kpiType)) {
			 sql = "SELECT emp_id as empId,emp_name as empName,emp_post_id as empPostId,emp_post_name as empPostName,emp_dept_id as empDeptId,emp_dept_name as empDeptName FROM v_t_emp where emp_status_name = '在职'";

		} else {
			 sql = " SELECT ID_ as deptId,NAME_ as deptName FROM bdf2_dept ";
		}

		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		session.flush();
		session.close();
		return list;
	}
}