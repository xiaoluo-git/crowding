package com.atguigu.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springboot.bean.TAdmin;
import com.atguigu.springboot.service.TAdminService;

@RestController
public class TAdminController {
	
	@Autowired
	private TAdminService adminService;
	
	@ResponseBody
	@GetMapping("/getTAdminById/{id}")
	public TAdmin getTAdminById(@PathVariable("id") Integer id ) {
		
		return adminService.getTAdminById(id);
	}
}
