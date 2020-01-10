package com.jdry.pms.chargeManager.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "v_charge_serial")
public class ChargeSerialInfo implements Serializable {

    @Id
    @Column(name = "serial_id")
    private String serialId;//缴费ID

    @Column(name = "charge_type_no")
    private String chargeTypeNo;//收费编码

    @Column(name = "charge_type_name")
    private String chargeTypeName;//收费名称

    @Column(name = "type_flag")
    private String typeFlag;//收费类型

    @Column(name = "type_flag_name")
    private String typeFlagName;//收费类型名称

    @Column(name = "oper_emp_id")
    private String operEmpId;//操作员Id

    @Column(name = "oper_emp_name")
    private String operEmpName;//操作员

    @Column(name = "owner_id")
    private String ownerId;//业主Id

    @Column(name = "owner_name")
    private String ownerName;//业主

    @Column(name = "build_id")
    private String buildId;//楼盘

    @Column(name = "build_name")
    private String buildName;//楼盘名称

    @Column(name = "community_id")
    private String communityId;//小区

    @Column(name = "community_name")
    private String communityName;//小区名称

    @Column(name = "storied_build_id")
    private String storiedBuildId;//楼宇

    @Column(name = "storied_build_name")
    private String storiedBuildName;//楼宇名称

    @Column(name = "unit_id")
    private String unitId;//单元

    @Column(name = "unit_name")
    private String unitName;//单元名称

    @Column(name = "room_id")
    private String roomId;//房间ID

    @Column(name = "room_no")
    private String roomNo;//房间名称

    @Column(name = "room_type")
    private String roomType;//房间类型

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;//支付金额

    @Column(name = "receive_amount")
    private BigDecimal receiveAmount;//实收金额

    @Column(name = "paid_mode")
    private String paidMode;//支付模式ID

    @Column(name = "paid_mode_name")
    private String paidModeName;//支付方式名称

    @Column(name = "paid_date")
    private Date paidDate;//支付日期

    @Column(name = "begin_date")
    private Date beginDate;//开始时间

    @Column(name = "end_date")
    private Date endDate;//结束时间

    @Column(name = "update_date")
    private Date updateDate;//录入时间

    @Column(name = "remark")
    private String remark;//备注

    @Column(name = "order_id")
    private String orderId;//支付宝订单

    @Column(name = "reduce_url")
    private String reduceUrl;//减免凭证

    @Column(name = "receipt_id")
    private String receiptId;//凭证号

    @Column(name = "charge_type")
    private String chargeType;//缴费类型

    @Column(name = "serial_type_name")
    private String serialTypeName;//缴费类型名称

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getChargeTypeNo() {
        return chargeTypeNo;
    }

    public void setChargeTypeNo(String chargeTypeNo) {
        this.chargeTypeNo = chargeTypeNo;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public String getTypeFlagName() {
        return typeFlagName;
    }

    public void setTypeFlagName(String typeFlagName) {
        this.typeFlagName = typeFlagName;
    }

    public String getOperEmpId() {
        return operEmpId;
    }

    public void setOperEmpId(String operEmpId) {
        this.operEmpId = operEmpId;
    }

    public String getOperEmpName() {
        return operEmpName;
    }

    public void setOperEmpName(String operEmpName) {
        this.operEmpName = operEmpName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getStoriedBuildId() {
        return storiedBuildId;
    }

    public void setStoriedBuildId(String storiedBuildId) {
        this.storiedBuildId = storiedBuildId;
    }

    public String getStoriedBuildName() {
        return storiedBuildName;
    }

    public void setStoriedBuildName(String storiedBuildName) {
        this.storiedBuildName = storiedBuildName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getPaidMode() {
        return paidMode;
    }

    public void setPaidMode(String paidMode) {
        this.paidMode = paidMode;
    }

    public String getPaidModeName() {
        return paidModeName;
    }

    public void setPaidModeName(String paidModeName) {
        this.paidModeName = paidModeName;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReduceUrl() {
        return reduceUrl;
    }

    public void setReduceUrl(String reduceUrl) {
        this.reduceUrl = reduceUrl;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getSerialTypeName() {
        return serialTypeName;
    }

    public void setSerialTypeName(String serialTypeName) {
        this.serialTypeName = serialTypeName;
    }
}
