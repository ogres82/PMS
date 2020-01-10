package com.jdry.pms.msgandnotice.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.web.DoradoContext;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.msgandnotice.pojo.MessageSendMain;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.service.MessageSendService;
import com.jdry.pms.msgandnotice.service.MsgTempService;

/**
*
* @author 钟涛
*
*/
@Component
public class MessageSendView {
	@Resource
	MessageSendService messageSendService;
	@Resource
	MsgTempService messageTempService;
	@DataProvider
	public void getMessage(Page<MessageSendMain> page, Map<String, Object> parameter,Criteria criteria){
		try {
			DoradoContext dc = DoradoContext.getCurrent();
			HttpServletRequest request=dc.getRequest();
			if(request !=null){
				String remark=request.getParameter("remark");
				System.out.println(remark);
			}
			messageSendService.getSendMessage(page, parameter, criteria);
			ArrayList<MessageSendMain> displayList=new ArrayList();
			displayList=(ArrayList) page.getEntities();
			ArrayList<MessageSendMain> results=new ArrayList();
			for (MessageSendMain messageSendMain:displayList){
				MessageSendMain targetProduct = EntityUtils.toEntity(messageSendMain);
		        EntityUtils.setValue(targetProduct, "msgCreatorName", messageSendMain.getMsgCreator().getCname());
		        if(messageSendMain.getMsgSender()!=null){
			        EntityUtils.setValue(targetProduct, "msgSenderName", messageSendMain.getMsgSender().getCname());
		        }
		        EntityUtils.setValue(targetProduct, "msgTempTitle", messageSendMain.getMsgTemp().getMsgTempSubject());
		        EntityUtils.setValue(targetProduct, "msgReceiverNames", messageSendMain.getMsgReceivers().getOwnerName());
		        EntityUtils.setValue(targetProduct, "msgReceiverPhone", messageSendMain.getMsgReceivers().getPhone());

		        results.add(targetProduct);
		   }
		    page.setEntities(results);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@DataProvider
	/*
	 * 获取所有的短信模板，用于dropdown
	 */
	public Collection<MessageTempMain> getMessagTemp(){
		Collection<MessageTempMain> result =new ArrayList();
		try {
			
			result=messageTempService.getAllMsgTemp();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@DataProvider
	/*
	 * 为短信模板提供映射
	 */
	public Map<String,String> findMapTemp(){
		Map<String,String> rtnResult=new LinkedHashMap<String,String>();
		try {
			List<MessageTempMain> result=new ArrayList<MessageTempMain>();
			result =(List<MessageTempMain>) messageTempService.getAllMsgTemp();
		    for(MessageTempMain messageTempMain:result){
		    	rtnResult.put(messageTempMain.getMsgTempId(),messageTempMain.getMsgTempSubject());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnResult;
	}
	@DataResolver
	public void saveSendMsg (Collection<MessageSendMain> mes) throws IOException{
		
		messageSendService.saveAll(mes);
	}
	/*
	 * 找到系统用户
	 */
	@DataProvider
	public Collection<DefaultUser> getUser(){
		Collection<DefaultUser> result=new ArrayList();
		try {
			
			result= messageSendService.getUser();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@DataProvider
	/*
	 * 找到系统用户映射
	 */
	public Map<String,String> findMapUser(){
		Map<String,String> rtnResult=new LinkedHashMap<String,String>();
		try {
			List<DefaultUser> result=new ArrayList<DefaultUser>();
			result =(List<DefaultUser>) messageSendService.getUser();
		    for(DefaultUser defaultUser:result){
		    	rtnResult.put(defaultUser.getUsername(),defaultUser.getCname());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnResult;
	}
	@DataProvider
	public void queryOwnerInfo(Page page,Map<String, Object> parameter,Criteria criteria){
		messageSendService.queryOwnerInfo(page, parameter, criteria);
		ArrayList<Object[]> result=(ArrayList<Object[]>) page.getEntities();
		ArrayList<PropertyOwner> displayList=new ArrayList();
		for(int i=0;i<result.size();i++){
			 PropertyOwner po =new PropertyOwner();
		     Object[] tmpObjArray=result.get(i);
			 String ownerId=tmpObjArray[0].toString();
			 po.setOwnerId(ownerId);
			 String ownerName=tmpObjArray[1].toString();
			 po.setOwnerName(ownerName);
			 String roomNo=tmpObjArray[2].toString();
			 String phone=tmpObjArray[3].toString();
			 String remark=tmpObjArray[4]==null?"":tmpObjArray[4].toString();
			 po.setPhone(phone);
			 po.setRemark(remark);
			 displayList.add(po);
		}
	    page.setEntities(displayList);
	}
}
