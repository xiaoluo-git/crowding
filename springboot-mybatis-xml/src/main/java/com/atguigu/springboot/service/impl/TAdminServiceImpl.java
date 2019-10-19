package com.atguigu.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.springboot.bean.TAdmin;
import com.atguigu.springboot.mapper.TAdminMapper;
import com.atguigu.springboot.service.TAdminService;

@Service
public class TAdminServiceImpl implements TAdminService{
	
	@Autowired
	private TAdminMapper adminMapper;

	@Override
	public TAdmin getTAdminById(Integer id) {
		// TODO Auto-generated method stub
		return adminMapper.selectByPrimaryKey(id);
	}
}
