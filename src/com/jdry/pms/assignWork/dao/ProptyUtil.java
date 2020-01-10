package com.jdry.pms.assignWork.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProptyUtil{

	 public String getProperty(String propertyKey){
    	InputStream in = getClass().getResourceAsStream("JDRY.properties");
    	Properties pro = new Properties();
	    try {
			pro.load(in);
		} catch (IOException e) {
		}
	    String postionId =pro.getProperty(propertyKey).trim();
	    return postionId;
	 }
}
