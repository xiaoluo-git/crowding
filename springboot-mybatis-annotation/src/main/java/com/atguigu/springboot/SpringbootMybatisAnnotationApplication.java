package com.atguigu.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.atguigu.springboot.mapper")
@SpringBootApplication
public class SpringbootMybatisAnnotationApplication {
	

	public static void main(String[] args) {
		
		SpringApplication.run(SpringbootMybatisAnnotationApplication.class, args);
	}

}
