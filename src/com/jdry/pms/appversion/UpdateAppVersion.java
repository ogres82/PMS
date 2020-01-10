package com.jdry.pms.appversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class UpdateAppVersion {

	/**
	 * 获取物业App版本信息
	 * @param data
	 * @return
	 */
	public String getWyAppVersion(String data){
		JSONObject obj = JSON.parseObject(data);
		String appType = obj.getString("appType");
		String url = obj.getString("url");
		String filePath = "C:\\pms\\appversion\\lwwy_wg.txt";
		if(appType.equals("android_wgend")){
			filePath = "C:\\pms\\appversion\\lwwy_wg_android.txt";
			url+="/downloadApp.app?method=android_wgend";
		}else if(appType.equals("android_yzend")){
			filePath = "C:\\pms\\appversion\\lwwy_yz_android.txt";
			url+="/downloadApp.app?method=android_yzend";
		}else if(appType.equals("ios_wgend")){
			filePath = "C:\\pms\\appversion\\lwwy_wg_ios.txt";
			url+="/downloadApp.app?method=ios_wgend";
		}else if(appType.equals("ios_yzend")){
			filePath = "C:\\pms\\appversion\\lwwy_yz_ios.txt";
			url+="/downloadApp.app?method=ios_yzend";
		}else{
			return "{\"status\":\"0\",\"message\":\"参数不正确\"}";
		}
		String msg = readTxtFile(filePath);
		if(msg==null || msg.equals("false")){
			return "{\"status\":\"0\",\"message\":\"获取失败\"}";
		}else{
			System.out.println("{\"status\":\"1\",\"message\":\"获取成功\",\"data\":{\"version\":\""+msg+"\",\"downloadpath\":\""+url+"\"}}");
			return "{\"status\":\"1\",\"message\":\"获取成功\",\"data\":{\"version\":\""+msg+"\",\"downloadpath\":\""+url+"\"}}";
		}
	}
	
	public static String readTxtFile(String filePath){
		try {
			String encoding="GBK";
			File file=new File(filePath);
			if(file.isFile() && file.exists()){ //判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file),encoding);//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
					System.out.println(lineTxt);
					return lineTxt;
				}
				read.close();
				return "false";
			}else{
				// 如果文件不存在,则创建  
				file.createNewFile();
				writeTxtFile(filePath,"no version");
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		
	}
	
	
	public static String writeTxtFile(String filePath,String content){
		try {
			File file = new File(filePath);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			return "false";
		} catch (IOException e) {
			e.printStackTrace();
			return "true";
		}
	}
	     
	public static void main(String argv[]){
		String filePath = "C:\\pms\\appversion\\lwwy_yz.txt";
		writeTxtFile(filePath,"test");
		readTxtFile(filePath);
	}
}
