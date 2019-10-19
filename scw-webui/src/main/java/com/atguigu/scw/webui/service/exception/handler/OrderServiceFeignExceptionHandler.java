package com.atguigu.scw.webui.service.exception.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.req.vo.OrderInfoSubmitVo;
import com.atguigu.scw.webui.resp.vo.TOrder;
import com.atguigu.scw.webui.service.OrderServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderServiceFeignExceptionHandler implements OrderServiceFeign {

	@Override
	public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
		AppResponse<TOrder> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用SCW-ORDER服务[保存订单]失败");
		log.debug("调用SCW-ORDER服务[保存订单]失败");
		return appResponse;
	}

}
