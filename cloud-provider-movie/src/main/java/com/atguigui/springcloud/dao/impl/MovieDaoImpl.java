package com.atguigui.springcloud.dao.impl;

import org.springframework.stereotype.Repository;

import com.atguigui.springcloud.bean.Movie;
import com.atguigui.springcloud.dao.MovieDao;

@Repository
public class MovieDaoImpl implements MovieDao {
	Movie movie = new Movie();

	@Override
	public Movie getMovieById(Integer id) {
		Movie movie = new Movie();
		movie.setMovieId(id);
		movie.setMovieName("功夫" + id);
		return movie;
	}
	
	

}
