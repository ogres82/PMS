package com.jdry.pms.comm.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	
	private static JedisPool pool = null;    
	
	static Logger log = Logger.getLogger(RedisPool.class);

    //静态代码初始化池配置  
	
	private static void initialPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        // 是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(true);
        // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(100);
        // 最大连接数, 默认8个
        config.setMaxTotal(300);
        config.setMinIdle(20);
        //Idle时进行连接扫描
        config.setTestWhileIdle(true);
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "localhost", 6379, 10000 , "jdry1234redis");
	}

	/**
	 * 初始化jedis连接池
	 */
	private static synchronized void poolInit() {
        if (pool == null) {  
            initialPool();
        }
    }
    
    /**获得jedis对象*/
	
	public synchronized static Jedis getJedisObject() {  
        if (pool == null) {  
            poolInit();
        }
        Jedis jedis = null;
        if(pool != null){
        	try {  
        		if(jedis == null){
        			jedis = pool.getResource(); 
//        			log.info("获取redis连接！ ");
        		}
            } catch (Exception e) {  
            	e.printStackTrace();
                log.error("产生redis连接异常 : "+e);
            }
        }
        return jedis;
        
    } 


    /**归还jedis对象*/

    public static void recycleJedisOjbect(Jedis jedis){
    	if(jedis != null){
    		jedis.close();
//    		log.info("释放redis连接!");
    	}
    }
    
    


}
