package com.atguigu.scw.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.req.vo.OrderInfoSubmitVo;
import com.atguigu.scw.order.service.OrderService;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api("订单模块")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("order/saveOrder")
	AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo orderInfoSubmitVo){
		
		try {
			log.debug("保存订单信息orderInfoSubmitVo={}",orderInfoSubmitVo);
			TOrder order = orderService.saveOrder(orderInfoSubmitVo);
			AppResponse<TOrder> appResponse = AppResponse.ok(order) ;
			return appResponse;
		} catch (Exception e) {
			e.printStackTrace();
			AppResponse<TOrder> appResponse = AppResponse.fail(null);
			appResponse.setMsg("保存订单失败");
			log.debug("保存订单失败");
			return appResponse;
		}
	}
}
