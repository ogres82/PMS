package com.jdry.pms.bbs.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.bbs.pojo.BbsImgUrl;
import com.jdry.pms.bbs.pojo.BbsReply;
import com.jdry.pms.bbs.pojo.BbsTopic;
import com.jdry.pms.bbs.pojo.BbsUser;
import com.jdry.pms.bbs.pojo.VBbsReply;
import com.jdry.pms.bbs.pojo.VBbsTopic;
@Repository
public interface BbsService {
	
	public void addTopic(BbsTopic bbsTopic);
	public void addImg(BbsImgUrl imgUrl);
	public void addReply(BbsReply bbsReply);
	public List<VBbsTopic> loadTopicList(Map<String,Object> parameter);
	public List<BbsImgUrl> queryImgById(String topicId);
	public List<VBbsReply> loadReplyById(String topicId);
	public BbsTopic getTopicById(String topicId);
	public void addZanById(String topicId);
	public void addViewById(String topicId);
	public void setTopicTop(String topicId);
	public void setTopicElite(String topicId);
	public void delTopic(String topicId);
	public int getCommentCount(String topicId);
	public void cancelTop(String topicId);
	public void canceElite(String topicId);
	public void addBbsUser(BbsUser bbsUser);
	public BbsUser getBbsUserByPhone(String phone);
	public VBbsTopic refreshTopic(String topicId);
}
