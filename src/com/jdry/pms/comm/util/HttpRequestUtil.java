/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-16 下午9:40:20
 * @description
 *
 */

package com.jdry.pms.comm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;

/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-16 下午9:40:20
 * @description 请求远程http接口工具，GET与POST
 *
 */
public class HttpRequestUtil {

	/**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Accept-Charset", "utf-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 Map<key,value> 的形式。
     * @return 所代表远程资源的响应结果
     */
	 public static String sendPost(String urls, Map<String, Object> paramMap) {
			String paramStr = JSON.toJSONString(paramMap);
			StringBuffer result = new StringBuffer();
			InputStream is = null;
			InputStreamReader inputStreamReader = null;
			OutputStream os = null;
			OutputStreamWriter outputStreamWriter = null;
			BufferedReader reader = null;
			URL url = null;
			try {
				// 打开和URL之间的连接
				url = new URL(urls);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				
				// 写入url操作
				os = conn.getOutputStream();
				outputStreamWriter = new OutputStreamWriter(os, "UTF-8");
				outputStreamWriter.write(paramStr);
				outputStreamWriter.flush();
				
				// 定义 BufferedReader输入流来读取URL的响应
				is = conn.getInputStream();
				inputStreamReader = new InputStreamReader(is, "UTF-8");
				reader = new BufferedReader(inputStreamReader);
				String line;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (inputStreamReader != null) {
					try {
						inputStreamReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStreamWriter != null) {
					try {
						outputStreamWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return result.toString();
		}
	    
}
