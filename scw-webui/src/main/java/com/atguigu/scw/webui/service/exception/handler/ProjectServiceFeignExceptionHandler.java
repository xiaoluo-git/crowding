package com.atguigu.scw.webui.service.exception.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.resp.vo.ReturnPayConfirmVo;
import com.atguigu.scw.webui.resp.vo.TProjectDetailsVo;
import com.atguigu.scw.webui.resp.vo.TProjectResponseVo;
import com.atguigu.scw.webui.service.ProjectServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProjectServiceFeignExceptionHandler implements ProjectServiceFeign {

	@Override
	public AppResponse<List<TProjectResponseVo>> all() {
		AppResponse<List<TProjectResponseVo>> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用远程Project接口【查询热点项目】服务失败");
		log.debug("调用远程Project接口【查询热点项目】服务失败");
		return appResponse;
	}

	@Override
	public AppResponse<TProjectDetailsVo> detail(Integer projectId) {
		AppResponse<TProjectDetailsVo> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用远程Project接口[查询项目详细信息]服务失败");
		log.debug("调用远程Project接口[查询项目详细信息]服务失败");
		return appResponse;
	}

	@Override
	public AppResponse<ReturnPayConfirmVo> suport(Integer projectId, Integer returnId) {
		AppResponse<ReturnPayConfirmVo> appResponse = AppResponse.fail(null);
		appResponse.setMsg("调用远程Project接口[查询项目回报信息]服务失败");
		log.debug("调用远程Project接口[查询项目回报信息]服务失败");
		return appResponse;
	}

}
