package com.jdry.pms.assignWork.controller;

import java.io.Serializable;
import java.net.URLEncoder;
import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventSource;
/**
 * 消息推送类
 * @author hezuping
 * 2016年5月19日11:13:18
 */
public class EventMessagePushlet implements EventSource, Serializable {

	  private static final long serialVersionUID = 1L;

	    // 处理结果编码
	    private static String ACKNOWLEDGE = null;
	    // 处理记录
	    private static String RESULT = null; 
	    
	    // 处理人列表
	    private static String ADMIN_LIST = null;
	    // 处理记录
	    private static String DUTY_NOTE = null; 
	    
	    
	    /**
	     * 推送消息
	     * @param dutyNote 
	     * @param adminList 
	     * @param string
	     */
		public void push(String acknowledge, String result, String adminList, String dutyNote) {
			ACKNOWLEDGE = acknowledge;
			RESULT = result;
			ADMIN_LIST = adminList;
			DUTY_NOTE = dutyNote;
			
			passivate();
		}
	    
	    /**
	     * 这个方法在servlet启动的时候会执行
	     */
	    @Override
	    public void activate() {
	        System.out.println("PushEventAcknowledgeService activate");
	    }

	    /**
	     * 执行推送的方法
	     * 
	     */
	    @Override
	    public void passivate() {
	    	
			
	        Event event = Event.createDataEvent("/event/message");
	        try{
	        //URLEncoder.encode(ACKNOWLEDGE,"UTF-8")
	         event.setField("message",  URLEncoder.encode(ACKNOWLEDGE,"UTF-8"));
	         event.setField("content", URLEncoder.encode(RESULT,"UTF-8"));
	        event.setField("handle", URLEncoder.encode(ADMIN_LIST,"UTF-8"));
	      //  event.setField("state", DUTY_NOTE);
			}catch (Exception e) {
				
			}
	        // 推送到页面
	        Dispatcher.getInstance().multicast(event);
	    }

	    /**
	     * 这个方法在servlet关闭的时候会执行
	     */
	    @Override
	    public void stop() {
	        System.out.println("PushEventAcknowledgeService stop");
	    }
	   
	    

}
