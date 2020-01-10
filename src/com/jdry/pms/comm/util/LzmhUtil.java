package com.jdry.pms.comm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.google.gson.Gson;

public class LzmhUtil {

	public final static String zlmhPost(String uri, Map<String, Object> body) {
		Properties prop = new Properties();
		String requestResult = "";
		try {
			prop = FileUtil.getProperties("lzmh.properties");
			Gson GSON = new Gson();
			// 正文转Json
			String payload = GSON.toJson(body);
			// 服务地址
			String domain = prop.getProperty("domain");
			// 接口URL
			String url = domain + uri;
			// 接入方app_id
			String app_id = prop.getProperty("app_id");
			// 接入方app_secret
			String app_secret = prop.getProperty("app_secret");
			// 时间戳
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
			// 计算签名
			String sign = SignUtil.getSign(uri, app_id, app_secret, timestamp, payload);

			// 组装request headers
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("app_id", app_id);
			headers.put("timestamp", timestamp);
			headers.put("sign", sign);
			// 发起请求
			requestResult = HttpConnectionUtil.doPost(url, headers, payload);

		} catch (Exception e) {
			requestResult = "{{\"code\":-10000,\"message\":\"操作失败\"}}";
			e.printStackTrace();
		}
		return requestResult;
	}
}
