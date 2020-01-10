package com.jdry.pms.camera.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.camera.dao.CameraDao;
import com.jdry.pms.camera.pojo.CameraInfo;
import com.jdry.pms.camera.service.CameraService;
import com.jdry.pms.comm.util.PageUtil;

@Repository
@Component
public class CameraServiceImpl implements CameraService {
	@Resource
	CameraDao cameraDao;

	@Override
	public void queryCameraList(PageUtil<?> pageUtil, Map<String, String> parameter) {
		cameraDao.queryCameraList(pageUtil, parameter);
	}

	@Override
	public String saveorUpdateCamera(CameraInfo cam) {
		return cameraDao.saveorUpdateCamera(cam);	
	}

	@Override
	public String deleteCamera(CameraInfo cam) {
		return cameraDao.deleteCamera(cam);
	}

	@Override
	public List<CameraInfo> getCameraList(Map<String, Object> param) {
		return cameraDao.getCameraList(param);
	}

	@Override
	public List<CameraInfo> getCameraListByCommunityId(Map<String, String> parameter) {
		return cameraDao.getCameraListByCommunityId(parameter);
	}

}
