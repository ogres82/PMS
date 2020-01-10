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

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.service.EventSearchService;

/**
 * 家政查询
 * 
 * @author hezuping date 2016-06-11
 */
@Repository
@Component
public class evnetSearchController implements IController {

	@Resource
	EventSearchService eventSearchService;

	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@SuppressWarnings("unchecked")
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
		if (method.equals("listHouseWorkEvent")) {

			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数
			int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
			String search = java.net.URLDecoder.decode(req.getParameter("search"), "UTF-8");
			String businessFromSearch = req.getParameter("businessFromSearch");
			String CommNameSearch = java.net.URLDecoder.decode(req.getParameter("CommNameSearch"), "UTF-8");
			String dateSearch = req.getParameter("dateSearch");
			String eventStatusSearch = req.getParameter("eventStatusSearch");
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
			parameter.put("eventStatusSearch", eventStatusSearch);
			Page page = new Page(showCount, currentPage);
			List res = eventSearchService.queryHouseWorkEvent(page, parameter, null);
			List list = new ArrayList();
			for (int i = 0, len = res.size(); i < len; i++) {
				Map map = new HashMap<String, String>();
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

				String bpm_processId = null == obj[8] ? "" : obj[8].toString();
				map.put("bpm_processId", bpm_processId);
				String rpt_name = null == obj[9] ? "" : obj[9].toString();
				map.put("rpt_name", rpt_name);
				String event_content = null == obj[10] ? "" : obj[10].toString();
				map.put("event_content", event_content);
				String verify_oper_id = null == obj[11] ? "" : obj[11].toString();
				map.put("verify_oper_id", verify_oper_id);
				String user_address = null == obj[12] ? "" : obj[12].toString();
				map.put("user_address", user_address);
				String id = null == obj[13] ? "" : obj[13].toString();
				map.put("id", id);
				// 派工
				String oper_id = null == obj[14] ? "" : obj[14].toString();
				map.put("oper_id", oper_id);// 处理人ID

				String send_time = null == obj[15] ? "" : obj[15].toString();
				map.put("send_time", send_time);

				String send_state = null == obj[16] ? "" : obj[16].toString();
				map.put("send_state", send_state);

				String event_state = null == obj[17] ? "" : obj[17].toString();
				map.put("event_state", event_state);
				String send_no = null == obj[18] ? "" : obj[18].toString();
				if (!"".equals(send_no)) {
					map.put("send_no", send_no);
				} else {
					map.put("send_no", "");
				}

				String oper_name = null == obj[19] ? "" : obj[19].toString();
				map.put("oper_name", oper_name);
				String send_id = null == obj[20] ? "" : obj[20].toString();
				map.put("send_id", send_id);

				String handle_content = null == obj[21] ? "" : obj[21].toString();
				map.put("handle_content", handle_content);
				String arrv_time = null == obj[22] ? "" : obj[22].toString();
				map.put("arrv_time", arrv_time);

				String other = null == obj[23] ? "" : obj[23].toString();
				if (other.equals("待派工环节取消家政服务") || other.equals("派工环节业主取消工单") || other.equals("业主现场取消工单")) {
					map.put("cancelFlag", "取消");// 1为取消

				} else if (other.equals("物管拒单")) {
					map.put("cancelFlag", "拒单");

				} else {
					map.put("cancelFlag", "正常");
				}

				String houseKeepingPay = null == obj[24] ? "" : obj[24].toString();
				map.put("houseKeepingPay", houseKeepingPay);

				String visit_content = null == obj[25] ? "" : obj[25].toString();

				map.put("visit_content", visit_content);

				String visit_evaluate = null == obj[26] ? "" : obj[26].toString();
				if (visit_evaluate.equals("非常满意")) {
					map.put("visit_evaluate", "0");
				} else if (visit_evaluate.equals("满意")) {
					map.put("visit_evaluate", "1");
				} else if (visit_evaluate.equals("不满意")) {
					map.put("visit_evaluate", "2");
				}

				String visit_from = null == obj[27] ? "" : obj[27].toString();
				map.put("visit_from", visit_from);

				String visit_levContent = null == obj[30] ? "" : obj[30].toString();
				map.put("visit_levContent", visit_levContent);

				String verify_oper_name = null == obj[31] ? "" : obj[31].toString();
				map.put("verify_oper_name", verify_oper_name);

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

	}

	@Override
	public String getUrl() {
		return "/houseWork/evnetSearch";
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

}
