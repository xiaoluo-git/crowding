package com.atguigu.atcrowdfunding.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.github.pagehelper.PageInfo;


public interface TAdminService {


	TAdmin getTAdminByName(Map<String, Object> paramMap);

	PageInfo<TAdmin> listTAdminByPage(Map<String, Object> params);

	void saveTAdmin(TAdmin tAdmin);

	TAdmin selectTAdminById(Integer id);

	void updateTAdmin(TAdmin tAdmin);

	void deleteTAdminById(Integer id);

	void batchDelete(String ids);



}
