package com.jdry.pms.basicInfo.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_emp")
public class Emp {

	String empId; // 员工ID

	// 员工自然信息
	String empNo; // 员工序号
	String empName; // 姓名
	String empSex; // 性别
	String empNation; // 民族
	Date empBirth; // 出生日期
	String empIdNo; // 身份证号码
	String empPolitics; // 政治面貌
	String empPhone; // 联系电话
	String empStatus; // 员工状态
	String empEmail;// 邮箱

	//// 入职企业内部信息
	String empPlate; // 板块
	String empDeptId; // 员工部门ID
	String empWorkPlace; // 工作职场
	String empPostId; // 员工岗位/职务ID

	Date empEntryTime; // 入职时间
	Date empQuitTime; // 离职时间
	String empProbation; // 试用期
	String empRegularize; // 转正情况
	Date empRegularizeTime; // 转正日

	String empPositionLevl; // 职能层级
	String empTechLevl; // 技术层级
	Date empTenureDate; // 在任时间
	String empPosChange1; // 岗位平调
	String empPosChange2; // 岗位晋升/降职
	String empTrainingRec; // 培训记录
	String empRewordPunish; // 奖惩记录
	String empFileStorePos; // 人事档案存放地
	String empFileNo; // 人事档案编号
	String empEducation; // 学历
	String empEduFullTime; // 是否全日制
	String empGraduateUni; // 毕业院校
	String empMajor; // 所学专业
	String empCertificate; // 专业资质证书及名称
	String empCertifiNo; // 专业资质证书编号
	Date empCertifiDate; // 发证日期
	Date empCertifiExpireDate; // 证书有效期
	Date empCertifiCheckDate; // 复审日期
	String empCertifiLev; // 专业资质证书等级
	String empCertifiScan; // 是否有扫描件
	// 劳动（聘用）合同履行
	String empEmployType; // 用工类型
	String empContractSubject; // 劳动合同主体
	Date empContractBeginDate; // 起始日期
	Date empContractEndDate; // 终止日期

	// 社保关系
	String empInsuredUnitNo; // 参保单位编号
	String empInsuredNo; // 社会保障号
	String empInsuredFee; // 参保基数
	Date empInsuredDate; // 入职我单位后参保时间
	// 公积金汇缴情况
	String empFundsUnitNo; // 公积金单位编号
	String empFundsNo; // 公积金个人账号
	String empFundsFee; // 公积金基数
	Date empFundsDate; // 入职我单位后汇费时间
	// 员工现劳动关系状态
	String empLaborStatus;
	// 辅助信息
	String empPreWorkUnit; // 入职前单位及职务
	Date empFirstWorkDate; // 初次工作时间
	String empMarriage; // 婚姻状况
	String empBear; // 生育状况
	String empAccount; // 户口
	String empAccountProperties; // 户口性质
	String empAccountAddress; // 户籍所在地址
	String empAddress; // 家庭住址
	String empMobilePhone; // 手机
	String empContactInfo; // 紧急联系人电话及关系
	String empResidence; // 是否住宿
	String empApply; // 应聘渠道
	String empSpecial; // 特长
	String empRemark; // 备注
	// 薪资信息
	String empProbationWages; // 试用期工资
	String empFullWages; // 转正工资
	Date empFullWagesDate; // 转正工资体现月
	String empFullWagesFlag; // 是否体现
	String empRealWages; // 现工资
	String empRaiseWages; // 调薪情况
	String empBankName; // 银行名称
	String empBankCardNo; // 银行卡号
	// 员工资料明细
	String empFlowChart; // 录入流程表
	String empResume; // 个人简历
	String empRegForm; // 员工登记
	String empAgreement; // 保密协议
	String empPhoto; // 照片
	String empIDCardCopy; // 身份证（复）
	String empBookletCopy; // 户口簿（复）
	String empDiplomaCopy; // 毕业证（复）
	String empCheckupReport; // 体检报告
	String empLeaveCertificate; // 原单位离职证明
	String empBondsmanInfo; // 担保人资料
	String empBond; // 担保书
	String empDriverLicense; // 驾照资料
	String empTitleCertificate; // 职称证书

	@Id
	@Column(name = "emp_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "emp_no")
	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "emp_name")
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "emp_sex")
	public String getEmpSex() {
		return this.empSex;
	}

	public void setEmpSex(String empSex) {
		this.empSex = empSex;
	}

	@Column(name = "emp_birth")
	public Date getEmpBirth() {
		return this.empBirth;
	}

	public void setEmpBirth(Date empBirth) {
		this.empBirth = empBirth;
	}

	@Column(name = "emp_id_no")
	public String getEmpIdNo() {
		return this.empIdNo;
	}

	public void setEmpIdNo(String empIdNo) {
		this.empIdNo = empIdNo;
	}

	@Column(name = "emp_nation")
	public String getEmpNation() {
		return this.empNation;
	}

	public void setEmpNation(String empNation) {
		this.empNation = empNation;
	}

	@Column(name = "emp_dept_id")
	public String getEmpDeptId() {
		return this.empDeptId;
	}

	public void setEmpDeptId(String empDeptId) {
		this.empDeptId = empDeptId;
	}

	@Column(name = "emp_post_id")
	public String getEmpPostId() {
		return this.empPostId;
	}

	public void setEmpPostId(String empPostId) {
		this.empPostId = empPostId;
	}

	@Column(name = "emp_phone")
	public String getEmpPhone() {
		return this.empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	@Column(name = "emp_email")
	public String getEmpEmail() {
		return this.empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	@Column(name = "emp_contact_info")
	public String getEmpContactInfo() {
		return this.empContactInfo;
	}

	public void setEmpContactInfo(String empContactInfo) {
		this.empContactInfo = empContactInfo;
	}

	@Column(name = "emp_entry_time")
	public Date getEmpEntryTime() {
		return this.empEntryTime;
	}

	public void setEmpEntryTime(Date empEntryTime) {
		this.empEntryTime = empEntryTime;
	}

	@Column(name = "emp_quit_time")
	public Date getEmpQuitTime() {
		return this.empQuitTime;
	}

	public void setEmpQuitTime(Date empQuitTime) {
		this.empQuitTime = empQuitTime;
	}

	@Column(name = "emp_status")
	public String getEmpStatus() {
		return this.empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	@Column(name = "emp_photo")
	public String getEmpPhoto() {
		return this.empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	@Column(name = "emp_politics")
	public String getEmpPolitics() {
		return this.empPolitics;
	}

	public void setEmpPolitics(String empPolitics) {
		this.empPolitics = empPolitics;
	}

	@Column(name = "emp_work_place")
	public String getEmpWorkPlace() {
		return this.empWorkPlace;
	}

	public void setEmpWorkPlace(String empWorkPlace) {
		this.empWorkPlace = empWorkPlace;
	}

	@Column(name = "emp_probation")
	public String getEmpProbation() {
		return this.empProbation;
	}

	public void setEmpProbation(String empProbation) {
		this.empProbation = empProbation;
	}

	@Column(name = "emp_regularize")
	public String getEmpRegularize() {
		return this.empRegularize;
	}

	public void setEmpRegularize(String empRegularize) {
		this.empRegularize = empRegularize;
	}

	@Column(name = "emp_regularize_time")
	public Date getEmpRegularizeTime() {
		return this.empRegularizeTime;
	}

	public void setEmpRegularizeTime(Date empRegularizeTime) {
		this.empRegularizeTime = empRegularizeTime;
	}

	@Column(name = "emp_position_levl")
	public String getEmpPositionLevl() {
		return this.empPositionLevl;
	}

	public void setEmpPositionLevl(String empPositionLevl) {
		this.empPositionLevl = empPositionLevl;
	}

	@Column(name = "emp_tech_levl")
	public String getEmpTechLevl() {
		return this.empTechLevl;
	}

	public void setEmpTechLevl(String empTechLevl) {
		this.empTechLevl = empTechLevl;
	}

	@Column(name = "emp_file_store_pos")
	public String getEmpFileStorePos() {
		return this.empFileStorePos;
	}

	public void setEmpFileStorePos(String empFileStorePos) {
		this.empFileStorePos = empFileStorePos;
	}

	@Column(name = "emp_pos_change1")
	public String getEmpPosChange1() {
		return this.empPosChange1;
	}

	public void setEmpPosChange1(String empPosChange1) {
		this.empPosChange1 = empPosChange1;
	}

	@Column(name = "emp_pos_change2")
	public String getEmpPosChange2() {
		return this.empPosChange2;
	}

	public void setEmpPosChange2(String empPosChange2) {
		this.empPosChange2 = empPosChange2;
	}

	@Column(name = "emp_training_rec")
	public String getEmpTrainingRec() {
		return this.empTrainingRec;
	}

	public void setEmpTrainingRec(String empTrainingRec) {
		this.empTrainingRec = empTrainingRec;
	}

	@Column(name = "emp_reword_punish")
	public String getEmpRewordPunish() {
		return this.empRewordPunish;
	}

	public void setEmpRewordPunish(String empRewordPunish) {
		this.empRewordPunish = empRewordPunish;
	}

	@Column(name = "emp_education")
	public String getEmpEducation() {
		return this.empEducation;
	}

	public void setEmpEducation(String empEducation) {
		this.empEducation = empEducation;
	}

	@Column(name = "emp_edu_full_time")
	public String getEmpEduFullTime() {
		return this.empEduFullTime;
	}

	public void setEmpEduFullTime(String empEduFullTime) {
		this.empEduFullTime = empEduFullTime;
	}

	@Column(name = "emp_graduate_uni")
	public String getEmpGraduateUni() {
		return this.empGraduateUni;
	}

	public void setEmpGraduateUni(String empGraduateUni) {
		this.empGraduateUni = empGraduateUni;
	}

	@Column(name = "emp_major")
	public String getEmpMajor() {
		return this.empMajor;
	}

	public void setEmpMajor(String empMajor) {
		this.empMajor = empMajor;
	}

	@Column(name = "emp_certificate")
	public String getEmpCertificate() {
		return this.empCertificate;
	}

	public void setEmpCertificate(String empCertificate) {
		this.empCertificate = empCertificate;
	}

	@Column(name = "emp_certifi_no")
	public String getEmpCertifiNo() {
		return this.empCertifiNo;
	}

	public void setEmpCertifiNo(String empCertifiNo) {
		this.empCertifiNo = empCertifiNo;
	}

	@Column(name = "emp_certifi_date")
	public Date getEmpCertifiDate() {
		return this.empCertifiDate;
	}

	public void setEmpCertifiDate(Date empCertifiDate) {
		this.empCertifiDate = empCertifiDate;
	}

	@Column(name = "emp_certifi_expire_date")
	public Date getEmpCertifiExpireDate() {
		return this.empCertifiExpireDate;
	}

	public void setEmpCertifiExpireDate(Date empCertifiExpireDate) {
		this.empCertifiExpireDate = empCertifiExpireDate;
	}

	@Column(name = "emp_certifi_check_date")
	public Date getEmpCertifiCheckDate() {
		return this.empCertifiCheckDate;
	}

	public void setEmpCertifiCheckDate(Date empCertifiCheckDate) {
		this.empCertifiCheckDate = empCertifiCheckDate;
	}

	@Column(name = "emp_certifi_lev")
	public String getEmpCertifiLev() {
		return this.empCertifiLev;
	}

	public void setEmpCertifiLev(String empCertifiLev) {
		this.empCertifiLev = empCertifiLev;
	}

	@Column(name = "emp_certifi_scan")
	public String getEmpCertifiScan() {
		return this.empCertifiScan;
	}

	public void setEmpCertifiScan(String empCertifiScan) {
		this.empCertifiScan = empCertifiScan;
	}

	@Column(name = "emp_employ_type")
	public String getEmpEmployType() {
		return this.empEmployType;
	}

	public void setEmpEmployType(String empEmployType) {
		this.empEmployType = empEmployType;
	}

	@Column(name = "emp_contract_subject")
	public String getEmpContractSubject() {
		return this.empContractSubject;
	}

	public void setEmpContractSubject(String empContractSubject) {
		this.empContractSubject = empContractSubject;
	}

	@Column(name = "emp_contract_begin_date")
	public Date getEmpContractBeginDate() {
		return this.empContractBeginDate;
	}

	public void setEmpContractBeginDate(Date empContractBeginDate) {
		this.empContractBeginDate = empContractBeginDate;
	}

	@Column(name = "emp_contract_end_date")
	public Date getEmpContractEndDate() {
		return this.empContractEndDate;
	}

	public void setEmpContractEndDate(Date empContractEndDate) {
		this.empContractEndDate = empContractEndDate;
	}

	@Column(name = "emp_labor_status")
	public String getEmpLaborStatus() {
		return this.empLaborStatus;
	}

	public void setEmpLaborStatus(String empLaborStatus) {
		this.empLaborStatus = empLaborStatus;
	}

	@Column(name = "emp_pre_work_unit")
	public String getEmpPreWorkUnit() {
		return this.empPreWorkUnit;
	}

	public void setEmpPreWorkUnit(String empPreWorkUnit) {
		this.empPreWorkUnit = empPreWorkUnit;
	}

	@Column(name = "emp_first_work_date")
	public Date getEmpFirstWorkDate() {
		return this.empFirstWorkDate;
	}

	public void setEmpFirstWorkDate(Date empFirstWorkDate) {
		this.empFirstWorkDate = empFirstWorkDate;
	}

	@Column(name = "emp_marriage")
	public String getEmpMarriage() {
		return this.empMarriage;
	}

	public void setEmpMarriage(String empMarriage) {
		this.empMarriage = empMarriage;
	}

	@Column(name = "emp_bear")
	public String getEmpBear() {
		return this.empBear;
	}

	public void setEmpBear(String empBear) {
		this.empBear = empBear;
	}

	@Column(name = "emp_account")
	public String getEmpAccount() {
		return this.empAccount;
	}

	public void setEmpAccount(String empAccount) {
		this.empAccount = empAccount;
	}

	@Column(name = "emp_account_address")
	public String getEmpAccountAddress() {
		return this.empAccountAddress;
	}

	public void setEmpAccountAddress(String empAccountAddress) {
		this.empAccountAddress = empAccountAddress;
	}

	@Column(name = "emp_address")
	public String getEmpAddress() {
		return this.empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	@Column(name = "emp_mobile_phone")
	public String getEmpMobilePhone() {
		return this.empMobilePhone;
	}

	public void setEmpMobilePhone(String empMobilePhone) {
		this.empMobilePhone = empMobilePhone;
	}

	@Column(name = "emp_apply")
	public String getEmpApply() {
		return this.empApply;
	}

	public void setEmpApply(String empApply) {
		this.empApply = empApply;
	}

	@Column(name = "emp_residence")
	public String getEmpResidence() {
		return this.empResidence;
	}

	public void setEmpResidence(String empResidence) {
		this.empResidence = empResidence;
	}

	@Column(name = "emp_special")
	public String getEmpSpecial() {
		return this.empSpecial;
	}

	public void setEmpSpecial(String empSpecial) {
		this.empSpecial = empSpecial;
	}

	@Column(name = "emp_title_certificate")
	public String getEmpTitleCertificate() {
		return this.empTitleCertificate;
	}

	public void setEmpTitleCertificate(String empTitleCertificate) {
		this.empTitleCertificate = empTitleCertificate;
	}

	@Column(name = "emp_driver_license")
	public String getEmpDriverLicense() {
		return this.empDriverLicense;
	}

	public void setEmpDriverLicense(String empDriverLicense) {
		this.empDriverLicense = empDriverLicense;
	}

	@Column(name = "emp_probation_wages")
	public String getEmpProbationWages() {
		return this.empProbationWages;
	}

	public void setEmpProbationWages(String empProbationWages) {
		this.empProbationWages = empProbationWages;
	}

	@Column(name = "emp_remark")
	public String getEmpRemark() {
		return this.empRemark;
	}

	public void setEmpRemark(String empRemark) {
		this.empRemark = empRemark;
	}

	@Column(name = "emp_full_wages")
	public String getEmpFullWages() {
		return this.empFullWages;
	}

	public void setEmpFullWages(String empFullWages) {
		this.empFullWages = empFullWages;
	}

	@Column(name = "emp_full_wages_date")
	public Date getEmpFullWagesDate() {
		return this.empFullWagesDate;
	}

	public void setEmpFullWagesDate(Date empFullWagesDate) {
		this.empFullWagesDate = empFullWagesDate;
	}

	@Column(name = "emp_real_wages")
	public String getEmpRealWages() {
		return this.empRealWages;
	}

	public void setEmpRealWages(String empRealWages) {
		this.empRealWages = empRealWages;
	}

	@Column(name = "emp_raise_wages")
	public String getEmpRaiseWages() {
		return this.empRaiseWages;
	}

	public void setEmpRaiseWages(String empRaiseWages) {
		this.empRaiseWages = empRaiseWages;
	}

	@Column(name = "emp_bank_name")
	public String getEmpBankName() {
		return this.empBankName;
	}

	public void setEmpBankName(String empBankName) {
		this.empBankName = empBankName;
	}

	@Column(name = "emp_bank_card_no")
	public String getEmpBankCardNo() {
		return this.empBankCardNo;
	}

	public void setEmpBankCardNo(String empBankCardNo) {
		this.empBankCardNo = empBankCardNo;
	}

	@Column(name = "emp_flow_chart")
	public String getEmpFlowChart() {
		return this.empFlowChart;
	}

	public void setEmpFlowChart(String empFlowChart) {
		this.empFlowChart = empFlowChart;
	}

	@Column(name = "emp_resume")
	public String getEmpResume() {
		return this.empResume;
	}

	public void setEmpResume(String empResume) {
		this.empResume = empResume;
	}

	@Column(name = "emp_reg_form")
	public String getEmpRegForm() {
		return this.empRegForm;
	}

	public void setEmpRegForm(String empRegForm) {
		this.empRegForm = empRegForm;
	}

	@Column(name = "emp_agreement")
	public String getEmpAgreement() {
		return this.empAgreement;
	}

	public void setEmpAgreement(String empAgreement) {
		this.empAgreement = empAgreement;
	}

	// @Column(name = "emp_id_card_copy")
	// public String getEmpIdCardCopy(){
	// return this.empIdCardCopy;
	// }
	//
	// public void setEmpIdCardCopy(String empIdCardCopy){
	// this.empIdCardCopy = empIdCardCopy;
	// }
	@Column(name = "emp_booklet_copy")
	public String getEmpBookletCopy() {
		return this.empBookletCopy;
	}

	public void setEmpBookletCopy(String empBookletCopy) {
		this.empBookletCopy = empBookletCopy;
	}

	@Column(name = "emp_diploma_copy")
	public String getEmpDiplomaCopy() {
		return this.empDiplomaCopy;
	}

	public void setEmpDiplomaCopy(String empDiplomaCopy) {
		this.empDiplomaCopy = empDiplomaCopy;
	}

	@Column(name = "emp_checkup_report")
	public String getEmpCheckupReport() {
		return this.empCheckupReport;
	}

	public void setEmpCheckupReport(String empCheckupReport) {
		this.empCheckupReport = empCheckupReport;
	}

	@Column(name = "emp_leave_certificate")
	public String getEmpLeaveCertificate() {
		return this.empLeaveCertificate;
	}

	public void setEmpLeaveCertificate(String empLeaveCertificate) {
		this.empLeaveCertificate = empLeaveCertificate;
	}

	@Column(name = "emp_bondsman_info")
	public String getEmpBondsmanInfo() {
		return this.empBondsmanInfo;
	}

	public void setEmpBondsmanInfo(String empBondsmanInfo) {
		this.empBondsmanInfo = empBondsmanInfo;
	}

	@Column(name = "emp_bond")
	public String getEmpBond() {
		return this.empBond;
	}

	public void setEmpBond(String empBond) {
		this.empBond = empBond;
	}

	@Column(name = "emp_account_properties")
	public String getEmpAccountProperties() {
		return this.empAccountProperties;
	}

	public void setEmpAccountProperties(String empAccountProperties) {
		this.empAccountProperties = empAccountProperties;
	}

	@Column(name = "emp_insured_unit_no")
	public String getEmpInsuredUnitNo() {
		return this.empInsuredUnitNo;
	}

	public void setEmpInsuredUnitNo(String empInsuredUnitNo) {
		this.empInsuredUnitNo = empInsuredUnitNo;
	}

	@Column(name = "emp_insured_no")
	public String getEmpInsuredNo() {
		return this.empInsuredNo;
	}

	public void setEmpInsuredNo(String empInsuredNo) {
		this.empInsuredNo = empInsuredNo;
	}

	@Column(name = "emp_insured_fee")
	public String getEmpInsuredFee() {
		return this.empInsuredFee;
	}

	public void setEmpInsuredFee(String empInsuredFee) {
		this.empInsuredFee = empInsuredFee;
	}

	@Column(name = "emp_insured_date")
	public Date getEmpInsuredDate() {
		return this.empInsuredDate;
	}

	public void setEmpInsuredDate(Date empInsuredDate) {
		this.empInsuredDate = empInsuredDate;
	}

	@Column(name = "emp_funds_unit_no")
	public String getEmpFundsUnitNo() {
		return this.empFundsUnitNo;
	}

	public void setEmpFundsUnitNo(String empFundsUnitNo) {
		this.empFundsUnitNo = empFundsUnitNo;
	}

	@Column(name = "emp_funds_no")
	public String getEmpFundsNo() {
		return this.empFundsNo;
	}

	public void setEmpFundsNo(String empFundsNo) {
		this.empFundsNo = empFundsNo;
	}

	@Column(name = "emp_funds_fee")
	public String getEmpFundsFee() {
		return this.empFundsFee;
	}

	public void setEmpFundsFee(String empFundsFee) {
		this.empFundsFee = empFundsFee;
	}

	@Column(name = "emp_funds_date")
	public Date getEmpFundsDate() {
		return this.empFundsDate;
	}

	public void setEmpFundsDate(Date empFundsDate) {
		this.empFundsDate = empFundsDate;
	}

	@Column(name = "emp_file_no")
	public String getEmpFileNo() {
		return this.empFileNo;
	}

	public void setEmpFileNo(String empFileNo) {
		this.empFileNo = empFileNo;
	}

	@Column(name = "emp_plate")
	public String getEmpPlate() {
		return this.empPlate;
	}

	public void setEmpPlate(String empPlate) {
		this.empPlate = empPlate;
	}

	@Column(name = "emp_tenure_date")
	public Date getEmpTenureDate() {
		return empTenureDate;
	}

	public void setEmpTenureDate(Date empTenureDate) {
		this.empTenureDate = empTenureDate;
	}

	@Column(name = "emp_fullWages_flag")
	public String getEmpFullWagesFlag() {
		return empFullWagesFlag;
	}

	public void setEmpFullWagesFlag(String empFullWagesFlag) {
		this.empFullWagesFlag = empFullWagesFlag;
	}

	@Column(name = "emp_id_card_copy")
	public String getEmpIDCardCopy() {
		return empIDCardCopy;
	}

	public void setEmpIDCardCopy(String empIDCardCopy) {
		this.empIDCardCopy = empIDCardCopy;
	}

}