package com.jdry.pms.assignWork.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nl.justobjects.pushlet.util.Log;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.pojo.DispatchPerson;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.DispatchPersonService;
import com.jdry.pms.assignWork.service.ImpairedRepairService;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.SpringUtil;
import com.soft.service.GrapService;

/**
 * 报章报修物管端
 * 
 * @author hezuping
 *         com.jdry.pms.assignWork.controller.ImpairedRepairServiceInterface
 */
@Repository
@Component
public class ImpairedRepairServiceInterface {
	@Resource
	ImpairedRepairService impairedRepairService;

	@Resource
	AssignWorkService assignWorkService;
	// 收费接口
	@Resource
	ChargeInfoService chargeInfoService;

	@Resource
	GrapService grapService;

	@Resource
	CommUtil commUtil;

	@Resource
    DispatchPersonService dispatchPersonService;
	/**
	 * 描述：物管端报障报修获取派工历史
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProptyImpairRepHistory(String handleId) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String status = "0";
		String message = "请联系技术人员";

		if (impairedRepairService == null) {
			impairedRepairService = (ImpairedRepairService) SpringUtil
					.getObjectFromApplication("impairedRepairServiceImpl");
		}
		if (chargeInfoService == null)// 创建上下文环境
		{
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		}
		Map map = new HashMap();
		JSONObject dispatch_handle = (JSONObject) JSON.parse(handleId);
		String dispatch_handle_id = (String) dispatch_handle.get("handle_id");

		List ImpariList = impairedRepairService.getProptyImpairRepHistory(dispatch_handle_id);

		List ls = new ArrayList();
		if (ImpariList.size() < 1) {
			status = "1";
			message = "无相关数据";
		} else {
			for (int i = 0, len = ImpariList.size(); i < len; i++) {
				Map mp = new HashMap();
				Object[] obj = (Object[]) ImpariList.get(i);
				String rpt_id = null == obj[0] ? "" : obj[0].toString();
				mp.put("rpt_id", rpt_id);
				String createTime = null == obj[1] ? "" : obj[1].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!createTime.equals("")) {
						comp_createTime1 = sd.parse(createTime);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status = "0";
					message = "后台异常，请联系技术人员";
					Log.error(message + e.getMessage());
				}

				mp.put("createTime", comp_createTime);
				String event_content = null == obj[2] ? "" : obj[2].toString();
				mp.put("event_content", event_content);
				String address = null == obj[3] ? "" : obj[3].toString();
				mp.put("address", address);
				String dispatch_status = null == obj[4] ? "" : obj[4].toString();
				mp.put("comp_status", dispatch_status);

				String state = null == obj[6] ? "" : obj[6].toString();
				if (state.equals("03")) {
					mp.put("payFlag", "2");// 1 已支付 2未支付 3 其他
				} else if (state.equals("02")) {
					mp.put("payFlag", "1");// 1 已支付 2未支付 3 其他
				} else {
					mp.put("payFlag", "3");// 1 已支付 2未支付 3 其他
				}
				/*
				 * if(dispatch_status.equals("3")){//待回访时候提供
				 * List<ChargeInfoEntity>
				 * chargList=chargeInfoService.queryChargeByWorkId(rpt_id);
				 * if(chargList.size()>1) { String
				 * state=chargList.get(0).getState(); String
				 * state2=chargList.get(1).getState(); if(state.equals(state2))
				 * { if(state.equals("03")) { mp.put("payFlag", "2");//1 已支付
				 * 2未支付 3 其他 }else if(state.equals("02")) { mp.put("payFlag",
				 * "1");//1 已支付 2未支付 3 其他 }else { mp.put("payFlag", "3");//1 已支付
				 * 2未支付 3 其他 } }else { mp.put("payFlag", "2");//1 已支付 2未支付 3 其他
				 * 
				 * }
				 * 
				 * }else if(chargList.size()==1) { String
				 * state=chargList.get(0).getState(); if(state.equals("03")) {
				 * mp.put("payFlag", "2");//1 已支付 2未支付 3 其他 }else
				 * if(state.equals("02")) { mp.put("payFlag", "1");//1 已支付 2未支付
				 * 3 其他 }else { mp.put("payFlag", "3");//1 已支付 2未支付 3 其他 }
				 * 
				 * }else { mp.put("payFlag", "3");//1 已支付 2未支付 3 其他
				 * 
				 * }
				 * 
				 * 
				 * }
				 */

				ls.add(mp);
				status = "1";
				message = "成功";

			}
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", ls);

		String jsonString = JSON.toJSONString(map);

		return jsonString;
	}

	/**
	 * 物管端获取派工详情
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProptyImpairRepDetailByEventId(String rptId) {

		String jsonString = "";
		String status = "0";
		String message = "请联系技术人员";
		JSONObject Obj = (JSONObject) JSON.parse(rptId);
		String rpt_id = (String) Obj.get("rpt_id");// 解析json

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List ls = new ArrayList();
		if (impairedRepairService == null){
			impairedRepairService = (ImpairedRepairService) SpringUtil.getObjectFromApplication("impairedRepairServiceImpl");
		}
		if (chargeInfoService == null){
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		}
		WorkMainEntity work = impairedRepairService.getProptyImpairRepDetailByEventId(rpt_id);
		Map map = new HashMap();
		if (work != null){
			List res = impairedRepairService.getDispatchStepByEventId(work.getRpt_id());
			if (res.size() < 1) {
				status = "1";
				message = "无相关数据";
			} else {
				for (int i = 0, len = res.size(); i < len; i++) {
					Map mp = new HashMap();
					Object[] obj = (Object[]) res.get(i);
					String handle_time = null == obj[0] ? "" : obj[0].toString();
					String comp_createTime = "";
					Date comp_createTime1 = null;
					try {
						if (!handle_time.equals("")) {
							comp_createTime1 = sd.parse(handle_time);
							comp_createTime = sd.format(comp_createTime1);
						}
					} catch (ParseException e) {
						status = "0";
						message = "后台异常，请联系技术人员";
						Log.error(message + e.getMessage());
					}
					mp.put("handle_time", comp_createTime);
					String cname = null == obj[1] ? "" : obj[1].toString();
					if (cname.equals("")) {
						mp.put("cname", "业主");
					} else {
						mp.put("cname", cname);
					}
					String handler_dept = null == obj[2] ? "" : obj[2].toString();
					mp.put("handler_dept", handler_dept);
					String handler_phone = null == obj[3] ? "" : obj[3].toString();
					mp.put("handler_phone", handler_phone);
					String statu = null == obj[4] ? "" : obj[4].toString();
					mp.put("comp_status", statu);
					String mtn_type = null == obj[6] ? "" : obj[6].toString();
					mp.put("mtn_type", mtn_type);
					ls.add(mp);
				}
				status = "1";
				message = "成功";
			}
			// 外层数据
			String comp_createTime = "";
			if (work.getCreateTime() != null && !work.getCreateTime().equals("")) {
				comp_createTime = sd.format(work.getCreateTime());
			}
			map.put("rpt_id", work.getRpt_id());
			map.put("createTime", comp_createTime);
			map.put("event_content", work.getEvent_content() + "");
			map.put("repair_name", work.getRpt_name());
			map.put("phone", work.getIn_call());
			map.put("address", work.getAddress() + "");
			map.put("sum_price", work.getSum_price() + "");
			map.put("material_cost", work.getMaterial_cost() + "");
			map.put("labor_cost", work.getLabor_cost() + "");
			map.put("event_state", work.getEvent_state());
			map.put("dispatch_tools", work.getDispatch_tools());
			String dispatch_visit_lev = null == work.getDispatch_visit_lev() ? "" : work.getDispatch_visit_lev();
			if (dispatch_visit_lev.equals("0")) {
				map.put("dispatch_visit_lev", "非常满意");
			} else if (dispatch_visit_lev.equals("1")) {
				map.put("dispatch_visit_lev", "满意");
			} else if (dispatch_visit_lev.equals("2")) {
				map.put("dispatch_visit_lev", "不满意");
			} else {
				map.put("dispatch_visit_lev", "");
			}

			String dispatch_evaluate = work.getDispatch_evaluate();
			map.put("dispatch_evaluate", dispatch_evaluate);

			String dispatch_visit_record = work.getDispatch_visit_record();
			map.put("dispatch_visit_record", dispatch_visit_record);
			// if(event_state.equals("3")){//待回访时候提供
			List<ChargeInfoEntity> chargList = chargeInfoService.queryChargeByWorkId(work.getRpt_id());
			if (chargList.size() > 1) {
				String state = chargList.get(0).getState();
				String state2 = chargList.get(1).getState();
				if (state.equals(state2)) {
					if (state.equals("03")) {
						map.put("payFlag", "2");// 1 已支付 2未支付 3 其他
					} else if (state.equals("02")) {
						map.put("payFlag", "1");// 1 已支付 2未支付 3 其他
					} else {
						map.put("payFlag", "3");// 1 已支付 2未支付 3 其他
					}
				} else {
					map.put("payFlag", "2");// 1 已支付 2未支付 3 其他

				}

			} else if (chargList.size() == 1) {
				String state = chargList.get(0).getState();
				if (state.equals("03")) {
					map.put("payFlag", "2");// 1 已支付 2未支付 3 其他
				} else if (state.equals("02")) {
					map.put("payFlag", "1");// 1 已支付 2未支付 3 其他
				} else {
					map.put("payFlag", "3");// 1 已支付 2未支付 3 其他
				}

			} else {
				map.put("payFlag", "3");// 1 已支付 2未支付 3 其他

			}
			// }
			String img_url = null == work.getImg_url() ? "" : work.getImg_url();
			map.put("img_url", img_url);
			map.put("dispatch_result", work.getDispatch_result());
			map.put("repairDetail", ls);
			String finishImgUrl = null == work.getFinish_img_url() ? "" : work.getFinish_img_url();
			map.put("finishImgUrl", finishImgUrl);

			Map jmp = new HashMap();
			jmp.put("status", status);
			jmp.put("message", message);
			jmp.put("data", map);

			jsonString = JSON.toJSONString(jmp);
		}

		return jsonString;
	}

	/**
	 * 到达现场、确认维修信息
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String dispachHandleByApp(String json) {
		boolean flag = false;
		String jsonString = "";
		String status = "0";
		String message = "请联系技术人员，处理存在问题";
		// String data=arg0.getParameter("data");
		JSONObject Obj = (JSONObject) JSON.parse(json);
		String rpt_id = null == Obj.getString("rpt_id") ? "" : Obj.getString("rpt_id");// 解析json
		String labor_cost = null == Obj.getString("labor_cost") ? "" : Obj.getString("labor_cost");
		String material_cost = null == Obj.getString("material_cost") ? "" : Obj.getString("material_cost");
		String sum_price = null == Obj.getString("sum_price") ? "" : Obj.getString("sum_price");
		String mtn_type = null == Obj.getString("mtn_type") ? "" : Obj.getString("mtn_type");
		String mtn_priority = null == Obj.getString("mtn_priority") ? "" : Obj.getString("mtn_priority");
		String rejectReason = null == Obj.getString("rejectReason") ? "" : Obj.getString("rejectReason");
		try {
			rejectReason = URLDecoder.decode(rejectReason, "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		if (assignWorkService == null)// 初始化上下文环境
		{
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		}

		if (grapService == null)// 初始化上下文环境
		{
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		}
		WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
		/**
		 * 驱动流程图
		 */
		WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);

		if (mainEntity == null) {
			mainEntity = new WorkMainEntity();
		}

		String state = "";
		dispatchEntity.setMtn_type(mtn_type);
		dispatchEntity.setDispatch_expense(sum_price);// 总价
		if (!labor_cost.equals("") && labor_cost.equals("0")) {
			dispatchEntity.setLabor_cost(0.0);

		} else {
			dispatchEntity.setLabor_cost(Double.parseDouble(labor_cost));
		}
		if (!material_cost.equals("") && material_cost.equals("0")) {
			dispatchEntity.setMaterial_cost(0.0);
		} else {
			dispatchEntity.setMaterial_cost(Double.parseDouble(material_cost));
			dispatchEntity.setDispatch_result("");
		}
		dispatchEntity.setDispatch_arrive_time(new Date());

		dispatchEntity.setMtn_priority(mtn_priority);// 业主处理意见
		state = dispatchEntity.getDispatch_status();
		// 流程条件
		Map<String, Object> variables = new HashMap<String, Object>();
		String compFlag = "";
		// flag==2 质保期...公共维修, flag==1 现场确认及费用核算
		if (null != mtn_type && mtn_type.equals("1")) {
			// 日常紧急维修
			variables.put("flag", "2");
			// compFlag==1 业主拒绝维修 compFlag==2业主确认维修
			if (null != mtn_priority && mtn_priority.equals("0")) {
				dispatchEntity.setDispatch_status("2");// 2017115
				state = dispatchEntity.getDispatch_status();
				// 业主维修意见
				compFlag = "2";
				variables.put("compFlag", compFlag);

			} else if (null != mtn_priority && mtn_priority.equals("1")) {
				mainEntity.setOrder_state("拒单");
				assignWorkService.addWorkMain(mainEntity);
				compFlag = "1";
				variables.put("compFlag", compFlag);
				dispatchEntity.setDispatch_status("3");// 2017115
				dispatchEntity.setDispatch_result("拒绝维修");
				dispatchEntity.setRejectReason(rejectReason);
				dispatchEntity.setDispatch_finish_time(new Date());
				state = dispatchEntity.getDispatch_status();
			}
		} else {
			dispatchEntity.setDispatch_status("2");// 2017115
			state = dispatchEntity.getDispatch_status();
			variables.put("flag", "1");
		}

		String processinstanceid = mainEntity.getProcessInstanceId() + "";
		if (null != processinstanceid && processinstanceid.length() > 0) {
			grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), variables,
					state);
			status = "1";
			message = "成功";
		}

		/**
		 * 描述：推荐流程多走一步
		 */
		if (null != processinstanceid && processinstanceid.length() > 0) {
			grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), variables, "3");
			status = "1";
			message = "成功";
		}

		// 派工历史纪录
		EventSendEntity send = new EventSendEntity();
		send.setHandle_time(dispatchEntity.getDispatch_arrive_time());
		send.setOwnHandler(dispatchEntity.getDispatch_handle_id());
		send.setEvent_id(dispatchEntity.getMtn_id());

		String deptName = assignWorkService.getdept(dispatchEntity.getDispatch_handle_id());
		String dept_name = null == deptName ? "" : deptName;
		if (dept_name.equals("")) {
			send.setHandler_dept("系统");
		} else {
			send.setHandler_dept(dept_name);
		}
		String phone = assignWorkService.getHandlePhone(dispatchEntity.getDispatch_handle_id());
		send.setHandler_phone(phone);
		send.setStatus(state);
		send.setMtn_type(mtn_type);

		assignWorkService.addWorkDispatch(dispatchEntity);
		assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(), dispatchEntity.getDispatch_status());// 更新主表状态
		assignWorkService.addEventSend(send);
		if (compFlag.equals("1")) {
			dispatchEntity.setDispatch_status("3");
			dispatchEntity.setDispatch_result("拒绝维修");
			assignWorkService.addWorkDispatch(dispatchEntity);
			assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(), dispatchEntity.getDispatch_status());// 更新主表状态
			send.setStatus("3");
			assignWorkService.addEventSend(send);
			mainEntity.setOrder_state("拒单");
			mainEntity.setEvent_state(dispatchEntity.getDispatch_status());
			assignWorkService.addWorkMain(mainEntity);
			status = "1";
			message = "拒绝维修成功";
		}
		// 封装给物管APP
		Map map = new HashMap();
		map.put("status", status);
		map.put("message", message);
		map.put("rpt_id", dispatchEntity.getMtn_id());
		jsonString = JSON.toJSONString(map);
		return jsonString;
	}

	/**
	 * 维修完成,消单
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String impairedRepairFinsh(String json) {
		String status = "0";
		String message = "请联系技术人员，处理存在问题";
		boolean flag = false;
		if (assignWorkService == null) {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		}
		if (grapService == null) {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		}
		if (chargeInfoService == null) {
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		}
		String str = "";
		try {
			str = URLDecoder.decode(json, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		String state = "";
		JSONObject Obj = (JSONObject) JSON.parse(str);
		String rpt_id = (String) Obj.get("rpt_id");// 解析json
		String finishImgUrl = null == Obj.getString("finishImgUrl") ? "" : Obj.getString("finishImgUrl");// 解析json
		String dispatch_tools = null == Obj.getString("dispatch_tools") ? "" : Obj.getString("dispatch_tools");
		EventSendEntity send = new EventSendEntity();
		WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
		WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);
		dispatchEntity.setDispatch_finish_time(new Date());
		dispatchEntity.setDispatch_status("3");
		state = dispatchEntity.getDispatch_status();
		if (!"".equals(finishImgUrl)) {
			dispatchEntity.setFinishImgUrl(finishImgUrl);
		}
		if (!dispatch_tools.isEmpty()) {
			dispatchEntity.setDispatch_tools(dispatch_tools);
		}
		// =================================
		try {
			Map map = new HashMap<String, Object>();
			map.put("room_id", mainEntity.getOwner_house());
			map.put("room_no", mainEntity.getRoomNo());
			map.put("owner_id", mainEntity.getOwner_id());
			map.put("owner_name", mainEntity.getRpt_name());
			map.put("begin_time", getSimp(new Date()));
			map.put("end_time", getSimp(new Date()));
			map.put("oper_emp_id", dispatchEntity.getDispatch_handle_id());
			double mCost = dispatchEntity.getMaterial_cost();
			if ((new Double(mCost * 100)).intValue() != 0) {
				map.put("work_id", rpt_id);
				map.put("charge_type_no", "0007");
				map.put("charge_type_name", "维修材料费");
				map.put("receive_amount", mCost);// 材料费
				try {
					flag = chargeInfoService.addCharge(map);
				} catch (Exception e) {
					Log.error("维修材料费用接口异常，请排查:" + e.getMessage());
				}

				status = "1";
				message = "成功";
			}
			// if(dispatchEntity.getMaterial_cost().equals("0")||dispatchEntity.getMaterial_cost()==0.0||dispatchEntity.getMaterial_cost()==null)
			// {}else
			// {
			// map.put("work_id", rpt_id);
			// map.put("charge_type_no", "0007");
			// map.put("charge_type_name", "维修材料费");
			// map.put("receive_amount",
			// dispatchEntity.getMaterial_cost());//材料费
			// try {
			// flag=chargeInfoService.addCharge(map);
			// } catch (Exception e)
			// {
			// Log.error("维修材料费用接口异常，请排查:"+e.getMessage());
			// }
			//
			// status="1";
			// message="成功";
			// }
			double lCost = dispatchEntity.getLabor_cost();
			if ((new Double(lCost * 100)).intValue() != 0) {
				map.put("work_id", rpt_id);
				map.put("charge_type_no", "0006");
				map.put("charge_type_name", "维修人工费");
				map.put("receive_amount", lCost);// 人工费
				try {
					flag = chargeInfoService.addCharge(map);
				} catch (Exception e) {
					Log.error("人工费用接口异常，请排查:" + e.getMessage());
				}
				status = "1";
				message = "成功";
			}
			// if(dispatchEntity.getLabor_cost().equals("0")||dispatchEntity.getLabor_cost()==0.0||dispatchEntity.getLabor_cost().equals("0.0")||dispatchEntity.getLabor_cost()==null)
			// {
			// }else
			// {
			// map.put("work_id", rpt_id);
			// map.put("charge_type_no", "0006");
			// map.put("charge_type_name", "维修人工费");
			// map.put("receive_amount", dispatchEntity.getLabor_cost());//人工费
			// try {
			// flag=chargeInfoService.addCharge(map);
			// } catch (Exception e)
			// {
			// Log.error("人工费用接口异常，请排查:"+e.getMessage());
			// }
			// status="1";
			// message="成功";
			// }

		} catch (Exception e) {
			status = "0";
			message = "系统异常，请联系管理员";
		}
		// =========================================
		send.setHandle_time(new Date());// 处理时间
		send.setOwnHandler(dispatchEntity.getDispatch_handle_id());
		send.setEvent_id(dispatchEntity.getMtn_id());
		String deptName = assignWorkService.getdept(dispatchEntity.getDispatch_handle_id());
		String dept_name = null == deptName ? "" : deptName;
		if (dept_name.equals("")) {
			send.setHandler_dept("系统");
		} else {
			send.setHandler_dept(dept_name);
		}
		String phone = assignWorkService.getHandlePhone(dispatchEntity.getDispatch_handle_id());
		send.setHandler_phone(phone);
		send.setStatus(state);

		String processinstanceid = mainEntity.getProcessInstanceId() + "";
		assignWorkService.addWorkDispatch(dispatchEntity);
		// ===
		mainEntity.setOrder_state("正常");
		mainEntity.setFinishTime(new Date());
		assignWorkService.addWorkMain(mainEntity);
		// 完成任务
		assignWorkService.addWorkWorkState(dispatchEntity.getDispatch_handle_id(), 0);
		assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(), dispatchEntity.getDispatch_status());// 更新主表状态
		assignWorkService.addEventSend(send);
		if (null != processinstanceid && processinstanceid.length() > 0) {
			grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), null, state);
			status = "1";
			message = "成功";
		}

		Map map = new HashMap();
		map.put("status", status);
		map.put("message", message);
		map.put("rpt_id", dispatchEntity.getMtn_id());
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}

	/**
	 * 物管取消工单——拒单
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally", "unused" })
	public String confirmBeforeCancel(String json) {
		String jsonString = "";
		String status = "1";
		String type = "3";
		String message = "取消成功";
		// String data=arg0.getParameter("data");
		JSONObject Obj = (JSONObject) JSON.parse(json);
		String rpt_id = (String) Obj.get("rpt_id");// 解析json
		String handler = null == Obj ? "" : Obj.get("handler_id").toString();
		String rejectReason = null == Obj ? "" : Obj.get("rejectReason").toString();
		try {
			rejectReason = URLDecoder.decode(rejectReason, "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		if (assignWorkService == null){
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		}
		if (chargeInfoService == null){
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		}
		if (grapService == null){
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		}
		WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);

		/**
		 * 驱动流程图
		 */
		WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);

		if (mainEntity == null) {
			mainEntity = new WorkMainEntity();
		}

		dispatchEntity.setDispatch_status(type);
		dispatchEntity.setDispatch_result("拒绝维修");
		dispatchEntity.setRejectReason(rejectReason);
		dispatchEntity.setDispatch_finish_time(new Date());
		mainEntity.setEvent_state(type);
		mainEntity.setOrder_state("拒单");
		mainEntity.setFinishTime(new Date());

		EventSendEntity send = new EventSendEntity();
		send.setHandle_time(dispatchEntity.getDispatch_arrive_time());
		send.setOwnHandler(dispatchEntity.getDispatch_handle_id());
		send.setEvent_id(dispatchEntity.getMtn_id());

		String deptName = assignWorkService.getdept(dispatchEntity.getDispatch_handle_id());
		String dept_name = null == deptName ? "" : deptName;
		if (dept_name.equals("")) {
			send.setHandler_dept("系统");
		} else {
			send.setHandler_dept(dept_name);
		}
		String phone = assignWorkService.getHandlePhone(dispatchEntity.getDispatch_handle_id());
		send.setHandler_phone(phone);
		send.setStatus(type);

		String processinstanceid = mainEntity.getProcessInstanceId() + "";
		if (null != processinstanceid && processinstanceid.length() > 0) {
			grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), null, null);
			status = "1";
			message = "成功";
		}

		/**
		 * 描述：推荐流程多走两步
		 */
		if (null != processinstanceid && processinstanceid.length() > 0) {
			grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), null, null);
			grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), null, null);
			status = "1";
			message = "成功";
		}

		assignWorkService.addWorkDispatch(dispatchEntity);
		assignWorkService.addWorkMain(mainEntity);
		// assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
		assignWorkService.addEventSend(send);
		ChargeInfoEntity chargeType = new ChargeInfoEntity();
		chargeType.setWork_id(dispatchEntity.getMtn_id());
		// 删除账单信息
		try {
			chargeInfoService.delete(chargeType);
		} finally {
			// 封装给物管APP
			Map map = new HashMap();
			map.put("status", status);
			map.put("message", message);
			map.put("rpt_id", dispatchEntity.getMtn_id());
			jsonString = JSON.toJSONString(map);
			return jsonString;
		}
	}

	/**
	 * 客户端修改金额接口
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDispatcherCost(String json) {
		if (assignWorkService == null) {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		}
		if (chargeInfoService == null) {
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		}
		JSONObject Obj = (JSONObject) JSON.parse(json);
		String rpt_id = (String) Obj.get("rpt_id");// 解析json
		String labor_cost = null == Obj.get("labor_cost") ? "0" : Obj.get("labor_cost").toString();
		String material_cost = null == Obj.get("material_cost") ? "0" : Obj.get("material_cost").toString();
		String dispatch_expense = null == Obj.get("dispatch_expense") ? "0" : Obj.get("dispatch_expense").toString();
		String description = null == Obj.get("description") ? "" : Obj.get("description").toString();
		WorkMainDispatchEntity dispatch = assignWorkService.getWorkDispatchById(rpt_id);
		if (dispatch == null) {
			dispatch = new WorkMainDispatchEntity();
		}
		dispatch.setLabor_cost(Double.parseDouble(labor_cost));
		dispatch.setMaterial_cost(Double.parseDouble(material_cost));
		dispatch.setDispatch_expense(dispatch_expense);
		assignWorkService.addWorkDispatch(dispatch);
		String status = "1";
		String message = "费用修改成功";
		Map map = new HashMap();
		map.put("status", status);
		map.put("message", message);
		map.put("rpt_id", rpt_id);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}

	/**
	 * 查询所有未派工工单，用于客户端待接单列表
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryAllNoDispatch(String data) {
		if (impairedRepairService == null) {
			impairedRepairService = (ImpairedRepairService) SpringUtil
					.getObjectFromApplication("impairedRepairServiceImpl");
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String status = null;
		String message = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<?> list = impairedRepairService.queryAllNoDispatch();
		List result = new ArrayList();
		if (list.size() < 1) {
			status = "1";
			message = "无相关数据";
		} else {
			for (int i = 0, len = list.size(); i < len; i++) {
				Map mp = new HashMap();
				Object[] obj = (Object[]) list.get(i);
				String rpt_id = null == obj[0] ? "" : obj[0].toString();
				mp.put("rpt_id", rpt_id);
				String createTime = null == obj[1] ? "" : obj[1].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!createTime.equals("")) {
						comp_createTime1 = sd.parse(createTime);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {
					status = "0";
					message = "后台异常，请联系技术人员";
					Log.error(message + e.getMessage());
				}
				mp.put("createTime", comp_createTime);
				String event_content = null == obj[2] ? "" : obj[2].toString();
				mp.put("event_content", event_content);
				String address = null == obj[3] ? "" : obj[3].toString();
				mp.put("address", address);
				String dispatch_status = null == obj[4] ? "" : obj[4].toString();
				mp.put("dispatch_status", dispatch_status);
				result.add(mp);
				status = "1";
				message = "成功";
			}
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", result);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}

	/**
	 * 客户端接单操作 接单后，工单推进流程至已派工
	 */
	public String dipatchByClient(String data) {
		if (assignWorkService == null) {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		}
		if (commUtil == null) {
			commUtil = (CommUtil) SpringUtil.getObjectFromApplication("commUtil");
		}
		if (impairedRepairService == null) {
			impairedRepairService = (ImpairedRepairService) SpringUtil
					.getObjectFromApplication("impairedRepairServiceImpl");
		}
		String str = "";
		try {
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		JSONObject obj = JSON.parseObject(str);
		String rpt_id = obj.getString("rpt_id") == null ? "" : obj.getString("rpt_id");
		String dispatch_handle_id = obj.getString("dispatch_handle_id") == null ? ""
				: obj.getString("dispatch_handle_id");
		String dispatch_handle_username = obj.getString("dispatch_handle_username") == null ? ""
				: obj.getString("dispatch_handle_username");
		WorkMainEntity assignWork = assignWorkService.getWorkMainById(rpt_id);

		// 派工单生成
		WorkMainDispatchEntity wormDispatch = null;
		wormDispatch = assignWorkService.getWorkDispatchById(rpt_id);
		if (null == wormDispatch) {
			wormDispatch = new WorkMainDispatchEntity();
			wormDispatch.setDispatch_kid(CommUtil.getGuuid());
		}
		wormDispatch.setMtn_id(rpt_id);
		wormDispatch.setMtn_detail(assignWork.getEvent_content());
		wormDispatch.setMtn_emergency("0");
		wormDispatch.setDispatch_time(new Date());
		wormDispatch.setCreateby(dispatch_handle_id);
		wormDispatch.setCreateTime(new Date());// 派工时间
		wormDispatch.setDispatch_status("1");
		wormDispatch.setDispatch_handle_id(dispatch_handle_id);
		wormDispatch.setDispatch_handle_name(dispatch_handle_username);
		wormDispatch.setDispatch_id(commUtil.getBusinessId("pg"));

		// 流程记录生成
		EventSendEntity send = new EventSendEntity();
		send.setHandle_time(new Date());
		send.setOwnHandler(dispatch_handle_id);
		String deptName = assignWorkService.getdept(dispatch_handle_id);
		String phone = assignWorkService.getHandlePhone(dispatch_handle_id);
		String dept_name = null == deptName ? "" : deptName;
		if (dept_name.equals("")) {
			send.setHandler_dept("");
		} else {
			send.setHandler_dept(dept_name);
		}
		send.setHandler_phone(phone);
		send.setStatus("1");
		send.setEvent_id(rpt_id);
		
		// 人员工单关系生成
		DispatchPerson person=new DispatchPerson();
		person.setRpt_id(rpt_id);
		person.setHandle_id(dispatch_handle_id);
		person.setHandle_name(dispatch_handle_username);

		// 工单保存、流程推进处理
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("rptId", rpt_id);
		parameter.put("dispatch", wormDispatch);
		parameter.put("send", send);
		parameter.put("person", person);
		parameter.put("assignWork", assignWork);
		parameter.put("dispatch_handle_username", dispatch_handle_username);
		String jsonString = impairedRepairService.dipatchByClient(parameter);
		return jsonString;
	}

	/**
	 * 时间转换
	 * 
	 * @param time
	 * @return
	 */
	public String getSimp(Date time) {

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String time1 = sd.format(time);
		return time1;

	}

}
