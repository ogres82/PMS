package com.jdry.pms.camera.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.camera.pojo.CameraInfo;
import com.jdry.pms.comm.util.PageUtil;

@Repository	
public interface CameraService {
	/**
	 * 查询监控列表
	 * 
	 * @param pageUtil
	 * @param parameter
	 */
	void queryCameraList(PageUtil<?> pageUtil, Map<String, String> parameter);

	public String saveorUpdateCamera(CameraInfo cam);

	String deleteCamera(CameraInfo cam);

	List<CameraInfo> getCameraList(Map<String, Object> param);

	List<CameraInfo> getCameraListByCommunityId(Map<String, String> parameter);

}
