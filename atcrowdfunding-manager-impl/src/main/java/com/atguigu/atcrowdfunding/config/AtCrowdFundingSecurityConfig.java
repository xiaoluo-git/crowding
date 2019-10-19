package com.atguigu.atcrowdfunding.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AtCrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/static/**","/welcome.jsp","/toLogin").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/toLogin")
							.loginProcessingUrl("/doLogin")
							.usernameParameter("loginacct")
							.passwordParameter("userpswd")
							.defaultSuccessUrl("/main")
							
				.and()
				.logout().logoutSuccessUrl("/welcome.jsp")
				.and()
				.rememberMe();
			
			http.csrf().disable();
			http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
				
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response,
						AccessDeniedException accessDeniedException) throws IOException, ServletException {
					//X-Requested-With:XMLHttpRequest
					String header = request.getHeader("X-Requested-With");
					if("XMLHttpRequest".equals(header)) {
						
						response.getWriter().print("403");
					}else {
						request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
					}
				}
			});
			
			
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
}
