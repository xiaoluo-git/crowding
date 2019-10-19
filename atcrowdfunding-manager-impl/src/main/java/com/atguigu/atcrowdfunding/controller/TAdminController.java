package com.atguigu.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TAdminController {
	
	
	@Autowired
	private TAdminService adminService;
	
	@Autowired
	private TRoleService roleService;
	
	Logger log = LoggerFactory.getLogger(TAdminController.class);
	
	@RequestMapping(value="/admin/index")
	public String index(@RequestParam(value="pageNum",required=false,defaultValue="1") Integer pageNum ,
						@RequestParam(value="pageSize",required=false,defaultValue="3") Integer pageSize,
					    Model model,String condition) {
		PageHelper.startPage(pageNum, pageSize);
		Map<String , Object> params = new HashMap<>();
		params.put("condition",condition);
		PageInfo<TAdmin> page = adminService.listTAdminByPage(params);
		model.addAttribute("page",page);
	
		return "admin/index";
	}
	
	@PreAuthorize("hasRole('PM - 项目经理')")
	@RequestMapping("/admin/toAdd")
	public String toAdd() {
		
		return "admin/add";
	}
	@RequestMapping("/admin/toUpdate")
	public String toUpdate(Integer id,Model model) {
		TAdmin tAdmin = adminService.selectTAdminById(id);
		model.addAttribute("tAdmin" ,tAdmin);
		return "admin/update";
	}
	@RequestMapping("/admin/doAdd")
	public String doAdd(TAdmin tAdmin) {
		adminService.saveTAdmin(tAdmin);
		
		return "redirect:/admin/index";
	}
	
	@RequestMapping("/admin/doUpdate")
	public String doUpdate(Integer pageNum ,TAdmin tAdmin) {
		adminService.updateTAdmin(tAdmin);
		
		return "redirect:/admin/index?pageNum=" + pageNum;
	}
	
	@RequestMapping("/admin/delete")
	public String delete(Integer pageNum ,Integer id) {
		adminService.deleteTAdminById(id);
		
		return "redirect:/admin/index?pageNum=" + pageNum;
	}
	
	@RequestMapping("/admin/batchDelete")
	public String batchDelete(String ids,Integer pageNum) {
		adminService.batchDelete(ids);
		return "redirect:/admin/index?pageNum=" + pageNum;
	}
	
	@ResponseBody
	@RequestMapping("/admin/assignRoleToAdmin")
	public String assignRoleToAdmin(Integer[] id,Integer adminId) {
		log.debug("id={}",id);
		log.debug("adminId={}",adminId);
		
		roleService.saveAdminAndRoleRelationship(id,adminId);
		
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/admin/deleteRoleForAdmin")
	public String deleteRoleForAdmin(String roleIds,Integer adminId) {
		log.debug("roleIds={}",roleIds);
		log.debug("adminId={}",adminId);
		
		roleService.deleteAdminAndRoleRelationship(roleIds,adminId);
		
		return "ok";
	}
	
	@RequestMapping("/admin/toAssign")
	public String toAssign(Integer id,Model model) {
		//查询所有角色
		List<TRole> roleList = roleService.listAllRole();
		List<TRole> assignList = new ArrayList<>();
		List<TRole> unAssignList = new ArrayList<>();
		//查询当前用户所拥有的角色
		List<Integer> roleIds = roleService.listRoleByAdminId(id);
		
		for (TRole role : roleList) {
			if(roleIds.contains(role.getId())) {
				assignList.add(role);
			}else {
				unAssignList.add(role);
			}
		}
		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);
		return "admin/assignRole";
	}
	
	
}
