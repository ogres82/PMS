/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-23 上午9:21:24
 * @description
 *
 */

package com.jdry.pms.comm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Copyright 2016 JDRY Co.Ltd
 * All rights reserved.
 * @Author 徐磊
 * @Created on 2016-12-23 上午9:21:24
 * @description
 *
 */
public class ObjectUtil {
	
	/**对象转byte[]
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] objectToBytes(Object obj) throws IOException{
    	ObjectOutputStream oos = null;
    	ByteArrayOutputStream baos = null;
    	try {
    	//序列化
	    	baos = new ByteArrayOutputStream();
	    	oos = new ObjectOutputStream(baos);
	    	oos.writeObject(obj);
	    	byte[] bytes = baos.toByteArray();
	    	return bytes;
    	} catch (Exception e) {
    	 
    	}
    	return null;
    }
    /**byte[]转对象
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws Exception{
    	ByteArrayInputStream bais = null;
    	try {
    	//反序列化
	    	bais = new ByteArrayInputStream(bytes);
	    	ObjectInputStream ois = new ObjectInputStream(bais);
	    	return ois.readObject();
    	} catch (Exception e) {
    	 
    	}
    	return null;
    }

}
