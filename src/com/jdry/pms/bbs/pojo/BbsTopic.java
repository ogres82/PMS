package com.jdry.pms.bbs.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_bbs_topic")
public class BbsTopic {

	private String topicId;
	private String topicUserId;
	private Date topicPostTime;
	private String topicTitle;
	private String topicContent;
	private String topicPostMark;
	private String topicTop;
	private String topicElite;
	private int topicViewTimes;
	private int topicCommentTimes;
	private int topicZanTimes;
	private String topicDelMark;
	private Date lastUpdateTime;

	@Id
	@Column(name = "topic_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	@Column(name = "topic_user_id")
	public String getTopicUserId() {
		return topicUserId;
	}

	public void setTopicUserId(String topicUserId) {
		this.topicUserId = topicUserId;
	}

	@Column(name = "topic_post_time")
	public Date getTopicPostTime() {
		return topicPostTime;
	}

	public void setTopicPostTime(Date topicPostTime) {
		this.topicPostTime = topicPostTime;
	}

	@Column(name = "topic_title")
	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	@Column(name = "topic_content")
	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	@Column(name = "topic_top")
	public String getTopicTop() {
		return topicTop;
	}

	public void setTopicTop(String topicTop) {
		this.topicTop = topicTop;
	}

	@Column(name = "topic_view_times")
	public int getTopicViewTimes() {
		return topicViewTimes;
	}

	public void setTopicViewTimes(int topicViewTimes) {
		this.topicViewTimes = topicViewTimes;
	}

	@Column(name = "topic_comment_times")
	public int getTopicCommentTimes() {
		return topicCommentTimes;
	}

	public void setTopicCommentTimes(int topicCommentTimes) {
		this.topicCommentTimes = topicCommentTimes;
	}

	@Column(name = "topic_zan_times")
	public int getTopicZanTimes() {
		return topicZanTimes;
	}

	public void setTopicZanTimes(int topicZanTimes) {
		this.topicZanTimes = topicZanTimes;
	}

	@Column(name = "topic_del_mark")
	public String getTopicDelMark() {
		return topicDelMark;
	}

	public void setTopicDelMark(String topicDelMark) {
		this.topicDelMark = topicDelMark;
	}

	@Column(name = "topic_elite")
	public String getTopicElite() {
		return topicElite;
	}

	public void setTopicElite(String topicElite) {
		this.topicElite = topicElite;
	}

	@Column(name = "last_update_time")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "topic_post_mark")
	public String getTopicPostMark() {
		return topicPostMark;
	}

	public void setTopicPostMark(String topicPostMark) {
		this.topicPostMark = topicPostMark;
	}

}
