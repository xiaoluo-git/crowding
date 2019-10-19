package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.service.TMenuService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;

@Controller
public class DispatcherController {
	
	@Autowired
	private TAdminService adminService;
	
	@Autowired
	private TMenuService menuService;
	
	Logger logger = LoggerFactory.getLogger(DispatcherController.class);
	@RequestMapping("/index")
	public String index() {
		logger.debug("跳转的程序的主页面");
		return "index";
	}
	
	@RequestMapping("/toLogin")
	public String login() {
		logger.debug("跳转到登录页面");
		return "login";
	}
	
	@RequestMapping("/main")
	public String main(HttpSession session) {
		logger.debug("跳转到主页面");
		
		List<TMenu> parents = menuService.listTMenu();
		session.setAttribute("parents", parents);
		return "main";
	}
	
	
	
	@RequestMapping("/register")
	public String register() {
		logger.debug("跳转到注册页面");
		return "register";
	}
	
	/*@RequestMapping(value="/doLogin", method=RequestMethod.POST)
	public String doLogin(String loginacct,String userpswd,HttpSession session,Model model) {
		logger.debug("开始登录");
		
		logger.debug("logginacct={}",loginacct);
		logger.debug("userpswd={}",userpswd);
		
		logger.debug(MD5Util.digest("123456"));
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);
		
	try {
		TAdmin tAdmin = adminService.getTAdminByName(paramMap);
		session.setAttribute(Const.LOGIN_ADMIN, tAdmin);
		logger.debug("登录成功");
	} catch (Exception e) {
		logger.debug("登录失败{}",e.getMessage());
		model.addAttribute(Const.MESSAGE,e.getMessage());
		return "login";
	}
		return "redirect:/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		logger.debug("注销");
		if(session != null) {
			
			session.removeAttribute(Const.LOGIN_ADMIN);
			session.invalidate();
		}
		return "redirect:/index";
	}*/
}
