package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "v_room_vs_owner_info")
public class RoomOfOwnerInfo implements Serializable {
    //房间编号
    @Column(name = "room_addrs")
    private String roomAddrs;
    //楼盘id
    @Column(name = "build_id")
    private String buildId;
    //楼盘名称
    @Column(name = "build_name")
    private String buildName;
    //小区id
    @Column(name = "community_id")
    private String communityId;
    //小区名称
    @Column(name = "community_name")
    private String communityName;
    //楼宇id
    @Column(name = "storied_build_id")
    private String storiedBuildId;
    //楼宇id
    @Column(name = "storied_build_name")
    private String storiedBuildName;
    //单元id
    @Column(name = "unit_id")
    private String unitId;
    //单元名称
    @Column(name = "unit_name")
    private String unitName;
    @Id
    //房间id
    @Column(name = "room_id")
    private String roomId;
    //房间编号
    @Column(name = "room_no")
    private String roomNo;
    //联掌住户ID
    @Column(name = "room_lz_id")
    private String roomLzId;
    //建筑面积
    @Column(name = "build_area")
    private BigDecimal buildArea;
    //套内面积
    @Column(name = "within_area")
    private BigDecimal withinArea;
    //房间状态
    @Column(name = "room_state")
    private String roomState;
    //房间状态名称
    @Column(name = "room_state_name")
    private String roomStateName;
    //房间类型
    @Column(name = "room_type")
    private String roomType;
    //房屋类型名称
    @Column(name = "room_type_name")
    private String roomTypeName;
    //合同收费日期
    @Column(name = "make_room_date")
    private Date makeRoomDate;
    //业主收房日期
    @Column(name = "receive_room_date")
    private Date receiveRoomDate;
    //起征日期
    @Column(name = "charge_date")
    private Date chargeDate;
    //收费项目ID
    @Column(name = "charge_type_no")
    private String chargeTypeNo;
    //单价
    @Column(name = "charge_price")
    private BigDecimal chargePrice;
    //收费名称
    @Column(name = "charge_type_name")
    private String chargeTypeName;
    //收费状态
    @Column(name = "charge_state")
    private String chargeState;
    //收费状态名称
    @Column(name = "charge_state_name")
    private String chargeStateName;
    //每月物业费
    @Column(name = "months_price")
    private BigDecimal monthsPrice;
    //每天物业费
    @Column(name = "days_price")
    private BigDecimal daysPrice;
    //业主ID
    @Column(name = "owner_id")
    private String ownerId;
    //名字
    @Column(name = "owner_name")
    private String ownerName;
    //性别
    @Column(name = "sex")
    private String sex;
    //电话
    @Column(name = "phone")
    private String phone;
    //身份证
    @Column(name = "card_id")
    private String cardId;
    //生日
    @Column(name = "birth_date")
    private String birthDate;
    //联掌住户ID
    @Column(name = "lz_room_owner_id")
    private String lzRoomOwnerId;

    //收费项目ID
    @Column(name = "charge_type_id")
    private String chargeTypeId;

    public String getChargeTypeId() {
        return chargeTypeId;
    }

    public void setChargeTypeId(String chargeTypeId) {
        this.chargeTypeId = chargeTypeId;
    }

    public String getLzRoomOwnerId() {
        return lzRoomOwnerId;
    }

    public void setLzRoomOwnerId(String lzRoomOwnerId) {
        this.lzRoomOwnerId = lzRoomOwnerId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
