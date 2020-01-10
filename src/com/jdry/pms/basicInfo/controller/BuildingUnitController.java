package com.jdry.pms.basicInfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.service.BuildUnitService;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.lzmh.service.LzmhService;

@Repository
@Component
public class BuildingUnitController implements IController {
	@Resource
	BuildUnitService service;
	@Resource
	HousePropertyService hpService;
	@Resource
	LzmhService lzmhService;

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
		String unitId = arg0.getParameter("unitId") == null ? "" : arg0.getParameter("unitId");
		String unitName = arg0.getParameter("unitName") == null ? "" : arg0.getParameter("unitName");
		String storiedBuildId = arg0.getParameter("storiedBuildId") == null ? "" : arg0.getParameter("storiedBuildId");
		String communityId = arg0.getParameter("communityId") == null ? "" : arg0.getParameter("communityId");

		try {

			if (method.equalsIgnoreCase("list")) {

				List<BuildUnit> lists = service.queryBuildUnitInfo(this.makeParams(unitId, unitName, storiedBuildId, communityId));
				jsonString = JSON.toJSONString(lists, SerializerFeature.WriteMapNullValue);
			}
			if (method.equalsIgnoreCase("save")) {
				String buildUnitInfo = arg0.getParameter("buildUnitInfo");
				BuildUnit bu = JSON.parseObject(buildUnitInfo, BuildUnit.class);
				String id = bu.getUnitId();
				if(id==null||"".equals(id)) {
					service.saveEntity(bu);
					jsonString = JSON.toJSONString("succese");
					lzmhService.addUnit(bu);
				}else {
					bu.setUpdateTime(new Date());
					bu.setIsDel("0");
					service.saveOrUpdateEntity(bu);
					jsonString = JSON.toJSONString("succese");
					String str= lzmhService.modUnit(bu);
					System.out.print(str);
				}
			}
			if (method.equalsIgnoreCase("del")) {
				String ids[] = unitId.split(",");
				for (int i = 0, len = ids.length-1; i <= len; i++) {
					List<HouseProperty> hpLists = (List<HouseProperty>) hpService.queryHousePropertyByParam(this.makeParams(ids[i], "", "", ""));
					if (hpLists.size() > 0) {
						jsonString = JSON.toJSONString("该单元下还存在房间，无法删除！");
					} else {
						BuildUnit bu = service.findById(ids[i]);
						bu.setIsDel("1");
						bu.setUpdateTime(new Date());
						service.saveOrUpdateEntity(bu);
						jsonString = JSON.toJSONString("succese");
						lzmhService.delUnit(bu.getLzId());
					}
				}

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
		return "/buildingUnitInfo";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

	// 整理查询条件
	public Map<String, Object> makeParams(String unitId, String unitName, String storiedBuildId, String communityId) {
		Map<String, Object> params = new HashMap<String, Object>();
			params.put("unitId", unitId);
			params.put("unitName", unitName);
			params.put("storiedBuildId", storiedBuildId);
			params.put("communityId", communityId);
		return params;
	}

}
