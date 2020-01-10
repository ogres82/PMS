package com.jdry.pms.basicInfo.dao;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;

@Repository
@Transactional
public class RoomSituationDao extends HibernateDao{
	@SuppressWarnings("unchecked")
	public Collection<PropertyOwner> queryRoomAndOwnerByJdbc(Map<String,String> param){
		Session session = this.getSessionFactory().openSession();
		
		String buildId = param.get("buildId")!=null?param.get("buildId"):"";
		String keyWord = param.get("keyWord")!=null?param.get("keyWord"):"";
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select * from t_property_owner o where 1=1 ");
		if(buildId!=null&&!"".equals(buildId)){
			sqlStr.append(" and o.room_id in ");
			sqlStr.append(" (select h.room_id from t_house_property h where h.belong_sb_id in ");
			sqlStr.append(" (select p.storied_build_id from t_building_property p where p.belong_comm_id in ");
			sqlStr.append(" (select a.community_id from t_area_property a where a.belong_build_id = '"+buildId+"')))");
		}
		if(keyWord!=null&&!"".equals(keyWord)){
			sqlStr.append(" and (o.room_no like '%"+keyWord+"%' or o.owner_name like '%"+keyWord+"%') ");
		}
		 
		return session.createSQLQuery(sqlStr.toString()).addEntity(PropertyOwner.class).list();
	}
}
