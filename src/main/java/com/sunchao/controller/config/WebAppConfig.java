package com.sunchao.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {
	
	private final static Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("toaddUser").setViewName("addUser");;
		super.addViewControllers(registry);
	}
	
	/**
	 * 处理静态资源
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
	}
	
	/**
	 * 注册自定义拦截器
	 * 
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		//注册拦截器
		InterceptorRegistration addInterceptor= registry.addInterceptor(new HandleLogin());
		addInterceptor.excludePathPatterns("/login");
		addInterceptor.excludePathPatterns("/addUser");
		addInterceptor.excludePathPatterns("/toaddUser");
		addInterceptor.excludePathPatterns("/");
		addInterceptor.excludePathPatterns("/**/*.js");
		addInterceptor.excludePathPatterns("/**/*.css");
		addInterceptor.excludePathPatterns("/**/*.img");
		addInterceptor.addPathPatterns("/index/**");
		addInterceptor.addPathPatterns("/index");
//		//页面拦截器
		
//		InterceptorRegistration addPageInterceptor= registry.addInterceptor(new HadlePage());
//		addPageInterceptor.excludePathPatterns("/index");
//		addPageInterceptor.excludePathPatterns("/index/*");
//		addPageInterceptor.excludePathPatterns("/login/*");
//		addInterceptor.addPathPatterns("/**");
	}
}
