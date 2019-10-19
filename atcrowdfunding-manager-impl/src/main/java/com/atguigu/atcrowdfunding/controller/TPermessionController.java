package com.atguigu.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.service.TPermessionService;

@Controller
public class TPermessionController {

	@Autowired
	private TPermessionService permessionService;
	
	@RequestMapping("/permission/index")
	public String index() {
		return "permission/index";
	}
	
	
	@ResponseBody
	@RequestMapping("/premission/loadTree")
	public List<TPermission> loadTree() {
		List<TPermission> list = permessionService.listPermessionToTree();
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/permission/toUpdate")
	public TPermission toUpdate(Integer id) {
		TPermission permission = permessionService.getPermessionById(id);
		return permission;
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/add",method=RequestMethod.POST)
	public String  add(TPermission permission) {
		permessionService.savePermission(permission);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/doUpdate",method=RequestMethod.POST)
	public String  doUpdate(TPermission permission) {
		permessionService.updatePermission(permission);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/delete",method=RequestMethod.POST)
	public String  delete(Integer id) {
		permessionService.deletePermission(id);
		return "ok";
	}
	
	
}
