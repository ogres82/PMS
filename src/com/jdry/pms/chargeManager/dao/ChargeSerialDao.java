package com.jdry.pms.chargeManager.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.jdry.pms.chargeManager.pojo.*;
import com.jdry.pms.chargeManager.service.ChargeRoomInfoViewService;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;

@Repository
public class ChargeSerialDao extends HibernateDao {
    static Logger log = Logger.getLogger(ChargeSerialDao.class);


    @Resource
    private ChargeRoomInfoViewService chargeRoomInfoViewService;


    // 预收押金页面表格显示
    public void queryAll(Page<ChargeSerialViewEntity> page, Map<String, Object> parameter) {

        String hql = " from " + ChargeSerialViewEntity.class.getName() + " a where 1=1 ";
        String sqlCount = "select count(serial_id) from " + ChargeSerialViewEntity.class.getName() + " b where 1=1 ";

        Map<String, Object> map = new HashMap<String, Object>();

        if (parameter != null) {
            String search = parameter.get("search").toString();
            String begin_time = parameter.get("begin_time").toString();
            String end_time = parameter.get("end_time").toString();
            String charge_type_select = parameter.get("charge_type_select").toString();
            String paid_mode_select = parameter.get("paid_mode_select").toString();
            String room_id = parameter.get("room_id").toString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (!begin_time.equals("")) {
                Date beginTime = null;
                try {
                    beginTime = formatter.parse(begin_time);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                hql += " and a.paid_date>=:beginTime ";
                sqlCount += " and b.paid_date>=:beginTime ";
                map.put("beginTime", beginTime);
            }
            if (!end_time.equals("")) {
                Date endTime = null;
                try {
                    endTime = formatter.parse(end_time);
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(endTime);
                    calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.
                    endTime = calendar.getTime();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                hql += " and a.paid_date<:endTime ";
                sqlCount += " and b.paid_date<:endTime ";

                map.put("endTime", endTime);
            }
            if (!room_id.equals("")) {
                hql += " and a.room_id =:room_id ";
                sqlCount += " and b.room_id =:room_id ";
                map.put("room_id", room_id);
            }

            if (!charge_type_select.isEmpty()) {
                hql += " and a.type_flag=:type_flag ";
                sqlCount += " and b.type_flag=:type_flag ";
                map.put("type_flag", charge_type_select);
            }
            if (!paid_mode_select.isEmpty()) {
                hql += " and a.paid_mode=:paid_mode ";
                sqlCount += " and b.paid_mode=:paid_mode ";
                map.put("paid_mode", paid_mode_select);
            }
            if (!search.equals("")) {
                hql += " and (a.owner_name like:owner_name " + " or a.room_no like:room_no"
                        + " or a.charge_type_name like:charge_type_name" +

                        " or a.drop_paid_mode like:drop_paid_mode" + " or a.drop_state like:drop_state"
                        + " or a.drop_charge_type like:drop_charge_type"
                        + " or a.drop_ticket_mode like:drop_ticket_mode" + " or a.drop_odd_mode like:drop_odd_mode" +

                        " or a.oper_emp_id like:oper_emp_id)";
                sqlCount += " and (b.owner_name like:owner_name " + " or b.room_no like:room_no"
                        + " or b.charge_type_name like:charge_type_name" +

                        " or b.drop_paid_mode like:drop_paid_mode" + " or b.drop_state like:drop_state"
                        + " or b.drop_charge_type like:drop_charge_type"
                        + " or b.drop_ticket_mode like:drop_ticket_mode" + " or b.drop_odd_mode like:drop_odd_mode" +

                        " or b.oper_emp_id like:oper_emp_id)";
                map.put("owner_name", "%" + search + "%");
                map.put("room_no", "%" + search + "%");
                map.put("charge_type_name", "%" + search + "%");
                map.put("oper_emp_id", "%" + search + "%");

                map.put("drop_paid_mode", "%" + search + "%");
                map.put("drop_state", "%" + search + "%");
                map.put("drop_charge_type", "%" + search + "%");
                map.put("drop_ticket_mode", "%" + search + "%");
                map.put("drop_odd_mode", "%" + search + "%");
            }

        }

        hql += " ORDER BY paid_date DESC";
        try {
            this.pagingQuery(page, hql, sqlCount, map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 新增
    public void save(ChargeSerialEntity chargeType) {
        Session session = null;
        Transaction tx = null;
        try {
            session = this.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(chargeType);
            // 提交
            tx.commit();
            log.info("账单缴费成功(预存/押金)--业主Id:" + chargeType.getOwner_id() + ", 金额：" + chargeType.getPaid_amount());
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            log.info("账单缴费失败(预存/押金)--业主Id:" + chargeType.getOwner_id() + ", 金额：" + chargeType.getPaid_amount());
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void delete(ChargeSerialEntity chargeType) {
        Session session = this.getSessionFactory().openSession();
        session.delete(chargeType);
        session.flush();
        session.close();
        log.info("账单删除--" + chargeType.getSerial_id());
    }

    // 业主缴费历史，共app
    public List<ChargeSerialViewEntity> getPaidHostory(Map<String, Object> parameter) {
        List<ChargeSerialViewEntity> result = null;
        if (parameter != null) {
            String room_id = parameter.get("room_id") == null ? "" : parameter.get("room_id").toString();
            String hql = " from " + ChargeSerialViewEntity.class.getName()
                    + " a where 1=1 ";

            if (!room_id.isEmpty()) {
                hql += " and a.room_id='" + room_id + "' order by a.paid_date desc";
                result = this.query(hql);
            }

        }
        return result;
    }

    // 客户收费页面的收费
    public String paid(Map<String, Object> parameter, List<ChargeSerialEntity> chargeInfos) {
        if (chargeInfos.size() > 0) {

            Session session = this.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            try {
                for (int i = 0, len = chargeInfos.size(); i < len; i++) {
                    this.save(chargeInfos.get(i));
                }
                // 提交
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null) {
                    tx.rollback();
                }
                return e.getMessage();

            } finally {
                session.flush();
                session.close();
            }
        }
        return "success";
    }

    // 押金转预存
    public boolean toPrestore(ChargeSerialEntity serial) {
        boolean flag = true;

        Session session = null;
        Transaction tx = null;
        try {
            session = this.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String roomId = serial.getRoom_id();
            List<RoomVsFee> roomVsFeeList = chargeRoomInfoViewService.getRoomOfChargeInfo(roomId);

            if (roomVsFeeList != null && !roomVsFeeList.isEmpty()) {

                //不为空的情况
                String chargeTypeNo = roomVsFeeList.get(0).getChargeTypeNo();
                String chargeTypeName = roomVsFeeList.get(0).getChargeTypeName();
                // 更新该条流水的charge_type
                serial.setCharge_type("05");
                serial.setUpdate_date(new Date());
                serial.setCharge_type("01");
                serial.setCharge_type_name(chargeTypeName);
                serial.setCharge_type_no(chargeTypeNo);
                serial.setRemark("押金转预存");
                // 执行
                session.saveOrUpdate(serial);
            } else {
                //为空的情况
                flag = false;
            }
            // 提交
            tx.commit();
            log.info("押金转预存成功--流水：" + serial.getSerial_id());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            log.info("押金转预存失败--流水：" + serial.getSerial_id());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return flag;
    }

    // APP端支付
    public boolean paidFromApp(Map<String, Object> parameter, List<ChargeRoomInfoViewEntity> infos) {
        boolean flag = true;
        String charge_paid_mode = parameter.get("charge_paid_mode") == null ? ""
                : parameter.get("charge_paid_mode").toString();
        String transaction_fee = parameter.get("transaction_fee") == null ? ""
                : parameter.get("transaction_fee").toString();
        String order_id = parameter.get("order_id") == null ? "" : parameter.get("order_id").toString();

        Session session = null;
        Transaction tx = null;
        String chargeInfoIds = "";
        try {
            session = this.getSessionFactory().openSession();
            tx = session.beginTransaction();
            // 流程、流程详情、预存sql
            //String sqlSerialInsert = "";
            String uuid ="";

            for (ChargeRoomInfoViewEntity chargeType : infos) {
                ChargeSerialEntity serial = new ChargeSerialEntity();
                //serial.setSerial_id(uuid);
                serial.setRoom_id(chargeType.getRoomId());
                serial.setRoom_no(chargeType.getRoomNo());
                serial.setOwner_id(chargeType.getOwnerId());
                serial.setOwner_name(chargeType.getOwnerName());
                serial.setCharge_type_no(chargeType.getChargeTypeNo());
                serial.setCharge_type_name(chargeType.getChargeTypeName());
                serial.setState("01");
                serial.setCharge_type("01");
                serial.setReceive_amount(new BigDecimal(transaction_fee));
                serial.setPaid_amount(new BigDecimal(transaction_fee));
                serial.setPaid_date(new Date());
                serial.setPaid_mode(charge_paid_mode);
                serial.setOper_emp_id("admin");
                serial.setUpdate_date(new Date());
                serial.setReduce_mount(new BigDecimal(0));
                serial.setBegin_date(chargeType.getBeginDate());
                serial.setEnd_date(chargeType.getEndDate());
                serial.setOrder_id(order_id);
                serial.setRoom_type(chargeType.getRoomType());
                serial.setCommunity_name(chargeType.getCommunityName());
                serial.setStoried_build_name(chargeType.getStoriedBuildName());
//                sqlSerialInsert = "INSERT INTO t_charge_serial_info(serial_id,room_id,room_no,owner_id,owner_name,charge_type_no,charge_type_name,state,charge_type,receive_amount,paid_amount,paid_date,paid_mode,"
//                        + " oper_emp_id,update_date, begin_date, end_date,order_id,room_type,community_name,storied_build_name) "
//                        + " values('" + uuid + "','" + chargeType.getRoomId() + "','" + chargeType.getRoomNo() + "','"
//                        + chargeType.getOwnerId() + "','" + chargeType.getOwnerName() + "','" + chargeType.getChargeTypeNo() + "','"
//                        + chargeType.getChargeTypeName() + "','01','01'," + transaction_fee + "," + transaction_fee
//                        + ",SYSDATE(),'" + charge_paid_mode + "','admin',SYSDATE(),str_to_date('" + chargeType.getBeginDate()
//                        + "', '%Y-%m-%d %H:%i:%s'),str_to_date('" + chargeType.getEndDate() + "', '%Y-%m-%d %H:%i:%s'),'" + order_id + "','"
//                        + chargeType.getRoomType() + "','" + chargeType.getCommunityName() + "','" + chargeType.getStoriedBuildName() + "')";
//                System.out.println(sqlSerialInsert);
//               session.createSQLQuery(sqlSerialInsert).executeUpdate();
                 session.saveOrUpdate(serial);
                uuid = serial.getSerial_id();
            }

            //flag = false;
            // 提交
            tx.commit();
            log.info("批量收费成功--流水：" + uuid + ", 账单：" + chargeInfoIds);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            log.info("批量收费失败--账单：" + chargeInfoIds);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return flag;

    }

    // APP端预存
    public boolean storeFromApp(Map<String, Object> parameter) {
        boolean flag = true;
        String charge_paid_mode = parameter.get("charge_paid_mode") == null ? ""
                : parameter.get("charge_paid_mode").toString();
        String room_id = parameter.get("room_id") == null ? "" : parameter.get("room_id").toString();
        String room_no = parameter.get("room_no") == null ? "" : parameter.get("room_no").toString();
        String owner_id = parameter.get("owner_id") == null ? "" : parameter.get("owner_id").toString();
        String owner_name = parameter.get("owner_name") == null ? "" : parameter.get("owner_name").toString();
        String order_id = parameter.get("order_id") == null ? "" : parameter.get("order_id").toString();
        String topup_amount = parameter.get("topup_amount") == null ? "" : parameter.get("topup_amount").toString();
        // 构造以字符串内容为值的BigDecimal类型
        BigDecimal paidAmount = new BigDecimal(topup_amount);
        // 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        paidAmount = paidAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

        Session session = null;
        Transaction tx = null;
        try {
            session = this.getSessionFactory().openSession();
            tx = session.beginTransaction();

            List<RoomVsFee> roomVsFeeList = chargeRoomInfoViewService.getRoomOfChargeInfo(room_id);

            String chargeTypeNo = roomVsFeeList.get(0).getChargeTypeNo();
            String chargeTypeName = roomVsFeeList.get(0).getChargeTypeName();
            String communityName = roomVsFeeList.get(0).getCommunityName();
            String storiedBuildName = roomVsFeeList.get(0).getStoriedBuildName();
            String roomType = roomVsFeeList.get(0).getRoomType();

            String sqlSerialInsert = "INSERT INTO t_charge_serial_info(serial_id,room_id,room_no,owner_id,owner_name,charge_type_no,charge_type_name,remark,state,charge_type,paid_amount,receive_amount,paid_date,paid_mode,oper_emp_id,begin_date, end_date,update_date,order_id,room_type,community_name,storied_build_name) "
                    + "values(UUID(),'" + room_id + "','" + room_no + "','" + owner_id + "','" + owner_name
                    + "','" + chargeTypeNo + "','" + chargeTypeName + "','APP端预存','01','03'," + topup_amount + "," + topup_amount + ",SYSDATE(),'" + charge_paid_mode
                    + "','系统操作',SYSDATE(),SYSDATE(),SYSDATE(),'" + order_id + "','" + roomType + "','"
                    + communityName + "','" + storiedBuildName + "')";
            session.createSQLQuery(sqlSerialInsert).executeUpdate();
            // 提交
            tx.commit();
            log.info("APP端预存成功--房间id：" + room_id + ", 房号：" + room_no + ", 业主id：" + owner_id + ", 业主：" + owner_name
                    + ", 金额：" + topup_amount);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            log.info("APP端预存失败--房间id：" + room_id + ", 房号：" + room_no + ", 业主id：" + owner_id + ", 业主：" + owner_name
                    + ", 金额：" + topup_amount);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return flag;

    }

    // APP端订单号的唯一性验证
    public boolean validateOrderId(String orderId) {
        boolean flag = true;

        String sql = " SELECT t.order_id FROM t_charge_serial_info t where t.order_id='" + orderId + "' ";

        Session session = this.getSessionFactory().openSession();
        List result = session.createSQLQuery(sql).list();

        session.flush();
        session.close();
        System.out.println(sql);

        if (result.size() > 0) {
            flag = false;
        }

        return flag;
    }

    public List<Map<String, Object>> chargingItem(String room_id) {
        String sql = " select cts.charge_type_no,cts.charge_type_name from t_charge_type_room_rela trr"
                + " LEFT JOIN t_charge_type_setting cts ON trr.charge_type_no = cts.charge_type_no AND trr.charge_type_id=cts.charge_type_id"
                + " WHERE trr.room_id ='" + room_id + "' GROUP BY cts.charge_type_no";

        Session session = this.getSessionFactory().openSession();
        List result = session.createSQLQuery(sql).addScalar("charge_type_no", StandardBasicTypes.STRING)
                .addScalar("charge_type_name", StandardBasicTypes.STRING).list();
        return result;
    }

}
