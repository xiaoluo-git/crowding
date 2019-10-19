package com.atguigu.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.atguigu.scw.project.mapper")
@EnableTransactionManagement
public class ScwProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwProjectApplication.class, args);
	}

}
