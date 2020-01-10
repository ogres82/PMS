package com.jdry.pms.bbs.service;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.jdry.pms.bbs.pojo.VBbsReply;

@Repository
public interface TopicService {
	
	public String loadTopicList(HttpServletRequest arg0);
	public String loadTopic(HttpServletRequest arg0);
	public String loadReply(HttpServletRequest arg0);
	public VBbsReply addReply(HttpServletRequest arg0);
	public String setElite(HttpServletRequest arg0);
	public String setTop(HttpServletRequest arg0);
	public String cancelElite(HttpServletRequest arg0);
	public String cancelTop(HttpServletRequest arg0);
	public String delete(HttpServletRequest arg0);
	public String saveTopic(HttpServletRequest arg0) throws ParseException;
	public String delComment(HttpServletRequest arg0);
}
