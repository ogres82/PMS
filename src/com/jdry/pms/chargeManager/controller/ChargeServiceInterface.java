package com.jdry.pms.chargeManager.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import com.jdry.pms.chargeManager.service.ChargeRoomInfoViewService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.service.ChargeSerialService;
import com.jdry.pms.comm.util.SpringUtil;

/**
 * 收费模块对app接口
 *
 * @author lpb
 */
@Repository
@Component
public class ChargeServiceInterface {

    //	@Resource
//	private ChargeInfoService service;
//
    @Resource
    private ChargeSerialService serialService;
    @Resource
    private ChargeRoomInfoViewService service;


    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getArrearInfoByRoom(String data) {
        //初始化上下文环境
        if (service == null) {
            service = (ChargeRoomInfoViewService) SpringUtil.getObjectFromApplication("chargeRoomInfoViewImpl");
        }


        JSONObject parm = JSON.parseObject(data);
        String roomId = parm.getString("roomId");
        Map<String, Object> parameter = new HashMap();
        Date beginTime ;
        Date endTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map map = new HashMap();
        Map mapRes = new HashMap();
        String status = "";
        String message = "";
        List res = new ArrayList();
        if (roomId != null && !roomId.isEmpty()) {
            parameter.put("roomId", roomId);
            parameter.put("ArrNum", "0");
            parameter.put("communityId", "");
            parameter.put("roomType", "");
            parameter.put("roomState", "");
            List<ChargeRoomInfoViewEntity> chargeInfoViewEntities = service.queryArrearage(parameter);

            for (int i = 0, len = chargeInfoViewEntities.size(); i < len; i++) {
                Map mp = new HashMap();
                beginTime =  chargeInfoViewEntities.get(i).getBeginDate();
                endTime = chargeInfoViewEntities.get(i).getEndDate();
                mp.put("charge_type_no", chargeInfoViewEntities.get(i).getChargeTypeNo());
                mp.put("charge_type_name", chargeInfoViewEntities.get(i).getChargeTypeName());
                mp.put("work_id", "");
                mp.put("owner_id", chargeInfoViewEntities.get(i).getOwnerId());
                mp.put("owner_name", chargeInfoViewEntities.get(i).getOwnerName());
                mp.put("room_id", chargeInfoViewEntities.get(i).getRoomId());
                mp.put("room_no", chargeInfoViewEntities.get(i).getRoomNo());
                mp.put("begin_time",sdf.format(beginTime));
                mp.put("end_time", sdf.format(endTime));
                mp.put("arrearage_amount", chargeInfoViewEntities.get(i).getReceiveAmount());
                mp.put("charge_ids", roomId);
                res.add(mp);
            }

            List<ChargeRoomInfoViewEntity> roomVsFees = service.queryAll(parameter);
            if (roomVsFees.size() > 0) {
                for (int i = 0, len = roomVsFees.size(); i < len; i++) {
                    mapRes.put("type_no", roomVsFees.get(i).getChargeTypeNo());
                    mapRes.put("type_name", roomVsFees.get(i).getChargeTypeName());
                    mapRes.put("build_area", roomVsFees.get(i).getBuildArea());
                    mapRes.put("charge_price", roomVsFees.get(i).getChargePrice());
                    mapRes.put("total_price", roomVsFees.get(i).getMonthsPrice());
                    mapRes.put("amount", roomVsFees.get(i).getAmount());
                    mapRes.put("room_id", roomId);
                }
            } else {
                mapRes.put("type_no", null);
                mapRes.put("type_name", null);
                mapRes.put("build_area", null);
                mapRes.put("charge_price", null);
                mapRes.put("total_price", null);
                mapRes.put("amount", null);
                mapRes.put("room_id", roomId);
            }

            mapRes.put("arrearageInfo", res);


            status = "1";
            message = "成功，欠费信息有" + chargeInfoViewEntities.size() + "条";
        } else {
            status = "0";
            message = "失败，业主房间ID为空";
        }

        // 传输JSON
        map.put("status", status);
        map.put("message", message);
        map.put("data", mapRes);
        String arrearInfo = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);

        System.out.println(arrearInfo);
        return arrearInfo;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public String getPaidHostoryByRoom(String data) {
        //初始化上下文环境
        if (serialService == null) {
            serialService = (ChargeSerialService) SpringUtil.getObjectFromApplication("chargeSerialImpl");
        }


        JSONObject obj = JSON.parseObject(data);
        String roomId = obj.getString("roomId");

        Map map = new HashMap();
        String status = "";
        String message = "";
        List<ChargeSerialViewEntity> result = null;
        if (roomId != null && !roomId.isEmpty()) {
            Map<String, Object> parameter = new HashMap();
            parameter.put("room_id", roomId);
            result = serialService.getPaidHostory(parameter);
            status = "1";
            message = "成功，缴费信息有" + result.size() + "条";
        } else {
            status = "0";
            message = "失败，业主房间ID为空";
        }

        // 传输JSON
        map.put("status", status);
        map.put("message", message);
        map.put("data", result);
//		String paidInfo = JSON.toJSONString(map);
        String paidInfo = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        return paidInfo;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getArrearInfoByWorkId(String data) {
        //初始化上下文环境
//        if (service == null) {
//            service = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
//        }

        JSONObject parm = JSON.parseObject(data);
        String workId = parm.getString("workId");

        Map map = new HashMap();
        String status = "";
        String message = "";
        List result = null;
        List res = new ArrayList();
//        if (workId != null && !workId.isEmpty()) {
//            result = service.queryChargeArrearByWorkId(workId);
//
//            for (int i = 0, len = result.size(); i < len; i++) {
//                Map mp = new HashMap();
//                Object[] obj = (Object[]) result.get(i);
//                String work_id = null == obj[0] ? "" : obj[0].toString();
//                mp.put("work_id", work_id);
//                String owner_id = null == obj[1] ? "" : obj[1].toString();
//                mp.put("owner_id", owner_id);
//                String owner_name = null == obj[2] ? "" : obj[2].toString();
//                mp.put("owner_name", owner_name);
//                String room_id = null == obj[3] ? "" : obj[3].toString();
//                mp.put("room_id", room_id);
//                String room_no = null == obj[4] ? "" : obj[4].toString();
//                mp.put("room_no", room_no);
//                String arrearage_total = null == obj[5] ? "" : obj[5].toString();
//                mp.put("arrearage_total", arrearage_total);
//                String receive_total = null == obj[6] ? "" : obj[6].toString();
//                mp.put("receive_total", receive_total);
//                String charge_type_no = null == obj[7] ? "" : obj[7].toString();
//                mp.put("charge_type_no", charge_type_no);
//                String charge_type_name = null == obj[8] ? "" : obj[8].toString();
//                mp.put("charge_type_name", charge_type_name);
//                String arrearage_amount = null == obj[9] ? "" : obj[9].toString();
//                mp.put("arrearage_amount", arrearage_amount);
//                String receive_amount = null == obj[10] ? "" : obj[10].toString();
//                mp.put("receive_amount", receive_amount);
//                String charge_ids = null == obj[11] ? "" : obj[11].toString();
//                mp.put("charge_ids", charge_ids);
//                String begin_time = null == obj[12] ? "" : obj[12].toString();
//                mp.put("begin_time", begin_time);
//                String end_time = null == obj[13] ? "" : obj[13].toString();
//                mp.put("end_time", end_time);
//                res.add(mp);
//
//            }
//
//            status = "1";
//            message = "成功，欠费信息有" + result.size() + "条";
//        } else {
//            status = "0";
//            message = "失败，业主房间ID为空";
//        }

        // 传输JSON
        map.put("status", status);
        map.put("message", message);
        map.put("data", res);
        String arrearInfo = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);

        System.out.println(arrearInfo);
        return arrearInfo;
    }

}
