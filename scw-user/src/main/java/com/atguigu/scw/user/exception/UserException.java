package com.atguigu.scw.user.exception;

import com.atguigu.scw.user.enums.UserExceptionEnum;

public class UserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserException() {
		// TODO Auto-generated constructor stub
	}
	
	public UserException(UserExceptionEnum enums) {
		super(enums.getMsg());
	}

}
