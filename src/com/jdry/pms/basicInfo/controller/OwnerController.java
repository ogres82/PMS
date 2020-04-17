package com.jdry.pms.basicInfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;
import com.jdry.pms.basicInfo.pojo.VOwnerRoomBasicInfo;
import com.jdry.pms.basicInfo.pojo.VPropertyOwner;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.OwnerInfoService;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.lzmh.service.LzmhService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class OwnerController implements IController {

	@Resource
	private OwnerInfoService service;

	@Resource
	private PropertyManagerService propertyManagerService;

	@Resource
	private AllAreaService allAreaService;

	@Resource
	private AreaPropertyService areaPropertyService;

	@Resource
	private BuildingPropertyService buildingPropertyService;

	@Resource
	private HousePropertyService housePropertyService;

	@Resource
	private LzmhService lzmhService;

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
		String ownerId = null == arg0.getParameter("ownerId") ? "" : arg0.getParameter("ownerId").toString();// 员工工号
		try {

			DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
			if (method.equalsIgnoreCase("list")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String ownerIdentity = arg0.getParameter("ownerIdentity") == null ? ""
						: arg0.getParameter("ownerIdentity");
				String regState = arg0.getParameter("regState") == null ? "" : arg0.getParameter("regState");

				parameter.put("ownerIdentity", ownerIdentity);
				parameter.put("regState", regState);

				List<VOwnerRoomBasicInfo> result = service.querOwnerRoomBasicInfo(parameter);

				String b = JSON.toJSONString(result);
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}

			if (method.equalsIgnoreCase("ownerVisitorlist")) {
				int currentPage = arg0.getParameter("offset") == null ? 1
						: Integer.parseInt(arg0.getParameter("offset"));// 每页行数
				int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
				search = CharacterUtil.getUTF_8String(search);
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				currentPage += 1;
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("search", search);
				Page<VPropertyOwner> page = new Page<VPropertyOwner>(showCount, currentPage);
				service.queryOwnerVisitorList(page, parameter);
				List<VPropertyOwner> result = (List<VPropertyOwner>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}

			if (method.equalsIgnoreCase("editPropertyOwner") || method.equals("viewPropertyOwner")) {

				arg1.setContentType("application/json;charset=utf-8");
				VPropertyOwner owner = new VPropertyOwner();
				owner = service.getVPropertyOwnerById(ownerId);
				String jsonString = JSON.toJSONString(owner);

				// 传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();

			}
			if (method.equals("save")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String isChange = arg0.getParameter("isChange").toString();

				String ownerName = null == arg0.getParameter("ownerName") ? ""
						: arg0.getParameter("ownerName").toString();
				String birthDate = null == arg0.getParameter("birthDate") ? ""
						: arg0.getParameter("birthDate").toString();
				String cardId = null == arg0.getParameter("cardId") ? "" : arg0.getParameter("cardId").toString();

				String ownerIdentity = null == arg0.getParameter("ownerIdentity") ? ""
						: arg0.getParameter("ownerIdentity").toString();
				String ownerType = null == arg0.getParameter("ownerType") ? ""
						: arg0.getParameter("ownerType").toString();
				String sex = null == arg0.getParameter("sex") ? "" : arg0.getParameter("sex").toString();
				String phone = null == arg0.getParameter("phone") ? "" : arg0.getParameter("phone").toString();
				String telPhone = null == arg0.getParameter("telPhone") ? "" : arg0.getParameter("telPhone").toString();

				String remark = null == arg0.getParameter("remark") ? "" : arg0.getParameter("remark").toString();
				String carId = null == arg0.getParameter("carId") ? "" : arg0.getParameter("carId").toString();
				String emergencyContact = null == arg0.getParameter("emergencyContact") ? ""
						: arg0.getParameter("emergencyContact").toString();
				String emergencyContactPhone = null == arg0.getParameter("emergencyContactPhone") ? ""
						: arg0.getParameter("emergencyContactPhone").toString();

				PropertyOwner owner = new PropertyOwner();
				if (ownerId != null && !ownerId.isEmpty()) {
					owner = service.getPropertyOwnerById(ownerId);
				}
				if (birthDate != null && !birthDate.isEmpty()) {
					owner.setBirthDate(sdf.parse(birthDate));
				}
				owner.setCardId(cardId);
				owner.setCarId(carId);
				owner.setOwnerIdentity(ownerIdentity);
				owner.setOwnerType(ownerType);
				owner.setSex(sex);
				owner.setPhone(phone);
				owner.setTelPhone(telPhone);
				owner.setOwnerName(ownerName);
				owner.setRemark(remark);
				owner.setEmergencyContact(emergencyContact);
				owner.setEmergencyContactPhone(emergencyContactPhone);
				service.addPropertyOwner(owner);
				// 判断业主手机号是否修改
				if ("1".equals(isChange)) {
					housePropertyService.changeOwnerInfo(owner.getOwnerId(), "admin", "1");
				}

				// 传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();

			}

			if (method.equalsIgnoreCase("deletePropertyOwner")) {

				PrintWriter out = arg1.getWriter();
				try {

					if (!StringUtil.isEmpty(ownerId)) {
						service.deletePropertyOwner(ownerId);
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				} catch (Exception e) {
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}

			} else if (method.equalsIgnoreCase("initDropAll")) {

				List<DirDirectoryDetail> positions = propertyManagerService.getDirectoryLikeCode("owner_");
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();

			}

			if (method.equalsIgnoreCase("ownerInfo")) {
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"), "UTF-8");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("keyword", keyword);
				List<PropertyOwner> house = (List<PropertyOwner>) service.queryOwnerInfoByParam(param);
				String jsonString = JSON.toJSONString(house, SerializerFeature.WriteMapNullValue);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma", "no-cache");
				arg1.setHeader("cache-control", "no-cache");
				// 传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();

			}

			if (method.equalsIgnoreCase("checkUniPhone")) {

				String fieldId = null == arg0.getParameter("fieldId") ? "" : arg0.getParameter("fieldId").toString();
				String fieldValue = null == arg0.getParameter("fieldValue") ? ""
						: arg0.getParameter("fieldValue").toString();
				String info = "[\"" + fieldId + "\",false]";
				try {
					boolean check = service.checkUniPhone(fieldValue);
					if (check) {
						info = "[\"" + fieldId + "\",true]";
					}
				} catch (Exception e) {
					e.printStackTrace();
					info = "[\"" + fieldId + "\",false]";
				}
				PrintWriter out = arg1.getWriter();
				out.print(info);
				out.flush();
			}

			if (method.equalsIgnoreCase("getFamily")) {

				List<VPropertyOwner> owner = service.getFamily(ownerId);
				if (owner != null) {

					String jsonString = JSON.toJSONString(owner);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma", "no-cache");
					arg1.setHeader("cache-control", "no-cache");
					PrintWriter out = arg1.getWriter();
					out.print(jsonString);
					out.flush();
				}

			}

			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public String getUrl() {

		return "/ownerInfo/ownerList";
	}

	@Override
	public boolean isDisabled() {

		return false;
	}

}
