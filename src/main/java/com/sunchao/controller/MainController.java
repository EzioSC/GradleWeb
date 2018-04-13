package com.sunchao.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunchao.entity.User;
import com.sunchao.repositery.UserRepositery;
import com.sunchao.util.ConstantEnum;
import com.sunchao.util.MD5Util;

import net.minidev.json.JSONObject;


@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private HttpServletResponse respoionse;
	
	@Autowired
	private UserRepositery userR;
	
	@Autowired
	private HttpSession session;
	
	
	@RequestMapping(value="")
	public String checkCookies(Model model,HttpServletRequest request) {
		User user = new User();
		Cookie [] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cool :cookies) {
				if(cool.getName().equals("loginid")) {
					user.setLoginid(cool.getValue());
				}
				if(cool.getName().equals("password")) {
					user.setPassword(cool.getValue());
				}
			}
			user = checkUser(user);
			if( user == null) {
				//model.addAttribute("msg", "账号信息过期！！！");
				return "login";
			}else {
				model.addAttribute(ConstantEnum.SESSION_KEY,user);
				session.setAttribute(ConstantEnum.SESSION_KEY, user);
				return "index";
			}
		}
		return "login";
	}
	
	@RequestMapping(value="loginUser",method= {RequestMethod.GET,RequestMethod.POST})
	public String toIndex(Model model,@ModelAttribute("user")User user,@RequestParam("cool")String cool) {
		user = checkUser(user);
		if( user == null) {
			model.addAttribute("msg", "输入的账号密码错误！！");
			return "login";
		}
		model.addAttribute(ConstantEnum.SESSION_KEY,user);
		session.setAttribute(ConstantEnum.SESSION_KEY,user);
		//保存cookies
		if(cool.equals("on")) {
			Cookie cookielog = new Cookie("loginid",user.getLoginid());
			Cookie cookiepwd = new Cookie("password",user.getPassword());
			cookielog.setMaxAge(60*60*24*30);
			cookiepwd.setMaxAge(60*60*24*30);
			
			respoionse.addCookie(cookielog);
			respoionse.addCookie(cookiepwd);
		}
		return "index";
	}
	
	private User checkUser(User user) {
		return userR.findByloginidAndPassword(user.getLoginid(), MD5Util.md5Password(user.getPassword()));
	}
	
	@ResponseBody
	@RequestMapping(value="addUser",method=RequestMethod.POST)
	public JSONObject addUser(User user) {
		JSONObject jsOb = new JSONObject();
		boolean test = true; 
		if(null == userR.findByloginid(user.getLoginid())) {
			try {
				user.setPassword(MD5Util.md5Password(user.getPassword()));
				user = userR.saveAndFlush(user);
				jsOb.put("msg", "注册成功");
				test = true;
			}catch(Exception e) {
				jsOb.put("msg","内部错误");
			}
		}else {
			jsOb.put("msg", "用户已被注册！");
		}
		jsOb.put("success", test);
		return jsOb;
	}
}
