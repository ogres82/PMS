package com.jdry.pms.assignWork.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jdry.pms.assignWork.pojo.WorkMainEntity;

/**
 * 报障报修对外——APP端接口
 * @author hezuping
 *
 */
@Component
public interface ImpairedRepairService
{
  /**
   * 获取历史列表APP
   * @param handleId
   * @return
   */
  public List getProptyImpairRepHistory(String handleId);
  /**
   * 通过事件编号查询详情APP
   * @param rpt_id
   * @return
   */
  public WorkMainEntity  getProptyImpairRepDetailByEventId(String rpt_id);
  /**
   * 通过事件编号获取派工步骤
   * @param rpt_id
   * @return
   */
  public List getDispatchStepByEventId(String rpt_id);
  
  public List<?> queryAllNoDispatch();
  
  /**
	 * 客户端接单操作
	 * 接单后，工单推进流程至已派工
	 */
	public String dipatchByClient(Map<Object,Object> parameter);
}
