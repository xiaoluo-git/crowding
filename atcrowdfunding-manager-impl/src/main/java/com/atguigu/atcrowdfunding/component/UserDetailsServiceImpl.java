package com.atguigu.atcrowdfunding.component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private TAdminMapper adminMapper;
	
	@Autowired
	private TRoleMapper roleMapper;
	
	@Autowired
	private TPermissionMapper permissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> adminList = adminMapper.selectByExample(example );
		TAdmin tAdmin = null;
		if(adminList != null && adminList.size() == 1) {
			tAdmin = adminList.get(0);
			log.debug("tAdmin={}",tAdmin);
			
			Set<GrantedAuthority> authorities = new HashSet<>();
			
			List<TRole> currentAdminHasRoleList = roleMapper.listAdminHasRole(tAdmin.getId());
			log.debug("currentAdminHasRoleList={}",currentAdminHasRoleList);
			for (TRole tRole : currentAdminHasRoleList) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + tRole.getName()));
			}
			List<TPermission> currentAdminHasPermissionList = permissionMapper.listAdminHasPermission(tAdmin.getId());
			log.debug("currentAdminHasPermissionList={}",currentAdminHasPermissionList);
			log.debug(new BCryptPasswordEncoder().encode("123456"));
			for (TPermission tPermission : currentAdminHasPermissionList) {
				authorities.add(new SimpleGrantedAuthority(tPermission.getName()));
			}
			log.debug("authorities={}",authorities);
			//return new User(tAdmin.getLoginacct(), tAdmin.getUserpswd(), authorities);
			return new SecurityTAdmin(tAdmin, authorities);
		}else {
			return null;
		}
	}

}
