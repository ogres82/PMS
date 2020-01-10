
package com.jdry.pms.TimeTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;
import com.jdry.pms.chargeManager.service.ChargeArrearViewService;
import com.jdry.pms.comm.service.impl.ExpireEventService;
import com.jdry.pms.comm.service.impl.SmsSendInteface;
import com.jdry.pms.comm.util.CommEnum;
import com.jdry.pms.comm.util.NoticePushUtil;
import com.jdry.pms.comm.util.SpringUtil;

/**
 * Copyright 2017 JDRY Co.Ltd All rights reserved.
 * 
 * @Author 徐磊
 * @Created on 2017-2-22 下午1:08:47
 * @description
 *
 */

@Component
public class PushNotice implements ServletContextListener {

	private ScheduledThreadPoolExecutor schedule = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		schedule.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		String contextString = arg0.getServletContext().getContextPath();
		if ("/PMS".equals(contextString)) {
			schedule = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3);
			ServiceReminder task1 = new ServiceReminder();
			schedule.scheduleAtFixedRate(task1, 2 * 60 * 1000, 5 * 60 * 1000, TimeUnit.MILLISECONDS);
			ArrearReminder task2 = new ArrearReminder();
			schedule.scheduleAtFixedRate(task2, 2 * 60 * 1000, 30 * 60 * 1000, TimeUnit.MILLISECONDS);
			BlessingSMS task3 = new BlessingSMS();
			Calendar currentDate = Calendar.getInstance();
			long currentDateLong = currentDate.getTime().getTime(); 
			// 计算从当前时间到最近一次执行时间的时间间隔
			Calendar earliestDate = this.getEarliestDate(currentDate, 9, 30, 0);
			long earliestDateLong = earliestDate.getTime().getTime();
			long delay = earliestDateLong - currentDateLong;
			// 计算执行周期为一天
			long period = 24 * 60 * 60 * 1000;
			schedule.scheduleAtFixedRate(task3, delay, period, TimeUnit.MILLISECONDS);
		}

	}

	private static class ServiceReminder implements Runnable {
		@Override
		public void run() {
			ExpireEventService.delayedDispatchPush();
		}
	}

	private static class ArrearReminder implements Runnable {
		private int MONTHOFDAY = 1;
		private int HOUROFDAY = 9;
		ChargeArrearViewService service;

		@Override
		public void run() {
			Calendar cal = Calendar.getInstance();
			int time = cal.get(Calendar.HOUR_OF_DAY);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (MONTHOFDAY == day && HOUROFDAY == time) { // 如果当天是1号上午9点就执行下边内容
				if (service == null) {
					service = (ChargeArrearViewService) SpringUtil.getObjectFromApplication("chargeArrearViewImpl");
				}
				List<ChargeArrearViewEntity> list = service.queryAll();
				for (int i = 0; i < list.size(); i++) {
					ChargeArrearViewEntity charge = list.get(i);
					String content = "尊敬的业主" + charge.getOwner_name() + "，截至本月您共欠物业费" + charge.getArrearage_amount() + "元，请及时缴纳。";
					NoticePushUtil.pushNotice(charge.getPhone(), content, "01");
					System.out.println(charge.getOwner_name() + "----推送成功！！");
				}
			}
		}
	}

	private static class BlessingSMS implements Runnable {

		PropertyManagerService service;
		SmsSendInteface SMS;

		@Override
		public void run() {
			if (service == null) {
				service = (PropertyManagerService) SpringUtil.getObjectFromApplication("propertyManagerServiceImpl");
			}
			if (SMS == null) {
				SMS = (SmsSendInteface) SpringUtil.getObjectFromApplication("smsSendInteface");
			}
			try {
				List<Map> list = service.getEmpInfoForSMS();

				int listNum = list.size();

				for (int i = 0; i < listNum; i++) {
					// 01 生日祝福 02 周年祝福
					String smsType = list.get(i).get("smsType").toString();
					String mobile = list.get(i).get("empPhone").toString();
					String empName = list.get(i).get("empName").toString();
					String empYear = list.get(i).get("empYear").toString();

					String strSMS = "";
					if ("01".equals(smsType)) {
						strSMS = "【乐湾物业】公司全体同事祝您生日快乐！公司的发展倾注了您和您家人的支持、奉献，感谢您的辛勤工作，在此向您表示衷心感谢！愿我们在今后的工作中和谐、同心，共创美好的明天！";
					} else {
						strSMS = "【乐湾物业】亲爱的" + empName + "：今天，迎来了您进入乐湾物业" + empYear + "周年的日子。感谢您" + empYear + "年来的付出与奉献，愿您在新的一年再创佳绩，勇攀高峰！";
					}
					SMS.sendSms(CommEnum.API_KEY, strSMS, mobile, null);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Calendar getEarliestDate(Calendar currentDate, int hourOfDay, int minuteOfHour, int secondOfMinite) {
		int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
		int currentMinute = currentDate.get(Calendar.MINUTE);
		int currentSecond = currentDate.get(Calendar.SECOND);

		// 如果输入条件中的时间小于当前时间就退后一天
		boolean dayLater = false;
		if (hourOfDay < currentHour) {
			dayLater = true;
		} else if (hourOfDay == currentHour) {
			if (minuteOfHour < currentMinute) {
				dayLater = true;
			} else if (minuteOfHour == currentSecond) {

				if (secondOfMinite < currentSecond) {
					dayLater = true;
				}
			}
		}

		if (dayLater) {
			currentDate.set(Calendar.DATE, currentDate.get(Calendar.DATE) + 1);
		}
		currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		currentDate.set(Calendar.MINUTE, minuteOfHour);
		currentDate.set(Calendar.SECOND, secondOfMinite);
		return currentDate;
	}
}
