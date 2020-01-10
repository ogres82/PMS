package com.jdry.pms.msgandnotice.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.comm.util.FileUtil;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.contract.pojo.VContractAttachment;
import com.jdry.pms.contract.service.ContractInfoService;
import com.jdry.pms.msgandnotice.pojo.AppAuditInfo;
import com.jdry.pms.msgandnotice.pojo.AppNotice;
import com.jdry.pms.msgandnotice.pojo.AppNoticeFullContent;
import com.jdry.pms.msgandnotice.pojo.AppPicture;
import com.jdry.pms.msgandnotice.pojo.AppResult;
import com.jdry.pms.msgandnotice.pojo.MsgandNoticePicture;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.AuditInfoService;
import com.jdry.pms.msgandnotice.service.MsgAndNoticeConstant;
import com.jdry.pms.msgandnotice.service.NoticeService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.msgpublic.pojo.AppMsgPubAttach;
import com.jdry.pms.msgpublic.pojo.AppMsgpubMain;
import com.jdry.pms.msgpublic.pojo.MsgPubMain;
import com.jdry.pms.msgpublic.service.MsgPubService;
import com.jdry.pms.system.pojo.PrivilegeConfig;
import com.jdry.pms.system.service.PrivilegeConfigService;

@Repository
@Component
public class AppInteface {
	@Resource
	MsgPubService  msgPubService;
	@Resource
    public NoticeService noticeService;
	@Resource
    public IUserService userService;
	@Resource
	private AuditInfoService auditInfoService;
	@Resource
	ContractInfoService contractInfoService;
	@Resource
	PrivilegeConfigService configService;
	//app获取最新的一条公告
	public String getLastesdNotice(String defaultStr){
		AppResult ar=new AppResult();
		String status="1";
		String message="获取数据成功";
		String jsonString="";
		Page<MsgandnotNoticeMain> page =new Page<MsgandnotNoticeMain>(3, 1);
		try{
			if(noticeService == null){
				noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
			}
			noticeService.getAllPubNotice(page, null, null);
			List<MsgandnotNoticeMain> result=(List<MsgandnotNoticeMain>) page.getEntities();
			MsgandnotNoticeMain notice=new MsgandnotNoticeMain();
			AppNotice an=new AppNotice();
			if(result!=null&&result.size()>0){
				 notice=result.get(0);
				 an.setId(notice.getNtcId());
				 String shortContentFull=notice.getNtcContent().replaceAll("</?[^>]+>", "");
				 int length=shortContentFull.length();
				 length=length>11?11:length;
				 String shortContent=shortContentFull.substring(0, length);
				 an.setShortContent(shortContent);
				 an.setPubTime(DateUtil.convertDateToString(notice.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 an.setTitle(notice.getNtcSubject());
			}
			 jsonString = JSON.toJSONString(an);
		}catch(Exception e){
			status="0";
			e.printStackTrace();
			message="获取数据失败";
		}
		ar.setStatus(status);
		ar.setMessage(message);
		ar.setData(jsonString);
		String rtnStr=JSON.toJSONString(ar);
		return rtnStr;
		
	}
	//返回最近一个月的公告列表
	public String getLastesdMonthNotice(String deafaultStr){
		AppResult ar=new AppResult();
		String status="1";
		String message="获取数据成功";
		String jsonString="";
		Page<MsgandnotNoticeMain> page =new Page<MsgandnotNoticeMain>(200, 1);
		try{
			if(noticeService == null){
				noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
			}
			noticeService.getLastMonthNotice(page, null, null);
			List<MsgandnotNoticeMain> result=(List<MsgandnotNoticeMain>) page.getEntities();
			List<AppNotice> appNoticeList=new ArrayList<AppNotice>();
			for(MsgandnotNoticeMain msgandnotNoticeMain:result){
				 AppNotice an=new AppNotice();
				 an.setId(msgandnotNoticeMain.getNtcId());
				 String shortContentFull=msgandnotNoticeMain.getNtcContent().replaceAll("</?[^>]+>", "");
				 int length=shortContentFull.length();
				 String shortContent="";
				 if(length>=12){
					 length=11;
				 }else{
					 length-=1;
				 }
				 if(length>0){
					 shortContent=shortContentFull.substring(0, length);
				 }
				 an.setShortContent(shortContent);
				 an.setPubTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 an.setTitle(msgandnotNoticeMain.getNtcSubject());
				 an.setDept(msgandnotNoticeMain.getNtcDept());
				 an.setContent(msgandnotNoticeMain.getNtcContent());
				 appNoticeList.add(an);
			}
			 jsonString = JSON.toJSONString(appNoticeList);
		}catch(Exception e){
			status="0";
			message="获取数据失败";
		}
		ar.setStatus(status);
		ar.setMessage(message);
		ar.setData(jsonString);
		String rtnStr=JSON.toJSONString(ar);
		return rtnStr;
		
	}
	//根据公告ID获取公告具体内容
	public String getNoticeById(String noticeId){
		JSONObject obj=JSON.parseObject(noticeId);
		String ntcId = obj.getString("NoticeId");
		MsgandnotNoticeMain msgandnotNoticeMain=new MsgandnotNoticeMain();
		System.out.println(ntcId);
		AppResult ar=new AppResult();
		String status="1";
		String message="获取数据成功";
		String jsonString="";
		try{
			if(noticeService == null){
				noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
			}
			if(ntcId.startsWith("GG")){
				msgandnotNoticeMain=noticeService.getNoticeByNo(ntcId);
			}else{
				msgandnotNoticeMain=noticeService.getNoticeById(ntcId);
			}
		     AppNoticeFullContent an=new AppNoticeFullContent();
			 String ntcContent=msgandnotNoticeMain.getNtcContent();
			 String rge2="<img[^<>]*?\\ssrc=['\"]?(.*?)['\"].*?>";//去掉img标签的正则表达式
			 //String ntcContent1=ntcContent.replaceAll(rge2, "");
			 String ntcContent1=ntcContent;
			 an.setContent(ntcContent1);
			 an.setPubTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			 an.setTitle(msgandnotNoticeMain.getNtcSubject());
			 an.setDeptName(msgandnotNoticeMain.getNtcDept());
			 an.setStatus(msgandnotNoticeMain.getNtcStatus());
			 an.setCreateTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			 List<AppAuditInfo> aaiList=new ArrayList<AppAuditInfo>();
			 List<AppPicture> picList=new ArrayList<AppPicture>();
			 for (MsgandnoticeNoticeAuditinfo msgAuinfo: msgandnotNoticeMain.getMsgandnoticeNoticeAuditinfos()){
				 AppAuditInfo aai=new AppAuditInfo();
				 aai.setAudior(msgAuinfo.getNtcAuditor());
				 aai.setAuditContent(msgAuinfo.getNtcAuditContnt());
				 aai.setAuditTime(DateUtil.convertDateToString(msgAuinfo.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 aaiList.add(aai);
			 }
			 for(MsgandNoticePicture pics:msgandnotNoticeMain.getMsgandnoticePics()){
				 AppPicture appPic=new AppPicture();
				 appPic.setFilePath(pics.getFilePath());
				 picList.add(appPic);
			 }
			 an.setUrl(picList);
			 an.setAuditInfos(aaiList);
			 jsonString = JSON.toJSONString(an);
		}catch(Exception e){
			e.printStackTrace();
			status="0";
			message="获取数据失败！";
		}
		ar.setStatus(status);
		ar.setMessage(message);
		ar.setData(jsonString);
		String rtnStr=JSON.toJSONString(ar);
		return rtnStr;
	}
	//获取待我审核的公告
	public String getMyAuditNoticeList(String userInfo){
		String rtnPosiId="";
		AppResult ar=new AppResult();
		String status="1";
		String message="获取数据成功";
		String jsonString="";
		Page<MsgandnotNoticeMain> page =new Page<MsgandnotNoticeMain>(200, 1);
		try{
			if(noticeService == null){
				noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
			}
			List<MsgandnotNoticeMain> result=noticeService.getAllNotice();
			List<AppNotice> appNoticeList=new ArrayList<AppNotice>();
			for(MsgandnotNoticeMain msgandnotNoticeMain:result){
				 AppNotice an=new AppNotice();
				 an.setId(msgandnotNoticeMain.getNtcId());
				 String shortContentFull=msgandnotNoticeMain.getNtcContent().replaceAll("</?[^>]+>", "");
				 int length=shortContentFull.length();
				 String shortContent="";
				 if(length>=12){
					 length=11;
				 }else{
					 length-=1;
				 }
				 if(length>0){
					  shortContent=shortContentFull.substring(0, length);
				 }
				 an.setShortContent(shortContent);
				 an.setPubTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 an.setLastUpdateTime(DateUtil.convertDateToString(msgandnotNoticeMain.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
				 an.setTitle(msgandnotNoticeMain.getNtcSubject());
				 an.setStatus(msgandnotNoticeMain.getNtcStatus());
				 an.setCreator(msgandnotNoticeMain.getNtcCreator());
				 an.setCreatorId(msgandnotNoticeMain.getNtcCreatorId());
				 appNoticeList.add(an);
			}
			jsonString = JSON.toJSONString(appNoticeList);			
		}catch(Exception e){
			e.printStackTrace();
			status="0";
			message="获取数据失败！";
		}
		ar.setStatus(status);
		ar.setMessage(message);
		ar.setData(jsonString);
		ar.setPosiId(rtnPosiId);
		String rtnStr=JSON.toJSONString(ar);
		return rtnStr;
		
	}
	/*
	 * 获取所有的消息（消息公开）
	 */
	public String getAllPubMsg(String userInfo){
		String rtnPosiId="";
		AppResult ar=new AppResult();
		String status="1";
		String message="获取数据成功";
		String jsonString="";
		try{ 
			if(msgPubService == null){
				msgPubService = (MsgPubService) SpringUtil.getObjectFromApplication("msgPubServiceImpl");
			}
			
			List<MsgPubMain> result=msgPubService.getAllNotice();
			List<AppNotice> appNoticeList=new ArrayList<AppNotice>();
			for(MsgPubMain msgPubMain:result){
				 AppNotice an=new AppNotice();
				 an.setId(msgPubMain.getNtcId());
				 String shortContentFull=msgPubMain.getNtcContent().replaceAll("</?[^>]+>", "");
				 int length=shortContentFull.length();
				 String shortContent="";
				 if(length>=12){
					 length=11;
				 }else{
					 length-=1;
				 }
				 if(length>0){
					  shortContent=shortContentFull.substring(0, length);
				 }
				 an.setPubTime(DateUtil.convertDateToString(msgPubMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 an.setTitle(msgPubMain.getNtcSubject());
				 an.setStatus(msgPubMain.getNtcStatus());
				 an.setCreator(msgPubMain.getNtcCreatorId());
				 an.setDept(msgPubMain.getNtcDept());
				 appNoticeList.add(an);
			}
			jsonString = JSON.toJSONString(appNoticeList);			
		}catch(Exception e){
			e.printStackTrace();
			status="0";
			message="获取数据失败！";
		}
		ar.setStatus(status);
		ar.setMessage(message);
		ar.setData(jsonString);
		ar.setPosiId(rtnPosiId);
		String rtnStr=JSON.toJSONString(ar);
		return rtnStr;
		
	}
	/*
	 * 查看消息公开具体内容
	 */
	public String viewMsgpubById(String msgpubId) throws UnsupportedEncodingException{
		JSONObject obj=JSON.parseObject(msgpubId);
		String ntcId = obj.getString("msgpubId");
		MsgPubMain msgPubMain=new MsgPubMain();
		AppResult ar=new AppResult();
		String status="1";
		String message="获取数据成功";
		String jsonString="";
		try{
			if(msgPubService == null){
				msgPubService = (MsgPubService) SpringUtil.getObjectFromApplication("msgPubServiceImpl");
			}
			if(contractInfoService==null){
				contractInfoService = (ContractInfoService) SpringUtil.getObjectFromApplication("contractInfoServiceImpl");
			}
			 AppMsgpubMain an=new AppMsgpubMain();
			 msgPubMain=msgPubService.getNoticeById(ntcId);
			 List<VContractAttachment> attr = new ArrayList<VContractAttachment>();
			 attr = contractInfoService.getContractAttachmentById(ntcId);
			 List<AppMsgPubAttach> attachs=new ArrayList<AppMsgPubAttach>();
			 for(int i=0;i<attr.size();i++){
				 String ipAddr=FileUtil.getProperties("com/jdry/pms/msgandnotice/ApplicationResources.properties").getProperty("ipaddr");
				 String fileName=attr.get(i).getFileName();
				 String fileId=attr.get(i).getFileId();
				 String filePath=ipAddr+"msgpub/attrdown.app?method=downloadAttr&fileId="+fileId;
				 AppMsgPubAttach attach=new AppMsgPubAttach();
				 attach.setFileName(fileName);
				 attach.setFilePath(filePath);
				 attachs.add(attach);
			 }
			 an.setMsgSubject(msgPubMain.getNtcSubject());
			 an.setDept(msgPubMain.getNtcDept());
			 String pubtime=DateUtil.convertDateToString(msgPubMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss");
			 an.setPubTime(pubtime);
			 an.setCreator(msgPubMain.getNtcCreator());
			 an.setAttach(attachs);
			 jsonString = JSON.toJSONString(an);
		}catch(Exception e){
			e.printStackTrace();
			status="0";
			message="获取数据失败！";
		}
		ar.setStatus(status);
		ar.setMessage(message);
		ar.setData(jsonString);
		String rtnStr=JSON.toJSONString(ar);
		return rtnStr;
	}
	//审核公告
		public String auditNoticeById(String auditInfo) throws UnsupportedEncodingException{
			JSONObject obj=JSON.parseObject(auditInfo);
			String ntcId = obj.getString("NoticeId");
			String auditor= obj.getString("auditor");
			String passflag= obj.getString("passflag");
			String auditContent=obj.getString("auditContent");
			String auditContent1=CharacterUtil.getUTF_8String(auditContent);
			MsgandnotNoticeMain notice=new MsgandnotNoticeMain();
			System.out.println(ntcId);
			AppResult ar=new AppResult();
			String status="1";
			String message="获取数据成功";
			String jsonString="";
			try{
				if(noticeService == null){
					noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
				}
				if(auditInfoService == null){
					auditInfoService = (AuditInfoService) SpringUtil.getObjectFromApplication("auditInfoServiceImpl");
				}
				if(userService==null){
					userService=(IUserService)SpringUtil.getObjectFromApplication("bdf2.userService");
				}
				if(ntcId.startsWith("GG")){
					notice=noticeService.getNoticeByNo(ntcId);
				}else{
					notice=noticeService.getNoticeById(ntcId);
				}
				MsgandnoticeNoticeAuditinfo mna=new MsgandnoticeNoticeAuditinfo();
				DefaultUser user=(DefaultUser) userService.loadUserByUsername(auditor);
	        	mna.setNtcAuditor(user.getCname());
	        	mna.setNtcAuditorId(auditor);
	        	mna.setNtcAuditContnt(auditContent1);
	        	mna.setNtcCreateTime(new Date());
	        	mna.setNtcNoticeId(ntcId);
	        	mna.setNtcPassFlag(passflag);
				noticeService.updateSingleNotice(notice,passflag,user.getCname());
	        	auditInfoService.saveSingleInfo(mna);
			}catch(Exception e){
				//e.printStackTrace();
				status="1";
				message="获取数据成功";
			}
			ar.setStatus(status);
			ar.setMessage(message);
			ar.setData(jsonString);
			String rtnStr=JSON.toJSONString(ar);
			return rtnStr;
		}
		//获取待发布的以及发布了的公告
			public String getMyPubNotice(String auditInfo) throws UnsupportedEncodingException{
				String rtnPosiId="";
				AppResult ar=new AppResult();
				String status="1";
				String message="获取数据成功";
				String jsonString="";
				Page<MsgandnotNoticeMain> page =new Page<MsgandnotNoticeMain>(200, 1);
				try{
					if(noticeService == null){
						noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
					}
					
					List<MsgandnotNoticeMain> result=noticeService.getAllNotice();
					List<AppNotice> appNoticeList=new ArrayList<AppNotice>();
					for(MsgandnotNoticeMain msgandnotNoticeMain:result){
						 AppNotice an=new AppNotice();
						 an.setId(msgandnotNoticeMain.getNtcId());
						 String shortContentFull=msgandnotNoticeMain.getNtcContent().replaceAll("</?[^>]+>", "");
						 int length=shortContentFull.length();
						 String shortContent="";
						 if(length>=12){
							 length=11;
						 }else{
							 length-=1;
						 }
						 if(length>0){
							 shortContent=shortContentFull.substring(0, length);
						 }
						 an.setShortContent(shortContent);
						 an.setPubTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						 an.setLastUpdateTime(DateUtil.convertDateToString(msgandnotNoticeMain.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
						 an.setTitle(msgandnotNoticeMain.getNtcSubject());
						 an.setStatus(msgandnotNoticeMain.getNtcStatus());
						 an.setCreator(msgandnotNoticeMain.getNtcCreator());
						 an.setCreatorId(msgandnotNoticeMain.getNtcCreatorId());
						 appNoticeList.add(an);
					}
					jsonString = JSON.toJSONString(appNoticeList);			
				}catch(Exception e){
					e.printStackTrace();
					status="0";
					message="获取数据失败！";
				}
				ar.setStatus(status);
				ar.setMessage(message);
				ar.setData(jsonString);
				ar.setPosiId(rtnPosiId);
				String rtnStr=JSON.toJSONString(ar);
				return rtnStr;
				
			}
			//发布公告
			public String pubNotice(String auditInfo) throws UnsupportedEncodingException{
				JSONObject obj=JSON.parseObject(auditInfo);
				String userId = obj.getString("userId");
				String ntcId = obj.getString("noticeId");
				System.out.println(ntcId);
				AppResult ar=new AppResult();
				String status="1";
				String message="获取数据成功";
				String jsonString="";
				try{
					if(noticeService == null){
						noticeService = (NoticeService) SpringUtil.getObjectFromApplication("noticeServiceImpl");
					}
					if(userService==null){
						userService=(IUserService)SpringUtil.getObjectFromApplication("bdf2.userService");
					}
					DefaultUser user=(DefaultUser) userService.loadUserByUsername(userId);
					MsgandnotNoticeMain notice=noticeService.getNoticeById(ntcId);
					noticeService.updateSingleNotice(notice,null,user.getCname());
					
				}catch(Exception e){
					e.printStackTrace();
					status="0";
					message="获取数据失败";
				}
				ar.setStatus(status);
				ar.setMessage(message);
				ar.setData(jsonString);
				String rtnStr=JSON.toJSONString(ar);
				return rtnStr;
			}
	   public String getAuditNoticeStr(Page page,String type,String posiId,String userId){
		  if(type.equals(MsgAndNoticeConstant.AUDIT_TYPE_WYFZ)){
			   noticeService.getWyfzAuditNotice(page, null, null,userId);
		  }else{
			noticeService.getWylzAuditNotice(page, null, null,userId);
		  }
		List<MsgandnotNoticeMain> result=(List<MsgandnotNoticeMain>) page.getEntities();
		List<AppNotice> appNoticeList=new ArrayList<AppNotice>();
		String jsonString ="";
		for(MsgandnotNoticeMain msgandnotNoticeMain:result){
			 AppNotice an=new AppNotice();
			 an.setId(msgandnotNoticeMain.getNtcId());
			 String shortContentFull=msgandnotNoticeMain.getNtcContent().replaceAll("</?[^>]+>", "");
			 int length=shortContentFull.length();
			 String shortContent="";
			 if(length>=12){
				 length=11;
			 }else{
				 length-=1;
			 }
			 if(length>0){
				  shortContent=shortContentFull.substring(0, length);
			 }
			 an.setShortContent(shortContent);
			 an.setPubTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcPublishTime(), "yyyy-MM-dd HH:mm:ss"));
			 an.setTitle(msgandnotNoticeMain.getNtcSubject());
			 an.setStatus(msgandnotNoticeMain.getNtcStatus());
			 an.setCreator(msgandnotNoticeMain.getNtcCreator());
			 an.setCreateTime(DateUtil.convertDateToString(msgandnotNoticeMain.getNtcCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			 an.setPassFlag(msgandnotNoticeMain.getPassFlag());
			 String auditer="";
			 auditer=noticeService.getauditer(type,posiId);
			 an.setAuditer(auditer);
			 List btnList=new ArrayList();
			 appNoticeList.add(an);
		}
		 jsonString = JSON.toJSONString(appNoticeList);
	     return jsonString;
	}
	   
	public String getPrivilegePosition(String data){
		if(configService == null){
			configService = (PrivilegeConfigService) SpringUtil.getObjectFromApplication("privilegeServiceImpl");
		}
		List<PrivilegeConfig> list = configService.loadPrivilegeConfig();
		return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":"+JSON.toJSONString(list)+"}";
	}
}
