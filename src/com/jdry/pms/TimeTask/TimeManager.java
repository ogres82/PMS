package com.jdry.pms.TimeTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimeManager {
	private static final long PERIOD_DAY =7 * 24 * 60 * 60 * 1000;
	
	public TimeManager(long delay) {
//		Calendar calendar = Calendar.getInstance(); 
//		  /*** 定制每日2:00执行方法 ***/ 
//	    calendar.set(Calendar.HOUR_OF_DAY, 21);
//	    calendar.set(Calendar.MINUTE, 0);
//	    calendar.set(Calendar.SECOND, 0);
//	    Date date=calendar.getTime(); //第一次执行定时任务的时间
	    Timer timer = new Timer();
	    EqpDataTimerTask task = new EqpDataTimerTask();
	    //安排指定的任务在延迟后开始进行重复的固定延迟执行。
	    timer.schedule(task,delay,PERIOD_DAY);
	}
	
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}
}
