package com.atguigu.scw.order.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.order.resp.vo.TReturn;
import com.atguigu.scw.order.service.exception.handler.ProjectServiceFeignExceptionHandler;
import com.atguigu.scw.vo.resp.AppResponse;

@FeignClient(value="SCW-PROJECT",fallback=ProjectServiceFeignExceptionHandler.class)
public interface ProjectServiceFeign {
	
	@GetMapping("/project/getReturnById")
	public AppResponse<TReturn> getReturnById(@RequestParam("returnId")Integer returnId);
}
