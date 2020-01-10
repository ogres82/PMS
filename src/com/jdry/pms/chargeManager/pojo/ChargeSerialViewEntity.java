package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_charge_serial_info")
public class ChargeSerialViewEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5271915846263332479L;
    private String serial_id; // 流水id
    private String serial_no; // 流水编号
    private String owner_id; // 业主id
    private String owner_name; // 业主姓名
    private String charge_type_no; // 收费项目编号
    private String charge_type_name; // 收费项目名称
    private String room_id; // 房间id
    private String room_no; // 房间编号
    private String state; // 收支状态01:收入 02支出
    private String charge_type; // 费用类型 01:正常 02押金 03预收款
    private BigDecimal receive_amount; // 应收金额
    private BigDecimal paid_amount; // 实收金额
    private BigDecimal advance_amount; // 预收金额
    private Date paid_date; // 收款日期
    private String paid_mode; // 收款方式 01:抵扣 02现金 03支票 04转账 05支付宝 06 POS机 99其他
    private String ticket_mode; // 开票方式 01：发票 02收据
    private String oper_emp_id; // 操作人
    private Date begin_date; // 开始时间
    private Date end_date; // 结束时间
    private String charge_info_id; // 账单id
    private Date update_date; // 更新时间
    private int delete_id; // 删除标识，只是逻辑删除 0：未删除，1：已删除
    private String remark; // 备注

    private BigDecimal reduce_mount; // 减免金额
    private BigDecimal get_mount; // 前台收到金额
    private BigDecimal odd_mount; // 找零金额
    private String odd_mode; // 找零方式 01:找零 02转预存
    private String reduce_mode; // 减免方式 待定

    // drop_开头是对应的中文解释
    private String drop_paid_mode;
    private String drop_state;
    private String drop_charge_type;
    private String drop_ticket_mode;
    private String drop_odd_mode;

    private String community_name; // 小区名称
    private String storied_build_name; // 楼宇名称
    private String room_type; // 房间类型

    private String receipt_id;// 收据单号
    private String oper_emp_name;//收款人
    private String reduce_url;

    private String room_addr;
    private String type_flag;
    private String order_id;

    @Id
    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCharge_type_no() {
        return charge_type_no;
    }

    public void setCharge_type_no(String charge_type_no) {
        this.charge_type_no = charge_type_no;
    }

    public String getCharge_type_name() {
        return charge_type_name;
    }

    public void setCharge_type_name(String charge_type_name) {
        this.charge_type_name = charge_type_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getReceive_amount() {
        return receive_amount;
    }

    public void setReceive_amount(BigDecimal receive_amount) {
        this.receive_amount = receive_amount;
    }

    public BigDecimal getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(BigDecimal paid_amount) {
        this.paid_amount = paid_amount;
    }

    public BigDecimal getAdvance_amount() {
        return advance_amount;
    }

    public void setAdvance_amount(BigDecimal advance_amount) {
        this.advance_amount = advance_amount;
    }

    public Date getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(Date paid_date) {
        this.paid_date = paid_date;
    }

    public String getPaid_mode() {
        return paid_mode;
    }

    public void setPaid_mode(String paid_mode) {
        this.paid_mode = paid_mode;
    }

    public String getOper_emp_id() {
        return oper_emp_id;
    }

    public void setOper_emp_id(String oper_emp_id) {
        this.oper_emp_id = oper_emp_id;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public int getDelete_id() {
        return delete_id;
    }

    public void setDelete_id(int delete_id) {
        this.delete_id = delete_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getCharge_info_id() {
        return charge_info_id;
    }

    public void setCharge_info_id(String charge_info_id) {
        this.charge_info_id = charge_info_id;
    }

    public String getTicket_mode() {
        return ticket_mode;
    }

    public void setTicket_mode(String ticket_mode) {
        this.ticket_mode = ticket_mode;
    }

    public BigDecimal getReduce_mount() {
        return reduce_mount;
    }

    public void setReduce_mount(BigDecimal reduce_mount) {
        this.reduce_mount = reduce_mount;
    }

    public BigDecimal getGet_mount() {
        return get_mount;
    }

    public void setGet_mount(BigDecimal get_mount) {
        this.get_mount = get_mount;
    }

    public BigDecimal getOdd_mount() {
        return odd_mount;
    }

    public void setOdd_mount(BigDecimal odd_mount) {
        this.odd_mount = odd_mount;
    }

    public String getOdd_mode() {
        return odd_mode;
    }

    public void setOdd_mode(String odd_mode) {
        this.odd_mode = odd_mode;
    }

    public String getReduce_mode() {
        return reduce_mode;
    }

    public void setReduce_mode(String reduce_mode) {
        this.reduce_mode = reduce_mode;
    }

    public String getDrop_paid_mode() {
        return drop_paid_mode;
    }

    public void setDrop_paid_mode(String drop_paid_mode) {
        this.drop_paid_mode = drop_paid_mode;
    }

    public String getDrop_state() {
        return drop_state;
    }

    public void setDrop_state(String drop_state) {
        this.drop_state = drop_state;
    }

    public String getDrop_charge_type() {
        return drop_charge_type;
    }

    public void setDrop_charge_type(String drop_charge_type) {
        this.drop_charge_type = drop_charge_type;
    }

    public String getDrop_ticket_mode() {
        return drop_ticket_mode;
    }

    public void setDrop_ticket_mode(String drop_ticket_mode) {
        this.drop_ticket_mode = drop_ticket_mode;
    }

    public String getDrop_odd_mode() {
        return drop_odd_mode;
    }

    public void setDrop_odd_mode(String drop_odd_mode) {
        this.drop_odd_mode = drop_odd_mode;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getStoried_build_name() {
        return storied_build_name;
    }

    public void setStoried_build_name(String storied_build_name) {
        this.storied_build_name = storied_build_name;
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getOper_emp_name() {
        return oper_emp_name;
    }

    public void setOper_emp_name(String oper_emp_name) {
        this.oper_emp_name = oper_emp_name;
    }

    public String getReduce_url() {
        return reduce_url;
    }

    public void setReduce_url(String reduce_url) {
        this.reduce_url = reduce_url;
    }

	public String getRoom_addr() {
		return room_addr;
	}

	public void setRoom_addr(String room_addr) {
		this.room_addr = room_addr;
	}

	public String getType_flag() {
		return type_flag;
	}

	public void setType_flag(String type_flag) {
		this.type_flag = type_flag;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
}
