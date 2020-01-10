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

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 消息模板
 * 
 * @author 钟涛
 * @version 1.0 2016-01-14
 */
@Entity
@Table(name="t_msgandnotice_msg_temp")
public class MessageTempMain{
	/**
	 * 主键ID
	 */
	protected String msgTempId;
	/**
	 * @return 主键ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "msg_id")
	public String getMsgTempId() {
		return msgTempId;
	}
	/**
	 * @param msgTempId 主键ID
	 */
	public void setMsgTempId(String msgTempId) {
		this.msgTempId = msgTempId;
	}

	/**
	 * 标题
	 */
	protected String msgTempSubject;
	
	/**
	 * @return 标题
	 */
	@Column(name="msg_temp_subject")
	public String getMsgTempSubject() {
		return msgTempSubject;
	}
	
	/**
	 * @param msgTempSubject 标题
	 */
	public void setMsgTempSubject(String msgTempSubject) {
		this.msgTempSubject = msgTempSubject;
	}
	
	/**
	 * 模板内容
	 */
	@Column(name="msg_temp_content")
	protected String msgTempContent;
	
	/**
	 * @return 模板内容
	 */
	public String getMsgTempContent() {
		return msgTempContent;
	}
	
	/**
	 * @param msgTempContent 模板内容
	 */
	public void setMsgTempContent(String msgTempContent) {
		this.msgTempContent = msgTempContent;
	}
	
	/**
	 * 创建时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	protected Date msgTempCtime;
	
	/**
	 * @return 创建时间
	 */
	@Column(name = "msg_temp_ctime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMsgTempCtime() {
		return msgTempCtime;
	}
	
	/**
	 * @param msgTempCtime 创建时间
	 */
	public void setMsgTempCtime(Date msgTempCtime) {
		this.msgTempCtime = msgTempCtime;
	}

}
