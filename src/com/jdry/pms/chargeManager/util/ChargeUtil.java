package com.jdry.pms.chargeManager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChargeUtil {

	public static Date StrToDate(String str) {		  
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}
	
	public static Date StrToDateTime(String str) {		  
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}
}
