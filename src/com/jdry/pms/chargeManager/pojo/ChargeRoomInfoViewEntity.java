package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "v_room_billing_info")
public class ChargeRoomInfoViewEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "room_addrs")
    private String roomAddrs; //房屋地址

    @Column(name = "build_id")
    private String buildId; //楼盘ID

    @Column(name = "build_name")
    private String buildName; //楼盘名称

    @Column(name = "community_id")
    private String communityId; //小区ID

    @Column(name = "community_name")
    private String communityName;//小区名称

    @Column(name = "storied_build_id")
    private String storiedBuildId; //楼宇ID

    @Column(name = "storied_build_name")
    private String storiedBuildName; //楼宇名称

    @Column(name = "unit_id")
    private String unitId; //单元ID

    @Column(name = "unit_name")
    private String unitName; //单元名称
    @Id
    @Column(name = "room_id")
    private String roomId; //房间ID

    @Column(name = "room_no")
    private String roomNo; //房间编号

    @Column(name = "room_lz_id")
    private String roomLzId;//联掌编号

    @Column(name = "build_area")
    private BigDecimal buildArea; //建筑面积

    @Column(name = "within_area")
    private BigDecimal withinArea;//套内面积

    @Column(name = "room_state")
    private String roomState;//房间状态

    @Column(name = "room_state_name")
    private String roomStateName;//房屋状态名称

    @Column(name = "room_type")
    private String roomType;//房屋类型

    @Column(name = "room_type_name")
    private String roomTypeName;// 房屋类型名称

    @Column(name = "make_room_date")
    private Date makeRoomDate;//交房日期

    @Column(name = "receive_room_date")
    private Date receiveRoomDate;//收房日期

    @Column(name = "charge_date")
    private Date chargeDate;//物业费起征日期

    @Column(name = "charge_type_no")
    private String chargeTypeNo;//收费类型编码

    @Column(name = "charge_price")
    private BigDecimal chargePrice;//收费单价

    @Column(name = "charge_type_name")
    private String chargeTypeName;//收费名称

    @Column(name = "charge_state")
    private String chargeState;//收费状态

    @Column(name = "charge_state_name")
    private String chargeStateName;//收费状态名称

    @Column(name = "months_price")
    private BigDecimal monthsPrice;//月单价

    @Column(name = "days_price")
    private BigDecimal daysPrice;//天单价

    @Column(name = "owner_id")
    private String ownerId;//业主ID

    @Column(name = "owner_name")
    private String ownerName;//业主名称

    @Column(name = "card_id")
    private String cardId;//身份证号码

    @Column(name = "phone")
    private String phone;//电话

    @Column(name = "sex")
    private String sex;//性别

    @Column(name = "birth_date")
    private Date birthDate;//生日

    @Column(name = "total_paid_amount")
    private String totalPaidAmount;//共计缴费

    @Column(name = "fee_months")
    private Integer feeMonths;//欠费月份

    @Column(name = "fee_days")
    private Integer feeDays;//欠费天数

    @Column(name = "exp_date")
    private Date beginDate;//费用截止日期

    @Column(name = "arrears_state")
    private String arrearsState;//欠费标志名称

    @Column(name = "amount")
    private BigDecimal amount;//余额

    @Column(name = "arr_amount")
    private BigDecimal receiveAmount;//欠费金额

    @Column(name = "end_date")
    private Date endDate;//当前日期


    @Column(name = "dely_months")
    private Integer delyMonths;//欠费月数

    @Column(name = "dely_days")
    private Integer delyDays;//欠费天数

    @Transient
    private BigDecimal reduceMount = new BigDecimal(0.00);


    @Transient
    private BigDecimal paidAmount ;

    public Integer getDelyMonths() {
        return delyMonths;
    }

    public void setDelyMonths(Integer delyMonths) {
        this.delyMonths = delyMonths;
    }

    public Integer getDelyDays() {
        return delyDays;
    }

    public void setDelyDays(Integer delyDays) {
        this.delyDays = delyDays;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(String totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public Integer getFeeMonths() {
        return feeMonths;
    }

    public void setFeeMonths(Integer feeMonths) {
        this.feeMonths = feeMonths;
    }

    public Integer getFeeDays() {
        return feeDays;
    }

    public void setFeeDays(Integer feeDays) {
        this.feeDays = feeDays;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getArrearsState() {
        return arrearsState;
    }

    public void setArrearsState(String arrearsState) {
        this.arrearsState = arrearsState;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Date getReceiveRoomDate() {
        return receiveRoomDate;
    }

    public void setReceiveRoomDate(Date receiveRoomDate) {
        this.receiveRoomDate = receiveRoomDate;
    }

    public BigDecimal getReduceMount() {
        return reduceMount;
    }

    public void setReduceMount(BigDecimal reduceMount) {
        this.reduceMount = reduceMount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
}
