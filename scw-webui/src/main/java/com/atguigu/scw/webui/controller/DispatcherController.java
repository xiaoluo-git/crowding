package com.atguigu.scw.webui.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.resp.vo.TProjectResponseVo;
import com.atguigu.scw.webui.resp.vo.UserResponseVo;
import com.atguigu.scw.webui.service.ProjectServiceFeign;
import com.atguigu.scw.webui.service.UserServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DispatcherController {
	
	@Autowired
	private UserServiceFeign userServiceFeign;
	
	@Autowired
	private ProjectServiceFeign projectServiceFeign;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@GetMapping("/index")
	public String index(Model model) {
		
		List<TProjectResponseVo> data = (List<TProjectResponseVo>) redisTemplate.opsForValue().get("projectInfo");
		
		if(data == null) {
			AppResponse<List<TProjectResponseVo>> appResponse = projectServiceFeign.all();
			data = appResponse.getData();
			redisTemplate.opsForValue().set("projectInfo", data, 1, TimeUnit.HOURS);
		}
		model.addAttribute("projectVos", data);
		
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	
	@GetMapping("/member")
	public String main() {
		return "member";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if(session != null) {
			session.removeAttribute("userResponseVo");
			session.invalidate();
		}
		return "redirect:/index";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(@RequestParam(value="count",required=false) Integer count,@RequestParam("loginacct") String loginacct,@RequestParam("userpswd") String userpswd,HttpSession session) {
		 AppResponse<UserResponseVo> appResponse = userServiceFeign.login(loginacct, userpswd);
		 UserResponseVo data = appResponse.getData();
		 if(data != null) {
			log.debug("登录成功");
			session.setAttribute("userResponseVo", data);
			log.debug("count={}",count);
			if(count == null) {
				return "redirect:/index";
			}
			return "redirect:/project/confirm/order/" + count;
		}else {
			log.debug("登录失败");
			return "login";
		}
	}
	

}
