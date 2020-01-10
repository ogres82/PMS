package com.jdry.pms.msgandnotice.pojo;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 公告
 * 
 * @author 钟涛
 * @version 1.0 2016-01-05
 */
@Entity
@Table(name="t_msgandnotice_ntc")
public class MsgandnotNoticeMain {
	/**
	 * 公告主键Id
	 */
	protected String ntcId;
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "ntc_id")	
	public String getNtcId() {
		return ntcId;
	}
	
	public void setNtcId(String ntcId) {
		this.ntcId = ntcId;
	}
	/**
	 * 标题
	 */
	protected String ntcSubject;

	/**
	 * @return 标题
	 */
	@Column(name = "ntc_subject")
	public String getNtcSubject() {
		return ntcSubject;
	}
	
	/**
	 * @param ntcSubject 标题
	 */
	public void setNtcSubject(String ntcSubject) {
		this.ntcSubject = ntcSubject;
	}
	
	/**
	 * 公告状态
	 */
	protected String ntcStatus;
	
	/**
	 * @return 公告状态
	 */
	@Column(name = "ntc_status")
	public String getNtcStatus() {
		return ntcStatus;
	}
	
	/**
	 * @param ntcStatus 公告状态
	 */
	public void setNtcStatus(String ntcStatus) {
		this.ntcStatus = ntcStatus;
	}
	
	/**
	 * 创建时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	protected Date ntcCreateTime;
	
	/**
	 * @return 创建时间
	 */
	@Column(name = "ntc_create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getNtcCreateTime() {
		return ntcCreateTime;
	}
	
	/**
	 * @param ntcCreateTime 创建时间
	 */
	public void setNtcCreateTime(Date ntcCreateTime) {
		this.ntcCreateTime = ntcCreateTime;
	}
	
	/**
	 * 创建人
	 */
	
	protected String ntcCreator;
	
	/**
	 * @return 创建人
	 */
	@Column(name = "ntc_creator")
	public String getNtcCreator() {
		return ntcCreator;
	}
	
	/**
	 * @param ntcCreator 创建人
	 */
	public void setNtcCreator(String ntcCreator) {
		this.ntcCreator = ntcCreator;
	}
	
	/**
	 * 创建人ID
	 */
	
	protected String ntcCreatorId;
	
	/**
	 * @return 创建人ID
	 */
	@Column(name = "ntc_creator_id")
	public String getNtcCreatorId() {
		return ntcCreatorId;
	}
	
	/**
	 * @param ntcCreator 创建人ID
	 */
	public void setNtcCreatorId(String ntcCreatorId) {
		this.ntcCreatorId = ntcCreatorId;
	}
	/**
	 * 所属部门ID
	 */
	
	protected String ntcDept;
	
	/**
	 * @return 创建人ID
	 */
	@Column(name = "ntc_dept")
	public String getNtcDept() {
		return ntcDept;
	}
	
	/**
	 * @param ntcCreator 创建人ID
	 */
	public void setNtcDept(String ntcDept) {
		this.ntcDept = ntcDept;
	}
	
	/**
	 * 审核人
	 */
	protected String ntcAuditor;
	
	/**
	 * @return 审核人
	 */
	@Column(name = "ntc_auditor")
	public String getNtcAuditor() {
		return ntcAuditor;
	}
	
	/**
	 * @param ntcAuditor 审核人
	 */
	public void setNtcAuditor(String ntcAuditor) {
		this.ntcAuditor = ntcAuditor;
	}
	
	/**
	 * 发布人
	 */
	protected String ntcPublishor;
	
	/**
	 * @return 发布人
	 */
	@Column(name = "ntc_publishor")
	public String getNtcPublishor() {
		return ntcPublishor;
	}
	
	/**
	 * @param ntcPublishor 发布人
	 */
	public void setNtcPublishor(String ntcPublishor) {
		this.ntcPublishor = ntcPublishor;
	}
	/**
	 * 发布人Id
	 */
	protected String ntcPublishorId;
	
	/**
	 * @return 发布人Id
	 */
	@Column(name = "ntc_publishor_id")
	public String getNtcPublishorId() {
		return ntcPublishorId;
	}
	
	/**
	 * @param ntcPublishor 发布人Id
	 */
	public void setNtcPublishorId(String ntcPublishorId) {
		this.ntcPublishorId = ntcPublishorId;
	}
	
	/**
	 * 公告内容
	 */
	protected String ntcContent;
	
	/**
	 * @return 公告内容
	 */
	@Column(name = "ntc_content")
	public String getNtcContent() {
		return ntcContent;
	}
	
	/**
	 * @param ntcContent 公告内容
	 */
	public void setNtcContent(String ntcContent) {
		this.ntcContent = ntcContent;
	}
	
	/**
	 * 发布时间
	 */
	protected Date ntcPublishTime;
	
	/**
	 * @return 发布时间
	 */
	@Column(name = "ntc_publish_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getNtcPublishTime() {
		return ntcPublishTime;
	}
	
	/**
	 * @param ntcPublishTime 发布时间
	 */
	public void setNtcPublishTime(Date ntcPublishTime) {
		this.ntcPublishTime = ntcPublishTime;
	}
	private Collection<MsgandnoticeNoticeAuditinfo> msgandnoticeNoticeAuditinfos;
	 
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "ntc_notice_id",insertable = false, updatable = false)
    public Collection<MsgandnoticeNoticeAuditinfo> getMsgandnoticeNoticeAuditinfos() {
		return msgandnoticeNoticeAuditinfos;
	}

	public void setMsgandnoticeNoticeAuditinfos(
			Collection<MsgandnoticeNoticeAuditinfo> msgandnoticeNoticeAuditinfos) {
		this.msgandnoticeNoticeAuditinfos = msgandnoticeNoticeAuditinfos;
	}
    
    //图片位置
    private Set<MsgandNoticePicture> msgandnoticePics;
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "ntc_notice_id",nullable=true,insertable = true, updatable = true)
    public Set<MsgandNoticePicture> getMsgandnoticePics() {
		return msgandnoticePics;
	}

	public void setMsgandnoticePics(Set<MsgandNoticePicture> msgandnoticePics) {
		this.msgandnoticePics = msgandnoticePics;
	}
	/**
	 * 流程实例ID
	 */
	private String processInstanceId;
	
	

	@Column(name = "process_instance_id")
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	private String currentAuditer;
	
	@Column(name = "current_auditer")
	public String getCurrentAuditer() {
		return currentAuditer;
	}

	public void setCurrentAuditer(String currentAuditer) {
		this.currentAuditer = currentAuditer;
	}
	
	protected String passFlag;
	@Column(name = "ntc_pass_flag")
	public String getPassFlag() {
		return passFlag;
	}
	
	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	} 
	/*
	 * 公告编号
	 */
	protected String rptId;
	@Column(name = "ntc_no")
	public String getRptId() {
		return rptId;
	}

	public void setRptId(String rptId) {
		this.rptId = rptId;
	}
	/*
	 * 最后更新时间
	 */
	protected Date lastUpdateTime;
	@Column(name = "ntc_lastupdatetime")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
