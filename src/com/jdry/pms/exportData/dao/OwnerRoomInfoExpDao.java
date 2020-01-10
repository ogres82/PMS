package com.jdry.pms.exportData.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.exportData.pojo.OwnerRoomInfoExp;

@Repository
@Transactional
public class OwnerRoomInfoExpDao extends HibernateDao {

	public List<OwnerRoomInfoExp> queryOwnerRoomInfo(Map<String, Object> parameter) {
		String hql = " FROM OwnerRoomInfoExp WHERE 1=1";
		String strWhere = getCriteria(parameter);
		
		hql += strWhere;
		List<OwnerRoomInfoExp> list = this.query(hql);

		return list;
	}

	public String getCriteria(Map<String, Object> parameter) {
		String strWhere = "";

		String communityId = parameter.get("communityId") == null ? "" : parameter.get("communityId").toString();
		String belongSbId = parameter.get("belongSbId") == null ? "" : parameter.get("belongSbId").toString();
		String roomType = parameter.get("roomType") == null ? "" : parameter.get("roomType").toString();
		String roomState = parameter.get("roomState") == null ? "" : parameter.get("roomState").toString();
		if (!communityId.equals("")) {
			strWhere += " and communityId ='" + communityId + "'";
		}
		if (!belongSbId.equals("")) {
			strWhere += " and belongSbId ='" + belongSbId + "'";
		}
		if (!roomType.equals("")) {
			strWhere += " and roomType ='" + roomType + "'";
		}
		if (!roomState.equals("")) {
			strWhere += " and roomState ='" + roomState + "'";
		}
		return strWhere;
	}
}
