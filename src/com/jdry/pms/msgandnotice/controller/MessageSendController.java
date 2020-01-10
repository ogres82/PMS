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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.msgandnotice.pojo.MessageSendMain;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.pojo.MsgReceiverMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.service.MessageSendService;
import com.jdry.pms.msgandnotice.service.MsgTempService;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
@Repository
@Component
public class MessageSendController implements IController{
	@Resource
    public NoticeService noticeService;
	@Resource
	PositionService positionService;
	@Resource
	MessageSendService messageSendService;
	@Resource
	MsgTempService messageTempService;
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
			if(method.equalsIgnoreCase("listMsg")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				  // 每页行数
				  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				  if (currentPage != 0) {// 获取页数
				   currentPage = currentPage / showCount;
				  }
				  currentPage += 1;
				Page<MessageSendMain> page =new Page<MessageSendMain>(showCount, currentPage);
				messageSendService.getSendMessage(page, null, null);
				List<MessageSendMain> result=(List<MessageSendMain>) page.getEntities();
				String jsonString = JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
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
			//获取短信模板
			if(method.equalsIgnoreCase("getMsgTemp")){
				List<MessageTempMain> temps=(List<MessageTempMain>) messageTempService.getAllMsgTemp();
				String jsonString = JSON.toJSONString(temps);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}//获取短信模板内容
			if(method.equalsIgnoreCase("getMsgTempContent")){
				String tempId=arg0.getParameter("tempId");
				MessageTempMain temp=messageTempService.getTempById(tempId);
				String jsonString = JSON.toJSONString(temp);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}//获取业主信息
			if(method.equalsIgnoreCase("listOwner")){
				List<Object[]> result=messageSendService.queryAllOwnerInfo();
				ArrayList<MsgReceiverMain> displayList=new ArrayList<MsgReceiverMain>();
				for(int i=0;i<result.size();i++){
					MsgReceiverMain po =new MsgReceiverMain();
				     Object[] tmpObjArray=result.get(i);
					 String ownerId=tmpObjArray[0].toString();
					 po.setOwnerId(ownerId);
					 String ownerName=tmpObjArray[1].toString();
					 po.setOwnerName(ownerName);
					 String roomNo=tmpObjArray[2]==null?"":tmpObjArray[2].toString();
					 String phone=tmpObjArray[3].toString();
					 String remark=tmpObjArray[4]==null?"":tmpObjArray[4].toString();
					 String storiedBuildName=tmpObjArray[5].toString();
					 String buildName=tmpObjArray[6].toString();
					 String communityName=tmpObjArray[7].toString();
					 po.setRoomNo(roomNo);
					 po.setPhone(phone);
					 po.setRemark(remark);
					 po.setBuildName(buildName);
					 po.setCommunityName(communityName);
					 po.setStoriedBuildName(storiedBuildName);
					 displayList.add(po);
				}
				String jsonString = JSON.toJSONString(displayList);
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
				String msgContent=arg0.getParameter("msgContent");
				String msgTemp=arg0.getParameter("msgTemp");
				String msgReceiverIds=arg0.getParameter("msgReceiverIds");
				String[] receiverIds=msgReceiverIds.split("\\;");
            	List<MessageSendMain> sendList=new ArrayList<MessageSendMain>();
	            for(String recId:receiverIds){
					MessageSendMain empClone=new MessageSendMain();
	            	String id=CommUtil.getGuuid();
	            	empClone.setMsgSendId(id);
	            	empClone.setMsgReceiverId(recId);
	            	empClone.setMsgContent(msgContent);
	            	empClone.setMsgCreateTime(new Date());
	            	empClone.setMsgCreatorId(user.getUsername());
	            	empClone.setMsgTempId(msgTemp);
	            	sendList.add(empClone);
	            	
	            }
				messageSendService.saveAll(sendList);
			}
			if(method.equalsIgnoreCase("viewForAjax")){
				String id =arg0.getParameter("sendId");
				MessageSendMain notice=messageSendService.getSendMessageById(id);
				String jsonString = JSON.toJSONString(notice);
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
				String ntcId=CharacterUtil.getUTF_8String(arg0.getParameter("ntcId"));
				String ntcContent=CharacterUtil.getUTF_8String(arg0.getParameter("ntcContent"));
				String ntcSubject=CharacterUtil.getUTF_8String(arg0.getParameter("ntcSubject"));
				String ntcAudit=CharacterUtil.getUTF_8String(arg0.getParameter("ntcAudit"));
				MsgandnotNoticeMain notice=noticeService.getNoticeById(ntcId);
				notice.setNtcAuditor(ntcAudit);
				notice.setNtcContent(ntcContent);
				notice.setNtcSubject(ntcSubject);
				List<MsgandnotNoticeMain> updateList=new ArrayList<MsgandnotNoticeMain>();
				updateList.add(notice);
				noticeService.updateNotice(updateList);
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
							messageSendService.deleteMsgByIds((String) jsonArr.get(i));
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
			arg0.getRequestDispatcher("/msgandnotice/MessageSend.jsp").forward(arg0,arg1);
		}
		if(method.equals("save")){
			arg0.getRequestDispatcher("/msgandnotice/MessageSend.jsp").forward(arg0,arg1);
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
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
		}
	}

	@Override
	public String getUrl() {
		return "/msgandnotice/mgssend";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
}