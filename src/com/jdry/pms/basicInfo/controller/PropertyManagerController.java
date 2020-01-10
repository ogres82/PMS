package com.jdry.pms.basicInfo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.RoleMember;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.basicInfo.pojo.VEmp;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.comm.service.BusinessService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.system.pojo.VRoleBtn;
import com.jdry.pms.system.service.URLBtnService;

@Repository
@Component
public class PropertyManagerController implements IController {

	@Resource
	private PropertyManagerService service;

	@Resource
	private URLBtnService urlBtnService;

	@Resource
	private BusinessService businessService;

	@Override
	public boolean anonymousAccess() {

		return false;
	}

	@Override
	public String getUrl() {
		return "/empInfo/empList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	/*
	 * public String getList(){ List<VEmp> result=(List<VEmp>)service.queryEmpAll(); Map<Object, Object> info = new HashMap<Object, Object>(); info.put("empInfo", result); String b
	 * = JSON.toJSONString(info); b = b.substring(b.indexOf(":")+1, b.length()-1); return b; }
	 */

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");

		String method = arg0.getParameter("method");
		String empId = null == arg0.getParameter("empId") ? "" : arg0.getParameter("empId").toString();// 员工工号
		PrintWriter out = arg1.getWriter();
		String jsonString = "";
		Map<String, Object> parameter = new HashMap();
		try {

			DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();

			if (method.equalsIgnoreCase("export")) {				
				String tempFilePath = arg0.getSession().getServletContext().getRealPath("/resources/emp_info.xlsx");
				List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
				List<String> dataListCell = new ArrayList<String>();
				String fileName = "乐湾物业花名册.xlsx";
				String filePath = "E:/" + fileName;

				List<VEmp> vempList = service.queryEmpAll(parameter);
				dataListCell.add("cur_date");
				dataListCell.add("emp_work_unit");
				dataListCell.add("emp_no");
				dataListCell.add("emp_name");
				dataListCell.add("emp_sex_name");
				dataListCell.add("emp_nation");
				dataListCell.add("emp_birth");
				dataListCell.add("emp_birth_mon");
				dataListCell.add("emp_age");
				dataListCell.add("emp_age_group");
				dataListCell.add("emp_id_no");
				dataListCell.add("emp_politics");
				dataListCell.add("emp_plate");
				dataListCell.add("emp_phone");
				dataListCell.add("emp_dept_name");
				dataListCell.add("emp_work_place");
				dataListCell.add("emp_post_name");
				dataListCell.add("emp_entry_time");
				dataListCell.add("emp_quit_time");
				dataListCell.add("emp_probation");
				dataListCell.add("emp_regularize");
				dataListCell.add("emp_regularize_time");
				dataListCell.add("emp_work_age1");
				dataListCell.add("emp_work_age2");
				dataListCell.add("emp_position_levl");
				dataListCell.add("emp_tech_levl");
				dataListCell.add("emp_tenure_date");
				dataListCell.add("emp_pos_change1");
				dataListCell.add("emp_pos_change2");
				dataListCell.add("emp_training_rec");
				dataListCell.add("emp_reword_punish");
				dataListCell.add("emp_file_store_pos");
				dataListCell.add("emp_file_no");
				dataListCell.add("emp_education");
				dataListCell.add("emp_edu_full_time");
				dataListCell.add("emp_graduate_uni");
				dataListCell.add("emp_major");
				dataListCell.add("emp_certificate");
				dataListCell.add("emp_certifi_no");
				dataListCell.add("emp_certifi_date");
				dataListCell.add("emp_certifi_expire_date");
				dataListCell.add("emp_certifi_check_date");
				dataListCell.add("emp_certifi_lev");
				dataListCell.add("emp_certifi_scan");
				dataListCell.add("emp_employ_type");
				dataListCell.add("emp_contract_subject");
				dataListCell.add("emp_contract_begin_date");
				dataListCell.add("emp_contract_end_date");
				dataListCell.add("emp_contract_remain");
				dataListCell.add("emp_contract_mark");
				dataListCell.add("emp_insured_unit_no");
				dataListCell.add("emp_insured_no");
				dataListCell.add("emp_insured_fee");
				dataListCell.add("emp_insured_date");
				dataListCell.add("emp_funds_unit_no");
				dataListCell.add("emp_funds_no");
				dataListCell.add("emp_funds_fee");
				dataListCell.add("emp_funds_date");
				dataListCell.add("emp_labor_status1");
				dataListCell.add("emp_labor_status2");
				dataListCell.add("emp_labor_status3");
				dataListCell.add("emp_labor_status4");
				dataListCell.add("emp_labor_status5");
				dataListCell.add("emp_labor_status6");
				dataListCell.add("emp_pre_work_unit");
				dataListCell.add("emp_first_work_date");
				dataListCell.add("emp_marriage");
				dataListCell.add("emp_bear");
				dataListCell.add("emp_account");
				dataListCell.add("emp_account_properties");
				dataListCell.add("emp_account_address");
				dataListCell.add("emp_address");
				dataListCell.add("emp_mobile_phone");
				dataListCell.add("emp_contact_info");
				dataListCell.add("emp_residence");
				dataListCell.add("emp_apply");
				dataListCell.add("emp_special");
				dataListCell.add("emp_remark");
				dataListCell.add("emp_flow_chart");
				dataListCell.add("emp_resume");
				dataListCell.add("emp_reg_form");
				dataListCell.add("emp_agreement");
				dataListCell.add("emp_photo");
				dataListCell.add("emp_id_card_copy");
				dataListCell.add("emp_booklet_copy");
				dataListCell.add("emp_diploma_copy");
				dataListCell.add("emp_checkup_report");
				dataListCell.add("emp_leave_certificate");
				dataListCell.add("emp_bondsman_info");
				dataListCell.add("emp_bond");
				dataListCell.add("emp_driver_license");
				dataListCell.add("emp_title_certificate");
				dataListCell.add("emp_probation_wages");
				dataListCell.add("emp_full_wages");
				dataListCell.add("emp_full_wages_date");
				dataListCell.add("emp_fullwages_flag");
				dataListCell.add("emp_real_wages");
				dataListCell.add("emp_raise_wages");
				dataListCell.add("emp_bank_name");
				dataListCell.add("emp_bank_card_no");

				ExcelHandle handle = new ExcelHandle();
				// 物业人员信息插入
				dataList = handle.getVEmp(vempList);
				// 物业信息写入excel模板
				handle.writeListData(tempFilePath, dataListCell, dataList, 0);

				File file = new File(filePath);
				OutputStream os = new FileOutputStream(file);

				handle.writeAndClose(tempFilePath, os);
				handle.readClose(tempFilePath);
				jsonString = JSON.toJSONString("导出Excel成功");

				// 写到输出流并关闭资源
				os.flush();
				os.close();

			}
			if (method.equals("download")) {
				String filePath = "E:/乐湾物业花名册.xlsx";
				this.downloadFile(arg0, arg1, filePath);
			}
			if (method.equalsIgnoreCase("list")) {

				String empDeptId = arg0.getParameter("deptIdFromSearch") == null ? "" : arg0.getParameter("deptIdFromSearch");
				String empPosId = arg0.getParameter("posIdFromSearch") == null ? "" : arg0.getParameter("posIdFromSearch");
				String empState = arg0.getParameter("empStateFromSearch") == null ? "" : arg0.getParameter("empStateFromSearch");
				String startDate = arg0.getParameter("startDateFromSearch") == null ? "" : arg0.getParameter("startDateFromSearch");				
				String endDate = arg0.getParameter("endDateFromSearch") == null ? "" : arg0.getParameter("endDateFromSearch");
			
				parameter.put("empDeptId", empDeptId);
				parameter.put("empPosId", empPosId);
				parameter.put("empState", empState);
				parameter.put("startDate", startDate);
				parameter.put("endDate", endDate);
				List<VEmp> VEmpList = service.queryEmpAll(parameter);
				jsonString = JSON.toJSONString(VEmpList);			
			}
			if (method.equalsIgnoreCase("editEmp") || method.equals("viewEmp")) {

				arg1.setContentType("application/json;charset=utf-8");
				Emp emp = new Emp();
				emp = service.getEmpById(empId);
				jsonString = JSON.toJSONString(emp);
			}
			if (method.equals("save")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String empNo = null == arg0.getParameter("empNo") ? "" : arg0.getParameter("empNo").toString();
				String empName = null == arg0.getParameter("empName") ? "" : arg0.getParameter("empName").toString();
				String empSex = null == arg0.getParameter("empSex") ? "" : arg0.getParameter("empSex").toString();
				String empDeptId = null == arg0.getParameter("empDeptId") ? "" : arg0.getParameter("empDeptId").toString();
				String empPostId = null == arg0.getParameter("empPostId") ? "" : arg0.getParameter("empPostId").toString();
				String empIdNo = null == arg0.getParameter("empIdNo") ? "" : arg0.getParameter("empIdNo").toString();
				String empNation = null == arg0.getParameter("empNation") ? "" : arg0.getParameter("empNation").toString();
				String empPhone = null == arg0.getParameter("empPhone") ? "" : arg0.getParameter("empPhone").toString();
				String empContactInfo = null == arg0.getParameter("empContactInfo") ? "" : arg0.getParameter("empContactInfo").toString();
				String empEntryTime = null == arg0.getParameter("empEntryTime") ? "" : arg0.getParameter("empEntryTime").toString();
				String empQuitTime = null == arg0.getParameter("empQuitTime") ? "" : arg0.getParameter("empQuitTime").toString();
				String empPolitics = null == arg0.getParameter("empPolitics") ? "" : arg0.getParameter("empPolitics").toString();
				String empWorkPlace = null == arg0.getParameter("empWorkPlace") ? "" : arg0.getParameter("empWorkPlace").toString();
				String empProbation = null == arg0.getParameter("empProbation") ? "" : arg0.getParameter("empProbation").toString();
				String empRegularize = null == arg0.getParameter("empRegularize") ? "" : arg0.getParameter("empRegularize").toString();
				String empRegularizeTime = null == arg0.getParameter("empRegularizeTime") ? "" : arg0.getParameter("empRegularizeTime").toString();
				String empPositionLevl = null == arg0.getParameter("empPositionLevl") ? "" : arg0.getParameter("empPositionLevl").toString();
				String empTechLevl = null == arg0.getParameter("empTechLevl") ? "" : arg0.getParameter("empTechLevl").toString();
				String empFileStorePos = null == arg0.getParameter("empFileStorePos") ? "" : arg0.getParameter("empFileStorePos").toString();
				String empPosChange1 = null == arg0.getParameter("empPosChange1") ? "" : arg0.getParameter("empPosChange1").toString();
				String empPosChange2 = null == arg0.getParameter("empPosChange2") ? "" : arg0.getParameter("empPosChange2").toString();
				String empTrainingRec = null == arg0.getParameter("empTrainingRec") ? "" : arg0.getParameter("empTrainingRec").toString();
				String empRewordPunish = null == arg0.getParameter("empRewordPunish") ? "" : arg0.getParameter("empRewordPunish").toString();
				String empEducation = null == arg0.getParameter("empEducation") ? "" : arg0.getParameter("empEducation").toString();
				String empEduFullTime = null == arg0.getParameter("empEduFullTime") ? "" : arg0.getParameter("empEduFullTime").toString();
				String empGraduateUni = null == arg0.getParameter("empGraduateUni") ? "" : arg0.getParameter("empGraduateUni").toString();
				String empMajor = null == arg0.getParameter("empMajor") ? "" : arg0.getParameter("empMajor").toString();
				String empCertificate = null == arg0.getParameter("empCertificate") ? "" : arg0.getParameter("empCertificate").toString();
				String empCertifiNo = null == arg0.getParameter("empCertifiNo") ? "" : arg0.getParameter("empCertifiNo").toString();
				String empCertifiDate = null == arg0.getParameter("empCertifiDate") ? "" : arg0.getParameter("empCertifiDate").toString();
				String empCertifiExpireDate = null == arg0.getParameter("empCertifiExpireDate") ? "" : arg0.getParameter("empCertifiExpireDate").toString();
				String empCertifiCheckDate = null == arg0.getParameter("empCertifiCheckDate") ? "" : arg0.getParameter("empCertifiCheckDate").toString();
				String empCertifiLev = null == arg0.getParameter("empCertifiLev") ? "" : arg0.getParameter("empCertifiLev").toString();
				String empCertifiScan = null == arg0.getParameter("empCertifiScan") ? "" : arg0.getParameter("empCertifiScan").toString();
				String empEmployType = null == arg0.getParameter("empEmployType") ? "" : arg0.getParameter("empEmployType").toString();
				String empContractSubject = null == arg0.getParameter("empContractSubject") ? "" : arg0.getParameter("empContractSubject").toString();
				String empContractBeginDate = null == arg0.getParameter("empContractBeginDate") ? "" : arg0.getParameter("empContractBeginDate").toString();
				String empContractEndDate = null == arg0.getParameter("empContractEndDate") ? "" : arg0.getParameter("empContractEndDate").toString();
				String empLaborStatus = null == arg0.getParameter("empLaborStatus") ? "" : arg0.getParameter("empLaborStatus").toString();
				String empPreWorkUnit = null == arg0.getParameter("empPreWorkUnit") ? "" : arg0.getParameter("empPreWorkUnit").toString();
				String empFirstWorkDate = null == arg0.getParameter("empFirstWorkDate") ? "" : arg0.getParameter("empFirstWorkDate").toString();
				String empMarriage = null == arg0.getParameter("empMarriage") ? "" : arg0.getParameter("empMarriage").toString();
				String empBear = null == arg0.getParameter("empBear") ? "" : arg0.getParameter("empBear").toString();
				String empAccount = null == arg0.getParameter("empAccount") ? "" : arg0.getParameter("empAccount").toString();
				String empAccountAddress = null == arg0.getParameter("empAccountAddress") ? "" : arg0.getParameter("empAccountAddress").toString();
				String empAddress = null == arg0.getParameter("empAddress") ? "" : arg0.getParameter("empAddress").toString();
				String empMobilePhone = null == arg0.getParameter("empMobilePhone") ? "" : arg0.getParameter("empMobilePhone").toString();
				String empApply = null == arg0.getParameter("empApply") ? "" : arg0.getParameter("empApply").toString();
				String empResidence = null == arg0.getParameter("empResidence") ? "" : arg0.getParameter("empResidence").toString();
				String empSpecial = null == arg0.getParameter("empSpecial") ? "" : arg0.getParameter("empSpecial").toString();
				String empRemark = null == arg0.getParameter("empRemark") ? "" : arg0.getParameter("empRemark").toString();
				String empProbationWages = null == arg0.getParameter("empProbationWages") ? "" : arg0.getParameter("empProbationWages").toString();
				String empFullWages = null == arg0.getParameter("empFullWages") ? "" : arg0.getParameter("empFullWages").toString();
				String empFullWagesDate = null == arg0.getParameter("empFullWagesDate") ? "" : arg0.getParameter("empFullWagesDate").toString();
				String empFullWagesFlag = null == arg0.getParameter("empFullWagesFlag") ? "" : arg0.getParameter("empFullWagesFlag").toString();
				String empRealWages = null == arg0.getParameter("empRealWages") ? "" : arg0.getParameter("empRealWages").toString();
				String empRaiseWages = null == arg0.getParameter("empRaiseWages") ? "" : arg0.getParameter("empRaiseWages").toString();
				String empBankName = null == arg0.getParameter("empBankName") ? "" : arg0.getParameter("empBankName").toString();
				String empBankCardNo = null == arg0.getParameter("empBankCardNo") ? "" : arg0.getParameter("empBankCardNo").toString();
				String empFlowChart = null == arg0.getParameter("empFlowChart") ? "" : arg0.getParameter("empFlowChart").toString();
				String empResume = null == arg0.getParameter("empResume") ? "" : arg0.getParameter("empResume").toString();
				String empRegForm = null == arg0.getParameter("empRegForm") ? "" : arg0.getParameter("empRegForm").toString();
				String empAgreement = null == arg0.getParameter("empAgreement") ? "" : arg0.getParameter("empAgreement").toString();
				String empPhoto = null == arg0.getParameter("empPhoto") ? "" : arg0.getParameter("empPhoto").toString();
				String empIDCardCopy = null == arg0.getParameter("empIDCardCopy") ? "" : arg0.getParameter("empIDCardCopy").toString();
				String empBookletCopy = null == arg0.getParameter("empBookletCopy") ? "" : arg0.getParameter("empBookletCopy").toString();
				String empDiplomaCopy = null == arg0.getParameter("empDiplomaCopy") ? "" : arg0.getParameter("empDiplomaCopy").toString();
				String empCheckupReport = null == arg0.getParameter("empCheckupReport") ? "" : arg0.getParameter("empCheckupReport").toString();
				String empLeaveCertificate = null == arg0.getParameter("empLeaveCertificate") ? "" : arg0.getParameter("empLeaveCertificate").toString();
				String empBondsmanInfo = null == arg0.getParameter("empBondsmanInfo") ? "" : arg0.getParameter("empBondsmanInfo").toString();
				String empBond = null == arg0.getParameter("empBond") ? "" : arg0.getParameter("empBond").toString();
				String empDriverLicense = null == arg0.getParameter("empDriverLicense") ? "" : arg0.getParameter("empDriverLicense").toString();
				String empTitleCertificate = null == arg0.getParameter("empTitleCertificate") ? "" : arg0.getParameter("empTitleCertificate").toString();
				String empInsuredUnitNo = null == arg0.getParameter("empInsuredUnitNo") ? "" : arg0.getParameter("empInsuredUnitNo").toString();
				String empInsuredNo = null == arg0.getParameter("empInsuredNo") ? "" : arg0.getParameter("empInsuredNo").toString();
				String empInsuredFee = null == arg0.getParameter("empInsuredFee") ? "" : arg0.getParameter("empInsuredFee").toString();
				String empInsuredDate = null == arg0.getParameter("empInsuredDate") ? "" : arg0.getParameter("empInsuredDate").toString();
				String empFundsUnitNo = null == arg0.getParameter("empFundsUnitNo") ? "" : arg0.getParameter("empFundsUnitNo").toString();
				String empFundsNo = null == arg0.getParameter("empFundsNo") ? "" : arg0.getParameter("empFundsNo").toString();
				String empFundsFee = null == arg0.getParameter("empFundsFee") ? "" : arg0.getParameter("empFundsFee").toString();
				String empFundsDate = null == arg0.getParameter("empFundsDate") ? "" : arg0.getParameter("empFundsDate").toString();
				String empPlate = null == arg0.getParameter("empPlate") ? "" : arg0.getParameter("empPlate").toString();
				String empTenureDate = null == arg0.getParameter("empTenureDate") ? "" : arg0.getParameter("empTenureDate").toString();
				String empFileNo = null == arg0.getParameter("empFileNo") ? "" : arg0.getParameter("empFileNo").toString();
				String empAccountProperties = null == arg0.getParameter("empAccountProperties") ? "" : arg0.getParameter("empAccountProperties").toString();

				Emp emp = new Emp();
				if (empId != null && !empId.isEmpty()) {
					emp = service.getEmpById(empId);
				}
				emp.setEmpNo(empNo);
				emp.setEmpName(empName);
				emp.setEmpSex(empSex);
				emp.setEmpDeptId(empDeptId);
				emp.setEmpPostId(empPostId);
				emp.setEmpIdNo(empIdNo);
				emp.setEmpNation(empNation);
				emp.setEmpPhone(empPhone);
				emp.setEmpContactInfo(empContactInfo);
				if (!empEntryTime.isEmpty()) {
					emp.setEmpEntryTime(sdf.parse(empEntryTime));
				}
				if (!empQuitTime.isEmpty()) {
					emp.setEmpQuitTime(sdf.parse(empQuitTime));
				}
				emp.setEmpPolitics(empPolitics);
				emp.setEmpWorkPlace(empWorkPlace);
				emp.setEmpProbation(empProbation);
				emp.setEmpRegularize(empRegularize);
				if (!empRegularizeTime.isEmpty()) {
					emp.setEmpRegularizeTime(sdf.parse(empRegularizeTime));
				}

				emp.setEmpPositionLevl(empPositionLevl);
				emp.setEmpTechLevl(empTechLevl);
				emp.setEmpFileStorePos(empFileStorePos);
				emp.setEmpPosChange1(empPosChange1);
				emp.setEmpPosChange2(empPosChange2);
				emp.setEmpTrainingRec(empTrainingRec);
				emp.setEmpRewordPunish(empRewordPunish);
				emp.setEmpEducation(empEducation);
				emp.setEmpEduFullTime(empEduFullTime);
				emp.setEmpGraduateUni(empGraduateUni);
				emp.setEmpMajor(empMajor);
				emp.setEmpCertificate(empCertificate);
				emp.setEmpCertifiNo(empCertifiNo);
				if (!empCertifiDate.isEmpty()) {
					emp.setEmpCertifiDate(sdf.parse(empCertifiDate));
				}
				if (!empCertifiExpireDate.isEmpty()) {
					emp.setEmpCertifiExpireDate(sdf.parse(empCertifiExpireDate));
				}
				if (!empCertifiCheckDate.isEmpty()) {
					emp.setEmpCertifiCheckDate(sdf.parse(empCertifiCheckDate));
				}
				emp.setEmpCertifiLev(empCertifiLev);
				emp.setEmpCertifiScan(empCertifiScan);
				emp.setEmpEmployType(empEmployType);
				emp.setEmpContractSubject(empContractSubject);
				if (!empContractBeginDate.isEmpty()) {
					emp.setEmpContractBeginDate(sdf.parse(empContractBeginDate));
				}
				if (!empContractEndDate.isEmpty()) {
					emp.setEmpContractEndDate(sdf.parse(empContractEndDate));
				}

				// emp.setEmpContractMark(empContractMark);
				emp.setEmpLaborStatus(empLaborStatus);
				emp.setEmpPreWorkUnit(empPreWorkUnit);
				if (!empFirstWorkDate.isEmpty()) {
					emp.setEmpFirstWorkDate(sdf.parse(empFirstWorkDate));
				}
				emp.setEmpMarriage(empMarriage);
				emp.setEmpBear(empBear);
				emp.setEmpAccount(empAccount);
				emp.setEmpAccountAddress(empAccountAddress);
				emp.setEmpAddress(empAddress);
				emp.setEmpMobilePhone(empMobilePhone);
				emp.setEmpApply(empApply);
				emp.setEmpResidence(empResidence);
				emp.setEmpSpecial(empSpecial);
				emp.setEmpRemark(empRemark);

				emp.setEmpProbationWages(empProbationWages);
				emp.setEmpFullWages(empFullWages);
				if (!empFullWagesDate.isEmpty()) {
					emp.setEmpFullWagesDate(sdf.parse(empFullWagesDate));
				}
				emp.setEmpFullWagesFlag(empFullWagesFlag);
				emp.setEmpRealWages(empRealWages);
				emp.setEmpRaiseWages(empRaiseWages);
				emp.setEmpBankName(empBankName);
				emp.setEmpBankCardNo(empBankCardNo);
				emp.setEmpFlowChart(empFlowChart);
				emp.setEmpResume(empResume);
				emp.setEmpRegForm(empRegForm);
				emp.setEmpAgreement(empAgreement);
				emp.setEmpPhoto(empPhoto);
				emp.setEmpIDCardCopy(empIDCardCopy);
				emp.setEmpBookletCopy(empBookletCopy);
				emp.setEmpDiplomaCopy(empDiplomaCopy);
				emp.setEmpCheckupReport(empCheckupReport);
				emp.setEmpLeaveCertificate(empLeaveCertificate);
				emp.setEmpBondsmanInfo(empBondsmanInfo);
				emp.setEmpBond(empBond);
				emp.setEmpDriverLicense(empDriverLicense);
				emp.setEmpTitleCertificate(empTitleCertificate);
				emp.setEmpInsuredUnitNo(empInsuredUnitNo);
				emp.setEmpInsuredNo(empInsuredNo);
				emp.setEmpInsuredFee(empInsuredFee);
				if (!empInsuredDate.isEmpty()) {
					emp.setEmpInsuredDate(sdf.parse(empInsuredDate));
				}
				emp.setEmpFundsUnitNo(empFundsUnitNo);
				emp.setEmpFundsFee(empFundsFee);
				if (!empFundsDate.isEmpty()) {
					emp.setEmpFundsDate(sdf.parse(empFundsDate));
				}
				emp.setEmpPositionLevl(empPlate);
				if (!empTenureDate.isEmpty()) {
					emp.setEmpTenureDate(sdf.parse(empTenureDate));
				}
				emp.setEmpFileNo(empFileNo);
				emp.setEmpAccountProperties(empAccountProperties);
				emp.setEmpFundsNo(empFundsNo);

				service.addEmp(emp);
				jsonString = JSON.toJSONString("succese");	
			}
			if (method.equalsIgnoreCase("deleteEmp")) {

				if (!StringUtil.isEmpty(empId)) {
					service.deleteEmp(empId);
				}
				jsonString = JSON.toJSONString("删除成功！");
			}
			if (method.equalsIgnoreCase("initDropAll")) {
				List<DirDirectoryDetail> positions = service.getDirectoryLikeCode("emp_status");
				jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
			}
			if (method.equalsIgnoreCase("initDeptDrop")) {				
				List<DefaultDept> depts = (List<DefaultDept>) service.queryDept(parameter);
				jsonString = JSON.toJSONString(depts);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");

			}
			if (method.equalsIgnoreCase("initPositionDrop")) {
				
				List<DefaultPosition> positions = (List<DefaultPosition>) service.queryPosition(parameter);
				jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
			}
			if (method.equalsIgnoreCase("initPermissions")) {
				String pathName = null == arg0.getParameter("pathName") ? "" : arg0.getParameter("pathName").toString();
				RoleMember role = businessService.getRoleMemberByUsername(CommUser.getUserName());
				if (role != null) {
					String projectName = arg0.getContextPath();
					pathName = pathName.substring(projectName.length() + 1);
					System.out.println(pathName);
					List<VRoleBtn> roleBtn = urlBtnService.getAuthBtnByRole(role.getRoleId(), pathName);
					jsonString = JSON.toJSONString(roleBtn);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma", "no-cache");
					arg1.setHeader("cache-control", "no-cache");
				}
			}
			if (method.equalsIgnoreCase("initEmpDept")) {				
				List<Object> objs = service.queryEmpDept(parameter);
				if (objs.size() > 0) {
					jsonString = JSON.toJSONString(objs);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma", "no-cache");
					arg1.setHeader("cache-control", "no-cache");
				}
			}

			if (method.equalsIgnoreCase("getEmpById")) {
				Emp emp = service.getEmpById(empId);
				if (emp != null) {
					jsonString = JSON.toJSONString(emp);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma", "no-cache");
					arg1.setHeader("cache-control", "no-cache");
				}

			}
			if (method.equalsIgnoreCase("getEmpByDeptId")) {
				String deptId = null == arg0.getParameter("deptId") ? "" : arg0.getParameter("deptId").toString();

				List<Emp> emp = service.getEmpByDeptId(deptId);
				if (emp != null) {
					jsonString = JSON.toJSONString(emp);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma", "no-cache");
					arg1.setHeader("cache-control", "no-cache");
				}

			}
			if (method.equalsIgnoreCase("getEmpAllNum")) {
				List<Emp> emp = service.getEmpAllNum();
				if (emp != null) {
					String num = emp.size() + "";
					jsonString = JSON.toJSONString(num);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma", "no-cache");
					arg1.setHeader("cache-control", "no-cache");

				}
			}

			if (method.equalsIgnoreCase("bsEmpInfo")) { // 下拉搜索框
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"), "UTF-8");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("keyword", keyword);
				List<VEmp> emp = service.queryEmpByKeyword(param);
				jsonString = JSON.toJSONString(emp);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			jsonString = JSON.toJSONString("操作失败！");
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
			out.close();
		}
	}

	public String inputFile(HttpServletRequest arg0, HttpServletResponse arg1, String folder) throws IOException, ServletException {

		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = "D:/upload/emp/" + folder;
		String paths = "";
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdirs();
		}

		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(arg0)) {
				// 按照传统方式获取数据
				return null;
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(arg0);

			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String filename = item.getName();
					String ext = filename.substring(filename.lastIndexOf(".") + 1);
					Date d = new Date();
					String fileType = Long.toString(d.getTime()) + "." + ext;
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}

					if (paths.isEmpty()) {
						paths = savePath + "/" + fileType;
					} else {
						paths += " ;" + savePath + "/" + fileType;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + fileType);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();
					// return "true";
				}
			}
			return paths;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String filepath) {
		// path是指欲下载的文件的路径。
		try {
			File file = new File(filepath);
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
