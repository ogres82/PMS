package com.jdry.pms.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.jdry.pms.comm.util.LzmhUtil;

public class Demo {
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		/**
		 * 模拟调用开门接口		
		 **/
		// 接口URI 
		///{"code":10000,"message":"操作成功","value":{"openid":"72fb69f67ad04d6303aabecd4a7176f5"}}
		//		{"code":10000,"message":"操作成功","value":{"openid":"aab22aacaa9e40951d1c9b1dc7de9982"}}
		//{"code":10000,"message":"操作成功","value":{"sip_info":"39ZXp2tZY/iqKM6pJcIb10Sf9hvCj1gpP3pD2EXRr07WM/YLSu1VfNgJfKd5UbCRTj2yMxbxRFnU\nw2YCWCRWkcgsEgVR9rtIaw0D/1GQdicPVrAFinyRcRZlDKLw0j9kAMQk6Buiy6Iw8xmgbE2NbDoz\n3gz+5pWbSDubiozxFz8="}}

		String uri = "/api/v1/room/create";
	
		// 组装正文 
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("name","0404");
		body.put("unit_id", 36683);
		//body.put("add_type", 1);// (添加类型 1：户主 2：住户，必填): integer
		//body.put("mobile_phone", "18984811697");// (添加类型 1：户主 2：住户，必填): integer
		//body.put("room_id", 1570737);// (添加类型 1：户主 2：住户，必填): integer
		//body.put("cycle_type", 1);// (添加类型 1：户主 2：住户，必填): integer
		//body.put("time_type", 1);// (添加类型 1：户主 2：住户，必填): integer

		///body.put("openid", "c499be16b4473cfccca48f5756d57c18");// (添加类型 1：户主 2：住户，必填): integer
		
		String jsonObj =LzmhUtil.zlmhPost(uri, body);
		
		
		System.out.println(jsonObj);
		//{"code":10000,"message":"操作成功","value":{"id":654563,"add_type":1,"mobile_phone":"13985003206","room_id":1554315,"cycle_type":1,"cycle_start":"2000-01-01","cycle_end":"4000-01-01","time_type":1,"times":"00:00:00-23:59:59","alias":"13985003206"}}
		//{"code":10000,"message":"操作成功","value":{"id":654567,"add_type":1,"mobile_phone":"13985003206","room_id":1554316,"cycle_type":1,"cycle_start":"2000-01-01","cycle_end":"4000-01-01","time_type":1,"times":"00:00:00-23:59:59","alias":"13985003206"}}

	}

}
