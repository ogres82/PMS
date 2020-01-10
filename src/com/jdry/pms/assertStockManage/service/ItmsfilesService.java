package com.jdry.pms.assertStockManage.service;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assertStockManage.pojo.SupplieType;
import com.jdry.pms.assertStockManage.pojo.Titmsfiles;
/**
 * 描述：物品档案信息
 * @author hezuping
 *
 */
@Repository
public interface ItmsfilesService 
{
	
  public List quaryItmsfilesInfo(Page<Titmsfiles> page,Map<String, Object> parameter,Criteria criteria);
  
  public String  saveItmsfilesInfo(Collection<Titmsfiles> itemfiles);

  public Titmsfiles getItmsFiles(Map<String, Object> parameter);
  
  public Collection<SupplieType> getDirectoryLikeCode(String code);

  public Titmsfiles findItmsFilesByCode(Map map);
  /**
   * 删除选中的信息
   * @param warehouse_code
   * @return
   */
  public boolean deleteItmsFilesInfoById(String bar_code);
 
  }
