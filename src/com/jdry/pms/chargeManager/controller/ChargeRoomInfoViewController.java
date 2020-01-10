package com.jdry.pms.chargeManager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.service.ChargeRoomInfoViewService;

@Repository
public class ChargeRoomInfoViewController implements IController {

    @Resource
    ChargeRoomInfoViewService service;

    @Override
    public boolean anonymousAccess() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
        // TODO Auto-generated method stub
        arg0.setCharacterEncoding("utf-8");
        arg1.setCharacterEncoding("utf-8");
        arg1.setContentType("text/html; charset=utf-8");
        // 获取前端的方法来 进行路由
        String method = arg0.getParameter("method");
        // 返回前端字符串

        PrintWriter out = arg1.getWriter();
        String jsonString="";

        String communityId = arg0.getParameter("communityId") == null ? "" : arg0.getParameter("communityId");
        String roomState = arg0.getParameter("roomState") == null ? "" : arg0.getParameter("roomState");
        String roomType = arg0.getParameter("roomType") == null ? "" : arg0.getParameter("roomType");
        String paidMode = arg0.getParameter("paidMode") == null ? "" : arg0.getParameter("paidMode").toString();
        String roomId = arg0.getParameter("roomId") == null ? "" : arg0.getParameter("roomId").toString();
        String typeFlag = arg0.getParameter("typeFlag") == null ? "" : arg0.getParameter("typeFlag").toString();
        String ArrNum = arg0.getParameter("ArrNum") == null ? "" : arg0.getParameter("ArrNum").toString();

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("communityId", communityId);
        parameter.put("roomState", roomState);
        parameter.put("roomType", roomType);
        parameter.put("roomId", roomId);
        parameter.put("paidMode", paidMode);
        parameter.put("typeFlag", typeFlag);
        if("".equals(ArrNum)){
            ArrNum="0";
        }
        parameter.put("ArrNum", ArrNum);
        try {
            if (method.equalsIgnoreCase("list")) {
                List<ChargeRoomInfoViewEntity> lists = service.queryAll(parameter);
                jsonString = JSON.toJSONString(lists);
            }
            if (method.equalsIgnoreCase("payRec")) {
                List<Map<String, Object>> lists = service.queryPayRec(parameter);
                jsonString = JSON.toJSONString(lists);
            }
            if (method.equalsIgnoreCase("arrearage")) {
                List<ChargeRoomInfoViewEntity> lists = service.queryArrearage(parameter);
                jsonString = JSON.toJSONString(lists);
            }
        } catch (Exception e) {

            jsonString = "操作失败，失败信息为：" + e.getMessage();
            out.println(jsonString);
            e.printStackTrace();
        } finally {
            out.println(jsonString);
            out.flush();
            out.close();
        }

    }

    @Override
    public String getUrl() {
        // TODO Auto-generated method stub
        return "/ChargeManager/ChargeRoomInfoList";
    }

    @Override
    public boolean isDisabled() {
        // TODO Auto-generated method stub
        return false;
    }

}
