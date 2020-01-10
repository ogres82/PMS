package com.jdry.pms.bbs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.bbs.dao.BbsDao;
import com.jdry.pms.bbs.pojo.BbsImgUrl;
import com.jdry.pms.bbs.pojo.BbsReply;
import com.jdry.pms.bbs.pojo.BbsTopic;
import com.jdry.pms.bbs.pojo.BbsUser;
import com.jdry.pms.bbs.pojo.VBbsReply;
import com.jdry.pms.bbs.pojo.VBbsTopic;
import com.jdry.pms.bbs.service.BbsService;
@Repository
@Component
public class BbsServiceImpl implements BbsService{
	@Resource
	BbsDao dao;
	@Override
	public void addTopic(BbsTopic bbsTopic) {
		// TODO Auto-generated method stub
		dao.addTopic(bbsTopic);
	}
	@Override
	public void addImg(BbsImgUrl imgUrl) {
		// TODO Auto-generated method stub
		dao.addImg(imgUrl);
	}
	@Override
	public void addReply(BbsReply bbsReply) {
		// TODO Auto-generated method stub
		dao.addReply(bbsReply);
	}
	@Override
	public List<VBbsTopic> loadTopicList(Map<String,Object> parameter) {
		// TODO Auto-generated method stub
		return dao.loadTopicList(parameter);
	}
	@Override
	public List<BbsImgUrl> queryImgById(String topicId) {
		// TODO Auto-generated method stub
		return dao.queryImgById(topicId);
	}
	@Override
	public List<VBbsReply> loadReplyById(String topicId) {
		// TODO Auto-generated method stub
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicViewTimes(bt.getTopicViewTimes()+1);
		dao.addTopic(bt);
		return dao.loadReplyById(topicId);
	}
	@Override
	public BbsTopic getTopicById(String topicId) {
		// TODO Auto-generated method stub

		return dao.getTopicById(topicId);
	}
	@Override
	public void addZanById(String topicId) {
		// TODO Auto-generated method stub
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicZanTimes(bt.getTopicZanTimes()+1);
		dao.addTopic(bt);
		
	}
	@Override
	public void addViewById(String topicId) {
		// TODO Auto-generated method stub
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicViewTimes(bt.getTopicViewTimes()+1);
		dao.addTopic(bt);
	}
	@Override
	public void setTopicTop(String topicId) {
		// TODO Auto-generated method stub
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicTop("1");
		dao.addTopic(bt);
	}
	@Override
	public void setTopicElite(String topicId) {
		// TODO Auto-generated method stub
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicElite("1");
		dao.addTopic(bt);
	}
	@Override
	public void delTopic(String topicId) {
		// TODO Auto-generated method stub
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicDelMark("1");
		dao.addTopic(bt);
	}
	@Override
	public int getCommentCount(String topicId) {
		// TODO Auto-generated method stub
		return dao.getCommentCount(topicId);
	}
	@Override
	public void cancelTop(String topicId){
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicTop("0");
		dao.addTopic(bt);
	}
	@Override
	public void canceElite(String topicId){
		BbsTopic bt = this.getTopicById(topicId);
		bt.setTopicElite("0");
		dao.addTopic(bt);
	}
	@Override
	public void addBbsUser(BbsUser bbsUser) {
		// TODO Auto-generated method stub
		dao.addBbsUser(bbsUser);
	}
	@Override
	public VBbsTopic refreshTopic(String topicId) {
		// TODO Auto-generated method stub
		return dao.refreshTopic(topicId);
	}
	@Override
	public BbsUser getBbsUserByPhone(String phone) {
		
		return dao.getBbsUserByPhone(phone);
	}
}
