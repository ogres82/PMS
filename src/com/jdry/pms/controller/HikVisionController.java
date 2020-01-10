/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-16 下午9:38:47
 * @description
 *
 */

package com.jdry.pms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.comm.util.HikvisionUtil;
import com.jdry.pms.comm.util.HttpRequestUtil;

/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-16 下午9:38:47
 * @description
 *
 */
@Repository
@Component
public class HikVisionController implements IController{

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String method = arg0.getParameter("method");
		if(method.equals("/pms/getPlatFixedCardRechargeRecord")){
			
			String param = arg0.getParameter("param");
//			try{
////				param = URLDecoder.decode(param, "UTF-8");
//				param = URLEncoder.encode(param, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//					
//			}
//			String value = URLEncoder.encode("贵A", "UTF-8");
//			String param = "plateNo="+value+"&pageNo=1&pageSize=15";
//			param = URLDecoder.decode(param, "UTF-8");
			String url = HikvisionUtil.buildGetUrl(method,param,"0");
			String params = HikvisionUtil.buildGetUrl(method,param,"1");
			System.out.println("------->参数----"+params);
			String result = HttpRequestUtil.sendGet(url, params);
			System.out.println(result);
		}else if(method.equals("/pms/reChargePlatCar")){
//			SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
//			String carIndexCode = arg0.getParameter("carIndexCode");
//			String parkingSyscode  = arg0.getParameter("parkingSyscode");
//			String startTime  = arg0.getParameter("startTime");
//			String endTime  = arg0.getParameter("endTime");
//			String money  = arg0.getParameter("money");
//			Date startTime1 = null;
//			Date endTime1 = null;
//			try {
//				startTime1 = sdf.parse(startTime);
//				endTime1 = sdf.parse(endTime);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("carIndexCode", carIndexCode);
//			map.put("parkingSyscode", parkingSyscode);
//			map.put("startTime", startTime1.getTime());
//			map.put("endTime", endTime1.getTime());
//			map.put("money", Float.parseFloat(money));
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("deptCode", "91234be7b21b4d3e96fb2577aed29baf");
			map.put("personCode", "test");
//			map.put("userName", "超哥");
			map.put("sex", "test");
//			map.put("ocsIdentityId", "1231443");
			map.put("name", "test");
			String result = HttpRequestUtil.sendPost(HikvisionUtil.buildPostUrl("/base/addPlatPersonInfo", map), map);
//			String result = HttpRequestUtil.sendPost(HikvisionUtil.buildPostUrl(method, map), map);
			System.out.println(result);
		}else if(method.equals("/pms/getPlatParkCarInfo")){
			String param = arg0.getParameter("param");
			String url = HikvisionUtil.buildGetUrl(method,param,"0");
			String params = HikvisionUtil.buildGetUrl(method,param,"1");
			String result = HttpRequestUtil.sendGet(url, params);
			System.out.println(result);
		}else if(method.equals("/pms/addPlatParkCarInfo")){
			String plateNo = arg0.getParameter("plateNo");
			String ownerId  = arg0.getParameter("ownerId");
			String cardNo  = arg0.getParameter("cardNo");
			String carType  = arg0.getParameter("carType");
			String carColor  = arg0.getParameter("carColor");
			String plateColor  = arg0.getParameter("plateColor");
			String plateType  = arg0.getParameter("plateType");
			String plateStart  = arg0.getParameter("plateStart");
			String carBrand  = arg0.getParameter("carBrand");
			String driver  = arg0.getParameter("driver");
			String driverPhone  = arg0.getParameter("driverPhone");
			String num  = arg0.getParameter("num");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("plateNo", plateNo);
			map.put("ownerId", Integer.parseInt(ownerId));
			map.put("cardNo", cardNo);
			map.put("carType", Integer.parseInt(carType));
//			map.put("carColor", Integer.parseInt(carColor));
			map.put("plateColor", Integer.parseInt(plateColor));
			map.put("plateType", Integer.parseInt(plateType));
			map.put("plateStart", null);
			map.put("carBrand", null);
			map.put("driver", driver);
			map.put("driverPhone", driverPhone);
			map.put("num", Integer.parseInt(num));
			String url = HikvisionUtil.buildPostUrl(method, map);
			String result = HttpRequestUtil.sendPost(url, map);
			System.out.println(result);
		}
		
		
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/hikvision";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
