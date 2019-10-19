package com.atguigu.scw.user.service;

import java.util.List;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.resp.vo.UserAddressVo;

public interface MemberService {

	TMember getMemberById(Integer memberId);

	List<UserAddressVo> ListAllAddressByAccessToken(String memberId);

}
