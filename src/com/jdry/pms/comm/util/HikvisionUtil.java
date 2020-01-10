/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-17 上午12:40:29
 * @description
 *
 */

package com.jdry.pms.comm.util;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;

/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-17 上午12:40:29
 * @description 生成Hikvision GET请求所需url及参数
 * @param method 方法名 如/pms/getPlatParkInfo
 * 		  param 请求参数串 如parkingIndexCodes=
 * 		  mark 标识 0 返回url 1返回完整参数串
 */
public class HikvisionUtil {
	public final static String buildGetUrl(String method,String param,String mark){
		Properties prop = new Properties();
		try {
			prop = FileUtil.getProperties("hikvision.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ip = prop.getProperty("ip");
		String appkey = prop.getProperty("appkey");
		String secret = prop.getProperty("secret");
		String uri = prop.getProperty("uri");
		String url = ip + uri + method;
		if("0".equals(mark)){
			return url;
		}
		String time = Long.toString(new Date().getTime());
		String urlStr = uri + method + param + "&appkey=" + appkey + "&time=" + time;
		System.out.println("------->token----"+urlStr);
		String token = MD5Util.MD5(urlStr+secret);
		if("1".equals(mark)){
			return param + "&appkey=" + appkey + "&time=" + time + "&token=" + token;
		}
		return "";
	}
	
	/**
	 * Copyright 2016 JDRY Co.Ltd
	 * All rights reserved.
	 * @Author 徐磊
	 * @Created on 2016-12-17 上午12:40:29
	 * @description 生成Hikvision POST请求所需url及参数
	 * @param method 方法名 如'/pms/reChargePlatCar'
	 * 		  param 请求参数JSON串 如'{'name':'xulei','age':'20'}'
	 * 		  
	 */
	
	public final static String buildPostUrl(String method,Map<String,Object> param){
		Properties prop = new Properties();
		try {
			prop = FileUtil.getProperties("hikvision.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ip = prop.getProperty("ip");
		String appkey = prop.getProperty("appkey");
		String secret = prop.getProperty("secret");
		String uri = prop.getProperty("uri");
		String time = Long.toString(new Date().getTime());
		param.put("appkey", appkey);
		param.put("time", time);
		String paramString = JSON.toJSONString(param);
		
		String urlStr = uri + method + paramString + secret;
		String token = MD5Util.MD5(urlStr);
		String url = ip + uri + method + "?token=" + token;
		System.out.println("url-----------------"+url);
		System.out.println("dataString-----------------"+paramString);
		return url;
	}
}
