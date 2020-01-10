package com.jdry.pms.bbs.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_bbs_reply")
public class BbsReply {

	private String topicId;
	private String replyId;
	private String replyToId;
	private String replyUserId;
	private String replyToUserId;
	private Date replyTime;
	private String replyContent;
	
	
	@Column(name = "topic_id")
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	@Id
	@Column(name = "reply_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	@Column(name = "reply_user_id")
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	@Column(name = "reply_to_user_id")
	public String getReplyToUserId() {
		return replyToUserId;
	}
	public void setReplyToUserId(String replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	@Column(name = "reply_time")
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	@Column(name = "reply_content")
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	@Column(name = "reply_to_id")
	public String getReplyToId() {
		return replyToId;
	}
	public void setReplyToId(String replyToId) {
		this.replyToId = replyToId;
	}
	
	
	
	
}
