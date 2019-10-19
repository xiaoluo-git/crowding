package com.atguigu.scw.project.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.project.resp.vo.TMember;
import com.atguigu.scw.project.service.feign.exception.handler.MemberServiceFeignExceptionHandler;
import com.atguigu.scw.vo.resp.AppResponse;

@FeignClient(value="SCW-USER",fallback=MemberServiceFeignExceptionHandler.class)
public interface MemberServiceFeign {
	
	@GetMapping("/user/info/getMemberById")
	public AppResponse<TMember> getMemberById(@RequestParam("memberId")Integer memberId);
	
	
}
