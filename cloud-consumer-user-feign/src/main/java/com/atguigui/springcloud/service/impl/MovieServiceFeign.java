package com.atguigui.springcloud.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigui.springcloud.bean.Movie;
import com.atguigui.springcloud.service.exception.MovieServiceFeignExceptionHandler;

@FeignClient(value="CLOUD-PROVIDER-MOVIE",fallback=MovieServiceFeignExceptionHandler.class)
public interface MovieServiceFeign {
	
	@GetMapping("getMovieById/{id}")
	public Movie getMovieById(@PathVariable("id") Integer id);
}
