package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.jdry.pms.system.pojo.PrivilegeConfig;
import com.jdry.pms.system.service.PosService;
import com.jdry.pms.system.service.PrivilegeConfigService;
@Repository
@Component
public class PrivilegeConfigController implements IController{

	@Resource
	PosService service;
	@Resource
	PrivilegeConfigService configService;
	
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
		String method=arg0.getParameter("method");
		if(method.equals("initDrops")){
			PrintWriter out = arg1.getWriter();
			List<DefaultPosition> positions =  service.loadPosition();
			String b = JSON.toJSONString(positions);				
			out.println(b);
			out.flush();
			out.close();
		}
		if(method.equals("save")){
			PrintWriter out = arg1.getWriter();
			String id1 = null == arg0.getParameter("id1")?"":arg0.getParameter("id1").toString();
			String id2 = null == arg0.getParameter("id2")?"":arg0.getParameter("id2").toString();
			String id3 = null == arg0.getParameter("id3")?"":arg0.getParameter("id3").toString();
			String id4 = null == arg0.getParameter("id4")?"":arg0.getParameter("id4").toString();
			String type1 = null == arg0.getParameter("type1")?"":arg0.getParameter("type1").toString();
			String type2 = null == arg0.getParameter("type2")?"":arg0.getParameter("type2").toString();
			String type3 = null == arg0.getParameter("type3")?"":arg0.getParameter("type3").toString();
			String type4 = null == arg0.getParameter("type4")?"":arg0.getParameter("type4").toString();
			String positionId1 = null == arg0.getParameter("positionId1")?"":arg0.getParameter("positionId1").toString();
			String positionId2 = null == arg0.getParameter("positionId2")?"":arg0.getParameter("positionId2").toString();
			String positionId3 = null == arg0.getParameter("positionId3")?"":arg0.getParameter("positionId3").toString();
			String positionId4 = null == arg0.getParameter("positionId4")?"":arg0.getParameter("positionId4").toString();
			String positionName1 = null == arg0.getParameter("positionName1")?"":arg0.getParameter("positionName1").toString();
			String positionName2 = null == arg0.getParameter("positionName2")?"":arg0.getParameter("positionName2").toString();
			String positionName3 = null == arg0.getParameter("positionName3")?"":arg0.getParameter("positionName3").toString();
			String positionName4 = null == arg0.getParameter("positionName4")?"":arg0.getParameter("positionName4").toString();
			PrivilegeConfig pc1 = new PrivilegeConfig();
			PrivilegeConfig pc2 = new PrivilegeConfig();
			PrivilegeConfig pc3 = new PrivilegeConfig();
			PrivilegeConfig pc4 = new PrivilegeConfig();
			if(!id1.equals("")){
				pc1.setId(id1);
			}
			pc1.setType(type1);
			pc1.setPositionId(positionId1);
			pc1.setPositionName(positionName1);
			if(!id2.equals("")){
				pc2.setId(id2);
			}
			pc2.setType(type2);
			pc2.setPositionId(positionId2);
			pc2.setPositionName(positionName2);
			if(!id3.equals("")){
				pc3.setId(id3);
			}
			pc3.setType(type3);
			pc3.setPositionId(positionId3);
			pc3.setPositionName(positionName3);
			if(!id4.equals("")){
				pc4.setId(id4);
			}
			pc4.setType(type4);
			pc4.setPositionId(positionId4);
			pc4.setPositionName(positionName4);
			List<PrivilegeConfig> list = new ArrayList<PrivilegeConfig> ();
			list.add(pc1);
			list.add(pc2);
			list.add(pc3);
			list.add(pc4);
			configService.saveOrUpdateConfig(list);
			out.print(JSON.toJSONString("操作成功！"));
			out.flush();
			out.close();
		}
		if(method.equals("initData")){
			PrintWriter out = arg1.getWriter();
			List<PrivilegeConfig> list = configService.loadPrivilegeConfig();
			String b = JSON.toJSONString(list);				
			out.println(b);
			out.flush();
			out.close();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/system/privilege";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
