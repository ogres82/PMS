package com.jdry.pms.advertise.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.advertise.pojo.AdevertiseModel;
import com.jdry.pms.advertise.pojo.AdvertiseAttachModel;
import com.jdry.pms.advertise.pojo.AdvertisePositionModel;
import com.jdry.pms.advertise.service.AdvertiseService;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;

@Component
public class AdvertiseController implements IController{

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
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html;charset=utf-8");
		arg0.setCharacterEncoding("utf-8");
		
		String method=null == arg0.getParameter("method")?"":arg0.getParameter("method").toString();//方法
//		String assignId=null == arg0.getParameter("assignId")?"":arg0.getParameter("assignId").toString();//业务编号
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();//当前处理人
		if(method.equalsIgnoreCase("initDropAll")){
			List<DirDirectoryDetail> positions=(List<DirDirectoryDetail>) directoryService.getDirectoryLikeCode("adv_");
			String jsonString = JSON.toJSONString(positions);
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
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
			String adv_position= null == arg0.getParameter("adv_position")?"":arg0.getParameter("adv_position").toString();
			String adv_pos_size= null == arg0.getParameter("adv_pos_size")?"":arg0.getParameter("adv_pos_size").toString();
			String adv_pos_status= null == arg0.getParameter("adv_pos_status")?"":arg0.getParameter("adv_pos_status").toString();
			String adv_pos_type= null == arg0.getParameter("adv_pos_type")?"":arg0.getParameter("adv_pos_type").toString();
			String adv_memo= null == arg0.getParameter("adv_memo")?"":arg0.getParameter("adv_memo").toString();
			
			AdvertiseAttachModel attachModel = null;
			AdvertisePositionModel positionModel = null;
			
			
			if(null !=adv_pos_code && adv_pos_code.length()>0){
				attachModel = advertiseService.getAttachModelById(adv_pos_code);
				positionModel = advertiseService.getPositionModelById(adv_pos_code);
			}else{
				adv_pos_code = commUtil.getBusinessId("adv");
			}
			if(null == attachModel){
				attachModel = new AdvertiseAttachModel();
				attachModel.setAdv_pos_id(CommUtil.getGuuid());
			}
			
			if(null == positionModel){
				positionModel = new AdvertisePositionModel();
				positionModel.setAdv_pos_id(CommUtil.getGuuid());
			}
			positionModel.setAdv_pos_code(adv_pos_code);
			positionModel.setAdv_position(adv_position);
			positionModel.setAdv_pos_size(adv_pos_size);
			positionModel.setAdv_pos_status(adv_pos_status);
			positionModel.setAdv_pos_type(adv_pos_type);
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
			advertiseService.addPositionModel(positionModel);
			
		}else if(method.equals("list")){
			//返回查询结果列表

			 int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
			  // 每页行数
			  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			  String search = null == arg0.getParameter("search")?"":arg0.getParameter("search").toString();
			  if(null != search && search.length()>0){
				  search=java.net.URLDecoder.decode(arg0.getParameter("search") , "UTF-8");
			  }
			  
			  if(search.equals("undefined"))
			    {
			    	search="";
			    }
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			  if (currentPage != 0) {// 获取页数
			   currentPage = currentPage / showCount;
			  }
			  currentPage += 1;
			Page<AdevertiseModel> page =new Page<AdevertiseModel>(showCount, currentPage);
			try {
				advertiseService.getAdvertiseAll(page, parameter, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<AdevertiseModel> result=(List<AdevertiseModel>) page.getEntities();
			String jsonString = JSON.toJSONString(result);
			JSONArray jsonArr = JSON.parseArray(jsonString); 
		    JSONObject jsonObject=new JSONObject();
		    jsonObject.put("rows", jsonArr);//JSONArray
		    jsonObject.put("total",page.getEntityCount());//总记录数
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(JSON.toJSONString(jsonObject));
			out.flush();
		
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/advertise/advertiseInformation";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
