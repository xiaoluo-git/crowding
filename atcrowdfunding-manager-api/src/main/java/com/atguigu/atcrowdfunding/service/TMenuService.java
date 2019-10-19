package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TMenu;

public interface TMenuService {

	List<TMenu> listTMenu();

	List<TMenu> listTMenu2Tree();

	void saveMenu(TMenu menu);

	TMenu getMenu(Integer id);

	void upDateMenu(TMenu menu);

	void deleteMenuById(Integer id);

	List<Integer> listPermissionIdByMenuId(Integer menuId);

	void deleteMenuAllPermission(Integer menuId);

	void insertPermissionToMenu(Integer menuId, List<Integer> ids);

	

}
