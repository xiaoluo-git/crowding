package com.atguigu.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.atguigu.springboot.mapper")
@SpringBootApplication
public class SpringbootMybatisXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisXmlApplication.class, args);
	}

}
