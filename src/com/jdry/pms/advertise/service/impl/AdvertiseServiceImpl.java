package com.jdry.pms.advertise.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.advertise.dao.AdvertiseAttachDao;
import com.jdry.pms.advertise.dao.AdvertisePositionDao;
import com.jdry.pms.advertise.pojo.AdevertiseModel;
import com.jdry.pms.advertise.pojo.AdvertiseAttachModel;
import com.jdry.pms.advertise.pojo.AdvertisePositionModel;
import com.jdry.pms.advertise.pojo.VAdvertisePositionModel;
import com.jdry.pms.advertise.service.AdvertiseService;
@Component
public class AdvertiseServiceImpl implements AdvertiseService {

	@Resource
	AdvertiseAttachDao attachDao;
	
	@Resource
	AdvertisePositionDao positionDao;
	
	@Override
	public void addAttachModel(AdvertiseAttachModel attachModel) {
		// TODO Auto-generated method stub
		attachDao.addAttachModel(attachModel);
	}

	@Override
	public void addPositionModel(AdvertisePositionModel positionModel) {
		// TODO Auto-generated method stub
		positionDao.addAttachModel(positionModel);
	}

	@Override
	public AdvertiseAttachModel getAttachModelByCode(String adv_code) {
		// TODO Auto-generated method stub
		return attachDao.getAttachModelByCode(adv_code);
	}
	
	@Override
	public AdvertiseAttachModel getAttachModelById(String id) {
		// TODO Auto-generated method stub
		return attachDao.getAttachModelById(id);
	}

	@Override
	public AdvertisePositionModel getPositionModelById(String adv_code) {
		// TODO Auto-generated method stub
		return positionDao.getPositionModelById(adv_code);
	}

	@Override
	public void getAdvertiseAll(Page<AdevertiseModel> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		attachDao.getAdvertiseAll(page,parameter,criteria);
	}

	@Override
	public void getAdvPositionAll(Page<VAdvertisePositionModel> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		positionDao.getAdvPositionAll(page, parameter, criteria);
	}

	@Override
	public void deleteAdvPositionByCode(String adv_code) {
		// TODO Auto-generated method stub
		positionDao.deleteAdvPositionByCode(adv_code);
	}

	@Override
	public List<AdvertisePositionModel> getAllPosition() {
		// TODO Auto-generated method stub
		return positionDao.getAllPosition();
	}

	@Override
	public void deleteAdvAttachById(String id) {
		// TODO Auto-generated method stub
		attachDao.deleteAdvPositionByCode(id);
	}

	@Override
	public List<VAdvertisePositionModel> queryAll() {
		// TODO Auto-generated method stub
		return positionDao.queryAll();
	}

	@Override
	public List<AdevertiseModel> queryAllAdvAttach() {
		// TODO Auto-generated method stub
		return attachDao.queryAllAdvAttach();
	}

}
