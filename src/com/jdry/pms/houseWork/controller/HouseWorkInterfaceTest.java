package com.jdry.pms.houseWork.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.controller.IController;

/**
 * 接口单元测试
 * @author hezuping
 *
 */
@Repository
@Component
public class HouseWorkInterfaceTest implements IController
{
    @Resource
    HouseWorkOwnerInterface houseWorkOwnerInterface;
    @Resource
    HouseworkPropertManageInterface houseworkPropertManageInterface;
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException
	{
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("pragma","no-cache");
		resp.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		if(method.equals("ownerEventSupply"))//测试申请
		{
			 String jsonSupply = req.getParameter("jsonSupply") == null ? "" : req.getParameter("jsonSupply");
			 System.out.println(houseWorkOwnerInterface.ownerEventSupply(jsonSupply));
			
		}
		if(method.equals("getAllHouseWorkEventInfo"))
		{
			 String jsonStr= req.getParameter("call_phone") == null ? "" : req.getParameter("call_phone");
			 System.out.println(houseWorkOwnerInterface.getAllHouseWorkEventInfo(jsonStr));
		}
		if(method.equals("getHouseWorkEventDeatail"))
		{
			 String jsonStr= req.getParameter("event_id") == null ? "" : req.getParameter("event_id");
			 System.out.println(houseWorkOwnerInterface.getHouseWorkEventDeatail(jsonStr));
		}
		if(method.equals("quaryHouseKeepingList"))
		{
			String jsonStr= req.getParameter("data") == null ? "" : req.getParameter("data");
			System.out.println(houseworkPropertManageInterface.quaryHouseKeepingList(jsonStr));
			
		}
		if(method.equals("getHouseKeepingDeatail"))
		{
			String jsonStr= req.getParameter("data") == null ? "" : req.getParameter("data");
			System.out.println(houseworkPropertManageInterface.getHouseKeepingDeatail(jsonStr));
		}
		if(method.equals("houseWorkCancel"))
		{
			String jsonStr= req.getParameter("data") == null ? "" : req.getParameter("data");
			System.out.println(houseworkPropertManageInterface.houseWorkCancel(jsonStr));
		}
		if(method.equals("gethouseKeepingPay"))//现场支付
		{
			String jsonStr= req.getParameter("data") == null ? "" : req.getParameter("data");
			System.out.println(houseworkPropertManageInterface.gethouseKeepingPay(jsonStr));
		}
		if(method.equals("houseKeepingCancel"))
		{
			String jsonStr= req.getParameter("data") == null ? "" : req.getParameter("data");
			System.out.println(houseworkPropertManageInterface.houseKeepingCancel(jsonStr));
		}
	}

	@Override
	public String getUrl() {
		return "/housework/test";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
