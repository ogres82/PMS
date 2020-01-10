package com.jdry.pms.secManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.secManage.pojo.SecurityFirecontrolDuty;
import com.jdry.pms.system.service.PrivilegeConfigService;
@Repository
@Transactional
public class SecurityFirecontrolDutyDao extends HibernateDao{
	@Resource
    public NoticeService noticeService;
	@Resource
	PrivilegeConfigService configService;
	public void deleteEmp(String empId) {

		Session session = this.getSessionFactory().openSession();
		String[] ids = empId.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			SecurityFirecontrolDuty securityFirecontrolDuty = getFirecontrolDutyById(ids[i]);
			if(null != securityFirecontrolDuty){
				session.delete(securityFirecontrolDuty);
			}
		}
		session.flush();
		session.close();
	
		
	}

	public SecurityFirecontrolDuty getFirecontrolDutyById(String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empId", empId);

		List<SecurityFirecontrolDuty> lists = this
				.query("from " + SecurityFirecontrolDuty.class.getName()
						+ " where recId='" + empId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	
	}

	public void addSecurityFirecontrolDuty(SecurityFirecontrolDuty scd) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(scd);
		session.flush();
		session.close();
		session = null;
	}

	public void queryFirecontrolDutyByParam(Page<SecurityFirecontrolDuty> page,
			Map<String, Object> parameter, String type) {
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		String hql = " from "+SecurityFirecontrolDuty.class.getName();
		String where ="where 1=1";
		String sqlCount="select count(*) from "+SecurityFirecontrolDuty.class.getName()+" a where 1=1";
//		Properties property = PositionServiceImpl.getProperty();
		Map<String,Object> map =new HashMap<String,Object>();
		
		String bajlPosiId = configService.loadPCByType("03").getPositionId(); //获取消防中心交接班审核岗位ID
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

}
