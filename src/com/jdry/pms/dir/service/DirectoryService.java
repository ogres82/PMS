package com.jdry.pms.dir.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.dir.pojo.DirDirectory;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

@Repository
public interface DirectoryService {
	public Collection<DirDirectory> queryAll(Page<DirDirectory> page);
	public Map<Object, String> getDeleteId();
	public Collection<DirDirectoryDetail> getDirectory(String code);
	public void saveAll(Collection<DirDirectory> dirs);
	public void queryAllByPage(Page<DirDirectory> page) throws Exception;
	public String getDetailName(String code,String code_detail);
	public Collection<DirDirectoryDetail> getDirectoryLikeCode(String code);
	public List<DirDirectoryDetail> queryAll();
}
