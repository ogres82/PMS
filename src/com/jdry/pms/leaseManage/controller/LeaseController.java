package com.jdry.pms.leaseManage.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.leaseManage.pojo.LeaseInfo;
import com.jdry.pms.leaseManage.service.LeaseService;

@Repository
public class LeaseController implements IController {
	
	@Resource
	LeaseService service;

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
		String method = arg0.getParameter("method");
		String jsonString = "";
		PrintWriter out = arg1.getWriter();
		String state = arg0.getParameter("state") == null ? "" : arg0.getParameter("state");

		try {
			if (method.equalsIgnoreCase("leaseInfo")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				List<Map> list = service.getLeaseInfoAll(parameter);
				jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			}
			if (method.equalsIgnoreCase("getShopInfo")) {
				String keyword = arg0.getParameter("keyword") == null ? "" : arg0.getParameter("keyword");	
				List<Map> list = service.queryShopInfo(keyword);
				jsonString = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
			}
			if (method.equalsIgnoreCase("delLeaseInfo")) {
				String leaseId = arg0.getParameter("leaseId") == null ? "" : arg0.getParameter("leaseId");		
				service.delLeaseInfo(leaseId);
				jsonString = JSON.toJSONString("删除成功！");
			}
			if (method.equalsIgnoreCase("addLeaseInfo")) {				
				String leaseManageInfo = arg0.getParameter("leaseManageInfo") == null ? "" : arg0.getParameter("leaseManageInfo");	
				String operate = arg0.getParameter("operate") == null ? "" : arg0.getParameter("operate");	
				LeaseInfo li= JSON.parseObject(leaseManageInfo,LeaseInfo.class);				
				service.saveLeaseInfo(li,operate);
				jsonString = JSON.toJSONString("保持成功！");
			}
			/*if (method.equalsIgnoreCase("kpiSave")) {
				String kpiInfo = arg0.getParameter("kpiInfo");
			
				AssessmentKpi ak = JSON.parseObject(kpiInfo, AssessmentKpi.class);
				List<AssessmentRate> arList = ak.getKpiRate();
				if (arList.size() > 0) {
					for (int i = 0, len = arList.size(); i < len; i++) {
						service.saveOrUpdateEntity(arList.get(i));
					}
				}
				service.saveOrUpdateEntity(ak);
				jsonString = JSON.toJSONString("succese");
			}
			if (method.equalsIgnoreCase("rateDel")) {
				String rateId = arg0.getParameter("rateId");
				String rateIdArr[] = rateId.split(",");
				AssessmentRate ar = null;
				for (int i = 0, len = rateIdArr.length; i < len; i++) {
					ar = service.findById(Integer.parseInt( rateIdArr[i]));
					if (ar != null) {
						service.deleteEntity(ar);
					}
				}
				
			}*/

		
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
		return "/leaseManage";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
