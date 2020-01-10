package com.jdry.pms.chargeManager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialEntity;
import com.jdry.pms.chargeManager.pojo.ChargeSerialViewEntity;
import com.jdry.pms.chargeManager.service.ChargeArrearViewService;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.chargeManager.service.ChargeSerialService;
import com.jdry.pms.chargeManager.util.ChargeUtil;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class ChargeSerialController implements IController {
	@Resource
	private ChargeInfoService service;
	@Resource
	private ChargeArrearViewService ArrearService;

	@Resource
	private DirectoryService dirService;
	@Resource
	private ChargeSerialService serialService;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		PrintWriter out = arg1.getWriter();
		String roomId = arg0.getParameter("room_id") == null ? "" : arg0.getParameter("room_id");
		try {

			DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
			arg0.setAttribute("loginUser", user);

			// 预收押金页面的表单显示
			if (method.equalsIgnoreCase("advaceList")) {
				int currentPage = arg0.getParameter("offset") == null ? 1
						: Integer.parseInt(arg0.getParameter("offset"));// 每页行数
				int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
				String date_select = arg0.getParameter("date_select") == null ? "" : arg0.getParameter("date_select");
				String date_search = arg0.getParameter("date_search") == null ? "" : arg0.getParameter("date_search");
				// search = CharacterUtil.getUTF_8String(search);
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				currentPage += 1;
				Map<String, Object> parameter = new HashMap();
				parameter.put("search", search);
				parameter.put("date_select", date_select);
				parameter.put("date_search", date_search);
				// charge_type类型有01正常，02押金，03预收，charge_type_opp默认为false,传入ture表明取反。
				// 本例中后台sql语句会“charge_type<>'01'”来查询
				parameter.put("charge_type", "01");
				parameter.put("charge_type_opp", "true");
				// 不等于抵扣01
				parameter.put("paid_mode", "01");
				parameter.put("paid_mode_opp", "true");

				Page<ChargeSerialViewEntity> page = new Page<ChargeSerialViewEntity>(showCount, currentPage);
				serialService.queryAll(page, parameter);
				List<ChargeSerialViewEntity> result = (List<ChargeSerialViewEntity>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数			
				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录	
				out.print(r);

			}

			if (method.equalsIgnoreCase("serialList")) {
				int currentPage = arg0.getParameter("offset") == null ? 1
						: Integer.parseInt(arg0.getParameter("offset"));// 每页行数
				int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
				String begin_time = arg0.getParameter("begin_time") == null ? "" : arg0.getParameter("begin_time");
				String end_time = arg0.getParameter("end_time") == null ? "" : arg0.getParameter("end_time");
				String room_id =  arg0.getParameter("room_id") == null ? "" : arg0.getParameter("room_id");
				String charge_type_select =  arg0.getParameter("charge_type_select") == null ? "" : arg0.getParameter("charge_type_select");
				String paid_mode_select =  arg0.getParameter("paid_mode_select") == null ? "" : arg0.getParameter("paid_mode_select");

				search = CharacterUtil.getUTF_8String(search);
				if (currentPage != 0) {// 获取页数
					currentPage = currentPage / showCount;
				}
				currentPage += 1;
				Map<String, Object> parameter = new HashMap();
				parameter.put("search", search);
				parameter.put("begin_time", begin_time);
				parameter.put("end_time", end_time);
				parameter.put("room_id", room_id);
				parameter.put("charge_type_select", charge_type_select);
				parameter.put("paid_mode_select", paid_mode_select);

				Page<ChargeSerialViewEntity> page = new Page<ChargeSerialViewEntity>(showCount, currentPage);
				serialService.queryAll(page, parameter);
				List<ChargeSerialViewEntity> result = (List<ChargeSerialViewEntity>) page.getEntities();
				String b = JSON.toJSONString(result);
				long count = page.getEntityCount(); // 获取总记录数
				String r = "{\"total\":" + count + ",\"rows\":" + b + "}"; // 服务端分页必须返回total和rows,rows为每页记录
				out.print(r);

			}
			// 获取详情
			if (method.equalsIgnoreCase("chargeSerialDetailInfo")) {
				String serial_id = arg0.getParameter("serial_id") == null ? "" : arg0.getParameter("serial_id");
				List<ChargeInfoViewEntity> list = service.queryChargeBySerial(serial_id);
				String b = JSON.toJSONString(list);
				out.print(b);
			}

			// 新增预收款保存/退费
			if (method.equalsIgnoreCase("insert")) {
				String room_no = arg0.getParameter("room_no");
				String owner_id = arg0.getParameter("owner_id");
				String owner_name = arg0.getParameter("owner_name");
				String paid_type = arg0.getParameter("paid_type");
				String paid_amount = arg0.getParameter("paid_amount");
				String paid_mode = arg0.getParameter("paid_mode");
				String paid_date = arg0.getParameter("paid_date");
				String charge_name = arg0.getParameter("charge_name");
				String remark = arg0.getParameter("remark");
				String receipt_id = arg0.getParameter("receipt_id");
				String reduce_url = arg0.getParameter("reduce_url");
				String use = arg0.getParameter("use");
				
				ChargeSerialEntity ser = new ChargeSerialEntity();
				ser.setRoom_id(roomId);
				ser.setRoom_no(room_no);
				ser.setOwner_id(owner_id);
				ser.setOwner_name(owner_name);
				if (paid_amount != null && !paid_amount.isEmpty()) {
					BigDecimal bigCount = new BigDecimal(paid_amount);
					ser.setPaid_amount(bigCount);
					ser.setReceive_amount(bigCount);
				}

				//ser.setRoom_type(use); // 借用字段传参缴费用途类型
				ser.setPaid_date(ChargeUtil.StrToDateTime(paid_date));
				ser.setBegin_date(ChargeUtil.StrToDateTime(paid_date));
				ser.setEnd_date(ChargeUtil.StrToDateTime(paid_date));

				ser.setPaid_mode(paid_mode);
				ser.setUpdate_date(new Date());
				ser.setOper_emp_id(CommUser.getUserName());
				ser.setCharge_type_name(charge_name);
				ser.setCharge_type_no(use);

				ser.setCharge_type(paid_type);
				ser.setState("01");
				ser.setRemark(remark);
				ser.setReceipt_id(receipt_id);
				ser.setReduce_url(reduce_url);

				serialService.save(ser);
			}

			// 退费
			if (method.equalsIgnoreCase("returnPay")) {
				String serial_id = arg0.getParameter("serial_id");
				ChargeSerialEntity chargeSerialEntity = new ChargeSerialEntity();
				chargeSerialEntity.setSerial_id(serial_id);
				serialService.delete(chargeSerialEntity);
				JSONObject obj = new JSONObject();
				obj.put("status", "200");
				obj.put("msg", "成功");
				out.println(obj.toJSONString());
			}

			// 押金转预存
			if (method.equalsIgnoreCase("toPrestore")) {
				String select = arg0.getParameter("select");
				ChargeSerialEntity serial = JSON.parseObject(select, ChargeSerialEntity.class);

				boolean isSuccess = serialService.toPrestore(serial);
				if (!isSuccess) {
					JSONObject obj = new JSONObject();
					obj.put("status", "104");
					obj.put("msg", "押金转预存失败");
					out.println(obj.toJSONString());
				}else
				{
					JSONObject obj = new JSONObject();
					obj.put("status", "200");
					obj.put("msg", "成功");
					out.println(obj.toJSONString());
				}
			}

			// 通过小区id获取关联收费项目
			if (method.equalsIgnoreCase("chargingItem")) {
				String room_id = arg0.getParameter("roomId");
				List list = serialService.chargingItem(room_id);
				out.print(JSON.toJSONString(list));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}

	@Override
	public String getUrl() {
		return "/ChargeManager/ChargeSerial";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
}