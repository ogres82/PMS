package com.jdry.pms.comm.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {
	
	private static final String ISO88591_CHARSETNAME = "ISO8859-1";
	private static final String UTF8_CHARSETNAME = "UTF-8";
	
	/**
	 * 字符串字符编码转换		ISO8859-1 转 UTF-8
	 * @param str
	 * @return
	 */
	public static String transformISO88591ToUTF8(String str){
		return transform(str, ISO88591_CHARSETNAME, UTF8_CHARSETNAME);
	}
	/**
	 * 字符串字符编码转换
	 * @param str
	 * @param srcCharsetName	源编码
	 * @param destCharsetName	目标编码
	 * @return
	 */
	public static String transform(String str, String srcCharsetName, String destCharsetName){
		if(isBlank(str) || isBlank(srcCharsetName) || isBlank(destCharsetName)){
			return null;
		}
		try {
			return new String(str.getBytes(srcCharsetName), destCharsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断字符串str是否为空 如果不为空判断长度是否小于len  如果小于len则返回true 否则返回false
	 * @param str
	 * @param len
	 * @return
	 */
	public static boolean isNotBlankAndLessThanLen(String str, Integer len){
		if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
			if(str.length() > len){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	/**
	 * 调用 org.apache.commons.lang.StringUtilss 的isNotBlank方法
	 * @param str
	 * @param len
	 * @return
	 */
	public static boolean isNotBlank(String str){
		if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 调用 org.apache.commons.lang.StringUtilss 的isBlank方法
	 * @param str
	 * @param len
	 * @return
	 */
	public static boolean isBlank(String str){
		return org.apache.commons.lang.StringUtils.isBlank(str);
	}
	
	/**
	 * 获取指定字符个数
	 * @param ch 被指定的字符
	 * @param str
	 * @return
	 */
	public static int assignCharCount(String str, char ch) {
		char list[] = str.toCharArray();
		int num = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] == ch) {
				num++;
			}
		}
		return num;
	}
	
	public static boolean isExist(String tmp){
		boolean isOk = false;
		char [] tmpchar = tmp.toCharArray();
		for (int i = 0; i < tmpchar.length; i++) {
			for (int j = i+1; j < tmpchar.length; j++) {
				if(tmpchar[i] == tmpchar[j]){
					isOk = true;
					return isOk;
				}else{
					isOk = false;
				}
			}
		}
		return isOk;
	}
	
	/**
	 * 把第一个字母变成大写
	 */
	public static String upperFirstLetter(String str){
		return str.substring(0, 1).toUpperCase()
		+ str.substring(1);
	}
	
	/**
	 * 将一个日期转换成String 
	 * 方法名：getDateString<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月11日-下午9:59:14 <BR>
	 * 
	 * @param date
	 * @param pattern
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String formatDateToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期字符串转换成Date 方法名：getDateString<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月11日-下午10:04:06 <BR>
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 *             Date<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static Date parseStringToDate(String dateString, String pattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(dateString);
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));           
    }
    
    /** 
     * 计算两个日期之间相差的天数  
     * 字符串的日期格式的计算 
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @throws ParseException 
     */  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));     
    }
    
    /**
     * 计算两个日期之间相差的毫秒数
     * 方法名：timesBetween
     * 创建人：liuzhangcheng
     * 时间：2015-6-18-下午5:04:51
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return
     * @throws ParseException long
     * @exception
     * @since  1.0.0
     */
    public static long timesBetween(Date smdate,Date bdate) throws ParseException {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        return time2-time1;           
    }

	/**
	 * 
	 * 将小数格式化成字符串，会进行四舍五入 如：3656.4554===结果:3656.46<BR>
	 * 方法名：formatDoubleToString<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月12日-下午9:12:01 <BR>
	 * @param dou
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String formatDoubleToString(double dou,String format) {
		if(isEmpty(format))format = "#.##";
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String string = decimalFormat.format(dou);// 四舍五入，逢五进一
		return string;
	}
	
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
	
	/**
	 * 非空判断
	 * 方法名：isNotEmpty<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月12日-下午9:36:18 <BR>
	 * @param str
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 空字符串返回""
	 * 方法名：isEmptyBackBlank<BR>
	 * 创建人：刘章程 <BR>
	 * 时间：2015年11月17日-上午8:56:33 <BR>
	 * @param str
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	*/
	public static String isEmptyBackBlank(String str) {
		if( isEmpty(str) ){
			str = "";
		}
		return str;
	}
	
	/**
	 * 空字符串返回"0"
	 * 方法名：isEmptyBackZero<BR>
	 * 创建人：刘章程 <BR>
	 * 时间：2015年11月17日-上午9:30:09 <BR>
	 * @param str
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	*/
	public static String isEmptyBackZero(String str) {
		if( isEmpty(str) ){
			str = "0";
		}
		return str;
	}
	
	/**
	 * 百分比转换
	 * 方法名：getPercent<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月12日-下午9:50:46 <BR>
	 * @param num
	 * @param totalCount
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getPercent(int num,double totalCount,String...objects){
		String format = "#.##";
		if(objects!=null && objects.length>0){
			format = objects[0];
		}
		return StringUtils.formatDoubleToString((num/totalCount)*100,format)+"%";
	}
	
	/**
	 * 百分比转换
	 * 方法名：getPercent<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月12日-下午9:50:46 <BR>
	 * @param num 当前的数字
	 * @param totalCount 总数
	 * @param format 
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getPercent(int num,float totalCount,String...objects){//动态参数
		String format = "#.##";
		if(objects!=null && objects.length>0){
			format = objects[0];
		}
		return StringUtils.formatDoubleToString((num/totalCount)*100,format)+"%";
	}
	
	
	
	/**
	 *冒泡排序方法,如果为true那就是降序，false那么久是升序 
	 * 方法名：sorts<BR>
	 * 创建人：liuzhangcheng <BR>
	 * 时间：2014年8月12日-下午11:35:55 <BR>
	 * @param datas
	 * @param flag
	 * @return int[]<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static int[] sorts(int[] datas,boolean flag){
		for (int i = 0; i < datas.length; i++) {//轮询次数
			for(int j=0; j < datas.length-1; j++){//交换次数
				if(flag){ 
					if(datas[j] < datas[j+1]){
						int temp = datas[j];
						datas[j] = datas[j+1];
						datas[j+1] = temp;
					}
				}else{
					if(datas[j] < datas[j+1]){
						int temp = datas[j];
						datas[j] = datas[j+1];
						datas[j+1] = temp;
					}
				}
			}
		}
		return datas;
	}
	
	/**
	 * 文件后缀处理
	 * @param oldExt
	 * @return
	 */
	public static String ext(String oldExt){
		String result =oldExt;
		if(!oldExt.equals("") && oldExt!=null){
			if(oldExt.toLowerCase().equals("xlsx") || oldExt.toLowerCase().equals("xlsx"))result = "xls";
			if(oldExt.toLowerCase().equals("docx") || oldExt.toLowerCase().equals("doc"))result = "word";
		}
		return result;
	}
	
	public static boolean isImage(String ext){
		return ext.toLowerCase().matches("jpg|gif|bmp|png|jpeg");
	}
	
	public static boolean isDoc(String ext){
		return ext.toLowerCase().matches("doc|docx|xls|xlsx|pdf|txt|ppt|pptx|rar|zip|html|jsp|sql|htm|shtml|xml");
	}
	
	public static boolean isVideo(String ext){
		return ext.toLowerCase().matches("mp4|flv|mp3|rmbv|avi");
	}
	
	/**
	 * 
	 * 字符对应转换特定字符
	 * 方法名：transMeaning
	 * 创建人：liuzhangcheng
	 * 时间：2015-4-27-上午9:52:13
	 * @param transMeaning （例 ：1:是|2:否） “|”1级分割符，“:”二级分割符
	 * @param textValue（例：1）
	 * @return String（例：是）
	 * @exception
	 * @since  1.0.0
	 */
	public static String transMeaning (String transMeaning,String textValue){
		if (!StringUtils.isEmpty(textValue) && !textValue.equalsIgnoreCase("null")) {
			String[] transMeaningSz1 = transMeaning.split("\\|");
			for(int j1 = 0; j1 < transMeaningSz1.length; j1++){
				String[] transMeaningSz2 = transMeaningSz1[j1].split(":"); 
				if(textValue.equals(transMeaningSz2[0])){
					textValue = transMeaningSz2[1];
				}
			}
		}else {
			textValue="";
		}
		return textValue;
	}
	
	/**
	 * 字符串中间添加%
	 * 方法名：toAddPercent<BR>
	 * 创建人：刘章程 <BR>
	 * 时间：2015年11月11日-下午4:29:56 <BR>
	 * @param textValue
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String toAddPercent(String textValue){
		StringBuffer output = new StringBuffer("");
		if (!StringUtils.isEmpty(textValue) && !textValue.equalsIgnoreCase("null")) {
			char[] textChar = textValue.trim().toCharArray();  
			for (char c : textChar) {
                 output.append( c + "%" );
			}
			if( output.length() > 0 ){
				output.deleteCharAt( output.length() - 1 );
			}
		}
		return output.toString();
	}
}
