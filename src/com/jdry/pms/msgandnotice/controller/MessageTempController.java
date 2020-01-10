package com.jdry.pms.msgandnotice.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.service.MsgTempService;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.msgandnotice.service.PositionService;
@Repository
@Component
public class MessageTempController implements IController{
	@Resource
    public NoticeService noticeService;
	@Resource
	PositionService positionService;
	@Resource
    public MsgTempService msgTempService;
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		String method=arg0.getParameter("method");
		try {
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("listTemp")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				  // 每页行数
				  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				  if (currentPage != 0) {// 获取页数
				   currentPage = currentPage / showCount;
				  }
				  currentPage += 1;
				Page<MessageTempMain> page =new Page<MessageTempMain>(showCount, currentPage);
				msgTempService.getMsgTemp(page, null, null);
				List<MessageTempMain> result=(List<MessageTempMain>) page.getEntities();
				String jsonString = JSON.toJSONString(result);
				JSONArray jsonArr = JSON.parseArray(jsonString); 
			    //int TotalCount=SqlADO.getTradeRowsCount(sql);
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
				//out.flush();
				//arg0.setAttribute("noticeList", result);

			}
			//获取审核人
			if(method.equalsIgnoreCase("getAuditor")){
				List<DefaultPosition> positions=(List<DefaultPosition>) positionService.findPosition();
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if(method.equalsIgnoreCase("save")){
				String msgTempSubject=arg0.getParameter("msgTempSubject");
				String msgTempContent=arg0.getParameter("msgTempContent");
				String ntcCreateTimeStr=arg0.getParameter("msgTempCtime");
				Date ntcCreateTime =DateUtil.convertStringToDate(ntcCreateTimeStr, "yyyy-MM-dd HH:mm:ss");
				MessageTempMain notice=new MessageTempMain();
				notice.setMsgTempContent(msgTempContent);
				notice.setMsgTempCtime(ntcCreateTime);
				notice.setMsgTempSubject(msgTempSubject);
				List<MessageTempMain> saveList=new ArrayList<MessageTempMain>();
				saveList.add(notice);
				msgTempService.saveMsgTemp(saveList);
			}
			if(method.equalsIgnoreCase("viewForAjax")){
				String id =arg0.getParameter("tempId");
				MessageTempMain temp=msgTempService.getTempById(id);
				String jsonString = JSON.toJSONString(temp);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if(method.equalsIgnoreCase("update")){
				String msgTempId=arg0.getParameter("msgTempId");
				String msgTempSubject=arg0.getParameter("msgTempSubject");
				String msgTempContent=arg0.getParameter("msgTempContent");
				MessageTempMain notice=msgTempService.getTempById(msgTempId);
				notice.setMsgTempContent(msgTempContent);
				notice.setMsgTempSubject(msgTempSubject);
				List<MessageTempMain> updateList=new ArrayList<MessageTempMain>();
				updateList.add(notice);
				msgTempService.updateTemp(updateList);
			}if(method.equalsIgnoreCase("deleteByAjax")){
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				System.out.println("来delete方法了");
				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					if(!StringUtil.isEmpty(deleteIds)){
						JSONArray jsonArr = JSON.parseArray(deleteIds);
						for (int i=0;i<jsonArr.size();i++){
							msgTempService.deleteTempById((String) jsonArr.get(i));
						}
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(method.equals("list")){
			arg0.getRequestDispatcher("/msgandnotice/MessageTemp.jsp").forward(arg0,arg1);
		}
		if(method.equals("save")){
			arg0.getRequestDispatcher("/msgandnotice/MessageTemp.jsp").forward(arg0,arg1);
		}
		if(method.equals("edit")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePubEdit.jsp").forward(arg0,arg1);
		}
		if(method.equals("delete")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
		}
		if(method.equals("view")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePubView.jsp").forward(arg0,arg1);
		}
		if(method.equals("update")){
			arg0.getRequestDispatcher("/msgandnotice/MessageTemp.jsp").forward(arg0,arg1);
		}
	}

	@Override
	public String getUrl() {
		return "/msgandnotice/msgtemp";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
}