package com.atguigu.scw.project.service.feign.exception.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.project.resp.vo.TMember;
import com.atguigu.scw.project.service.feign.MemberServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberServiceFeignExceptionHandler implements MemberServiceFeign {

	@Override
	public AppResponse<TMember> getMemberById(Integer memberId) {
		AppResponse<TMember> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用远程SRC-USER服务[根据用户id获取用户信息]失败");
		log.error("调用远程SRC-USER服务[根据用户id获取用户信息]失败");
		return appResponse;
	}

}
