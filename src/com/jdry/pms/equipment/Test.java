package com.jdry.pms.equipment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.jdry.pms.comm.util.DateUtil;


public class Test {
	float valOri = 10000;
	float rate = 3;
	int lifeTime = 5;
	
	public static float calculate(float originVal,float valRate,int lifeTime,int usedMonths){
		
		float res = (float) (originVal*(100-valRate)/lifeTime*0.01/12);
		float currentVal = originVal-res*usedMonths;
		return currentVal;
	}
	public static int calUsedMoths(){
		long dateNow = DateUtil.getDateNumber(new Date());
		
		long dateInstall = DateUtil.getDateNumber(DateUtil.convertStringToDate("2016-02-01", "yyyy-MM-dd"));
		float u1 = (dateNow-dateInstall)/DateUtil.MONTH;
		int usedMonths = (int) Math.rint((dateNow-dateInstall)/DateUtil.MONTH);
		return usedMonths;
	}
	public static long getLastDayOfMonth(){
		
	    Calendar calendar = Calendar.getInstance();  
	    
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
	      
	    // 打印  
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	    System.out.println(DateUtil.getDateNumber(new Date()));
	    System.out.println(DateUtil.getDateNumber(calendar.getTime()));
		return 0;
	}
	
	public static Date getDateLater(Date date){
		SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 3);
		System.out.println("增加月份后的日期："+sdf.format(calendar.getTime()));
		
		return calendar.getTime();
	}
	
	public static void main(String args[]) throws ParseException{
		SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(calculate(10000,3,5,3));
		System.out.println(getLastDayOfMonth());
		System.out.println(getDateLater(sdf.parse("2016-05-03")));
	}
}


