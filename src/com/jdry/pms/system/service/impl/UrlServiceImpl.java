package com.jdry.pms.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.Url;
import com.jdry.pms.system.dao.UrlDao;
import com.jdry.pms.system.service.UrlService;
@Repository
@Component
public class UrlServiceImpl implements UrlService {

	@Resource
	UrlDao dao;
	
	@Override
	public void saveUrl(Url url) {
		// TODO Auto-generated method stub
		dao.saveUrl(url);
	}

	@Override
	public int countChildren(String parentId) {
		// TODO Auto-generated method stub
		return dao.countChildren(parentId);
	}

	@Override
	public void deleteUrl(String id) {
		// TODO Auto-generated method stub
		dao.deleteUrl(id);
	}

	@Override
	public List<Url> loadMeunUrls(String roleId) {
		return dao.loadMeunUrls(roleId);
	}

	@Override
	public List<Url> loadAllMeunUrls() {
		return dao.loadAllMeunUrls();
	}

	@Override
	public List<Url> loadRoleMeunUrls(String roleId) {
		return dao.loadRoleMeunUrls(roleId);
	}

	@Override
	public List<Url> loadAllBuildMeunUrls() {
		return dao.loadAllBuildMeunUrls();
	}

}
