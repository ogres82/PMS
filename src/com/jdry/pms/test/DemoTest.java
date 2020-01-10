package com.jdry.pms.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jdry.pms.comm.util.HttpConnectionUtil;
import com.jdry.pms.comm.util.SignUtil;

public class DemoTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		Gson GSON = new Gson();
		// 组装正文
		Map<String, Object> body = new HashMap<String, Object>();
		//body.put("name","01栋");
		body.put("door_no","乐湾单元测试机");
		body.put("call_timeout","15654511");
		body.put("door_no","乐湾单元测试机");
		body.put("sip_group_num","999908600100628432");
		body.put("sip_num_door","999908600100628432");
		body.put("sip_num_app","乐湾单元机");
		body.put("id","111111");
		body.put("phone","13985003206");		
		
	

		// 正文转Json
		String payload = GSON.toJson(body);
		// 服务地址
		//String domain = "http://lwgj.gzjdry.com:8080/PMS";
		// 服务地址
		String domain = "http://localhost:8080/PMS";
		// 接口URI
		String uri = "/jdry/api/Notify.app";
		// 接口URL
		String url = domain + uri;
		// 接入方app_id
		String app_id = "d9c57ea0e1f44af1b6cbd919847cab9a";
		// 接入方app_secret
		String app_secret = "7dd20231d01a459381a0c9891f24c328";

		// 时间戳
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
	
		// 计算签名
		String sign = SignUtil.getSign(url, app_id, app_secret, timestamp, payload);
		
		
		// 组装request headers
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("app_id", app_id);
		headers.put("timestamp", timestamp);
		headers.put("sign", sign);
		System.out.println(JSON.toJSON(headers));
		// 发起请求
		String requestResult = HttpConnectionUtil.doPost(url, headers, payload);
	
		System.out.println(requestResult);
	}

}
