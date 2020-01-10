package com.jdry.pms.topTask.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.topTask.service.TopTaskService;

/**
 * 描述：物管端APP——代办
 * @author hezuping
 * 2016年6月6日23:30:56
 */
@Repository
@Component
public class TopTaskInterface 
{
	 @Resource
	 TopTaskService topTaskService;
	/**
	 * 通过物管登录账号统计代办个数
	 * @param JsonUserId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTopTaskCount(String JsonUserId)
	{
		 String status="";
		 String message="";
		if(topTaskService == null)
		 {
			topTaskService = (TopTaskService) SpringUtil.getObjectFromApplication("topTaskServiceImpl");
		 }
		 Map map = new HashMap();
		 JSONObject dispatch_handle= (JSONObject) JSON.parse(JsonUserId);
		 String userId=(String) dispatch_handle.get("handler_id");
		 int count =topTaskService.getTaskCountByUserId(userId);
		if(count<=0)
		{
			 status="1";
			 message="无相关数据";
		}else
		{
			 status="1";
			 message="成功";
		}
		map.put("status", status);
		map.put("message",message);
		map.put("count",count);
		String jsonString = JSON.toJSONString(map);
	    return jsonString;
	}
	/**
	 * 根据登录用户统计出有多少代办信息
	 * @param JsonUserId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getTopTaskInfoList(String JsonUserId)
	{
		
		String status="";
		String message="";
		if(topTaskService == null)
		 {
			topTaskService = (TopTaskService) SpringUtil.getObjectFromApplication("topTaskServiceImpl");
		 }
		JSONObject dispatch_handle= (JSONObject) JSON.parse(JsonUserId);
	    String userId=(String) dispatch_handle.get("handler_id");
		List res=topTaskService.queryTopTaskInfoByUserId(userId);
		Map mp = new HashMap();
		List ls=new ArrayList();
		if(res.size()<1)
	    {
		    	 status="1";
				 message="无相关数据";
	   }else{
		    for(int i=0,len=res.size();i<len;i++)
			{
		    Map map = new HashMap();
			Object[] obj = (Object[])res.get(i);
			String rpt_id = null == obj[0]?"":obj[0].toString();
			map.put("rpt_id", rpt_id);
				
			String accepttime = null == obj[1]?"":obj[1].toString();
			
			if(!accepttime.equals("")){
				 map.put("accept_time", getSimp(accepttime));
			}else
			{
					map.put("accept_time", accepttime);	
			}
			String creater = null == obj[2]?"":obj[2].toString();
			map.put("creater", creater);
			String address = null == obj[3]?"":obj[3].toString();
			map.put("address", address);	
			String dispatch_expense = null == obj[4]?"":obj[4].toString();
			map.put("dispatch_expense", dispatch_expense);	
			String oper_id = null == obj[5]?"":obj[5].toString();
			map.put("oper_id", oper_id);
			String event_state = null == obj[6]?"":obj[6].toString();
			map.put("event_state", event_state);
			
			String type_name = null == obj[7]?"":obj[7].toString();
			map.put("type_name", type_name);
			String event_content = null == obj[8]?"":obj[8].toString();
			map.put("event_content", event_content);
			ls.add(map);
			 }
		     status="1";
			 message="成功";
		    }
		    mp.put("status", status);
		    mp.put("message",message);
		    mp.put("data", ls);
		    String jsonString = JSON.toJSONString(mp);
	    return jsonString;	
	}
	
	public  String getSimp(String time)
	{
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sd.parse(time);
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		String time1=sd.format(d);
		return time1;
	}
	
}
