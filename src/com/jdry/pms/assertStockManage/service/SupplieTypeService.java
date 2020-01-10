package com.jdry.pms.assertStockManage.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

/**
 * 物资类别接口
 * @author hezuping
 * @date 2016-01-06 20:21:56
 */
@Repository
public interface SupplieTypeService {
	/**
	 * 查询物资信息
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @return
	 */
	public List querySuppliesInfo(Page<SupplieType> page,Map<String, Object> parameter,Criteria criteria);
	/**
	 * 物资类别的增加删除修改操作
	 * @param supplieTypes
	 */
	public String saveSupplieType(SupplieType supplieType,String stat);
	/**
	 * 根据条件查询
	 * @param parameter
	 * @return
	 */
	public SupplieType getSupplieTypeInfo(Map<String, Object> parameter);
	
	/**
	 * 根据条件删除数据
	 * @param id
	 * @return
	 */
	public boolean deleteSupplieTypeById(String id);
	/**
	 * 查询所有的数据
	 * @return
	 */
	public  Collection<SupplieType> querySuppTypeList();
	
	public Collection<DirDirectoryDetail> getDirectoryLikeCode(String code);
}
