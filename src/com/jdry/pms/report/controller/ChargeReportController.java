package com.jdry.pms.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.jdry.pms.chargeManager.service.ChargeTypeSettingService;
import com.jdry.pms.report.service.ChargeReportService;

@Repository
@Component
public class ChargeReportController implements IController {
	@Resource
	ChargeReportService service;
	@Resource
	ChargeTypeSettingService typeService;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		PrintWriter out = arg1.getWriter();
		String method = arg0.getParameter("method");
		String date2 = null == arg0.getParameter("date2") ? "" : arg0.getParameter("date2").toString();
		String date1 = null == arg0.getParameter("date1") ? "" : arg0.getParameter("date1").toString();
		String communityId = null == arg0.getParameter("communityId") ? ""
				: arg0.getParameter("communityId").toString();
		if (method.equals("year")) {
			List list = (List) service.getChargeInfoByYear();
			if (list.size() > 0) {
				List list1 = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					Map map = new HashMap();
					Object[] obj = (Object[]) list.get(i);
					if (obj[0] == null || obj[0].equals("")) {
						continue;
					}
					map.put("years", obj[0]);
					map.put("receiveAmount", obj[2]);
					map.put("paidAmount", obj[1]);
					map.put("arrearageAmount", obj[3]);
					list1.add(map);
				}

				String jsonString = JSON.toJSONString(list1);
				out.println(jsonString);
				out.flush();
			}
		}
		if (method.equals("month")) {
			String year = null == arg0.getParameter("year") ? "" : arg0.getParameter("year").toString();
			List list = (List) service.getChargeInfoByMonth(year);
			if (list.size() > 0) {
				List list1 = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					Map map = new HashMap();
					Object[] obj = (Object[]) list.get(i);
					if (obj[0] == null || obj[0].equals("")) {
						continue;
					}
					map.put("months", obj[0]);
					map.put("receiveAmount", obj[2]);
					map.put("paidAmount", obj[1]);
					map.put("arrearageAmount", obj[3]);
					list1.add(map);
				}

				String jsonString = JSON.toJSONString(list1);
				out.println(jsonString);
				out.flush();
			}
		}
		if (method.equals("area")) {
			String month = null == arg0.getParameter("month") ? "" : arg0.getParameter("month").toString();
			List list = (List) service.getChargeInfoByArea(month);
			if (list.size() > 0) {
				List list1 = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					Map map = new HashMap();
					Object[] obj = (Object[]) list.get(i);
					if (obj[0] == null || obj[0].equals("")) {
						continue;
					}
					map.put("communityName", obj[0]);
					map.put("communityId", obj[1]);
					map.put("receiveAmount", obj[3]);
					map.put("paidAmount", obj[2]);
					map.put("arrearageAmount", obj[4]);
					list1.add(map);
				}

				String jsonString = JSON.toJSONString(list1);
				out.println(jsonString);
				out.flush();
			}
		}
		if (method.equals("build")) {
			List list = (List) service.getChargeInfoByBuild(date1, communityId);
			if (list.size() > 0) {
				List list1 = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					Map map = new HashMap();
					Object[] obj = (Object[]) list.get(i);
					if (obj[0] == null || obj[0].equals("")) {
						continue;
					}
					map.put("communityName", obj[0]);
					map.put("communityId", obj[1]);
					map.put("buildId", obj[2]);
					map.put("buildName", obj[3]);
					map.put("receiveAmount", obj[5]);
					map.put("paidAmount", obj[4]);
					map.put("arrearageAmount", obj[6]);
					list1.add(map);
				}

				String jsonString = JSON.toJSONString(list1);
				out.println(jsonString);
				out.flush();
			}
		}
		if (method.equals("type")) {
			String month = null == arg0.getParameter("month") ? "" : arg0.getParameter("month").toString();
			List list = (List) service.getChargeInfoByType(month);
			List r = typeService.queryChargeTypeInfo("", "");
			List list2 = new ArrayList();
			for (int j = 0; j < r.size(); j++) {
				BigDecimal receiveAmount = new BigDecimal(0);
				BigDecimal paidAmount = new BigDecimal(0);
				BigDecimal arrearageAmount = new BigDecimal(0);
				Object[] o = (Object[]) r.get(j);
				Map map1 = new HashMap();
				map1.put("chargeTypeName", o[2]);
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[]) list.get(i);
						if (obj[1].toString().equals(o[1].toString())) {
							receiveAmount = (BigDecimal) obj[3];
							paidAmount = (BigDecimal) obj[2];
							arrearageAmount = (BigDecimal) obj[4];
						}
					}
					map1.put("receiveAmount", receiveAmount);
					map1.put("paidAmount", paidAmount);
					map1.put("arrearageAmount", arrearageAmount);
					list2.add(map1);
				}
			}
			String jsonString = JSON.toJSONString(list2);
			out.println(jsonString);
			out.flush();

		}
		if (method.equals("AuditDetail")) {
			String beginDate = null == arg0.getParameter("beginYear") ? "" : arg0.getParameter("month").toString();
			String endDate = null == arg0.getParameter("endYear") ? "" : arg0.getParameter("month").toString();
			List list = (List) service.queryAuditDetail(beginDate, endDate);
			String jsonString = JSON.toJSONString(list);
			out.println(jsonString);
			out.flush();

		}
		if (method.equals("AuditRep")) {
			String beginDate = null == arg0.getParameter("beginYear") ? "" : arg0.getParameter("beginYear").toString();
			String endDate = null == arg0.getParameter("endYear") ? "" : arg0.getParameter("endYear").toString();
			List list = (List) service.queryAuditRep(beginDate, endDate);
			String jsonString = JSON.toJSONString(list);
			out.println(jsonString);
			out.flush();

		}

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/report/chargeReport";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
