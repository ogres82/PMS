package com.jdry.pms.houseWork.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;

/**
 * 家政查询
 * @author hezuping
 *
 */
@Repository
public interface EventSearchService
{

	public List queryHouseWorkEvent(Page page,Map<String, Object> parameter, Object object);
	
}
