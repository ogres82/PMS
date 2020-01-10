package com.jdry.pms.dir.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.dir.pojo.DirDirectory;
import com.jdry.pms.dir.service.DirectoryService;
import com.jdry.pms.dir.util.DirctoryComm;

@Component
public class DirectoryView {
	
	@Resource
	DirectoryService directoryService;
	
	@Resource
	DirctoryComm dirctoryComm;

	@DataProvider
	public Collection<DirDirectory> getAll(Page<DirDirectory> page) throws Exception {
		return directoryService.queryAll(page);
	}
	
	@DataProvider
	public void queryAllByPage(Page<DirDirectory> page) throws Exception{
		directoryService.queryAllByPage(page);
	}
	
	/**得到事件类型
	 * 
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getDeleteId() {
	    return dirctoryComm.getDirByCode("delete_id");
	}
	
	/**保存数据
	 * 
	 * @param dirs
	 */
	@DataResolver
	public void saveAll(Collection<DirDirectory> dirs) {
		directoryService.saveAll(dirs);
	}
}
