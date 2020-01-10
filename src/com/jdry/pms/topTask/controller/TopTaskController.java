package com.jdry.pms.topTask.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.topTask.service.TopTaskService;
/**
 * 描述:代办任务控制器
 * @author hezuping
 * 2016-06-03 09:49:31
 */
@Repository
@Component
public class TopTaskController implements IController
{
    @Resource
    TopTaskService topTaskService;
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException 
    {
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("pragma","no-cache");
		resp.setHeader("cache-control","no-cache");
		String method=req.getParameter("method");
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		if(method.equals("getTopTask"))
		{  
			int count =topTaskService.getTaskCountByUserId(user.getUsername());
			
			PrintWriter out = resp.getWriter();
			out.println(JSON.toJSONString(count));
			out.flush();
			
		}//咨询建议任务列表
		if(method.equals("topTaskList"))
		{
			int currentPage = req.getParameter("offset") == null ? 1 : Integer.parseInt(req.getParameter("offset"));// 每页行数			  
		    int showCount = req.getParameter("limit") == null ? 10 : Integer.parseInt(req.getParameter("limit"));
		    String search=java.net.URLDecoder.decode(req.getParameter("search") , "UTF-8");
		    if (currentPage != 0) {// 获取页数
		    	currentPage = currentPage / showCount;
		    }
		    currentPage += 1;
		    Map<String, Object> parameter = new HashMap();
		    if(search.equals("undefined"))
		    {
		    	search="";
		    }
		    parameter.put("search", search);
		    parameter.put("userId", user.getUsername());
		    Page page =new Page(showCount, currentPage);	
			List res=topTaskService.queryTopTaskInfo(page, parameter, null);
			List list=new ArrayList();
			for(int i=0;i<res.size();i++)
			{
				Map map = new HashMap();
				Object[] obj = (Object[])res.get(i);
				String rpt_id = null == obj[0]?"":obj[0].toString();
				map.put("rpt_id", rpt_id);
					
				
				String accepttime = null == obj[1]?"":obj[1].toString();
				
				if(!accepttime.equals("")){
					 map.put("accept_time", getSimp(accepttime));
				}else
				{
						map.put("accept_time", accepttime);	
				}
				String creater = null == obj[2]?"":obj[2].toString();
				map.put("creater", creater);
				String address = null == obj[3]?"":obj[3].toString();
				map.put("address", address);	
				String dispatch_expense = null == obj[4]?"":obj[4].toString();
				map.put("dispatch_expense", dispatch_expense);	
				//bpm_processId
				String oper_id = null == obj[9]?"":obj[9].toString();
				map.put("oper_id", oper_id);
				String event_state = null == obj[6]?"":obj[6].toString();
				map.put("event_state", event_state);
				
				String type_name = null == obj[7]?"":obj[7].toString();
				map.put("type_name", type_name);
				
				
				list.add(map);
				
			}
			page.setEntities(list);
			List result=(List) page.getEntities();
			long count = page.getEntityCount();	
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("total", count);
		    info.put("rows", result);
			String jsonString = JSON.toJSONString(info);
			//传输JSON
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			out.flush();	
			
		}
		
	}

	@Override
	public String getUrl()
	{
		
		return "/top/topTask";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}
	
	public  String getSimp(String time)
	{
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String time1=sd.format(d);
		return time1;
		
	}

}
