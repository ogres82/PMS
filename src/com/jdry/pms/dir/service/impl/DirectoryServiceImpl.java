package com.jdry.pms.dir.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.dir.dao.DirectoryDao;
import com.jdry.pms.dir.pojo.DirDirectory;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
@Repository
@Component
public class DirectoryServiceImpl implements DirectoryService{

	@Resource
	DirectoryDao directory;
	
	@Override
	public Collection<DirDirectory> queryAll(Page<DirDirectory> page) {
		// TODO Auto-generated method stub
		return directory.queryAll(page);
	}

	@Override
	public Map<Object, String> getDeleteId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DirDirectoryDetail> getDirectory(String code) {
		// TODO Auto-generated method stub
		return directory.getDirectory(code);
	}

	@Override
	public void saveAll(Collection<DirDirectory> dirs) {
		// TODO Auto-generated method stub
		directory.saveAll(dirs);
	}

	@Override
	public void queryAllByPage(Page<DirDirectory> page) throws Exception {
		// TODO Auto-generated method stub
		directory.queryAllByPage(page);
	}

	@Override
	public String getDetailName(String code, String code_detail) {
		// TODO Auto-generated method stub
		return directory.getDetailName(code,code_detail);
	}
	
	@Override
	public Collection<DirDirectoryDetail> getDirectoryLikeCode(String code) {
		// TODO Auto-generated method stub
		return directory.getDirectoryLikeCode(code);
	}

	@Override
	public List<DirDirectoryDetail> queryAll() {
		return directory.queryAll();
	}

}
