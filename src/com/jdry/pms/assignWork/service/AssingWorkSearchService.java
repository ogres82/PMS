package com.jdry.pms.assignWork.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * 描述：综合查询
 * @author hezuping
 *  2016年7月13日15:20:45
 */
@Component
public interface AssingWorkSearchService
{
	public void assignWorkSearch(Page page,Map<String, Object> parameter, Object object,Criteria criteria) throws Exception;
}
