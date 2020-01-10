package com.jdry.pms.advertise.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.advertise.pojo.AdevertiseModel;
import com.jdry.pms.advertise.pojo.AdvertiseAttachModel;
import com.jdry.pms.advertise.pojo.AdvertisePositionModel;
import com.jdry.pms.advertise.service.AdvertiseService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
@Component
public class AdvAttachController implements IController{

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Resource
	DirectoryService directoryService;
	
	@Resource
	CommUtil commUtil;
	
	@Resource
	AdvertiseService advertiseService;

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String method=null == arg0.getParameter("method")?"":arg0.getParameter("method").toString();//方法
//		String assignId=null == arg0.getParameter("assignId")?"":arg0.getParameter("assignId").toString();//业务编号
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("application/json;charset=utf-8");
		arg1.setHeader("pragma","no-cache");
		arg1.setHeader("cache-control","no-cache");
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();//当前处理人
		System.out.println("method==="+method);
		if(method.equalsIgnoreCase("initDropAll")){
			List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("adv_");
			String jsonString = JSON.toJSONString(positions);
			
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("save")){
			String adv_pos_code= null == arg0.getParameter("adv_pos_code")?"":arg0.getParameter("adv_pos_code").toString();
			String adv_business= null == arg0.getParameter("adv_business")?"":arg0.getParameter("adv_business").toString();
			String adv_business_phone= null == arg0.getParameter("adv_business_phone")?"":arg0.getParameter("adv_business_phone").toString();
			String contract_code= null == arg0.getParameter("contract_code")?"":arg0.getParameter("contract_code").toString();
			String price= null == arg0.getParameter("price")?"0":arg0.getParameter("price").toString();
			String dur_time= null == arg0.getParameter("dur_time")?"0":arg0.getParameter("dur_time").toString();
			String adv_memo= null == arg0.getParameter("adv_memo")?"":arg0.getParameter("adv_memo").toString();
			String p_id= null == arg0.getParameter("p_id")?"":arg0.getParameter("p_id").toString();
			
			AdvertiseAttachModel attachModel = null;
			
			if(null !=p_id && p_id.length()>0){
				attachModel = advertiseService.getAttachModelById(p_id);
			}
			
			if(null == attachModel){
				attachModel = new AdvertiseAttachModel();
				attachModel.setAdv_pos_id(CommUtil.getGuuid());
			}
			
			attachModel.setAdv_business(adv_business);
			attachModel.setAdv_business_phone(adv_business_phone);
			attachModel.setAdv_memo(adv_memo);
			attachModel.setAdv_pos_code(adv_pos_code);
			attachModel.setContract_code(contract_code);
			attachModel.setCreate_by(user.getUsername());
			attachModel.setCreate_time(new Date());
			attachModel.setDur_time(dur_time);
			attachModel.setPrice(price);
			
			advertiseService.addAttachModel(attachModel);
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.print(JSON.toJSONString("succese"));
			out.flush();
		}else if(method.equals("view")){
			arg1.setContentType("application/json;charset=utf-8");
			String posCode= null == arg0.getParameter("posCode")?"":arg0.getParameter("posCode").toString();
			System.out.println("posCode==="+posCode);
			AdvertiseAttachModel positionModel = null;
			positionModel = advertiseService.getAttachModelById(posCode);
			if(null == positionModel){
				positionModel = new AdvertiseAttachModel();
			}
			String jsonString = JSON.toJSONString(positionModel);
			System.out.println(jsonString);
			
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("list")){
			List<AdevertiseModel> result=advertiseService.queryAllAdvAttach();
			String jsonString = JSON.toJSONString(result);
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		
		}else if(method.equals("deleteAttach")){
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			PrintWriter out = arg1.getWriter();
			String posCode= null == arg0.getParameter("posCode")?"":arg0.getParameter("posCode").toString();
			try{
				
				if(!StringUtil.isEmpty(posCode)){
					String[] jsonArr = posCode.split(",");
					for (int i=0;i<jsonArr.length;i++){
						String adv_code = jsonArr[i];
						
						//不管了，直接删除了
						advertiseService.deleteAdvAttachById(adv_code);
					}
				}
				String jsonString = JSON.toJSONString("删除成功！");
				out.println(jsonString);
				out.flush();

			}catch(Exception e){
				String jsonString = JSON.toJSONString("删除失败！");
				out.println(jsonString);
				out.flush();
			}
		}else if(method.equals("initPosCodeDrop")){
			List<AdvertisePositionModel> models = advertiseService.getAllPosition();
			String jsonString = JSON.toJSONString(models);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/attach/advAttachInfor";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
