package com.jdry.pms.lzmh.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VBuildingProperty;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.BuildUnitService;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.OwnerInfoService;
import com.jdry.pms.comm.util.LzmhUtil;

@Repository
@Transactional
public class LzmhDao extends HibernateDao {

	@Resource
	private AllAreaService allAreaService;

	@Resource
	private AreaPropertyService apService;

	@Resource
	private BuildingPropertyService bpService;

	@Resource
	private BuildUnitService buService;

	@Resource
	private HousePropertyService hpService;

	@Resource
	private OwnerInfoService osService;
	
	@Resource
	private HousePropertyDao hpDao;

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getLzmhUserInfo(Map<String, Object> parameter) {
		String sql = "select b.build_name as buildName,b.community_name as communityName,b.storied_build_name as storiedBuildName,b.room_no as roomNo," + "b.owner_name as ownerName,b.phone,b.room_id as roomId," + "a.user_id as userId,a.validity_start_date as validityStartDate,a.validity_end_date as validityEndDate,a.validity_start_time as validityStartTime,a.validity_end_time as validityEndTime,a.state as ownerState,a.identity_name as identityName" + " from t_lzmh_user a,v_house_vs_owner b where a.user_id = b.lz_id ";
		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	// 获取联掌同步返回的ID值
	public int getLzId(String jsonStr) {
		int lzId = 0;
		if ("".equals(jsonStr)) {
			lzId = -1;
		}
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		if (10000 == jsonObj.getIntValue("code")) {
			JSONObject jsonValue = (JSONObject) jsonObj.get("value");
			lzId = jsonValue.getIntValue("id");
		} else {
			lzId = -1;
		}
		return lzId;
	}

	@Transactional
	public String addCommunity(AllArea allArea) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uri = "/api/v1/community/create";

		body.put("name", allArea.getBuildName());
		body.put("area_id", 520000);
		body.put("city_id", 520100);
		body.put("region_id", 520112);
		body.put("property_id", 1739);
		body.put("lon", "106.831867");
		body.put("lat", "26.636766");
		strResult = LzmhUtil.zlmhPost(uri, body);

		int lzId = getLzId(strResult);
		if (lzId == -1) {
			resultMap.put("code", -10000);
			resultMap.put("message", "操作失败");
		} else {
			allArea.setLzId(lzId);
			allAreaService.saveOrUpdateEntity(allArea);
			resultMap.put("code", 10000);
			resultMap.put("message", "操作成功");
		}
		return JSON.toJSONString(resultMap);
	}

	public String modCommunity(AllArea allArea) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delCommunity(int lzId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addPartition(AreaProperty areaProp) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String buildId = areaProp.getBelongBuildId();
		AllArea allArea = allAreaService.getAllAreaById(buildId);

		String uri = "/api/v1/partition/create";
		body.put("community_id", allArea.getLzId());
		body.put("name", areaProp.getCommunityName());

		strResult = LzmhUtil.zlmhPost(uri, body);

		int lzId = getLzId(strResult);
		if (lzId == -1) {
			resultMap.put("code", -10000);
			resultMap.put("message", "操作失败");
		} else {
			areaProp.setLzId(lzId);
			apService.addAreaProperty(areaProp);
			resultMap.put("code", 10000);
			resultMap.put("message", "操作成功");
		}
		return JSON.toJSONString(resultMap);
	}

	public String modPartition(AreaProperty areaProp) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delPartition(int lzId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addTenement(BuildingProperty bp) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String commId = bp.getBelongCommId();
		AreaProperty areaProp = apService.getAreaPropertyById(commId);

		String uri = "/api/v1/tenement/create";
		body.put("partition_id", areaProp.getLzId());
		body.put("name", bp.getStoriedBuildName());

		strResult = LzmhUtil.zlmhPost(uri, body);

		int lzId = getLzId(strResult);
		if (lzId == -1) {
			resultMap.put("code", -10000);
			resultMap.put("message", "操作失败");
		} else {
			bp.setLzId(lzId);
			bpService.addBuildingProperty(bp);
			resultMap.put("code", 10000);
			resultMap.put("message", "操作成功");
		}
		return JSON.toJSONString(resultMap);
	}

	public String modTenement(BuildingProperty bp) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delTenement(int lzId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addUnit(BuildUnit bu) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String storiedBuildId = bu.getBelongSbId();
		VBuildingProperty vbp = bpService.getVBuildingPropertyById(storiedBuildId);

		String uri = "/api/v1/unit/create";
		body.put("tenement_id", vbp.getLzId());
		body.put("name", bu.getUnitName());

		strResult = LzmhUtil.zlmhPost(uri, body);

		int lzId = getLzId(strResult);
		if (lzId == -1) {
			resultMap.put("code", -10000);
			resultMap.put("message", "操作失败");
		} else {
			bu.setLzId(lzId);
			buService.saveOrUpdateEntity(bu);
			resultMap.put("code", 10000);
			resultMap.put("message", "操作成功");
		}
		return JSON.toJSONString(resultMap);
	}

	public String modUnit(BuildUnit bu) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		
		String uri = "/api/v1/unit/modify";
		body.put("id", bu.getLzId());
		body.put("name", bu.getUnitName());

		strResult = LzmhUtil.zlmhPost(uri, body);		
		return JSON.toJSONString(strResult);		
	}

	public String delUnit(int lzId) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		
		String uri = "/api/v1/unit/delete";
		body.put("id", lzId);

		strResult = LzmhUtil.zlmhPost(uri, body);		
		return JSON.toJSONString(strResult);
		
	}

	public String addRoom(HouseProperty hp) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uri = "/api/v1/room/create";
		String unitId = hp.getUnitId();
		BuildUnit bu = buService.findById(unitId);
		body.put("name", hp.getRoomNo().replaceAll("[^0-9]", ""));
		body.put("unit_id", bu.getLzId());

		strResult = LzmhUtil.zlmhPost(uri, body);

		int lzId = getLzId(strResult);
		if (lzId == -1) {
			resultMap.put("code", -10000);
			resultMap.put("message", "操作失败");
		} else {
			hp.setLzId(lzId);
			hpDao.updateHouseProperty(hp);
			//hpService.updateHouseProperty(hp);
			resultMap.put("code", 10000);
			resultMap.put("message", "操作成功");
		}
		return JSON.toJSONString(resultMap);
	}

	public String modRoom(HouseProperty hp) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		String uri = "/api/v1/room/modify";
		body.put("name", hp.getRoomNo().replaceAll("[^0-9]", ""));
		body.put("id", hp.getLzId());

		strResult = LzmhUtil.zlmhPost(uri, body);

		return strResult;
	}

	public String delRoom(int lzId) {
		// TODO Auto-generated method stub
		Map<String, Object> body = new HashMap<String, Object>();
		String strResult = "";
		String uri = "/api/v1/room/delete";
		body.put("id", lzId);// (手机号码，必填): string,
		strResult = LzmhUtil.zlmhPost(uri, body);
		return strResult;
	}

	public String addOwnerInfo(HouseOwner ho) {
		// TODO Auto-generated method stub
		String strResult = "";
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uri = "/api/v1/resident/add";
		String roomId = ho.getRoomId();
		String ownerId = ho.getOwnerId();

		HouseProperty hp = hpService.getHousePropertyById(roomId);
		PropertyOwner po = osService.getPropertyOwnerById(ownerId);
		String ownerIdentity = po.getOwnerIdentity();
		if ("0".equals(ownerIdentity)) {
			body.put("add_type", 1);// (添加类型 1：户主 2：住户，必填): integer
		} else {
			body.put("add_type", 2);// (添加类型 1：户主 2：住户，必填): integer
		}
		body.put("mobile_phone", po.getPhone());// (手机号码，必填): string,
		body.put("room_id", hp.getLzId());// (房间Id，必填): integer,
		body.put("cycle_type", 1);// (有效周期类型，1.永久2.手动设置，必填): integer,
		body.put("time_type", 1);// (有效时段类型,1：24H 2:手动设置，必填): integer,
		body.put("alias", po.getPhone()); // (住户的别名，非必填): string

		strResult = LzmhUtil.zlmhPost(uri, body);

		int lzId = getLzId(strResult);
		if (lzId == -1) {
			resultMap.put("code", -10000);
			resultMap.put("message", "操作失败");
		} else {
			ho.setLzId(lzId);
			hpService.addHouseOwner(ho);
			resultMap.put("code", 10000);
			resultMap.put("message", "操作成功");
		}
		System.out.print(strResult);
		return JSON.toJSONString(resultMap);
	}

	public String delOwnerInfo(int resident_id) {
		// TODO Auto-generated method stub
		Map<String, Object> body = new HashMap<String, Object>();
		String strResult = "";
		String uri = "/api/v1/resident/delete";
		body.put("resident_id", resident_id);// (手机号码，必填): string,
		strResult = LzmhUtil.zlmhPost(uri, body);
		return strResult;
	}

}