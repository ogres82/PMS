package com.jdry.pms.advertise.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.advertise.pojo.AdevertiseModel;
import com.jdry.pms.advertise.pojo.AdvertiseAttachModel;
import com.jdry.pms.advertise.pojo.AdvertisePositionModel;
import com.jdry.pms.advertise.pojo.VAdvertisePositionModel;

@Component
public interface AdvertiseService {
	public void addAttachModel(AdvertiseAttachModel attachModel);
	public void addPositionModel(AdvertisePositionModel positionModel);
	
	public AdvertiseAttachModel getAttachModelByCode(String adv_code);
	public AdvertiseAttachModel getAttachModelById(String id);
	public AdvertisePositionModel getPositionModelById(String adv_code);
	
	public void getAdvertiseAll(Page<AdevertiseModel> page,Map<String, Object> parameter,Criteria criteria) throws Exception;
	public void getAdvPositionAll(Page<VAdvertisePositionModel> page,Map<String, Object> parameter,Criteria criteria) throws Exception;
	
	public void deleteAdvPositionByCode(String adv_code);
	
	public List<AdvertisePositionModel> getAllPosition();
	
	public void deleteAdvAttachById(String id);
	public List<VAdvertisePositionModel> queryAll();
	public List<AdevertiseModel> queryAllAdvAttach();
}
