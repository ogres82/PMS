package com.jdry.pms.msgpublic.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.service.MsgAndNoticeConstant;
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.msgandnotice.service.impl.PositionServiceImpl;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.msgpublic.pojo.MsgPubAuditinfo;
import com.jdry.pms.msgpublic.pojo.MsgPubMain;
import com.jdry.pms.msgpublic.service.MsgPubAuditInfoService;
import com.jdry.pms.msgpublic.service.MsgPubService;
@Repository
@Component
public class MsgPubAuditController implements IController{
	@Resource
    public MsgPubService noticeService;
	@Resource
	PositionService positionService;
	@Resource
	private MsgPubAuditInfoService auditInfoService;
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
			if(method.equalsIgnoreCase("listAuditNotice")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				  // 每页行数
				  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				  if (currentPage != 0) {// 获取页数
				   currentPage = currentPage / showCount;
				  }
				  currentPage += 1;
				Page<MsgPubMain> page =new Page<MsgPubMain>(showCount, currentPage);
				Properties property = PositionServiceImpl.getProperty();
				String wyfzPosiId = property.getProperty(MsgAndNoticeConstant.PROPERTY_WYFZ_KEY);
				String wylzPosiId = property.getProperty(MsgAndNoticeConstant.PROPERTY_WYLZ_KEY);
				boolean isWyfz=noticeService.isAuthorityAudit(user.getUsername(), wyfzPosiId);
				boolean isWylz=noticeService.isAuthorityAudit(user.getUsername(),wylzPosiId);
				if(isWyfz){
					noticeService.getWyfzAuditNotice(page, null, null,user.getUsername());
				}else if(isWylz){
					noticeService.getWylzAuditNotice(page, null, null,user.getUsername());					
				}else{
					List<MsgPubMain> noticeList=new ArrayList<MsgPubMain>();
					page.setEntities(noticeList);
				}
				List<MsgPubMain> result=(List<MsgPubMain>) page.getEntities();
				String jsonString = JSON.toJSONString(result);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
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
				String ntcContent=CharacterUtil.getUTF_8String(arg0.getParameter("ntcContent"));
				String ntcCreatorId=CharacterUtil.getUTF_8String(arg0.getParameter("ntcCreatorId"));
				String ntcSubject=CharacterUtil.getUTF_8String(arg0.getParameter("ntcSubject"));
				String ntcCreatorName=CharacterUtil.getUTF_8String(arg0.getParameter("ntcCreatorName"));
				String ntcAudit=CharacterUtil.getUTF_8String(arg0.getParameter("ntcAudit"));
				String ntcCreateTimeStr=CharacterUtil.getUTF_8String(arg0.getParameter("ntcCreateTime"));
				Date ntcCreateTime =DateUtil.convertStringToDate(ntcCreateTimeStr, "yyyy-MM-dd HH:mm:ss");
				MsgPubMain notice=new MsgPubMain();
				notice.setNtcAuditor(ntcAudit);
				notice.setNtcContent(ntcContent);
				notice.setNtcCreateTime(ntcCreateTime);
				notice.setNtcCreator(ntcCreatorName);
				notice.setNtcCreatorId(ntcCreatorId);
				notice.setNtcStatus("20");
				notice.setNtcSubject(ntcSubject);
				List<MsgPubMain> saveList=new ArrayList<MsgPubMain>();
				saveList.add(notice);
				noticeService.saveNotice(saveList,null);
			}
			if(method.equalsIgnoreCase("viewForAjax")){
				String id =arg0.getParameter("noticeId");
				MsgPubMain notice=noticeService.getNoticeById(id);
				String jsonString = JSON.toJSONString(notice);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				arg0.setAttribute("notice", notice);
			}
			if(method.equalsIgnoreCase("update")){
				String ntcId=arg0.getParameter("ntcId");
				String passFlag=arg0.getParameter("passFlag");
				String auditContent=arg0.getParameter("auditContent");
				MsgPubMain notice=noticeService.getNoticeById(ntcId);
				String username=user.getUsername();
				String userCname=user.getCname();
				MsgPubAuditinfo mna=new MsgPubAuditinfo();
	        	mna.setNtcAuditor(userCname);
	        	mna.setNtcAuditorId(username);
	        	mna.setNtcAuditContnt(auditContent);
	        	mna.setNtcCreateTime(new Date());
	        	mna.setNtcNoticeId(ntcId);
	        	mna.setNtcPassFlag(passFlag);
	        	noticeService.updateSingleNotice(notice,passFlag,null);
	        	auditInfoService.saveSingleInfo(mna);
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
							noticeService.deleteNoticeById((String) jsonArr.get(i));
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
			arg0.getRequestDispatcher("/msgandnotice/NoticeAudit.jsp").forward(arg0,arg1);
		}
		if(method.equals("save")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
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
			arg0.getRequestDispatcher("/msgandnotice/NoticeAudit.jsp").forward(arg0,arg1);
		}
	}

	@Override
	public String getUrl() {
		return "/msgpublic/msgaudit";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
}