package com.jdry.pms.TimeTask;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jdry.pms.comm.util.DateUtil;

public class EqpDataTaskListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
//	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
//	    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    calendar.add(Calendar.WEEK_OF_YEAR, 1);
		long delay = DateUtil.getDateNumber(calendar.getTime())-DateUtil.getDateNumber(new Date());
		new TimeManager(delay);
	}

}
