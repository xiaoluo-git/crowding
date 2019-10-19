package com.atguigu.atcrowdfunding.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.atcrowdfunding.bean.TMember;
import com.atguigu.atcrowdfunding.service.TMemberService;

@Controller
public class TMemberController {
	
	Logger logger = LoggerFactory.getLogger(TMemberController.class);
	
	@Autowired
	private TMemberService memberService;
	
	
	@RequestMapping(value="/doregister", method=RequestMethod.POST)
	public String doregister(TMember member) {
		logger.debug("获取用户信息");
		
		memberService.saveMember(member);
		
		
		return "redirect:login";
	}
}
