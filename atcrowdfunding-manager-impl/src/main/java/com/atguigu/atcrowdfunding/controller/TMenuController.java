package com.atguigu.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.TMenuService;
import com.atguigu.atcrowdfunding.util.Datas;

@Controller
public class TMenuController {

	@Autowired
	private TMenuService menuService;

	@RequestMapping("/menu/index")
	public String index() {

		return "menu/index";
	}

	@ResponseBody
	@RequestMapping("/menu/loadTree")
	public List<TMenu> loadTree() {

		List<TMenu> list = menuService.listTMenu2Tree();

		return list;
	}

	@ResponseBody
	@RequestMapping("/menu/update")
	public TMenu update(Integer id) {

		TMenu menu = menuService.getMenu(id);

		return menu;
	}

	@ResponseBody
	@RequestMapping(value="menu/doAdd",method=RequestMethod.POST)
	public String doAdd(TMenu menu) {

		menuService.saveMenu(menu);

		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="menu/doUpdate",method=RequestMethod.POST)
	public String doUpdate(TMenu menu) {
		
		menuService.upDateMenu(menu);
		
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="menu/delete",method=RequestMethod.POST)
	public String delete(Integer id) {
		
		menuService.deleteMenuById(id);
		
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/menu/listPermissionIdByMenuId")
	public List<Integer> listPermissionIdByMenuId(Integer menuId) {
		
		List<Integer> permissionList = menuService.listPermissionIdByMenuId(menuId);
		
		return permissionList;
	}
	
	@ResponseBody
	@RequestMapping(value="/menu/assignPermissionToMenu",method=RequestMethod.POST)
	public String assignPermissionToMenu(Integer menuId,Datas ds) {
		//删除当前菜单的 所有权限
		menuService.deleteMenuAllPermission(menuId);
		
		//分配新的权限
		menuService.insertPermissionToMenu(menuId,ds.getIds());
		 
		
		return "ok";
	}

}
