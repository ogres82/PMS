package com.jdry.pms.comm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hsqldb.lib.StringUtil;
/*
 * author 钟涛
 */
public class DateUtil {
	public static final long SECOND = 1000;

	public static final long MINUTE = SECOND * 60;

	public static final long HOUR = MINUTE * 60;

	public static final long DAY = HOUR * 24;

	public static final long WEEK = DAY * 7;

	public static final long MONTH = DAY * 30;

	public static final long YEAR = DAY * 365;

	public static final String TYPE_DATE = "date";

	public static final String TYPE_TIME = "time";

	public static final String TYPE_DATETIME = "datetime";
	
	/**
	 * 模式:yyyy-MM-dd HH:mm
	 */
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm";

	/**
	 * 模式:yyyy-MM-dd
	 */
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	private static final String DEFAULT_PATTERN = PATTERN_DATETIME;

	public static final String[] TYPE_ALL = { TYPE_DATE, TYPE_DATETIME,
			TYPE_TIME };

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param strDate
	 * @param pattern
	 *            格式
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate, String pattern) {
		if (StringUtil.isEmpty(strDate))
			return null;
		if (StringUtil.isEmpty(pattern))
			pattern = DEFAULT_PATTERN;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param strDate
	 * @param type
	 *            见本类中的TYPE_*常量
	 * @param locale
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate, String type,
			Locale locale) {
		if (StringUtil.isEmpty(strDate))
			return null;
		if (StringUtil.isEmpty(type))
			type = TYPE_DATETIME;
		String pattern = "yyyy-MM-dd HH:mm";
		RuntimeException rtnE;
		try {
			return convertStringToDate(strDate, pattern);
		} catch (RuntimeException e) {
			rtnE = e;
		}
		for (int i = 0; i < TYPE_ALL.length; i++) {
			pattern = "yyyy-MM-dd HH:mm";
			try {
				return convertStringToDate(strDate, pattern);
			} catch (RuntimeException e) {
			}
		}
		throw rtnE;
	}

	/**
	 * 将Date转换为字符串
	 * 
	 * @param aDate
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String convertDateToString(Date aDate, String pattern) {
		if (aDate == null)
			return null;
		if (StringUtil.isEmpty(pattern))
			pattern = DEFAULT_PATTERN;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(aDate);
	}

	/**
	 * 将Date转换为字符串
	 * 
	 * @param aDate
	 * @param type
	 *            见本类中的TYPE_*常量
	 * @param locale
	 * @return
	 */
	public static String convertDateToString(Date aDate, String type,
			Locale locale) {
		if (aDate == null)
			return null;
		if (type == null)
			type = TYPE_DATETIME;
		String pattern = "yyyy-MM-dd HH:mm";
		return convertDateToString(aDate, pattern);
	}

	/**
	 * 将日期、时间合并成长整型数据
	 * 
	 * @param date
	 *            日期
	 * @param time
	 *            时间
	 * @return
	 */
	public static long getDateTimeNumber(Date date, Date time) {
		Calendar dateCal = getCalendar(date);
		Calendar timeCal = getCalendar(time);
		dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
		dateCal.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND));
		return dateCal.getTimeInMillis();
	}

	/**
	 * 将日期的时间部分清除后，转换成long类型
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateNumber(Date date) {
		return removeTime(date).getTimeInMillis();
	}

	/**
	 * 获取日期(获取当天日期getDate(0))
	 * 
	 * @param day
	 * @return
	 */
	public static Date getDate(int day) {
		Calendar cal = getCalendar(new Date());
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 将时间的日期部分清除后，转换成long类型
	 * 
	 * @param date
	 * @return
	 */
	public static long getTimeNubmer(Date date) {
		return getCalendar(date).getTimeInMillis() - getDateNumber(date);
	}

	/**
	 * 将一个不包含日期的时间量，转换为Date类型，其中的日期为当天
	 * 
	 * @param l
	 * @return
	 */
	public static Date getTimeByNubmer(long l) {
		return new Date(getDateNumber(new Date()) + l);
	}

	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		return cal;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Calendar removeTime(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = getCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	/* 
	 * 将形如"Fri Nov 05 15:36:49 CST 2010"格式的字符串
	 */
	public static Date parseToDate(String dateString){
		Date rtnDate=null;
	    try {
	    	SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM DD HH:mm:ss z yyyy", Locale.ENGLISH);
		    SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    rtnDate=sdf1.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return rtnDate;
	}
	
	/**
     * 把long 转换成 日期 再转换成String类型
     */
    public String convertLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
}
