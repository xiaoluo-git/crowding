package com.atguigu.scw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScwUserApplicationTests {

	Logger logger = LoggerFactory.getLogger(getClass()); // 引入日志文件

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	StringRedisTemplate stringTemplate; // k,v都是字符串

	@Test
	public void contextLoads() {
		/**
		 * string,list,set,zset,hash         redisTemplate.opsForXXX（）
		 */
		// redisTemplate.opsForValue().set("haha", "2");
		stringTemplate.opsForValue().set("msg", "哈哈");
		logger.debug("操作成功.....");
	}

}
