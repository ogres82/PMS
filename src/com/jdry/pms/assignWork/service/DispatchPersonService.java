package com.jdry.pms.assignWork.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jdry.pms.assignWork.pojo.DispatchPerson;
/**
 * 描述：派工单和处理人关系
 * @author hezuping
 * 时间：2016年10月8日14:20:41
 */
@Component
public interface DispatchPersonService 
{
  public void addDisptchPersonInfo(DispatchPerson person);
  
  public List queryAllHandler();
  
  public void deleteDispatchPersonById(String rptId);
  
}
