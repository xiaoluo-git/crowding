package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.atguigu.atcrowdfunding.util.Const;

public class SystemUpInitListener implements ServletContextListener{
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String contextPath = servletContext.getContextPath();
		servletContext.setAttribute(Const.PATH, contextPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
