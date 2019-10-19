package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.atguigu.atcrowdfunding.util.Datas;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TRoleController {
	
	@Autowired
	private TRoleService roleService;
	
	
	Logger log = LoggerFactory.getLogger(TRoleController.class);
	@RequestMapping("/role/index")
	public String index() {
		
		return "role/index";
	}
	@ResponseBody
	@RequestMapping("/role/toUpdate")
	public TRole toUpdate(Integer id) {
		TRole role = roleService.getTRoleById(id);
		
		return role;
	}
	
	@ResponseBody
	@RequestMapping("/role/listPermissionIdByRoleId")
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {
		List<Integer> permissionIdList = roleService.listPermissionIdByRoleId(roleId);
		
		return permissionIdList;
	}
	
	@PreAuthorize("hasRole('PM - 项目经理')")
	@ResponseBody
	@RequestMapping(value="/role/add",method=RequestMethod.POST)
	public String add(TRole role) {
		roleService.saveTRole(role);
		
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="/role/assignPermissionToRole",method=RequestMethod.POST)
	public String assignPermissionToRole(Integer roleId,Datas datas) {
		log.debug("roleId={}",roleId);
		log.debug("Datas={}",datas);
		//删除之前所有的授权
		roleService.deleteRoleAllPermissionByRoleId(roleId);
		//添加新的权限
		roleService.saveRolePermissionByAssign(roleId,datas.getIds());
		
		
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="/role/doUpdate",method=RequestMethod.POST)
	public String doUpdate(TRole role) {
		roleService.updateRole(role);
		return "ok";
	}
	@ResponseBody
	@RequestMapping(value="/role/delete",method=RequestMethod.POST)
	public String doUpdate(Integer id) {
		roleService.deleteRoleById(id);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="role/loadData")
	public PageInfo<TRole> loadDate(@RequestParam(value="pageNum",required=false,defaultValue="1") Integer pageNum,
									@RequestParam(value="pageSize",required=false,defaultValue="3") Integer pageSize,
									@RequestParam(value="condition",required=false,defaultValue="") String condition){
		PageHelper.startPage(pageNum, pageSize);
		Map<String,Object> params = new HashMap<>();
		params.put("condition", condition);
		PageInfo<TRole> page = roleService.listTRoleByPage(params);
		return page;
	}
}
