package com.jdry.pms.test;

import com.jdry.pms.comm.util.RedisPool;

import redis.clients.jedis.Jedis;

public class TestRedis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisObject();
			jedis.set("lwyx", "testeskljtdsjfkldjsklfjdklsjfld");
			jedis.setex("lwyx1", 60, "fdjsfjdksjfkdlsj");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			RedisPool.recycleJedisOjbect(jedis);
		}

	}

}
