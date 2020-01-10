package com.jdry.pms.camera.pojo;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_camera_info")
public class CameraInfo {
	private String id;
	private String regionId;
	private String cameraName;
	private String cameraAddress;
	private String cameraModel;
	private String cameraSerialNumber;
	private String cameraVerificationCode;
	private Date createDate;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "region_id")
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	@Column(name = "camera_name")
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	@Column(name = "camera_address")
	public String getCameraAddress() {
		return cameraAddress;
	}
	public void setCameraAddress(String cameraAddress) {
		this.cameraAddress = cameraAddress;
	}
	@Column(name = "camera_model")
	public String getCameraModel() {
		return cameraModel;
	}
	public void setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
	}
	@Column(name = "camera_serial_number")
	public String getCameraSerialNumber() {
		return cameraSerialNumber;
	}
	public void setCameraSerialNumber(String cameraSerialNumber) {
		this.cameraSerialNumber = cameraSerialNumber;
	}
	@Column(name = "camera_verification_code")
	public String getCameraVerificationCode() {
		return cameraVerificationCode;
	}
	public void setCameraVerificationCode(String cameraVerificationCode) {
		this.cameraVerificationCode = cameraVerificationCode;
	}
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
