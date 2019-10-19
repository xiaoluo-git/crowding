package com.atguigui.springcloud.dao.impl;

import org.springframework.stereotype.Repository;

import com.atguigui.springcloud.bean.User;
import com.atguigui.springcloud.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@Override
	public User getUserById(Integer id) {
		User user = new User();
		user.setUserId(id);
		user.setUserName("sunyu" + id);
		return user;
	}
	
	
}
