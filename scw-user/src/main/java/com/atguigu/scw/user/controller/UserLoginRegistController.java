package com.atguigu.scw.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.user.component.SmsTemplate;
import com.atguigu.scw.user.req.vo.UserRegistVo;
import com.atguigu.scw.user.resp.vo.UserResponseVo;
import com.atguigu.scw.user.service.UserService;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "用户登陆注册模块")
@RequestMapping("/user")
@RestController
@Slf4j
public class UserLoginRegistController {
	
	@Autowired
	private SmsTemplate smsTemplate;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private UserService userService;
	@ApiOperation(value="用户登陆") 
	@ApiImplicitParams(value={
			@ApiImplicitParam(value="登陆账号",name="loginacct"),
			@ApiImplicitParam(value="用户密码",name="password")
	})
	@PostMapping("/login")
	public AppResponse<UserResponseVo> login(String loginacct,String password){
			log.debug("loginacct={}",loginacct);
			log.debug("password={}",password);
		try {
			UserResponseVo userRespVo = userService.getUserByLogin(loginacct,password);
			log.debug("{}登录成功",loginacct);
			return AppResponse.ok(userRespVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<UserResponseVo> response = AppResponse.fail(null);
			log.debug("{}登录失败：{}",loginacct,e.getMessage());
			response.setMsg(e.getMessage());
			return response;
		}
		
	} 
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(UserRegistVo userRegistVo){
		//校验验证码
		String code = userRegistVo.getLoginacct();
		if(!StringUtil.isEmpty(code)) {
			String code1 = redisTemplate.opsForValue().get("code");
			if(!StringUtil.isEmpty(code1)) {
				if(code.equals(code1)) {
					//验证数据库是否存在此用户
					//验证邮箱是否已经注册
					
					int count = userService.saveTMember(userRegistVo);
					if(count == 1) {
						redisTemplate.delete(code);
						return AppResponse.ok("ok");
					}else {
						
						AppResponse<Object> appResponse = AppResponse.fail(null);
						appResponse.setMsg("保存失败");
						return appResponse;
					}
					
				}else {
					AppResponse<Object> appResponse = AppResponse.fail(null);
					appResponse.setMsg("验证码错误！");
					return appResponse;
				}
			}else {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("验证码失效！");
				return appResponse;
			}
		}else {
			AppResponse<Object> appResponse = AppResponse.fail(null);
			appResponse.setMsg("用户名为空！");
			return appResponse;
		}
		
		
	} 
	
	@ApiOperation(value="发送短信验证码") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String loginacct){
		
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			code.append(random.nextInt(10));
			
		}
		
		redisTemplate.opsForValue().set(loginacct,code.toString(),5,TimeUnit.MINUTES);
		
		Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", loginacct);
	    querys.put("param", "code:" + code);
	    querys.put("tpl_id", "TP1711063");
		try {
			smsTemplate.sendSms(querys);
			return AppResponse.ok("ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<Object> appResponse = AppResponse.fail(null);
			appResponse.setMsg(e.getMessage());
			return appResponse;
		}
		
	} 
	
	@ApiOperation(value="验证短信验证码") 
	@PostMapping("/valide")
	public AppResponse<Object> valide(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="重置密码") 
	@PostMapping("/reset")
	public AppResponse<Object> reset(){
		return AppResponse.ok("ok");
	} 
	
	

}
