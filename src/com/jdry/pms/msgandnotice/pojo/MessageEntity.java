package com.jdry.pms.msgandnotice.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="t_msgandnotice_msg")
/**
 * 消息实体
 * @author 钟涛
 *
 */
public class MessageEntity {
	/**
	 * 消息ID
	 */
	protected String msgId;
	/**
	 * @return 消息ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "msg_id")
	public String getMsgId() {
		return msgId;
	}
	/**
	 * @param msgId 消息ID
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	protected String msgSubject;
	
	/**
	 * @return 消息标题
	 */
	@Column(name="msg_subject")
	public String getMsgSubject() {
		return msgSubject;
	}
	
	/**
	 * @param msgSubject 消息标题
	 */
	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
	protected String msgBusinessId;
	/**
	 * @return 消息编号
	 */
	@Column(name="msg_business_id")
	public String getMsgBusinessId() {
		return msgBusinessId;
	}
	public void setMsgBusinessId(String msgBusinessId) {
		this.msgBusinessId = msgBusinessId;
	}

	/**
	 * 消息状态
	 */
	protected String msgStatus;
	
	/**
	 * @return 消息状态
	 */
	@Column(name="msg_status")
	public String getMsgStatus() {
		return msgStatus;
	}
	
	/**
	 * @param msgStatus 消息状态
	 */
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	
	/**
	 * 创建时间
	 */
	
	protected Date msgCreateTime;
	
	/**
	 * @return 创建时间
	 */
	@Column(name = "msg_create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMsgCreateTime() {
		return msgCreateTime;
	}
	
	/**
	 * @param msgCreateTime 创建时间
	 */
	public void setMsgCreateTime(Date msgCreateTime) {
		this.msgCreateTime = msgCreateTime;
	}
	
	/**
	 * 消息内容
	 */
	protected String msgContent;
	
	/**
	 * @return 消息内容
	 */
	@Column(name = "msg_content")
	public String getMsgContent() {
		return msgContent;
	}
	
	/**
	 * @param msgContent 消息内容
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	/**
	 * 消息创建人
	 */
	protected String msgCreater;
	
	/**
	 * @return 消息创建人
	 */
	@Column(name = "msg_creater")
	public String getMsgCreater() {
		return msgCreater;
	}
	
	/**
	 * @param msgCreater 消息创建人
	 */
	public void setMsgCreater(String msgCreater) {
		this.msgCreater = msgCreater;
	}
	/**
	 * 消息创建人ID
	 */
	protected String msgCreaterId;
	
	/**
	 * @return 消息创建人ID
	 */
	@Column(name = "msg_creater_id")
	public String getMsgCreaterId() {
		return msgCreaterId;
	}
	
	/**
	 * @param msgCreater 消息创建人ID
	 */
	public void setMsgCreaterId(String msgCreaterId) {
		this.msgCreaterId = msgCreaterId;
	}
}
