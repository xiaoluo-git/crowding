package com.atguigu.atcrowdfunding.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TMember;
import com.atguigu.atcrowdfunding.mapper.TMemberMapper;
import com.atguigu.atcrowdfunding.service.TMemberService;
import com.atguigu.atcrowdfunding.util.Const;


@Service
public class TMemberServiceimpl implements TMemberService {
	
	
	@Autowired
	private TMemberMapper memberMapper;
	
	@Override
	public void saveMember(TMember member) {
		
		
		
	}

}
