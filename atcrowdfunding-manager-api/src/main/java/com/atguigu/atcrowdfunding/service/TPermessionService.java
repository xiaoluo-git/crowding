package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TPermission;

public interface TPermessionService {

	List<TPermission> listPermessionToTree();

	void savePermission(TPermission permission);

	void updatePermission(TPermission permission);

	void deletePermission(Integer id);

	TPermission getPermessionById(Integer id);

}
