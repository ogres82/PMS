package com.jdry.pms.basicInfo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.HouseProperty;

@Repository
@Transactional
public class OwnerStayInDao extends HibernateDao{
	public void query(Page<HouseProperty> page, Map<String, Object> parameter,
			Criteria criteria) {
		
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		
		
		String roomNo = parameter.get("roomNo")!=null?parameter.get("roomNo").toString():"";
		String belongSbId = parameter.get("belongSbId")!=null?parameter.get("belongSbId").toString():"";
		String houseType = parameter.get("houseType")!=null?parameter.get("houseType").toString():"";
		String buildArea = parameter.get("buildArea")!=null?parameter.get("buildArea").toString():"";
		String roomType = parameter.get("roomType")!=null?parameter.get("roomType").toString():"";
		String roomState = "1";
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+HouseProperty.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+HouseProperty.class.getName()+" du where 1=1";
	    if(roomNo!=null&&!"".equals(roomNo)){
	        map.put("roomNo", "%"+roomNo+"%");
	        sql+=" and du.roomNo like:roomNo";
	        sqlCount+=" and du.roomNo like:roomNo";
	    }
	    if(belongSbId!=null&&!"".equals(belongSbId)){
	        map.put("belongSbId", belongSbId);
	        sql+=" and du.belongSbId =:belongSbId";
	        sqlCount+=" and du.belongSbId =:belongSbId";
	    }
	    if(houseType!=null&&!"".equals(houseType)){
	        map.put("houseType", "%"+houseType+"%");
	        sql+=" and du.houseType like:houseType";
	        sqlCount+=" and du.houseType like:houseType";
	    }
	    if(buildArea!=null&&!"".equals(buildArea)){
	        map.put("buildArea", buildArea);
	        sql+=" and du.buildArea =:buildArea";
	        sqlCount+=" and du.buildArea =:buildArea";
	    }
	    if(roomType!=null&&!"".equals(roomType)){
	        map.put("roomType", roomType);
	        sql+=" and du.roomType =:roomType";
	        sqlCount+=" and du.roomType =:roomType";
	    }
	    if(roomState!=null&&!"".equals(roomState)){
	        map.put("roomState", roomState);
	        sql+=" and du.roomState =:roomState";
	        sqlCount+=" and du.roomState =:roomState";
	    }
	    
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
