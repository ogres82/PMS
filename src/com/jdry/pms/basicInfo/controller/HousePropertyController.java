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
import com.jdry.pms.chargeManager.pojo.*;
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

    @Resource
    private ChargeTypeRoomRelaService serviceRela;

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
        String communityId = arg0.getParameter("communityId") == null ? "" : arg0.getParameter("communityId");
        String storiedBuildId = arg0.getParameter("storiedBuildId") == null ? "" : arg0.getParameter("storiedBuildId");
        String roomState = arg0.getParameter("roomState") == null ? "" : arg0.getParameter("roomState");
        String roomType = arg0.getParameter("roomType") == null ? "" : arg0.getParameter("roomType");
        Integer lzRoomOwnerId = arg0.getParameter("lzRoomOwnerId") == null ? 0 : Integer.parseInt(arg0.getParameter("lzRoomOwnerId"));
        String newOwnerId = arg0.getParameter("newOwnerId");
        String lzRoomId = arg0.getParameter("lzRoomId");
        String phone = arg0.getParameter("phone");
        String decorateEndDate = null == arg0.getParameter("decorateEndDate") ? "" : arg0.getParameter("decorateEndDate");
        String decorateInstructions = null == arg0.getParameter("decorateInstructions") ? "" : arg0.getParameter("decorateInstructions");
        String communityNameSearch = arg0.getParameter("communityNameSearch") == null ? "" : arg0.getParameter("communityNameSearch");
        String storiedBuildNameSearch = arg0.getParameter("storiedBuildNameSearch") == null ? "" : arg0.getParameter("storiedBuildNameSearch");
        String roomTypeSearch = arg0.getParameter("roomTypeSearch") == null ? "" : arg0.getParameter("roomTypeSearch");
        String roomStateSearch = arg0.getParameter("roomStateSearch") == null ? "" : arg0.getParameter("roomStateSearch");
        String reciveRoomDate = null == arg0.getParameter("reciveRoomDate") ? "" : arg0.getParameter("reciveRoomDate");
        String makeRoomDate = arg0.getParameter("makeRoomDate") == null ? "" : arg0.getParameter("makeRoomDate");
        String chargeDate = null == arg0.getParameter("chargeDate") ? "" : arg0.getParameter("chargeDate");
        String retainerInfo = null == arg0.getParameter("retainerInfo") ? "" : arg0.getParameter("retainerInfo");
        String roomNo = null == arg0.getParameter("roomNo") ? "" : arg0.getParameter("roomNo");
        String unitId = arg0.getParameter("unitId") == null ? "" : arg0.getParameter("unitId");
        String decorateStartDate = null == arg0.getParameter("decorateStartDate") ? "" : arg0.getParameter("decorateStartDate");
        String decoratePlanDate = null == arg0.getParameter("decoratePlanDate") ? "" : arg0.getParameter("decoratePlanDate");
        String decorateInfo = null == arg0.getParameter("decorateInfo") ? "" : arg0.getParameter("decorateInfo");
        PrintWriter out = arg1.getWriter();
        String jsonString = "";
        String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
        String operId = CommUser.getUserName();
        String chargeTypeRoomRelaInfo = arg0.getParameter("chargeTypeRoomRelaInfo") == null ? "" : arg0.getParameter("chargeTypeRoomRelaInfo");
        String ownerId = arg0.getParameter("ownerId") == null ? "" : arg0.getParameter("ownerId");
        String buildId = null == arg0.getParameter("buildId") ? "" : arg0.getParameter("buildId").toString();
        int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
        int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
        search = CharacterUtil.getUTF_8String(search);
        String keyword = arg0.getParameter("keyword") == null ? "" : arg0.getParameter("keyword");
        keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
        Map<String, Object> parameter = new HashMap<String, Object>(40);
        try {
            DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
            if ("list".equalsIgnoreCase(method)) {
                if (currentPage != 0) {
                    currentPage = currentPage / showCount;
                }
                if ("undefined".equals(search)) {
                    search = "";
                }

                currentPage += 1;

                parameter.put("search", search);
                parameter.put("communityNameSearch", communityNameSearch);
                parameter.put("storiedBuildNameSearch", storiedBuildNameSearch);
                parameter.put("roomTypeSearch", roomTypeSearch);
                parameter.put("roomStateSearch", roomStateSearch);
                Page<VHouseProperty> page = new Page<VHouseProperty>(showCount, currentPage);
                service.queryHousePropertyByParam(page, parameter, null);
                List<VHouseProperty> result = (List<VHouseProperty>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount();
                jsonString = "{\"total\":" + count + ",\"rows\":" + b + "}";
            }
            if ("giveHouseList".equalsIgnoreCase(method)) {
                if (currentPage != 0) {
                    currentPage = currentPage / showCount;
                }
                currentPage += 1;
                parameter.put("search", search);
                parameter.put("order", "makeRoomDate");
                Page<VHouseOwner> page = new Page<VHouseOwner>(showCount, currentPage);
                service.queryHouseOwnerByParam(page, parameter, "1");
                List<VHouseOwner> result = (List<VHouseOwner>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount();
                jsonString = "{\"total\":" + count + ",\"rows\":" + b + "}";
            }

            if ("reciveHouseList".equalsIgnoreCase(method)) {
                parameter.put("search", search);
                parameter.put("order", "receiveRoomDate");
                Page<VHouseOwner> page = new Page<VHouseOwner>(showCount, currentPage);
                service.queryHouseOwnerByParam(page, parameter, "2");
                List<VHouseOwner> result = (List<VHouseOwner>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount();
                jsonString = "{\"total\":" + count + ",\"rows\":" + b + "}";
            }
            // 装修房间列表
            if ("decorateHouseList".equalsIgnoreCase(method)) {
                if (currentPage != 0) {
                    currentPage = currentPage / showCount;
                }
                currentPage += 1;
                parameter.put("search", search);
                parameter.put("decorateStartDate", "notNull");
                parameter.put("order", "decorateStartDate");
                Page<VHouseOwner> page = new Page<VHouseOwner>(showCount, currentPage);
                service.queryHouseOwnerByParam(page, parameter, "3");
                List<VHouseOwner> result = (List<VHouseOwner>) page.getEntities();
                String b = JSON.toJSONString(result);
                long count = page.getEntityCount();
                jsonString = "{\"total\":" + count + ",\"rows\":" + b + "}";
            }
            // 收费项关联列表
            if ("chargeTypeList".equalsIgnoreCase(method)) {
                List<VRoomChargeTypeRela> rc = service.getVRoomChargeTypeRelaById(roomId);
                jsonString = JSON.toJSONString(rc);
            }
            // 收费项关联列表
            if ("chargeInfoList".equalsIgnoreCase(method)) {
                List<ChargeInfoEntity> rc = service.getChargeInfoEntityById(roomId);
                jsonString = JSON.toJSONString(rc);
            }
            if ("editHouseProperty".equalsIgnoreCase(method) || "viewHouseProperty".equals(method)) {
                VHouseProperty house = service.getVHousePropertyById(roomId);
                jsonString = JSON.toJSONString(house);
            }
            if ("editReceiveHouse".equalsIgnoreCase(method) || "viewReceiveHouse".equals(method)) {
                List<VRoomChargeTypeRela> rc = service.getVRoomChargeTypeRelaById(roomId);
                jsonString = JSON.toJSONString(rc);
            }
            if ("save".equals(method)) {
                HouseProperty houseProperty = JSON.parseObject(housePropertyInfo, HouseProperty.class);
                if ("".equalsIgnoreCase(houseProperty.getRoomId())) {
                    service.addHouseProperty(houseProperty);
                    lzmhService.addRoom(houseProperty);
                } else {
                    service.updateHouseProperty(houseProperty);
                    lzmhService.modRoom(houseProperty);
                }
                jsonString = JSON.toJSONString("succese");
            }
            // 校验房号是否可用
            if ("checkRoomNo".equalsIgnoreCase(method)) {
                if (service.checkRoomNo(unitId, roomNo, roomId)) {
                    jsonString = JSON.toJSONString("succese");
                } else {
                    jsonString = JSON.toJSONString("failed");
                }
                return;
            }
            if ("deleteHouseProperty".equalsIgnoreCase(method)) {
                String ids[] = roomId.split(",");
                for (int i = 0, len = ids.length; i <= len - 1; i++) {
                    HouseProperty hp = service.getHousePropertyById(ids[i]);
                    if (hp != null) {
                        hp.setIsDel("1");
                        hp.setUpdateTime(new Date());
                        service.updateHouseProperty(hp);
                        out.print(JSON.toJSONString("succese"));
                        out.flush();
                    }
                }
                jsonString = JSON.toJSONString("删除成功！");
            }
            if ("initDropAll".equalsIgnoreCase(method)) {
                List<DirDirectoryDetail> positions = propertyManagerService.getDirectoryLikeCode("room_");
                jsonString = JSON.toJSONString(positions);
            }

            if ("initAllAreaDrop".equalsIgnoreCase(method)) {
                Map<String, Object> param = new HashMap<String, Object>();
                List<AllArea> allAreas = (List<AllArea>) allAreaService.queryAllAreaByParam(param);
                jsonString = JSON.toJSONString(allAreas);
            }

            if ("initAreaPropertyDrop".equalsIgnoreCase(method)) {
                parameter.put("buildId", buildId);
                List<AreaProperty> areaProperty = (List<AreaProperty>) areaPropertyService.queryAreaPropertyByParam(parameter);
                jsonString = JSON.toJSONString(areaProperty);
            }

            if ("initBuildingPropertyDrop".equalsIgnoreCase(method)) {
                parameter.put("communityId", communityId);
                List<BuildingProperty> buildingProperty = (List<BuildingProperty>) buildingPropertyService.queryBuildingPropertyByParam(parameter);
                jsonString = JSON.toJSONString(buildingProperty);
            }
            if ("houseInfo".equalsIgnoreCase(method)) {
                parameter.put("keyword", keyword);
                // 取房间状态为未售的
                parameter.put("roomState", "0");
                List<VHouseOwner> buildingProperty = service.queryVHouseOwnerByParam(parameter);
                jsonString = JSON.toJSONString(buildingProperty);
            }

            if ("receiveHouseInfo".equalsIgnoreCase(method)) {
                parameter.put("keyword", keyword);
                // 取房间状态已交房的
                parameter.put("roomState", "1");
                List<VHouseOwner> buildingProperty = service.queryVHouseOwnerByParam(parameter);
                jsonString = JSON.toJSONString(buildingProperty);
            }

            if ("decorateHouseInfo".equalsIgnoreCase(method)) {
                parameter.put("keyword", keyword);
                // 取房间状态已收房的
                parameter.put("roomState", "2");
                parameter.put("decorateStartDate", "notNull");
                List<VHouseOwner> buildingProperty = service.queryVHouseOwnerByParam(parameter);
                jsonString = JSON.toJSONString(buildingProperty);
            }
            //地产交房
            if ("giveHouse".equalsIgnoreCase(method)) {
                parameter.put("roomId", roomId);
                parameter.put("ownerId", ownerId);
                parameter.put("makeRoomDate", makeRoomDate);
                String result = service.giveHouse(parameter);
                // 同步到lzmh
                if ("success".equals(result)) {
                    HouseOwner ho = service.getHouseOwnerById(roomId, ownerId);
                    lzmhService.addOwnerInfo(ho);
                }
                jsonString = JSON.toJSONString(result);
            }
            // 解除交房关系
            if ("deleteGiveHouse".equalsIgnoreCase(method)) {
                jsonString = service.deleteGiveHouse(roomId);
            }

            if ("initRetainerProject".equalsIgnoreCase(method)) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("charge_way", "01");
                List<ChargeTypeSettingViewEntity> chargeType = service.queryChargeTypeByParam(param);
                jsonString = JSON.toJSONString(chargeType);
            }
            // 装修收费项
            if ("initDecorateProject".equalsIgnoreCase(method)) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("charge_way", "02");
                param.put("charge_type_name", "装修");
                List<ChargeTypeSettingViewEntity> chargeType = service.queryChargeTypeByParam(param);
                jsonString = JSON.toJSONString(chargeType);
            }

            // 收房业务
            if ("receiveHouse".equalsIgnoreCase(method)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                jsonString = JSON.toJSONString("success");
                HouseProperty house = service.getHousePropertyById(roomId);
                if (house != null) {
                    house.setRoomState("2");
                    if (reciveRoomDate != null && !reciveRoomDate.equals("")) {
                        house.setReceiveRoomDate(sdf.parse(reciveRoomDate));
                    }
                    service.updateHouseProperty(house);
                    List<ChargeTypeRoomRelaEntity> chargeInfos = JSON.parseArray(retainerInfo, ChargeTypeRoomRelaEntity.class);
                    for (ChargeTypeRoomRelaEntity charge : chargeInfos) {
                        charge.setCharge_date(sdf.parse(chargeDate));
                        serviceRela.saveAll(charge);
                    }
                }
            }

            // 新增装修申请
            if ("addDecorateApply".equalsIgnoreCase(method)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                }
                jsonString = JSON.toJSONString(result);
            }

            // 装修完成
            if ("decorateFinish".equalsIgnoreCase(method)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            }

            // 装修账单账单列表
            if ("decorateChargeInfo".equalsIgnoreCase(method)) {
//				Map<String, Object> param = new HashMap<String, Object>();
//				param.put("roomId", roomId);
//				param.put("ownerId", ownerId);
//				if (!roomId.isEmpty() && !ownerId.isEmpty()) {
//					List<VRoomCharge> rc = service.getVRoomChargeById(param);
//					List<ChargeSerialViewEntity> rc1 = service.getChargeSerial(param);
//					VRoomCharge v = new VRoomCharge();
//					for (int i = 0; i < rc1.size(); i++) {
//						v.setCharge_type_name(rc1.get(i).getCharge_type_name());
//						v.setReceive_amount(rc1.get(i).getPaid_amount().floatValue());
//						v.setPaid_date(rc1.get(i).getPaid_date());
//						v.setBegin_time(rc1.get(i).getBegin_date());
//						v.setEnd_time(rc1.get(i).getEnd_date());
//						v.setCharge_type("押金");
//						v.setCharge_way("临时性");
//						v.setCharge_mode("定额");
//						rc.add(v);
//					}
//					jsonString = JSON.toJSONString(rc);
//				}
                return;
            }

            // 装修完成账单列表
            if ("decorateFinishInfo".equalsIgnoreCase(method)) {
                parameter.put("roomId", roomId);
                parameter.put("ownerId", ownerId);
                parameter.put("end_time", "is not null");
                if (!roomId.isEmpty() && !ownerId.isEmpty()) {
                    List<VRoomCharge> rc = service.getVRoomChargeById(parameter);
                    List<ChargeSerialViewEntity> rc1 = service.getChargeSerial(parameter);
                    for (int i = 0; i < rc1.size(); i++) {
                        VRoomCharge v = new VRoomCharge();
                        v.setCharge_type_name(rc1.get(i).getCharge_type_name());
                        v.setReceive_amount(rc1.get(i).getPaid_amount().floatValue());
                        v.setBegin_time(rc1.get(i).getBegin_date());
                        v.setEnd_time(rc1.get(i).getEnd_date());
                        rc.add(v);
                    }
                    jsonString = JSON.toJSONString(rc);
                }
            }

            if ("inputFile".equalsIgnoreCase(method)) {
                String result = "{'status':'0','message':'上传失败'}";
                try {
                    String flag = inputFile(arg0, arg1);
                    if (flag != null) {
                        result = "{'status':'1','message':'" + flag + "'}";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jsonString = JSON.toJSONString(result);
            }

            // 删除装修记录
            if ("delDecorate".equalsIgnoreCase(method)) {
                service.delDecorateInfo(roomId);
                jsonString = JSON.toJSONString("success");
            }
            if ("roomOfownerInfo".equalsIgnoreCase(method)) {
                parameter.put("roomId", roomId);
                parameter.put("ownerId", ownerId);
                parameter.put("communityId", communityId);
                parameter.put("storiedBuildId", storiedBuildId);
                parameter.put("roomState", roomState);
                parameter.put("roomType", roomType);
                parameter.put("unitId", unitId);
                parameter.put("search", search);

                Page<RoomOfOwnerInfo> page = new Page<RoomOfOwnerInfo>(showCount, currentPage);
                service.queryRoomOfOwnerInfo(page, parameter);
                List<RoomOfOwnerInfo> lists = (List<RoomOfOwnerInfo>) page.getEntities();
                jsonString = "{\"total\":" + page.getEntityCount() + ",\"rows\":" + JSON.toJSONString(lists) + "}";

            }
            if ("unOwner".equalsIgnoreCase(method)) {
                service.unOwner(roomId, lzRoomOwnerId, CommUser.getUserName());
                jsonString = JSON.toJSONString("success");
            }
            if ("repOwner".equalsIgnoreCase(method)) {

                service.repOwner(roomId, lzRoomOwnerId, CommUser.getUserName(), newOwnerId, phone, lzRoomId);
                jsonString = JSON.toJSONString("success");
                // 传输JSON

            }
            if ("repCharge".equalsIgnoreCase(method)) {
                if ("".equals(chargeTypeRoomRelaInfo)) {
                    return;
                }
                ChargeTypeRoomRelaEntity chargeTypeRoomRelaEntity = JSON.parseObject(chargeTypeRoomRelaInfo, ChargeTypeRoomRelaEntity.class);
                chargeTypeRoomRelaEntity.setUpdate_emp_id(operId);
                serviceRela.saveAll(chargeTypeRoomRelaEntity);
                jsonString = JSON.toJSONString("success");
            }
            arg0.setAttribute("loginUser", user);
        } catch (Exception e) {
            e.printStackTrace();
            jsonString = JSON.toJSONString(e.getMessage());
            out.println(jsonString);
            out.flush();
        } finally {
            out.println(jsonString);
            out.flush();
            out.close();
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
            Row row = rows.next();
            System.out.println("Row #" + row.getRowNum());
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

            Iterator<Cell> cells = row.cellIterator();
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
