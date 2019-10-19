package com.atguigu.springboot.sevice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.springboot.bean.TAdmin;
import com.atguigu.springboot.mapper.TAdminMapper;
import com.atguigu.springboot.sevice.TAdminService;

@Service
public class TAdminServiceImpl implements TAdminService {
	
	@Autowired
	private TAdminMapper adminMapper;
	@Override
	public TAdmin getAdminById(Integer id) {
		// TODO Auto-generated method stub
		return adminMapper.getAdminById(id);
	}

}
