package com.jdry.pms.msgandnotice.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;


/**
 * 短信发送
 * 
 * @author 钟涛
 * @version 1.0 2016-01-18
 */
@Entity
@Table(name = "t_msgandnotice_send")
public class MessageSendMain  implements Cloneable{
	/**
	 * 发送短信ID
	 */
	protected String msgSendId;
	/**
	 * @return 发送短信ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "msg_send_id")
	public String getMsgSendId() {
		return msgSendId;
	}
	/**
	 * @param msgContent 发送短信ID
	 */
	public void setMsgSendId(String msgSendId) {
		this.msgSendId = msgSendId;
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
	 * 创建时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
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
	 * 发送时间
	 */
	protected Date msfSendTime;
	
	/**
	 * @return 发送时间
	 */
	@Column(name = "msg_send_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMsfSendTime() {
		return msfSendTime;
	}
	
	/**
	 * @param msfSendTime 发送时间
	 */
	public void setMsfSendTime(Date msfSendTime) {
		this.msfSendTime = msfSendTime;
	}
	
	/**
	 * 短信标识
	 */
	protected String msgFlag;
	
	/**
	 * @return 短信标识
	 */
	@Column(name = "msg_flag")
	public String getMsgFlag() {
		return msgFlag;
	}
	
	/**
	 * @param msgFlag 短信标识
	 */
	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}
	/**
	 * 短信是否发送成功标识
	 */
	protected String successFlag;
	
	/**
	 * @return 短信是否发送成功标识
	 */
	@Column(name = "success_flag")
	public String getSuccessFlag() {
		return successFlag;
	}
	/**
	 * @param successFlag 是否发送成功标识
	 */
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	/**
	 * 短信是否发送标识
	 */
	protected String sendFlag;
	/**
	 * @return 短信是否发送成功标识
	 */
	@Column(name = "send_flag")
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	/**
	 * 创建者
	 */
	protected DefaultUser msgCreator;

	/**
	 * @return 创建者
	 */
	@ManyToOne
	@JoinColumn(name = "msg_creator_id", insertable = false, updatable = false)
	public DefaultUser getMsgCreator() {
		return msgCreator;
	}
	
	/**
	 * @param msgCreator 创建者
	 */
	public void setMsgCreator(DefaultUser msgCreator) {
		this.msgCreator = msgCreator;
	}
	
    private String msgCreatorId;
    @Column(name = "msg_creator_id")
	public String getMsgCreatorId() {
		return msgCreatorId;
	}
	public void setMsgCreatorId(String msgCreatorId) {
		this.msgCreatorId = msgCreatorId;
	}
	/**
	 * 发送者
	 */
	protected DefaultUser msgSender;
	
	/**
	 * @return 发送者
	 */
	@ManyToOne
	@JoinColumn(name = "msg_sender_id", insertable = false, updatable = false)
	public DefaultUser getMsgSender() {
		return msgSender;
	}
	
	/**
	 * @param msgSender 发送者
	 */
	public void setMsgSender(DefaultUser msgSender) {
		this.msgSender = msgSender;
	}
	
	protected String msgSenderId;
	
	@Column(name = "msg_sender_id")
	public String getMsgSenderId() {
		return msgSenderId;
	}
	public void setMsgSenderId(String msgSenderId) {
		this.msgSenderId = msgSenderId;
	}
	/**
	 * 所属模板
	 */
	protected MessageTempMain msgTemp;
	
	/**
	 * @return 所属模板
	 */
	@ManyToOne
	@JoinColumn(name = "msg_temp_id", insertable = false, updatable = false)
	public MessageTempMain getMsgTemp() {
		return msgTemp;
	}
	
	/**
	 * @param msgTemp 所属模板
	 */
	public void setMsgTemp(MessageTempMain msgTemp) {
		this.msgTemp = msgTemp;
	}
    protected String msgTempId;
    
    @Column(name = "msg_temp_id")
	public String getMsgTempId() {
		return msgTempId;
	}
	public void setMsgTempId(String msgTempId) {
		this.msgTempId = msgTempId;
	}
	/**
	 * 接收人
	 */
	protected PropertyOwner msgReceivers;
	
	/**
	 * @return 接收人
	 */
	@ManyToOne
	@JoinColumn(name = "msg_receiver_id", insertable = false, updatable = false)
	public PropertyOwner getMsgReceivers() {
		return msgReceivers;
	}
	
	/**
	 * @param msgReceivers 接收人
	 */
	public void setMsgReceivers(PropertyOwner msgReceivers) {
		this.msgReceivers = msgReceivers;
	}
	
    protected String msgReceiverId;
    @Column(name = "msg_receiver_id")
	public String getMsgReceiverId() {
		return msgReceiverId;
	}
	public void setMsgReceiverId(String msgReceiverId) {
		this.msgReceiverId = msgReceiverId;
	}
	@Override
	public Object clone() {   
        try {   
            return super.clone();   
        } catch (CloneNotSupportedException e) {   
            return null;   
        }   
    }   
}
