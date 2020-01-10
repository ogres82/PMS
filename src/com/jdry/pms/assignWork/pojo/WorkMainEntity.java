package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "T_R_MAINTAIN")
public class WorkMainEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2269405758870666548L;

	
	private String rpt_kid;// key
	private String rpt_id;// 业务编号
	private String rpt_name;// 申告人姓名
	private String owner_name;// 业主姓名
	private String owner_id;// 业主ID
	private String owner_type;// 业主类别
	private String owner_house;// 业主房号
	private String in_call;// 呼入电话
	private String owner_phone;// 联系电话
	private String event_source;// 事件来源
	private Date event_time;// 事件时间
	private String event_type;// 事件类别
	private String createby;// 受理人
	private Date createTime;// 受理时间
	private Date finishTime;// 完成时间
	private String address;// 地址
	private String roomNo;
	private String addres_record;//录音文件
	
	private String event_content;//新增事件内容
	
	private String event_state;//事件状态
	
	private String order_state;
	
	//接口新增
	private String sum_price;
	
	private String material_cost;
	
	private String labor_cost;
	
	private String img_url;
	
	private String hander_names;
	
	//一下为评价属性只做临时用，不存储数据
	
	private String dispatch_visit_lev;
	
	private String dispatch_evaluate;
	private String dispatch_visit_record;
	
	private String dispatch_visit_time;
	private String finish_img_url;
	private String dispatch_tools;
	private String payFlag;
	/**
	 * 针对于物管拒单扩展字段
	 */
	private String dispatch_result;
	
	public String getAddres_record() {
		return addres_record;
	}

	public void setAddres_record(String addres_record) {
		this.addres_record = addres_record;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	@PropertyDef(label = "流程编号")
	private Long processInstanceId;
	
	@Id
	public String getRpt_kid() {
		return rpt_kid;
	}

	public void setRpt_kid(String rpt_kid) {
		this.rpt_kid = rpt_kid;
	}
	
	public String getRpt_id() {
		return rpt_id;
	}

	public void setRpt_id(String rpt_id) {
		this.rpt_id = rpt_id;
	}

	public String getRpt_name() {
		return rpt_name;
	}

	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

	public String getOwner_house() {
		return owner_house;
	}

	public void setOwner_house(String owner_house) {
		this.owner_house = owner_house;
	}

	public String getIn_call() {
		return in_call;
	}

	public void setIn_call(String in_call) {
		this.in_call = in_call;
	}

	public String getOwner_phone() {
		return owner_phone;
	}

	public void setOwner_phone(String owner_phone) {
		this.owner_phone = owner_phone;
	}

	public String getEvent_source() {
		return event_source;
	}

	public void setEvent_source(String event_source) {
		this.event_source = event_source;
	}

	public Date getEvent_time() {
		return event_time;
	}

	public void setEvent_time(Date event_time) {
		this.event_time = event_time;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getEvent_content() {
		return event_content;
	}

	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}

	public String getEvent_state() {
		return event_state;
	}

	public void setEvent_state(String event_state) {
		this.event_state = event_state;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSum_price() {
		return sum_price;
	}

	public void setSum_price(String sum_price) {
		this.sum_price = sum_price;
	}

	public String getMaterial_cost() {
		return material_cost;
	}

	public void setMaterial_cost(String material_cost) {
		this.material_cost = material_cost;
	}

	public String getLabor_cost() {
		return labor_cost;
	}

	public void setLabor_cost(String labor_cost) {
		this.labor_cost = labor_cost;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getDispatch_visit_lev() {
		return dispatch_visit_lev;
	}

	public String getDispatch_evaluate() {
		return dispatch_evaluate;
	}

	public void setDispatch_visit_lev(String dispatch_visit_lev) {
		this.dispatch_visit_lev = dispatch_visit_lev;
	}

	public void setDispatch_evaluate(String dispatch_evaluate) {
		this.dispatch_evaluate = dispatch_evaluate;
	}

	public String getDispatch_visit_record() {
		return dispatch_visit_record;
	}

	public void setDispatch_visit_record(String dispatch_visit_record) {
		this.dispatch_visit_record = dispatch_visit_record;
	}

	@Transient
	public String getDispatch_visit_time() {
		return dispatch_visit_time;
	}

	public void setDispatch_visit_time(String dispatch_visit_time) {
		this.dispatch_visit_time = dispatch_visit_time;
	}

	@Transient
	public String getDispatch_result() {
		return dispatch_result;
	}

	public void setDispatch_result(String dispatch_result) {
		this.dispatch_result = dispatch_result;
	}
	@Transient
	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	
	public String getHander_names() {
		return hander_names;
	}

	public void setHander_names(String hander_names) {
		this.hander_names = hander_names;
	}
	@Transient
	public String getFinish_img_url() {
		return finish_img_url;
	}

	public void setFinish_img_url(String finish_img_url) {
		this.finish_img_url = finish_img_url;
	}
	@Transient
	public String getDispatch_tools() {
		return dispatch_tools;
	}

	public void setDispatch_tools(String dispatch_tools) {
		this.dispatch_tools = dispatch_tools;
	}
	
	
}
