package com.jdry.pms.exportData.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.exportData.pojo.RoomChargeInfoExp;

@Repository
@Transactional
public class RoomChargeInfoExpDao extends HibernateDao {

	public List<RoomChargeInfoExp> queryOwnerRoomInfo(Map<String, Object> parameter) {
		String hql = " FROM RoomChargeInfoExp WHERE 1=1";
		String strWhere = getCriteria(parameter);

		hql += strWhere;

		List<RoomChargeInfoExp> list = this.query(hql);

		return list;
	}

	//拼接查询条件
	public String getCriteria(Map<String, Object> parameter) {
		String strWhere = "";

		String communityId = parameter.get("communityId") == null ? "" : parameter.get("communityId").toString();
		String belongSbId = parameter.get("belongSbId") == null ? "" : parameter.get("belongSbId").toString();
		String roomType = parameter.get("roomType") == null ? "" : parameter.get("roomType").toString();
		if (!communityId.equals("")) {
			strWhere += " and communityId ='" + communityId + "'";
		}
		if (!belongSbId.equals("")) {
			strWhere += " and belongSbId ='" + belongSbId + "'";
		}
		if (!roomType.equals("")) {
			strWhere += " and roomType ='" + roomType + "'";
		}

		return strWhere;
	}

}
