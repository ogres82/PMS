package com.jdry.pms.bbs.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_bbs_topic_user")
public class VBbsTopic {

	private String topicId;
	private String userNickname;
	private String mappingUserId;
	private String ownerHeadImg;
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
	private String imgUrl;
	
	@Id
	@Column(name = "topic_id")
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	@Column(name = "user_nickname")
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
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
	@Column(name = "last_update_time")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Column(name = "topic_top")
	public String getTopicTop() {
		return topicTop;
	}
	public void setTopicTop(String topicTop) {
		this.topicTop = topicTop;
	}
	@Column(name = "topic_elite")
	public String getTopicElite() {
		return topicElite;
	}
	public void setTopicElite(String topicElite) {
		this.topicElite = topicElite;
	}
	@Column(name = "mapping_user_id")
	public String getMappingUserId() {
		return mappingUserId;
	}
	public void setMappingUserId(String mappingUserId) {
		this.mappingUserId = mappingUserId;
	}
	@Column(name = "img_url")
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Column(name = "owner_head_img")
	public String getOwnerHeadImg() {
		return ownerHeadImg;
	}
	public void setOwnerHeadImg(String ownerHeadImg) {
		this.ownerHeadImg = ownerHeadImg;
	}
	@Column(name = "topic_post_mark")
	public String getTopicPostMark() {
		return topicPostMark;
	}
	public void setTopicPostMark(String topicPostMark) {
		this.topicPostMark = topicPostMark;
	}
	
	
}
