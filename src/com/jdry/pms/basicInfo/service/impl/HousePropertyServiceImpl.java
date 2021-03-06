package com.jdry.pms.basicInfo.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.basicInfo.pojo.*;
import com.jdry.pms.chargeManager.dao.ChargeTypeRoomRelaDao;
import com.jdry.pms.chargeManager.pojo.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.OwnerInfoService;
import com.jdry.pms.lzmh.dao.LzmhDao;
import com.jdry.pms.lzmh.service.LzmhService;

@Repository
@Component
public class HousePropertyServiceImpl implements HousePropertyService {

    @Resource
    private HousePropertyDao dao;
    @Autowired
    private LzmhService lzmhService;
    @Resource
    private OwnerInfoService ownerInfoService;

    @Autowired
    private ChargeTypeRoomRelaDao chargeTypeRoomRelaDao;

    @Override
    public void query(Page<HouseProperty> page, Map<String, Object> parameter, Criteria criteria) {

        dao.query(page, parameter, criteria);
    }

    @Override
    public void saveHouseProperty(Collection<HouseProperty> houses) {

        dao.saveHouseProperty(houses);
    }

    @Override
    public List<BuildingProperty> getBuilding(Map<String, Object> parameter) {

        return dao.getBuilding(parameter);
    }

    @Override
    public Collection<HouseProperty> queryHousePropertyByParam(Map<String, Object> parameter) {

        return dao.queryHousePropertyByParam(parameter);
    }

    @Override
    public Collection<HouseProperty> queryHousePropertyByParent(String id) {

        return dao.queryHousePropertyByParent(id);
    }

    @Override
    public Collection<HouseProperty> queryHousePropertyByJdbc(Map<String, String> parameter) {

        return dao.queryHousePropertyByJdbc(parameter);
    }

    @Override
    public void queryHousePropertyByParam(Page<VHouseProperty> page, Map<String, Object> parameter, String type) {
        dao.queryHousePropertyByParam(page, parameter, type);
    }

    @Override
    public void queryHouseOwnerByParam(Page<VHouseOwner> page, Map<String, Object> parameter, String type) {
        dao.queryHouseOwnerByParam(page, parameter, type);
    }

    @Override
    public VHouseProperty getVHousePropertyById(String roomId) {

        return dao.getVHousePropertyById(roomId);
    }

    @Override
    public void addHouseProperty(HouseProperty houseProperty) {

        dao.addHouseProperty(houseProperty);
    }

    @Override
    public void updateHouseProperty(HouseProperty houseProperty) {

        dao.updateHouseProperty(houseProperty);
    }

    public boolean checkRoomNo(String unitId, String roomNo, String roomId) {
        return dao.checkRoomNo(unitId, roomNo, roomId);
    }

    @Override
    public void deleteHouseProperty(String roomId) {
        dao.deleteHouseProperty(roomId);
    }

    @Override
    public String giveHouse(Map<String, Object> param) {
        return dao.giveHouse(param);
    }

    @Override
    public List<VHouseOwner> queryVHouseOwnerByParam(Map<String, Object> param) {

        return dao.queryVHouseOwnerByParam(param);
    }

    @Override
    public List<ChargeTypeSettingViewEntity> queryChargeTypeByParam(Map<String, Object> param) {
        return dao.queryChargeTypeByParam(param);
    }

    @Override
    public HouseProperty getHousePropertyById(String roomId) {

        return dao.getHousePropertyById(roomId);
    }

    @Override
    public List<VRoomChargeTypeRela> getVRoomChargeTypeRelaById(String roomId) {

        return dao.getVRoomChargeTypeRelaById(roomId);
    }

    @Override
    public List<ChargeInfoEntity> getChargeInfoEntityById(String roomId) {
        return dao.getChargeInfoEntityById(roomId);
    }

    @Override
    public List<VRoomCharge> getVRoomChargeById(Map<String, Object> param) {
        return dao.getVRoomChargeById(param);
    }

    @Override
    public boolean isExistCharge(Map<String, Object> param) {
        return dao.isExistCharge(param);
    }

    @Override
    public HouseProperty getHousePropertyByNo(String roomNo) {
        return dao.getHousePropertyByNo(roomNo);
    }

    @Override
    public List<ChargeSerialViewEntity> getChargeSerial(Map<String, Object> param) {
        return dao.getChargeSerial(param);
    }

    @Override
    public List queryHouseDetail(String keyword) {
        // TODO Auto-generated method stub
        return dao.queryHouseDetail(keyword);
    }

    @Override
    public List<VHouseOwner> getVHouseOwnerByOwnerId(String ownerId) {
        // TODO Auto-generated method stub
        return dao.getVHouseOwnerByOwnerId(ownerId);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.jdry.pms.basicInfo.service.HousePropertyService#addHouseOwner(com.jdry.
     * pms.basicInfo.pojo.HouseOwner)
     */
    @Override
    public void addHouseOwner(HouseOwner ho) {
        // TODO Auto-generated method stub
        dao.addHouseOwner(ho);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.jdry.pms.basicInfo.service.HousePropertyService#queryHouseOwner(java.util
     * .Map)
     */
    @Override
    public List<HouseOwner> queryHouseOwner(Map<String, Object> parameter) {
        // TODO Auto-generated method stub
        return dao.queryHouseOwner(parameter);
    }

    @Override
    public String deleteGiveHouse(String roomId) {
        return dao.deleteGiveHouse(roomId);
    }

    @Override
    public HouseOwner getHouseOwnerById(String roomId, String ownerId) {
        // TODO Auto-generated method stub
        return dao.getHouseOwnerById(roomId, ownerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delDecorateInfo(String roomId) {
        // TODO Auto-generated method stub
        String[] roomIds = roomId.split(",");
        if (roomIds.length > 0) {
            dao.delDecorateInfo(roomId);
        }
    }

    @Override
    public void updateOrSaveDecorateDetail(DecorateInfo di) {
        // TODO Auto-generated method stub
        dao.updateOrSaveDecorateDetail(di);
    }

    @Override
    public void delDecorateDetail(DecorateInfo di) {
        // TODO Auto-generated method stub
        dao.updateOrSaveDecorateDetail(di);
    }

    @Override
    public void changeOwnerInfo(String ownerId, String operId, String operType) {
        // TODO Auto-generated method stub 13739488928 18980771518
        List<VHouseOwner> vhouseOwnerlist = this.getVHouseOwnerByOwnerId(ownerId);
        if (vhouseOwnerlist != null && vhouseOwnerlist.size() > 0) {
            Integer lzId;//联掌ID
            String ownerIdentity;//业主类型
            String newOwnerId = null;//业主ID

            for (int i = 0, len = vhouseOwnerlist.size(); i < len; i++) {
                String roomId = vhouseOwnerlist.get(i).getRoomId();
                List<Map<String, Object>> list = dao.changeOwnerInfo(roomId, operId, operType);
                if (list != null && list.size() > 0) {
                    //删除之前绑定的业主
                    for (int j = 0; j < list.size(); j++) {
                        ownerIdentity = list.get(j).get("ownerIdentity").toString();
                        lzId = (Integer) list.get(j).get("lzId");
                        lzmhService.delOwnerInfo((Integer) list.get(j).get("lzId"));
                        if ("0".equals(ownerIdentity)) {
                            newOwnerId = list.get(j).get("ownerId").toString();
                        }
                    }

                    //重新添加业主
                    lzmhService.addOwnerInfo(this.getHouseOwnerById(roomId, newOwnerId));
                    for (int k = 0; k < list.size(); k++) {
                        ownerIdentity = list.get(k).get("ownerIdentity").toString();
                        if (!"0".equals(ownerIdentity)) {
                            lzmhService.addOwnerInfo(this.getHouseOwnerById(roomId, list.get(k).get("ownerId").toString()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void queryRoomOfOwnerInfo(Page<RoomOfOwnerInfo> page,Map<String, Object> params) throws Exception {
        dao.queryRoomOfOwnerInfo(page,params);
    }

    @Override
    public List<RoomOfOwnerInfo> queryHouseOwnerByParam(Map<String, Object> param) {
        return null;
    }

    @Override
    public void unOwner(String roomId, Integer lzRoomOwnerId,String operId) {
		this.batchDelLzOwner(roomId,lzRoomOwnerId);
		dao.unOwner(roomId,operId);
    }

    public void batchDelLzOwner(String roomId,Integer lzRoomOwnerId) {
        Map<String, Object> parameter = new HashMap<String, Object>();
		JSONObject strResult = new JSONObject();
		Integer lzId;
        if ("".equals(roomId)) {
            return;
        }
        parameter.put("roomId",roomId);
        List<HouseOwner> houseOwnerList = this.queryHouseOwner(parameter);
        if(houseOwnerList.size()==0||houseOwnerList==null){
        	return;
		}
		for(int i=0,len=houseOwnerList.size();i<len;i++){
			lzId = houseOwnerList.get(i).getLzId();//联掌ID
			if(lzId==lzRoomOwnerId){
				continue;
			}
			strResult = JSON.parseObject(lzmhService.delOwnerInfo(lzId));
			System.out.println(strResult);
		}
		lzmhService.delOwnerInfo(lzRoomOwnerId);
        return;
    }
    public void repOwner(String roomId,Integer lzRoomOwnerId,String operId,String newOwnerId,String phone,String lzRoomId){
        //删除联掌中的关联用户
        this.batchDelLzOwner(roomId, Integer.parseInt(lzRoomId));
        ChargeTypeRoomRelaEntity chargeTypeRoomRelaEntity= dao.getChargeTypeRoomRelaEntityByRoomId(roomId);
        //删除物业平台关联用户
        dao.delHouseOwners(roomId);
        //新增物业平台关联用户
        HouseOwner houseOwner = new HouseOwner();
        houseOwner.setRoomId(roomId);
        houseOwner.setOwnerId(newOwnerId);
        houseOwner.setOperId(operId);
        houseOwner.setBandingMark("0");
        houseOwner.setDefaultMark("0");
        this.addHouseOwner(houseOwner);
        //修改物业平台收费项关联业主
        chargeTypeRoomRelaEntity.setOwner_id(newOwnerId);
        chargeTypeRoomRelaEntity.setUpdate_emp_id(operId);
        chargeTypeRoomRelaDao.saveAll(chargeTypeRoomRelaEntity);
        //重新添加联掌用户
        lzmhService.addOwnerInfo(houseOwner);
    }
}
