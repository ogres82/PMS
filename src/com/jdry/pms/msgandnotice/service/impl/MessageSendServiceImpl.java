package com.jdry.pms.msgandnotice.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.dao.MessageSendDaoImpl;
import com.jdry.pms.msgandnotice.pojo.MessageSendMain;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.service.MessageSendService;
import com.jdry.pms.msgandnotice.service.MsgTempService;
@Repository
@Component
public class MessageSendServiceImpl implements MessageSendService{
	@Resource
    private MessageSendDaoImpl messageSendDaoImpl;
	@Resource
    private MsgTempService msgTempService;
	@Override
	public void getSendMessage(Page<MessageSendMain> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		Map map =new HashMap<String,Object>();
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		String msgCreater = parameter.get("msgCreatorId")!=null?parameter.get("msgCreatorId").toString():"";
		String msgFlag = parameter.get("msgFlag")!=null?parameter.get("msgFlag").toString():"";
		String msgCreateTimeStart = parameter.get("msgCreateTime")!=null?parameter.get("msgCreateTime").toString():"";
		String msfSendTime = parameter.get("msfSendTime")!=null?parameter.get("msfSendTime").toString():"";
		String msgSenderId = parameter.get("msgSenderId")!=null?parameter.get("msgSenderId").toString():"";
		String msgTempId = parameter.get("msgTempId")!=null?parameter.get("msgTempId").toString():"";
		String successFlag = parameter.get("successFlag")!=null?parameter.get("successFlag").toString():"";
		String sendFlag = parameter.get("sendFlag")!=null?parameter.get("sendFlag").toString():"";
		String msgCreateTimeEnd = parameter.get("msgCreateTimeEnd")!=null?parameter.get("msgCreateTimeEnd").toString():"";
		String msfSendTimeEnd = parameter.get("msfSendTimeEnd")!=null?parameter.get("msfSendTimeEnd").toString():"";
		String sql="from "+MessageSendMain.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+MessageSendMain.class.getName()+" du where 1=1";
	    if(!StringUtil.isEmpty(msgCreater)){
	        map.put("msgCreater",msgCreater);
	        sql+=" and du.msgCreatorId =:msgCreater";
	        sqlCount+=" and du.msgCreatorId =:msgCreater";
	    }
	    if(!StringUtil.isEmpty(msgSenderId)){
	        map.put("msgSenderId",msgSenderId);
	        sql+=" and du.msgSenderId =:msgSenderId";
	        sqlCount+=" and du.msgSenderId =:msgSenderId";
	    }
	    if(!StringUtil.isEmpty(msgFlag)){
	        map.put("msgFlag",msgFlag);
	        sql+=" and du.msgFlag =:msgFlag";
	        sqlCount+=" and du.msgFlag =:msgFlag";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeStart)){
			Date msgCreateTimeStartDate = DateUtil.parseToDate(msgCreateTimeStart);
	        map.put("msgCreateTime", msgCreateTimeStartDate);
	        sql+=" and du.msgCreateTime >=:msgCreateTime";
	        sqlCount+=" and du.msgCreateTime >=:msgCreateTime";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeEnd)){
			Date msgCreateTimeEndDate = DateUtil.parseToDate(msgCreateTimeEnd);
	        map.put("msgCreateTimeEnd", msgCreateTimeEndDate);
	        sql+=" and du.msgCreateTime <=:msgCreateTimeEnd";
	        sqlCount+=" and du.msgCreateTime <=:msgCreateTimeEnd";
	    }
	    if(!StringUtil.isEmpty(msfSendTime)){
			Date msfSendTimeDateStart = DateUtil.parseToDate(msfSendTime);
	        map.put("msfSendTimeStart", msfSendTimeDateStart);
	        sql+=" and du.msfSendTime >=:msfSendTimeStart";
	        sqlCount+=" and du.msfSendTime>=:msfSendTimeStart";
	    }
	    if(!StringUtil.isEmpty(msfSendTimeEnd)){
			Date msfSendTimeDateEnd = DateUtil.parseToDate(msfSendTimeEnd);
	        map.put("msfSendTimeDateEnd",msfSendTimeDateEnd);
	        sql+=" and du.msfSendTime <=:msfSendTimeDateEnd";
	        sqlCount+=" and du.msfSendTime <=:msfSendTimeDateEnd";
	    }
	    if(!StringUtil.isEmpty(msgTempId)){
	        map.put("msgTempId",msgTempId);
	        sql+=" and du.msgTempId =:msgTempId";
	        sqlCount+=" and du.msgTempId =:msgTempId";
	    }
	    if(!StringUtil.isEmpty(successFlag)){
	        map.put("successFlag",successFlag);
	        sql+=" and du.successFlag =:successFlag";
	        sqlCount+=" and du.successFlag =:successFlag";
	    }
	    if(!StringUtil.isEmpty(sendFlag)){
	        map.put("sendFlag",sendFlag);
	        sql+=" and du.sendFlag =:sendFlag";
	        sqlCount+=" and du.sendFlag =:sendFlag";
	    }
	    messageSendDaoImpl.find(page, sql, sqlCount, map);
	}

	@Override
	public void saveSendMessage(Collection<MessageSendMain> mes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAll(Collection<MessageSendMain> MessageSendMains) throws IOException {
		messageSendDaoImpl.saveMessageSend(MessageSendMains);
		
	}

	@Override
	public void saveSingleSendMessage(MessageSendMain mes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<DefaultUser> getUser() {
	    String sql="from "+DefaultUser.class.getName()+" du where 1=1";
	    return messageSendDaoImpl.getUser(sql);
	}
	@Override
	public void queryOwnerInfo(Page page,
			Map<String, Object> parameter, Criteria criteria) {
		if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
		Map map =new HashMap<String,Object>();
	    String sql="select po.ownerId,po.ownerName,po.roomNo,po.phone,po.remark from "
				+PropertyOwner.class.getName()+" po,"
				+AllArea.class.getName()+" aa,"
				+AreaProperty.class.getName()+" ap,"
				+BuildingProperty.class.getName()+" bp,"
				+HouseProperty.class.getName()+" hp"
				+" where po.roomId=hp.roomId and hp.belongSbId=bp.storiedBuildId " +
				"and bp.belongCommId=ap.communityId and ap.belongBuildId=aa.buildId";
	    String sqlCount="select count(*) from "
	    		+PropertyOwner.class.getName()+" po,"
	    		+AllArea.class.getName()+" aa,"
	    		+AreaProperty.class.getName()+" ap,"
	    		+BuildingProperty.class.getName()+" bp,"
	    		+HouseProperty.class.getName()+" hp"
	    		+" where po.roomId=hp.roomId and hp.belongSbId=bp.storiedBuildId " +
				"and bp.belongCommId=ap.communityId and ap.belongBuildId=aa.buildId";
		String belongSbId = parameter.get("belongSbId")!=null?parameter.get("belongSbId").toString():"";
		String buildId = parameter.get("buildId")!=null?parameter.get("buildId").toString():"";
		String areaId = parameter.get("areaId")!=null?parameter.get("areaId").toString():"";
		if(!StringUtil.isEmpty(belongSbId)){
	        map.put("belongSbId", belongSbId);
	        sql+=" and bp.storiedBuildId =:belongSbId";
	        sqlCount+=" and bp.storiedBuildId =:belongSbId";
	    }
		if(!StringUtil.isEmpty(buildId)){
	        map.put("buildId", buildId);
	        sql+=" and aa.buildId =:buildId";
	        sqlCount+=" and aa.buildId =:buildId";
	    }if(!StringUtil.isEmpty(areaId)){
	        map.put("areaId", areaId);
	        sql+=" and ap.communityId =:areaId";
	        sqlCount+=" and ap.communityId =:areaId";
	    }
		//String msgFlag = parameter.get("msgFlag")!=null?parameter.get("msgFlag").toString():"";
		//String msgCreateTimeStart = parameter.get("msgCreateTime")!=null?parameter.get("msgCreateTime").toString():"";
		messageSendDaoImpl.findOwner(page, sql, sqlCount, map);
		System.out.println();
	}
	//通过ID查询业主
	@Override
	public PropertyOwner queryOwnerById(String ownerId) {
		PropertyOwner propertyOwner=messageSendDaoImpl.queryOwnerById(ownerId);
		return propertyOwner;
	}


	@Override
	public String saveChargeMessage(PropertyOwner propertyOwner, Date dateline, float arrears) {
		String result="";
		try{
			String sendContent=msgTempService.findTempById("欠费短信模板");
			if(!StringUtil.isEmpty(sendContent)){
				MessageSendMain messageSendMain=new MessageSendMain();
				messageSendMain.setMsgCreateTime(new Date());
				messageSendMain.setMsgCreatorId(ContextHolder.getLoginUser().getUsername());
				messageSendMain.setMsgContent(sendContent);
				messageSendDaoImpl.saveSingleMessageSend(messageSendMain);
			}
		}catch(Exception e){
		   
		}
		return null;
	}

	@Override
	public List queryAllOwnerInfo() {
		// TODO Auto-generated method stub
		String sql="select po.ownerId,po.ownerName,po.roomNo,po.phone,po.remark,bp.storiedBuildName,aa.buildName,ap.communityName from "
				+PropertyOwner.class.getName()+" po,"
				+AllArea.class.getName()+" aa,"
				+AreaProperty.class.getName()+" ap,"
				+BuildingProperty.class.getName()+" bp,"
				+HouseProperty.class.getName()+" hp"
				+" where po.roomId=hp.roomId and hp.belongSbId=bp.storiedBuildId " +
				"and bp.belongCommId=ap.communityId and ap.belongBuildId=aa.buildId";
		List result=messageSendDaoImpl.findAllOwner(sql);
		return 	result;
	}

	@Override
	public MessageSendMain getSendMessageById(String id) throws Exception {
		MessageSendMain msm=messageSendDaoImpl.getMessageById(id);
		return msm;
	}

	@Override
	public String sendArrearMessage(List<Map<String, Object>> arrayList,String chargeMng) {
		//String tempId=CommEnum.CHARGE_TEMP_ID;
		String rtnMessage="发送成功";
		StringBuilder sendMessageTemp=new StringBuilder("");
		StringBuilder phonesTemp=new StringBuilder("");

		
//		MessageTempMain tempMain=msgTempService.getTempById(tempId);
//		String temp1=tempMain.getMsgTempContent();
//		String temp=temp1.replaceAll("</?[^>]+>","");
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		List<MessageSendMain> msgList=new ArrayList<MessageSendMain>();
	    try{
	    	if(arrayList!=null&&arrayList.size()>0){
				for(Map sendMap:arrayList){
					String tempId=sendMap.get("tempId").toString();
					MessageTempMain tempMain=msgTempService.getTempById(tempId);
					String temp1=tempMain.getMsgTempContent();
					String temp=temp1.replaceAll("</?[^>]+>","");
					String phone=(String) sendMap.get("phone");
					String msgType = (String) sendMap.get("msgType");
					if(phone != null && !phone.isEmpty()){
						String sumFee=(String) sendMap.get("sumFee");
						String delayPay=(String) sendMap.get("delayPay");					
						String sendMessge1=temp;
						String sendMessge2=sendMessge1.replace("#sumFee#", sumFee);
						if("1".equals(msgType)){
							sendMessge2=sendMessge2.replace("#lateFee#", delayPay);
						}
						MessageSendMain messageSendMain=new MessageSendMain();
						messageSendMain.setMsgCreateTime(new Date());
						messageSendMain.setMsgContent(sendMessge2);
						messageSendMain.setMsgTempId(tempId);
						messageSendMain.setMsgReceiverId(sendMap.get("ownerId").toString());
						messageSendMain.setMsgCreatorId(user.getUsername());
						msgList.add(messageSendMain);
						sendMessageTemp.append(sendMessge2);
						sendMessageTemp.append(",");
						phonesTemp.append(phone);
						phonesTemp.append(",");
					}
				}
				int phLength=phonesTemp.length();
				int teLength=sendMessageTemp.length();
				String phones=phonesTemp.substring(0, phLength-1);
				String sendMessage=sendMessageTemp.substring(0,teLength-1);
				rtnMessage=messageSendDaoImpl.sendArrearMessage(sendMessage,phones);
				messageSendDaoImpl.saveMessageSend(msgList);
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    	rtnMessage="发送失败";
	    }
		
		return rtnMessage;
	}

	@Override
	public PropertyOwner queryOwnerByPhone(String phone) {
		Map paraMap=new HashMap();
		String sql=" from "+PropertyOwner.class.getName()+" owner where owner.phone=:phone";
		String sqlCount="select count(*) from "+PropertyOwner.class.getName()+" owner where owner.phone=:phone";
		Page<PropertyOwner> page=new Page(1, 1);
		paraMap.put("phone", phone);
		messageSendDaoImpl.findPropertyByPhone(page, sql, sqlCount, paraMap);
		List<PropertyOwner> rtnList=(List<PropertyOwner>) page.getEntities();
		return rtnList.get(0);
	}

	@Override
	public List<MessageSendMain> queryMessagesByPhone(String phone) {
		Map paraMap=new HashMap();
		String sql=" from "+MessageSendMain.class.getName()+" msg where msg.msgReceivers.phone=:phone";
		paraMap.put("phone",phone);
		List<MessageSendMain> rtnList=messageSendDaoImpl.query(sql,paraMap);
		return rtnList;
	}
	
	@Override
	public void deleteMsgByIds(String id) {
		messageSendDaoImpl.deleteNoticeById(id);
		
	}
		
}
