package com.jdry.pms.chargeManager.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.basicInfo.pojo.VHouseProperty;
import com.jdry.pms.chargeManager.dao.ChargeInfoDao;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.pojo.ChargeInfoViewEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.chargeManager.util.ChargeUtil;

@Repository
@Component
public class ChargeInfoImpl implements ChargeInfoService {

	@Resource
	private ChargeInfoDao dao;

	@Resource
	private HousePropertyDao houseDao;
	
	//Collection<ChargeTypeSettingEntity> 
	@Override
	public void queryAll(Page<ChargeInfoEntity> page, Map<String, Object> parameter, String type)
	{
//		dao.queryAll(page, parameter, type);
	}
	
	@Override
	public List<ChargeInfoEntity> queryAll(Map<String, Object> parameter, String type){
//		return dao.queryAll(parameter, type);
		return null;
	}
	
	
	@Override
	public void saveAll(Collection<ChargeInfoEntity> chargeTypes){
		dao.saveAll(chargeTypes);
	}
	
	@Override
	public void delete(ChargeInfoEntity chargeType){
		dao.delete(chargeType);
	}
	
	@Override
	public void paidInfoSave(Map paidInfo,  List<ChargeInfoEntity> chargeInfo){
		dao.paidInfoSave(paidInfo, chargeInfo);
	}
	
	@Override
	public void queryChargeByParam(Page<ChargeInfoViewEntity> page, Map<String, Object> parameter, String type){
		dao.queryChargeByParam(page, parameter, type);
	}

	@Override
	public boolean addCharge(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		//
		if(parameter != null){
			//基础信息
			String charge_type_no = parameter.get("charge_type_no")==null?"":parameter.get("charge_type_no").toString();
			String charge_type_name = parameter.get("charge_type_name")==null?"":parameter.get("charge_type_name").toString();
			String room_id = parameter.get("room_id")==null?"":parameter.get("room_id").toString();
			String room_no = parameter.get("room_no")==null?"":parameter.get("room_no").toString();
			String owner_id = parameter.get("owner_id")==null?"":parameter.get("owner_id").toString();
			String owner_name = parameter.get("owner_name")==null?"":parameter.get("owner_name").toString();
			
			String begin_time = parameter.get("begin_time")==null?"":parameter.get("begin_time").toString();
			String end_time = parameter.get("end_time")==null?"":parameter.get("end_time").toString();
			
			String price = parameter.get("price")==null?"0":parameter.get("price").toString();
			String count = parameter.get("count")==null?"1":parameter.get("count").toString();
			String receive_amount = parameter.get("receive_amount")==null?"":parameter.get("receive_amount").toString();
						
			String oper_emp_id = parameter.get("oper_emp_id")==null?"":parameter.get("oper_emp_id").toString();
			String work_id = parameter.get("work_id")==null?"":parameter.get("work_id").toString();
			
		
			
			VHouseProperty vhp = houseDao.getVHousePropertyById(room_id);
			
			ChargeInfoEntity chargeInfo=new ChargeInfoEntity();
			chargeInfo.setCharge_type_no(charge_type_no);
			chargeInfo.setCharge_type_name(charge_type_name);
			chargeInfo.setRoom_id(room_id);
			chargeInfo.setRoom_no(room_no);
			chargeInfo.setOwner_id(owner_id);
			chargeInfo.setOwner_name(owner_name);
			chargeInfo.setBegin_time(ChargeUtil.StrToDate(begin_time));
			chargeInfo.setEnd_time(ChargeUtil.StrToDate(end_time));
			chargeInfo.setPrice(new BigDecimal(price));
			chargeInfo.setCount(new BigDecimal(count));
			chargeInfo.setReceive_amount(new BigDecimal(receive_amount));
			chargeInfo.setArrearage_amount(new BigDecimal(receive_amount));
			chargeInfo.setOper_emp_id(oper_emp_id);
			chargeInfo.setWork_id(work_id);
			chargeInfo.setPaid_amount(new BigDecimal(0));
			
			chargeInfo.setCommunity_name(vhp.getCommunity_name());
			chargeInfo.setStoried_build_name(vhp.getStoried_build_name());
			chargeInfo.setRoom_type(vhp.getRoomType());
			
			
			//账单状态为未收
			chargeInfo.setState("03");
			chargeInfo.setUpdate_date(new Date());
			
			//从该接口过来的全部是手动生成的账单
			chargeInfo.setData_from("02");
			
			return dao.addCharge(chargeInfo);
			
		}		
		
		return false;
	}

	@Override
	public List<ChargeInfoEntity> queryChargeByRoomId(String roomId) {
		// TODO Auto-generated method stub
		return dao.queryChargeByRoomId(roomId);
	}

	@Override
	public List<ChargeInfoViewEntity> queryChargeBySerial(String serialId) {
		// TODO Auto-generated method stub
		return dao.queryChargeBySerial(serialId);
	}

	@Override
	public List<ChargeInfoEntity> queryChargeByWorkId(String WorkId) {
		// TODO Auto-generated method stub
		return dao.queryChargeByWorkId(WorkId);
	}

	@Override
	public List<ChargeInfoEntity> queryChargeArrearByRoomId(String RoomId) {
		// TODO Auto-generated method stub
		return dao.queryChargeArrearByRoomId(RoomId);
	}

	@Override
	public BigDecimal getPayCountByIds(String ids) {
		// TODO Auto-generated method stub
		return dao.getPayCountByIds(ids);
	}

	@Override
	public List<ChargeInfoEntity> getChargeInfoByIds(String ids) {
		// TODO Auto-generated method stub
		return dao.getChargeInfoByIds(ids);
	}

	@Override
	public BigDecimal getArrearAmountByRoomId(String id) {
		// TODO Auto-generated method stub
		return dao.getArrearAmountByRoomId(id);
	}

	@Override
	public List<ChargeInfoEntity> queryChargeArrearByWorkId(String workId) {
		// TODO Auto-generated method stub
		return dao.queryChargeArrearByWorkId(workId);
	}

	@Override
	public List queryChargeStoreAmountByRoomId(String RoomId) {
		// TODO Auto-generated method stub
		return dao.queryChargeStoreAmountByRoomId(RoomId);
	}

	@Override
	public int manOwnerOfCharge(String operId, String roomId, String startTime, String endTime) {
		// TODO Auto-generated method stub
		return dao.manOwnerOfCharge(operId, roomId, startTime, endTime);
	}

}
