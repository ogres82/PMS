package com.jdry.pms.wechatToApp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.HttpRequestUtil;
import com.jdry.pms.wechatToApp.dao.WechatKeyInfoDao;
import com.jdry.pms.wechatToApp.pojo.WechatKeyInfo;
import com.jdry.pms.wechatToApp.service.WechatService;

@Repository
@Component
public class WechatServiceImpl implements WechatService {

	@Resource
	WechatKeyInfoDao dao;

	@Override
	public String getWechatToken() {
		WechatKeyInfo wki = dao.getWechatKeyInfo().get(0);
		String token = "";
		String para = buildTokenPara();
		Date dt = new Date();
		Long time = dt.getTime();
		// 判断第一次获取token
		if (!"".equals(wki.getAccessToken()) || !"".equals(wki.getTokenGetTime())) {
			if ((time - wki.getTokenGetTime()) < wki.getExpiresIn()) {
				token = wki.getAccessToken();
			} else {
				JSONObject obj = JSON.parseObject(HttpRequestUtil.sendGet(wki.getUriGetToken(), para));
				token = obj.getString("access_token");
				setToken(token, time);
			}
		}
		return token;
	}

	
	public String buildTokenPara() {
		WechatKeyInfo wki = dao.getWechatKeyInfo().get(0);
		String para = "grant_type=" + wki.getGrantType() + "&appid=" + wki.getAppId() + "&secret=" + wki.getAppSecret();
		return para;
	}

	
	public void setToken(String accessToken, long tokenGetTime) {
		// TODO Auto-generated method stub
		WechatKeyInfo wki = dao.getWechatKeyInfo().get(0);
		wki.setAccessToken(accessToken);
		wki.setTokenGetTime(tokenGetTime);
		dao.saveOrupWechatKeyInfo(wki);
	}
	
	@Override
	public String getWechatMaterial(String token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "news");
		params.put("offset", 0);
		params.put("count", 10);
		
		WechatKeyInfo wki = dao.getWechatKeyInfo().get(0);
		String urls = wki.getUriBatchgetMaterial() + token;
		String json = HttpRequestUtil.sendPost(urls, params);
		return json;
	}
}
