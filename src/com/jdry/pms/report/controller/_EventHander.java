package com.jdry.pms.report.controller;

import java.util.List;

/**
 * 描述：事件发生时间分析
 * @author hezuping
 * 时间：2016年12月21日10:21:59
 */
public class _EventHander 
{
	/**
	 * 事件发生时间数据组装
	 * @param ls
	 * @return string
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String combQuery(List ls,int send_week)
	{   
		String str="";
		String str2="";
		String myWeek=send_week+"";
		if(ls==null)
		{
			str+=getStr(str2,myWeek);
		}else
		if(ls.size()>0)
		{
			for(int i=0;i<ls.size();i++)
			{
				Object[] obj=(Object[]) ls.get(i);
				String week=null==obj[2]?"":obj[2].toString();
				String hours=null==obj[1]?"":obj[1].toString();
				String num=null==obj[0]?"0":obj[0].toString();
				if(!week.equals("")&&(!hours.equals("")))
				{
					str2+=hours+",";
					str+=findEventNumByHours(Integer.parseInt(week),Integer.parseInt(hours),Integer.parseInt(num));
				}
			}
			str+=getStr(str2,myWeek);
		}
		return str;

	}
	/**
	 * 数据组装
	 * @param week
	 * @param hours
	 * @param num
	 * @return
	 */
	public static String findEventNumByHours(int week,int hours,int num)
	{
		String str01="";
		if(week!=0)
		{
			int week_index=week-1;
			str01="["+week_index+","+hours+","+num+"],";	
		}else
		{
			str01="[6,"+hours+","+num+"],";		
		}
		return str01;
	}

	//空数据拼装
	public static String getStr(String str2,String myWeek)
	{
		String str01="";//接收拼装数据
		String[] arr1 =Week.str1.split(",") ;
		String arr2[] = str2.split(",") ; 
		for (int i = 0; i < arr2.length; i++)
		{
			for (int j = 0; j < arr1.length; j++)
			{
				if (arr1[j].equals(arr2[i]))
				{
					arr1[j] = "" ;
				}
			}
		}

		StringBuffer sb = new StringBuffer() ;
		for (int j = 0; j < arr1.length; j++)
		{
			if (!"".equals(arr1[j]) )
			{
				sb.append(arr1[j] + ",") ;
			}
		}

		String new_index[]=sb.toString().split(",");
		if(!myWeek.equals("0"))
		{
			int week_index=Integer.parseInt(myWeek)-1;
			for(int i=0;i<new_index.length;i++)
			{
				if(i==0)
				{
					str01+="["+week_index+","+new_index[i]+",0]";
				}else
				{
					str01+=",["+week_index+","+new_index[i]+",0]";

				}
			}
		}else
		{
			for(int i=0;i<new_index.length;i++)
			{
				if(i==0)
				{
					str01+="[6,"+new_index[i]+",0]";
				}else
				{
					str01+=",[6,"+new_index[i]+",0]";

				}
			}
		}
		return str01;
	}

}
