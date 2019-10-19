package com.atguigu.scw.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.bean.TMemberAddressExample;
import com.atguigu.scw.user.mapper.TMemberAddressMapper;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.resp.vo.UserAddressVo;
import com.atguigu.scw.user.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private TMemberMapper memberMapper;
	
	@Autowired
	private TMemberAddressMapper memberAddressMapper;

	@Override
	public TMember getMemberById(Integer memberId) {
		
		return memberMapper.selectByPrimaryKey(memberId);
	}

	@Override
	public List<UserAddressVo> ListAllAddressByAccessToken(String memberId) {
		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));
		List<TMemberAddress> list = memberAddressMapper.selectByExample(example );
		List<UserAddressVo> voList = new ArrayList<>();
		for (TMemberAddress memberAddress : list) {
			UserAddressVo userAddressVo = new UserAddressVo();
			BeanUtils.copyProperties(memberAddress, userAddressVo);
			voList.add(userAddressVo);
		}
		return voList;
	}
	
}
