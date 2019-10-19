package com.atguigu.scw.order.service.exception.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.order.resp.vo.TReturn;
import com.atguigu.scw.order.service.feign.ProjectServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProjectServiceFeignExceptionHandler implements ProjectServiceFeign {

	@Override
	public AppResponse<TReturn> getReturnById(Integer returnId) {
		AppResponse<TReturn> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用SCW-PROJECT服务[根据回报ID查询回报]失败");
		log.debug("调用SCW-PROJECT服务[根据回报ID查询回报]失败");
		return appResponse;
	}

}
