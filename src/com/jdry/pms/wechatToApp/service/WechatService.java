package com.jdry.pms.wechatToApp.service;

import org.springframework.stereotype.Repository;

@Repository
public interface WechatService {
	public String getWechatToken();

	public String getWechatMaterial(String token);
}
