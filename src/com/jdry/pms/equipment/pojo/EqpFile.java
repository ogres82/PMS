package com.jdry.pms.equipment.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 设备档案
 * @author xulei
 *
 */
@Entity
@Table(name = "t_eqp_file")
public class EqpFile {
	
	private String eqpId;				//设备ID
	private String eqpName;				//设备名称
	private String eqpAddress;			//安装地点
	private String eqpBelongArea;		//所属楼盘
	private String eqpType;				//设备类型
	private String eqpModel;			//规格型号
	private String eqpStatus;			//设备状态
	private String eqpMtnCycle;			//保养周期
	private Date eqpInstallDate;		//安装日期
	private Date eqpDestroyDate;		//报废日期
	private int eqpLifetime;			//使用年限
	private int eqpUsedMonths;		//已用月数
	private int eqpFixedTimes;		//维修次数
	private int eqpMtnTimes;			//保养次数
	private Date eqpMtnLast;			//最近保养日期
	private Date eqpMtnNext;			//下次保养日期
	private String eqpMtnMark;
	
	@Transient
	public String getEqpMtnMark() {
		return eqpMtnMark;
	}
	public void setEqpMtnMark(String eqpMtnMark) {
		this.eqpMtnMark = eqpMtnMark;
	}
	private float eqpOriginValue;		//设备原值
	private float eqpValueRate;		//净残值率
	private float eqpCurrentValue;		//设备净值
	private String eqpMtnUnit;			//维保单位
	private String eqpMtnPhone;			//维保单位电话
	private String eqpMtnAddress;		//维保单位地址
	private String eqpMtnPerson;		//设备维保人
	private String eqpManu;				//生产厂家
	private String eqpManuPhone;		//厂家电话
	private String eqpManuAddress;		//厂家地址
	private Date eqpRegisterDate;		//登记日期
	private String eqpRemark;			//备注
	
	
	@Id
	@Column(name = "eqp_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	@Column(name = "eqp_name")
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	@Column(name = "eqp_address")
	public String getEqpAddress() {
		return eqpAddress;
	}
	public void setEqpAddress(String eqpAddress) {
		this.eqpAddress = eqpAddress;
	}
	@Column(name = "eqp_belong_area")
	public String getEqpBelongArea() {
		return eqpBelongArea;
	}
	public void setEqpBelongArea(String eqpBelongArea) {
		this.eqpBelongArea = eqpBelongArea;
	}
	@Column(name = "eqp_type")
	public String getEqpType() {
		return eqpType;
	}
	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}
	@Column(name = "eqp_model")
	public String getEqpModel() {
		return eqpModel;
	}
	public void setEqpModel(String eqpModel) {
		this.eqpModel = eqpModel;
	}
	@Column(name = "eqp_status")
	public String getEqpStatus() {
		return eqpStatus;
	}
	public void setEqpStatus(String eqpStatus) {
		this.eqpStatus = eqpStatus;
	}
	@Column(name = "eqp_maintain_cycle")
	public String getEqpMtnCycle() {
		return eqpMtnCycle;
	}
	public void setEqpMtnCycle(String eqpMtnCycle) {
		this.eqpMtnCycle = eqpMtnCycle;
	}
	@Column(name = "eqp_install_date")
	public Date getEqpInstallDate() {
		return eqpInstallDate;
	}
	public void setEqpInstallDate(Date eqpInstallDate) {
		this.eqpInstallDate = eqpInstallDate;
	}
	@Column(name = "eqp_destroy_date")
	public Date getEqpDestroyDate() {
		return eqpDestroyDate;
	}
	public void setEqpDestroyDate(Date eqpDestroyDate) {
		this.eqpDestroyDate = eqpDestroyDate;
	}
	@Column(name = "eqp_lifetime")
	public int getEqpLifetime() {
		return eqpLifetime;
	}
	public void setEqpLifetime(int eqpLifetime) {
		this.eqpLifetime = eqpLifetime;
	}
	@Column(name = "eqp_used_months")
	public int getEqpUsedMonths() {
		return eqpUsedMonths;
	}
	public void setEqpUsedMonths(int eqpUsedMonths) {
		this.eqpUsedMonths = eqpUsedMonths;
	}
	@Column(name = "eqp_fixed_times")
	public int getEqpFixedTimes() {
		return eqpFixedTimes;
	}
	public void setEqpFixedTimes(int eqpFixedTimes) {
		this.eqpFixedTimes = eqpFixedTimes;
	}
	@Column(name = "eqp_maintain_times")
	public int getEqpMtnTimes() {
		return eqpMtnTimes;
	}
	public void setEqpMtnTimes(int eqpMtnTimes) {
		this.eqpMtnTimes = eqpMtnTimes;
	}
	@Column(name = "eqp_maintain_last")
	public Date getEqpMtnLast() {
		return eqpMtnLast;
	}
	public void setEqpMtnLast(Date eqpMtnLast) {
		this.eqpMtnLast = eqpMtnLast;
	}
	@Column(name = "eqp_maintain_next")
	public Date getEqpMtnNext() {
		return eqpMtnNext;
	}
	public void setEqpMtnNext(Date eqpMtnNext) {
		this.eqpMtnNext = eqpMtnNext;
	}
	@Column(name = "eqp_origin_value")
	public float getEqpOriginValue() {
		return eqpOriginValue;
	}
	public void setEqpOriginValue(float eqpOriginValue) {
		this.eqpOriginValue = eqpOriginValue;
	}
	@Column(name = "eqp_value_rate")
	public float getEqpValueRate() {
		return eqpValueRate;
	}
	public void setEqpValueRate(float eqpValueRate) {
		this.eqpValueRate = eqpValueRate;
	}
	@Column(name = "eqp_current_value")
	public float getEqpCurrentValue() {
		return eqpCurrentValue;
	}
	public void setEqpCurrentValue(float eqpCurrentValue) {
		this.eqpCurrentValue = eqpCurrentValue;
	}
	@Column(name = "eqp_maintain_unit")
	public String getEqpMtnUnit() {
		return eqpMtnUnit;
	}
	public void setEqpMtnUnit(String eqpMtnUnit) {
		this.eqpMtnUnit = eqpMtnUnit;
	}
	@Column(name = "eqp_maintain_phone")
	public String getEqpMtnPhone() {
		return eqpMtnPhone;
	}
	public void setEqpMtnPhone(String eqpMtnPhone) {
		this.eqpMtnPhone = eqpMtnPhone;
	}
	@Column(name = "eqp_maintain_address")
	public String getEqpMtnAddress() {
		return eqpMtnAddress;
	}
	public void setEqpMtnAddress(String eqpMtnAddress) {
		this.eqpMtnAddress = eqpMtnAddress;
	}
	@Column(name = "eqp_maintain_person")
	public String getEqpMtnPerson() {
		return eqpMtnPerson;
	}
	public void setEqpMtnPerson(String eqpMtnPerson) {
		this.eqpMtnPerson = eqpMtnPerson;
	}
	@Column(name = "eqp_manu")
	public String getEqpManu() {
		return eqpManu;
	}
	public void setEqpManu(String eqpManu) {
		this.eqpManu = eqpManu;
	}
	@Column(name = "eqp_manu_phone")
	public String getEqpManuPhone() {
		return eqpManuPhone;
	}
	public void setEqpManuPhone(String eqpManuPhone) {
		this.eqpManuPhone = eqpManuPhone;
	}
	@Column(name = "eqp_manu_address")
	public String getEqpManuAddress() {
		return eqpManuAddress;
	}
	public void setEqpManuAddress(String eqpManuAddress) {
		this.eqpManuAddress = eqpManuAddress;
	}
	@Column(name = "eqp_register_date")
	public Date getEqpRegisterDate() {
		return eqpRegisterDate;
	}
	public void setEqpRegisterDate(Date eqpRegisterDate) {
		this.eqpRegisterDate = eqpRegisterDate;
	}
	@Column(name = "eqp_remark")
	public String getEqpRemark() {
		return eqpRemark;
	}
	public void setEqpRemark(String eqpRemark) {
		this.eqpRemark = eqpRemark;
	}

}
