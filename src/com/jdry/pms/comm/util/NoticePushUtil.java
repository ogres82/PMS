
package com.jdry.pms.comm.util;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * Copyright 2017 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2017-2-22 下午4:54:51
 * @description
 *
 */
public class NoticePushUtil {
	private static final String appKey1 = "adefe2d0ff4bc1092590296b";    //业主端appkey
	private static final String masterSecret1 = "732eb054d914943bf649e53b";  //业主端masterSecret
	
	private static final String appKey2 = "cc1afb2c123ca2c3b3e674f7";    //物管端appkey
	private static final String masterSecret2 = "743068af08cde622f8ca06fd";  //物管端masterSecret
	static Logger LOG = Logger.getLogger(NoticePushUtil.class);
	
	/**
	 * 通知推送
	 * @param tagName  推送设备标识：app登录名
	 * @param content  推送内容
	 * @param type  app端标识 01业主端02物管端
	 */
	public static void pushNotice(String tagName,String content,String type){
		String appKey = null;
		String masterSecret = null;
		if(type.equals("01")){
			appKey = appKey1;
			masterSecret = masterSecret1;
		}
		if(type.equals("02")){
			appKey = appKey2;
			masterSecret = masterSecret2;
		}
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		PushPayload payload = buildPushObject_all_tag_alert(tagName,content);
		try {
	        PushResult result = jpushClient.sendPush(payload);
	        LOG.info("Got result - " + result);

	    } catch (APIConnectionException e) {
	        // Connection error, should retry later
	        LOG.error("Connection error, should retry later", e);

	    } catch (APIRequestException e) {
	        // Should review the error, and fix the request
	        LOG.error("Should review the error, and fix the request", e);
	        LOG.info("HTTP Status: " + e.getStatus());
	        LOG.info("Error Code: " + e.getErrorCode());
	        LOG.info("Error Message: " + e.getErrorMessage());
	    }
	}
	
	/**
	 * 通知推送
	 * @param aliasName  推送设备标识：alias分组
	 * @param content  推送内容
	 * @param type  app端标识 01业主端02物管端
	 */
	public static void pushNoticeByAlias(String aliasName,String content,String type){
		String appKey = null;
		String masterSecret = null;
		if(type.equals("01")){
			appKey = appKey1;
			masterSecret = masterSecret1;
		}
		if(type.equals("02")){
			appKey = appKey2;
			masterSecret = masterSecret2;
		}
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		PushPayload payload = buildPushObject_all_alias_alert(aliasName,content);
		try {
	        PushResult result = jpushClient.sendPush(payload);
	        LOG.info("Got result - " + result);

	    } catch (APIConnectionException e) {
	        // Connection error, should retry later
	        LOG.error("Connection error, should retry later", e);

	    } catch (APIRequestException e) {
	        // Should review the error, and fix the request
	        LOG.error("Should review the error, and fix the request", e);
	        LOG.info("HTTP Status: " + e.getStatus());
	        LOG.info("Error Code: " + e.getErrorCode());
	        LOG.info("Error Message: " + e.getErrorMessage());
	    }
	}
	
	//以alias为标签推送
	private static PushPayload buildPushObject_all_alias_alert(String aliasName,String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(aliasName))
                .setNotification(Notification.alert(content))
                .build();
    }
	//以tag为标签推送
	private static PushPayload buildPushObject_all_tag_alert(String tagName,String content) {
		Options options = Options.sendno();
		options.setApnsProduction(true);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagName))
                .setNotification(Notification.alert(content))
                .setOptions(options)
                .build();
    }
	

	
	//推送所有
	private static PushPayload buildPushObject_all_all_alert(String content) {
        return PushPayload.alertAll(content);
    }
}
