package com.sunchao.controller.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunchao.util.ConstantEnum;

/**
 * 自定义拦截器(拦截非法地址)
 * 
 * @author 91527
 *
 */
public class HadlePage extends HandlerInterceptorAdapter {

	/**
	 * 执行之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.sendRedirect(ConstantEnum.PAGE_404);
		return false;
	}

	/**
	 * 执行之后 渲染之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 渲染之后(用于清理资源)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}

	/**
	 * 并发之后
	 */
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	}
}
