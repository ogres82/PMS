package com.jdry.pms.basicInfo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.DecorateInfo;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;
import com.jdry.pms.basicInfo.pojo.VHouseProperty;
import com.jdry.pms.basicInfo.pojo.VRoomCharge;
import com.jdry.pms.basicInfo.pojo.VRoomChargeTypeRela;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;

@Repository
public interface HousePropertyService {

	public void query(Page<HouseProperty> page, Map<String, Object> parameter, Criteria criteria);

	public void saveHouseProperty(Collection<HouseProperty> houses);

	public List<BuildingProperty> getBuilding(Map<String, Object> parameter);

	public Collection<HouseProperty> queryHousePropertyByParam(Map<String, Object> parameter);

	public Collection<HouseProperty> queryHousePropertyByParent(String id);

	public Collection<HouseProperty> queryHousePropertyByJdbc(Map<String, String> parameter);

	public void queryHousePropertyByParam(Page<VHouseProperty> page, Map<String, Object> parameter, String type);

	public void queryHouseOwnerByParam(Page<VHouseOwner> page, Map<String, Object> parameter, String type);

	public VHouseProperty getVHousePropertyById(String roomId);

	public void addHouseProperty(HouseProperty houseProperty);

	public boolean checkRoomNo(String unitId, String roomNo,String roomId);

	public void deleteHouseProperty(String roomId);

	public String giveHouse(Map<String, Object> param);

	public List<VHouseOwner> queryVHouseOwnerByParam(Map<String, Object> param);

	public List<ChargeTypeSettingViewEntity> queryChargeTypeByParam(Map<String, Object> param);

	public HouseProperty getHousePropertyById(String roomId);

	public List<VRoomChargeTypeRela> getVRoomChargeTypeRelaById(String roomId);

	public List<ChargeInfoEntity> getChargeInfoEntityById(String roomId);

	public List<VRoomCharge> getVRoomChargeById(Map<String, Object> param);

	public boolean isExistCharge(Map<String, Object> param);

	public HouseProperty getHousePropertyByNo(String roomNo);

	public List<ChargeSerialViewEntity> getChargeSerial(Map<String, Object> param);

	public List<?> queryHouseDetail(String keyword);

	public List<VHouseOwner> getVHouseOwnerByOwnerId(String ownerId);

	public void addHouseOwner(HouseOwner ho);

	public List<HouseOwner> queryHouseOwner(Map<String, Object> parameter);

	public String deleteGiveHouse(String roomId);

	public HouseOwner getHouseOwnerById(String roomId, String ownerId);

	public void delDecorateInfo(String roomId);

	public void updateOrSaveDecorateDetail(DecorateInfo di);

	public void delDecorateDetail(DecorateInfo di);

	public void updateHouseProperty(HouseProperty houseProperty);
	
	public void changeOwnerInfo(String ownerId,String operId,String operType);
}
