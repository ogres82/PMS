package com.jdry.pms.msgandnotice.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_msgandnotice_pic")
public class MsgandNoticePicture {
	private String picId;
	private String ntcId;
	private String filePath;
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "pic_id")
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	@Column(name = "ntc_notice_id")
	public String getNtcId() {
		return ntcId;
	}
	public void setNtcId(String ntcId) {
		this.ntcId = ntcId;
	}
	@Column(name = "file_path")
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	private MsgandnotNoticeMain ntcNotice;
	
	@ManyToOne
	@JoinColumn(name = "ntc_notice_id", insertable = false, updatable = false)
	public MsgandnotNoticeMain getNtcNotice() {
		return ntcNotice;
	}
	
	/**
	 * @param ntcNotice 所属公告
	 */
	public void setNtcNotice(MsgandnotNoticeMain ntcNotice) {
		this.ntcNotice = ntcNotice;
	}
}
