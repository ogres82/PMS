package com.jdry.pms.equipment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.equipment.pojo.EqpMtnRec;
import com.jdry.pms.equipment.pojo.VEqpMtnRec;
import com.jdry.pms.equipment.service.EqpMtnRecService;
@Repository
@Component
public class EqpMtnRecController implements IController{
	
	@Resource
	EqpMtnRecService service;
	
	@Resource
	DirectoryService directoryService;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String mtnRecId = arg0.getParameter("mtnRecId");
		if(method.equals("list")){
			List<VEqpMtnRec> result = (List<VEqpMtnRec>) service.queryAllRec();
			String jsonString = JSON.toJSONString(result);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("addOrUpdate")){
			SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
			mtnRecId = null == arg0.getParameter("mtnRecId")?"":arg0.getParameter("mtnRecId").toString();
			String eqpId = null == arg0.getParameter("eqpId")?"":arg0.getParameter("eqpId").toString();
			String eqpMtnPerson = null == arg0.getParameter("eqpMtnPerson")?"":arg0.getParameter("eqpMtnPerson").toString();
			String eqpMtnDate = null == arg0.getParameter("eqpMtnDate")?"":arg0.getParameter("eqpMtnDate").toString();
			String eqpMtnEnddate = null == arg0.getParameter("eqpMtnEnddate")?"":arg0.getParameter("eqpMtnEnddate").toString();
			String eqpMtnType = null == arg0.getParameter("eqpMtnType")?"":arg0.getParameter("eqpMtnType").toString();
			String eqpMtnContent = null == arg0.getParameter("eqpMtnContent")?"":arg0.getParameter("eqpMtnContent").toString();
			String eqpMtnTime = null == arg0.getParameter("eqpMtnTime")?"":arg0.getParameter("eqpMtnTime").toString();
			String eqpMtnFee = null == arg0.getParameter("eqpMtnFee")?"":arg0.getParameter("eqpMtnFee").toString();
			String eqpMtnDetail = null == arg0.getParameter("eqpMtnDetail")?"":arg0.getParameter("eqpMtnDetail").toString();
			EqpMtnRec rec = new EqpMtnRec();
			if(mtnRecId!=null && !mtnRecId.isEmpty()){
				rec.setMtnRecId(mtnRecId);
			}
			try {
				if(eqpMtnDate!=null && !eqpMtnDate.isEmpty()){
					rec.setEqpMtnDate(sdf.parse(eqpMtnDate));
				}
				if(eqpMtnEnddate!=null && !eqpMtnEnddate.isEmpty()){
					rec.setEqpMtnEnddate(sdf.parse(eqpMtnEnddate));
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(eqpMtnTime!=null && !eqpMtnTime.isEmpty()){
				rec.setEqpMtnTime(Float.parseFloat(eqpMtnTime));
			}
			if(eqpMtnFee!=null && !eqpMtnFee.isEmpty()){
				rec.setEqpMtnFee(Float.parseFloat(eqpMtnFee));
			}
			rec.setEqpMtnContent(eqpMtnContent);
			rec.setEqpMtnDetail(eqpMtnDetail);
			rec.setEqpMtnPerson(eqpMtnPerson);
			rec.setEqpMtnType(eqpMtnType);
			rec.setEqpId(eqpId);
			if (mtnRecId == null || "".equals(mtnRecId)){
				service.addEqpMtnRec(rec);
			}else{
				service.editEqpMtnRec(rec);
			}
			PrintWriter out = arg1.getWriter();
			out.print(JSON.toJSONString("success"));
			out.flush();
		}else if(method.equals("initDropAll")){
			List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("equipment_mtn");
			String jsonString = JSON.toJSONString(positions);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.print(jsonString);
			out.flush();
		}else if(method.equals("edit")){
			PrintWriter out = arg1.getWriter();
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("mtnRecId", mtnRecId);
			List<VEqpMtnRec> result = (List<VEqpMtnRec>) service.queryEqpMtnRecByParam(parameter);
			VEqpMtnRec e = null;
			if(result.size()>0){
				e =result.get(0);
				String jsonString = JSON.toJSONString(e);
				out.print(jsonString);
			}else{
				out.print("未找到该用户");
			}
			out.flush();
		}else if(method.equals("delete")){
			PrintWriter out = arg1.getWriter();
			try{
				
				if(!StringUtil.isEmpty(mtnRecId)){
					service.delEqpMtnRecById(mtnRecId);
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			}catch(Exception e){
				e.printStackTrace();
				String jsonString = JSON.toJSONString("删除失败！");
				out.println(jsonString);
				out.flush();
			}
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/equipment/equipmentRecList";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
