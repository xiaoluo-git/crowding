package com.atguigu.scw.webui.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.req.vo.OrderInfoSubmitVo;
import com.atguigu.scw.webui.resp.vo.TOrder;
import com.atguigu.scw.webui.service.exception.handler.OrderServiceFeignExceptionHandler;

@FeignClient(value="SCW-ORDER",fallback=OrderServiceFeignExceptionHandler.class)
public interface OrderServiceFeign {
	
	@GetMapping("order/saveOrder")
	AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo orderInfoSubmitVo);
	
	
}
