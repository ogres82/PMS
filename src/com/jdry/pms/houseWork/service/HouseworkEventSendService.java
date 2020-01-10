package com.jdry.pms.houseWork.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.houseWork.pojo.HouseWorkEventSendEntity;

/**
 *
 *描述：派工处理处理人后期需改为多人
 * @author hezuping
 *
 */
@Repository
public interface HouseworkEventSendService
{
   public List queryHouseWorkEventSendInfo(Page page,Map<String, Object> parameter, Object object);
   /**
    * 通过派工单ID查询数据
    * @param send_id
    * @return
    */
   public HouseWorkEventSendEntity queryHouseWorkSendInfo(String send_id);
   /**
    * 通过事件ID查询派工单
    * @param id
    * @return
    */
   public HouseWorkEventSendEntity queryHouseWorkById(String id);
   
   public HouseWorkEventSendEntity queryHouseWorkSendInfoByEventNo(String event_no);
}
