package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TAdminExample.Criteria;
import com.atguigu.atcrowdfunding.exception.LoginException;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.util.AppDateUtils;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.github.pagehelper.PageInfo;

@Service
public class TAdminServiceImpl implements TAdminService{

	
	@Autowired
	private TAdminMapper adminMapper;


	@Override
	public TAdmin getTAdminByName(Map<String, Object> paramMap) {
		
		TAdminExample example = new TAdminExample();
	    example.createCriteria().andLoginacctEqualTo(paramMap.get("loginacct") + "");
		
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		if(list == null || list.size() == 0) {
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
		}
		
		 TAdmin tAdmin = list.get(0);
		if(!tAdmin.getUserpswd().equals(MD5Util.digest(String.valueOf(paramMap.get("userpswd"))))) {
			throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
		}
		
		return tAdmin;
	}


	@Override
	public PageInfo<TAdmin> listTAdminByPage(Map<String, Object> params) {
		String condition = (String) params.get("condition");
		TAdminExample example = new TAdminExample();
		
		if(!StringUtil.isEmpty(condition)) {
			Criteria criteria1 = example.createCriteria().andLoginacctLike("%" + condition + "%");
			Criteria criteria2 = example.createCriteria().andUsernameLike("%" + condition + "%");
			Criteria criteria3 = example.createCriteria().andEmailLike("%" + condition + "%");
			example.or(criteria2);
			example.or(criteria3);
		}
		example.setOrderByClause("createtime desc");
		List<TAdmin> list = adminMapper.selectByExample(example);
		PageInfo<TAdmin> pageInfo = new PageInfo<>(list,5);
		return pageInfo;
	}


	@Override
	public void saveTAdmin(TAdmin tAdmin) {
		
		tAdmin.setCreatetime(AppDateUtils.getFormatTime());
		tAdmin.setUserpswd(Const.DEFAULT_USERPSWD);
		adminMapper.insertSelective(tAdmin);
		
	}


	@Override
	public TAdmin selectTAdminById(Integer id) {
		TAdmin tAdmin = adminMapper.selectByPrimaryKey(id);
		return tAdmin;
	}


	@Override
	public void updateTAdmin(TAdmin tAdmin) {
		adminMapper.updateByPrimaryKeySelective(tAdmin);
	}


	@Override
	public void deleteTAdminById(Integer id) {
		adminMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void batchDelete(String ids) {
		adminMapper.batchDelete(ids);
		
	}



}
