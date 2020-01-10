package com.jdry.pms.chargeManager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdry.pms.chargeManager.pojo.ChargeSerialEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.service.ChargeArrearViewService;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.chargeManager.service.ChargeSerialService;
import com.jdry.pms.chargeManager.util.ChargeUtil;
import com.jdry.pms.comm.util.CommEnum;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.msgandnotice.pojo.MessageSendMain;
import com.jdry.pms.msgandnotice.service.MessageSendService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class ChargeInfoController implements IController {
    @Resource
    private ChargeInfoService service;
    @Resource
    private ChargeArrearViewService ArrearService;

    @Resource
    private DirectoryService dirService;
    @Resource
    private ChargeSerialService serialService;

    @Resource
    MessageSendService messageSendService;

    @Override
    public boolean anonymousAccess() {
        // TODO Auto-generated method stub
        return false;
    }

    public String getList() {
        List<ChargeInfoEntity> result = service.queryAll(null, null);
        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("chargeInfo", result);
        String b = JSON.toJSONString(info);
        b = b.substring(b.indexOf(":") + 1, b.length() - 1);
        return b;
    }

    @SuppressWarnings({"unused", "rawtypes", "unchecked"})
    @Override
    public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
        arg0.setCharacterEncoding("utf-8");
        arg1.setCharacterEncoding("utf-8");
        arg1.setContentType("text/html; charset=utf-8");
        String method = arg0.getParameter("method");
        try {

            DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
            if (method.equalsIgnoreCase("list")) {
                int currentPage = arg0.getParameter("offset") == null ? 1
                        : Integer.parseInt(arg0.getParameter("offset"));// 每页行数
                int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
                String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
                String begin_time = arg0.getParameter("begin_time") == null ? "" : arg0.getParameter("begin_time");
                String end_time = arg0.getParameter("end_time") == null ? "" : arg0.getParameter("end_time");
                String paid_mode_select = arg0.getParameter("paid_mode_select") == null ? ""
                        : arg0.getParameter("paid_mode_select");
                String charge_type_name_select = arg0.getParameter("charge_type_name_select") == null ? ""
                        : arg0.getParameter("charge_type_name_select");
                search = CharacterUtil.getUTF_8String(search);
                if (currentPage != 0) {// 获取页数
                    currentPage = currentPage / showCount;
                }
                currentPage += 1;
                Map<String, Object> parameter = new HashMap();
                parameter.put("search", search);
                parameter.put("begin_time", begin_time);
                parameter.put("end_time", end_time);
                parameter.put("paid_mode_select", paid_mode_select);
                parameter.put("charge_type_name_select", charge_type_name_select);
                Page<ChargeInfoViewEntity> page = new Page<ChargeInfoViewEntity>(showCount, currentPage);
                service.queryChargeByParam(page, parameter, null);
                List<ChargeInfoViewEntity> result = (List<ChargeInfoViewEntity>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount(); // 获取总记录数
                PrintWriter out = arg1.getWriter();
                String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
                out.print(r);
                out.flush();

                System.out.print(b);
                // arg0.setAttribute("chargeInfoList", getList());
            }
            // 已收账单显示
            if (method.equalsIgnoreCase("ReceivedList")) {
                int currentPage = arg0.getParameter("offset") == null ? 1
                        : Integer.parseInt(arg0.getParameter("offset"));// 每页行数
                int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
                String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
                search = CharacterUtil.getUTF_8String(search);
                if (currentPage != 0) {// 获取页数
                    currentPage = currentPage / showCount;
                }
                currentPage += 1;
                Map<String, Object> parameter = new HashMap();
                parameter.put("search", search);
                Page<ChargeInfoViewEntity> page = new Page<ChargeInfoViewEntity>(showCount, currentPage);
                service.queryChargeByParam(page, parameter, "02");
                List<ChargeInfoViewEntity> result = (List<ChargeInfoViewEntity>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount(); // 获取总记录数
                PrintWriter out = arg1.getWriter();
                String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
                out.print(r);
                out.flush();
                // arg0.setAttribute("chargeInfoList", getList());
            }
            // 欠费账单显示
            if (method.equalsIgnoreCase("ArrearList")) {
                int currentPage = arg0.getParameter("offset") == null ? 1
                        : Integer.parseInt(arg0.getParameter("offset"));// 每页行数
                int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
                String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
                String communityNameSearch = arg0.getParameter("communityNameSearch") == null ? ""
                        : arg0.getParameter("communityNameSearch");
                String chargeArrearNum = arg0.getParameter("chargeArrearNum") == null ? ""
                        : arg0.getParameter("chargeArrearNum");
                search = CharacterUtil.getUTF_8String(search);
                communityNameSearch = CharacterUtil.getUTF_8String(communityNameSearch);
                if (currentPage != 0) {// 获取页数
                    currentPage = currentPage / showCount;
                }
                currentPage += 1;
                Map<String, Object> parameter = new HashMap();
                parameter.put("search", search);
                parameter.put("communityNameSearch", communityNameSearch);
                parameter.put("chargeArrearNum", chargeArrearNum);
                Page<ChargeArrearViewEntity> page = new Page<ChargeArrearViewEntity>(showCount, currentPage);
                ArrearService.queryAll(page, parameter);
                List<ChargeArrearViewEntity> result = (List<ChargeArrearViewEntity>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount(); // 获取总记录数
                PrintWriter out = arg1.getWriter();
                String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
                out.print(r);
                out.flush();
                // arg0.setAttribute("chargeInfoList", getList());
            }

            // 欠费明细
            if (method.equalsIgnoreCase("DetailArrearList")) {
                int currentPage = arg0.getParameter("offset") == null ? 1
                        : Integer.parseInt(arg0.getParameter("offset"));// 每页行数
                int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
                String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
                String room_id = arg0.getParameter("room_id") == null ? "" : arg0.getParameter("room_id");
                search = CharacterUtil.getUTF_8String(search);
                if (currentPage != 0) {// 获取页数
                    currentPage = currentPage / showCount;
                }
                currentPage += 1;
                Map<String, Object> parameter = new HashMap();
                parameter.put("search", search);
                parameter.put("room_id", room_id);
                Page<ChargeInfoViewEntity> page = new Page<ChargeInfoViewEntity>(showCount, currentPage);
                service.queryChargeByParam(page, parameter, "03");
                List<ChargeInfoViewEntity> result = (List<ChargeInfoViewEntity>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount(); // 获取总记录数
                PrintWriter out = arg1.getWriter();
                String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
                out.print(r);
                out.flush();
                // arg0.setAttribute("chargeInfoList", getList());
            }

            // 欠费短信发送
            if (method.equalsIgnoreCase("sendMessage")) {
                String arrearInfo = arg0.getParameter("arrearInfo");
                String msgType = arg0.getParameter("msgType");
                List<Map<String, Object>> sendInfos = new ArrayList<Map<String, Object>>();
                List<ChargeArrearViewEntity> chargeInfos = JSON.parseArray(arrearInfo, ChargeArrearViewEntity.class);

                for (ChargeArrearViewEntity info : chargeInfos) {
                    Map<String, Object> parameter = new HashMap();
                    String room_id = info.getRoom_id();
//					BigDecimal currentMounth = service.getArrearAmountByRoomId(room_id);
//					currentMounth = currentMounth == null ? new BigDecimal(0):currentMounth;

                    Map<String, Object> send = new HashMap();
                    send.put("ownerId", info.getOwner_id());
                    send.put("phone", info.getPhone());
                    send.put("delayPay", info.getDelay_pay().toString());
                    send.put("sumFee", info.getArrearage_amount().toString());
                    if ("1".equals(msgType)) {
                        send.put("tempId", CommEnum.CHARGE_LATE_TEMP_ID);
                    } else {
                        send.put("tempId", CommEnum.CHARGE_TEMP_ID);
                    }
                    send.put("msgType", msgType);
                    sendInfos.add(send);
                }

                String result = messageSendService.sendArrearMessage(sendInfos, null);

                PrintWriter out = arg1.getWriter();
                out.print(result);
            }

            // 欠费短信发送的历史记录
            if (method.equalsIgnoreCase("sendMessageHistory")) {
                String phone = arg0.getParameter("phone");
                List<MessageSendMain> his = messageSendService.queryMessagesByPhone(phone);
                List res = new ArrayList();

                for (MessageSendMain send : his) {
                    Map mp = new HashMap();
                    mp.put("create_time", send.getMsgCreateTime());
                    mp.put("create_name", send.getMsgCreator().getCname());
                    mp.put("receive_name", send.getMsgReceivers().getOwnerName());
                    mp.put("phone", send.getMsgReceivers().getPhone());
                    mp.put("content", send.getMsgContent());
                    res.add(mp);
                }

                String b = JSON.toJSONString(res);
                System.out.println(b);
                PrintWriter out = arg1.getWriter();
                String r = "{\"total\":" + res.size() + ",\"rows\":" + b + "}";
                out.print(r);
            }

            // 应付的账单明细(欠费和未付)
            if (method.equalsIgnoreCase("paymentList")) {
                String room_id = arg0.getParameter("room_id") == null ? "" : arg0.getParameter("room_id");
                List<ChargeInfoEntity> result = service.queryChargeByRoomId(room_id);
                String b = JSON.toJSONString(result);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            // 客户收费页面，确认收费按钮
            if (method.equalsIgnoreCase("batchPaid")) {
                String paid = arg0.getParameter("chargeInfo");
                List<ChargeSerialEntity> chargeInfos = JSON.parseArray(paid, ChargeSerialEntity.class);
                String result = serialService.paid(null, chargeInfos);
                if ("success".equals(result)) {
                    PrintWriter out = arg1.getWriter();
                    JSONObject obj = new JSONObject();
                    obj.put("status", "200");
                    obj.put("msg", "成功");
                    out.println(obj.toJSONString());
                    out.flush();
                } else {
                    PrintWriter out = arg1.getWriter();
                    JSONObject obj = new JSONObject();
                    obj.put("status", "104");
                    obj.put("msg", result);
                    out.println(obj.toJSONString());
                    out.flush();
                }
                System.out.println(paid);
            }

            // 客户收费页面，新增收费项保存
            if (method.equalsIgnoreCase("saveCharge")) {
                String room_id = arg0.getParameter("room_id");
                String room_no = arg0.getParameter("room_no");
                String owner_id = arg0.getParameter("owner_id");
                String owner_name = arg0.getParameter("owner_name");
                String charge_type_id = arg0.getParameter("charge_type_id");
                String charge_type_no = arg0.getParameter("charge_type_no");
                String charge_type_name = arg0.getParameter("charge_type_name");
                String begin_time = arg0.getParameter("begin_time");
                String end_time = arg0.getParameter("end_time");
                String receive_amount = arg0.getParameter("receive_amount");
                String remark = arg0.getParameter("remark");
                Map<String, Object> parameter = new HashMap();
                parameter.put("room_id", room_id);
                parameter.put("room_no", room_no);
                parameter.put("owner_id", owner_id);
                parameter.put("owner_name", owner_name);
                parameter.put("charge_type_id", charge_type_id);
                parameter.put("charge_type_no", charge_type_no);
                parameter.put("charge_type_name", charge_type_name);
                parameter.put("begin_time", begin_time);
                parameter.put("end_time", end_time);
                parameter.put("receive_amount", receive_amount);
                parameter.put("remark", remark);

                service.addCharge(parameter);

            }

            if (method.equalsIgnoreCase("update")) {
                String charge_id = arg0.getParameter("charge_id");
                String charge_no = arg0.getParameter("charge_no");
                String room_no = arg0.getParameter("room_no");
                String owner_name = arg0.getParameter("owner_name");
                String charge_type_no = arg0.getParameter("charge_type_no");
                String charge_type_name = arg0.getParameter("charge_type_name");
                String price = arg0.getParameter("price");
                String count = arg0.getParameter("count");
                String rate = arg0.getParameter("rate");
                String begin_time = arg0.getParameter("begin_time");
                String end_time = arg0.getParameter("end_time");
                String receive_amount = arg0.getParameter("receive_amount");
                String paid_amount = arg0.getParameter("paid_amount");
                String arrearage_amount = arg0.getParameter("arrearage_amount");
                String state = arg0.getParameter("state");
                String paid_mode = arg0.getParameter("paid_mode");
                String paid_date = arg0.getParameter("paid_date");
                String oper_emp_id = arg0.getParameter("oper_emp_id");
                String update_date = arg0.getParameter("update_date");
                String remark = arg0.getParameter("remark");

                Date beginTime = ChargeUtil.StrToDate(begin_time);
                Date endTime = ChargeUtil.StrToDate(end_time);
                Date paidDate = ChargeUtil.StrToDate(paid_date);
                Date updateDate = ChargeUtil.StrToDate(update_date);

                ChargeInfoEntity chargeInfo = new ChargeInfoEntity();
                chargeInfo.setCharge_id(charge_id);
                chargeInfo.setCharge_no(charge_no);
                chargeInfo.setCharge_type_no(charge_type_no);
                chargeInfo.setCharge_type_name(charge_type_name);
                chargeInfo.setRoom_no(room_no);
                chargeInfo.setOwner_name(owner_name);
                chargeInfo.setPrice(new BigDecimal(price));
                chargeInfo.setCount(new BigDecimal(count));
                chargeInfo.setRate(new BigDecimal(rate));
                chargeInfo.setReceive_amount(new BigDecimal(receive_amount));
                chargeInfo.setPaid_amount(new BigDecimal(paid_amount));
                chargeInfo.setArrearage_amount(new BigDecimal(arrearage_amount));
                chargeInfo.setState(state);
                chargeInfo.setPaid_mode(paid_mode);
                chargeInfo.setOper_emp_id(oper_emp_id);
                chargeInfo.setRemark(remark);

                chargeInfo.setBegin_time(beginTime);
                chargeInfo.setEnd_time(endTime);
                chargeInfo.setPaid_date(paidDate);
                chargeInfo.setUpdate_date(updateDate);

                List<ChargeInfoEntity> saveList = new ArrayList<ChargeInfoEntity>();
                saveList.add(chargeInfo);
                service.saveAll(saveList);

//				arg0.setAttribute("chargeInfoList", getList());
            }

            // 初始化下拉框initDropAll
            if (method.equalsIgnoreCase("initDropAll")) {
                List<DirDirectoryDetail> positions = (List<DirDirectoryDetail>) dirService
                        .getDirectoryLikeCode("charge_");
                String jsonString = JSON.toJSONString(positions);
                arg1.setCharacterEncoding("utf-8");
                arg1.setContentType("application/json;charset=utf-8");
                arg1.setHeader("pragma", "no-cache");
                arg1.setHeader("cache-control", "no-cache");
                // 传输JSON
                PrintWriter out = arg1.getWriter();
                out.println(jsonString);
                out.flush();
            }
            //手动生成业主账单
/*			if(method.equalsIgnoreCase("manualCharge")) {
				String roomId = arg0.getParameter("roomId");
				String operId = arg0.getParameter("operId");
				String startTime = arg0.getParameter("startTime");
				String endTime = arg0.getParameter("endTime");
				
				int intError = service.manOwnerOfCharge(operId, roomId, startTime, endTime);
				String jsonString = "";
				if(intError == 0) {
					 jsonString = "succeed";
				}else {
					 jsonString = "failure";
				}
				
				// 传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(JSON.toJSONString(jsonString));
				out.flush();
			}
*/
            arg0.setAttribute("loginUser", user);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public String getUrl() {
        return "/ChargeManager/ChargeInfoList";
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}