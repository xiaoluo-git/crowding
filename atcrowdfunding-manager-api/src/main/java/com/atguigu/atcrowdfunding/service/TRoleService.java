package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

public interface TRoleService {

	PageInfo<TRole> listTRoleByPage(Map<String, Object> params);

	void saveTRole(TRole role);

	TRole getTRoleById(Integer id);

	void updateRole(TRole role);

	void deleteRoleById(Integer id);

	List<TRole> listAllRole();

	List<Integer> listRoleByAdminId(Integer id);

	void saveAdminAndRoleRelationship(Integer[] id, Integer adminId);

	void deleteAdminAndRoleRelationship(String roleIds, Integer adminId);

	List<Integer> listPermissionIdByRoleId(Integer roleId);

	void deleteRoleAllPermissionByRoleId(Integer roleId);

	void saveRolePermissionByAssign(Integer roleId,List<Integer> ids);

}
