package com.jdry.pms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.comm.util.SignUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

@Component
public class lzmhController implements IController {

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		PrintWriter out = arg1.getWriter();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			arg1.setContentType("text/html");
			arg1.setCharacterEncoding("UTF-8");
			// 获取联掌门户请求的信息
			String app_id = arg0.getHeader("app_id");
			String timestamp = arg0.getHeader("timestamp");
			String sign = arg0.getHeader("sign");
			String url = arg0.getRequestURL().toString();

			System.out.print(url);
			// 获取request正文
			BufferedReader br = new BufferedReader(new InputStreamReader(arg0.getInputStream(), "UTF-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String payload = sb.toString();

			System.out.print("payload - " + payload);

			JSONObject jsonPayload = JSON.parseObject(payload);

			String phone = jsonPayload.getString("phone");

			PushPayload pushpayloadIOS = PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.tag(phone))
					.setNotification(Notification.newBuilder()
					.addPlatformNotification(IosNotification.newBuilder()
							.setAlert(jsonPayload.getString("door_no") + "呼叫！")
							.setBadge(1).setSound("lzdoor.caf")							
							.addExtra("door_no", jsonPayload.getString("door_no"))
							.addExtra("call_timeout", jsonPayload.getString("call_timeout"))
							.addExtra("sip_group_num", jsonPayload.getString("sip_group_num"))
							.addExtra("sip_num_door", jsonPayload.getString("sip_num_door"))
							.addExtra("sip_num_app", jsonPayload.getString("sip_num_app"))
							.addExtra("id", jsonPayload.getString("id")).build())
					.build())
					.setOptions(Options.newBuilder()
							.setApnsProduction(true).build())
					.build();
			
			PushPayload pushpayloadAND = PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.tag(phone))
					.setNotification(Notification.newBuilder()
					.addPlatformNotification(AndroidNotification.newBuilder()
							.setAlert(jsonPayload.getString("door_no") + "呼叫！")
							.addExtra("door_no", jsonPayload.getString("door_no"))
							.addExtra("call_timeout", jsonPayload.getString("call_timeout"))
							.addExtra("sip_group_num", jsonPayload.getString("sip_group_num"))
							.addExtra("sip_num_door", jsonPayload.getString("sip_num_door"))
							.addExtra("sip_num_app", jsonPayload.getString("sip_num_app"))
							.addExtra("id", jsonPayload.getString("id"))
							.build())
					.build())
					.build();
			

			
			// 验证是否是联掌门户发起的请求
			if (SignUtil.checkSign(url, app_id, timestamp, payload, sign)) {
				// TODO 业务处理
				JPushClient jpushClient = new JPushClient("732eb054d914943bf649e53b", "adefe2d0ff4bc1092590296b");

				jpushClient.sendPush(pushpayloadIOS);
				jpushClient.sendPush(pushpayloadAND);

				// 组装响应消息
				resultMap.put("code", 10000);
				resultMap.put("message", "操作成功");
			} else {
				resultMap.put("code", -10000);
				resultMap.put("message", "操作失败");
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.println(JSON.toJSONString(resultMap));
			out.close();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/jdry/api/Notify";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
