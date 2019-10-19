package com.atguigu.atcrowdfunding.component;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.atguigu.atcrowdfunding.bean.TAdmin;

public class SecurityTAdmin extends User{
	
	 TAdmin tAdmin;
	
	
	public SecurityTAdmin(TAdmin tAdmin,Set<GrantedAuthority> authorities) {
		super(tAdmin.getLoginacct(), tAdmin.getUserpswd(),authorities );
		this.tAdmin = tAdmin;
	}

	
}
