package com.jdry.pms.houseWork.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.pojo.HouseworkVisitEntity;

/**
 * 回访，归档控制器
 * @author hezuping
 *
 */
@Repository
public interface HouseworkEventVisitSevice
{
  public List queryHouseWorkEventVisitInfo(Page page,Map<String, Object> parameter, Object object);

  public boolean saveEventVisit(HouseworkVisitEntity vi);
  
  public HouseworkVisitEntity queryVisitInfo(String event_no);
  
}
