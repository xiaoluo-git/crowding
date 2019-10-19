package com.atguigui.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.atguigui.springcloud.bean.Movie;
import com.atguigui.springcloud.dao.MovieDao;
import com.atguigui.springcloud.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieDao movieDao;
	
	@Value("${server.port}")
	private String port;

	@Override
	public Movie getMovieById(Integer id) {
		System.out.println("当前电影的服务端口PORT:"+ port);
		return movieDao.getMovieById(id);
	}
}
