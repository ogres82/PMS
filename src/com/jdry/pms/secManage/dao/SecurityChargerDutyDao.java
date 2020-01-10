package com.jdry.pms.secManage.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.secManage.pojo.SecurityChargerDuty;
import com.jdry.pms.system.service.PrivilegeConfigService;

@Repository
@Transactional
public class SecurityChargerDutyDao extends HibernateDao{


	@Resource
    public NoticeService noticeService;
	@Resource
	PrivilegeConfigService configService;
	
	public void query(Page<Emp> page,Map<String, Object> parameter, Criteria criteria) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String empNo = parameter.get("empNo")!=null?parameter.get("empNo").toString():"";
		String empIdNo = parameter.get("empIdNo")!=null?parameter.get("empIdNo").toString():"";
		String empName = parameter.get("empName")!=null?parameter.get("empName").toString():"";
		String empSex = parameter.get("empSex")!=null?parameter.get("empSex").toString():"";
		String empDeptId = parameter.get("empDeptId")!=null?parameter.get("empDeptId").toString():"";
		String empPostId = parameter.get("empPostId")!=null?parameter.get("empPostId").toString():"";
		Date empEntryTime = (Date) (parameter.get("empEntryTime")!=null?parameter.get("empEntryTime"):null);
		Date empQuitTime = (Date) (parameter.get("empQuitTime")!=null?parameter.get("empQuitTime"):null);
		String empStatus = parameter.get("empStatus")!=null?parameter.get("empStatus").toString():"";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+Emp.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+Emp.class.getName()+" du where 1=1";
	    if(empNo!=null&&!"".equals(empNo)){
	        map.put("empNo", "%"+empNo+"%");
	        sql+=" and du.empNo like:empNo";
	        sqlCount+=" and du.empNo like:empNo";
	    }
	    if(empIdNo!=null&&!"".equals(empIdNo)){
	        map.put("empIdNo", "%"+empIdNo+"%");
	        sql+=" and du.empIdNo like:empIdNo";
	        sqlCount+=" and du.empIdNo like:empIdNo";
	    }
	    if(empName!=null&&!"".equals(empName)){
	        map.put("empName", "%"+empName+"%");
	        sql+=" and du.empName like:empName";
	        sqlCount+=" and du.empName like:empName";
	    }
	    if(empSex!=null&&!"".equals(empSex)){
	        map.put("empSex", empSex);
	        sql+=" and du.empSex=:empSex";
	        sqlCount+=" and du.empSex=:empSex";
	    }
	    if(empDeptId!=null&&!"".equals(empDeptId)){
	        map.put("empDeptId", empDeptId);
	        sql+=" and du.empDeptId =:empDeptId";
	        sqlCount+=" and du.empDeptId =:empDeptId";
	    }
	    if(empPostId!=null&&!"".equals(empPostId)){
	        map.put("empPostId", empPostId);
	        sql+=" and du.empPostId =:empPostId";
	        sqlCount+=" and du.empPostId =:empPostId";
	    }
	    
	    if(empEntryTime!=null&&!"".equals(empEntryTime)){
	        map.put("empEntryTime", df.format(empEntryTime));
	        sql+=" and date_format(du.empEntryTime,'%Y-%m-%d')=:empEntryTime";
	        sqlCount+=" and date_format(du.empEntryTime,'%Y-%m-%d')=:empEntryTime";
	    }
	    if(empQuitTime!=null&&!"".equals(empQuitTime)){
	        map.put("empQuitTime", df.format(empQuitTime));
	        sql+=" and date_format(du.empQuitTime,'%Y-%m-%d')=:empQuitTime";
	        sqlCount+=" and date_format(du.empQuitTime,'%Y-%m-%d')=:empQuitTime";
	    }
	    
	    if(empStatus!=null&&!"".equals(empStatus)){
	        map.put("empStatus", empStatus);
	        sql+=" and du.empStatus=:empStatus";
	        sqlCount+=" and du.empStatus=:empStatus";
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
	    try{
	        for (Emp emp : emps) {
	        	EntityState state=EntityUtils.getState(emp);
	            if (state.equals(EntityState.NEW)) {
	                session.save(emp);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(emp);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(emp);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	
	
	public String checkEmpName(String parameter)
			throws InterruptedException {
		
		List<Object> object =  this.query(
				"from Emp where empName='"+parameter+"'");

		if (object.size()==0) {
			return null;
		} else {
			return "姓名\"" + parameter + "\"已经被人注册了 。";
		}
	}


	public Collection<DefaultPosition> queryPosition(
			Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String id = parameter.get("id") != null?parameter.get("id").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+DefaultPosition.class.getName()+" du where 1=1 ";
		if(id!=null&&!"".equals(id)){
			map.put("id", id);
			sql += " and du.id =:id ";
		}
		return this.query(sql, map);
	}


	public Collection<DefaultDept> queryDept(Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String id = parameter.get("id") != null?parameter.get("id").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+DefaultDept.class.getName()+" du where 1=1";
		if(id!=null&&!"".equals(id)){
			map.put("id", id);
			sql += " and du.id =:id ";
		}
		return this.query(sql, map);
	}


	public Emp getTemp(Map<String, Object> parameter) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String empId = parameter.get("empId") != null?parameter.get("empId").toString():"";
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "from "+Emp.class.getName()+" du where 1=1 ";
		if(empId!=null&&!"".equals(empId)){
			map.put("empId", empId);
			sql += " and du.empId =:empId ";
		}
		return (Emp) this.query(sql, map).get(0);
		
	}


	public void queryChargerDutyByParam(Page<SecurityChargerDuty> page,
			Map<String, Object> parameter, String type) {
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		String hql = " from "+SecurityChargerDuty.class.getName();
		String where ="where 1=1";
		String sqlCount="select count(*) from "+SecurityChargerDuty.class.getName()+" a where 1=1";
//		Properties property = PositionServiceImpl.getProperty();
		Map<String,Object> map =new HashMap<String,Object>();
		
		String bajlPosiId = configService.loadPCByType("04").getPositionId(); //获取主管交接班审核岗位ID
//		String bajlPosiId = property.getProperty(MsgAndNoticeConstant.PROPERTY_BAJL_KEY);
		boolean isBajl=noticeService.isAuthorityAudit(user.getUsername(), bajlPosiId);
		if(!isBajl){
			where += " and (a.shiftPasserId=:currentUser or a.shiftTakerId=:currentUser) order by a.shiftDate desc";
			sqlCount+=" and (a.shiftPasserId=:currentUser or a.shiftTakerId=:currentUser) order by a.shiftDate desc";
			map.put("currentUser",user.getUsername());
		}else{
			where +=" order by a.shiftDate desc";
			sqlCount+=" order by a.shiftDate desc";
		}
		String whereCase = " a " + where; 
		hql += whereCase;				
		System.out.println("hql==="+hql);
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public List<Emp> queryAll(Map<String, Object> parameter, String type) {
		String hql = " from "+Emp.class.getName();		
		String where ="";
		if(type != null && !type.isEmpty()){
			where = " where state='"+type+"' ";
		}else{
			where = " where 1=1 ";
		}
		
		String whereCase = " a " + where;
		Map<String,Object> map =new HashMap<String,Object>();
		 
		 //条件查询
		if(parameter != null){
			/*
			String owner_name = parameter.get("owner_name")==null?"":parameter.get("owner_name").toString();
			String charge_type_name = parameter.get("charge_type_name")==null?"":parameter.get("charge_type_name").toString();
			String room_no = parameter.get("room_no")==null?"":parameter.get("room_no").toString();

			if(!owner_name.equals("")){
				whereCase += " and a.owner_name like:owner_name ";
				sqlCount += " and b.owner_name like:owner_name ";
				map.put("owner_name", "%"+owner_name+"%");
			}
			
			if(!charge_type_name.equals("")){
				whereCase += " and a.charge_type_name like:charge_type_name ";
				sqlCount += " and b.charge_type_name like:charge_type_name ";
				map.put("charge_type_name", "%"+charge_type_name+"%");
			}
			
			if(!room_no.equals("")){
				whereCase += " and a.room_no like:room_no ";
				sqlCount += " and b.room_no like:room_no ";
				map.put("room_no", "%"+room_no+"%");
			}
			*/
		}		
		hql += whereCase;				
		System.out.println("hql==="+hql);
		
		List<Emp> list = this.query(hql, map);
		return list;
	}


	public void saveAll(Collection<Emp> emps) {

		Session session = this.getSessionFactory().openSession();
		for(Emp emp:emps){
			session.saveOrUpdate(emp);		
		}
		
		session.flush();
		session.close();
	
	}


	public List<DirDirectoryDetail> getDirectoryLikeCode(String code) {
		String sql="select code,code_detail,code_detail_name from "+DirDirectoryDetail.class.getName() +" di";
	    if(code!=null&&!"".equals(code)){
	        sql+=" where di.code like '"+code+"%' order by code";
	    }
	    System.out.println(sql);
	    return this.query(sql);
	}


	public void addSecurityChargerDuty(SecurityChargerDuty securityChargerDuty) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(securityChargerDuty);
		session.flush();
		session.close();
		session = null;
	}


	public void deleteEmp(String empId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = empId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			SecurityChargerDuty securityChargerDuty = getChargerDutyById(ids[i]);
			if(null != securityChargerDuty){
				session.delete(securityChargerDuty);
			}
		}
		session.flush();
		session.close();
	}


	public SecurityChargerDuty getChargerDutyById(String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empId", empId);

		List<SecurityChargerDuty> lists = this
				.query("from " + SecurityChargerDuty.class.getName()
						+ " where recId='" + empId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}


	public List<DefaultUser> getAllUser() {
		List<DefaultUser> userList=new ArrayList<DefaultUser>();
		String sql="from "+DefaultUser.class.getName();
		userList = this.query(sql);
		return userList;
	}


}
