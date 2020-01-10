package com.jdry.pms.assignWork.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;

/**
 * 回访接口
 * @author hezuping
 *
 */
@Repository
public class VisitWorkServerInterfaceServiceDAO extends HibernateDao
{
	public WorkMainDispatchEntity getVisitInfo(String mtn_id)
	{
		String hql=" from WorkMainDispatchEntity where mtn_id='"+mtn_id+"'";
		List<WorkMainDispatchEntity> result=this.query(hql);
		WorkMainDispatchEntity we=null;
		if(result.size()>0)
		{
			we=result.get(0);
			
		}
		return we;
	}
}
