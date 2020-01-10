package com.jdry.pms.houseWork.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.houseWork.pojo.HouseworkType;

@Repository
@Transactional
public class HouseWorkTypeDao extends HibernateDao{

	public List<HouseworkType> queryType()
	{
		String hql="select id,project_name from "+HouseworkType.class.getName()+" t";
		List<HouseworkType> ls=this.query(hql);
		if (null != ls && ls.size() > 0) {
			return ls;
		} else {
			return null;
		}
	}

}
