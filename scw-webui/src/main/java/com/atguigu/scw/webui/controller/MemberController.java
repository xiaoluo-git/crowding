package com.atguigu.scw.webui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {
	
	@GetMapping("/member/minecrowdfunding")
	public String myOrderList() {
		log.debug("支付结果同步处理。。。。");
		return "member/minecrowdfunding";
	}
}
