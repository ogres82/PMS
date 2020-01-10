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
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.equipment.pojo.EqpFile;
import com.jdry.pms.equipment.service.EqpFileService;
@Repository
@Component
public class EqpFileController implements IController{
	
	@Resource
	EqpFileService service;
	
	@Resource
	DirectoryService directoryService;

	static Logger log = Logger.getLogger(EqpFileController.class);
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
		String eqpId = arg0.getParameter("eqpId");
		if(method.equals("list")){
			List<EqpFile> result = (List<EqpFile>) service.queryEqpFileAll();
			String jsonString = JSON.toJSONString(result);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("addOrUpdate")){
			SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
			eqpId = null == arg0.getParameter("eqpId")?"":arg0.getParameter("eqpId").toString();
			String eqpName = null == arg0.getParameter("eqpName")?"":arg0.getParameter("eqpName").toString();
			String eqpAddress = null == arg0.getParameter("eqpAddress")?"":arg0.getParameter("eqpAddress").toString();
			String eqpBelongArea = null == arg0.getParameter("eqpBelongArea")?"":arg0.getParameter("eqpBelongArea").toString();
			String eqpType = null == arg0.getParameter("eqpType")?"":arg0.getParameter("eqpType").toString();
			String eqpModel = null == arg0.getParameter("eqpModel")?"":arg0.getParameter("eqpModel").toString();
			String eqpStatus = null == arg0.getParameter("eqpStatus")?"":arg0.getParameter("eqpStatus").toString();
			String eqpMtnCycle = null == arg0.getParameter("eqpMtnCycle")?"":arg0.getParameter("eqpMtnCycle").toString();
			String eqpInstallDate = null == arg0.getParameter("eqpInstallDate")?"":arg0.getParameter("eqpInstallDate").toString();
			String eqpDestroyDate = null == arg0.getParameter("eqpDestroyDate")?"":arg0.getParameter("eqpDestroyDate").toString();
			String eqpLifetime = null == arg0.getParameter("eqpLifetime")?"":arg0.getParameter("eqpLifetime").toString();
			String eqpUsedMonths = null == arg0.getParameter("eqpUsedMonths")?"":arg0.getParameter("eqpUsedMonths").toString();
			String eqpFixedTimes = null == arg0.getParameter("eqpFixedTimes")?"":arg0.getParameter("eqpFixedTimes").toString();
			String eqpMtnTimes = null == arg0.getParameter("eqpMtnTimes")?"":arg0.getParameter("eqpMtnTimes").toString();
			String eqpMtnLast = null == arg0.getParameter("eqpMtnLast")?"":arg0.getParameter("eqpMtnLast").toString();
			String eqpMtnNext = null == arg0.getParameter("eqpMtnNext")?"":arg0.getParameter("eqpMtnNext").toString();
			String eqpOriginValue = null == arg0.getParameter("eqpOriginValue")?"":arg0.getParameter("eqpOriginValue").toString();
			String eqpValueRate = null == arg0.getParameter("eqpValueRate")?"":arg0.getParameter("eqpValueRate").toString();
			String eqpCurrentValue = null == arg0.getParameter("eqpCurrentValue")?"":arg0.getParameter("eqpCurrentValue").toString();
			String eqpMtnUnit = null == arg0.getParameter("eqpMtnUnit")?"":arg0.getParameter("eqpMtnUnit").toString();
			String eqpMtnPhone = null == arg0.getParameter("eqpMtnPhone")?"":arg0.getParameter("eqpMtnPhone").toString();
			String eqpMtnAddress = null == arg0.getParameter("eqpMtnAddress")?"":arg0.getParameter("eqpMtnAddress").toString();
			String eqpMtnPerson = null == arg0.getParameter("eqpMtnPerson")?"":arg0.getParameter("eqpMtnPerson").toString();
			String eqpManu = null == arg0.getParameter("eqpManu")?"":arg0.getParameter("eqpManu").toString();
			String eqpManuPhone = null == arg0.getParameter("eqpManuPhone")?"":arg0.getParameter("eqpManuPhone").toString();
			String eqpManuAddress = null == arg0.getParameter("eqpManuAddress")?"":arg0.getParameter("eqpManuAddress").toString();
			String eqpRegisterDate = null == arg0.getParameter("eqpRegisterDate")?"":arg0.getParameter("eqpRegisterDate").toString();
			String eqpRemark = null == arg0.getParameter("eqpRemark")?"":arg0.getParameter("eqpRemark").toString();
			EqpFile eqpFile = new EqpFile();
			if(eqpId!=null && !eqpId.isEmpty()){
				eqpFile.setEqpId(eqpId);
			}
			
			try {
				if(eqpInstallDate!=null && !eqpInstallDate.isEmpty()){
					eqpFile.setEqpInstallDate(sdf.parse(eqpInstallDate));
				}
				if(eqpDestroyDate!=null && !eqpDestroyDate.isEmpty()){
					eqpFile.setEqpDestroyDate(sdf.parse(eqpDestroyDate));
				}
				if(eqpRegisterDate!=null && !eqpRegisterDate.isEmpty()){
					eqpFile.setEqpRegisterDate(sdf.parse(eqpRegisterDate));
				}
				if(eqpMtnLast!=null && !eqpMtnLast.isEmpty()){
					eqpFile.setEqpMtnLast(sdf.parse(eqpMtnLast));
				}
				if(eqpMtnNext!=null && !eqpMtnNext.isEmpty()){
					eqpFile.setEqpMtnNext(sdf.parse(eqpMtnNext));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("设备档案格式解析错误");
			}
			if(eqpLifetime!=null && !eqpLifetime.isEmpty()){
				eqpFile.setEqpLifetime(Integer.parseInt(eqpLifetime));
			}
			if(eqpFixedTimes!=null && !eqpFixedTimes.isEmpty()){
				eqpFile.setEqpFixedTimes(Integer.parseInt(eqpFixedTimes));
			}
			if(eqpMtnTimes!=null && !eqpMtnTimes.isEmpty()){
				eqpFile.setEqpMtnTimes(Integer.parseInt(eqpMtnTimes));
			}
			if(eqpUsedMonths!=null && !eqpUsedMonths.isEmpty()){
				eqpFile.setEqpUsedMonths(Integer.parseInt(eqpUsedMonths));
			}
			if(eqpCurrentValue!=null && !eqpCurrentValue.isEmpty()){
				eqpFile.setEqpCurrentValue(Float.parseFloat(eqpCurrentValue));
			}
			if(eqpOriginValue!=null && !eqpOriginValue.isEmpty()){
				eqpFile.setEqpOriginValue(Float.parseFloat(eqpOriginValue));
			}
			if(eqpValueRate!=null && !eqpValueRate.isEmpty()){
				eqpFile.setEqpValueRate(Float.parseFloat(eqpValueRate));
			}
			eqpFile.setEqpManu(eqpManu);
			eqpFile.setEqpAddress(eqpAddress);
			eqpFile.setEqpBelongArea(eqpBelongArea);
			eqpFile.setEqpManu(eqpManuAddress);
			eqpFile.setEqpManuAddress(eqpManuAddress);
			eqpFile.setEqpManuPhone(eqpManuPhone);
			eqpFile.setEqpModel(eqpModel);
			eqpFile.setEqpMtnAddress(eqpMtnAddress);
			eqpFile.setEqpMtnCycle(eqpMtnCycle);
			eqpFile.setEqpMtnPerson(eqpMtnPerson);
			eqpFile.setEqpMtnPhone(eqpMtnPhone);
			eqpFile.setEqpMtnUnit(eqpMtnUnit);
			eqpFile.setEqpName(eqpName);
			eqpFile.setEqpRemark(eqpRemark);
			eqpFile.setEqpStatus(eqpStatus);
			eqpFile.setEqpType(eqpType);
			service.addEqp(eqpFile);
			PrintWriter out = arg1.getWriter();
			out.print(JSON.toJSONString("success"));
			out.flush();
		}else if(method.equals("initDropAll")){
			List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("equipment");
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
			parameter.put("eqpId", eqpId);
			List<EqpFile> result = (List<EqpFile>) service.queryEqpFileByParam(parameter);
			EqpFile e = null;
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
				
				if(!StringUtil.isEmpty(eqpId)){
					service.delEqpById(eqpId);
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			}catch(Exception e){
				e.printStackTrace();
				log.error("删除设备失败");
				String jsonString = JSON.toJSONString("删除失败！");
				out.println(jsonString);
				out.flush();
			}
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/equipment/equipmentList";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
