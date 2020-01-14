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
    @Column(name = "room_addrs") //房间编号
    private String roomAddrs;
    @Column(name = "build_id") //楼盘id
    private String buildId;
    @Column(name = "build_name") //楼盘名称
    private String buildName;
    @Column(name = "community_id") //小区id
    private String communityId;
    @Column(name = "community_name") //小区名称
    private String communityName;
    @Column(name = "storied_build_id") //楼宇id
    private String storiedBuildId;
    @Column(name = "storied_build_name") //楼宇id
    private String storiedBuildName;
    @Column(name = "unit_id") //单元id
    private String unitId;
    @Column(name = "unit_name") //单元名称
    private String unitName;
    @Id
    @Column(name = "room_id") //房间id
    private String roomId;
    @Column(name = "room_no") //房间编号
    private String roomNo;
    @Column(name = "room_lz_id") //联掌住户ID
    private String roomLzId;
    @Column(name = "build_area") //建筑面积
    private BigDecimal buildArea;
    @Column(name = "within_area") //套内面积
    private BigDecimal withinArea;
    @Column(name = "room_state") //房间状态
    private String roomState;
    @Column(name = "room_state_name") //房间状态名称
    private String roomStateName;
    @Column(name = "room_type") //房间类型
    private String roomType;
    @Column(name = "room_type_name") //房屋类型名称
    private String roomTypeName;
    @Column(name = "make_room_date") //合同收费日期
    private Date makeRoomDate;
    @Column(name = "receive_room_date") //业主收房日期
    private Date receiveRoomDate;
    @Column(name = "charge_date") //起征日期
    private Date chargeDate;
    @Column(name = "charge_type_no") //收费项目ID
    private String chargeTypeNo;
    @Column(name = "charge_price") //单价
    private BigDecimal chargePrice;
    @Column(name = "charge_type_name") //收费名称
    private String chargeTypeName;
    @Column(name = "charge_state") //收费状态
    private String chargeState;
    @Column(name = "charge_state_name") //收费状态名称
    private String chargeStateName;
    @Column(name = "months_price") //每月物业费
    private BigDecimal monthsPrice;
    @Column(name = "days_price") //每天物业费
    private BigDecimal daysPrice;
    @Column(name = "owner_id") //业主ID
    private String ownerId;
    @Column(name = "owner_name") //名字
    private String ownerName;
    @Column(name = "sex") //性别
    private String sex;
    @Column(name = "phone") //电话
    private String phone;
    @Column(name = "card_id") //身份证
    private String cardId;
    @Column(name = "birth_date") //生日
    private String birthDate;
    @Column(name = "lz_room_owner_id") //生日
    private String lzRoomOwnerId;

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
