package com.atguigu.scw.project.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.atguigu.scw.project.component.OOSTemplate;

@SpringBootConfiguration
public class AppOOSTemplateconfig {
	
	@Bean
	@ConfigurationProperties(prefix="oos")
	public OOSTemplate oosTemplate() {
		return new OOSTemplate();
	}
}
