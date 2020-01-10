package com.jdry.pms.chargeManager.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRelaViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRoomRelaEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeSettingViewEntity;
import com.jdry.pms.chargeManager.service.ChargeTypeRoomRelaService;
import com.jdry.pms.chargeManager.service.ChargeTypeSettingService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
@Repository
@Component
public class ChargeTypeController implements IController{
	@Resource
	private ChargeTypeSettingService service;
	@Resource
	private ChargeTypeRoomRelaService serviceRela;
	
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("UTF-8");
		arg1.setCharacterEncoding("UTF-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		try {			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("typeList")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);

			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap();
			    parameter.put("search", search);
			    Page<ChargeTypeSettingViewEntity> page =new Page<ChargeTypeSettingViewEntity>(showCount, currentPage);
			    service.queryAll(page, parameter);
			    List<ChargeTypeSettingViewEntity> result=(List<ChargeTypeSettingViewEntity>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
				
				System.out.print(b);
				//arg0.setAttribute("chargeInfoList", getList());
			}	
			
			//新增或更新
			if(method.equalsIgnoreCase("saveOrUpdate")){
				String charge_type_id=arg0.getParameter("charge_type_id");
				String charge_type_no=arg0.getParameter("charge_type_no");
				String charge_type_name=arg0.getParameter("charge_type_name");
				String charge_mode=arg0.getParameter("charge_mode");
				String charge_price=arg0.getParameter("charge_price");
				String charge_way=arg0.getParameter("charge_way");
				String charge_type=arg0.getParameter("charge_type");
				String charge_cycle_count=arg0.getParameter("charge_cycle_count");
				String charge_cycle_unit=arg0.getParameter("charge_cycle_unit");
				String type_flag=arg0.getParameter("type_flag");
				
				ChargeTypeSettingEntity type = new ChargeTypeSettingEntity();
				type.setCharge_type_id(charge_type_id);
				type.setCharge_type_no(charge_type_no);
				type.setCharge_type_name(charge_type_name);
				if(charge_cycle_count != null && !charge_cycle_count.isEmpty()){
					type.setCharge_cycle_count(Integer.valueOf(charge_cycle_count));
				}
				
				type.setCharge_cycle_unit(charge_cycle_unit);
				type.setCharge_mode(charge_mode);
				type.setCharge_price(new BigDecimal(charge_price));
				type.setCharge_type(charge_type);
				type.setCharge_way(charge_way);
				type.setType_flag(type_flag);
				
				service.saveAll(type);
			}
			
			//删除
			if(method.equalsIgnoreCase("delete")){
				String delSelects = arg0.getParameter("delSelects");
				List<ChargeTypeSettingViewEntity> chargeTypeInfos = JSON.parseArray(delSelects, ChargeTypeSettingViewEntity.class);
				List<ChargeTypeSettingEntity> typeList = new ArrayList<ChargeTypeSettingEntity>();
				for(ChargeTypeSettingViewEntity type:chargeTypeInfos){
					ChargeTypeSettingEntity t = new ChargeTypeSettingEntity();
					t.setCharge_type_id(type.getCharge_type_id());
					t.setCharge_type_no(type.getCharge_type_no());
					
					typeList.add(t);
				}
				
				service.delete(typeList);
			}
			
			//以下是业主的收费项关联相关的处理
			//数据初始化
			if(method.equalsIgnoreCase("typeRelaList")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);

			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap();
			    parameter.put("search", search);
			    Page<ChargeTypeRelaViewEntity> page =new Page<ChargeTypeRelaViewEntity>(showCount, currentPage);
			    serviceRela.queryAllFromView(page, parameter);
			    List<ChargeTypeRelaViewEntity> result=(List<ChargeTypeRelaViewEntity>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			
			//新增或更新
			if(method.equalsIgnoreCase("saveOrUpdateRela")){
				String charge_type_no=arg0.getParameter("charge_type_no");
				String charge_type_id=arg0.getParameter("charge_type_id");
				String room_no=arg0.getParameter("room_no");
				String room_id=arg0.getParameter("room_id");
				String owner_id=arg0.getParameter("owner_id");
				String type_flag=arg0.getParameter("type_flag");
				
				ChargeTypeRoomRelaEntity rela = new ChargeTypeRoomRelaEntity();
				rela.setCharge_type_id(charge_type_id);
				rela.setCharge_type_no(charge_type_no);
				rela.setOwner_id(owner_id);
				rela.setRoom_id(room_id);
				rela.setRoom_no(room_no);
				rela.setType_flag(type_flag);
				
				serviceRela.saveAll(rela);
			}
			
			//删除
			if(method.equalsIgnoreCase("deleteRela")){
				String delSelects = arg0.getParameter("delSelects");
				List<ChargeTypeRelaViewEntity> relaInfos = JSON.parseArray(delSelects, ChargeTypeRelaViewEntity.class);
				List<ChargeTypeRoomRelaEntity> relaList = new ArrayList<ChargeTypeRoomRelaEntity>();
				
				for(ChargeTypeRelaViewEntity rela:relaInfos){
					ChargeTypeRoomRelaEntity r = new ChargeTypeRoomRelaEntity();
					r.setCharge_type_id(rela.getCharge_type_id());
					r.setRoom_id(rela.getRoom_id());
					
					relaList.add(r);
				}
								
				serviceRela.delete(relaList);
			}
			
			//批量新增
			if(method.equalsIgnoreCase("saveOrUpdateRelaBatch")){
				String batch_charge_type_id=arg0.getParameter("batch_charge_type_id");
				String batch_charge_type_no=arg0.getParameter("batch_charge_type_no");
				String batch_belong_unit_id=arg0.getParameter("batch_belong_unit_id");
				String batch_storied_build_id=arg0.getParameter("batch_storied_build_id");
				String batch_type_flag=arg0.getParameter("batch_type_flag");
				
				Map<String, Object> parameter = new HashMap();
			    parameter.put("charge_type_id", batch_charge_type_id);
			    parameter.put("charge_type_no", batch_charge_type_no);
			    parameter.put("belong_unit_id", batch_belong_unit_id);
			    parameter.put("storied_build_id", batch_storied_build_id);
			    parameter.put("type_flag", batch_type_flag);
			    
			    serviceRela.addBatch(parameter);
			}
			
			
			//下拉框：收费项
			if(method.equalsIgnoreCase("dropdownChargeType")){
				String keyword = arg0.getParameter("keyword") == null ? "" :java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				String type = arg0.getParameter("type");
				List list = service.queryChargeTypeInfo(keyword,type);
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSON(list));
			}
			
			//下拉框：房间及业主
			if(method.equalsIgnoreCase("dropdownHourseOwner")){
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				List list = service.queryHourseOwnerInfo(keyword);
				PrintWriter out = arg1.getWriter();
				out.print("{\"result\": "+JSON.toJSON(list)+"}");
			}
			
			//下拉框：管理处
			if(method.equalsIgnoreCase("dropdownBelongUnit")){
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				List list = service.queryBelongUnitInfo(keyword);
				PrintWriter out = arg1.getWriter();
				out.print("{\"result\": "+JSON.toJSON(list)+"}");
			}
			
			//下拉框：楼宇
			if(method.equalsIgnoreCase("dropdownStoriedBuild")){
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				List list = service.queryStoriedBuildInfo(keyword);
				PrintWriter out = arg1.getWriter();
				out.print("{\"result\": "+JSON.toJSON(list)+"}");
			}
			
			//表单费用类型的唯一性验证
			if(method.equalsIgnoreCase("ajaxValidateTypeNo")){
				String fieldId = arg0.getParameter("fieldId");
				fieldId = CharacterUtil.getUTF_8String(fieldId);
				String fieldValue = arg0.getParameter("fieldValue");
				fieldValue = CharacterUtil.getUTF_8String(fieldValue);
				
				boolean find = service.validateTpyeNo(fieldValue);
				PrintWriter out = arg1.getWriter();
				System.out.println("[\""+fieldId+"\","+find+"]");
				out.print("[\""+fieldId+"\","+find+"]");
			}
			
			//表单费用类型的唯一性验证(二次验证，fuck，插件bug)
			if(method.equalsIgnoreCase("ajaxValidateTypeNoSec")){
				String fieldValue = arg0.getParameter("fieldValue");
				fieldValue = CharacterUtil.getUTF_8String(fieldValue);
				
				boolean find = service.validateTpyeNo(fieldValue);
				PrintWriter out = arg1.getWriter();
				out.print(find);
			}
			
			//物业费和高层物业费的互斥性验证,这个函数暂时没用，验证采用了提交时验证，在二次验证函数。
			if(method.equalsIgnoreCase("ajaxValidatePropertyRela")){
				String fieldId = arg0.getParameter("fieldId");
				fieldId = CharacterUtil.getUTF_8String(fieldId);
				String fieldValue = arg0.getParameter("fieldValue");
				fieldValue = CharacterUtil.getUTF_8String(fieldValue);
				boolean find = true;
				if(fieldValue.equals("0001") || fieldValue.equals("0002")){
//					find = serviceRela.validatePropertyRela(fieldValue);
				}
				PrintWriter out = arg1.getWriter();
				System.out.println("[\""+fieldId+"\","+find+"]");
				out.print("[\""+fieldId+"\","+find+"]");
			}
			
			//物业费和高层物业费的互斥性验证(二次验证，fuck，插件bug)
			if(method.equalsIgnoreCase("ajaxValidatePropertyRelaSec")){
				String roomId = arg0.getParameter("room_id");
				String chargeTypeNo = arg0.getParameter("charge_type_no");
				String typeFlag = arg0.getParameter("type_flag");
				
				boolean find = true;
//				if(chargeTypeNo.equals("0001") || chargeTypeNo.equals("0002")){
//					find = serviceRela.validatePropertyRela(roomId);
//				}
				Map<String, Object> parameter = new HashMap();
			    parameter.put("room_id", roomId);
			    parameter.put("charge_type_no", chargeTypeNo);
			    parameter.put("type_flag", typeFlag);
			    //验证
			    find = serviceRela.validatePropertyRela(parameter);				
				
				PrintWriter out = arg1.getWriter();
				out.print(find);
			}
			
			
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(method.equals("list")){
			//arg0.getRequestDispatcher("/ChargeManager/ChargeInfo.jsp").forward(arg0,arg1);
		}
	}

	@Override
	public String getUrl() {
		return "/ChargeManager/ChargeType";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
}