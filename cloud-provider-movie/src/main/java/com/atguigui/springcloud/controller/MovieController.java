package com.atguigui.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.atguigui.springcloud.bean.Movie;
import com.atguigui.springcloud.service.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("getMovieById/{id}")
	public Movie getMovieById(@PathVariable("id") Integer id) {
		
		return movieService.getMovieById(id);
	}
}
