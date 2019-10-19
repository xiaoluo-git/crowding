package com.atguigu.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@Api("Hello接口")
public class HelloController {
	@ApiOperation("测试hello1")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name="name",value="名字",required=true)	
	})
	@ApiResponse(message="ok", code = 0)
	@GetMapping("/hello")
	public String hello(String name) {
		return "OK:"+name;
	}

}
