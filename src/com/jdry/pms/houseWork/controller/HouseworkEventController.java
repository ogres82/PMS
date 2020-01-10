package com.jdry.pms.houseWork.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;
import com.jdry.pms.houseWork.pojo.HouseworkEventEntity;
import com.jdry.pms.houseWork.service.HouseworkEventService;
import com.soft.service.GrapService;

/**
 * 描述：家政服务事件受理
 * 
 * @author hezuping
 *
 */
@Repository
@Component
public class HouseworkEventController implements IController {

	@Resource
	CommUtil commUtil;
	@Resource
	HouseworkEventService houseworkEventService;
	@Resource
	GrapService grapService;
	@Resource
	AssignWorkService assignWorkService;
	static Logger log = Logger.getLogger(HouseworkEventController.class);

	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("pragma", "no-cache");
		resp.setHeader("cache-control", "no-cache");
		String method = req.getParameter("method");
		String workId = null == req.getParameter("workId") ? "" : req.getParameter("workId").toString();// 业务编号
		DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
		if (method.equals("addHouseWork")) {

			HouseworkEventEntity house = new HouseworkEventEntity();
			house.setVerify_oper_id(user.getUsername());
			house.setVerify_oper_name(user.getCname());
			house.setAccept_time(new Date());
			String jsonString = JSON.toJSONString(house);
			PrintWriter out = resp.getWriter();
			out.println(jsonString);
			out.flush();

		}
		if (method.equals("houseWorkSave"))// 事件临时保存
		{
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			String id = req.getParameter("id") == null ? "" : req.getParameter("id");
			String event_from = req.getParameter("event_from") == null ? "" : req.getParameter("event_from");
			String ownerId = req.getParameter("ownerId") == null ? "" : req.getParameter("ownerId");
			String rpt_name = req.getParameter("rpt_name") == null ? "" : req.getParameter("rpt_name");
			String link_phone = req.getParameter("link_phone") == null ? "" : req.getParameter("link_phone");
			String user_address = req.getParameter("addres") == null ? "" : req.getParameter("addres");
			String event_title = req.getParameter("event_title") == null ? "" : req.getParameter("event_title");
			String pre_time = req.getParameter("pre_time") == null ? "" : req.getParameter("pre_time");
			String event_content = req.getParameter("event_content") == null ? "" : req.getParameter("event_content");
			String verify_oper_id = req.getParameter("verify_oper_id") == null ? "" : req.getParameter("verify_oper_id");
			String accept_time = req.getParameter("accept_time") == null ? "" : req.getParameter("accept_time");
			String link_man_name = rpt_name;
			String msg = "";
			String room_id = req.getParameter("roomId") == null ? "" : req.getParameter("roomId");
			boolean flag = false;
			if (event_no.equals("")) {
				event_no = commUtil.getBusinessId("IM", "D");
			}
			String processKey = "";
			HouseworkEventEntity work = houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			if (work == null) {
				work = new HouseworkEventEntity();

			}
			work.setEvent_no(event_no);
			work.setEvent_from(event_from);
			work.setLink_man(ownerId);
			work.setEvent_state(HouseWorkTool.WITHOUTSEND);// 未派工
			work.setRoom_id(room_id);
			work.setUser_address(user_address);
			work.setLink_man_name(link_man_name);
			// work.setLink_man_name(link_man_name);

			if (id.equals("")) {

			} else {
				work.setId(id);
			}

			if (accept_time.equals("")) {
				work.setAccept_time(new Date());
			} else {
				try {
					work.setAccept_time(sd.parse(accept_time));
					if (pre_time.equals("")) {
						work.setPre_time(null);
					} else {
						work.setPre_time(sd.parse(pre_time));
					}
				} catch (ParseException e) {
					log.info("解析时间异常" + e.getMessage());
					flag = false;
				}
			}
			work.setCall_phone(link_phone);
			work.setEvent_title(event_title);
			work.setEvent_content(event_content);
			if (verify_oper_id.equals("") || verify_oper_id == null) {
				work.setVerify_oper_id(user.getUsername());
			} else {
				work.setVerify_oper_id(verify_oper_id);

			}
			processKey = "houseWorkProcess";
			String repeaEevent_no = houseworkEventService.findEventNOByNo(event_no);
			EventSendEntity send = new EventSendEntity();
			// 解决保存慢问题
			if (!repeaEevent_no.equals(event_no)) {
				work.setBpm_processId(Long.parseLong(startHouseWrokBpm(processKey)));
				send.setHandle_time(work.getAccept_time());
				send.setOwnHandler(work.getVerify_oper_id());
				String deptName = assignWorkService.getdept(work.getVerify_oper_id());
				String dept_name = null == deptName ? "" : deptName;
				if (dept_name.equals("")) {
					send.setHandler_dept("系统");
				} else {
					send.setHandler_dept(dept_name);
				}
				send.setHandler_phone(work.getCall_phone());
				send.setStatus(work.getEvent_state());
				send.setEvent_id(event_no);
			}
			/**
			 * 历史流程
			 */

			assignWorkService.addEventSend(send);

			flag = houseworkEventService.saveHouseWorkEvent(work);

			if (flag) {
				msg = "工单：" + event_no;
			} else {
				msg = "faild";
			}
			PrintWriter out = resp.getWriter();
			out.print(JSON.toJSONString(msg));
			out.flush();

		}
		if (method.equals("listHouseWork"))// 事件临时保存展示
		{

			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数
			int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
			String search = java.net.URLDecoder.decode(req.getParameter("search"), "UTF-8");
			String businessFromSearch = req.getParameter("businessFromSearch");
			String CommNameSearch = java.net.URLDecoder.decode(req.getParameter("CommNameSearch"), "UTF-8");
			String dateSearch = req.getParameter("dateSearch");
			if (currentPage != 0) {// 获取页数
				currentPage = currentPage / showCount;
			}
			currentPage += 1;
			Map<String, Object> parameter = new HashMap();
			if (search.equals("undefined")) {
				search = "";
			}
			parameter.put("search", search);
			parameter.put("businessFromSearch", businessFromSearch);
			parameter.put("CommNameSearch", CommNameSearch);
			parameter.put("dateSearch", dateSearch);
			Page page = new Page(showCount, currentPage);
			List res = houseworkEventService.queryHouseWorkEventInfo(page, parameter, null);
			List list = new ArrayList();
			for (int i = 0; i < res.size(); i++) {
				Map map = new HashMap();
				Object[] obj = (Object[]) res.get(i);
				String event_no = null == obj[0] ? "" : obj[0].toString();
				map.put("event_no", event_no);
				String event_title = null == obj[1] ? "" : obj[1].toString();
				map.put("event_title", event_title);
				String call_phone = null == obj[2] ? "" : obj[2].toString();
				map.put("call_phone", call_phone);

				String accept_time = null == obj[3] ? "" : obj[3].toString();
				String pre_time = null == obj[6] ? "" : obj[6].toString();

				if (!accept_time.equals("")) {
					map.put("accept_time", getSimp(accept_time));
				} else {
					map.put("accept_time", accept_time);
				}
				if (!pre_time.equals("")) {
					map.put("pre_time", getSimp(pre_time));
				} else {
					map.put("pre_time", pre_time);
				}
				String event_source = null == obj[4] ? "" : obj[4].toString();
				map.put("event_source", event_source);
				String event_source_name = null == obj[5] ? "" : obj[5].toString();
				map.put("event_source_name", event_source_name);
				String record_id = null == obj[7] ? "" : obj[7].toString();
				map.put("record_id", record_id);
				// bpm_processId
				String bpm_processId = null == obj[8] ? "" : obj[8].toString();
				map.put("bpm_processId", bpm_processId);
				String rpt_name = null == obj[9] ? "" : obj[9].toString();
				String link_man_name = null == obj[14] ? "" : obj[14].toString();
				if (rpt_name.equals("")) {
					rpt_name = link_man_name;
				}
				map.put("rpt_name", rpt_name);
				String event_content = null == obj[10] ? "" : obj[10].toString();
				map.put("event_content", event_content);
				String verify_oper_id = null == obj[11] ? "" : obj[11].toString();
				map.put("verify_oper_id", verify_oper_id);
				String user_address = null == obj[12] ? "" : obj[12].toString();
				map.put("user_address", user_address);
				String id = null == obj[13] ? "" : obj[13].toString();
				map.put("id", id);
				String verify_oper_name = null == obj[15] ? "" : obj[15].toString();
				map.put("verify_oper_name", verify_oper_name);

				String event_state = null == obj[16] ? "" : obj[16].toString();
				map.put("event_state", event_state);

				String other = null == obj[17] ? "" : obj[17].toString();
				map.put("other", other);
				list.add(map);

			}
			page.setEntities(list);
			List result = (List) page.getEntities();
			long count = page.getEntityCount();
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("total", count);
			info.put("rows", result);
			String jsonString = JSON.toJSONString(info);
			// 传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();
		}

		if (method.equals("eventSend"))// 派工处理
		{
			SimpleDateFormat sd = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			// 事件单
			String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			String id = req.getParameter("id") == null ? "" : req.getParameter("id");
			String event_from = req.getParameter("event_from") == null ? "" : req.getParameter("event_from");
			String ownerId = req.getParameter("ownerId") == null ? "" : req.getParameter("ownerId");
			String rpt_name = req.getParameter("rpt_name") == null ? "" : req.getParameter("rpt_name");
			String link_phone = req.getParameter("link_phone") == null ? "" : req.getParameter("link_phone");
			String user_address = req.getParameter("addres") == null ? "" : req.getParameter("addres");
			String event_title = req.getParameter("event_title") == null ? "" : req.getParameter("event_title");
			String pre_time = req.getParameter("pre_time") == null ? "" : req.getParameter("pre_time");
			String event_content = req.getParameter("event_content") == null ? "" : req.getParameter("event_content");
			String verify_oper_id = req.getParameter("verify_oper_id") == null ? "" : req.getParameter("verify_oper_id");
			String accept_time = req.getParameter("accept_time") == null ? "" : req.getParameter("accept_time");
			String msg = "";
			String room_id = req.getParameter("roomId") == null ? "" : req.getParameter("roomId");

			HouseworkEventEntity work = null;
			// 派工
			String send_no = commUtil.getBusinessId("SD", "D");
			String handle_content = req.getParameter("handle_content") == null ? "" : req.getParameter("handle_content");
			String oper_id = req.getParameter("comp_operator_id") == null ? "" : req.getParameter("comp_operator_id");
			String operator_username = req.getParameter("comp_operator_username") == null ? "" : req.getParameter("comp_operator_username");
			String send_time = req.getParameter("dispatch_finish_time") == null ? "" : req.getParameter("dispatch_finish_time");
			String send_state = req.getParameter("dispatch_status") == null ? "" : req.getParameter("dispatch_status");
			boolean flag = false;
			if (event_no.equals(""))// 直接派工事件
			{
				work = new HouseworkEventEntity();
				work.setEvent_no(commUtil.getBusinessId("IM", "D"));// 生成事件单
				work.setEvent_from(event_from);
				work.setLink_man(ownerId);
				event_no = work.getEvent_no();// 直接派工问题
				work.setRoom_id(room_id);
				work.setUser_address(user_address);
				if (accept_time.equals("")) {
					work.setAccept_time(new Date());
				} else {
					try {
						work.setAccept_time(sd.parse(accept_time));
						if (pre_time.equals("")) {
							work.setPre_time(null);
						} else {
							work.setPre_time(sd.parse(pre_time));
						}
					} catch (ParseException e) {
						flag = false;
					}
				}
				work.setEvent_state("0");
				work.setCall_phone(link_phone);
				work.setEvent_title(event_title);
				work.setEvent_content(event_content);
				// work.setPre_time(new Date());
				if (verify_oper_id.equals("")) {
					work.setVerify_oper_id(user.getUsername());
					work.setVerify_oper_name(user.getCname());
				} else {
					work.setVerify_oper_id(verify_oper_id);
				}
				work.setLink_man_name(rpt_name);
				/**
				 * 启动流程
				 */
				String processKey = "houseWorkProcess";
				work.setBpm_processId(Long.parseLong(startHouseWrokBpm(processKey)));

				houseworkEventService.saveHouseWorkEvent(work);
			}
			// 派工信息
			HouseWorkEventSendEntity send = new HouseWorkEventSendEntity();
			work = houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			work.setEvent_content(event_content);

			try {
				if (pre_time.equals("")) {
					work.setPre_time(null);
				} else {
					work.setPre_time(sd.parse(pre_time));
				}
			} catch (ParseException e) {
				flag = false;
				log.info("pre_time解析异常" + e.getMessage());
			}
			send.setEvent_id(work.getId());
			send.setSend_no(send_no);
			send.setHandle_content(handle_content);
			send.setOper_id(oper_id);

			send.setSend_time(new Date());
			send.setSend_state(send_state);
			send.setOper_name(operator_username);
			work.setEvent_state(HouseWorkTool.EVENTSEND);
			if (verify_oper_id.equals("")) {
				work.setVerify_oper_id(user.getUsername());
				work.setVerify_oper_name(user.getCname());
			}
			/**
			 * 历史表写入
			 */
			EventSendEntity sendH = new EventSendEntity();
			sendH.setHandle_time(send.getSend_time());
			sendH.setEvent_id(work.getEvent_no());
			sendH.setOwnHandler(send.getOper_id());
			String deptName = assignWorkService.getdept(send.getOper_id());
			String phone = assignWorkService.getHandlePhone(send.getOper_id());
			sendH.setHandler_phone(phone);
			String dept_name = null == deptName ? "" : deptName;
			if (dept_name.equals("")) {
				sendH.setHandler_dept("系统");
			} else {
				sendH.setHandler_dept(dept_name);
			}

			sendH.setStatus(HouseWorkTool.EVENTSEND);
			/**
			 * 推动流程
			 */
			Map variables = new HashMap<String, Object>();
			variables.put("flag", "2");
			String processinstanceid = work.getBpm_processId() + "";
			if (null != processinstanceid && processinstanceid.length() > 0) {
				grapService.completeHouseWorkBussniseTask(processinstanceid, oper_id, variables, HouseWorkTool.EVENTSEND);
			}
			// 写入后台
			houseworkEventService.saveHouseWorkEvent(work);
			assignWorkService.addEventSend(sendH);
			flag = houseworkEventService.saveHouseWorkSend(send);

			if (flag) {
				msg = "工单：" + event_no;
			} else {
				msg = "faild";
			}
			PrintWriter out = resp.getWriter();
			out.print(JSON.toJSONString(msg));
			out.flush();

		}
		if (method.equals("eventDel"))// 物管取消
		{
			String event_no = req.getParameter("event_no") == null ? "" : req.getParameter("event_no");
			String id = req.getParameter("id") == null ? "" : req.getParameter("id");
			HouseworkEventEntity work = houseworkEventService.queryHouseWorkEventInfoByNo(event_no);
			work.setEvent_state(HouseWorkTool.EVENTFILE);
			work.setFinish_time(new Date());
			work.setOther("待派工环节取消家政服务");

			EventSendEntity send = new EventSendEntity();
			send.setHandle_time(new Date());
			if (work.getLink_man_name() == null || work.getLink_man_name().equals("")) {
				send.setOwnHandler("客户");
			} else {
				send.setOwnHandler(work.getLink_man_name());
			}
			// String deptName=assignWorkService.getdept(work.getVerify_oper_id());
			/*
			 * String dept_name=null==deptName?"":deptName; if(dept_name.equals("")) { send.setHandler_dept("系统"); }else{ send.setHandler_dept(dept_name); }
			 */
			send.setHandler_phone(work.getCall_phone());
			send.setStatus(work.getEvent_state());
			send.setEvent_id(event_no);
			send.setDel_status("1");// 删除状态 1表示删除，其他正常

			/**
			 * 驱动流程
			 */
			Map variables = new HashMap<String, Object>();
			variables.put("flag", "1");
			String processinstanceid = work.getBpm_processId() + "";
			if (null != processinstanceid && processinstanceid.length() > 0) {
				grapService.completeHouseWorkBussniseTask(processinstanceid, user.getUsername(), variables, "1");
				/**
				 * 推到结束
				 */
				grapService.completeHouseWorkBussniseTask(processinstanceid, user.getUsername(), null, "1");
				grapService.completeHouseWorkBussniseTask(processinstanceid, user.getUsername(), null, null);
			}
			assignWorkService.addEventSend(send);
			boolean flag = houseworkEventService.saveHouseWorkEvent(work);

			String msg = "";
			if (flag) {
				msg = event_no;
			} else {
				msg = "faild";
			}
			PrintWriter out = resp.getWriter();
			out.print(JSON.toJSONString(msg));
			out.flush();
		}
		if (method.equals("deleteHouseWork")) {
			// String deleteIds=arg0.getParameter("deleteId");
			PrintWriter out = resp.getWriter();
			try {

				if (!StringUtil.isEmpty(workId)) {
					JSONArray jsonArr = JSON.parseArray(workId);
					for (int i = 0; i < jsonArr.size(); i++) {

						String houseWorkId = (String) jsonArr.get(i);
						houseworkEventService.deleteHouseWorkInfo(houseWorkId);
					}
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			} catch (Exception e) {
				String jsonString = JSON.toJSONString("删除失败！");
				log.error(jsonString);
				out.println(jsonString);
				out.flush();
			}
		}

	}

	@Override
	public String getUrl() {
		return "/houseWork/accept";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	public String getSimp(String time) {

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String time1 = sd.format(d);
		return time1;

	}

	public String startHouseWrokBpm(String key) {
		/*
		 * * 开始流程
		 */
		String processId = grapService.startBussniseTask(key);
		if (null == processId || processId.length() < 1) {
			processId = "0";

		} else {
			grapService.completeBussniseTask(processId, "", null);// 多走一步流程

		}
		return processId;
	}

}
