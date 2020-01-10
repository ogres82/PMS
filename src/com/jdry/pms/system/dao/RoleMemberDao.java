package com.jdry.pms.system.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.pojo.VBdf2RoleMember;

@Repository
@Transactional
public class RoleMemberDao extends HibernateDao{

	public void loadMembers(Page<VBdf2RoleMember> page, Map<String, Object> parameter) {
		String hql = " from "+VBdf2RoleMember.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VBdf2RoleMember.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String roleId = parameter.get("roleId")==null?"":parameter.get("roleId").toString();
			if(!roleId.equals("")){
				whereCase += " and a.roleId =:roleId ";
				sqlCount += " and b.roleId =:roleId ";
				map.put("roleId", roleId);
			}
		}		
		hql += whereCase;				
		System.out.println("hql==="+hql);
		
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
