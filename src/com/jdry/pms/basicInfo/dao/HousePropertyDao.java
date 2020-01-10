package com.jdry.pms.basicInfo.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.DecorateInfo;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.ParkingArea;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;
import com.jdry.pms.basicInfo.pojo.VHouseProperty;
import com.jdry.pms.basicInfo.pojo.VRoomCharge;
import com.jdry.pms.basicInfo.pojo.VRoomChargeTypeRela;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;

@Repository
@Transactional
public class HousePropertyDao extends HibernateDao {

	@Resource
	AreaPropertyDao areaProperty;

	@Resource
	OwnerInfoDao ownerInfoDao;

	public void query(Page<HouseProperty> page, Map<String, Object> parameter, Criteria criteria) {

		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}

		String roomNo = parameter.get("roomNo") != null ? parameter.get("roomNo").toString() : "";
		String belongSbId = parameter.get("belongSbId") != null ? parameter.get("belongSbId").toString() : "";
		String houseType = parameter.get("houseType") != null ? parameter.get("houseType").toString() : "";
		String buildArea = parameter.get("buildArea") != null ? parameter.get("buildArea").toString() : "";
		String roomType = parameter.get("roomType") != null ? parameter.get("roomType").toString() : "";
		String roomState = parameter.get("roomState") != null ? parameter.get("roomState").toString() : "";
		String roomId = parameter.get("roomId") != null ? parameter.get("roomId").toString() : "";
		String buildId = parameter.get("buildId") != null ? parameter.get("buildId").toString() : "";
		String areaId = parameter.get("areaId") != null ? parameter.get("areaId").toString() : "";

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + HouseProperty.class.getName() + " du where 1=1";
		String sqlCount = "select count(*) from " + HouseProperty.class.getName() + " du where 1=1";
		if (roomNo != null && !"".equals(roomNo)) {
			map.put("roomNo", "%" + roomNo + "%");
			sql += " and du.roomNo like:roomNo";
			sqlCount += " and du.roomNo like:roomNo";
		}
		if (belongSbId != null && !"".equals(belongSbId)) {
			map.put("belongSbId", belongSbId);
			sql += " and du.belongSbId =:belongSbId";
			sqlCount += " and du.belongSbId =:belongSbId";
		} else {

			if (areaId != null && !"".equals(areaId)) {
				List<String> a = new ArrayList<String>();
				Map<String, String> mapArea = new HashMap<String, String>();
				mapArea.put("areaId", areaId);
				Collection<BuildingProperty> builds = this.queryBuildingPropertyByJdbc(mapArea);
				for (BuildingProperty build : builds) {
					a.add(build.getStoriedBuildId());
				}
				map.put("a", a);
				sql += " and du.belongSbId in (:a)";
				sqlCount += " and du.belongSbId in (:a)";
			} else {
				if (buildId != null && !"".equals(buildId)) {

					List<String> b = new ArrayList<String>();
					Map<String, String> mapArea = new HashMap<String, String>();
					mapArea.put("buildId", buildId);
					Collection<BuildingProperty> builds = this.queryBuildingPropertyByJdbc(mapArea);
					for (BuildingProperty build : builds) {
						b.add(build.getStoriedBuildId());
					}

					map.put("b", b);
					sql += " and du.belongSbId in (:b)";
					sqlCount += " and du.belongSbId in (:b)";
				}
			}
		}

		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.roomId =:roomId";
			sqlCount += " and du.roomId =:roomId";
		}
		if (houseType != null && !"".equals(houseType)) {
			map.put("houseType", "%" + houseType + "%");
			sql += " and du.houseType like:houseType";
			sqlCount += " and du.houseType like:houseType";
		}
		if (buildArea != null && !"".equals(buildArea)) {
			map.put("buildArea", buildArea);
			sql += " and du.buildArea =:buildArea";
			sqlCount += " and du.buildArea =:buildArea";
		}
		if (roomType != null && !"".equals(roomType)) {
			map.put("roomType", roomType);
			sql += " and du.roomType =:roomType";
			sqlCount += " and du.roomType =:roomType";
		}
		if (roomState != null && !"".equals(roomState)) {
			map.put("roomState", roomState);
			sql += " and du.roomState =:roomState";
			sqlCount += " and du.roomState =:roomState";
		}

		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void saveHouseProperty(Collection<HouseProperty> houses) {
		Session session = this.getSessionFactory().openSession();
		try {
			for (HouseProperty house : houses) {
				EntityState state = EntityUtils.getState(house);
				if (state.equals(EntityState.NEW)) {
					session.save(house);
				} else if (state.equals(EntityState.MODIFIED)) {
					session.update(house);
				} else if (state.equals(EntityState.DELETED)) {
					session.delete(house);
				}
			}
		} finally {
			session.flush();
			session.close();
		}

	}

	public List<BuildingProperty> getBuilding(Map<String, Object> parameter) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		String storiedBuildId = parameter.get("id") != null ? parameter.get("id").toString() : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + BuildingProperty.class.getName() + " du where 1=1";
		if (storiedBuildId != null && !"".equals(storiedBuildId)) {
			map.put("storiedBuildId", storiedBuildId);
			sql += " and du.storiedBuildId =:storiedBuildId ";
		}
		return this.query(sql, map);
	}

	public AllArea queryAllAreaById(Map<String, String> param) {
		Session session = this.getSessionFactory().openSession();

		String roomId = param.get("roomId") != null ? param.get("roomId") : "";
		String carportId = param.get("carportId") != null ? param.get("carportId") : "";
		String parkId = param.get("parkId") != null ? param.get("parkId") : "";

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select * from  t_all_area b where b.build_id in ");
		sqlStr.append(" (select a.belong_build_id from t_area_property a where a.community_id in ");
		if (roomId != null && !"".equals(roomId)) {
			sqlStr.append(" (select p.belong_comm_id from t_building_property p where p.storied_build_id in ");
			sqlStr.append(" (select h.belong_sb_id from t_house_property h where h.room_id = '" + roomId + "'))) ");
		}
		if (carportId != null && !"".equals(carportId)) {
			sqlStr.append(" (select h.belong_com_id from t_parking h where h.carport_id = '" + carportId + "')) ");
		}
		if (parkId != null && !"".equals(parkId)) {
			sqlStr.append(" (select h.belong_com_id from t_parking_area h where h.park_id = '" + parkId + "')) ");
		}
		AllArea area = (AllArea) session.createSQLQuery(sqlStr.toString()).addEntity(AllArea.class).uniqueResult();
		session.close();
		return area != null ? area : new AllArea();
	}

	public AreaProperty queryAreaPropertyById(Map<String, String> param) {
		Session session = this.getSessionFactory().openSession();

		String roomId = param.get("roomId") != null ? param.get("roomId") : "";
		String carportId = param.get("carportId") != null ? param.get("carportId") : "";
		String parkId = param.get("parkId") != null ? param.get("parkId") : "";

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select * from t_area_property a where a.community_id in ");
		if (roomId != null && !"".equals(roomId)) {
			sqlStr.append(" (select p.belong_comm_id from t_building_property p where p.storied_build_id in ");
			sqlStr.append(" (select h.belong_sb_id from t_house_property h where h.room_id = '" + roomId + "')) ");
		}
		if (carportId != null && !"".equals(carportId)) {
			sqlStr.append(" (select h.belong_com_id from t_parking h where h.carport_id = '" + carportId + "') ");
		}
		if (parkId != null && !"".equals(parkId)) {
			sqlStr.append(" (select h.belong_com_id from t_parking_area h where h.park_id = '" + parkId + "') ");
		}
		AreaProperty pro = (AreaProperty) session.createSQLQuery(sqlStr.toString()).addEntity(AreaProperty.class)
				.uniqueResult();
		session.close();
		return pro != null ? pro : new AreaProperty();
	}

	public BuildingProperty queryBuildingPropertyById(Map<String, String> param) {
		Session session = this.getSessionFactory().openSession();

		String roomId = param.get("roomId") != null ? param.get("roomId") : "";

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select * from t_building_property p where p.storied_build_id in ");
		if (roomId != null && !"".equals(roomId)) {
			sqlStr.append(" (select h.belong_sb_id from t_house_property h where h.room_id = '" + roomId + "') ");
		}
		BuildingProperty build = (BuildingProperty) session.createSQLQuery(sqlStr.toString())
				.addEntity(BuildingProperty.class).uniqueResult();
		session.close();
		return build != null ? build : new BuildingProperty();
	}

	@SuppressWarnings("unchecked")
	public Collection<BuildingProperty> queryBuildingPropertyByJdbc(Map<String, String> param) {
		Session session = this.getSessionFactory().openSession();

		String buildId = param.get("buildId") != null ? param.get("buildId") : "";
		String areaId = param.get("areaId") != null ? param.get("areaId") : "";

		StringBuffer sqlStr = new StringBuffer();
		if (buildId != null && !"".equals(buildId)) {
			sqlStr.append(" select * from t_building_property p where p.belong_comm_id in ");
			sqlStr.append(
					" (select h.community_id from t_area_property h where h.belong_build_id = '" + buildId + "')");
		}
		if (areaId != null && !"".equals(areaId)) {
			sqlStr.append(" select * from t_building_property p where p.belong_comm_id ='" + areaId + "' ");
		}
		Collection<BuildingProperty> buildingPropertys = session.createSQLQuery(sqlStr.toString())
				.addEntity(BuildingProperty.class).list();
		session.close();
		return buildingPropertys;
	}

	@SuppressWarnings("unchecked")
	public Collection<HouseProperty> queryHousePropertyByJdbc(Map<String, String> param) {
		Session session = this.getSessionFactory().openSession();

		String areaId = param.get("areaId") != null ? param.get("areaId") : "";

		StringBuffer sqlStr = new StringBuffer();
		if (areaId != null && !"".equals(areaId)) {
			sqlStr.append(
					" select * from t_house_property h where h.belong_sb_id in (select p.storied_build_id from t_building_property p where p.belong_comm_id ='"
							+ areaId + "' ) ");
		}
		Collection<HouseProperty> houses = session.createSQLQuery(sqlStr.toString()).addEntity(HouseProperty.class)
				.list();
		session.close();
		return houses;
	}

	public ParkingArea queryParkingAreaById(Map<String, String> param) {
		Session session = this.getSessionFactory().openSession();

		String carportId = param.get("carportId") != null ? param.get("carportId") : "";

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select * from t_parking_area a where a.park_id in ");
		if (carportId != null && !"".equals(carportId)) {
			sqlStr.append(" (select h.belong_park_no from t_parking h where h.carport_id = '" + carportId + "') ");
		}
		ParkingArea park = (ParkingArea) session.createSQLQuery(sqlStr.toString()).addEntity(ParkingArea.class)
				.uniqueResult();
		session.close();
		return park != null ? park : new ParkingArea();
	}

	public Collection<HouseProperty> queryHousePropertyByParam(Map<String, Object> parameter) {
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}

		String belongSbId = parameter.get("storiedBuildId") != null ? parameter.get("storiedBuildId").toString() : "";
		String roomId = parameter.get("roomId") != null ? parameter.get("roomId").toString() : "";
		String keyword = parameter.get("keyword") != null ? parameter.get("keyword").toString() : "";
		String roomState = parameter.get("roomState") != null ? parameter.get("roomState").toString() : "";
		String unitId = parameter.get("unitId") != null ? parameter.get("unitId").toString() : "";

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + HouseProperty.class.getName() + " du where 1=1";
		if (belongSbId != null && !"".equals(belongSbId)) {
			map.put("belongSbId", belongSbId);
			sql += " and du.belongSbId =:belongSbId";
		}
		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.roomId =:roomId";
		}
		if (keyword != null && !"".equals(keyword)) {
			map.put("roomNo", "%" + keyword + "%");
			sql += " and du.roomNo like:roomNo";
		}
		if (roomState != null && !"".equals(roomState)) {
			map.put("roomState", roomState);
			sql += " and du.roomState =:roomState";
		}
		if (unitId != null && !"".equals(unitId)) {
			map.put("unitId", roomState);
			sql += " and du.unitId =:unitId";
		}

		return this.query(sql, map);
	}

	public Collection<HouseProperty> queryHousePropertyByParent(String id) {

		String belongSbId = id != null ? id : "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + HouseProperty.class.getName() + " du where 1=1";
		if (belongSbId != null && !"".equals(belongSbId)) {
			map.put("belongSbId", belongSbId);
			sql += " and du.belongSbId =:belongSbId";
		}

		return this.query(sql, map);
	}

	public void queryHousePropertyByParam(Page<VHouseProperty> page, Map<String, Object> parameter, String type) {

		String hql = " from " + VHouseProperty.class.getName();
		String where = "";
		if (type != null && !type.isEmpty()) {
			where = " where state='" + type + "' ";
		} else {
			where = " where 1=1 ";
		}
		String whereCase = " a " + where;
		String sqlCount = "select count(*) from " + VHouseProperty.class.getName() + " b " + where;
		Map<String, Object> map = new HashMap<String, Object>();
		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			String communityNameSearch = parameter.get("communityNameSearch") == null ? ""
					: parameter.get("communityNameSearch").toString();
			String storiedBuildNameSearch = parameter.get("storiedBuildNameSearch") == null ? ""
					: parameter.get("storiedBuildNameSearch").toString();
			String roomTypeSearch = parameter.get("roomTypeSearch") == null ? ""
					: parameter.get("roomTypeSearch").toString();
			String roomStateSearch = parameter.get("roomStateSearch") == null ? ""
					: parameter.get("roomStateSearch").toString();
			if (!search.equals("")) {
				whereCase += " and (a.roomNo like:roomNo " + " or a.build_name like:build_name"
						+ " or a.community_name like:community_name" + " or a.room_state_name like:room_state_name"
						+ " or a.room_type_name like:room_type_name"
						+ " or a.charge_object_name like:charge_object_name"
						+ " or a.storied_build_name like:storied_build_name)";
				sqlCount += " and (b.roomNo like:roomNo " + " or b.build_name like:build_name"
						+ " or b.community_name like:community_name" + " or b.room_state_name like:room_state_name"
						+ " or b.room_type_name like:room_type_name"
						+ " or b.charge_object_name like:charge_object_name"
						+ " or b.storied_build_name like:storied_build_name)";
				map.put("roomNo", "%" + search + "%");
				map.put("build_name", "%" + search + "%");
				map.put("community_name", "%" + search + "%");
				map.put("storied_build_name", "%" + search + "%");
				map.put("room_state_name", "%" + search + "%");
				map.put("room_type_name", "%" + search + "%");
				map.put("charge_object_name", "%" + search + "%");
			}
			if (communityNameSearch != null && !"".equals(communityNameSearch)) {
				whereCase += " and community_id =:community_id ";
				sqlCount += " and community_id =:community_id ";
				map.put("community_id", communityNameSearch);
			}
			if (storiedBuildNameSearch != null && !"".equals(storiedBuildNameSearch)) {
				whereCase += " and belongSbId =:belongSbId ";
				sqlCount += " and belongSbId =:belongSbId ";
				map.put("belongSbId", storiedBuildNameSearch);
			}
			if (roomTypeSearch != null && !"".equals(roomTypeSearch)) {
				whereCase += " and roomType =:roomType ";
				sqlCount += " and roomType =:roomType ";
				map.put("roomType", roomTypeSearch);
			}
			if (roomStateSearch != null && !"".equals(roomStateSearch)) {
				whereCase += " and roomState =:roomState ";
				sqlCount += " and roomState =:roomState ";
				map.put("roomState", roomStateSearch);
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

	public void queryHouseOwnerByParam(Page<VHouseOwner> page, Map<String, Object> parameter, String type) {

		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from " + VHouseOwner.class.getName();
		String where = "";
		String whereCase = " a " + where;
		if (type != null && !type.isEmpty()) {
			map.put("roomState", type);
			whereCase += " where a.roomState>=:roomState ";
			where = " where b.roomState>=:roomState ";
		} else {
			where = " where 1=1 ";
			whereCase = " a where 1=1 ";
		}
		String sqlCount = "select count(*) from " + VHouseOwner.class.getName() + " b " + where;
		String order = parameter.get("order") == null ? "" : parameter.get("order").toString();
		if (parameter != null) {
			String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
			String decorateStartDate = parameter.get("decorateStartDate") == null ? ""
					: parameter.get("decorateStartDate").toString();
			if (decorateStartDate.equals("notNull")) {
				whereCase += " and a.decorateStartDate is not null and a.decorateStartDate !='' ";
				sqlCount += " and b.decorateStartDate is not null and b.decorateStartDate !='' ";
			}
			if (!search.equals("")) {
				whereCase += " and (a.roomNo like:roomNo " + " or a.build_name like:build_name"
						+ " or a.community_name like:community_name" + " or a.ownerName like:ownerName"
						+ " or a.storied_build_name like:storied_build_name) ";
				sqlCount += " and (b.roomNo like:roomNo " + " or b.room_state_name like:roomState "
						+ " or b.build_name like:build_name" + " or b.community_name like:community_name"
						+ " or b.ownerName like:ownerName" + " or b.storied_build_name like:storied_build_name) ";
				map.put("roomNo", "%" + search + "%");
				map.put("build_name", "%" + search + "%");
				map.put("community_name", "%" + search + "%");
				map.put("ownerName", "%" + search + "%");
				map.put("storied_build_name", "%" + search + "%");
			}
		}
		hql += whereCase;
		if (!order.isEmpty()) {
			hql += " order by a." + order + " desc";
		}
		System.out.println("hql===" + hql);

		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public VHouseProperty getVHousePropertyById(String roomId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomId", roomId);

		List<VHouseProperty> lists = this
				.query("from " + VHouseProperty.class.getName() + " where roomId='" + roomId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public List<VHouseOwner> getVHouseOwnerByOwnerId(String ownerId) {
		String hql = "from VHouseOwner where ownerId = '" + ownerId + "'";
		List<VHouseOwner> list = this.query(hql);
		return list;
	}

	public HouseProperty getHousePropertyById(String roomId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomId", roomId);

		List<HouseProperty> lists = this
				.query("from " + HouseProperty.class.getName() + " where roomId='" + roomId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addHouseProperty(HouseProperty houseProperty) {
		Session session = this.getSessionFactory().openSession();
		session.save(houseProperty);
		session.flush();
		session.close();
	}
	
	public void updateHouseProperty(HouseProperty houseProperty) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(houseProperty);
		session.flush();
		session.close();
	}

	public void deleteHouseProperty(String roomId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = roomId.split(",");

		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
			HouseProperty houseProperty = getHousePropertyById(ids[i]);
			if (null != houseProperty) {
				session.delete(houseProperty);
			}
		}
		session.flush();
		session.close();
	}

	// 交房登记
	public String giveHouse(Map<String, Object> param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String roomId = null == param.get("roomId") ? "" : param.get("roomId").toString();
		String ownerId = null == param.get("ownerId") ? "" : param.get("ownerId").toString();
		String makeRoomDate = null == param.get("makeRoomDate") ? "" : param.get("makeRoomDate").toString();

		PropertyOwner owner = ownerInfoDao.getPropertyOwnerById(ownerId);
		HouseProperty house = getHousePropertyById(roomId);
		if (owner != null && house != null) {
			HouseOwner ho = new HouseOwner();
			ho.setOwnerId(ownerId);
			ho.setRoomId(roomId);
			ho.setDefaultMark("0");
			ho.setBandingMark("0");
			house.setRoomState("1");
			try {
				house.setMakeRoomDate(sdf.parse(makeRoomDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Session session = this.getSessionFactory().openSession();
			session.save(ho);
			session.saveOrUpdate(owner);
			session.saveOrUpdate(house);
			session.flush();
			session.close();
			return "success";
		}
		return "failure";
	}

	@SuppressWarnings("unchecked")
	public List<VHouseOwner> queryVHouseOwnerByParam(Map<String, Object> param) {

		if (param == null) {
			param = new HashMap<String, Object>();
		}
		String keyword = param.get("keyword") != null ? param.get("keyword").toString() : "";
		String roomState = param.get("roomState") != null ? param.get("roomState").toString() : "";
		String decorateStartDate = param.get("decorateStartDate") != null ? param.get("decorateStartDate").toString()
				: "";

		String hql = " from VHouseOwner where 1=1 ";
		if (!"".equals(roomState)) {
			hql += " and roomState = '" + roomState + "' ";
		}
		if (!"".equals(decorateStartDate)) {
			hql += " and (decorateStartDate is null or decorateStartDate = '') ";
		}
		if (!"".equals(keyword)) {
			hql += " and ( roomNo like '%" + keyword + "%'";
			hql += " or ownerName like '%" + keyword + "%'  ";
			hql += " or storied_build_name like '%" + keyword + "%'  ";
			hql += " or community_name like '%" + keyword + "%' ";
			hql += " or build_name like '%" + keyword + "%' ) ";
		}
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		query.setMaxResults(15);
		List<VHouseOwner> list = query.list();

		session.flush();
		session.close();
		return list;
	}

	public List<ChargeTypeSettingViewEntity> queryChargeTypeByParam(Map<String, Object> param) {
		if (param == null) {
			param = new HashMap<String, Object>();
		}

		String charge_type_id = param.get("charge_type_id") != null ? param.get("charge_type_id").toString() : "";
		String charge_way = param.get("charge_way") != null ? param.get("charge_way").toString() : "";
		String charge_type_name = param.get("charge_type_name") != null ? param.get("charge_type_name").toString() : "";

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + ChargeTypeSettingViewEntity.class.getName() + " du where 1=1";
		if (charge_type_id != null && !"".equals(charge_type_id)) {
			map.put("charge_type_id", charge_type_id);
			sql += " and du.charge_type_id =:charge_type_id";
		}
		if (charge_way != null && !"".equals(charge_way)) {
			map.put("charge_way", charge_way);
			sql += " and du.charge_way =:charge_way";
		}
		if (charge_type_name != null && !"".equals(charge_type_name)) {
			map.put("charge_type_name", "%" + charge_type_name + "%");
			sql += " and du.charge_type_name like:charge_type_name";
		}
		return this.query(sql, map);
	}

	public List<VRoomChargeTypeRela> getVRoomChargeTypeRelaById(String roomId) {

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + VRoomChargeTypeRela.class.getName() + " du where 1=1";
		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.room_id =:roomId";
		} else {
			map.put("roomId", "");
			sql += " and du.room_id =:roomId";
		}

		return this.query(sql, map);
	}

	public List<ChargeInfoEntity> getChargeInfoEntityById(String roomId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + ChargeInfoEntity.class.getName() + " du where 1=1";
		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			map.put("data_from", "02");
			map.put("charge_type_name", "物业费");

			sql += " and du.room_id =:roomId";
			sql += " and du.data_from =:data_from";
			sql += " and du.charge_type_name =:charge_type_name";
		} else {
			map.put("roomId", "");
			sql += " and du.room_id =:roomId";
		}

		return this.query(sql, map);
	}

	public List<VRoomCharge> getVRoomChargeById(Map<String, Object> param) {
		String roomId = param.get("roomId") != null ? param.get("roomId").toString() : "";
		String end_time = param.get("end_time") != null ? param.get("end_time").toString() : "";
		String charge_type_no = param.get("charge_type_no") != null ? param.get("charge_type_no").toString() : "";
		String begin_time = param.get("begin_time") != null ? param.get("begin_time").toString() : "";

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + VRoomCharge.class.getName() + " du where 1=1";
		sql += " and du.charge_type_name like '%装修%' and du.charge_way = '临时性' ";
		if (roomId != null && !"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.room_id =:roomId";
		} else {
			map.put("roomId", "没有房间号");
			sql += " and du.room_id =:roomId";
		}

		if (end_time != null && !"".equals(end_time)) {
			sql += " and du.end_time is not null ";
		}

		if (charge_type_no != null && !"".equals(charge_type_no)) {
			map.put("charge_type_no", charge_type_no);
			sql += " and du.charge_type_no =:charge_type_no ";
		}
		if (begin_time != null && !"".equals(begin_time)) {
			map.put("begin_time", begin_time);
			sql += " and date_format(du.begin_time,'%Y-%m-%d') =:begin_time ";
		}

		return this.query(sql, map);
	}

	public boolean isExistCharge(Map<String, Object> param) {

		String roomId = param.get("roomId") != null ? param.get("roomId").toString() : "";
		String charge_type_no = param.get("charge_type_no") != null ? param.get("charge_type_no").toString() : "";
		String begin_time = param.get("begin_time") != null ? param.get("begin_time").toString() : "";
		String sql = "from " + ChargeInfoEntity.class.getName() + " du where du.room_id = '" + roomId
				+ "' and du.charge_type_no = '" + charge_type_no + "'"
				+ " and date_format(du.begin_time,'%Y-%m-%d') = '" + begin_time + "'";
		List<ChargeInfoEntity> vroom = this.query(sql);
		if (vroom.size() > 0) {
			return true;
		}
		return false;
	}

	public HouseProperty getHousePropertyByNo(String roomNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomNo", roomNo);

		List<HouseProperty> lists = this
				.query("from " + HouseProperty.class.getName() + " where roomNo='" + roomNo + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public List<ChargeSerialViewEntity> getChargeSerial(Map<String, Object> param) {

		String roomId = param.get("roomId") != null ? param.get("roomId").toString() : "";
		String ownerId = param.get("ownerId") != null ? param.get("ownerId").toString() : "";

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from " + ChargeSerialViewEntity.class.getName() + " du where 1=1";
		sql += " and du.charge_type_name = '装修押金' ";
		if (!"".equals(roomId)) {
			map.put("roomId", roomId);
			sql += " and du.room_id =:roomId";
		}

		if (!"".equals(ownerId)) {
			map.put("ownerId", ownerId);
			sql += " and du.owner_id =:ownerId ";
		}

		return this.query(sql, map);
	}

	@SuppressWarnings("rawtypes")
	public List queryHouseDetail(String keyword) {
		String sql = "SELECT h.community_name,h.storied_build_name,h.room_no,h.room_type,h.build_area,h.within_area,h.room_state,h.receive_room_date,h.decorate_start_date,h.decorate_end_date,"
				+ "h.build_area*r.charge_price price,s.paid_date, s.paid_amount,i.*,r.amount,o.owner_name,o.phone,o.tel_phone,o.card_id,o.birth_date,o.car_id,o.owner_type,o.owner_identity"
				+ " FROM t_house_property h LEFT JOIN "
				+ " (SELECT t.room_id, t.paid_date, sum(t.paid_amount) paid_amount FROM t_charge_info t,"
				+ " (SELECT c.room_id, max(c.paid_date) paid_date FROM t_charge_info c WHERE c.paid_date is NOT NULL GROUP BY c.room_id) a "
				+ " WHERE t.room_id=a.room_id AND t.paid_date=a.paid_date GROUP BY t.room_id) s ON h.room_id=s.room_id "
				+ " LEFT JOIN"
				+ " (SELECT c.room_id, sum(c.receive_amount) receive_amount_total, sum(c.paid_amount) paid_amount_total, sum(c.arrearage_amount) arrearage_amount_total"
				+ " FROM t_charge_info c GROUP BY c.room_id) i ON h.room_id=i.room_id" + " LEFT JOIN"
				+ " (SELECT r.*, s.charge_price from t_charge_type_room_rela r, t_charge_type_setting s"
				+ " WHERE s.charge_type_id=r.charge_type_id AND r.charge_type_no in('0001','0002','0011')) r ON h.room_id=r.room_id"
				+ " LEFT JOIN"
				+ " (SELECT o.*,p.room_id FROM t_property_owner o LEFT JOIN t_house_owner p ON o.owner_id=p.owner_id WHERE o.owner_identity=0 GROUP BY p.room_id) o on h.room_id=o.room_id"
				+ " WHERE o.owner_name LIKE '%" + keyword + "%' OR o.phone LIKE '%" + keyword + "%' limit 15";
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return result;
	}

	public void addHouseOwner(HouseOwner ho) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(ho);
		session.flush();
		session.close();
	}

	public List<HouseOwner> queryHouseOwner(Map<String, Object> parameter) {
		String ownerId =parameter.get("ownerId") != null ? parameter.get("ownerId").toString() : "";
		String roomId = parameter.get("roomId") != null ? parameter.get("roomId").toString() : "";
		String whereClause = " where 1=1";
		if (parameter != null) {
			if(!"".equals(ownerId)) {
				whereClause = whereClause + " and ownerId='" + ownerId + "'";
			}
			if(!"".equals(roomId)) {
				whereClause = whereClause + " and roomId='" + roomId + "'";
			}
			
		}
		String hql = "from HouseOwner" + whereClause;
		List<HouseOwner> list = this.query(hql);
		return list;
	}

	public String deleteGiveHouse(String roomId) {
		Session session = this.getSessionFactory().openSession();
		try {
			String[] arr = roomId.split(",");
			HouseProperty house = getHousePropertyById(arr[0]);
			HouseOwner houseOwner = getHouseOwnerById(arr[0], arr[1]);
			if (houseOwner != null && house != null) {
				house.setRoomState("0");
				house.setMakeRoomDate(null);
				house.setUpdateTime(new Date());
				houseOwner.setIsDel("1");
				houseOwner.setUpdateTime(new Date());
				session.saveOrUpdate(houseOwner);
				session.saveOrUpdate(house);
				return "success";
			} else {
				return "failure";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		} finally {
			session.flush();
			session.close();
		}
	}

	public HouseOwner getHouseOwnerById(String roomId, String ownerId) {
		List<HouseOwner> lists = this.query(
				"from " + HouseOwner.class.getName() + " where roomId='" + roomId + "' and ownerId ='" + ownerId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void delDecorateInfo(String roomId) {
		Session session = this.getSessionFactory().getCurrentSession();
		HouseProperty house = getHousePropertyById(roomId);
		DecorateInfo di = getDecorateInfoById(roomId);
		if (house != null) {
			house.setDecorateStartDate(null);
			house.setDecorateEndDate(null);
			house.setDecoratePlanDate(null);

			if ("3".equals(house.getRoomState())) {
				house.setRoomState("2");
			}
			session.saveOrUpdate(house);
			if (di != null) {
				delDecorateDetail(di);
			}
		}

	}

	public void updateOrSaveDecorateDetail(DecorateInfo di) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(di);
		session.flush();
		session.close();
	}

	public void delDecorateDetail(DecorateInfo di) {
		Session session = this.getSessionFactory().openSession();
		session.delete(di);
		session.flush();
		session.close();
	}

	public DecorateInfo getDecorateInfoById(String roomId) {
		String hql = "from DecorateInfo where roomId = '" + roomId + "'";
		List<DecorateInfo> lists = this.query(hql);
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public boolean checkRoomNo(String unitId, String roomNo,String roomId) {
		String hql = "from HouseProperty where unitId='" + unitId + "' and roomNo='" + roomNo + "' "
				+ " and roomId!='" + roomId + "' ";
		List<HouseProperty> lists = this.query(hql);
		if (lists != null && lists.size() > 0) {//有记录表示房号校验不通过
			return false;
		}
		return true;

	}
	
	//更改关联房产用户信息
	public List<Map<String, Object>> changeOwnerInfo(String roomId,String operId,String operType){
		SQLQuery query = (SQLQuery) this.getSession().createSQLQuery("{CALL pro_change_owner_phone(?,?,?)}").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		query.setString(0,roomId);
		query.setString(1,operId);
		query.setString(2,operType);
		List<Map<String,Object>> list =  new ArrayList<Map<String,Object>>();
				list = query.list();
		return list;
	}
}
