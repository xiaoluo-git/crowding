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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable("id") Integer id) {
		
		return userService.getUserById(id);
	}
	
	@HystrixCommand(fallbackMethod="buyMovieTicketHystrix")
	@GetMapping("/buyMovieTicket/{userId}/{movieId}")
	public Map<String,Object> buyMovieTicket(@PathVariable("userId") Integer userId,
			@PathVariable("movieId") Integer movieId){
		Map<String,Object> map = new HashMap<>();
		User user = userService.getUserById(userId);
		map.put("user", user);
		Movie movie = restTemplate.getForObject("http://CLOUD-PROVIDER-MOVIE/getMovieById/" + movieId, Movie.class);
		map.put("movie", movie);
		return map;
	}
	
	public Map<String,Object> buyMovieTicketHystrix(@PathVariable("userId") Integer userId,
			@PathVariable("movieId") Integer movieId){
		Map<String,Object> map = new HashMap<>();
		User user = new User();
		user.setUserId(-1);
		user.setUserName("无此用户");
		map.put("user", user);
		Movie movie = new Movie();
		movie.setMovieId(-1);
		movie.setMovieName("无此电影");
		map.put("movie", movie);
		return map;
		
	}
	
}
