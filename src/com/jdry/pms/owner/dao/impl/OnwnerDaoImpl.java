package com.jdry.pms.owner.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.owner.dao.OnwnerDao;
@Transactional
@Repository
public class OnwnerDaoImpl extends HibernateDao implements OnwnerDao {

	
	@Override
	@SuppressWarnings("unchecked")
	public List findOnwerInfo(Map<String, Object> parameter) {
		
		
		String sql="select * from T_ONWER";
		List ls= this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).list();
		
		return ls;
				
	}

}
