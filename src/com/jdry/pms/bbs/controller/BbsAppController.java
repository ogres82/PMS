package com.jdry.pms.bbs.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdry.pms.bbs.pojo.BbsImgUrl;
import com.jdry.pms.bbs.pojo.BbsReply;
import com.jdry.pms.bbs.pojo.BbsTopic;
import com.jdry.pms.bbs.pojo.VBbsReply;
import com.jdry.pms.bbs.pojo.VBbsTopic;
import com.jdry.pms.bbs.service.BbsService;
import com.jdry.pms.comm.util.SpringUtil;

@Repository
@Component
public class BbsAppController{

	@Resource
	BbsService service;
	/**
	 * 发帖
	 * @param data
	 * @return
	 */
	public String addTopic(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicTitle = null == obj.getString("topicTitle")?"" : obj.getString("topicTitle");
		String topicContent = null == obj.getString("topicContent")?"" : obj.getString("topicContent");
		String topicUserId = null == obj.getString("topicUserId")?"" : obj.getString("topicUserId");
		String topicPostMark = null == obj.getString("topicPostMark")?"" : obj.getString("topicPostMark");
		Date topicPostTime = new Date();
		BbsTopic bt = new BbsTopic();
		bt.setTopicTitle(topicTitle);
		bt.setTopicContent(topicContent);
		bt.setTopicUserId(topicUserId);
		bt.setTopicPostTime(topicPostTime);
		bt.setLastUpdateTime(topicPostTime);
		bt.setTopicPostMark(topicPostMark);
		bt.setTopicDelMark("0");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.addTopic(bt);
		String topicId = bt.getTopicId();
		String imgUrl = null == obj.getString("imgUrl")?"" : obj.getString("imgUrl");
		if(!imgUrl.isEmpty()){
			JSONArray urls = JSON.parseArray(imgUrl);
			for(int i=0;i<urls.size();i++)
	        {   BbsImgUrl img=new BbsImgUrl();
	        	img.setTopicId(topicId);
	        	img.setImgUrl(urls.get(i)+"");
	        	service.addImg(img);
	        }
		}
		return "{\"status\":\"1\",\"message\":\"发帖成功！\"}";
	}
	/**
	 * 评论帖子
	 * @param data
	 * @return
	 */
	public String addReply(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		String replyToId = null == obj.getString("replyToId")?"" : obj.getString("replyToId");
		String replyUserId = null == obj.getString("replyUserId")?"" : obj.getString("replyUserId");
		String replyToUserId = null == obj.getString("replyToUserId")?"" : obj.getString("replyToUserId");
		String replyContent = null == obj.getString("replyContent")?"" : obj.getString("replyContent");
		Date replyTime = new Date();
		BbsReply br = new BbsReply();
		br.setReplyContent(replyContent);
		br.setReplyTime(replyTime);
		br.setReplyToUserId(replyToUserId);
		br.setReplyUserId(replyUserId);
		br.setTopicId(topicId);
		br.setReplyToId(replyToId);
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.addReply(br);
		BbsTopic bt = service.getTopicById(topicId);
		bt.setLastUpdateTime(replyTime);
		service.addTopic(bt);
		return "{\"status\":\"1\",\"message\":\"评论成功！\"}";
	}
	/**
	 * 加载帖子列表
	 * @param data
	 * @return
	 */
	public String loadTopicList(String data){
		System.out.println(data);
		JSONObject obj = JSON.parseObject(data);
		String pageStr = null == obj.getString("page")?"" : obj.getString("page");
		String ownerId = null == obj.getString("ownerId")?"" : obj.getString("ownerId");
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("page", pageStr);
		parameter.put("ownerId", ownerId);
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		System.out.println("开始："+System.currentTimeMillis());
		List<VBbsTopic> topics = service.loadTopicList(parameter);
		if(topics.size()==0){
			return "{\"status\":\"1\",\"message\":\"暂无帖子\",\"data\":[]}";
		}else{
			List list = new ArrayList();
			for(int i=0;i<topics.size();i++){
				String topicId = topics.get(i).getTopicId();
				Map map = new HashMap();
				String imgUrl = null == topics.get(i).getImgUrl()?"":topics.get(i).getImgUrl();
				map.put("imgUrl", imgUrl);
				map.put("topicId", topics.get(i).getTopicId());
				map.put("userNickname", topics.get(i).getUserNickname());
				map.put("mappingUserId", topics.get(i).getMappingUserId());
				map.put("ownerHeadImg", topics.get(i).getOwnerHeadImg());
				map.put("topicPostTime", topics.get(i).getTopicPostTime());
				map.put("topicTitle", topics.get(i).getTopicTitle());
				map.put("topicContent", topics.get(i).getTopicContent());
				map.put("topicTop", topics.get(i).getTopicTop());
				map.put("topicElite", topics.get(i).getTopicElite());
				map.put("topicViewTimes", topics.get(i).getTopicViewTimes());
				map.put("topicCommentTimes", service.getCommentCount(topicId));
				map.put("topicZanTimes", topics.get(i).getTopicZanTimes());
				map.put("topicDelMark", topics.get(i).getTopicDelMark());
				map.put("topicPostMark", topics.get(i).getTopicPostMark());
				list.add(map);
			}
			String jsonString = JSON.toJSONString(list,SerializerFeature.WriteMapNullValue);
			System.out.println("完成："+System.currentTimeMillis());
			return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
		}
	}
	
	
	public String refreshTopic(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		VBbsTopic topic = service.refreshTopic(topicId);
		Map map = new HashMap();
		map.put("topicId", topic.getTopicId());
		map.put("topicViewTimes", topic.getTopicViewTimes());
		map.put("topicTop", topic.getTopicTop());
		map.put("topicElite", topic.getTopicElite());
		map.put("topicCommentTimes", service.getCommentCount(topicId));
		map.put("topicZanTimes", topic.getTopicZanTimes());
		map.put("topicDelMark", topic.getTopicDelMark());
		String jsonString = JSON.toJSONString(map);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}               
	
	
	/**
	 * 加载帖子评论
	 * @param data
	 * @return
	 */
	public String loadReply(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		List<VBbsReply> list = service.loadReplyById(topicId);
		if(list.size() == 0){
			return "{\"status\":\"1\",\"message\":\"暂无评论\",\"data\":[]}";
		}else{
			String jsonString = JSON.toJSONString(list);
			return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
		}
	}
	
	/**
	 * 帖子点赞
	 */
	public String addZan(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.addZanById(topicId);
		return "{\"status\":\"1\",\"message\":\"点赞成功！\",\"data\":\"\"}";
	}
	
	/**
	 * 帖子浏览
	 */
	public String addView(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.addZanById(topicId);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":\"\"}";
	}
	
	/**
	 * 帖子置顶
	 */
	public String makeTop(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.setTopicTop(topicId);
		return "{\"status\":\"1\",\"message\":\"置顶成功！\",\"data\":\"\"}";
	}
	/**
	 * 取消帖子置顶
	 */
	
	public String cancelTop(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.cancelTop(topicId);
		return "{\"status\":\"1\",\"message\":\"取消置顶成功！\",\"data\":\"\"}";
	}
	
	/**
	 * 帖子加精
	 */
	public String makeElite(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.setTopicElite(topicId);
		return "{\"status\":\"1\",\"message\":\"加精成功！\",\"data\":\"\"}";
	}
	
	/**
	 * 取消加精
	 */
	public String cancelElite(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.canceElite(topicId);
		return "{\"status\":\"1\",\"message\":\"取消加精成功！\",\"data\":\"\"}";
	}
	
	/**
	 * 删除帖子
	 */
	public String delTopic(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String topicId = null == obj.getString("topicId")?"" : obj.getString("topicId");
		if(service == null){
			service = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
		}
		service.delTopic(topicId);
		return "{\"status\":\"1\",\"message\":\"删除成功！\",\"data\":\"\"}";
	}
}
