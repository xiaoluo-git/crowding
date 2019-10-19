package com.atguigu.scw.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberExample;
import com.atguigu.scw.user.enums.UserExceptionEnum;
import com.atguigu.scw.user.exception.UserException;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.req.vo.UserRegistVo;
import com.atguigu.scw.user.resp.vo.UserResponseVo;
import com.atguigu.scw.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private TMemberMapper memberMapper;
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED)
	public int saveTMember(UserRegistVo userRegistVo) {
		// TODO Auto-generated method stub
		try {
			String userpswd = userRegistVo.getUserpswd();
			userRegistVo.setUserpswd(new BCryptPasswordEncoder().encode(userpswd));
			TMember member = new TMember();
			BeanUtils.copyProperties(userRegistVo, member);
			member.setUsername(userRegistVo.getLoginacct());
			log.debug("转换的member:{}",member);
			
			int count = memberMapper.insert(member);
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 log.debug("注册会员失败信息{}",e.getMessage());
			throw new UserException(UserExceptionEnum.SAVE_USER_ERROR);
		}
	}



	@Override
	public UserResponseVo getUserByLogin(String loginacct, String password) {
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TMember> members = memberMapper.selectByExample(example );
		if(members == null || members.size() == 0) {
			throw new UserException(UserExceptionEnum.LOGINACCT_UNEXIST);
		}
		TMember member = members.get(0);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(!encoder.matches(password, member.getUserpswd())){
			log.debug("密码：{}",encoder.encode("123456"));
			throw new UserException(UserExceptionEnum.PASSWORD_ERROR);
		}
		UserResponseVo userRespVo = new UserResponseVo();
		
		BeanUtils.copyProperties(member, userRespVo);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		userRespVo.setAccessToken(uuid);
		
		
		redisTemplate.opsForValue().set(uuid, member.getId().toString());
		return userRespVo;
	}
}
