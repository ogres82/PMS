package com.jdry.pms.system.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.Url;
@Repository
public interface UrlService {

	public void saveUrl(Url url);
	public int countChildren(String parentId);
	public void deleteUrl(String id);
	public List<Url> loadMeunUrls(String roleId);
	public List<Url> loadAllMeunUrls();
	public List<Url> loadAllBuildMeunUrls();//查询所有菜单并建立父子关系
	public List<Url> loadRoleMeunUrls(String roleId);//未建立父子关系的集合
}
