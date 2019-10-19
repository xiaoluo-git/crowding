package com.atguigu.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.resp.vo.UserAddressVo;
import com.atguigu.scw.webui.resp.vo.UserResponseVo;
import com.atguigu.scw.webui.service.exception.handler.UserServiceFeignExceptionHandler;

@FeignClient(value="SCW-USER",fallback=UserServiceFeignExceptionHandler.class)
public interface UserServiceFeign {
	
	@PostMapping("/user/login")
	public AppResponse<UserResponseVo> login(@RequestParam("loginacct") String loginacct,@RequestParam("password")String password);
	
	@GetMapping("/user/info/address")
	public AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken") String accessToken);
}
