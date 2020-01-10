package com.jdry.pms.chargeManager.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_house_vs_fee")
public class RoomVsFee implements Serializable {
    @Id
    @Column(name = "room_id")
    private String roomId;    //房间ID
    @Column(name = "room_addrs")
    private String roomAddrs;    //房屋地址
    @Column(name = "build_id")
    private String buildId;    //楼盘ID
    @Column(name = "build_name")
    private String buildName;    //楼盘名称
    @Column(name = "community_id")
    private String communityId;    //小区ID
    @Column(name = "community_name")
    private String communityName;    //小区名称
    @Column(name = "storied_build_id")
    private String storiedBuildId;    //所属楼栋ID
    @Column(name = "storied_build_name")
    private String storiedBuildName;    //楼栋名称
    @Column(name = "unit_id")
    private String unitId;    //单元ID
    @Column(name = "unit_name")
    private String unitName;    //单元名称
    @Column(name = "room_no")
    private String roomNo;    //房间号
    @Column(name = "room_lz_id")
    private String roomLzId; //联掌房间ID
    @Column(name = "build_area")
    private BigDecimal buildArea;    //建筑面积
    @Column(name = "within_area")
    private BigDecimal withinArea;    //套内面积
    @Column(name = "room_state")
    private String roomState;    //房间状态 0未售1交房2接房3入住4出租
    @Column(name = "room_state_name")
    private String roomStateName;    //房间状态名称
    @Column(name = "room_type")
    private String roomType;    //房间类型 0高层1洋房2别墅
    @Column(name = "room_type_name")
    private String roomTypeName;    //房间类型名称 0高层1洋房2别墅
    @Column(name = "make_room_date")
    private Date makeRoomDate;    //收房日期（地产通知收房的日期）
    @Column(name = "receive_room_date")
    private Date receiveRoomDate;    //业主实际收房日期
    @Column(name = "charge_date")
    private Date chargeDate;    //收费开始时间
    @Column(name = "charge_type_no")
    private String chargeTypeNo;    //收费项编码
    @Column(name = "charge_price")
    private BigDecimal chargePrice;    //单价
    @Column(name = "charge_type_name")
    private String chargeTypeName;    //收费项名称
    @Column(name = "charge_state")
    private String chargeState;    //是否开始出账（1：开始出账；0：不出账）
    @Column(name = "charge_state_name")
    private String chargeStateName;    //是否开始出账名称（1：开始出账；0：不出账）
    @Column(name = "months_price")
    private BigDecimal monthsPrice;    //每月物业费
    @Column(name = "days_price")
    private BigDecimal daysPrice;    //每天物业费（每月按30天计算）

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomAddrs() {
        return roomAddrs;
    }

    public void setRoomAddrs(String roomAddrs) {
        this.roomAddrs = roomAddrs;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomLzId() {
        return roomLzId;
    }

    public void setRoomLzId(String roomLzId) {
        this.roomLzId = roomLzId;
    }

    public BigDecimal getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(BigDecimal buildArea) {
        this.buildArea = buildArea;
    }

    public BigDecimal getWithinArea() {
        return withinArea;
    }

    public void setWithinArea(BigDecimal withinArea) {
        this.withinArea = withinArea;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getRoomStateName() {
        return roomStateName;
    }

    public void setRoomStateName(String roomStateName) {
        this.roomStateName = roomStateName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Date getMakeRoomDate() {
        return makeRoomDate;
    }

    public void setMakeRoomDate(Date makeRoomDate) {
        this.makeRoomDate = makeRoomDate;
    }

    public Date getReceiveRoomDate() {
        return receiveRoomDate;
    }

    public void setReceiveRoomDate(Date receiveRoomDate) {
        this.receiveRoomDate = receiveRoomDate;
    }

    public Date getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(Date chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getChargeTypeNo() {
        return chargeTypeNo;
    }

    public void setChargeTypeNo(String chargeTypeNo) {
        this.chargeTypeNo = chargeTypeNo;
    }

    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getChargeState() {
        return chargeState;
    }

    public void setChargeState(String chargeState) {
        this.chargeState = chargeState;
    }

    public String getChargeStateName() {
        return chargeStateName;
    }

    public void setChargeStateName(String chargeStateName) {
        this.chargeStateName = chargeStateName;
    }

    public BigDecimal getMonthsPrice() {
        return monthsPrice;
    }

    public void setMonthsPrice(BigDecimal monthsPrice) {
        this.monthsPrice = monthsPrice;
    }

    public BigDecimal getDaysPrice() {
        return daysPrice;
    }

    public void setDaysPrice(BigDecimal daysPrice) {
        this.daysPrice = daysPrice;
    }
}
