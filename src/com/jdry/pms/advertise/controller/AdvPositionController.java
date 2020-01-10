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
import com.jdry.pms.advertise.pojo.AdvertisePositionModel;
import com.jdry.pms.advertise.pojo.VAdvertisePositionModel;
import com.jdry.pms.advertise.service.AdvertiseService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
@Component
public class AdvPositionController implements IController{

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
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();//当前处理人
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("application/json;charset=utf-8");
		arg1.setHeader("pragma","no-cache");
		arg1.setHeader("cache-control","no-cache");
		if(method.equalsIgnoreCase("initDropAll")){
			List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("adv_");
			String jsonString = JSON.toJSONString(positions);
			
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("save")){
			String adv_pos_code= null == arg0.getParameter("adv_pos_code")?"":arg0.getParameter("adv_pos_code").toString();
			String adv_position= null == arg0.getParameter("adv_position")?"":arg0.getParameter("adv_position").toString();
			String adv_pos_size= null == arg0.getParameter("adv_pos_size")?"":arg0.getParameter("adv_pos_size").toString();
			String adv_pos_status= null == arg0.getParameter("adv_pos_status")?"":arg0.getParameter("adv_pos_status").toString();
			String adv_pos_type= null == arg0.getParameter("adv_pos_type")?"":arg0.getParameter("adv_pos_type").toString();
			String adv_memo= null == arg0.getParameter("adv_memo")?"":arg0.getParameter("adv_memo").toString();
			
			AdvertisePositionModel positionModel = null;
			
			
			if(null !=adv_pos_code && adv_pos_code.length()>0){
				positionModel = advertiseService.getPositionModelById(adv_pos_code);
			}else{
				adv_pos_code = commUtil.getBusinessId("adv");
			}
			
			if(null == positionModel){
				positionModel = new AdvertisePositionModel();
				positionModel.setAdv_pos_id(CommUtil.getGuuid());
			}
			System.out.println("adv_pos_code=="+adv_pos_code);
			positionModel.setAdv_pos_code(adv_pos_code);
			positionModel.setAdv_position(adv_position);
			positionModel.setAdv_pos_size(adv_pos_size);
			positionModel.setAdv_pos_status(adv_pos_status);
			positionModel.setAdv_pos_type(adv_pos_type);
			positionModel.setAdv_memo(adv_memo);
			positionModel.setCreate_by(user.getUsername());
			positionModel.setCreate_time(new Date());
			advertiseService.addPositionModel(positionModel);
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.print(JSON.toJSONString("succese"));
			out.flush();
		}else if(method.equals("view")){
			arg1.setContentType("application/json;charset=utf-8");
			String posCode= null == arg0.getParameter("posCode")?"":arg0.getParameter("posCode").toString();
			System.out.println("posCode==="+posCode);
			AdvertisePositionModel positionModel = null;
			positionModel = advertiseService.getPositionModelById(posCode);
			if(null == positionModel){
				positionModel = new AdvertisePositionModel();
			}
			String jsonString = JSON.toJSONString(positionModel);
			
			
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(method.equals("list")){
			List<VAdvertisePositionModel> result=advertiseService.queryAll();
			String jsonString = JSON.toJSONString(result);
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		
		}else if(method.equals("deletePosition")){
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
						advertiseService.deleteAdvPositionByCode(adv_code);
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
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/position/advPositionInfor";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
