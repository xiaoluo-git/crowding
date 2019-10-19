package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.service.TPermessionService;

@Service
public class TPermessionServiceImpl implements TPermessionService {
	
	@Autowired
	private TPermissionMapper permissionMapper;

	@Override
	public List<TPermission> listPermessionToTree() {
		
		return permissionMapper.selectByExample(null);
	}

	@Override
	public void savePermission(TPermission permission) {
		permissionMapper.insertSelective(permission);
	}

	@Override
	public void updatePermission(TPermission permission) {
		permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public void deletePermission(Integer id) {
		permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TPermission getPermessionById(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

}
