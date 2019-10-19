package com.atguigu.scw.webui.service.exception.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.resp.vo.UserAddressVo;
import com.atguigu.scw.webui.resp.vo.UserResponseVo;
import com.atguigu.scw.webui.service.UserServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserServiceFeignExceptionHandler implements UserServiceFeign {

	@Override
	public AppResponse<UserResponseVo> login(String loginacct, String password) {
		AppResponse<UserResponseVo> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用SCW-USER[登录]服务异常");
		log.error("调用SCW-USER[登录]服务异常");
		return appResponse;
	}

	@Override
	public AppResponse<List<UserAddressVo>> address(String accessToken) {
		AppResponse<List<UserAddressVo>> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用SCW-USER[查询用户地址]服务异常");
		log.error("调用SCW-USER[查询用户地址]服务异常");
		return appResponse;
	}

	

}
