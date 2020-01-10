package com.jdry.pms.basicInfo.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VOwnerRoomBasicInfo;
import com.jdry.pms.basicInfo.pojo.VPropertyOwner;
import com.jdry.pms.comm.util.HikvisionUtil;
import com.jdry.pms.comm.util.HttpRequestUtil;

@Repository
@Transactional
public class OwnerInfoDao extends HibernateDao {

	public void query(Page<PropertyOwner> page, Map<String, Object> parameter, Criteria criteria) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String roomId = parameter.get("roomId") != null ? parameter.get("roomId").toString() : "";
		String roomNo = parameter.get("roomNo") != null ? parameter.get("roomNo").toString() : "";
		String ownerName = parameter.get("ownerName") != null ? parameter.get("ownerName").toString() : "";
		String ownerType = parameter.get("ownerType") != null ? parameter.get("ownerType").toString() : "";
		Date makeRoomDate = (Date) (parameter.get("makeRoomDate") != null ? parameter.get("makeRoomDate") : null);
		Date decorateDate = (Date) (parameter.get("decorateDate") != null ? parameter.get("decorateDate") : null);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + PropertyOwner.class.getName() + " du where 1=1";
		String sqlCount = "select count(*) from " + PropertyOwner.class.getName() + " du where 1=1";
		if (roomNo != null && !"".equals(roomNo)) {
			map.put("roomNo", "%" + roomNo + "%");
			sql += " and du.roomNo like:roomNo";
			sqlCount += " and du.roomNo like:roomNo";
		}
		if (ownerName != null && !"".equals(ownerName)) {
			map.put("ownerName", "%" + ownerName + "%");
			sql += " and du.ownerName like:ownerName";
			sqlCount += " and du.ownerName like:ownerName";
		}
		if (ownerType != null && !"".equals(ownerType)) {
			map.put("ownerType", ownerType);
			sql += " and du.ownerType =:ownerType";
			sqlCount += " and du.ownerType =:ownerType";
		}

		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.roomId =:roomId";
			sqlCount += " and du.roomId =:roomId";
		}

		if (makeRoomDate != null && !"".equals(makeRoomDate)) {
			map.put("makeRoomDate", df.format(makeRoomDate));
			sql += " and date_format(du.makeRoomDate,'%Y-%m-%d')=:makeRoomDate";
			sqlCount += " and date_format(du.makeRoomDate,'%Y-%m-%d')=:makeRoomDate";
		}
		if (decorateDate != null && !"".equals(decorateDate)) {
			map.put("decorateDate", df.format(decorateDate));
			sql += " and date_format(du.decorateDate,'%Y-%m-%d')=:decorateDate";
			sqlCount += " and date_format(du.decorateDate,'%Y-%m-%d')=:decorateDate";
		}

		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void saveOwnerInfo(Collection<PropertyOwner> owners) {

		Session session = this.getSessionFactory().openSession();
		try {
			for (PropertyOwner owner : owners) {
				EntityState state = EntityUtils.getState(owner);
				if (state.equals(EntityState.NEW)) {
					session.save(owner);
					String hql = "from HouseProperty du where du.roomId = ''";
					List<HouseProperty> houses = this.query(hql);
					for (HouseProperty house : houses) {
						house.setRoomState("2");
						session.update(house);
					}

				} else if (state.equals(EntityState.MODIFIED)) {
					session.update(owner);
				} else if (state.equals(EntityState.DELETED)) {
					session.delete(owner);
				}
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	public Collection<PropertyOwner> queryPropertyOwnerByParent(String id) {
		String roomId = id != null ? id : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + PropertyOwner.class.getName() + " du where 1=1";
		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.roomId =:roomId";
		}

		return this.query(sql, map);
	}

	public Collection<PropertyOwner> queryOwnerInfoByParam(Map<String, Object> parameter) {

		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}

		String mobilePhone = parameter.get("mobilePhone") != null ? parameter.get("mobilePhone").toString() : "";
		String keyword = parameter.get("keyword") != null ? parameter.get("keyword").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + PropertyOwner.class.getName() + " du where du.ownerIdentity=0";

		if (mobilePhone != null && !"".equals(mobilePhone)) {
			map.put("mobilePhone", mobilePhone);
			sql += " and phone=:mobilePhone";
		}
		if (keyword != null && !"".equals(keyword)) {
			map.put("ownerName", "%" + keyword + "%");
			map.put("mobilePhone", "%" + keyword + "%");
			sql += " and (phone like:mobilePhone ";
			sql += " or ownerName like:ownerName) ";
		}
		return this.query(sql, map);
	}

	public Collection<PropertyOwner> queryOwnerInfoByParam(Map<String, Object> parameter, String type) {

		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}

		String mobilePhone = parameter.get("mobilePhone") != null ? parameter.get("mobilePhone").toString() : "";
		String keyword = parameter.get("keyword") != null ? parameter.get("keyword").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + PropertyOwner.class.getName() + " du where 1=1";

		if (mobilePhone != null && !"".equals(mobilePhone)) {
			map.put("mobilePhone", mobilePhone);
			sql += " and phone=:mobilePhone";
		}
		if (keyword != null && !"".equals(keyword)) {
			map.put("ownerName", "%" + keyword + "%");
			map.put("mobilePhone", "%" + keyword + "%");
			sql += " and (phone like:mobilePhone ";
			sql += " or ownerName like:ownerName) ";
		}
		return this.query(sql, map);
	}

	public String ownerLogin(String ownerName, String pwd) {
		String hql = "from PropertyOwner where phone='" + ownerName + "'";
		List<PropertyOwner> list = this.query(hql);
		if (list.size() == 0) {
			return "0";
		} else {
			if (list.get(0).getPassword().equals(pwd)) {
				return "1";
			} else {
				return "0";
			}

		}

	}

	public void saveToken(String phone, String token) {
//		Session session = this.getSessionFactory().openSession();
		Session session = null;
		Transaction tr = null;
		Connection con = null;
		PreparedStatement ps = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH");
		Date d = new Date();
		String sql = "insert into t_property_owner_token(owner_phone,owner_token) values('" + phone + "','" + token + "')";
		session = this.getSessionFactory().openSession();
		tr = session.beginTransaction();
		con = session.connection();
		try {
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
			tr.commit();
			session.close();
			con.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean checkToken(String token) {
		Session session = null;
		boolean flag = true;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select t.owner_phone from t_property_owner_token t where t.owner_token='" + token + "'";
		session = this.getSessionFactory().openSession();

		con = session.connection();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;
			} else {
				flag = false;
			}

			session.close();
			con.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	public void registerOwner(PropertyOwner owner, String type) {
		Session session = this.getSessionFactory().openSession();
		if (type.equals("save")) {
			session.save(owner);
		} else if (type.equals("update")) {
			session.update(owner);
		} else if (type.equals("delete")) {
			session.delete(owner);
		}
		session.flush();
		session.close();
	}

	public List queryBasicInfo(String keyword) {
		String sql = "select * from v_area_build_house_owner_rela where owner_name like '%" + keyword + "%' " + "or room_no like '%" + keyword + "%' " + "or phone like '%"
				+ keyword + "%' limit 10";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).addScalar("community_id", StandardBasicTypes.STRING).addScalar("community_name", StandardBasicTypes.STRING)
				.addScalar("storied_build_id", StandardBasicTypes.STRING).addScalar("storied_build_name", StandardBasicTypes.STRING).addScalar("room_id", StandardBasicTypes.STRING)
				.addScalar("room_no", StandardBasicTypes.STRING).addScalar("owner_id", StandardBasicTypes.STRING).addScalar("owner_name", StandardBasicTypes.STRING)
				.addScalar("phone", StandardBasicTypes.STRING).list();
		System.out.println(sql);
		session.flush();
		session.close();
		return result;

	}

	public void queryOwnerByParam(Page<VPropertyOwner> page, Map<String, Object> parameter, String type) {

		String hql = " from " + VPropertyOwner.class.getName();
		String where = "";
		if (type != null && !type.isEmpty()) {
			where = " where ownerIdentity='0' ";
		} else {
			where = " where ownerIdentity='0' ";
		}
		String whereCase = " a " + where;
		String sqlCount = "select count(*) from " + VPropertyOwner.class.getName() + " b " + where;
		Map<String, Object> map = new HashMap<String, Object>();
		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			if (!search.equals("")) {
				whereCase += " and (a.ownerName like:ownerName" + " or a.phone like:phone)";
				sqlCount += " and (b.ownerName like:ownerName" + " or b.phone like:phone)";
				map.put("ownerName", "%" + search + "%");
				map.put("phone", "%" + search + "%");
			}
			String regStateSearch = parameter.get("regStateSearch") == null ? "" : parameter.get("regStateSearch").toString();
			if (!regStateSearch.equals("")) {
				if ("0".equals(regStateSearch)) {
					whereCase += " and  (password is null or LENGTH(password)=0) ";
					sqlCount += " and (password is null or LENGTH(password)=0) ";
				} else {
					whereCase += " and LENGTH(password)>0 ";
					sqlCount += " and LENGTH(password)>0 ";
				}
				// map.put("phone", "%"+search+"%");
			}
		}
		hql += whereCase;
		System.out.println("hql===" + hql);

		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public PropertyOwner getPropertyOwnerById(String ownerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ownerId", ownerId);

		List<PropertyOwner> lists = this.query("from " + PropertyOwner.class.getName() + " where ownerId='" + ownerId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public VPropertyOwner getVPropertyOwnerById(String ownerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ownerId", ownerId);

		List<VPropertyOwner> lists = this.query("from " + VPropertyOwner.class.getName() + " where ownerId='" + ownerId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public String addPropertyOwner(PropertyOwner owner) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(owner);
		session.flush();
		session.close();
		return owner.getOwnerId();
	}

	public void deletePropertyOwner(String ownerId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = ownerId.split(",");

		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
			PropertyOwner owner = getPropertyOwnerById(ids[i]);
			if (null != owner) {
				String ownerHkId = owner.getOwnerHkId();
				if (ownerHkId != null && !ownerHkId.isEmpty()) {
					deleteHkOwner(ownerHkId);
				}
				session.delete(owner);
			}
		}
		session.flush();
		session.close();
	}

	public void deleteHkOwner(String ownerHkId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("personIds", ownerHkId);
		HttpRequestUtil.sendPost(HikvisionUtil.buildPostUrl("/base/delPlatPersonInfo", map), map);
	}

	public boolean checkUniPhone(String fieldValue) {

		List<PropertyOwner> lists = this.query("from " + PropertyOwner.class.getName() + " where phone='" + fieldValue + "'");
		if (null != lists && lists.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public String getRoomId(String username) {
		String sql = "select t.room_id from t_property_owner t where t.phone='" + username + "'";
		Session session = this.getSessionFactory().openSession();
		String roomId = (String) session.createSQLQuery(sql).uniqueResult();
		session.flush();
		session.close();
		return roomId;
	}

	public List getOwnerInfo(String username) {
		String sql = "select c.community_id,c.community_name,c.belong_sb_id,c.storied_build_name,c.room_id,c.room_no,b.default_mark,a.owner_id,a.owner_name,"
				+ "a.phone,a.owner_head_img,e.user_nickname, a.owner_identity from " + "t_property_owner a LEFT JOIN t_house_owner b ON a.owner_id = b.owner_id"
				+ " LEFT JOIN t_house_property c ON b.room_id = c.room_id " + "LEFT JOIN t_bbs_user e ON a.owner_id = e.mapping_user_id where a.phone = '" + username + "'";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}

	public List<VPropertyOwner> getFamily(String ownerId) {

		List<VPropertyOwner> lists = this.query("from " + VPropertyOwner.class.getName() + " where parentId='" + ownerId + "'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public PropertyOwner queryOwnerByPhone(String phone) {
		String hql = "from " + PropertyOwner.class.getName() + " where phone = '" + phone + "'";
		return (PropertyOwner) this.query(hql).get(0);
	}

	public List queryOwnerByRoomId(Map<String, String> parameter) {
		String roomId = null;
		if (parameter != null) {
			roomId = parameter.get("roomId");
		}
		String sql = "SELECT * FROM v_area_build_house_owner_rela v WHERE v.room_id='" + roomId + "'";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}

	public void queryOwnerVisitor(Page<VPropertyOwner> page, Map<String, Object> parameter) {
		String hql = " from " + VPropertyOwner.class.getName();
		String where = " where ownerIdentity='3' ";
		String whereCase = " a " + where;
		String sqlCount = "select count(*) from " + VPropertyOwner.class.getName() + " b " + where;
		Map<String, Object> map = new HashMap<String, Object>();
		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			if (!search.equals("")) {
				whereCase += " and (a.ownerName like:ownerName" + " or a.phone like:phone)";
				sqlCount += " and (b.ownerName like:ownerName" + " or b.phone like:phone)";
				map.put("ownerName", "%" + search + "%");
				map.put("phone", "%" + search + "%");
			}
		}
		hql += whereCase;
		System.out.println("hql===" + hql);

		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public List getMySettingPage(Map<String, Object> map) {
		String roomIdString = "";
		JSONArray roomIds = (JSONArray) map.get("roomIds");
		String phone = (String) map.get("phone");
		for (int i = 0; i < roomIds.size(); i++) {
			if (i < roomIds.size() - 1) {
				roomIdString = roomIdString + "'" + (String) roomIds.get(i) + "',";
			} else {
				roomIdString = roomIdString + "'" + (String) roomIds.get(i) + "'";
			}

		}
		String sql = "SELECT COUNT(*) FROM t_r_maintain t WHERE t.event_type='1' and t.event_state IN ('0','1','2','3') AND t.in_call = '" + phone + "'" + " UNION ALL"
				+ " SELECT COUNT(*) FROM t_r_maintain t WHERE t.event_type='0' and t.event_state IN ('0','1','2','3') AND t.in_call = '" + phone + "'" + " UNION ALL"
				+ " SELECT COUNT(*) FROM t_housework_event t WHERE t.event_state IN ('0','1','2','3') AND t.call_phone = '" + phone + "'" + " UNION ALL"
				+ " SELECT COUNT(*) FROM t_charge_info t WHERE t.state = '03' AND t.room_id IN (" + roomIdString + ")";
		Session session = this.getSessionFactory().openSession();
		List<BigInteger> list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}

	public List<VOwnerRoomBasicInfo> querOwnerRoomBasicInfo(Map<String, Object> parameter) {
		String hql = " from " + VOwnerRoomBasicInfo.class.getName();
		String regState = parameter.get("regState") != null ? parameter.get("regState").toString() : "";
		String ownerIdentity = parameter.get("ownerIdentity") != null ? parameter.get("ownerIdentity").toString() : "";
		String ownerId = parameter.get("ownerId") != null ? parameter.get("ownerId").toString() : "";
		String whereCase = " Where 1=1 ";
		if(!"".equals(ownerIdentity)) {
			 whereCase += " and ownerIdentity=" + "'" + ownerIdentity + "'";
		}
		if(!"".equals(regState)) {
			whereCase += " and regState=" + "'" + regState + "'";
		}
		if(!"".equals(ownerId)) {
			whereCase += " and ownerId=" + "'" + ownerId + "'";
		}
	
		hql += whereCase;
		return this.query(hql);
	}
}
