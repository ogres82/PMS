package com.jdry.pms.wechatToApp.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.wechatToApp.pojo.WechatMatterEntity;
@Repository
public interface WechatToAppService {
	public List<WechatMatterEntity> getMatterInfo();	

	public WechatMatterEntity getWechatMatterById(String thumbMediaId,String mediaId);
	
	public String getMatterInfoForApp(String data);

	public void syncMatterInfo();

	public void setPublish(String thumbMediaId,String mediaId);

	public void delMatterInfo(String data);
}
