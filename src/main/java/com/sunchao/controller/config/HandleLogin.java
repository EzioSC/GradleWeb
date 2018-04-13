package com.sunchao.controller.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunchao.util.ConstantEnum;

/**
 * 自定义拦击器(非法登录)
 * 
 * @author 91527
 *
 */
class HandleLogin extends HandlerInterceptorAdapter {

	/**
	 * Contriller执行之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute(ConstantEnum.SESSION_KEY) == null) {
			response.sendRedirect("/");
			return false;
		}
		return true;
	}
}
