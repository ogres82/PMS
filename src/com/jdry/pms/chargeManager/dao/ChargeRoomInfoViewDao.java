package com.jdry.pms.chargeManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdry.pms.chargeManager.pojo.ChargeSerialInfo;
import com.jdry.pms.chargeManager.pojo.RoomVsFee;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;

@Repository
public class ChargeRoomInfoViewDao extends HibernateDao {

    public List<ChargeRoomInfoViewEntity> queryAll(Map<String, Object> parameter) {
        String hql = " FROM ChargeRoomInfoViewEntity a";
        String whereCase = " where 1=1 ";
        String communityId = parameter.get("communityId").toString();
        String roomType = parameter.get("roomType").toString();
        String roomState = parameter.get("roomState").toString();
        String roomId = parameter.get("roomId").toString();
        Map<String, Object> map = new HashMap<String, Object>();
        if (parameter != null) {
            if (!"".equals(communityId)) {
                map.put("communityId", communityId);
                whereCase += " and a.communityId =:communityId";
            }
            if (!"".equals(roomType)) {
                map.put("roomType", roomType);
                whereCase += " and a.roomType =:roomType";
            }
            if (!"".equals(roomState)) {
                map.put("roomState", roomState);
                whereCase += " and a.roomState =:roomState";
            }
            if (!"".equals(roomId)) {
                map.put("roomId", roomId);
                whereCase += " and a.roomId =:roomId";
            }
        }
        hql += whereCase;
        return this.query(hql, map);
    }

    public List<Map<String, Object>> queryPayRec(Map<String, Object> parameter) {

        String hql = " FROM ChargeSerialInfo a ";
        String whereCase = " where 1=1 ";
        String communityId = parameter.get("communityId").toString();
        String roomId = parameter.get("roomId").toString();
        String paidMode = parameter.get("paidMode").toString();
        String typeFlag = parameter.get("typeFlag").toString();
        //Map<String, Object> map = new HashMap<String, Object>();
        if (!"".equals(roomId)) {
            // map.put("roomId", roomId);
            whereCase += " and a.roomId =" + roomId;
        }
        if (!"".equals(paidMode)) {
            //map.put("paidMode", paidMode);
            whereCase += " and a.paidMode ='" + paidMode;
        }
        if (!"".equals(communityId)) {
            //map.put("communityId", communityId);
            whereCase += " and a.communityId =" + communityId;
        }
        if (!"".equals(typeFlag)) {
            //map.put("typeFlag", typeFlag);
            whereCase += " and a.typeFlag =" + typeFlag;
        }
        hql += whereCase;
        return this.query(hql);
    }

    public List<ChargeRoomInfoViewEntity> queryArrearage(Map<String, Object> parameter) {
        String hql = " FROM ChargeRoomInfoViewEntity a Where 1=1 ";
        String roomId = parameter.get("roomId").toString();
        String communityId = parameter.get("communityId").toString();
        Integer ArrNum = Integer.parseInt(parameter.get("ArrNum").toString());
        hql += " and a.receiveAmount >" + ArrNum + "*a.monthsPrice";
        if (!"".equals(roomId)) {
            hql += " and a.roomId ='" + roomId+"'";
        }
        if (!"".equals(communityId)) {
            hql += " and a.communityId =" + communityId;
        }
        return this.query(hql);
    }

    public List<RoomVsFee> getRoomOfChargeInfo(String roomId){
        String hql = " FROM RoomVsFee a WHERE roomId ='"+roomId+"'";
        return this.query(hql);
    }
}
