package com.jdry.pms.comm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 联掌门户开放平台签名工具
 * 
 * @author liucq
 * @date 2016年10月19日
 * 
 */
public class SignUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);
	/** 物业管理平台免密登录URI */
	private static String PROPERTY_LOGIN_URI = "/api/v1/property/create";
	/** 接入方app_secret */
	private static String APP_SECRET = "7dd20231d01a459381a0c9891f24c328";
	
	private static String APP_ID = "d9c57ea0e1f44af1b6cbd919847cab9a";	
	
	/**
	 * 根据参数信息，生成签名
	 * 
	 * @param uri
	 *            请求URI或URL(对接双方要一致)
	 * @param app_id
	 *            接入方app_id
	 * @param app_secret
	 *            接入方app_secret
	 * @param timestamp
	 *            时间戳（秒级）
	 * @param payload
	 *            参数内容
	 * @return 签名结果
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String getSign(String uri, String app_id, String app_secret, String timestamp, String payload)
			throws UnsupportedEncodingException {
		String signStr = MessageFormat.format("{0}?app_id={1}&timestamp={2}&payload={3}&app_secret={4}",
				URLEncoder.encode(uri, "UTF-8"), URLEncoder.encode(app_id, "UTF-8"),
				URLEncoder.encode(timestamp, "UTF-8"), URLEncoder.encode(payload, "UTF-8"),
				URLEncoder.encode(app_secret, "UTF-8"));
		//LOGGER.info("signStr = {}", signStr);
		// 计算签名
		return MD5Util.MD5(signStr);
	}

	/**
	 * 签名验证
	 * 
	 * @param uri
	 *            request uri
	 * @param app_id
	 *            接入方app_id
	 * @param timestamp
	 *            时间戳（秒级）
	 * @param payload
	 *            request 正文
	 * @param sign
	 *            request 签名
	 * @return 验证结果 true or false
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkSign(String uri, String app_id, String timestamp, String payload, String sign)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(timestamp)) {
			LOGGER.info("timestamp is null");
			return false;
		} else if (StringUtils.isEmpty(sign)) {
			LOGGER.info("sign is null");
			return false;
		}
		// 验证时间戳
		long thisTime = System.currentTimeMillis() / 1000;
		long times = Long.valueOf(timestamp);
		long diff = thisTime - times;
		LOGGER.info("times = {}, diff = {}, overdue = 1800", times, diff);
		if (diff >= 1800L) {
			LOGGER.info("timestamp is overdue");
			return false;
		}
		// 计算签名
		String mySign = getSign(uri, APP_ID, APP_SECRET, timestamp, payload);
		LOGGER.info("My sign : " + mySign);
		LOGGER.info("Request sign : " + sign);
		// 签名验证结果
		boolean checkResult = false;
		if (mySign.equalsIgnoreCase(sign)) {
			checkResult = true;
		}
		return checkResult;
	}

	/**
	 * 获取物业管理平台免密登录地址(30分钟有效)
	 * 
	 * @param domain
	 *            服务地址（域名）
	 * @param app_id
	 *            接入方app_id
	 * @param app_secret
	 *            接入方app_secret
	 * @param payload
	 *            物业账号
	 * @return 物业管理平台免密登录地址
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String getPropertyWebUrl(String domain, String app_id, String app_secret, String payload)
			throws UnsupportedEncodingException {
		// 获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		// 计算签名
		String sign = getSign(PROPERTY_LOGIN_URI, app_id, app_secret, timestamp, payload);
		// 生成URL
		String url = MessageFormat.format("{0}{1}?app_id={2}&timestamp={3}&payload={4}&sign={5}", domain,
				PROPERTY_LOGIN_URI, app_id, timestamp, payload, sign);
		return url;
	}

}
