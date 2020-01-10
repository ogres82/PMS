package com.jdry.pms.kpi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.kpi.pojo.AssessmentKpi;
import com.jdry.pms.kpi.pojo.AssessmentRate;
import com.jdry.pms.kpi.service.AssessmentKpiService;
import com.jdry.pms.kpi.service.AssessmentRateSerivce;
import com.jdry.pms.kpi.service.AssessmentRecService;

@Repository
public class AssessmentController implements IController {

	@Resource
	AssessmentKpiService akService;

	@Resource
	AssessmentRateSerivce arService;

	@Resource
	AssessmentRecService arcService;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		String jsonString = "";
		PrintWriter out = arg1.getWriter();
		String kpiId = arg0.getParameter("kpiId") == null ? "" : arg0.getParameter("kpiId");

		try {
			if (method.equalsIgnoreCase("kpiList")) {
				List<AssessmentKpi> list = akService.findEntityList(AssessmentKpi.class);
				jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			}
			if (method.equalsIgnoreCase("rateList")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("kpiId", kpiId);
				List<AssessmentRate> list = arService.findEntityByHQL("from AssessmentRate WHERE kpiId =:kpiId", params);
				jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			}
			if (method.equalsIgnoreCase("kpiSave")) {
				String kpiInfo = arg0.getParameter("kpiInfo");
			
				AssessmentKpi ak = JSON.parseObject(kpiInfo, AssessmentKpi.class);
				List<AssessmentRate> arList = ak.getKpiRate();
				if (arList.size() > 0) {
					for (int i = 0, len = arList.size(); i < len; i++) {
						arService.saveOrUpdateEntity(arList.get(i));
					}
				}
				akService.saveOrUpdateEntity(ak);
				jsonString = JSON.toJSONString("succese");
			}
			if (method.equalsIgnoreCase("rateDel")) {
				String rateId = arg0.getParameter("rateId");
				String rateIdArr[] = rateId.split(",");
				AssessmentRate ar = null;
				for (int i = 0, len = rateIdArr.length; i < len; i++) {
					ar = arService.findById(Integer.parseInt( rateIdArr[i]));
					if (ar != null) {
						arService.deleteEntity(ar);
					}
				}
				jsonString = JSON.toJSONString("删除成功！");
			}

			if (method.equalsIgnoreCase("kpiDel")) {

				String kpiIdArr[] = kpiId.split(",");
				String kpiLv = arg0.getParameter("kpiLv") == null ? "" : arg0.getParameter("kpiLv");
				AssessmentKpi ak = null;
				for (int i = 0, len = kpiIdArr.length; i < len; i++) {
					ak = akService.findById(kpiIdArr[i]);

					if ("0".equals(kpiLv)) {
						List<AssessmentKpi> listsLv1 = akService.findEntityByHQL("from AssessmentKpi WHERE kpiParentId ='" + kpiIdArr[i] + "'", null);
						if (listsLv1.size() > 0) {
							for (int j = 0; j < listsLv1.size(); j++) {
								List<AssessmentKpi> listsLv2 = akService.findEntityByHQL("from AssessmentKpi WHERE kpiParentId ='" + listsLv1.get(j).getKpiId() + "'", null);
								if (listsLv2.size() > 0) {
									for (int k = 0; k < listsLv2.size(); k++) {
										akService.deleteEntity(listsLv2.get(k));
									}
									akService.deleteEntity(listsLv1.get(j));
								}
							}
						}

					}
					if ("1".equals(kpiLv)) {
						List<AssessmentKpi> listsLv2 = akService.findEntityByHQL("from AssessmentKpi WHERE kpiParentId ='" + kpiIdArr[i] + "'", null);
						if (listsLv2.size() > 0) {
							for (int k = 0; k < listsLv2.size(); k++) {
								akService.deleteEntity(listsLv2.get(k));
							}
						}
					}
					akService.deleteEntity(ak);
				}
				jsonString = JSON.toJSONString("删除成功！");
			}
			if (method.equalsIgnoreCase("kpiRecList")) {
				Map<String, Object> parameter = new HashMap<String, Object>(4);

				String empDeptId = null == arg0.getParameter("empDeptId") ? "" : arg0.getParameter("empDeptId").toString();
				String insertDate = null == arg0.getParameter("insertDate") ? "" : arg0.getParameter("insertDate").toString();
				String operId = null == arg0.getParameter("operId") ? "" : arg0.getParameter("operId").toString();
				String recId = null == arg0.getParameter("recId") ? "" : arg0.getParameter("recId").toString();
				String kpiType = null == arg0.getParameter("kpiType") ? "" : arg0.getParameter("kpiType").toString();
				parameter.put("empDeptId", empDeptId);
				parameter.put("insertDate", insertDate);
				parameter.put("operId", operId);
				parameter.put("recId", recId);
				parameter.put("kpiType", kpiType);
				

				@SuppressWarnings("rawtypes")
				List<Map> list = arcService.queryAssessmentRecAll(parameter);

				jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			}
			if (method.equalsIgnoreCase("recMonthly")) {
				Map<String, Object> parameter = new HashMap<String, Object>(2);

				String empDeptId = null == arg0.getParameter("empDeptId") ? "" : arg0.getParameter("empDeptId").toString();
				String months = null == arg0.getParameter("months") ? "" : arg0.getParameter("months").toString();
				parameter.put("empDeptId", empDeptId);
				parameter.put("months", months);
	
				@SuppressWarnings("rawtypes")
				List<Map> list = arcService.queryAssessmentRecMonthly(parameter);

				jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			}

		} catch (Exception e) {
			// TODO: handle exception
			jsonString = JSON.toJSONString("操作失败！");
			e.printStackTrace();
		} finally {
			out.println(jsonString);
			out.flush();
		}

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/assessment/kpi";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
