package com.jdry.pms.assignWork.service;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 对外派工接口
 * @author hezuping
 *
 */
@Component
public interface AssignWorkServiceInterfaceService 
{
	/**
	 * 查询业主信息
	 * @param phone
	 * @return
	 */
  public List queryBasicInfo(String phone);
   /**
    * 查询投诉历史信息
    * @param phone
    * @return
    */
  public  List queryComplaintHistoryByPhone(String phone);
  /**
   * 
   * @param mtn_id
   */
  public List queryComplaintDetailByRptId(String mtn_id);	

}
