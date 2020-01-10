package com.jdry.pms.comm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Http工具
 * 
 * @author liucq
 * @date 2016年10月19日
 *
 */
public class HttpConnectionUtil {

	// post请求
	public static final String HTTP_POST = "POST";

	// get请求
	public static final String HTTP_GET = "GET";

	// utf-8字符编码
	public static final String CHARSET_UTF_8 = "UTF-8";

	// HTTP内容类型。如果未指定ContentType，默认为TEXT/HTML
	public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

	// HTTP内容类型。相当于form表单的形式，提交暑假
	public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

	// HTTP内容类型。json格式
	public static final String CONTENT_TYPE_TEXT_JSON = "application/json;charset=UTF-8";

	// 请求超时时间
	public static final int SEND_REQUEST_TIME_OUT = 5000;

	// 将读超时时间
	public static final int READ_TIME_OUT = 5000;

	/**
	 * Post 请求
	 * 
	 * @param httpUrl
	 *            请求地址
	 * @param headers
	 *            头部参数
	 * @param body
	 *            正文信息
	 * @return
	 */
	public static String doPost(String httpUrl, Map<String, String> headers, String body) {

		// 是否有http正文提交
		boolean isDoInput = false;
		if (StringUtils.isNotBlank(body)) {
			isDoInput = true;
		}
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			// 统一资源
			URL url = new URL(httpUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			if (isDoInput) {
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
			}
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpURLConnection.setDoInput(true);
			// 设置一个指定的超时值（以毫秒为单位）
			httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
			// 将读超时设置为指定的超时，以毫秒为单位。
			httpURLConnection.setReadTimeout(READ_TIME_OUT);
			// Post 请求不能使用缓存
			httpURLConnection.setUseCaches(false);

			// 设置请求头信息
			if (headers != null) {
				Iterator<String> iterator = headers.keySet().iterator();
				String key = null;
				String value = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					value = headers.get(key);
					httpURLConnection.addRequestProperty(key, value);
				}
			}

			// 设置字符编码
			httpURLConnection.setRequestProperty("Accept-Charset", CHARSET_UTF_8);
			// 设置内容类型
			httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_TEXT_JSON);
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod(HTTP_POST);

			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			// 如果在已打开连接（此时 connected 字段的值为 true）的情况下调用 connect 方法，则忽略该调用。
			httpURLConnection.connect();

			if (isDoInput) {
				outputStream = httpURLConnection.getOutputStream();
				outputStreamWriter = new OutputStreamWriter(outputStream,CHARSET_UTF_8);
				outputStreamWriter.write(body);
				outputStreamWriter.flush();// 刷新
			}
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}

			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream,CHARSET_UTF_8);
				reader = new BufferedReader(inputStreamReader);

				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					resultBuffer.append("\n");
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {// 关闭流
			try {
				if (outputStreamWriter != null) {
					outputStreamWriter.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultBuffer.toString();
	}
}
