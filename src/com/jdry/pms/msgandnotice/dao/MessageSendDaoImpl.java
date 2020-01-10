package com.jdry.pms.msgandnotice.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.comm.service.impl.SmsSendInteface;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.CommEnum;
import com.jdry.pms.msgandnotice.pojo.MessageSendMain;

/**
*
* @author 钟涛
*
*/
@Repository
@Transactional
public class MessageSendDaoImpl extends HibernateDao{
	@Resource
    public SmsSendInteface smsSendInteface;
	@SuppressWarnings("unused")
	public void find(Page<MessageSendMain> page,String sql,String sqlCount,Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	public void findOwner(Page page,String sql,String sqlCount,Map map) {
		try {
			this.pagingQuery(page, sql, sqlCount, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List findAllOwner(String sql){
		List rtnList=new ArrayList();
		rtnList=this.query(sql);
		return rtnList;
	}
	//保存短信内容
	public void saveMessageSend(Collection<MessageSendMain> emps) throws IOException {
		Session session = this.getSessionFactory().openSession();
		String sendText="";
		String sendMobile="";
	    try{
	        for (MessageSendMain emp : emps) {
	        	  String msgContent=emp.getMsgContent();
	        	  if(emp.getMsgTempId().equals(CommEnum.CODE_TEMP_ID)){
			          String code=getRandNum(4);
			          String msgContentRep=msgContent.replaceAll("#code#",code);
			          sendText+=URLEncoder.encode(msgContentRep,"utf-8")+",";
	        	  }else{
	        		  sendText+=URLEncoder.encode(msgContent,"utf-8")+",";
	        	  }
		          PropertyOwner po=queryOwnerById(emp.getMsgReceiverId());
		          sendMobile+=po.getPhone()+",";
		          session.save(emp);
	        }
	        sendText=sendText.substring(0,sendText.length()-1);
	        sendMobile=sendMobile.substring(0,sendMobile.length()-1);
	        smsSendInteface.sendSmsForMessage(sendText, sendMobile);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    finally{
	        session.flush();
	        session.close();
	    }
	    
	}
	public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
   public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
	@SuppressWarnings("unused")
	public Collection<DefaultUser> getUser(String sql) {
		Collection<DefaultUser> result=new ArrayList();
		try {
			result=this.query(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void saveSingleMessageSend(MessageSendMain emp) {
		Session session = this.getSessionFactory().openSession();
	    try{
	    	CommUtil cu=new CommUtil();
            String id=CommUtil.getGuuid();
            emp.setMsgSendId(id);
            session.save(emp);
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
	public MessageSendMain getMessageById(String id){
		Session session = this.getSessionFactory().openSession();
		MessageSendMain notice=new MessageSendMain();
		try{
			 notice=(MessageSendMain) session.get(MessageSendMain.class,id);
	    }finally{
	        session.flush();
	        session.close();
	    }
		return notice;
	}
	public PropertyOwner queryOwnerById(String id){
		Session session = this.getSessionFactory().openSession();
		PropertyOwner propertyOwner=new PropertyOwner();
		try{
			propertyOwner=(PropertyOwner) session.get(PropertyOwner.class,id);
	    }finally{
	        session.flush();
	        session.close();
	    }
		return propertyOwner;
	}
	public String sendArrearMessage(String sendMessge2, String phone) {
		String rtnMessage="";
		try {
			rtnMessage=smsSendInteface.sendMultiSms(sendMessge2, phone, "12212");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtnMessage;
	}
	public void findPropertyByPhone(Page<PropertyOwner> page, String sql,
			String sqlCount, Map paraMap) {
		try {
			this.pagingQuery(page, sql, sqlCount, paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void deleteNoticeById(String id) {
		Session session = this.getSessionFactory().openSession();
		String businessId="";
		MessageSendMain msg=null;
	    try{
	    	msg=(MessageSendMain) session.get(MessageSendMain.class,id);
	    	session.delete(msg);
	    }finally{
	        session.flush();
	        session.close();
	    }		
	}
}
