package com.jdry.pms.wechatToApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.wechatToApp.dao.WechatToAppDao;
import com.jdry.pms.wechatToApp.pojo.WechatMatterEntity;
import com.jdry.pms.wechatToApp.service.WechatToAppService;
import com.jdry.pms.wechatToApp.service.WechatService;

@Repository
@Component
public class WechatToAppServiceImpl implements WechatToAppService {

	@Resource
	WechatToAppDao dao;

	@Resource
	WechatService service;

	@Override
	public List<WechatMatterEntity> getMatterInfo() {
		// TODO Auto-generated method stub
		return dao.getMatterInfo("",0);
	}

	@Override
	public void syncMatterInfo() {
		// TODO Auto-generated method stub
		String token = service.getWechatToken();
		JSONObject json = JSON.parseObject(service.getWechatMaterial(token));
		List<JSONObject> item = (List<JSONObject>) json.get("item");
		System.out.println(json);
		for (int i = 0; i < item.size(); i++) {
			String mediaId = item.get(i).getString("media_id");
			long updateTime = item.get(i).getLongValue("update_time");
			JSONObject content = (JSONObject) item.get(i).get("content");
			long createTime = content.getLongValue("create_time");
			List<JSONObject> newItem = (List<JSONObject>) content.get("news_item");
			for (int j = 0; j < newItem.size(); j++) {
				String thumbMediaId = newItem.get(j).getString("thumb_media_id");
				String title = newItem.get(j).getString("title");
				String digest = newItem.get(j).getString("digest");
				String url = newItem.get(j).getString("url");
				String thumbUrl = newItem.get(j).getString("thumb_url");
				String publish = "0";
				WechatMatterEntity wme = getWechatMatterById(thumbMediaId,mediaId);
				if ("".equals(wme) || wme == null) {
					wme = new WechatMatterEntity();
					wme.setThumbMediaId(thumbMediaId);
					wme.setMediaId(mediaId);
					wme.setPublish(publish);
				}
				wme.setCreateTime(createTime);
				wme.setDigest(digest);
				wme.setThumbUrl(thumbUrl);
				wme.setTitle(title);
				wme.setUpdateTime(updateTime);
				wme.setUrl(url);
				dao.syncMatterInfo(wme);
			}
		}

	}

	@Override
	public void setPublish(String thumbMediaId,String mediaId) {
		// TODO Auto-generated method stub
		WechatMatterEntity wme = getWechatMatterById(thumbMediaId,mediaId);
		if (!"".equals(wme) && wme != null) {
			dao.setPublish(thumbMediaId,mediaId,"1");
		}
	}

	@Override
	public void delMatterInfo(String data) {
		// TODO Auto-generated method stub
		dao.delMatterInfo(data);
	}

	@Override
	public String getMatterInfoForApp(String data) {
		// TODO Auto-generated method stub
		if(dao == null){
			dao = (WechatToAppDao) SpringUtil.getObjectFromApplication("wechatToAppDao");
		}
		List<WechatMatterEntity> list = dao.getMatterInfo("1", 5);
		if (list != null && list.size() > 0) {			
			String jsonString = JSON.toJSONString(list);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":"+jsonString+"}";
		}else {

			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}		
	}

	@Override
	public WechatMatterEntity getWechatMatterById(String thumbMediaId,String mediaId) {
		// TODO Auto-generated method stub
		return dao.getWechatMatterById(thumbMediaId,mediaId);
	}

}
