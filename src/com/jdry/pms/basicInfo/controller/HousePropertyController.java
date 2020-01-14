package com.jdry.pms.basicInfo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdry.pms.basicInfo.pojo.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRoomRelaEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.chargeManager.service.ChargeSerialService;
import com.jdry.pms.chargeManager.service.ChargeTypeRoomRelaService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.lzmh.service.LzmhService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class HousePropertyController implements IController {

	@Resource
	private HousePropertyService service;

	@Resource
	private ChargeInfoService chargeService;

	@Resource
	private AllAreaService allAreaService;

	@Resource
	private PropertyManagerService propertyManagerService;

	@Resource
	private AreaPropertyService areaPropertyService;

	@Resource
	private BuildingPropertyService buildingPropertyService;

	@Resource
	private LzmhService lzmhService;

	// 收费项关联接口
	@Resource
	private ChargeTypeRoomRelaService serviceRela;
	// 账单接口
	@Resource
	ChargeInfoService chargeInfoService;

	@Resource
	ChargeSerialService serialService;

	@Override
	public boolean anonymousAccess() {

		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {

		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		String roomId = arg0.getParameter("roomId") == null ? "" : arg0.getParameter("roomId");
		String housePropertyInfo = arg0.getParameter("housePropertyInfo") == null ? "" : arg0.getParameter("housePropertyInfo");
		PrintWriter out = arg1.getWriter();
		String jsonString = "";
		String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
		int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数
		int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
		search = CharacterUtil.getUTF_8String(search);

		try {

			DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
			if (method.equalsIgnoreCase("list")) {
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				if (search.equals("undefined")) {
					search = "";
				}
				String communityNameSearch = arg0.getParameter("communityNameSearch") == null ? "" : arg0.getParameter("communityNameSearch");
				String storiedBuildNameSearch = arg0.getParameter("storiedBuildNameSearch") == null ? "" : arg0.getParameter("storiedBuildNameSearch");
				String roomTypeSearch = arg0.getParameter("roomTypeSearch") == null ? "" : arg0.getParameter("roomTypeSearch");
				String roomStateSearch = arg0.getParameter("roomStateSearch") == null ? "" : arg0.getParameter("roomStateSearch");

				currentPage += 1;
				Map<String, Object> parameter = new HashMap<String, Object>();

				parameter.put("search", search);
				parameter.put("communityNameSearch", communityNameSearch);
				parameter.put("storiedBuildNameSearch", storiedBuildNameSearch);
				parameter.put("roomTypeSearch", roomTypeSearch);
				parameter.put("roomStateSearch", roomStateSearch);

				Page<VHouseProperty> page = new Page<VHouseProperty>(showCount, currentPage);
				service.queryHousePropertyByParam(page, parameter, null);
				List<VHouseProperty> result = (List<VHouseProperty>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数

				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if (method.equalsIgnoreCase("giveHouseList")) {
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				currentPage += 1;
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("search", search);
				parameter.put("order", "makeRoomDate");
				Page<VHouseOwner> page = new Page<VHouseOwner>(showCount, currentPage);
				service.queryHouseOwnerByParam(page, parameter, "1");
				List<VHouseOwner> result = (List<VHouseOwner>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数

				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}

			if (method.equalsIgnoreCase("reciveHouseList")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("search", search);
				parameter.put("order", "receiveRoomDate");
				Page<VHouseOwner> page = new Page<VHouseOwner>(showCount, currentPage);
				service.queryHouseOwnerByParam(page, parameter, "2");
				List<VHouseOwner> result = (List<VHouseOwner>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数

				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			// 装修房间列表
			if (method.equalsIgnoreCase("decorateHouseList")) {
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				currentPage += 1;
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("search", search);
				parameter.put("decorateStartDate", "notNull");
				parameter.put("order", "decorateStartDate");
				Page<VHouseOwner> page = new Page<VHouseOwner>(showCount, currentPage);
				service.queryHouseOwnerByParam(page, parameter, "3");
				List<VHouseOwner> result = (List<VHouseOwner>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数

				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			// 收费项关联列表
			if (method.equalsIgnoreCase("chargeTypeList")) {

				List<VRoomChargeTypeRela> rc = service.getVRoomChargeTypeRelaById(roomId);
				String b = JSON.toJSONString(rc);
				out.print(b);
				out.flush();
			}
			// 收费项关联列表
			if (method.equalsIgnoreCase("chargeInfoList")) {

				List<ChargeInfoEntity> rc = service.getChargeInfoEntityById(roomId);
				String b = JSON.toJSONString(rc);
				out.print(b);
				out.flush();
			}
			if (method.equalsIgnoreCase("editHouseProperty") || method.equals("viewHouseProperty")) {

				arg1.setContentType("application/json;charset=utf-8");
				VHouseProperty house = new VHouseProperty();
				house = service.getVHousePropertyById(roomId);
				jsonString = JSON.toJSONString(house);

				// 传输JSON
				out.println(jsonString);
				out.flush();

			}
			if (method.equalsIgnoreCase("editReceiveHouse") || method.equals("viewReceiveHouse")) {

				arg1.setContentType("application/json;charset=utf-8");
				List<VRoomChargeTypeRela> rc = service.getVRoomChargeTypeRelaById(roomId);
				jsonString = JSON.toJSONString(rc);

				// 传输JSON
				out.println(jsonString);
				out.flush();

			}
			if (method.equals("save")) {

				HouseProperty houseProperty = JSON.parseObject(housePropertyInfo, HouseProperty.class);

				if ("".equalsIgnoreCase(houseProperty.getRoomId())) {
					service.addHouseProperty(houseProperty);
					lzmhService.addRoom(houseProperty);
				} else {
					service.updateHouseProperty(houseProperty);
					lzmhService.modRoom(houseProperty);
				}
				// 传输JSON
				out.print(JSON.toJSONString("succese"));
				out.flush();

			}
			// 校验房号是否可用
			if (method.equalsIgnoreCase("checkRoomNo")) {
				String roomNo = null == arg0.getParameter("roomNo") ? "" : arg0.getParameter("roomNo").toString().trim();
				String unitId = null == arg0.getParameter("unitId") ? "" : arg0.getParameter("unitId").toString().trim();
				
				if (service.checkRoomNo(unitId, roomNo,roomId)) {
					// 传输JSON
					out.print(JSON.toJSONString("succese"));
					out.flush();
				} else {
					// 传输JSON
					out.print(JSON.toJSONString("failed"));
					out.flush();
				}
				return;
			}
			if (method.equalsIgnoreCase("deleteHouseProperty")) {
				String ids[] = roomId.split(",");
				for (int i = 0, len = ids.length; i <=len - 1; i++) {				
					HouseProperty hp =  service.getHousePropertyById(ids[i]);
					if (hp != null) {
						hp.setIsDel("1");
						hp.setUpdateTime(new Date());
						service.updateHouseProperty(hp);
						out.print(JSON.toJSONString("succese"));
						out.flush();
					}
				}

				jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			}
			if (method.equalsIgnoreCase("initDropAll")) {

				List<DirDirectoryDetail> positions = propertyManagerService.getDirectoryLikeCode("room_");
				jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			if (method.equalsIgnoreCase("initAllAreaDrop")) {

				Map<String, Object> param = new HashMap<String, Object>();
				List<AllArea> allAreas = (List<AllArea>) allAreaService.queryAllAreaByParam(param);
				jsonString = JSON.toJSONString(allAreas);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			if (method.equalsIgnoreCase("initAreaPropertyDrop")) {
				String buildId = null == arg0.getParameter("buildId") ? "" : arg0.getParameter("buildId").toString();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("buildId", buildId);
				List<AreaProperty> areaProperty = (List<AreaProperty>) areaPropertyService.queryAreaPropertyByParam(param);
				jsonString = JSON.toJSONString(areaProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			if (method.equalsIgnoreCase("initBuildingPropertyDrop")) {

				String communityId = null == arg0.getParameter("communityId") ? "" : arg0.getParameter("communityId").toString();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("communityId", communityId);
				List<BuildingProperty> buildingProperty = (List<BuildingProperty>) buildingPropertyService.queryBuildingPropertyByParam(param);
				jsonString = JSON.toJSONString(buildingProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}
			if (method.equalsIgnoreCase("houseInfo")) {
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"), "UTF-8");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("keyword", keyword);
				param.put("roomState", "0");// 取房间状态为未售的
				List<VHouseOwner> buildingProperty = service.queryVHouseOwnerByParam(param);
				jsonString = JSON.toJSONString(buildingProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			if (method.equalsIgnoreCase("receiveHouseInfo")) {
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"), "UTF-8");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("keyword", keyword);
				param.put("roomState", "1");// 取房间状态已交房的
				List<VHouseOwner> buildingProperty = service.queryVHouseOwnerByParam(param);
				jsonString = JSON.toJSONString(buildingProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			if (method.equalsIgnoreCase("decorateHouseInfo")) {
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"), "UTF-8");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("keyword", keyword);
				param.put("roomState", "2");// 取房间状态已收房的
				param.put("decorateStartDate", "notNull");
				List<VHouseOwner> buildingProperty = service.queryVHouseOwnerByParam(param);
				jsonString = JSON.toJSONString(buildingProperty);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}
			//地产交房
			if (method.equalsIgnoreCase("giveHouse")) {

				String ownerId = null == arg0.getParameter("ownerId") ? "" : arg0.getParameter("ownerId").toString();
				String makeRoomDate = null == arg0.getParameter("makeRoomDate") ? "" : arg0.getParameter("makeRoomDate").toString();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roomId", roomId);
				param.put("ownerId", ownerId);
				param.put("makeRoomDate", makeRoomDate);

				String result = service.giveHouse(param);
				// 同步到lzmh
				if ("success".equals(result)) {
					HouseOwner ho = service.getHouseOwnerById(roomId, ownerId);
					lzmhService.addOwnerInfo(ho);
				}
				// 传输JSON
				out.print(JSON.toJSONString(result));
				out.flush();
			}

			if (method.equalsIgnoreCase("deleteGiveHouse")) { // 解除交房关系
				String result = service.deleteGiveHouse(roomId);

				// 传输JSON
				out.print(result);
				out.flush();
			}

			if (method.equalsIgnoreCase("initRetainerProject")) {

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("charge_way", "01");
				List<ChargeTypeSettingViewEntity> chargeType = service.queryChargeTypeByParam(param);
				jsonString = JSON.toJSONString(chargeType);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}
			// 装修收费项
			if (method.equalsIgnoreCase("initDecorateProject")) {

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("charge_way", "02");
				param.put("charge_type_name", "装修");

				List<ChargeTypeSettingViewEntity> chargeType = service.queryChargeTypeByParam(param);
				jsonString = JSON.toJSONString(chargeType);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			// 收房业务
			if (method.equalsIgnoreCase("receiveHouse")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String reciveRoomDate = null == arg0.getParameter("reciveRoomDate") ? "" : arg0.getParameter("reciveRoomDate").toString();
				String chargeDate = null == arg0.getParameter("chargeDate") ? "" : arg0.getParameter("chargeDate").toString();
				String retainerInfo = null == arg0.getParameter("retainerInfo") ? "" : arg0.getParameter("retainerInfo").toString();
				String operId = null == arg0.getParameter("operId") ? "" : arg0.getParameter("operId").toString();

				jsonString = JSON.toJSONString("success");

				int intError = 0;
				HouseProperty house = service.getHousePropertyById(roomId);
				if (house != null) {
					house.setRoomState("2");// 已收房
					if (reciveRoomDate != null && !reciveRoomDate.equals("")) {
						house.setReceiveRoomDate(sdf.parse(reciveRoomDate));
					}
					service.updateHouseProperty(house);

					List<ChargeTypeRoomRelaEntity> chargeInfos = JSON.parseArray(retainerInfo, ChargeTypeRoomRelaEntity.class);
					for (ChargeTypeRoomRelaEntity charge : chargeInfos) {
						charge.setCharge_date(sdf.parse(chargeDate));
						serviceRela.saveAll(charge);
					}
					// 生成历史账单
					intError = chargeService.manOwnerOfCharge(operId, roomId, chargeDate, null);
					if (intError == 1) {
						jsonString = JSON.toJSONString("failure");
					}
				}

				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			// 新增装修申请
			if (method.equalsIgnoreCase("addDecorateApply")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String decorateStartDate = null == arg0.getParameter("decorateStartDate") ? "" : arg0.getParameter("decorateStartDate").toString();
				String decoratePlanDate = null == arg0.getParameter("decoratePlanDate") ? "" : arg0.getParameter("decoratePlanDate").toString();
				String chargeStandardInfo = null == arg0.getParameter("chargeStandardInfo") ? "" : arg0.getParameter("chargeStandardInfo").toString();
				String decorateInfo = null == arg0.getParameter("decorateInfo") ? "" : arg0.getParameter("decorateInfo").toString();
				String ownerName = null == arg0.getParameter("ownerName") ? "" : arg0.getParameter("ownerName").toString();
				String result = "";
				HouseProperty house = service.getHousePropertyById(roomId);
				if (house != null) {
					if (decorateStartDate != null && !decorateStartDate.equals("")) {
						house.setDecorateStartDate(sdf.parse(decorateStartDate));
					}
					if (decoratePlanDate != null && !decoratePlanDate.equals("")) {
						house.setDecoratePlanDate(sdf.parse(decoratePlanDate));
					}
					// 新增装修状态
					house.setRoomState("3");
					service.updateHouseProperty(house);

					// 添加装修明细
					DecorateInfo di = JSON.parseObject(decorateInfo, DecorateInfo.class);
					service.updateOrSaveDecorateDetail(di);

					List<VRoomCharge> chargeInfos = JSON.parseArray(chargeStandardInfo, VRoomCharge.class);
					for (VRoomCharge charge : chargeInfos) {
						String charge_type_name = charge.getCharge_type_name();
						if (charge_type_name.contains("押金")) {

							String count = charge.getReceive_amount() + "";

							ChargeSerialEntity ser = new ChargeSerialEntity();
							ser.setRoom_id(charge.getRoom_id());
							ser.setRoom_no(charge.getRoom_no());
							ser.setOwner_id(charge.getOwner_id());
							ser.setOwner_name(ownerName);
							if (count != null && !count.isEmpty()) {
								ser.setPaid_amount(new BigDecimal(charge.getReceive_amount()));
								ser.setReceive_amount(new BigDecimal(charge.getReceive_amount()));
							}

							Date now = new Date();
							ser.setPaid_date(now);
							ser.setBegin_date(now);
							ser.setEnd_date(now);
							ser.setPaid_mode("02"); // 现金
							ser.setPaid_date(new Date());
							ser.setUpdate_date(new Date());
							ser.setOper_emp_id(CommUser.getUserName());
							ser.setCharge_type_name(charge_type_name);

							ser.setCharge_type("02");
							ser.setState("01");

							serialService.save(ser);
						} else {

							Map<String, Object> param = new HashMap<String, Object>();
							param.put("roomId", charge.getRoom_id());
							param.put("charge_type_no", charge.getCharge_type_no());
							param.put("begin_time", decorateStartDate);
							if (service.isExistCharge(param)) {
								result = "账单重复";
							} else {
								ChargeInfoEntity chargeEntity = new ChargeInfoEntity();
								if (chargeEntity != null) {

									Map<String, Object> parameter = new HashMap<String, Object>();

									parameter.put("charge_type_no", charge.getCharge_type_no());
									parameter.put("charge_type_name", charge.getCharge_type_name());
									parameter.put("room_id", charge.getRoom_id());
									parameter.put("room_no", charge.getRoom_no());
									parameter.put("owner_id", charge.getOwner_id());
									parameter.put("owner_name", ownerName);
									parameter.put("begin_time", decorateStartDate);
									parameter.put("end_time", decoratePlanDate);
									parameter.put("count", "1");
									parameter.put("receive_amount", charge.getReceive_amount());
									parameter.put("price", charge.getPrice());
									parameter.put("oper_emp_id", CommUser.getUserName());

									chargeInfoService.addCharge(parameter);
									result = "保存成功";
								}
							}
						}
					}
				}

				jsonString = JSON.toJSONString(result);
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			// 装修完成
			if (method.equalsIgnoreCase("decorateFinish")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String decorateEndDate = null == arg0.getParameter("decorateEndDate") ? "" : arg0.getParameter("decorateEndDate").toString();
				String decorateInstructions = null == arg0.getParameter("decorateInstructions") ? "" : arg0.getParameter("decorateInstructions").toString();
				String result = "操作失败";
				HouseProperty house = service.getHousePropertyById(roomId);
				if (house != null) {
					if (decorateEndDate != null && !decorateEndDate.equals("")) {
						house.setDecorateEndDate(sdf.parse(decorateEndDate));
					}
					house.setDecorateInstructions(decorateInstructions);
					house.setRoomDecorateStatus("2");
					// 修改原来的入住3状态为4
					house.setRoomState("4");
					service.updateHouseProperty(house);
					result = "操作成功";
				}

				jsonString = JSON.toJSONString(result);
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}

			// 装修账单账单列表
			if (method.equalsIgnoreCase("decorateChargeInfo")) {
				String ownerId = null == arg0.getParameter("ownerId") ? "" : arg0.getParameter("ownerId").toString();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roomId", roomId);
				param.put("ownerId", ownerId);
				if (!roomId.isEmpty() && !ownerId.isEmpty()) {
					List<VRoomCharge> rc = service.getVRoomChargeById(param);
					List<ChargeSerialViewEntity> rc1 = service.getChargeSerial(param);
					for (int i = 0; i < rc1.size(); i++) {
						VRoomCharge v = new VRoomCharge();
						v.setCharge_type_name(rc1.get(i).getCharge_type_name());
						v.setReceive_amount(rc1.get(i).getPaid_amount().floatValue());
						v.setPaid_date(rc1.get(i).getPaid_date());
						v.setBegin_time(rc1.get(i).getBegin_date());
						v.setEnd_time(rc1.get(i).getEnd_date());
						v.setCharge_type("押金");
						v.setCharge_way("临时性");
						v.setCharge_mode("定额");
						rc.add(v);
					}
					String b = JSON.toJSONString(rc);
					out.print(b);
					out.flush();
				}
			}

			// 装修完成账单列表
			if (method.equalsIgnoreCase("decorateFinishInfo")) {
				String ownerId = null == arg0.getParameter("ownerId") ? "" : arg0.getParameter("ownerId").toString();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roomId", roomId);
				param.put("ownerId", ownerId);
				param.put("end_time", "is not null");
				if (!roomId.isEmpty() && !ownerId.isEmpty()) {
					List<VRoomCharge> rc = service.getVRoomChargeById(param);
					List<ChargeSerialViewEntity> rc1 = service.getChargeSerial(param);
					for (int i = 0; i < rc1.size(); i++) {
						VRoomCharge v = new VRoomCharge();
						v.setCharge_type_name(rc1.get(i).getCharge_type_name());
						v.setReceive_amount(rc1.get(i).getPaid_amount().floatValue());
						v.setBegin_time(rc1.get(i).getBegin_date());
						v.setEnd_time(rc1.get(i).getEnd_date());
						rc.add(v);
					}
					String b = JSON.toJSONString(rc);
					out.print(b);
					out.flush();
				}
			}

			if (method.equalsIgnoreCase("inputFile")) {
				String result = "{'status':'0','message':'上传失败'}";
				try {
					String flag = inputFile(arg0, arg1);
					if (flag != null) {
						result = "{'status':'1','message':'" + flag + "'}";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String b = JSON.toJSONString(result);
				out.print(b);
				out.flush();
			}

			// 删除装修记录
			if (method.equalsIgnoreCase("delDecorate")) {
				service.delDecorateInfo(roomId);
				String result = "success";
				String b = JSON.toJSONString(result);
				out.print(b);
				out.flush();
			}
			if (method.equalsIgnoreCase("roomOfownerInfo")) {

				String ownerId= arg0.getParameter("ownerId") == null ? "" : arg0.getParameter("ownerId").toString();
				String communityId= arg0.getParameter("communityId") == null ? "" : arg0.getParameter("communityId").toString();
				String storiedBuildId= arg0.getParameter("storiedBuildId") == null ? "": arg0.getParameter("storiedBuildId").toString();
				String roomState= arg0.getParameter("roomState") == null ? "": arg0.getParameter("roomState").toString();
				String roomType= arg0.getParameter("roomType") == null ? "": arg0.getParameter("roomType").toString();
				String unitId= arg0.getParameter("unitId") == null ? "": arg0.getParameter("unitId").toString();

				Map<String, Object> param = new HashMap<String, Object>();
				if(!"".equals(roomId)){
					param.put("roomId",roomId);
				}
				if(!"".equals(ownerId)){
					param.put("ownerId",ownerId);
				}
				if(!"".equals(communityId)){
					param.put("communityId",communityId);
				}
				if(!"".equals(storiedBuildId)){
					param.put("storiedBuildId",storiedBuildId);
				}
				if(!"".equals(roomState)){
					param.put("roomState",roomState);
				}
				if(!"".equals(roomType)){
					param.put("roomType",roomType);
				}
				if(!"".equals(unitId)){
					param.put("unitId",unitId);
				}
				List<RoomOfOwnerInfo> lists  = service.queryRoomOfOwnerInfo(param);
				jsonString = JSON.toJSONString(lists);
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}
			if (method.equalsIgnoreCase("unOwner")) {
				Integer lzRoomOwnerId =  arg0.getParameter("lzRoomOwnerId") == null ? 0 :  Integer.parseInt(arg0.getParameter("lzRoomOwnerId"));
				service.unOwner(roomId,lzRoomOwnerId,CommUser.getUserName());
				jsonString = JSON.toJSONString("success");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}
			if (method.equalsIgnoreCase("repOwner")) {
				Integer lzRoomOwnerId =  arg0.getParameter("lzRoomOwnerId") == null ? 0 :  Integer.parseInt(arg0.getParameter("lzRoomOwnerId"));
				String newOwnerId = arg0.getParameter("newOwnerId") ;
				String lzRoomId = arg0.getParameter("lzRoomId") ;
				String phone = arg0.getParameter("phone") ;
				service.repOwner(roomId,lzRoomOwnerId,CommUser.getUserName(),newOwnerId,phone,lzRoomId);
				jsonString = JSON.toJSONString("success");
				// 传输JSON
				out.println(jsonString);
				out.flush();
			}
			if (method.equalsIgnoreCase("repCharge")) {

			}
			if (method.equalsIgnoreCase("sycnLz")) {

			}
			arg0.setAttribute("loginUser", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/houseProperty/housePropertyList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	public String inputFile(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {

		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = "D:/upload/house";
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdir();
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
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					Iterator<Row> rows = ReadExcel.readXml(in, filename);
					String message = this.importData(rows);
					return message;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	public String importData(Iterator<Row> rows) throws ParseException {
		String result = "导入成功";

		while (rows.hasNext()) {
			Row row = rows.next(); // 获得行数据
			System.out.println("Row #" + row.getRowNum()); // 获得行号从0开始
			if (row.getRowNum() == 0) {
				continue;
			}
			String roomNo = row.getCell(3).toString();
			String communityName = row.getCell(1).toString();
			String StoriedBuildName = row.getCell(2).toString();

			HouseProperty house = service.getHousePropertyByNo(roomNo);

			// 房间号存在，但是小区名称和栋号不一致，证明不是重复数据
			if (house != null) {
				if (communityName.equals(house.getCommunityName()) && StoriedBuildName.equals(house.getStoriedBuildName())) {
					System.out.println("重复数据---------------");
					result = "第" + row.getRowNum() + "行数据已存在   ";
					break;
				}
			}
			house = new HouseProperty();

			Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
			while (cells.hasNext()) {
				Cell cell = cells.next();
				int cellIndex = cell.getColumnIndex();
				String buildId = "";
				if (cellIndex == 0) {
					AllArea allArea = allAreaService.getAllAreaByName(cell.toString());
					if (allArea == null) {
						allArea = new AllArea();
						allArea.setBuildName(cell.toString());
						allArea.setRemark("导入数据");
						buildId = allAreaService.addAllArea(allArea);
						house.setBuildId(buildId);
						house.setBuildName(cell.toString());
					} else {
						house.setBuildId(allArea.getBuildId());
						house.setBuildName(allArea.getBuildName());
						buildId = allArea.getBuildId();
					}
				}
				String communityId = "";
				if (cellIndex == 1) {

					AreaProperty areaProperty = areaPropertyService.getAreaPropertyByName(cell.toString());
					if (areaProperty == null) {
						areaProperty = new AreaProperty();
						areaProperty.setCommunityName(cell.toString());
						areaProperty.setBelongBuildId(buildId);
						areaProperty.setRemark("导入数据");
						communityId = areaPropertyService.addAreaProperty(areaProperty);
						house.setCommunityId(communityId);
						house.setCommunityName(cell.toString());
					} else {
						house.setCommunityId(areaProperty.getCommunityId());
						house.setCommunityName(areaProperty.getCommunityName());
						communityId = areaProperty.getCommunityId();
					}

				}

				if (cellIndex == 2) {
					BuildingProperty buildingProperty = buildingPropertyService.getBuildingPropertyByName(cell.toString());
					if (buildingProperty == null) {
						buildingProperty = new BuildingProperty();
						buildingProperty.setBelongCommId(communityId);
						buildingProperty.setStoriedBuildName(cell.toString());
						buildingProperty.setRemark("导入数据");
						String storiedBuildId = buildingPropertyService.addBuildingProperty(buildingProperty);
						house.setBelongSbId(storiedBuildId);
						house.setStoriedBuildName(cell.toString());
					} else {
						house.setBelongSbId(buildingProperty.getStoriedBuildId());
						house.setStoriedBuildName(buildingProperty.getStoriedBuildName());
					}

				}

				if (cellIndex == 3) {
					house.setRoomNo(roomNo);
				}
				if (cellIndex == 4) {
					house.setHouseType(cell.toString());
				}
				if (cellIndex == 5) {
					house.setBuildArea(new BigDecimal(cell.toString()));
				}
				if (cellIndex == 6) {
					house.setWithinArea(new BigDecimal(cell.toString()));
				}
				if (cellIndex == 7) {
					String roomType = cell.toString();
					if (!roomType.isEmpty()) {
						if (roomType.length() > 1) {
							house.setRoomType(roomType.substring(0, roomType.indexOf(".")));
						} else {
							house.setRoomType(roomType);
						}
					}
				}
				if (cellIndex == 8) {
					String roomState = cell.toString();
					if (!roomState.isEmpty()) {
						if (roomState.length() > 1) {
							house.setRoomState(roomState.substring(0, roomState.indexOf(".")));
						} else {
							house.setRoomState(roomState);
						}
					}
				}
				if (cellIndex == 9) {
					String chargeObject = cell.toString();
					if (!chargeObject.isEmpty()) {
						if (chargeObject.length() > 1) {
							house.setChargeObject(chargeObject.substring(0, chargeObject.indexOf(".")));
						} else {
							house.setChargeObject(chargeObject);
						}
					}
				}
				if (cellIndex == 10) {
					house.setRemark(cell.toString());
				}
				if (cellIndex == 11) {
					house.setAdvanceAmount(new BigDecimal(cell.toString()));
				}
				if (cellIndex == 12) {
					if (cell.toString().equals("")) {
						break;
					}
					house.setMakeRoomDate(cell.getDateCellValue());
					// house.setMakeRoomDate(sdf.parse(cell.toString()));
				}
				if (cellIndex == 13) {
					if (cell.toString().equals("")) {
						break;
					}
					house.setDecorateStartDate(cell.getDateCellValue());
				}
				if (cellIndex == 14) {
					if (cell.toString().equals("")) {
						break;
					}
					house.setDecorateEndDate(cell.getDateCellValue());
				}
				if (cellIndex == 15) {
					if (cell.toString().equals("")) {
						break;
					}
					house.setReceiveRoomDate(cell.getDateCellValue());
				}
				if (cellIndex == 16) {
					if (cell.toString().equals("")) {
						break;
					}
					house.setDecoratePlanDate(cell.getDateCellValue());
				}

			}
			service.addHouseProperty(house);

		}
		return result;
	}

}
