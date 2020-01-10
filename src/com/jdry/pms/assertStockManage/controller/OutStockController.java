package com.jdry.pms.assertStockManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.assertStockManage.pojo.InstockModel;
import com.jdry.pms.assertStockManage.pojo.VoucherModel;
import com.jdry.pms.assertStockManage.service.ItmsfilesService;
import com.jdry.pms.assertStockManage.service.OutStockService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;
/**
 * 出库操作管理
 * @author hezuping
 *
 */
@Repository
@Component
public class OutStockController implements IController
{

	@Resource
	CommUtil commUtil;
	@Resource
	CommUser commUser;
	@Resource
	ItmsfilesService itmsfilesService;
	@Resource
	OutStockService outStockService;
	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json;charset=utf-8");
		res.setHeader("pragma","no-cache");
		res.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		if(method.equals("queryOutStockOrder"))
		{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String occurren_date=sd.format(new Date());
			String voucher_code = commUtil.getBusinessId("CK","D");
			IUser user=CommUser.getUser();
			String t_handler=user.getCname();
			Map map = new HashMap();
			map.put("voucher_code", voucher_code);
			map.put("t_handler", t_handler);
			map.put("occurren_date", occurren_date);
			String jsonString = JSON.toJSONString(map);
			PrintWriter out = res.getWriter();
			out.print(jsonString);	
		}
		if(method.equals("submitOutStockData"))
		{
		    String instockJson = req.getParameter("instockJson") == null ? "" : req.getParameter("instockJson");
		    String orderJson = req.getParameter("orderJson") == null ? "" : req.getParameter("orderJson");
		    JSONArray jsonArr = JSON.parseArray(instockJson);
		    List<InstockModel> instockList=new ArrayList<InstockModel>();
		    for(int i=0;i<jsonArr.size();i++)
		    {
		    	InstockModel ins=JSON.parseObject(jsonArr.get(i).toString(), new TypeReference<InstockModel>(){});
		    	instockList.add(ins);
		    }
		    VoucherModel vo=null;
		    if(!orderJson.equals("")||orderJson!=null)
		    {
		        vo=JSON.parseObject(orderJson.toString(), new TypeReference<VoucherModel>(){});
		        
		    }
		    boolean flag=outStockService.OutStockInfo(instockList, vo);
		    String msg="";
		    if(flag)
		    {
		    	msg="出库成功";
		    }else
		    {
		    	msg="出库失败,请跟管理员核实";
		    }
		    
		    Map<Object, Object> info = new HashMap<Object, Object>();
		    info.put("msg", msg);
			String jsonString = JSON.toJSONString(info); 
			PrintWriter out = res.getWriter();
			out.print(jsonString);
			out.flush();
		}
		
		
	}

	@Override
	public String getUrl() {
		return "/assertStockManage/outStockShowList";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}

}
