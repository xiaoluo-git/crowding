package com.atguigui.springcloud.service.exception;

import org.springframework.stereotype.Component;

import com.atguigui.springcloud.bean.Movie;
import com.atguigui.springcloud.service.impl.MovieServiceFeign;

@Component
public class MovieServiceFeignExceptionHandler implements MovieServiceFeign {

	@Override
	public Movie getMovieById(Integer id) {
		Movie movie = new Movie();
		movie.setMovieId(-1);
		movie.setMovieName("无此电影");
		return movie;
	}

}
