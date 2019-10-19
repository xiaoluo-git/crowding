package com.atguigui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CloudProviderMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudProviderMovieApplication.class, args);
	}

}
