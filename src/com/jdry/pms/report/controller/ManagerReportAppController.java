package com.jdry.pms.report.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.controller.ImpairedRepairServiceInterface;
import com.jdry.pms.assignWork.controller.PropertyManageComplaintServiceInterface;
import com.jdry.pms.chargeManager.service.ChargeTypeSettingService;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.houseWork.controller.HouseworkPropertManageInterface;
import com.jdry.pms.report.dao.ServiceReportDao;
import com.jdry.pms.report.service.ChargeReportService;
import com.jdry.pms.report.service.ServiceReportService;

@Component
public class ManagerReportAppController {
	
	
	@Resource
	ChargeReportService chargeReportService;
	
	@Resource
	ChargeTypeSettingService chargeTypeSettingService;
	@Resource
	ServiceReportDao serviceReportDao;
	@Resource
	ServiceReportService service;
	@Resource
	PropertyManageComplaintServiceInterface compService;
	@Resource
	ImpairedRepairServiceInterface mtnService;
	@Resource
	HouseworkPropertManageInterface hwService;
	
	/**
	 * 收费月统计
	 * @Title: queryChargeMonth   
	 * @Description: TODO
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryChargeMonth(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String year = null == obj.getString("year")?"" : obj.getString("year");
		if(chargeReportService == null){
			chargeReportService = (ChargeReportService) SpringUtil.getObjectFromApplication("chargeReportServiceImpl");
		}
		List list = (List) chargeReportService.getChargeInfoByMonth(year);
		List result = new ArrayList();
			for(int i=0;i<list.size();i++){
				Map map = new HashMap();
				Object[] obje = (Object[])list.get(i);
				if(obje[0]==null || obje[0].equals("")){
					continue;
				}
				map.put("months", obje[0]);
				map.put("receiveAmount", obje[2]);
				map.put("paidAmount", obje[1]);
				map.put("arrearageAmount", obje[3]);
				result.add(map);
			}
		String jsonString = JSON.toJSONString(result);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	} 
	
	/**
	 * 小区收费统计
	 * @Title: queryChargeArea   
	 * @Description: TODO
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryChargeArea(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String month = null == obj.getString("month")?"" : obj.getString("month");
		if(chargeReportService == null){
			chargeReportService = (ChargeReportService) SpringUtil.getObjectFromApplication("chargeReportServiceImpl");
		}
		List list = (List) chargeReportService.getChargeInfoByArea(month);
		List result = new ArrayList();
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = new HashMap<String,Object>();
				Object[] obje = (Object[])list.get(i);
				if(obje[0]==null || obje[0].equals("")){
					continue;
				}
				map.put("communityName", obje[0]);
				map.put("communityId", obje[1]);
				map.put("receiveAmount", obje[3]);
				map.put("paidAmount", obje[2]);
				map.put("arrearageAmount", obje[4]);
				result.add(map);
			}
		String jsonString = JSON.toJSONString(result);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	} 
	
	/**
	 * 收费项目收费统计
	 * @Title: queryChargeType   
	 * @Description: TODO
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryChargeType(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject ob = JSON.parseObject(str);
		String month = null == ob.getString("month")?"" : ob.getString("month");
		if(chargeReportService == null){
			chargeReportService = (ChargeReportService) SpringUtil.getObjectFromApplication("chargeReportServiceImpl");
		}
		List list = (List) chargeReportService.getChargeInfoByType(month);
		if(chargeTypeSettingService == null){
			chargeTypeSettingService = (ChargeTypeSettingService) SpringUtil.getObjectFromApplication("chargeTypeSettingImpl");
		}
		List r = chargeTypeSettingService.queryChargeTypeInfo("", "");
		List result = new ArrayList();
		for(int j=0;j<r.size();j++){
			BigDecimal receiveAmount = new BigDecimal(0);
			BigDecimal paidAmount = new BigDecimal(0);
			BigDecimal arrearageAmount = new BigDecimal(0);
			Object[] o = (Object[])r.get(j);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("chargeTypeName", o[2]);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] obj = (Object[])list.get(i);
					if(obj[1].toString().equals(o[1].toString())){
						receiveAmount = (BigDecimal) obj[3];
						paidAmount = (BigDecimal) obj[2];
						arrearageAmount = (BigDecimal) obj[4];
					}
				}
				map.put("receiveAmount", receiveAmount);
				map.put("paidAmount", paidAmount);
				map.put("arrearageAmount", arrearageAmount);
				result.add(map);
			}
		}
		String jsonString = JSON.toJSONString(result);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	} 
	
	/**
	 * 服务统计
	 * @Title: queryChargeArea   
	 * @Description: TODO
	 */
	public String queryServiceReport(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		if(serviceReportDao == null){
			serviceReportDao = (ServiceReportDao) SpringUtil.getObjectFromApplication("serviceReportDao");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String curMonth = sdf.format(new Date());
		List list1 = serviceReportDao.getMtnInfo();
		List list2 = serviceReportDao.getCompInfo();
		List list3 = serviceReportDao.getHouseworkInfo();
		int curMonthCountMtn = 0;
		int curMonthCountComp = 0;
		int curMonthCountHousework = 0;
		int totalMtn = 0;
		int totalComp = 0;
		int totalHousework = 0;
		for(int i=0;i<list1.size();i++){
			Object[] obj = (Object[])list1.get(i);
			if(curMonth.equals(obj[2].toString())){
				curMonthCountMtn = ((BigInteger)obj[3]).intValue();
			}
			totalMtn = totalMtn + ((BigInteger)obj[3]).intValue();
		}
		for(int i=0;i<list2.size();i++){
			Object[] obj = (Object[])list2.get(i);
			if(curMonth.equals(obj[2].toString())){
				curMonthCountComp = ((BigInteger)obj[3]).intValue();
			}
			totalComp = totalComp + ((BigInteger)obj[3]).intValue();
		}
		for(int i=0;i<list3.size();i++){
			Object[] obj = (Object[])list3.get(i);
			if(curMonth.equals(obj[2].toString())){
				curMonthCountHousework = ((BigInteger)obj[3]).intValue();
			}
			totalHousework = totalHousework + ((BigInteger)obj[3]).intValue();
		}
		JSONObject object = new JSONObject();
		object.put("curMonthCountMtn",curMonthCountMtn);
		object.put("curMonthCountComp",curMonthCountComp);
		object.put("curMonthCountHousework",curMonthCountHousework);
		object.put("totalMtn",totalMtn);
		object.put("totalComp",totalComp);
		object.put("totalHousework",totalHousework);
		String jsonString = JSON.toJSONString(object);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	/**
	 * 服务列表
	 * @Title: queryChargeArea   
	 * @Description: TODO
	 */
	public String queryServiceReportList(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		if(service == null){
			service = (ServiceReportService) SpringUtil.getObjectFromApplication("serviceReportServiceImpl");
		}
		JSONObject obj = JSON.parseObject(str);
		String eventType = obj.getString("eventType");
		String finishFlag = obj.getString("finishFlag");
		List<?> list = null;
		if(eventType.equals("01")){ //维修
			list = service.queryMtnList(finishFlag);
		}
		if(eventType.equals("02")){ //咨询建议
			list = service.queryCompList(finishFlag);
		}
		if(eventType.equals("03")){ //家政
			list = service.queryHouseworkList(finishFlag);
		}
		String jsonString = JSON.toJSONString(list);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	/**
	 * 服务详情
	 * @Title: queryChargeArea   
	 * @Description: TODO
	 */
	public String queryServiceReportDetail(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String eventType = obj.getString("eventType");
		String jsonString = null;
		if(eventType.equals("01")){ //维修
			if(mtnService == null){
				mtnService = (ImpairedRepairServiceInterface) SpringUtil.getObjectFromApplication("impairedRepairServiceInterface");
			}
			String id = "{'rpt_id':'"+obj.getString("id")+"'}";
			jsonString = mtnService.getProptyImpairRepDetailByEventId(id);
		}
		if(eventType.equals("02")){ //咨询建议
			if(compService == null){
				compService = (PropertyManageComplaintServiceInterface) SpringUtil.getObjectFromApplication("propertyManageComplaintServiceInterface");
			}
			String id = "{'mtn_id':'"+obj.getString("id")+"'}";
			jsonString = compService.getDispatchComplantDetail(id);
		}
		if(eventType.equals("03")){ //家政
			if(hwService == null){
				hwService = (HouseworkPropertManageInterface) SpringUtil.getObjectFromApplication("houseworkPropertManageInterface");
			}
			String id = "{'event_no':'"+obj.getString("id")+"'}";
			jsonString = hwService.getHouseKeepingDeatail(id);
		}
		return jsonString;
	}
	
}
