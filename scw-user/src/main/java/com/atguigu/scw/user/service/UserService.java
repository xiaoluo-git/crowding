package com.atguigu.scw.user.service;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.req.vo.UserRegistVo;
import com.atguigu.scw.user.resp.vo.UserResponseVo;

public interface UserService {

	int saveTMember(UserRegistVo userRegistVo);

	UserResponseVo getUserByLogin(String loginacct, String password);

}
