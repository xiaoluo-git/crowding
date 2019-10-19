package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.bean.TRolePermissionExample;
import com.atguigu.atcrowdfunding.mapper.TAdminRoleMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.mapper.TRolePermissionMapper;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;

@Service
public class TRoleServiceImpl implements TRoleService {
	
	@Autowired
	private TRoleMapper roleMapper;
	
	@Autowired
	private TAdminRoleMapper adminRoleMapper;
	
	@Autowired
	private TRolePermissionMapper rolePermissionMapper;

	@Override
	public PageInfo<TRole> listTRoleByPage(Map<String, Object> params) {
		TRoleExample example = new TRoleExample();
		String condition = (String)params.get("condition");
		if(!StringUtils.isEmpty(condition)) {
			
			example.createCriteria().andNameLike("%" +condition + "%");
		}
		example.setOrderByClause("id desc");
		List<TRole> list = roleMapper.selectByExample(example );
		PageInfo<TRole> page = new PageInfo<>(list, 5);
		return page;
	}

	@Override
	public void saveTRole(TRole role) {
		roleMapper.insertSelective(role);
		
	}

	@Override
	public TRole getTRoleById(Integer id) {
		
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateRole(TRole role) {
		roleMapper.updateByPrimaryKeySelective(role);
		
	}

	@Override
	public void deleteRoleById(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public List<TRole> listAllRole() {
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<Integer> listRoleByAdminId(Integer id) {
		
		return adminRoleMapper.listRoleByAdminId(id);
	}

	@Override
	public void saveAdminAndRoleRelationship(Integer[] id, Integer adminId) {
		adminRoleMapper.saveAdminAndRoleRelationship(id,adminId);
		
	}

	@Override
	public void deleteAdminAndRoleRelationship(String roleIds, Integer adminId) {
		adminRoleMapper.deleteAdminAndRoleRelationship(roleIds,adminId);
		
	}

	@Override
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {
		
		return rolePermissionMapper.listPermissionIdByRoleId(roleId);
	}

	@Override
	public void deleteRoleAllPermissionByRoleId(Integer roleId) {
		
		TRolePermissionExample example = new TRolePermissionExample();
		example.createCriteria().andRoleidEqualTo(roleId);
		rolePermissionMapper.deleteByExample(example );
		
	}

	@Override
	public void saveRolePermissionByAssign(Integer roleId,List<Integer> ids) {
		rolePermissionMapper.saveRolePermissionByAssign(roleId,ids);
	}

}
