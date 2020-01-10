package com.jdry.pms.dir.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;

@Component
public class DirctoryComm {
	
	@Resource
	DirectoryService directoryService;

	@DataProvider
	public Map<Object, String> getDeleteId() {
	    Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
	    List<DirDirectoryDetail> lists = (List<DirDirectoryDetail>) getDirectory("ccccc");
	    for(DirDirectoryDetail dirDetail:lists){
	    	mapValue.put(dirDetail.getCode_detail(), dirDetail.getCode_detail_name());
	    }
	    mapValue.put(1, "Mister");
	    mapValue.put(0, "Mistress");
	    return mapValue;
	}
	
	@DataResolver
	public Map<Object, String> getDelete(String code) {
		System.out.println("code==="+code);
	    Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
	    mapValue.put(1, "Mister");
	    mapValue.put(0, "Mistress");
	    return mapValue;
	}
	
	/**根据设定编号，得到设定信息
	 * 
	 * @param code
	 * @return
	 */
	@DataProvider
	public Collection<DirDirectoryDetail> getDirectory(String code){
		
		return directoryService.getDirectory(code);
	}
	
	public String getDetailName(String code,String code_detail){
		
		return directoryService.getDetailName(code,code_detail);
	}
	
	/**从公用表格中取设定值，在前台做下拉框显示出来
	 * 
	 * @param code
	 * @return
	 */
	@DataProvider
	public Map<Object, String> getDirByCode(String code){
		
		 Map<Object, String> mapValue = new LinkedHashMap<Object, String>();
	    List<DirDirectoryDetail> lists = (List<DirDirectoryDetail>) getDirectory(code);
	    for(DirDirectoryDetail dirDetail:lists){
	    	mapValue.put(dirDetail.getCode_detail(), dirDetail.getCode_detail_name());
	    }
	    return mapValue;
	}

}
