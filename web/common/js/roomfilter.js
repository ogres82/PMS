function RoomFilter(options) {
	this.buildCtrlId = options.buildCtrlId;
	this.communityCtrlId = options.communityCtrlId;// 必填
	this.belongCtrlId = options.belongCtrlId;// 必填
	this.unitCtrlId = options.unitCtrlId;
	this.roomCtrlId = options.roomCtrlId;
	this.init = function() {

		var optionList = [];
		if (this.buildCtrlId != undefined && this.buildCtrlId.trim() != "") {
			optionList
					.push({
						id : this.buildCtrlId,
						url : getRootPath()
								+ "houseProperty/housePropertyList.app?method=initAllAreaDrop",
						nullLabel : "所属楼盘",
						nullVal : "",
						itemF : function(item) {
							return "<option value='" + item.buildId + "'>"
									+ item.buildName + "</option>";
						},
						queryDataF : undefined
					});
		}

		optionList
				.push({
					id : this.communityCtrlId,
					url : getRootPath()
							+ "houseProperty/housePropertyList.app?method=initAreaPropertyDrop",
					nullLabel : "所属小区",
					nullVal : "",
					itemF : function(item) {
						return "<option value='" + item.communityId + "'>"
								+ item.communityName + "</option>";
					},
					queryDataF : function(parentV) {
						return {
							buildId : parentV
						};
					}
				});
		optionList
				.push({
					id : this.belongCtrlId,
					url : getRootPath()
							+ "houseProperty/housePropertyList.app?method=initBuildingPropertyDrop",
					nullLabel : "所属楼宇",
					nullVal : "",
					itemF : function(item) {
						return "<option value='" + item.storiedBuildId + "'>"
								+ item.storiedBuildName + "</option>";
					},
					queryDataF : function(parentV) {
						return {
							communityId : parentV
						};
					}
				});
		if (this.unitCtrlId != undefined && this.unitCtrlId.trim() != "") {
			optionList
					.push({
						id : this.unitCtrlId,
						url : getRootPath() + "buildingUnitInfo.app?method=list",
						nullLabel : "所属单元",
						nullVal : "",
						itemF : function(item) {
							return "<option value='" + item.unitId + "'>"
									+ item.unitName + "</option>";
						},
						queryDataF : function(parentV) {
							return {
								storiedBuildId : parentV
							};
						}
					});
		}
		if (this.roomCtrlId != undefined && this.roomCtrlId.trim() != "") {
			optionList
					.push({
						id : this.roomCtrlId,
						url : getRootPath() +"houseProperty/housePropertyList.app?method=RelevanceSelect&level=2",
						nullLabel : "所属房间",
						nullVal : "",
						itemF : function(item) {
							return "<option value='" + item.buildId + "'>"
									+ item.buildName + "</option>";
						},
						queryDataF : function(parentV) {

						}
					});
		}
		this.relevanceSelect = new RelevanceSelect(optionList);
	};
	this.setVal = function(value) {
		var defValArr = [];
		if (this.buildCtrlId != undefined && value.buildDef != undefined) {
			defValArr.push({
				defVal : value.buildDef,
				disabled : value.buildDisabled
			});
			if (this.communityCtrlId != undefined
					&& value.communityDef != undefined) {
				defValArr.push({
					defVal : value.communityDef,
					disabled : value.communityDisabled
				});
				if (this.belongCtrlId != undefined
						&& value.belongDef != undefined) {
					defValArr.push({
						defVal : value.belongDef,
						disabled : value.belongDisabled
					});
					if (this.unitCtrlId != undefined
							&& value.unitDef != undefined) {
						defValArr.push({
							defVal : value.unitDef,
							disabled : value.unitDisabled
						});
						if (this.roomCtrlId != undefined
								&& roomDef.roomDef != undefined) {
							defValArr.push({
								defVal : value.roomDef,
								disabled : value.roomDisabled
							});
						}
					}
				}
			}
		}

		this.relevanceSelect.setAllDefVal(defValArr);
	}
	this.val = function() {
		return {
			buildCtrlId : this.buildCtrlId == undefined ? undefined : $(
					'#' + this.buildCtrlId).val(),
			communityCtrlId : this.communityCtrlId == undefined ? undefined
					: $('#' + this.communityCtrlId).val(),
			belongCtrlId : this.belongCtrlId == undefined ? undefined : $(
					'#' + this.belongCtrlId).val(),
			unitCtrlId : this.unitCtrlId == undefined ? undefined : $(
					'#' + this.unitCtrlId).val(),
			roomCtrlId : this.roomCtrlId == undefined ? undefined : $(
					'#' + this.roomCtrlId).val()
		};
	};
	this.init();
}

/**
 * 多个下拉框进行级联封装类
 * 
 * @options:定义关联下拉框基本信息，每一个基本信息定义一个下拉框的选项
 * @注意:数组第一项可以多一个zeroVal属性，指明在初始化第一个下拉框的时候使用的请求数据，如果为Undefined 则默认使用 ""
 * @注意:没有具体数值的选项最好就不要传，没有必要传""
 * @id:下拉框控件的ID,必填
 * @url:下拉框获取数据的URl,必填
 * @nullLabel:下拉框默认的标题,如果没有则为"--请选择--"
 * @defVal:初始化时候每一个下拉框的默认值，可以不填表示没有默认值
 * @nullVal:下拉框默认的值，如果没有则为""
 * @itemF:该函返回结果每一项生成Option的函数，如果为Undefined 则默认值为item.value属性、默认显示为item.label
 * @queryDataF:该函数返回下拉框数据刷新时请求的参数，如果为Undefined 则默认为{value : parentV}
 */
function RelevanceSelect(options) {
	if (!Array.isArray(options) || options.length < 1)
		return;
	this.iniOptions = function(options) {
		this.flag = Math.random();
		this.zeroVal = options[0].zeroVal || "";
		var length = options.length;
		this.options = [];
		var needSetDef = true;
		var disabled = true;
		for (var i = 0; i < length; i++) {
			needSetDef = needSetDef && options[i].defVal != undefined;
			disabled = disabled && options[i].disabled == true;
			this.options.push({
				index : i,
				id : options[i].id,
				url : options[i].url,
				nullLabel : options[i].nullLabel || "--请选择--",
				nullVal : options[i].nullVal || "",
				defVal : options[i].defVal || (options[i].nullVal || ""),
				needSetDef : needSetDef,
				disabled : disabled,
				itemF : options[i].itemF || this.itemF,
				queryDataF : options[i].queryDataF || this.queryDataF
			});
			this.iniNullOption(this.options[i]);
		}
	}
	this.ini = function() {
		for (var i = 0; i < this.options.length; i++) {
			this.oneAddChange(this.options[i]);
		}
		this.iniChildSelect(this.options[0].id, this.zeroVal);
	}
	this.queryDataF = function(parentV) {
		return {
			value : parentV
		};
	}
	this.itemF = function(item) {
		return "<option value='" + item.value + "'> " + item.label
				+ "</option>";
	}
	this.iniNullOption = function(option) {
		$(
				"<option value='" + option.nullVal + "'>" + option.nullLabel
						+ "</option>").appendTo("#" + option.id);
	}
	this.setAllDefVal = function(defValArr) {
		if (!Array.isArray(defValArr) || defValArr.length < 1)
			return;
		var needSetDef = true;
		var disabled = true;
		for (var i = 0; i < this.options.length; i++) {
			var option = this.options[i];
			var defVal = defValArr[i] || {};
			option.defVal = defVal.defVal || option.nullVal;
			needSetDef = needSetDef && option.defVal != option.nullVal;
			disabled = disabled && defVal.disabled == true;
			option.needSetDef = needSetDef;
			option.disabled = disabled;
		}
		console.log(this.options);
		this.iniChildSelect(this.options[0].id, this.zeroVal);
	}
	this.clearOptions = function(option) {
		$("#" + option.id).empty();
		this.iniNullOption(option);
	}
	this.getOptionById = function(ctrlId) {
		var length = this.options.length;
		for (var i = 0; i < length; i++) {
			if (this.options[i].id == ctrlId) {
				return this.options[i];
			}
		}
	}
	this.oneAddChange = function(option) {
		var _this = this;
		if (option.index < this.options.length - 1) {
			var nextOption = this.options[option.index + 1];
			$('#' + option.id).unbind("change");
			$('#' + option.id).change(function() {
				var parentV = $('#' + option.id).val();
				if (parentV == option.nullVal) {
					_this.setDescendantDisabled(option);
				} else {
					_this.iniChildSelect(nextOption.id, parentV);
				}
			});
		}
	}
	this.iniChildSelect = function(childCtrlId, parentV) {
		var option = this.getOptionById(childCtrlId);
		this.clearOptions(option);
		var _this = this;
		var random = Math.random();
		$.ajax({
			type : "post",
			url : option.url,
			data : option.queryDataF(parentV),
			dataType : "json",
			async : true,
			success : function(data) {
				if (random == option.random) {
					var list = eval(data);
					_this.clearOptions(option);
					for (var j = 0; j < list.length; j++) {
						$(option.itemF(list[j])).appendTo("#" + option.id);
					}
					_this.setDefOneVal(option);
				}
			}
		});
		option.random = random;
	}
	this.setDescendantDisabled = function(option) {// 设置子孙控件为不可用状态
		for (var i = option.index + 1; i < this.options.length; i++) {
			$('#' + this.options[i].id).attr("disabled", true);
			$('#' + this.options[i].id).val(this.options[i].nullVal);
		}
	}
	this.setDefOneVal = function(option) {// 设置某一个控件的值，会设置后代的控件为不可用
		var id = '#' + option.id;
		$(id).attr("disabled", option.disabled);
		if (option.needSetDef) {
			option.needSetDef = false;
			$(id).val(option.defVal);
			$(id).trigger("change");
			return;
		}
		this.setDescendantDisabled(option);
	}
	this.iniOptions(options);
	this.ini();
}
