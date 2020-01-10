package com.jdry.pms.bbs.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.bbs.dao.TopicDao;
import com.jdry.pms.bbs.pojo.VBbsReply;
import com.jdry.pms.bbs.pojo.BbsReply;
import com.jdry.pms.bbs.pojo.BbsTopic;
import com.jdry.pms.bbs.pojo.VBbsTopic;
import com.jdry.pms.bbs.service.TopicService;

@Repository
@Component
public class TopicServiceImpl implements TopicService {
	@Resource
	TopicDao dao;

	@Override
	public String loadTopicList(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数
		int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
		if (currentPage != 0) {// 获取页数
			currentPage = currentPage / showCount;
		}
		currentPage += 1;
		Page<VBbsTopic> page = new Page<VBbsTopic>(showCount, currentPage);
		Map<String, Object> parameter = new HashMap<String, Object>();
		try {
			dao.loadTopicListByParam(page, parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JSON.toJSONString("查询失败！");
		}
		List<VBbsTopic> list = (List<VBbsTopic>) page.getEntities();

		long count = page.getEntityCount();
		String jsonString = JSON.toJSONString(list);
		String result = "{\"total\":" + count + ",\"rows\":" + jsonString + "}";
		return result;
	}

	@Override
	public String loadTopic(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setTopicViewTimes(topic.getTopicViewTimes() + 1);
		dao.saveTopic(topic);
		VBbsTopic vtopic = dao.loadTopic(topicId);
		String result = JSON.toJSONString(vtopic);
		return result;
	}

	@Override
	public String loadReply(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		List<VBbsReply> list = dao.loadReply(topicId);
		String result = JSON.toJSONString(list);
		return result;
	}

	@Override
	public VBbsReply addReply(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		String replyUserId = null == arg0.getParameter("replyUserId") ? "" : arg0.getParameter("replyUserId").toString();
		String replyToUserId = null == arg0.getParameter("replyToUserId") ? "" : arg0.getParameter("replyToUserId").toString();
		String replyContent = null == arg0.getParameter("replyContent") ? "" : arg0.getParameter("replyContent").toString();
		Date replyTime = new Date();
		BbsReply reply = new BbsReply();
		reply.setReplyContent(replyContent);
		reply.setReplyTime(replyTime);
		if (!replyToUserId.isEmpty()) {
			reply.setReplyToUserId(replyToUserId);
		}
		reply.setReplyUserId(replyUserId);
		reply.setTopicId(topicId);
		dao.addReply(reply);
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setLastUpdateTime(replyTime);
		dao.saveTopic(topic);
		return dao.loadReplyById(reply.getReplyId());
	}


	@Override
	public String setElite(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setTopicElite("1");
		dao.saveTopic(topic);
		return "success";
	}

	@Override
	public String setTop(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setTopicTop("1");
		dao.saveTopic(topic);
		return "success";
	}

	@Override
	public String saveTopic(HttpServletRequest arg0) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String topicTitle = null == arg0.getParameter("topicTitle") ? "" : arg0.getParameter("topicTitle").toString();
		String topicContent = null == arg0.getParameter("topicContent") ? "" : arg0.getParameter("topicContent").toString();
		String topicUserId = null == arg0.getParameter("topicUserId") ? "" : arg0.getParameter("topicUserId").toString();
		String imgUrls = null == arg0.getParameter("imgUrls") ? "" : arg0.getParameter("imgUrls").toString();
		Date date = new Date();
		BbsTopic topic = new BbsTopic();
		topic.setTopicPostTime(sdf.parse(sdf.format(date)));
		topic.setLastUpdateTime(sdf.parse(sdf.format(date)));
		topic.setTopicContent(topicContent);
		topic.setTopicTitle(topicTitle);
		topic.setTopicDelMark("0");
		topic.setTopicTop("0");
		topic.setTopicElite("0");
		topic.setTopicUserId(topicUserId);
		// topic.setImgUrls(imgUrls);
		dao.saveTopic(topic);
		return "sucess";
	}

	@Override
	public String cancelElite(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setTopicElite("0");
		dao.saveTopic(topic);
		return "success";
	}

	@Override
	public String cancelTop(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setTopicTop("0");
		dao.saveTopic(topic);
		return "sucess";
	}

	@Override
	public String delete(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String topicId = null == arg0.getParameter("topicId") ? "" : arg0.getParameter("topicId").toString();
		BbsTopic topic = dao.queryTopic(topicId);
		topic.setTopicDelMark("1");
		dao.saveTopic(topic);
		return "sucess";
	}

	@Override
	public String delComment(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String replyId = null == arg0.getParameter("replyId") ? "" : arg0.getParameter("replyId").toString();
		dao.delComment(replyId);
		return "sucess";
	}
}
