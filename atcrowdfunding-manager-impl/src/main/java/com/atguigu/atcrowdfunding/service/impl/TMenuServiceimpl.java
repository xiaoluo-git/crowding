package com.atguigu.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.bean.TPermissionMenuExample;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMenuMapper;
import com.atguigu.atcrowdfunding.service.TMenuService;

@Service
public class TMenuServiceimpl implements TMenuService {

	@Autowired
	private TMenuMapper menuMapper;
	
	@Autowired
	private TPermissionMenuMapper permissionMenuMapper;

	@Override
	public List<TMenu> listTMenu() {
		List<TMenu> list = menuMapper.selectByExample(null);
		List<TMenu> parents = new ArrayList<>();
		for (TMenu tMenu : list) {
			if (tMenu.getPid() == 0) {
				parents.add(tMenu);
			}
		}
		

		for (TMenu parent : parents) {
			parent.setChilds(new ArrayList<TMenu>());
			for (TMenu tMenu : list) {
				if (tMenu.getPid() == parent.getId()) {
					parent.getChilds().add(tMenu);

				}
			}
		}

		return parents;
	}

	@Override
	public List<TMenu> listTMenu2Tree() {
		
		
		return menuMapper.selectByExample(null);
	}

	@Override
	public void saveMenu(TMenu menu) {
		menuMapper.insertSelective(menu);
		
	}

	@Override
	public TMenu getMenu(Integer id) {
		// TODO Auto-generated method stub
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void upDateMenu(TMenu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
		
	}

	@Override
	public void deleteMenuById(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Integer> listPermissionIdByMenuId(Integer menuId) {
		
		return permissionMenuMapper.listPermissionIdByMenuId(menuId);
	}

	@Override
	public void deleteMenuAllPermission(Integer menuId) {
		TPermissionMenuExample example = new TPermissionMenuExample();
		example.createCriteria().andMenuidEqualTo(menuId);
		permissionMenuMapper.deleteByExample(example );
	}

	@Override
	public void insertPermissionToMenu(Integer menuId, List<Integer> ids) {
		permissionMenuMapper.insertPermissionToMenu(menuId,ids);
	}

	

}
