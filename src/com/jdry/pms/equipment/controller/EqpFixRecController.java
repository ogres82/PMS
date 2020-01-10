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

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.equipment.pojo.EqpFixRec;
import com.jdry.pms.equipment.pojo.VEqpFixRec;
import com.jdry.pms.equipment.service.EqpFixRecService;
@Repository
@Component
public class EqpFixRecController implements IController{
	
	@Resource
	EqpFixRecService service;
	
	@Resource
	DirectoryService directoryService;

	static Logger log = Logger.getLogger(EqpFixRecController.class);
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
		String fixRecId = arg0.getParameter("fixRecId");
		if(method.equals("list")){
			List<VEqpFixRec> result = (List<VEqpFixRec>) service.queryAllRec();
			String jsonString = JSON.toJSONString(result);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("addOrUpdate")){
			SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
			fixRecId = null == arg0.getParameter("fixRecId")?"":arg0.getParameter("fixRecId").toString();
			String eqpId = null == arg0.getParameter("eqpId")?"":arg0.getParameter("eqpId").toString();
			String eqpFixPerson = null == arg0.getParameter("eqpFixPerson")?"":arg0.getParameter("eqpFixPerson").toString();
			String eqpFixDate = null == arg0.getParameter("eqpFixDate")?"":arg0.getParameter("eqpFixDate").toString();
			String eqpFixEnddate = null == arg0.getParameter("eqpFixEnddate")?"":arg0.getParameter("eqpFixEnddate").toString();
			String eqpFixContent = null == arg0.getParameter("eqpFixContent")?"":arg0.getParameter("eqpFixContent").toString();
			String eqpFixTime = null == arg0.getParameter("eqpFixTime")?"":arg0.getParameter("eqpFixTime").toString();
			String eqpFixFee = null == arg0.getParameter("eqpFixFee")?"":arg0.getParameter("eqpFixFee").toString();
			String eqpFixDetail = null == arg0.getParameter("eqpFixDetail")?"":arg0.getParameter("eqpFixDetail").toString();
			String eqpHandlRes = null == arg0.getParameter("eqpHandlRes")?"":arg0.getParameter("eqpHandlRes").toString();
			EqpFixRec rec = new EqpFixRec();
			if(fixRecId!=null && !fixRecId.isEmpty()){
				rec.setFixRecId(fixRecId);
			}
			try {
				if(eqpFixDate!=null && !eqpFixDate.isEmpty()){
					rec.setEqpFixDate(sdf.parse(eqpFixDate));
				}
				if(eqpFixEnddate!=null && !eqpFixEnddate.isEmpty()){
					rec.setEqpFixEnddate(sdf.parse(eqpFixEnddate));
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("设备运维记录格式解析错误");
			}
			
			if(eqpFixTime!=null && !eqpFixTime.isEmpty()){
				rec.setEqpFixTime(Float.parseFloat(eqpFixTime));
			}
			if(eqpFixFee!=null && !eqpFixFee.isEmpty()){
				rec.setEqpFixFee(Float.parseFloat(eqpFixFee));
			}
			rec.setEqpFixContent(eqpFixContent);
			rec.setEqpFixDetail(eqpFixDetail);
			rec.setEqpFixPerson(eqpFixPerson);
			rec.setEqpHandlRes(eqpHandlRes);
			rec.setEqpId(eqpId);
			if (fixRecId == null || "".equals(fixRecId)){
				service.addEqpFixRec(rec);
			}else{
				service.editEqpFixRec(rec);
			}
			PrintWriter out = arg1.getWriter();
			out.print(JSON.toJSONString("success"));
			out.flush();
		}else if(method.equals("edit")){
			PrintWriter out = arg1.getWriter();
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("fixRecId", fixRecId);
			List<VEqpFixRec> result = (List<VEqpFixRec>) service.queryEqpFixRecByParam(parameter);
			VEqpFixRec e = null;
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
				
				if(!StringUtil.isEmpty(fixRecId)){
					service.delEqpFixRecById(fixRecId);
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			}catch(Exception e){
				e.printStackTrace();
				log.error("删除设备运维记录失败");
				String jsonString = JSON.toJSONString("删除失败！");
				out.println(jsonString);
				out.flush();
			}
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/equipment/equipmentFixList";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
