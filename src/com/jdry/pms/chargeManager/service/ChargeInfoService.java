package com.jdry.pms.chargeManager.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
@Repository
public interface ChargeInfoService
{

	public void queryAll(Page<ChargeInfoEntity> page, Map<String, Object> parameter, String type);
	public List<ChargeInfoEntity> queryAll(Map<String, Object> parameter, String type);
	public void saveAll(Collection<ChargeInfoEntity> chargeTypes);
	public void delete(ChargeInfoEntity chargeType);
	public void paidInfoSave(Map paidInfo,  List<ChargeInfoEntity> chargeInfo);
	public void queryChargeByParam(Page<ChargeInfoViewEntity> page, Map<String, Object> parameter, String type);
	
	public boolean addCharge(Map<String, Object> parameter);
	public List<ChargeInfoEntity> queryChargeByRoomId(String roomId);
	public List<ChargeInfoViewEntity> queryChargeBySerial(String serialId);
	
	public List<ChargeInfoEntity> queryChargeByWorkId(String WorkId);
	
	public List<ChargeInfoEntity> queryChargeArrearByRoomId(String RoomId);
	public BigDecimal getPayCountByIds(String ids);
	public List<ChargeInfoEntity> getChargeInfoByIds(String ids);
	public BigDecimal getArrearAmountByRoomId(String id);
	public List<ChargeInfoEntity> queryChargeArrearByWorkId(String workId);
	
	public List queryChargeStoreAmountByRoomId(String RoomId);
	
	public int manOwnerOfCharge(String operId,String roomId,String startTime,String endTime);
}
