package com.jdry.pms.assignWork.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.pojo.VWorkMainEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.AssingWorkSearchService;

/**
 * 描述：报障报修和咨询建议综合查询
 * 
 * @author hezuping 2016年7月13日14:31:10
 *
 */
@Repository
@Component
public class AssingWorkSearchController implements IController {

	@Resource
	AssingWorkSearchService assingWorkSearchService;
	@Resource
	AssignWorkService assignWorkService;
	static Logger log = Logger.getLogger(AssingWorkSearchController.class);

	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String method = null == req.getParameter("method") ? "" : req.getParameter("method").toString();// 方法
		String assignId = null == req.getParameter("assignId") ? "" : req.getParameter("assignId").toString();// 业务编号
		if (method.equals("assignWorkSearch")) {
			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数
			int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
			String search = java.net.URLDecoder.decode(req.getParameter("search"), "UTF-8");
			String businessFromSearch = req.getParameter("businessFromSearch");
			String eventTypeSearch = req.getParameter("eventTypeSearch");
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
			parameter.put("eventTypeSearch", eventTypeSearch);
			parameter.put("CommNameSearch", CommNameSearch);
			parameter.put("dateSearch", dateSearch);
			parameter.put("eventStatusSearch", eventStatusSearch);
			Page<VWorkMainEntity> page = new Page<VWorkMainEntity>(showCount, currentPage);
			try {

				assingWorkSearchService.assignWorkSearch(page, parameter, null, null);
			} catch (Exception e) {
				log.error("数据异常" + e.getMessage());
			}
			List<VWorkMainEntity> result = (List<VWorkMainEntity>) page.getEntities();
			String jsonString = JSON.toJSONString(result);
			JSONArray jsonArr = JSON.parseArray(jsonString);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("rows", jsonArr);// JSONArray
			jsonObject.put("total", page.getEntityCount());// 总记录数
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			resp.setHeader("pragma", "no-cache");
			resp.setHeader("cache-control", "no-cache");
			// 传输JSON
			PrintWriter out = resp.getWriter();
			out.println(JSON.toJSONString(jsonObject));
			out.flush();
		}
		if (method.equals("viewAssignSearch")) {
			resp.setContentType("application/json;charset=utf-8");
			WorkMainEntity assignWork = new WorkMainEntity();
			assignWork = assignWorkService.getWorkMainById(assignId);
			String jsonString = JSON.toJSONString(assignWork, SerializerFeature.WriteMapNullValue);

			// 派工单明细
			WorkMainDispatchEntity wormDispatch = new WorkMainDispatchEntity();
			wormDispatch = assignWorkService.getWorkDispatchById(assignId);
			if (null == wormDispatch) {
				wormDispatch = new WorkMainDispatchEntity();
			}

			// 投诉明细
			WorkComplaintEntity complaintEntity = null;
			complaintEntity = assignWorkService.getWorkComplById(assignId);
			if (null == complaintEntity) {
				complaintEntity = new WorkComplaintEntity();
			}

			String dispatch_evaluate = null == wormDispatch.getDispatch_evaluate() ? "" : wormDispatch.getDispatch_evaluate();
			String dispatch_visit_record = null == wormDispatch.getDispatch_visit_record() ? "" : wormDispatch.getDispatch_visit_record();
			String mtn_emergency = null == wormDispatch.getMtn_emergency() ? "" : wormDispatch.getMtn_emergency();
			String fee = null == wormDispatch.getDispatch_expense() ? "0.0" : wormDispatch.getDispatch_expense();
			String dispatch_status = null == wormDispatch.getDispatch_status() ? "" : wormDispatch.getDispatch_status();
			String mtn_detail = null == wormDispatch.getMtn_detail() ? "" : wormDispatch.getMtn_detail();
			String detail = mtn_detail.replace("\n", "");
			String comp_emergency = null == complaintEntity.getComp_emergency() ? "" : complaintEntity.getComp_emergency();
			String comp_status = null == complaintEntity.getComp_status() ? "" : complaintEntity.getComp_status();
			String comp_detail = null == complaintEntity.getComp_detail() ? "" : complaintEntity.getComp_detail();
			comp_detail = comp_detail.replace("\n", "");
			String name = null == wormDispatch.getDispatch_handle_name() ? "" : wormDispatch.getDispatch_handle_name();
			String dispatch_visit_lev = null == wormDispatch.getDispatch_visit_lev() ? "" : wormDispatch.getDispatch_visit_lev();
			String comp_visit_record = null == complaintEntity.getComp_visit_record() ? "" : complaintEntity.getComp_visit_record();
			String comp_reply = null == complaintEntity.getComp_reply() ? "" : complaintEntity.getComp_reply();
			String mtn_type = null == wormDispatch.getMtn_type() ? "" : wormDispatch.getMtn_type();
			String comp_result = null == complaintEntity.getComp_result() ? "" : complaintEntity.getComp_result();
			String comp_visit_recording = null == complaintEntity.getComp_visit_recording() ? "" : complaintEntity.getComp_visit_recording();
			String comp_visit_lev = null == complaintEntity.getComp_visit_lev() ? "" : complaintEntity.getComp_visit_lev();
			String comp_chuliren = null == complaintEntity.getComp_operator() ? "" : complaintEntity.getComp_operator();
			String comp_createby = null == complaintEntity.getComp_createby() ? "" : complaintEntity.getComp_createby();
			String finishImgUrl = null == wormDispatch.getFinishImgUrl() ? "" : wormDispatch.getFinishImgUrl();
			String rejectReason = null == wormDispatch.getRejectReason() ? "" : wormDispatch.getRejectReason();
			jsonString = jsonString.substring(0, jsonString.length() - 1) + ",\"mtn_emergency\":\"" + mtn_emergency + "\",\"dispatch_status\":\"" + dispatch_status + "\",\"mtn_detail\":\"" + detail + "\",\"comp_emergency\":\"" + comp_emergency + "\",\"comp_status\":\"" + comp_status + "\",\"comp_detail\":\"" + comp_detail + "\",\"comp_reply\":\"" + comp_reply + "\",\"comp_createby\":\"" + comp_createby + "\",\"dispatch_arrive_time\":\"" + wormDispatch.getDispatch_arrive_time() + "\",\"dispatch_time\":\"" + wormDispatch.getDispatch_time() + "\",\"mtn_type\":\"" + mtn_type + "\",\"mtn_priority\":\"" + wormDispatch.getMtn_priority() + "\",\"dispatch_handle_id\":\"" + name + "\",\"dispatch_expense\":\"" + fee + "\",\"dispatch_expense\":\"" + fee + "\",\"dispatch_visit_record\":\""
					+ dispatch_visit_record + "\",\"dispatch_evaluate\":\"" + dispatch_evaluate + "\",\"dispatch_visit_lev\":\"" + dispatch_visit_lev + "\",\"dispatch_visit_recording\":\"" + wormDispatch.getDispatch_visit_recording() + "\",\"comp_visit_lev\":\"" + comp_visit_lev + "\",\"comp_visit_record\":\"" + comp_visit_record + "\",\"comp_visit_recording\":\"" + comp_visit_recording + "\",\"comp_result\":\"" + comp_result + "\",\"comp_chuliren\":\"" + comp_chuliren + "\",\"finishImgUrl\":\"" + finishImgUrl + "\",\"rejectReason\":\"" + rejectReason + "\",\"dispatch_finish_time\":\"" + wormDispatch.getDispatch_finish_time() + "\",\"comp_finish_time\":\"" + complaintEntity.getComp_finish_time() + "\"}";
			// 传输JSON
			PrintWriter out = resp.getWriter();
			out.println(jsonString);
			out.flush();

		}

	}

	@Override
	public String getUrl() {
		return "/assigenwork/assignWorkSearch";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
