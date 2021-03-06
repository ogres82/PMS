package com.jdry.pms.msgpublic.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushClient {    

	
protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PushClient.class);

// demo App defined in resources/jpush-api.conf 
private static final String appKey ="0b0b6e8f2008d8dcbbb7525a";
private static final String masterSecret = "147474aa7ad206e1cb21a7fd";

public static final String TITLE = "公告测试001";
public static final String ALERT = "测试111--alert";
public static final String MSG_CONTENT = "<br /><br /><span style=\"background-color: yellow;\">斯蒂芬是否abc<br /></span><br />h<br /><br />";
public static final String REGISTRATION_ID = "0900e8d85ef";
public static final String TAG = "tag_api";

public static void main(String[] args) {
//    testSendPushWithCustomConfig();
    //testSendIosAlert();
	testSendPush();
}


public static void sendPush(String title,String alert,Map ext,String alias) {
    // HttpProxy proxy = new HttpProxy("localhost", 3128);
    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
    JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
    try {
		TagAliasResult result = jpushClient.getDeviceTagAlias("120c83f760275531e68");
		System.out.println(result.alias);
	    //PushResult result1=jpushClient.sendAndroidMessageWithAlias(TITLE, MSG_CONTENT, result.alias);
		PushResult result1=jpushClient.sendAndroidNotificationWithAlias(title, alert, ext, result.alias);//(TITLE, MSG_CONTENT, result.alias);
		/*System.out.println(result.tags.toString());
		PushPayload payload=PushPayload.newBuilder()
	            .setPlatform(Platform.android())
	            .setAudience(Audience.tag_and("property"))
	            .setNotification(Notification.newBuilder()
	                    .addPlatformNotification(AndroidNotification.newBuilder()
	                            .setAlert(ALERT)
	                            .setTitle(TITLE)
	                            .build())
	                    .build())
	             .setOptions(Options.newBuilder()
	                     .setApnsProduction(true)
	                     .build())
	             .build();
	    PushResult result1 = jpushClient.sendPush(payload);*/
		
		
		LOG.info(result.alias);
		LOG.info(result.tags.toString());
		
	} catch (APIConnectionException e) {
		LOG.error("Connection error. Should retry later. ", e);
		
	} catch (APIRequestException e) {
		LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
	}
    
    // For push, all you need do is to build PushPayload object.
    //PushPayload payload = buildPushObject_android_tag_alertWithTitle();
    //PushPayload payload =buildPushObject_all_all_alert();
    //testSendWithSMS();
    System.out.println("发送成功了没");
    
    /*try {
        PushResult result = jpushClient.sendPush(payload);
        LOG.info("Got result - " + result);
        
    } catch (APIConnectionException e) {
        LOG.error("Connection error. Should retry later. ", e);
        
    } catch (APIRequestException e) {
        LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
        LOG.info("Msg ID: " + e.getMsgId());
    }*/
}

public static void testSendPush() {
    // HttpProxy proxy = new HttpProxy("localhost", 3128);
    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
    JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
    try {
		TagAliasResult result = jpushClient.getDeviceTagAlias("120c83f760275531e68");
		System.out.println(result.alias);
	    //PushResult result1=jpushClient.sendAndroidMessageWithAlias(TITLE, MSG_CONTENT, result.alias);
		Map ext=new HashMap();
		ext.put("id", "111111");
		PushResult result1=jpushClient.sendAndroidNotificationWithAlias(TITLE, "公告alert", ext, result.alias);//(TITLE, MSG_CONTENT, result.alias);
		/*System.out.println(result.tags.toString());
		PushPayload payload=PushPayload.newBuilder()
	            .setPlatform(Platform.android())
	            .setAudience(Audience.tag_and("property"))
	            .setNotification(Notification.newBuilder()
	                    .addPlatformNotification(AndroidNotification.newBuilder()
	                            .setAlert(ALERT)
	                            .setTitle(TITLE)
	                            .build())
	                    .build())
	             .setOptions(Options.newBuilder()
	                     .setApnsProduction(true)
	                     .build())
	             .build();
	    PushResult result1 = jpushClient.sendPush(payload);*/
		
		
		LOG.info(result.alias);
		LOG.info(result.tags.toString());
		
	} catch (APIConnectionException e) {
		LOG.error("Connection error. Should retry later. ", e);
		
	} catch (APIRequestException e) {
		LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
	}
    
    // For push, all you need do is to build PushPayload object.
    //PushPayload payload = buildPushObject_android_tag_alertWithTitle();
    //PushPayload payload =buildPushObject_all_all_alert();
    //testSendWithSMS();
    System.out.println("发送成功了没");
    
    /*try {
        PushResult result = jpushClient.sendPush(payload);
        LOG.info("Got result - " + result);
        
    } catch (APIConnectionException e) {
        LOG.error("Connection error. Should retry later. ", e);
        
    } catch (APIRequestException e) {
        LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
        LOG.info("Msg ID: " + e.getMsgId());
    }*/
}

public static PushPayload buildPushObject_all_all_alert() {
    return PushPayload.alertAll(ALERT);
}

public static PushPayload buildPushObject_all_alias_alert() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.all())
            .setAudience(Audience.alias("alias1"))
            .setNotification(Notification.alert(ALERT))
            .build();
}

public static PushPayload buildPushObject_android_tag_alertWithTitle() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.android())
            .setAudience(Audience.tag("tag1"))
            .setNotification(Notification.android(ALERT, TITLE, null))
            .build();
    //.setAudience(Audience.tag("tag1"))
}

public static PushPayload buildPushObject_android_and_ios() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.android_ios())
            .setAudience(Audience.tag("tag1"))
            .setNotification(Notification.newBuilder()
            		.setAlert("alert content")
            		.addPlatformNotification(AndroidNotification.newBuilder()
            				.setTitle("Android Title").build())
            		.addPlatformNotification(IosNotification.newBuilder()
            				.incrBadge(1)
            				.addExtra("extra_key", "extra_value").build())
            		.build())
            .build();
}

public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.tag_and("tag1", "tag_all"))
            .setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                            .setAlert(ALERT)
                            .setBadge(5)
                            .setSound("happy")
                            .addExtra("from", "JPush")
                            .build())
                    .build())
             .setMessage(Message.content(MSG_CONTENT))
             .setOptions(Options.newBuilder()
                     .setApnsProduction(true)
                     .build())
             .build();
}

public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.android_ios())
            .setAudience(Audience.newBuilder()
                    .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                    .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                    .build())
            .setMessage(Message.newBuilder()
                    .setMsgContent(MSG_CONTENT)
                    .addExtra("from", "JPush")
                    .build())
            .build();
}

public static void testSendPushWithCustomConfig() {
    ClientConfig config = ClientConfig.getInstance();
    // Setup the custom hostname
    config.setPushHostName("https://api.jpush.cn");

    JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3, null, config);

    // For push, all you need do is to build PushPayload object.
    PushPayload payload = buildPushObject_all_all_alert();

    try {
        PushResult result = jpushClient.sendPush(payload);
        LOG.info("Got result - " + result);

    } catch (APIConnectionException e) {
        LOG.error("Connection error. Should retry later. ", e);

    } catch (APIRequestException e) {
        LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
        LOG.info("Msg ID: " + e.getMsgId());
    }
}

public static void testSendIosAlert() {
    JPushClient jpushClient = new JPushClient(masterSecret, appKey);

    IosAlert alert = IosAlert.newBuilder()
            .setTitleAndBody("test alert", "test ios alert json")
            .setActionLocKey("PLAY")
            .build();
    try {
        PushResult result = jpushClient.sendIosNotificationWithAlias(alert, new HashMap<String, String>(), "alias1");
        LOG.info("Got result - " + result);
    } catch (APIConnectionException e) {
        LOG.error("Connection error. Should retry later. ", e);
    } catch (APIRequestException e) {
        LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
    }
}

public static void testSendWithSMS() {
    JPushClient jpushClient = new JPushClient(masterSecret, appKey);
    try {
        SMS sms = SMS.content("尊敬的业主，祝你生日快乐！", 10);
        PushResult result = jpushClient.sendMessageAll("尊敬的业主，祝你生日快乐！");
        
        LOG.info("Got result - " + result);
    } catch (APIConnectionException e) {
        LOG.error("Connection error. Should retry later. ", e);
    } catch (APIRequestException e) {
        LOG.error("Error response from JPush server. Should review and fix it. ", e);
        LOG.info("HTTP Status: " + e.getStatus());
        LOG.info("Error Code: " + e.getErrorCode());
        LOG.info("Error Message: " + e.getErrorMessage());
    }
}
}
