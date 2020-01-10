package com.jdry.pms.appversion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.controller.IController;

@Component
public class DownloadLatestApp implements IController{

	@Override
	public boolean anonymousAccess() {
		return true;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		if(method.equalsIgnoreCase("android_wgend")){
			downloadFile(arg0,arg1,"C:\\pms\\appversion\\lwwy_wg_android.apk");
		}
		
		if(method.equalsIgnoreCase("android_yzend")){
			downloadFile(arg0,arg1,"C:\\pms\\appversion\\lwwy_yz_android.apk");
		}
		
		if(method.equalsIgnoreCase("ios_wgend")){
			downloadFile(arg0,arg1,"C:\\pms\\appversion\\lwwy_wg_ios.ipa");
		}
		
		if(method.equalsIgnoreCase("ios_yzend")){
			downloadFile(arg0,arg1,"C:\\pms\\appversion\\lwwy_yz_ios.ipa");
		}
	}

	@Override
	public String getUrl() {
		return "/downloadApp";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
	
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filepath){
		 // path是指欲下载的文件的路径。
		try {
		    File file = new File(filepath);
		    String filename = file.getName();
		    // 以流的形式下载文件。
		    InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
		    byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
		    fis.close();
		    // 清空response
		    response.reset();
		    // 设置response的Header
		    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
		    response.addHeader("Content-Length", "" + file.length());
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    toClient.write(buffer);
		    toClient.flush();
		    toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
