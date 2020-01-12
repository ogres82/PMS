package com.jdry.pms.chargeManager.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdry.pms.chargeManager.pojo.ChargeRoomInfoViewEntity;
import com.jdry.pms.chargeManager.service.ChargeRoomInfoViewService;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.beecloud.BCCache;
import cn.beecloud.BeeCloud;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.service.ChargeArrearViewService;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.chargeManager.service.ChargeSerialService;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.msgandnotice.service.MessageSendService;

@Repository
@Component
public class BeeCloudController implements IController {
    @Resource
    private ChargeInfoService service;
    //@Resource
    //private ChargeArrearViewService ArrearService;

	@Resource
	private ChargeRoomInfoViewService ArrearService;

    //@Resource
    //private DirectoryService dirService;
    @Resource
    private ChargeSerialService serialService;
    static Logger log = Logger.getLogger(BeeCloudController.class);
    @Resource
    MessageSendService messageSendService;


    private static final String APP_ID = "ce50356e-6eec-44e1-b8d3-5e61716d3c38";
    private static final String APP_Secret_LIVE = "8af3b29b-8c2d-4a26-a336-cb0d83d980e6";
    private static final String Test_Secret = "0a918958-2127-4421-8d6e-a1eeb3a3bfea";
    private static final String Master_Secret = "546d1928-78f1-46be-a33c-b17e41e95bd1";

    //支付方式：微信、阿里支付宝、银联、快钱、京东、……
    public enum Channel {
        WX, ALI, UN, KUAIQIAN, JD, BD, YEE, PAYPAL, BC
    }


    @Override
    public boolean anonymousAccess() {
        // TODO Auto-generated method stub
        return true;
    }

    @SuppressWarnings({"unused", "rawtypes", "unchecked"})
    @Override
    public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
            throws IOException, ServletException {
        arg0.setCharacterEncoding("utf-8");
        arg1.setCharacterEncoding("utf-8");
        arg1.setContentType("text/html; charset=utf-8");
        String method = arg0.getParameter("method");
        try {
            DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();

            //先使用测试模式
            BeeCloud.registerApp(APP_ID, Test_Secret, APP_Secret_LIVE, Master_Secret);
            BeeCloud.setSandbox(true);

            StringBuffer json = new StringBuffer();
            String line = null;
            try {
                arg0.setCharacterEncoding("utf-8");
                BufferedReader reader = arg0.getReader();
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jsonObj = JSONObject.fromObject(json.toString());
            System.out.println(BCCache.getAppID() + "--------" + BCCache.getAppSecret());
            String sign = jsonObj.getString("sign");
            String timestamp = jsonObj.getString("timestamp");

            //第一步验证数字签名
            boolean status = verifySign(sign, timestamp);
            System.out.println(jsonObj);
            System.out.println("第一步验证数字签名:" + status);

            if (status) {
                //第二步验证，去掉重复的请求
                //获取支付渠道
                String channel_type = jsonObj.getString("channel_type");
                //支付渠道方获得的详细结果信息
                JSONObject message_detail = jsonObj.getJSONObject("message_detail");
                String order_id = "";
                String charge_paid_mode = "";
                Channel channel = Channel.valueOf(channel_type);
                switch (channel) {
                    case WX:
                        order_id = message_detail.getString("transaction_id");    //微信交易号
                        charge_paid_mode = "08";
                        break;
                    case ALI:
                        order_id = message_detail.getString("trade_no");    //支付宝交易号
                        charge_paid_mode = "05";
                        break;
                    case UN:
                        order_id = message_detail.getString("queryId");        //银联交易流水号
                        charge_paid_mode = "04";
                        break;
                    case KUAIQIAN:
                        break;
                    case JD:
                        break;
                    case BD:
                        break;
                    case YEE:
                        break;
                    case PAYPAL:
                        break;
                    case BC:
                        break;
                    default:
                        break;
                }

                status = serialService.validateOrderId(order_id);
                System.out.println("第二步验证，去掉重复的请求:" + status);


                JSONObject optional = jsonObj.getJSONObject("optional");
                String type = optional.getString("optionalType");
                String owner_id = "";

                if (type != null && type.equals("02")) {        //type：02预缴费，否则缴欠费
                    if (status) {
                        //业务特殊，不需要第三部验证 验证订单金额
                        String room_id = optional.getString("roomId");
                        String room_no = optional.getString("roomNo");
                        String topup_amount = optional.getString("topupAmount");
                        owner_id = optional.getString("ownerId");
                        String owner_name = optional.getString("ownerName");
                        //此处处理业务逻辑
                        Map<String, Object> parameter = new HashMap();
                        parameter.put("charge_paid_mode", charge_paid_mode);
                        parameter.put("order_id", order_id);
                        parameter.put("room_id", room_id);
                        parameter.put("room_no", room_no);
                        parameter.put("topup_amount", topup_amount);
                        parameter.put("owner_id", owner_id);
                        parameter.put("owner_name", owner_name);
                        //System.out.println(parameter);
                        status = serialService.storeFromApp(parameter);
                        System.out.println("红包充值-----处理业务:" + status);
                    } else {
                        log.info("后台入账失败:账单重复验证失败---业主" + owner_id + "缴费金额未入账！");
                    }
                } else {
//			    	String charge_ids = "";
                    if (status) {
                        //第三部验证 验证订单金额
                        String transaction_fee = jsonObj.getString("transaction_fee");
                        String charge_ids = optional.getString("charge_ids");

                        BigDecimal total_pay = service.getPayCountByIds(charge_ids);
                        BigDecimal trans_fee = new BigDecimal(transaction_fee);

                        if (total_pay == null) {
                            System.out.println("获取欠费总额失败");
                        }
                        System.out.println(total_pay);
                        System.out.println(transaction_fee + "----" + trans_fee);


                        //比较是否相等
                        if (total_pay.compareTo(trans_fee) == 0) {
                            status = true;
                        } else {
                            status = false;
                        }

                        System.out.println("第三部验证 验证订单金额:" + status);

                        if (status) {
                            //此处处理业务逻辑
                            Map<String, Object> parameter = new HashMap();
                            parameter.put("charge_paid_mode", charge_paid_mode);
                            parameter.put("transaction_fee", trans_fee.divide(BigDecimal.valueOf(100)).toString());
                            parameter.put("order_id", order_id);
                            //System.out.println("账单id===========" + charge_ids);
                            //List<ChargeInfoEntity> infos = service.getChargeInfoByIds(charge_ids);

							parameter.put("roomId", charge_ids);
							parameter.put("ArrNum", "0");
							parameter.put("communityId", "");
							parameter.put("roomType", "");
							parameter.put("roomState", "");
							List<ChargeRoomInfoViewEntity> infos = ArrearService.queryArrearage(parameter);
                            status = serialService.paidFromApp(parameter, infos);
                            System.out.println("处理业务:" + status);
                        } else {
                            log.info("后台入账失败:账单金额不一致---账单" + charge_ids + "缴费金额未入账！");
                        }
                    } else {
                        log.info("后台入账失败:账单重复验证失败---业主" + owner_id + "缴费金额未入账！");
                    }
                }
            } else {
                log.info("后台入账失败:数字签名错误---缴费金额未入账！");
            }

            PrintWriter out = arg1.getWriter();
            if (status) {
                out.println("success"); //请不要修改或删除
            } else { //验证失败
                out.println("fail");
            }

            arg0.setAttribute("loginUser", user);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("后台入账失败:系统异常");
        }
    }

    boolean verify(String sign, String text, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));

        long timeDifference = System.currentTimeMillis() - Long.valueOf(key);
        if (mysign.equals(sign) && timeDifference <= 300000) {
            return true;
        } else {
            return false;
        }
    }

    boolean verifySign(String sign, String timestamp) {

        return verify(sign, BCCache.getAppID() + BCCache.getAppSecret(),
                timestamp, "UTF-8");

    }

    byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    @Override
    public String getUrl() {
        return "/ChargeManager/BeeCloud";
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}