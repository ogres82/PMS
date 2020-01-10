package com.jdry.pms.mainFrame.controller;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.model.Message;
import com.bstek.bdf2.core.view.frame.main.register.message.SeeMessage;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.mainFrame.pojo.VMessage;

@Component
public class MsgForApp{

	@Resource
	SeeMessage see;
	
	public String getUnreadMsgNum(String data){
		JSONObject obj = JSON.parseObject(data);
		String username = obj.getString("username");
		if(see == null){
			see = (SeeMessage) SpringUtil.getObjectFromApplication("bdf2.seeMessage");
		}
		if(!username.isEmpty()){
			String sql = "from " + Message.class.getName()
					+ " where receiver='" + username + "' and read = 0";
			List<Message> lists = see
					.query(sql);
			
			String num = lists.size()+"";
			return "{\"status\":\"1\",\"message\":\"获取成功\",\"data\":{\"msgNum\":\""+num+"\"}}";
			
		}else{
			return "{\"status\":\"0\",\"message\":\"未传递参数\"}";
		}
	}
	
	public String getMsgList(String data){
		JSONObject obj = JSON.parseObject(data);
		String username = obj.getString("username");
		if(see == null){
			see = (SeeMessage) SpringUtil.getObjectFromApplication("bdf2.seeMessage");
		}
		if(!username.isEmpty()){
			String sql = "from " + VMessage.class.getName()+ " where receiver ='"+username+"'";
			List<VMessage> lists = see.query(sql);
			String jsonString = JSON.toJSONString(lists);
			
			return "{\"status\":\"1\",\"message\":\"获取成功\",\"data\":"+jsonString+"}";
		}else{
			return "{\"status\":\"0\",\"message\":\"未传递参数\"}";
		}
	}
	
	public String setReadMsg(String data){
		JSONObject obj = JSON.parseObject(data);
		String id = obj.getString("id");
		if(see == null){
			see = (SeeMessage) SpringUtil.getObjectFromApplication("bdf2.seeMessage");
		}
		if(!id.isEmpty()){
			String sql = "from " + Message.class.getName()+ " where id='" + id + "'";
			Session session = see.getSessionFactory().openSession();
			List<Message> lists = see.query(sql);
			if (null != lists && lists.size() > 0) {
				Message msg = lists.get(0);
				msg.setRead(true);
				session.update(msg);
				session.flush();
				session.close();
				return "{\"status\":\"1\",\"message\":\"设置成功\"}";
			}else{
				session.close();
				return "{\"status\":\"0\",\"message\":\"设置失败\"}";
			}
			
		}else{
			return "{\"status\":\"0\",\"message\":\"未传递参数\"}";
		}
	}
	
}
