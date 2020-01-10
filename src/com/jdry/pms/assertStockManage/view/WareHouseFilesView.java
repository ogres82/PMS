package com.jdry.pms.assertStockManage.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.WareHouseFiles;
import com.jdry.pms.assertStockManage.service.WareHouseFilesService;
/**
 * 仓库档案信息
 * @author hezuping
 *
 */
@Component
public class WareHouseFilesView
{
	@Resource
	WareHouseFilesService wareHouseFilesService;
	/**
	 * 获取仓库档案信息
	 * @param parameter
	 * @param page
	 * @param criteria
	 */
	@DataProvider
	public void quaryWareHouseFilesInfo(Map<String, Object> parameter,Page page,Criteria criteria)
	{
		wareHouseFilesService.quaryWareHouseFilesInfo(page, parameter, criteria);
		
	}
	/**
	 * 操作仓库档案信息
	 * @param wareHouseFiles
	 */
	@DataResolver
	public void saveWareHouseFilesInfo(Collection<WareHouseFiles> wareHouseFiles)
	{
		wareHouseFilesService.saveWareHouseFilesInfo(wareHouseFiles);
	}

}
