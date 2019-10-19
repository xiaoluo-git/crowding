package com.atguigui.springcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigui.springcloud.bean.Movie;
import com.atguigui.springcloud.bean.User;
import com.atguigui.springcloud.service.UserService;
import com.atguigui.springcloud.service.impl.MovieServiceFeign;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MovieServiceFeign movieServiceFeign;
	
	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable("id") Integer id) {
		
		return userService.getUserById(id);
	}
	
	@GetMapping("/buyMovieTicket/{userId}/{movieId}")
	public Map<String,Object> buyMovieTicket(@PathVariable("userId") Integer userId,
			@PathVariable("movieId") Integer movieId){
		Map<String,Object> map = new HashMap<>();
		User user = userService.getUserById(userId);
		map.put("user", user);
		Movie movie = movieServiceFeign.getMovieById(movieId);
		map.put("movie", movie);
		return map;
	}
	
}
