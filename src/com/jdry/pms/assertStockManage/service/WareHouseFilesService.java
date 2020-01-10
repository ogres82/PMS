package com.jdry.pms.assertStockManage.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.WareHouseFiles;

@Repository
public interface WareHouseFilesService 
{
    /**
     * 查询仓库档案信息
     * @param page
     * @param parameter
     * @param criteria
     */
	public void quaryWareHouseFilesInfo(Page<WareHouseFiles> page,Map<String, Object> parameter,Criteria criteria);
	/**
	 * 编辑、修改，添加仓库信息
	 * @param wareHouseFiles
	 */
	public void saveWareHouseFilesInfo(Collection<WareHouseFiles> wareHouseFiles);
	/**
	 * 查询信息
	 * @param map
	 * @return
	 */
	public WareHouseFiles findWareHouseFilesByCode(Map map);
	/**
	 * 删除信息
	 * @param warehouse_code
	 * @return
	 */
	public boolean deleteSupplieInfoById(String warehouse_code);
	/**
	 * 获取所属库存下拉框使用
	 * @return
	 */
	public Collection<WareHouseFiles> queryWarHouesInfos();
}
