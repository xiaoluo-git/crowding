package com.atguigu.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.resp.vo.ReturnPayConfirmVo;
import com.atguigu.scw.webui.resp.vo.TProjectDetailsVo;
import com.atguigu.scw.webui.resp.vo.TProjectResponseVo;
import com.atguigu.scw.webui.service.exception.handler.ProjectServiceFeignExceptionHandler;

@FeignClient(value="SCW-PROJECT",fallback=ProjectServiceFeignExceptionHandler.class)
public interface ProjectServiceFeign {
	
	@GetMapping("/project/all/index")
	public AppResponse<List<TProjectResponseVo>> all();
	
	@GetMapping("/project/info/detail/{projectId}")
	public AppResponse<TProjectDetailsVo> detail(@PathVariable("projectId") Integer projectId);

	@GetMapping("/project/confirm/returns/{projectId}/{returnId}")
	public AppResponse<ReturnPayConfirmVo>  suport(@PathVariable("projectId") Integer projectId,
			@PathVariable("returnId") Integer returnId);
}
