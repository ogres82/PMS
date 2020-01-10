selections = [];
var loginUserName = "";
var loginUserCname = "";
$(function() {
	init();
	// 定位表格查询框
	$(".search").css({
		'position' : 'fixed',
		'right' : '10px',
		'top' : '0',
		'z-index' : '1000',
		'width' : '200px'

	});
	$(".search input").attr("placeholder", "搜索");
	$(".search input").css({
		'padding-right' : '15px'
	});
	$('#myForm1').validationEngine();
	initBtnEvent();
	$("#btn_export").bind('click', function() {
		exportExcel();
	});

	$('#buttonSearch').click(function() {
		$('#empInfo').bootstrapTable('refresh');
	});

});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {
	var urlmethod = "";
	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				$('#btEmpAdd').show();

				clearForm();
				$("#myModalTitle").html("物业人员信息");
				$('#myModal1').modal({
					backdrop : 'static',
					keyboard : false
				});

			});
		}
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				$('#btEmpAdd').show();
				selections = getIdSelections();

				urlmethod = "method=deleteEmp";

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../empInfo/empList.app?" + urlmethod;
					$.post(url, {
						empId : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#empInfo').bootstrapTable('refresh');
						});
					});
				}, function() {
					return;
				})

			});
		}
	}

}

function init() {

	initPermissions();
	initDrops();
	initDeptDrop();
	initPostionDrop();
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#empInfo').bootstrapTable({
			url : './../empInfo/empList.app?method=list', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			queryParams : oTableInit.queryParams,
			showExport : "true",
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "right",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			exportDataType : "all",
			columns : [ {
				field : 'state',
				checkbox : true,
			}, {
				field : 'empId',
				visible : false,
				title : 'ID'
			}, {
				field : 'empNo',
				searchable : true,
				title : '工号'
			}, {
				field : 'empName',
				title : '姓名'
			}, {
				field : 'empSexName',
				title : '性别'
			}, {
				field : 'empDeptName',
				title : '所属部门'
			}, {
				field : 'empPostName',
				title : '岗位'
			}, {
				field : 'empPhone',
				title : '联系电话'
			}, {
				field : 'empEntryTime',
				title : '入职时间',
				formatter : dateFormate
			}, {
				field : 'empIdNo',
				title : '身份证号'
			}, {
				field : 'empStatusName',
				title : '劳动关系状态'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				events : operateEvents,
				formatter : operateFormatter
			} ],
			onCheck : function(row, e) {
				tableCheckEvents();
			},
			onUncheck : function(row, e) {
				tableCheckEvents();
			},
			onCheckAll : function(rows) {
				$("#btn_del").attr("disabled", false);
			},
			onUncheckAll : function(rows) {
				$("#btn_del").attr("disabled", true);
			},
			onLoadSuccess : function(rows) {
				$("#btn_del").attr("disabled", true);
			}
		});
	};

	function operateFormatter(value, row, index) {

		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			editOrCheck(row, 1);
		},
		'click #a_edit' : function(e, value, row, index) {
			editOrCheck(row, 2);
		},

	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search,
			// 表格搜索框的值
			deptIdFromSearch : $("#deptIdFromSearch").val(),
			posIdFromSearch : $("#posIdFromSearch").val(),
			empStateFromSearch : $("#empStateFromSearch").val(),
			startDateFromSearch : $("#startDateFromSearch").val(),
			endDateFromSearch : $("#endDateFromSearch").val()
		};
		return temp;
	};
	dateFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2Date(value);
		}

	};
	return oTableInit;
};
// 表格选择事件
function tableCheckEvents() {
	var r = $('#empInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

// 初始化下拉框
function initDrops() {
	var url = "./../empInfo/empList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			var code = detailList[0];
			// 员工状态
			if (code == 'emp_status') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#empStatus");
			}

		}
		;
	});

}

/**
 * 初始化部门下拉框
 */
function initDeptDrop() {
	var url = "./../empInfo/empList.app?method=initDeptDrop";
	$.post(url, function(data) {

		var list = eval(data);

		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 员工部门
			$("<option value='" + detailList.id + "'>" + detailList.name + "</option>").appendTo("#empDeptId");
			$("<option value='" + detailList.id + "'>" + detailList.name + "</option>").appendTo("#deptIdFromSearch");
		}
		;
	});
}

/**
 * 初始化岗位下拉框
 */
function initPostionDrop() {
	var url = "./../empInfo/empList.app?method=initPositionDrop";
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 员工岗位
			$("<option value='" + detailList.id + "'>" + detailList.name + "</option>").appendTo("#empPostId");
			$("<option value='" + detailList.id + "'>" + detailList.name + "</option>").appendTo("#posIdFromSearch");
		}
		;
	});
}


/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#empInfo').bootstrapTable('getSelections'), function(row) {
		return row.empId;
	});
}

function getNameSelections() {
	return $.map($('#empInfo').bootstrapTable('getSelections'), function(row) {
		return row.empName;
	});
}
/**
 * 保存操作
 */
function openSaveButton() {

	var flag = $('#myForm1').validationEngine('validate');
	var start = $("#empEntryTime").val();
	var end = $("#empQuitTime").val();

	if (start > end && end != "") {
		layer.msg('离职时间不能小于入职时间！', {
			time : 2000
		});
		return;
	}
	if (flag) {
		var addJson = getDataForm();
		$.ajax({
			type : "post",
			url : "./../empInfo/empList.app?method=save",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#empInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 3000
				}, function() {

				});
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
		});
	}

}

/**
 * 获取表单数据
 * 
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {
	var addJson = {
		empWorkUnit : $("#empWorkUnit").val(),
		empBirthMon : $("#empBirthMon").val(),
		empAgeGroup : $("#empAgeGroup").val(),
		empPhoto : $("#empPhoto").val(),
		empFlowChart : $("#empFlowChart").val(),
		empResume : $("#empResume").val(),
		empRegForm : $("#empRegForm").val(),
		empAgreement : $("#empAgreement").val(),
		empIDCardCopy : $("#empIDCardCopy").val(),
		empBookletCopy : $("#empBookletCopy").val(),
		empDiplomaCopy : $("#empDiplomaCopy").val(),
		empCheckupReport : $("#empCheckupReport").val(),
		empLeaveCertificate : $("#empLeaveCertificate").val(),
		empBondsmanInfo : $("#empBondsmanInfo").val(),
		empBond : $("#empBond").val(),
		empDriverLicense : $("#empDriverLicense").val(),
		empTitleCertificate : $("#empTitleCertificate").val(),
		empPlate : $("#empPlate").val(),
		empTenureDate : $("#empTenureDate").val(),
		empFileNo : $("#empFileNo").val(),
		empInsuredUnitNo : $("#empInsuredUnitNo").val(),
		empInsuredNo : $("#empInsuredNo").val(),
		empInsuredFee : $("#empInsuredFee").val(),
		empInsured : $("#empInsured").val(),
		empFundsUnitNo : $("#empFundsUnitNo").val(),
		empFundsNo : $("#empFundsNo").val(),
		empFundsFee : $("#empFundsFee").val(),
		empFunds : $("#empFunds").val(),
		empProbationWages : $("#empProbationWages").val(),
		empFullWages : $("#empFullWages").val(),
		empFullWagesDate : $("#empFullWagesDate").val(),
		empFullWagesFlag : $("#empFullWagesFlag").val(),
		empRealWages : $("#empRealWages").val(),
		empRaiseWages : $("#empRaiseWages").val(),
		empBankName : $("#empBankName").val(),
		empBankCardNo : $("#empBankCardNo").val(),

		empId : $("#empId").val(),
		empNo : $("#empNo").val(),
		empName : $("#empName").val(),
		empSex : $("#empSex").val(),
		empDeptId : $("#empDeptId").val(),
		empPostId : $("#empPostId").val(),
		empBirth : $("#empBirth").val(),
		empIdNo : $("#empIdNo").val(),
		empNation : $("#empNation").val(),
		empPhone : $("#empPhone").val(),
		empContactInfo : $("#empContactInfo").val(),
		empEntryTime : $("#empEntryTime").val(),
		empQuitTime : $("#empQuitTime").val(),
		empStatus : $("#empStatus").val(),
		empAge : $("#empAge").val(),
		empPolitics : $("#empPolitics").val(),
		empWorkPlace : $("#empWorkPlace").val(),
		empProbation : $("#empProbation").val(),
		empRegularize : $("#empRegularize").val(),
		empRegularizeTime : $("#empRegularizeTime").val(),
		empWorkAge : $("#empWorkAge").val(),
		empPositionLevl : $("#empPositionLevl").val(),
		empTechLevl : $("#empTechLevl").val(),
		empFileStorePos : $("#empFileStorePos").val(),
		empPosChange1 : $("#empPosChange1").val(),
		empPosChange2 : $("#empPosChange2").val(),
		empTrainingRec : $("#empTrainingRec").val(),
		empRewordPunish : $("#empRewordPunish").val(),
		empEducation : $("#empEducation").val(),
		empEduFullTime : $("#empEduFullTime").val(),
		empGraduateUni : $("#empGraduateUni").val(),
		empMajor : $("#empMajor").val(),
		empCertificate : $("empCertificate").val(),
		empCertifiNo : $("#empCertifiNo").val(),
		empCertifiDate : $("#empCertifiDate").val(),
		empCertifiExpireDate : $("#empCertifiExpireDate").val(),
		empCertifiCheckDate : $("#empCertifiCheckDate").val(),
		empCertifiLev : $("#empCertifiLev").val(),
		empCertifiScan : $("#empCertifiScan").val(),
		empEmployType : $("#empEmployType").val(),
		empContractSubject : $("#empContractSubject").val(),
		empContractBeginDate : $("#empContractBeginDate").val(),
		empContractEndDate : $("#empContractEndDate").val(),
		empContractRemain : $("#empContractRemain").val(),
		empContractMark : $("#empContractMark").val(),
		empLaborStatus : $("#empLaborStatus").val(),
		empPreWorkUnit : $("#empPreWorkUnit").val(),
		empFirstWorkDate : $("#empFirstWorkDate").val(),
		empMarriage : $("#empMarriage").val(),
		empBear : $("#empBear").val(),
		empAccount : $("#empAccount").val(),
		empAccountAddress : $("#empAccountAddress").val(),
		empAddress : $("#empAddress").val(),
		empMobilePhone : $("#empMobilePhone").val(),
		empApply : $("#empApply").val(),
		empResidence : $("#empResidence").val(),
		empSpecial : $("#empSpecial").val(),
		empRemark : $("#empRemark").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#empId").val("");
	$("#empWorkUnit").val("乐湾物业");
	$("#empNo").val("");
	$("#empName").val("");
	$("#empIdNo").val("");
	$("#empPhone").val("");
	$("#empSex option[value='1']").attr("selected", "selected");
	$("#empNation").val("");
	$("#empPolitics").val("");
	$("#empBirth").val("");
	$("#empAge").val("");
	$("#empBirthMon").val("");
	$("#empAgeGroup").val("");
	$("#empFlowChart").val("");
	$("#empResume").val("");
	$("#empRegForm").val("");
	$("#empAgreement").val("");
	$("#empPhoto").val("");
	$("#empCheckupReport").val("");
	$("#empIDCardCopy").val("");
	$("#empBookletCopy").val("");
	$("#empDiplomaCopy").val("");
	$("#empLeaveCertificate").val("");
	$("#empDriverLicense").val("");
	$("#empTitleCertificate").val("");
	$("#empBondsmanInfo").val("");
	$("#empBond").val("");
	$("#empPlate").val("");
	$("#empDeptId").val("");
	$("#empPostId").val("");
	$("#empWorkPlace").val("贵阳");
	$("#empEntryTime").val("");
	$("#empQuitTime").val("");
	$("#empProbation").val("");
	$("#empRegularize").val("");
	$("#empRegularizeTime").val("");
	$("#empWorkAge").val("");
	$("#empPositionLevl").val("");
	$("#empTechLevl").val("");
	$("#empTenureDate").val("");
	$("#empFileStorePos").val("");
	$("#empFileNo").val("");
	$("#empPosChange1").val("");
	$("#empPosChange2").val("");
	$("#empTrainingRec").val("");
	$("#empRewordPunish").val("");
	$("#empEducation").val("");
	$("#empEduFullTime").val("");
	$("#empGraduateUni").val("");
	$("#empMajor").val("");
	$("#empCertificate").val("");
	$("#empCertifiNo").val("");
	$("#empCertifiDate").val("");
	$("#empCertifiExpireDate").val("");
	$("#empCertifiCheckDate").val("");
	$("#empCertifiLev").val("");
	$("#empCertifiScan").val("");
	$("#empEmployType option[value='0']").attr("selected", "selected");
	$("#empContractSubject").val("");
	$("#empContractBeginDate").val("");
	$("#empContractEndDate").val("");
	$("#empContractRemain").val("0");
	$("#empContractMark").val("尚未到期");
	$("#empLaborStatus option[value='0']").attr("selected", "selected");
	$("#empPreWorkUnit").val("");
	$("#empFirstWorkDate").val("");
	$("#empMarriage").val("");
	$("#empBear").val("");
	$("#empSpecial").val("");
	$("#empAccountAddress").val("");
	$("#empAddress").val("");
	$("#empMobilePhone").val("");
	$("#empApply").val("");
	$("#empResidence option[value='0']").attr("selected", "selected");
	$("#empContactInfo").val("");
	$("#empAccount").val("");
	$("#empAccountProperties").val("");
	$("#empRemark").val("");
	$("#empInsuredUnitNo").val("");
	$("#empInsuredNo").val("");
	$("#empInsuredFee").val("");
	$("#empInsuredDate").val("");
	$("#empFundsUnitNo").val("");
	$("#empFundsNo").val("");
	$("#empFundsFee").val("");
	$("#empFundsDate").val("");
	$("#empProbationWages").val("");
	$("#empFullWages").val("");
	$("#empFullWagesDate").val("");
	$("#empFullWagesFlag").val("");
	$("#empRealWages").val("");
	$("#empRaiseWages").val("");
	$("#empBankName").val("");
	$("#empBankCardNo").val("");
}

// 自动填写表格
function autoFill() {
	var userID = $("#empIdNo").val();
	var userBr;
	if (userID == '' || userID == null) {
		return null;
	}
	// 获取生日
	userBr = userID.substring(6, 10) + "-" + userID.substring(10, 12) + "-" + userID.substring(12, 14);
	$("#empBirth").val(userBr);
	// 获取出生月
	$("#empBirthMon").val(userBr.substring(5, 7));

	// 获取性别
	if (parseInt(userID.substring(16, 1)) % 2 == 1) {
		$("#empSex option[value='1']").attr("selected", "selected");
	} else {
		$("#empSex option[value='0']").attr("selected", "selected");
	}
	// 获取年龄
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var age = myDate.getFullYear() - userID.substring(6, 10) - 1;
	if (userID.substring(10, 12) < month || userID.substring(10, 12) == month && userID.substring(12, 14) <= day) {
		age++;
	}
	// 获取年龄段
	if (age < 31) {
		$("#empAgeGroup").val("30");
	} else if (age < 41) {
		$("#empAgeGroup").val("40");
	} else if (age < 51) {
		$("#empAgeGroup").val("50");
	} else
		$("#empAgeGroup").val("60");
	// 获取年龄
	$("#empAge").val(age);
	//
	// 合同剩余天数和合同到期提醒
	var endDate = $("#empContractEndDate").val();
	var array = endDate.split("-");
	var endTime = new Date(parseInt(array[0]), parseInt(array[1]) - 1, parseInt(array[2]));
	var nowTime = new Date(parseInt(myDate.getFullYear()), parseInt(myDate.getMonth()), parseInt(myDate.getDate()));
	var days = (Number(endTime) - Number(nowTime)) / (1000 * 60 * 60 * 24);
	$("#empContractRemain").val(days);
	if (days < 0) {
		$("#empContractMark").val("合同过期");
	} else if (days < 7) {
		$("#empContractMark").val("合同马上到期");
	} else if (days < 30) {
		$("#empContractMark").val("30天内到期");
	} else {
		$("#empContractMark").val("尚未到期");
	}
	// 获取工龄
	if ($("#empEntryTime").val() == '' || $("#empEntryTime").val() == null) {
		$("#empWorkAge").val("0年0月");
	} else {
		var workAge = new Date();
		var entryArray = $("#empEntryTime").val().split("-");
		var entryDate = new Date(parseInt(entryArray[0]), parseInt(entryArray[1]) - 1, parseInt(entryArray[2]));// 因为月份值0-11，这里把输入的月份减一

		// 获取输入的入职时间转换为Date
		if ($("#empQuitTime").val() == '' || $("#empQuitTime").val() == null) {
			workAge.setTime(nowTime.getTime() - entryDate.getTime());
		} else {
			var quitTimeArray = $("#empQuitTime").val().split("-");
			var quitDate = new Date(parseInt(quitTimeArray[0]), parseInt(quitTimeArray[1]) - 1, parseInt(quitTimeArray[2]));
			workAge.setTime(quitDate.getTime() - entryDate.getTime());
		}
		var strWorkAge = (workAge.getFullYear() - 1970) + "年" + workAge.getMonth() + "月";
		$("#empWorkAge").val(strWorkAge);
	}

}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btEmpAdd').hide();
	} else {
		$('#btEmpAdd').show();
	}
	$("#empId").val(obj.empId);
	$("#empWorkUnit").val("乐湾物业");
	$("#empNo").val(obj.empNo);
	$("#empName").val(obj.empName);
	$("#empIdNo").val(obj.empIdNo);
	$("#empPhone").val(obj.empPhone);
	$("#empSex").val(obj.empSex);
	$("#empNation").val(obj.empNation);
	$("#empPolitics").val(obj.empPolitics);
	$("#empFlowChart").val(obj.empFlowChart);
	$("#empResume").val(obj.empResume);
	$("#empRegForm").val(obj.empRegForm);
	$("#empAgreement").val(obj.empAgreement);
	$("#empPhoto").val(obj.empPhoto);
	$("#empCheckupReport").val(obj.empCheckupReport);
	$("#empIDCardCopy").val(obj.empIDCardCopy);
	$("#empBookletCopy").val(obj.empBookletCopy);
	$("#empDiplomaCopy").val(obj.empDiplomaCopy);
	$("#empLeaveCertificate").val(obj.empLeaveCertificate);
	$("#empDriverLicense").val(obj.empDriverLicense);
	$("#empTitleCertificate").val(obj.empTitleCertificate);
	$("#empBondsmanInfo").val(obj.empBondsmanInfo);
	$("#empBond").val(obj.empBond);
	$("#empPlate").val(obj.empPlate);
	$("#empDeptId").val(obj.empDeptId);
	$("#empPostId").val(obj.empPostId);
	$("#empWorkPlace").val("贵阳");
	$("#empEntryTime").val(getNowFormatDate(false, obj.empEntryTime));
	$("#empQuitTime").val(getNowFormatDate(false, obj.empQuitTime));
	$("#empProbation").val(obj.empProbation);
	$("#empRegularize").val(obj.empRegularize);
	$("#empRegularizeTime").val(getNowFormatDate(false, obj.empRegularizeTime));
	$("#empPositionLevl").val(obj.empPositionLevl);
	$("#empTechLevl").val(obj.empTechLevl);
	$("#empTenureDate").val(getNowFormatDate(false, obj.empTenureDate));
	$("#empFileStorePos").val(obj.empFileStorePos);
	$("#empFileNo").val(obj.empFileNo);
	$("#empPosChange1").val(obj.empPosChange1);
	$("#empPosChange2").val(obj.empPosChange2);
	$("#empTrainingRec").val(obj.empTrainingRec);
	$("#empRewordPunish").val(obj.empRewordPunish);
	$("#empEducation").val(obj.empEducation);
	$("#empEduFullTime").val(obj.empEduFullTime);
	$("#empGraduateUni").val(obj.empGraduateUni);
	$("#empMajor").val(obj.empMajor);
	$("#empCertificate").val(obj.empCertificate);
	$("#empCertifiNo").val(obj.empCertifiNo);
	$("#empCertifiDate").val(getNowFormatDate(false, obj.empCertifiDate));
	$("#empCertifiExpireDate").val(getNowFormatDate(false, obj.empCertifiExpireDate));
	$("#empCertifiCheckDate").val(getNowFormatDate(false, obj.empCertifiCheckDate));
	$("#empCertifiLev").val(obj.empCertifiLev);
	$("#empCertifiScan").val(obj.empCertifiScan);
	$("#empEmployType").val(obj.empEmployType);
	$("#empContractSubject").val(obj.empContractSubject);
	$("#empContractBeginDate").val(getNowFormatDate(false, obj.empContractBeginDate));
	$("#empContractEndDate").val(getNowFormatDate(false, obj.empContractEndDate));
	$("#empLaborStatus").val(obj.empLaborStatus);
	$("#empPreWorkUnit").val(obj.empPreWorkUnit);
	$("#empFirstWorkDate").val(getNowFormatDate(false, obj.empFirstWorkDate));
	$("#empMarriage").val(obj.empMarriage);
	$("#empBear").val(obj.empBear);
	$("#empSpecial").val(obj.empSpecial);
	$("#empAccountAddress").val(obj.empAccountAddress);
	$("#empAddress").val(obj.empAddress);
	$("#empMobilePhone").val(obj.empMobilePhone);
	$("#empApply").val(obj.empApply);
	$("#empResidence").val(obj.empResidence);
	$("#empContactInfo").val(obj.empContactInfo);
	$("#empAccount").val(obj.empAccount);
	$("#empAccountProperties").val(obj.empAccountProperties);
	$("#empRemark").val(obj.empRemark);
	$("#empInsuredUnitNo").val(obj.empInsuredUnitNo);
	$("#empInsuredNo").val(obj.empInsuredNo);
	$("#empInsuredFee").val(obj.empInsuredFee);
	$("#empInsuredDate").val(getNowFormatDate(false, obj.empInsuredDate));
	$("#empFundsUnitNo").val(obj.empFundsUnitNo);
	$("#empFundsNo").val(obj.empFundsNo);
	$("#empFundsFee").val(obj.empFundsFee);
	$("#empFundsDate").val(getNowFormatDate(false, obj.empFundsDate));
	$("#empProbationWages").val(obj.empProbationWages);
	$("#empFullWages").val(obj.empFullWages);
	$("#empFullWagesDate").val(getNowFormatDate(false, obj.empFullWagesDate));
	$("#empFullWagesFlag").val(obj.empFullWagesFlag);
	$("#empRealWages").val(obj.empRealWages);
	$("#empRaiseWages").val(obj.empRaiseWages);
	$("#empBankName").val(obj.empBankName);
	$("#empBankCardNo").val(obj.empBankCardNo);
	autoFill();
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/**
 * 导出excel
 */
function exportExcel() {
	initBlockUI();
	var url = "./../empInfo/empList.app?method=export";
	$.post(url, function(data) {
		$.unblockUI();
		var url1 = "./../empInfo/empList.app?method=download";
		window.location.href = url1;
	});
}

function clearSearch() {
	$("#deptIdFromSearch").val("");
	$("#posIdFromSearch").val("");
	$("#empStateFromSearch").val("");
	$("#startDateFromSearch").val("");
	$("#endDateFromSearch").val("");
	$('#buttonSearch').click();
}
