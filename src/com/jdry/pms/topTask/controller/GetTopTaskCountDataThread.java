package com.jdry.pms.topTask.controller;

import java.util.HashMap;



/***
 * 描述：执行公共存储过程获取待办数据的线程
 * @author hezuping
 * 2016-06-03 09:13:57
 *
 */
public class GetTopTaskCountDataThread extends Thread
{

	  private String procedureName;
	  private String userId;
	  private HashMap countMap = new HashMap();
		/**
		 * 构造方法
		 * 
		 * @param threadName
		 *            线程名
		 * @param procedureName
		 *            存储过程名
		 * @param userId
		 *            用户ID
		 */
	  public GetTopTaskCountDataThread(String threadName, String procedureName, String userId)
	  {
	    super(threadName);
	    this.procedureName = procedureName;
	    this.userId = userId;
	  }

		/**
		 * 线程执行完毕后获取待办数据
		 * 
		 * @return
		 */
	  public HashMap getCountMap()
	  {
	    return this.countMap;
	  }

	  @Override
	public void run()
	  {
		  
	   // try
	    //{
	      //DBManager db = DBManager.getConnection();
	      //db.setSqlValue("PKG_TOPTASKREPORT." + this.procedureName.trim());
	      //db.setParamet(this.userId);
	     // db.setOutCursor();
	  	  //List<List<HashMap>> list = (List<List<HashMap>>) db.executeProcPreReS(true);
	     // if ((list != null) && (list.size() > 0))
	       // this.countMap = ((HashMap)((List)list.get(0)).get(0));
	    //}
	   // catch (Throwable e) {
	      //LogDao.ThrowableErrprLog(e);
	      //e.printStackTrace();
	    //}
	  }
	}
