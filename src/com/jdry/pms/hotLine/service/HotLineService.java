package com.jdry.pms.hotLine.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.hotLine.pojo.HotLine;

@Repository
public interface HotLineService {
	
	public String getHotLineOfApp(String data);//app接口
	
	public List<HotLine> getHotLineList();//获取热线电话列表
	
	public void delHotLineInfo(String Id);//根据Id删除热线电话
	
	public boolean saveHotLineInfo(HotLine hotline);//新增或者便捷热线电话
	
}
