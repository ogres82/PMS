package com.jdry.pms.bbs.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "v_bbs_reply")
public class VBbsReply {

	private String topicId;
	private String replyId;
	private String replyToId;
	private String replyUserId;
	private String replyUserNickname;
	private String replyToUserId;
	private String replyToUserNickname;
	private Date replyTime;
	private String replyContent;

	private String replyAddrs;
	private String replyToAddrs;

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

	@Column(name = "reply_user_nickname")
	public String getReplyUserNickname() {
		return replyUserNickname;
	}

	public void setReplyUserNickname(String replyUserNickname) {
		this.replyUserNickname = replyUserNickname;
	}

	@Column(name = "reply_to_user_nickname")
	public String getReplyToUserNickname() {
		return replyToUserNickname;
	}

	public void setReplyToUserNickname(String replyToUserNickname) {
		this.replyToUserNickname = replyToUserNickname;
	}

	@Formula(value = "(SELECT IFNULL(CONCAT_WS('-',a.community_name,a.storied_build_name,a.room_no),'')  FROM v_house_vs_owner a WHERE a.owner_id = reply_user_id )")
	public String getReplyAddrs() {
		return replyAddrs;
	}

	public void setReplyAddrs(String replyAddrs) {
		this.replyAddrs = replyAddrs;
	}

	@Formula(value = "(SELECT IFNULL(CONCAT_WS('-',a.community_name,a.storied_build_name,a.room_no),'')  FROM v_house_vs_owner a WHERE a.owner_id = reply_to_user_id )")
	public String getReplyToAddrs() {
		return replyToAddrs;
	}

	public void setReplyToAddrs(String replyToAddrs) {
		this.replyToAddrs = replyToAddrs;
	}

}
